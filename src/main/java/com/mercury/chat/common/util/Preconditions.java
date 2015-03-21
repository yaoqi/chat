package com.mercury.chat.common.util;
import static com.google.common.base.Preconditions.checkNotNull;

public class Preconditions {
	
	public static void checkAllNotNull(Object... objects){
		for(Object object : objects){
			checkNotNull(object);
		}
	}
	
	public static void checkAllNotEmpty(String... values){
		for(String value : values){
			checkNotNull(value);
		}
	}
	
}
