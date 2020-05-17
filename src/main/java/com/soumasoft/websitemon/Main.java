package com.soumasoft.websitemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.config.ConfigManager;
import com.soumasoft.websitemon.db.DbManager;

/**
 * Main class of websitemon, a very basic java cli tool to check
 * if defined websites are online and notify by mail if not
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class Main {

	final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {

		try {

			Config cfg = ConfigManager.prepareConfig(args);
			
			logger.info(String.format("command: %s", cfg.getMainCommand().getCliOpt()));

			// create handler instance and execute command
			CommandHandler cmdHandler = ((CommandHandler)cfg.getMainCommand().getHandlerClazz().getDeclaredConstructor().newInstance());
			
			// init db if required
			if(cmdHandler.requiresDb()) {
				DbManager.checkPreparedAndThrow(cfg);
				ConfigManager.initDbSettingMap(cfg);
			}			

			// execute command
			cmdHandler.execute(cfg);
						
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.exit(1);
		}
	}

}
