package com.soumasoft.websitemon.commands;

import com.soumasoft.websitemon.config.Config;

/**
 * 
 * Each execution of websitemon has exactly one maincommand.
 * This command gets handled by an implementation of this interface.
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public interface CommandHandler {
	void execute(Config cfg) throws Exception;
}
