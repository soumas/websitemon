package com.soumasoft.websitemon.commands.handler;

import java.util.Arrays;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.config.SettingDefinition;

/**
 * 
 * Command to print out applied settings. 
 * Note that settings provided by cli args overrule persisted settings.
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdSettingList implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdSettingList.class);
	
	public void execute(Config cfg) throws EmailException {
		
		Arrays.asList(SettingDefinition.values()).forEach(def -> {
			logger.info(def.getName() +": "+ cfg.getSettingDescription(def));
		});

	}
	
}
