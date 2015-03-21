package com.mercury.chat.common;

import com.mercury.chat.common.struct.IMessage;

public enum MessageType {

	HANDSHAKE((byte) 0, null){
		@Override
		public boolean listenble(){
	    	return false;
	    }
	},
	LOGIN((byte) 1, "LoginAuthHandler"),
	LOGOFF((byte) 2, null){
		@Override
		public boolean listenble(){
	    	return false;
	    }
	},
	CHAT((byte) 3, "ChatHandler"),
    USER_LIST((byte) 4, "UserListHandler"),
    HEARTBEAT((byte) 5, "HeartBeatHandler"),
	HISTORICAL_MESSAGE((byte) 6, "HeartBeatHandler");
    
    private byte value;
    
    private String handlerName;

    private MessageType(byte value, String handlerName) {
    	this.value = value;
    	this.handlerName = handlerName;
    }

    public boolean listenble(){
    	return true;
    }
    
    public byte value() {
    	return this.value;
    }
    
    public String handler(){
    	return this.handlerName;
    }
    
    public boolean $(IMessage message){
    	return value == message.getHeader().getMessageType();
    }
    
}
