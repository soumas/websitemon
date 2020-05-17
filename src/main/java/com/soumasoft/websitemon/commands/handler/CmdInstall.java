package com.soumasoft.websitemon.commands.handler;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import org.javalite.activejdbc.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.CommandDefinition;
import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.config.SettingDefinition;
import com.soumasoft.websitemon.db.Db;
import com.soumasoft.websitemon.db.DbManager;
import com.soumasoft.websitemon.model.Setting;

/**
 * 
 * Command to prepare websitemon for first use or to reset the current websitemon installation (after user confirmation).
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdInstall implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdInstall.class);
	
	public void execute(Config cfg) {		
		
		File dbFile = new File(cfg.getDbFile());		
		if(!dbFile.exists()) {
			dbFile.getParentFile().mkdirs();
			
			logger.info("create db... ");
			try(DB db = DbManager.connect(cfg)) {

				// SQLite creates db automatically if not exists
				logger.info("done");
				
				// create table "websites"
				logger.info("create table '"+Db.WEBSITES.TABLENAME+"'... ");
				StringBuilder sql = new StringBuilder();
				sql.append("CREATE TABLE IF NOT EXISTS "+Db.WEBSITES.TABLENAME+" (");
				sql.append(Db.WEBSITES.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
				sql.append(Db.WEBSITES.COL_URL + " VARCHAR(250) NOT NULL, ");
				sql.append("CONSTRAINT UC_URL UNIQUE ("+Db.WEBSITES.COL_URL+")");
				sql.append(");");
				db.exec(sql.toString());
				logger.info("done");
				
				// create table "settings"
				logger.info("create table '"+Db.SETTINGS.TABLENAME+"'... ");
				sql = new StringBuilder();
				sql.append("CREATE TABLE IF NOT EXISTS "+Db.SETTINGS.TABLENAME+" (");
				sql.append(Db.SETTINGS.COL_NAME + " VARCHAR(50) PRIMARY KEY, ");
				sql.append(Db.SETTINGS.COL_VALUE + " VARCHAR(250) ");
				sql.append(");");
				db.exec(sql.toString());
				logger.info("done");
				
				logger.info("init setting values... ");
				Arrays.asList(SettingDefinition.values()).forEach(sett -> {
					new Setting()
						.set(Db.SETTINGS.COL_NAME, sett.getName())
						.set(Db.SETTINGS.COL_VALUE, sett.getDefaultValue())
						.saveIt();
				});
				logger.info("done");
			}
			
		} else {
			System.out.println(String.format("---------- USER INPUT REQUIRED ----------\r\n%s already exists! delete (y) or cancel (n)?", cfg.getDbFile()));
			try(Scanner s = new Scanner(System.in)) {
				if(s.next().charAt(0) == 'y') {
					logger.info("delete db... ");
					dbFile.delete();
					logger.info("done (use '"+CommandDefinition.INSTALL.getCliOpt()+"' to recreate db)");
				}
			}
		}

	}
	
	@Override
	public boolean requiresDb() {
		return false;
	}

	
}
