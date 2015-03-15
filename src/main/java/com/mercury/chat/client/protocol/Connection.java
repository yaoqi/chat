package com.mercury.chat.client.protocol;

public interface Connection {
	
	Session login(String userId, String password);
	
	void close();
	
	boolean isClosed();
}
