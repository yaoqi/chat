package com.mercury.chat.common;

import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.struct.protocol.Message;


public interface ConnectionListener{
    
	 void onConnection(Message message);
	 
	 void onClose(Message message);
	 
	 void onError(ChatException chatException);
	
}