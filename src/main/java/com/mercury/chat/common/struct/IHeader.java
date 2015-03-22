package com.mercury.chat.common.struct;

public interface IHeader {
	
	byte messageType();
	
	int statusCode();
	
	String getTo();

	String getFrom();
}
