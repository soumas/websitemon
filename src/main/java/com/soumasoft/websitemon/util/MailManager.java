package com.soumasoft.websitemon.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.config.SettingDefinition;

/**
 * 
 * This class is responsible for mail actions
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class MailManager {

	final static Logger logger = LoggerFactory.getLogger(MailManager.class);
	
	public static boolean sendMail(Config cfg, String subject, String msg) {
		try {
			Email email = new SimpleEmail();
			email.setHostName(cfg.getSettingValue(SettingDefinition.SMTPHOST));
			email.setSmtpPort(cfg.getSettingValueInt(SettingDefinition.SMTPPORT)); //
			email.setAuthenticator(new DefaultAuthenticator(cfg.getSettingValue(SettingDefinition.SMTPUSER),
					cfg.getSettingValue(SettingDefinition.SMTPPWD)));
			email.setSSLOnConnect(cfg.getSettingValueBoolean(SettingDefinition.SMTPSSL));
			email.setFrom(cfg.getSettingValue(SettingDefinition.SMTPSENDER));
			email.addTo(cfg.getSettingValue(SettingDefinition.MAILRECEIVER));
			email.setSubject(subject);
			email.setMsg(msg);
			email.send();
			
			return true;
			
		} catch (NullPointerException nullEx) {
			logger.error("Missing configuration! Make sure that settMailReceiver and all SMTP settings are configured.");
		} catch (Exception mailEx) {
			logger.error("failed", mailEx);
		}		
		
		return false;
	}

}
