package com.mercury.chat.client.protocol;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.mercury.chat.common.struct.protocol.Message;

public class MessageBox {

	private volatile boolean discard = false;
	
	private BlockingQueue<Message> box = new LinkedBlockingQueue<Message>(1);
	
	public void put(Message msg){
		if(!discard){
			box.offer(msg);
		}
	}
	
	public Message get(){
		Message msg = null;
		try {
			msg = box.poll(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			//ignore
		}
		
		if(msg == null){
			setDiscard(true);
		}
		
		return msg;
	}

	public boolean isDiscard() {
		return discard;
	}

	public void setDiscard(boolean discard) {
		this.discard = discard;
	}
	
}
