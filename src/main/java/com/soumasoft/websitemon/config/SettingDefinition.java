package com.soumasoft.websitemon.config;

import lombok.Getter;

/**
 * 
 * Definition of all supported settings.
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public enum SettingDefinition {
	
	DESCRIPTION("settDescription", "Setting: Description of this webmoninstance. used to identify websitemon installation","default-instance"),
	MAILRECEIVER("settMailReceiver", "Setting: E-Mail address to send notification mails to",null),
	SMTPHOST("settSmtpHost", "Setting: SMTP Host",null),
	SMTPPORT("settSmtpPort", "Setting: SMTP port (number)", "465"),
	SMTPUSER("settSmtpUser", "Setting: SMTP user", null),
	SMTPPWD("settSmtpPassword", "Setting: SMTP password", null),
	SMTPSENDER("settSmtpSender", "Setting: SMTP sender E-Mail address", null),
	SMTPSSL("settSmtpSSL","Setting: enable ssl for mail transfer (true/false)", "true");	
	
	@Getter
	private String name;
	@Getter
	private String description;
	@Getter
	private String defaultValue;
	
	private SettingDefinition(String name, String description, String defaultValue) {
		this.name = name;
		this.description = description;
		this.defaultValue = defaultValue;
	}
	
}
