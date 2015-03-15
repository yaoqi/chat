package com.mercury.chat.common.constant;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public enum LoginFlag {

	SUCCESS((byte) 0, "0","Login successfully!"), 
	FAIL((byte) 1, "1", "Login failed!"), 
	NOT_LOGIN((byte) 2, "2", "Invalid operation:not logged in!"),
	LOGGED_IN((byte) 3,"3", "Invalid operation:already logged in!");

	private byte key;
	
	private String value;
	
	private String message;

	private LoginFlag(byte key, String value, String message) {
		this.key = key;
		this.value = value;
		this.message = message;
	}
	
	public byte key(){
		return this.key;
	}
	
	public String value() {
		return this.value;
	}

	public String message(){
		return this.message;
	}
	
	public boolean isSuccess(){
		return this == SUCCESS;
	}
	
	public boolean isThisType(String value){
		return StringUtils.equals(this.value, value);
	}
	
	public boolean isThisType(byte key){
		return ObjectUtils.equals(this.key, key);
	}
	
	public static LoginFlag valOf(String value){
		LoginFlag[] flags = LoginFlag.class.getEnumConstants();
		for(LoginFlag flag:flags){
			if(StringUtils.equals(value, flag.value())){
				return flag;
			}
		}
		return null;
	}
	
	public static LoginFlag valOf(byte key){
		LoginFlag[] flags = LoginFlag.class.getEnumConstants();
		for(LoginFlag flag:flags){
			if(ObjectUtils.equals(key, flag.key)){
				return flag;
			}
		}
		return null;
	}

}
