package com.mercury.chat.client.protocol.impl;

import java.util.Date;
import java.util.List;

import io.netty.channel.Channel;

import com.google.common.collect.Lists;
import com.mercury.chat.client.protocol.MessageListener;
import com.mercury.chat.client.protocol.Session;
import com.mercury.chat.common.struct.protocol.Message;

public class SessionImpl implements Session {

	private Channel channel;
	
	public SessionImpl(Channel channel) {
		super();
		this.channel = channel;
	}

	@Override
	public boolean sendMessage(Message message) {
		try {
			channel.writeAndFlush(message).sync();
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean logoff() {
		//TODO
		return true;
	}

	@Override
	public List<Message> getHistoricalMessages(String user, Date from, Date to) {
		// TODO
		return Lists.newArrayList();
	}
	
	public void addMessageListener(MessageListener messageListener){
		//TODO
		return;
	}

}
