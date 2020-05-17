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
 * Command to delete an URL from the monitoring list.
 * <arg> Requires id of the website to delete (see -cmdList).
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdDelete implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdDelete.class);
	
	public void execute(Config cfg) {
		
		// prepare url
		Long id = cfg.getMainCommandParamAsLong();
		
		// add to database
		try(DB db = DbManager.connect(cfg)) {
			Website w = Website.findFirst(Db.WEBSITES.COL_ID+" = ?", id);
			if(w == null) {
				logger.info(String.format("website with id %d not found!", id));
			} else {
				logger.info(String.format("deleted: %s", w.get(Db.WEBSITES.COL_URL)));
				w.delete();
			}
		}
	}
	
	@Override
	public boolean requiresDb() {
		return true;
	}

	
}
