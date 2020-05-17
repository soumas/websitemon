package com.soumasoft.websitemon.commands.handler;

import org.apache.commons.mail.EmailException;
import org.javalite.activejdbc.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.CommandDefinition;
import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.db.Db;
import com.soumasoft.websitemon.db.DbManager;
import com.soumasoft.websitemon.model.Setting;

/**
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdSettingPersist implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdSettingPersist.class);
	
	public void execute(Config cfg) throws EmailException {

		if(cfg.getCliSettings() == null || cfg.getCliSettings().isEmpty()) {
			logger.info("no setting specified. use 'websitemon -"+CommandDefinition.SETT_PERSIST.getCliOpt()+" -settingname1 settingval1 -settingnam2 settingval2 ...'");
			return;
		}
		// f(Website.findFirst(Db.WEBSITES.COL_URL+" = ?", url) != null) {
		try(DB db = DbManager.connect(cfg)) {
			cfg.getCliSettings().forEach((sett, val) -> {
				logger.info(String.format("persist setting '%s' with '%s'... ", sett.getName(), val != "null" ? val : null));
				int count = Setting.update(Db.SETTINGS.COL_VALUE +" = ?", Db.SETTINGS.COL_NAME +" = ?", val, sett.getName());
				if(count ==  0) {
					new Setting().set(Db.SETTINGS.COL_NAME,sett.getName()).set(Db.SETTINGS.COL_VALUE,val).saveIt();
				}
				logger.info("done");
			});
		}
	}
}
