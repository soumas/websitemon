package com.soumasoft.websitemon.commands.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.mail.EmailException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soumasoft.websitemon.commands.CommandHandler;
import com.soumasoft.websitemon.config.Config;

/**
 * 
 * Command to print out websitemon version
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class CmdVersion implements CommandHandler {

	final static Logger logger = LoggerFactory.getLogger(CmdVersion.class);
	
	public void execute(Config cfg) throws EmailException, FileNotFoundException, IOException, XmlPullParserException {
		Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
		System.out.println(properties.getProperty("version"));
	}
	
	@Override
	public boolean requiresDb() {
		return false;
	}

}
