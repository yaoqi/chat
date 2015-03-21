package com.mercury.chat.common.struct;

import com.mercury.chat.user.entity.ChatMessage;

public interface IMessage {

	IHeader getHeader();
	
	Object getBody();
	
	String getFrom();
	
	String getTo();
	
	ChatMessage convert();
	
}
