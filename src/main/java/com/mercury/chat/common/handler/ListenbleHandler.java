package com.mercury.chat.common.handler;

import com.mercury.chat.common.MessageListener;

public interface ListenbleHandler {
	
	void addMessageListener(MessageListener listener);
	
	void removeMessageListener(MessageListener listener);
	
}
