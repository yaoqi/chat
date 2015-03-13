package com.mercury.chat.client.protocol;

import com.mercury.chat.common.struct.protocol.Message;

public interface MessageListener {
	
    void onMessage(Message message);
    
}