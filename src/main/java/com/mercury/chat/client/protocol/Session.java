package com.mercury.chat.client.protocol;

import java.util.Date;
import java.util.List;

import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.protocol.Message;

public interface Session {
	
	boolean sendMessage(Message message);
	
	boolean logoff();
	
	List<Message> getHistoricalMessages(String user, Date from, Date to);
	
	void addMessageListener(MessageType messageType, MessageListener messageListener);
}
