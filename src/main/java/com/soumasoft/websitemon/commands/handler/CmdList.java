package com.soumasoft.websitemon.commands.handler;

import org.javalite.activejdbc.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.db.Db;
import com.soumasoft.websitemon.db.DbManager;
import com.soumasoft.websitemon.model.Website;

/**
 * 
 * Command to print out a list of all monitored websites/urls.
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdList implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdList.class);
	
	public void execute(Config cfg) {
		try(DB db = DbManager.connect(cfg)) {
	        Website.<Website> findAll().forEach(w -> {
	        	logger.info(w.get(Db.WEBSITES.COL_ID) +", "+ w.get(Db.WEBSITES.COL_URL));
	        });
		}
	}
	
	@Override
	public boolean requiresDb() {
		return true;
	}

}
