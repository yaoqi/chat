package com.mercury.chat.client.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ClientConfig {
	
	private final static Configuration config = getConfig();
	
	private static Configuration getConfig() {
		Configuration configuration = null;
		try {
			configuration =  new PropertiesConfiguration("chat_config.properties");
		} catch (ConfigurationException e) {
			//ignore this exception
		}
		return configuration;
	}
	
}
