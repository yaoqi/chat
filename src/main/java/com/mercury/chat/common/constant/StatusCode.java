package com.mercury.chat.common.constant;

import org.apache.commons.lang.ObjectUtils;

import com.mercury.chat.common.struct.IMessage;

public enum StatusCode {
	OK(200, "Operate Success"){
		@Override
		public boolean isFailed() {
			return false;
		}
	}, 
	FAIL(400, "Operate Failure"),
	UNAUTHORIZED(401, "Unauthorized"), 
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	NOT_LOGIN(600, "Invalid operation:not logged in"),
	LOGGED_IN(601, "Invalid operation:already logged in"),
	USER_LOGIN(700, "User Login"){
		@Override
		public boolean isFailed() {
			return false;
		}
	},
	USER_LOGOFF(701, "User Logoff"){
		@Override
		public boolean isFailed() {
			return false;
		}
	};
	
	private int key;
	
	private String message;
	
	private StatusCode(int key, String message) {
		this.key = key;
		this.message = message;
	}
	
	public int key() {
		return key;
	}

	public String message() {
		return message;
	}

	public static StatusCode valOf(int key){
		StatusCode[] codes = StatusCode.class.getEnumConstants();
		for(StatusCode code:codes){
			if(ObjectUtils.equals(key, code.key)){
				return code;
			}
		}
		return null;
	}
	
	public static StatusCode valOf(IMessage message){
		int key = message.getHeader().getStatusCode();
		StatusCode[] codes = StatusCode.class.getEnumConstants();
		for(StatusCode code:codes){
			if(ObjectUtils.equals(key, code.key)){
				return code;
			}
		}
		return null;
	}
	
	public boolean $(IMessage message) {
		return ObjectUtils.equals(key(), message.getHeader().getStatusCode());
	}
	
	public boolean isFailed(){
		return true;
	}

}
