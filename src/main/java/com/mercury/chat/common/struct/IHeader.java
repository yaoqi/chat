package com.mercury.chat.common.struct;

public interface IHeader {
	byte getMessageType();
	
	int getStatusCode();
	
	String getTo();

	String getFrom();
}
