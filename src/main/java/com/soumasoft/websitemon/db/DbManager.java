package com.soumasoft.websitemon.db;

import java.io.File;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import com.soumasoft.websitemon.config.CommandDefinition;
import com.soumasoft.websitemon.config.Config;

/**
 * 
 * This class is responsible for db communication
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class DbManager {
	
	public static DB connect(Config cfg) {
		String url = "jdbc:sqlite:" + cfg.getDbFile();
		return Base.open("org.sqlite.JDBC", url, "root","");
	}

	public static void checkPreparedAndThrow(Config cfg) {
		File dbFile = new File(cfg.getDbFile());
		if(!dbFile.exists()) {
			throw new RuntimeException("database not ready! use '-"+CommandDefinition.INSTALL.getCliOpt()+"' to prepare websitemon");
		}
	}
	
}
