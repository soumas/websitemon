package com.soumasoft.websitemon.commands.handler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.javalite.activejdbc.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.CommandDefinition;
import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.config.SettingDefinition;
import com.soumasoft.websitemon.db.Db;
import com.soumasoft.websitemon.db.DbManager;
import com.soumasoft.websitemon.model.Website;
import com.soumasoft.websitemon.util.MailManager;

/**
 *
 * Command to send http head request to one or all monitored website(s) and evaluate the response.
 * If the response code is not 200, the check fails and a notification email is sent (if configured).
 * <arg> Requires id of the website to check (see -cmdList) or 'all' to run check for all monitored websites.
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdCheck implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdCheck.class);
	
	public void execute(Config cfg) {
		try (DB db = DbManager.connect(cfg)) {

			/* evaluate parameter */
			List<Website> wLst = new ArrayList<>();
			if(cfg.getMainCommandParam().equals("all")) {
				wLst.addAll(Website.<Website>findAll());
			} else {
				Website w = Website.findFirst(Db.WEBSITES.COL_ID+" = ?", cfg.getMainCommandParamAsLong());
				if(w != null) {					
					wLst.add(w);
				}
			}
			
			if (wLst.isEmpty()) {
				logger.info("website not found or monitoring list empty. use '-"+CommandDefinition.ADD.getCliOpt()+" http://yourwebsite.com' to add your website to monitoring list");
				return;
			}
			
			for(Website w : wLst) {
				String url = w.getString(Db.WEBSITES.COL_URL);
				logger.info(String.format("checking '%s'... ", url));
				HttpURLConnection connection = null;
				try {
					URL u = new URL(url);
					connection = (HttpURLConnection) u.openConnection();
					connection.setRequestMethod("HEAD");
					
					
					int expectedCode = w.getInteger(Db.WEBSITES.COL_EXPECTED_RETURNCODE);
					int code = connection.getResponseCode();
					if(code != expectedCode) {
						throw new RuntimeException(String.format("expected: %d, actual: %d", expectedCode, code));
					}
					logger.info(String.format("ok (responsecode %d)", code));
				} catch (Exception e) {
					logger.error("failed", e);
					
					sendFailNotification(cfg, url, e);
					
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}
	}

	/**
	 * Sends notification if settMailReceiver is defined
	 * @param cfg
	 * @param url
	 * @param e
	 * @throws EmailException 
	 */
	private void sendFailNotification(Config cfg, String url, Exception e) {
		String notifReceiver = cfg.getSettingValue(SettingDefinition.MAILRECEIVER);
		if(notifReceiver != null) {
			logger.info(String.format("sending notification to '%s'... ", notifReceiver));
			String subject = "CHECK FAILED FOR " + url;
			String msg = String.format("websitemon (instance: '%s') check failed for url %s\r\nMessage: %s", cfg.getSettingValue(SettingDefinition.DESCRIPTION), url, e.getMessage());
			if(MailManager.sendMail(cfg, subject, msg)) {
				logger.info("success");
			} else {
				logger.info("failed");
			}
		} else {
			logger.info("No notification is sent because settMailReceiver is undefined.");
		}
	}
	
	@Override
	public boolean requiresDb() {
		return true;
	}


}
