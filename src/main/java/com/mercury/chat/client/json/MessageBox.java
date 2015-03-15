package com.mercury.chat.client.json;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.mercury.chat.common.struct.protocol.Message;

public class MessageBox {

	private BlockingQueue<Message> box = new LinkedBlockingQueue<Message>(1);
	
	public void put(Message msg){
		box.offer(msg);
	}
	
	public Message get(){
		Message msg = null;
		try {
			msg = box.poll(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			//ignore
		}
		return msg;
	}
	
}
