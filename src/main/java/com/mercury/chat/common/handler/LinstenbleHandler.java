package com.mercury.chat.common.handler;

import com.mercury.chat.common.MessageListener;

public interface LinstenbleHandler {
	
	void addMessageListener(MessageListener listener);
	
	void removeMessageListener(MessageListener listener);
	
}
