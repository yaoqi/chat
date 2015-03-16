package com.mercury.chat.common;

import com.mercury.chat.common.struct.protocol.Message;

public interface MessageListener {
	
    void onMessage(Message message);
    
}