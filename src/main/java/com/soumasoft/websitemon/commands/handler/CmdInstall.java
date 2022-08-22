package com.soumasoft.websitemon.commands.handler;

import java.io.File;
import java.util.Arrays;

import org.javalite.activejdbc.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.Config;
import com.soumasoft.websitemon.config.SettingDefinition;
import com.soumasoft.websitemon.db.Db;
import com.soumasoft.websitemon.db.DbManager;
import com.soumasoft.websitemon.model.Setting;

/**
 * 
 * Command to prepare websitemon for first use or to reset the current
 * websitemon installation (after user confirmation).
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdInstall implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdInstall.class);

	public void execute(Config cfg) {

		File dbFile = new File(cfg.getDbFile());
		boolean isInitialSetup = !dbFile.exists();

		dbFile.getParentFile().mkdirs();

		try (DB db = DbManager.connect(cfg)) {

			// create table "websites"
			logger.info("CREATE TABLE IF NOT EXISTS '" + Db.WEBSITES.TABLENAME + "'... ");
			StringBuilder sql = new StringBuilder();
			sql.append("CREATE TABLE IF NOT EXISTS " + Db.WEBSITES.TABLENAME + " (");
			sql.append(Db.WEBSITES.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
			sql.append(Db.WEBSITES.COL_URL + " VARCHAR(250) NOT NULL, ");
			sql.append("CONSTRAINT UC_URL UNIQUE (" + Db.WEBSITES.COL_URL + ")");
			sql.append(");");
			db.exec(sql.toString());
			logger.info("done");

			// create table "settings"
			logger.info("CREATE TABLE IF NOT EXISTS '" + Db.SETTINGS.TABLENAME + "'... ");
			sql = new StringBuilder();
			sql.append("CREATE TABLE IF NOT EXISTS " + Db.SETTINGS.TABLENAME + " (");
			sql.append(Db.SETTINGS.COL_NAME + " VARCHAR(50) PRIMARY KEY, ");
			sql.append(Db.SETTINGS.COL_VALUE + " VARCHAR(250) ");
			sql.append(");");
			db.exec(sql.toString());
			logger.info("done");

			// add expected returncode if not exists
			if (db.count("pragma_table_info", "name = '" + Db.WEBSITES.COL_EXPECTED_RETURNCODE + "'") == 0) {
				logger.info("ALTER TABLE '" + Db.WEBSITES.TABLENAME + "' ADD " + Db.WEBSITES.COL_EXPECTED_RETURNCODE
						+ " INTEGER DEFAULT 200 NOT NULL ...");
				sql = new StringBuilder();
				sql.append("ALTER TABLE '" + Db.WEBSITES.TABLENAME + "' ADD " + Db.WEBSITES.COL_EXPECTED_RETURNCODE
						+ " INTEGER DEFAULT 200 NOT NULL;");
				db.exec(sql.toString());
				logger.info("done");
			}

			if (isInitialSetup) {
				logger.info("init default setting values... ");
				Arrays.asList(SettingDefinition.values()).forEach(sett -> {
					new Setting().set(Db.SETTINGS.COL_NAME, sett.getName())
							.set(Db.SETTINGS.COL_VALUE, sett.getDefaultValue()).saveIt();
				});
			}
			logger.info("done");
		}

	}

	@Override
	public boolean requiresDb() {
		return false;
	}

}
