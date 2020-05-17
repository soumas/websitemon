package com.soumasoft.websitemon.commands.handler;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.config.SettingDefinition;
import com.soumasoft.websitemon.util.MailManager;

/**
 * 
 * Command to send a testmail. This can be usefull to check smtp settings or send 'websitemon is alive'-mails periodically
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdMailtest implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdMailtest.class);
	
	public void execute(Config cfg) throws EmailException {
		logger.info(String.format("sending testmail to '%s'", cfg.getSettingValue(SettingDefinition.MAILRECEIVER))+"... ");
		if(MailManager.sendMail(cfg, "websitemon Mailtest", String.format("Sending mails by websitemon (instance '%s') works", cfg.getSettingValue(SettingDefinition.DESCRIPTION)))) {
			logger.info("success");
		} else {
			logger.info("failed");
		}
	}
	
	@Override
	public boolean requiresDb() {
		return true;
	}
	
}
