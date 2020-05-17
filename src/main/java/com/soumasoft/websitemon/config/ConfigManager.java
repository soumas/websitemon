package com.soumasoft.websitemon.config;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.javalite.activejdbc.DB;

import com.soumasoft.websitemon.db.Db;
import com.soumasoft.websitemon.db.DbManager;
import com.soumasoft.websitemon.model.Setting;

/**
 * 
 * This class is responsible to prepare and provide configurations and settings
 * Config data gets fetched from cli args, db or default values.
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class ConfigManager {

	/**
	 * default location of the db file
	 */
	private static final String DEFAULT_DB = "./db/websitemon.db";
	
	/**
	 * location of db file may be defined by cli arg 'db'
	 */
	private static final String CLI_ARG_DB = "db";
	
	/**
	 * holds the cli options for parsing cli args and print help text
	 */
	private static Options options;
	
	/**
	 * Builds config instance using cli arguments and default values.
	 * Note that db settings are not initialized by this method, because db
	 * may not be initialized at this point.
	 * @param args (cli)
	 * @return
	 */
	public static Config prepareConfig(String[] args) {
		
		Config cfg = new Config();
		
		options = new Options();
		options.addOption(CLI_ARG_DB, true, "Path to SQLite database file (defaults to '"+DEFAULT_DB+"')");
		appendCommandOptions(options);
		appendConfigOptions(options);
		
        try {
        	// parse given arguments
        	CommandLine parsedArgs = new DefaultParser().parse(options, args);
        	
        	// init settings
        	initDb(cfg, parsedArgs);
        	initCommand(cfg, parsedArgs);
        	initSettings(cfg, parsedArgs);

        } catch (ParseException e) {
        	printHelp();
            throw new RuntimeException(e);
        }        
        
        return cfg;
	}


	/**
	 * prints help :)
	 */
	public static void printHelp() {
		new HelpFormatter().printHelp(100,"java -jar websitemon","", options, "");
	}


	/**
	 * Puts all given setting-cli-arguments into the config instance. 
	 * @param cfg
	 * @param parsedArgs
	 */
	private static void initSettings(Config cfg, CommandLine parsedArgs) {
		/* settings provided by cli arguments */
		cfg.setCliSettings(new HashMap<>());
		Arrays.asList(SettingDefinition.values()).stream().forEach(sett -> {
			if(parsedArgs.hasOption(sett.getName())) {
				String val = parsedArgs.getOptionValue(sett.getName());
				if(val.equals("null")) {
					val = null;
				}
				cfg.getCliSettings().put(sett, val);
			}
		});

	}

	/**
	 * Determines the main command and the main command param (if required by the command)
	 * Default if no command can be determined: "-cmdCheck all" 
	 * @param cfg
	 * @param parsedArgs
	 * @throws ParseException
	 */
	private static void initCommand(Config cfg, CommandLine parsedArgs) throws ParseException {
		// find the given command (each run has exactly one "main-command")
		Arrays.asList(CommandDefinition.values()).stream().forEach(cmd -> {
			if(parsedArgs.hasOption(cmd.getCliOpt())) {
				if(cfg.getMainCommand() != null) {
					throw new RuntimeException("multiple commands detected");
				}
				cfg.setMainCommand(cmd);
				if(cmd.getCliHasArg()) {
					cfg.setMainCommandParam(parsedArgs.getOptionValue(cmd.getCliOpt()));
				}
			}
		});
		
		// default "main-command" is "-cmdCheck all"
		if(cfg.getMainCommand() == null) {
			cfg.setMainCommand(CommandDefinition.CHECK);
			cfg.setMainCommandParam("all");
		}
	}

	/**
	 * Sets db file path to given cli arg 'db' or to default if cli arg 'db' not available.
	 * @param cfg
	 * @param parsedArgs
	 */
	private static void initDb(Config cfg, CommandLine parsedArgs) {
		// db file
		if(parsedArgs.hasOption(CLI_ARG_DB)) {
			cfg.setDbFile(parsedArgs.getOptionValue(CLI_ARG_DB));
		} else {
			cfg.setDbFile(DEFAULT_DB);
		}
	}
	
	/**
	 * Appends all CommandDefinitions to the options, so 
	 * that they are available as cli arguments.
	 * @param options
	 */
	private static void appendCommandOptions(Options options) {
		Arrays.asList(CommandDefinition.values()).forEach(cmd -> {
			Option o = new Option(cmd.getCliOpt(), cmd.getCliHasArg(), cmd.getCliDescription());
			options.addOption(o);
		});
	}
	
	/**
	 * Appends all SettingDefinitions to the options, so 
	 * that they are available as cli arguments.
	 * @param options
	 */
	private static void appendConfigOptions(Options options) {
		Arrays.asList(SettingDefinition.values()).forEach(sett -> {
			Option o = new Option(sett.getName(), true, sett.getDescription());
			options.addOption(o);
		});
	}


	/**
	 * Enriches the cfg with all persisted settings
	 * @param cfg
	 */
	public static void initDbSettingMap(Config cfg) {
		/* settings persisted in db */
		cfg.setDbSettings(new HashMap<>());
		try(DB db = DbManager.connect(cfg)) {
			Setting.<Setting> findAll().forEach(dbSett -> {
				Arrays.asList(SettingDefinition.values()).stream().filter(d -> d.getName().equals(dbSett.get(Db.SETTINGS.COL_NAME))).findFirst().ifPresent(sett -> {
					String val = dbSett.getString(Db.SETTINGS.COL_VALUE);
					if(val != null) {
						cfg.getDbSettings().put(sett, val);
					}
				});
			});
		}
	}
}
