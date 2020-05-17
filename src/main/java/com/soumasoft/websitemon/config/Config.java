package com.soumasoft.websitemon.config;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
@Getter
@Setter
public class Config {

	/**
	 * Path to SQLite DB file
	 */
	private String dbFile;
	
	/**
	 * The main command the websitemon has to execute ()
	 */
	private CommandDefinition mainCommand;
	
	/**
	 * The parameter for the main command (if required)
	 */
	private String mainCommandParam;
	
	/**
	 * List of settings provided with cli args
	 */
	private Map<SettingDefinition,String> cliSettings;
	
	/**
	 * List of settings persisted in db
	 */	
	private Map<SettingDefinition,String> dbSettings;
	
	
	/**
	 * Util method to pars the MainCommandParam to Long
	 * @return
	 */
	public Long getMainCommandParamAsLong() {
		try {
			return Long.parseLong(getMainCommandParam().trim());
		} catch (Exception e) {
			throw new RuntimeException("invalid parameter! id required");
		}
	}
	
	/**
	 * Returns applied setting value specified by def. Note that cli arguments has higher priority than persisted settings.
	 * If "description=true" it returns the setting enriched with additional information.
	 * @param def
	 * @param describe
	 * @return
	 */
	private String getSetting(SettingDefinition def, boolean describe) {
		
		StringBuilder sb = new StringBuilder();
		
		if(cliSettings.containsKey(def)) {
			sb.append(cliSettings.get(def));
			if(describe) {
				sb.append(" (cli)");
			}
			return sb.toString();
		} else if(dbSettings.containsKey(def)) {
			sb.append(dbSettings.get(def));
			if(describe) {
				sb.append(" (persisted)");
			}	
			return sb.toString();
		}
		
		return describe ? "undefined" : null;
	}
	
	/**
	 * Return description of the setting value behind def
	 * @param def
	 * @return
	 */
	public String getSettingDescription(SettingDefinition def) {
		return getSetting(def, true);
	}
	
	/**
	 * Return setting value behind def as String
	 * @param def
	 * @return
	 */
	public String getSettingValue(SettingDefinition def) {
		return getSetting(def, false);
	}
	
	/**
	 * Return setting value behind def as Integer
	 * @param def
	 * @return
	 */
	public Integer getSettingValueInt(SettingDefinition def) {
		String val = getSettingValue(def);
		if(val != null) {
			return Integer.parseInt(val);
		}
		return null;
	}
	
	/**
	 * Return setting value behind def as Boolean
	 * @param def
	 * @return
	 */
	public Boolean getSettingValueBoolean(SettingDefinition def) {
		String val = getSettingValue(def);
		if(val != null) {
			return Boolean.parseBoolean(val);
		}
		return null;
	}
}
