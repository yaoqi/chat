package com.mercury.chat.client.json;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.mercury.chat.common.struct.json.JsonMessage;

public class MessageBox {

	private BlockingQueue<JsonMessage> box = new LinkedBlockingQueue<JsonMessage>(1);
	
	public void put(JsonMessage msg){
		box.offer(msg);
	}
	
	public JsonMessage get(){
		JsonMessage msg = null;
		try {
			msg = box.poll(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			//ignore
		}
		return msg;
	}
	
}
