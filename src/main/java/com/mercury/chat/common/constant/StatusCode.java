package com.mercury.chat.common.constant;

public enum StatusCode {
	SUCCESS(0, "Operate Success"), 
	FAIL(1, "Operate Failure"), 
	NOT_LOGIN(2, "Invalid operation:not logged in!"),
	LOGGED_IN(3, "Invalid operation:already logged in!");
	
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

}
