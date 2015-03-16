package com.mercury.chat.client.protocol;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mercury.chat.common.struct.protocol.Message;

public class MessageBox {

	private BlockingQueue<Message> box = new LinkedBlockingQueue<Message>(1);
	
	public void put(Message msg){
		box.offer(msg);
	}
	
	public Message get(){
		Message msg = null;
		try {
			msg = box.take();
		} catch (InterruptedException e) {
			//ignore
		}
		return msg;
	}
	
}
