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
	ADD("cmdAdd", true, "add URL an expected response code (default: 200) to monitoring list. (<arg> URL[,STATUSCODE][;OTERHURL[,STATUSCODE]])", CmdAdd.class),
	CHECK("cmdCheck",true, "run all checks by default or just for the specified id (<arg> id)", CmdCheck.class),
	DELETE("cmdDelete", true, "delete URL from the monitoring list (<arg> id)", CmdDelete.class),
	HELP("cmdHelp",false, "show usage instructions", CmdHelp.class),
	HELP2("help",false, "alias for -cmdHelp", CmdHelp.class),
	INSTALL("cmdInstall", false, "prepare websitemon for first use or to update", CmdInstall.class),
	LIST("cmdList",false, "show list of all monitored urls", CmdList.class),
	MAILTEST("cmdMailtest",false, "send a testmail (useful for alive-check)", CmdMailtest.class),
	SETT_LIST("cmdSettList",false, "show persisted settings", CmdSettingList.class),
	SETT_PERSIST("cmdSettPersist",false, "persist settings provided by cli args (e.g.: '-cmdSettPersist -settSmtpHost mail.myserver.com')", CmdSettingPersist.class),
	VERSION("cmdVersion", false, "show websitemon version", CmdVersion.class),
	VERSION2("version", false, "alias for -cmdVersion", CmdVersion.class),
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
