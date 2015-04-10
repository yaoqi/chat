package com.mercury.chat.common.struct;

import com.mercury.chat.user.entity.ChatMessage;

public interface IMessage {

	IHeader getHeader();
	
	Object getBody();
	
	String getFrom();
	
	String getTo();
	
	ChatMessage convert();

	void from(String user);

	void to(String user);

	String getFromUser();

	String getToUser();

	void fromUser(String user);

	void toUser(String user);
	
	long getRequestId();
	
	IMessage requestId(long requestId);
	
}
