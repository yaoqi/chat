package com.mercury.chat.common.exception;

import com.mercury.chat.common.constant.StatusCode;

public class ChatException extends RuntimeException {

	private static final long serialVersionUID = -3020226599902573310L;
	
	public ChatException(StatusCode statusCode) {
		this(statusCode.message());
	}
	
	public ChatException(ErrorCode errorCode) {
		this(errorCode.message());
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
	

}
