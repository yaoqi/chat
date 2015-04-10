package com.mercury.chat.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.mercury.chat.common.struct.protocol.Message;

public class MessageFuture {
	
	private Message content;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	public void putMessage(Message content){
		this.content = content;
		latch.countDown();
	}
	
	public Message get() throws InterruptedException{
		latch.await();
		return content;
	}
	
	public Message get(long timeout, TimeUnit unit) throws InterruptedException{
		latch.await(timeout, unit);
		return content;
	}
	
}
