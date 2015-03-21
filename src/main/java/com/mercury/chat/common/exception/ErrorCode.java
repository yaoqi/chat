package com.mercury.chat.common.exception;

public enum ErrorCode {
	
	CLOSED(1, "Connection Closed");
	
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
