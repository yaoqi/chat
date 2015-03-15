package com.mercury.chat.common.exception;

public class ChatException extends RuntimeException {

	private static final long serialVersionUID = -3020226599902573310L;
	
	private ErrorCode errorCode;
	
	public ChatException(ErrorCode messageCode) {
		super();
		this.errorCode = messageCode;
	}
	
	public ChatException() {
		super();
	}

	public ChatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ChatException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChatException(String message) {
		super(message);
	}

	public ChatException(Throwable cause) {
		super(cause);
	}
	
	public ErrorCode messageCode(){
		return this.errorCode;
	}

}
