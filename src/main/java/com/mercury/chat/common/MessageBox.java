package com.mercury.chat.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mercury.chat.common.struct.protocol.Message;

public class MessageBox {

	private ConcurrentMap<Long, MessageFuture> msgs = new ConcurrentHashMap<Long, MessageFuture>();
	
	public void register(long requestId){
		msgs.put(requestId, new MessageFuture());
	}
	
	public void unRegister(long requestId){
		msgs.remove(requestId);
	}
	
	public void put(Message msg){
		msgs.get(msg.getRequestId()).putMessage(msg);
	}
	
	public MessageFuture get(long requestId){
		return msgs.get(requestId);
	}
	
}
