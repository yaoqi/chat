package com.mercury.chat.common.constant;

import org.apache.commons.lang.ObjectUtils;

import com.mercury.chat.common.struct.protocol.Header;

public enum StatusCode {
	OK(200, "Operate Success"), 
	FAIL(400, "Operate Failure"),
	UNAUTHORIZED(401, "Unauthorized"), 
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	NOT_LOGIN(600, "Invalid operation:not logged in"),
	LOGGED_IN(601, "Invalid operation:already logged in"),
	USER_LOGIN(700, "User Login"),
	USER_LOGOFF(701, "User Logoff");
	
	private int key;
	
	private String message;

	private StatusCode(int key, String message) {
		this.key = key;
		this.message = message;
	}

	public int getKey() {
		return key;
	}

	public String getMessage() {
		return message;
	}

	public boolean isThisType(Header header) {
		return ObjectUtils.equals(getKey(), header.getStatusCode());
	}

}
