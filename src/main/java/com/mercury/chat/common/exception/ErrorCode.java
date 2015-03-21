package com.mercury.chat.common.exception;

public enum ErrorCode {
	
	CLOSED(1, "Connection Closed"),
	NOT_CONNECTED(2, "Not Connected"),
	UNAUTHORIZED(3, "Unauthorized"),
	NOT_LOGINED(4, "Not Logined"),
	LOGINED(5, "Logined"),
	TIME_OUT(6, "Time Out");
	
	private int key;
	
	private String message;
	
	private ErrorCode(int key, String message) {
		this.key = key;
		this.message = message;
	}
	
	public int key() {
		return key;
	}

	public String message() {
		return message;
	}
	
}
