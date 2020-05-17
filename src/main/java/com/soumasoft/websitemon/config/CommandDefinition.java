package com.soumasoft.websitemon.config;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.commands.handler.CmdAdd;
import com.soumasoft.websitemon.commands.handler.CmdCheck;
import com.soumasoft.websitemon.commands.handler.CmdDelete;
import com.soumasoft.websitemon.commands.handler.CmdHelp;
import com.soumasoft.websitemon.commands.handler.CmdInstall;
import com.soumasoft.websitemon.commands.handler.CmdList;
import com.soumasoft.websitemon.commands.handler.CmdMailtest;
import com.soumasoft.websitemon.commands.handler.CmdSettingList;
import com.soumasoft.websitemon.commands.handler.CmdSettingPersist;
import com.soumasoft.websitemon.commands.handler.CmdVersion;

import lombok.Getter;

/**
 * 
 * Definition of all supported main commands.
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
@Getter
public enum CommandDefinition {

	/* main commands */
	ADD("cmdAdd", true, "Command to add an URL to the monitoring list.\r\n<arg> Requires one or multpile (seperated by semicolon) valid URIs as cli param.", CmdAdd.class),
	CHECK("cmdCheck",true, "Command to send http head request to one or all monitored website(s) and evaluate the response. If the response code is not 200, the check fails and a notification email is sent (if configured).\r\n<arg> Requires id of the website to check (see -cmdList) or 'all' to run check for all monitored websites.", CmdCheck.class),
	DELETE("cmdDelete", true, "Command to delete an URL from the monitoring list.\r\n<arg> Requires id of the website to delete (see -cmdList).", CmdDelete.class),
	HELP("cmdHelp",false, "Command to print out websitemon usage instructions", CmdHelp.class),
	HELP2("help",false, "alias for -cmdHelp to meet general cli habits", CmdHelp.class),
	INSTALL("cmdInstall", false, "Command to prepare websitemon for first use or to reset the current websitemon installation (after user confirmation)", CmdInstall.class),
	LIST("cmdList",false, "Command to print out a list of all monitored websites/urls", CmdList.class),
	MAILTEST("cmdMailtest",false, "Command to send a testmail. This can be usefull to check smtp settings or send 'websitemon is alive'-mails periodically", CmdMailtest.class),
	SETT_LIST("cmdSettList",false, "Command to print out applied settings. Note that settings provided by cli args overrule persisted settings.", CmdSettingList.class),
	SETT_PERSIST("cmdSettPersist",false, "Command to persist settings provided by cli args.\r\nRequires setting key- value-pairs to persist (e.g.: '-cmdSettPersist -settSmtpHost mail.myserver.com')", CmdSettingPersist.class),
	VERSION("cmdVersion", false, "Command to print out websitemon version", CmdVersion.class),
	VERSION2("version", false, "alias for -cmdVersion to meet general cli habits", CmdVersion.class),
	;

	private String cliOpt;
	private Boolean cliHasArg;
	private String cliDescription;
	private Class<? extends CommandHandler> handlerClazz;
	
	private CommandDefinition(String opt, Boolean hasArg, String description, Class<? extends CommandHandler> handlerClazz) {
		this.cliOpt = opt;
		this.cliHasArg = hasArg;
		this.cliDescription = description;
		this.handlerClazz = handlerClazz;
	}
	
}
