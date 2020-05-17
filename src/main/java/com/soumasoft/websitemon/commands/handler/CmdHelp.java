package com.soumasoft.websitemon.commands.handler;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.ConfigManager;
import com.soumasoft.websitemon.config.Config;

/**
 * 
 * Command to print out websitemon usage instructions
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdHelp implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdHelp.class);
	
	public void execute(Config cfg) throws EmailException {
		ConfigManager.printHelp();
	}
	
}
