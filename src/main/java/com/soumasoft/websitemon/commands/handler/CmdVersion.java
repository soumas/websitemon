package com.soumasoft.websitemon.commands.handler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
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
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        System.out.println(model.getVersion());
	}
	
}
