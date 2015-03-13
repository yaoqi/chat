package com.mercury.chat.common.struct;

public interface IMessage {

	IHeader getHeader();
	
	Object getBody();
	
}
