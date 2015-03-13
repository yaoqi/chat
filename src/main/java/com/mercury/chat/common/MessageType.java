package com.mercury.chat.common;

import com.mercury.chat.common.struct.IHeader;

public enum MessageType {

	HANDSHAKE((byte) 0),
	LOGIN((byte) 1),
	LOGOFF((byte) 2),
	CHAT((byte) 3),
    USER_LIST((byte) 4),
    HEARTBEAT((byte) 5);
    
    private byte value;

    private MessageType(byte value) {
    	this.value = value;
    }

    public byte value() {
    	return this.value;
    }
    
    public boolean isThisType(IHeader header){
    	return value == header.getMessageType();
    }
    
}
