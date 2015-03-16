package com.mercury.chat.client;


public interface Connection {
	
	Session login(String userId, String password);
	
	void close();
	
	boolean isClosed();
}
