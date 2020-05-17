package com.soumasoft.websitemon.commands.handler;

import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
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
 * Command to add an URL to the monitoring list.
 * <arg> Requires one or multpile (seperated by semicolon) valid URIs as cli param.
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdAdd implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdAdd.class);
	
	public void execute(Config cfg) {

		Arrays.asList(StringUtils.split(cfg.getMainCommandParam(), ";")).forEach(url -> {
			try {
				// prepare url
				url = url.trim();

				// validate url
				new URL(url).toURI();

				// add to database
				try (DB db = DbManager.connect(cfg)) {
					if (Website.findFirst(Db.WEBSITES.COL_URL + " = ?", url) != null) {
						logger.info(String.format("already listed: %s", url));
						return;
					} else {
						new Website().set(Db.WEBSITES.COL_URL, url).saveIt();
						logger.info(String.format("added: %s", url));
					}
				}
			} catch (Exception e) {
				logger.error(String.format("error adding '%s': %s", url, e.getMessage()));
			}
		});

	}

	@Override
	public boolean requiresDb() {
		return true;
	}

}
