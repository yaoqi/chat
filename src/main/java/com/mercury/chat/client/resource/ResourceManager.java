package com.mercury.chat.client.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {
	
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",Locale.getDefault());
	
	public static String getMessage(String key){
		//if not found, there will throw an exception.
		return resourceBundle.getString(key);
	}
	
}
