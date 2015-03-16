package com.mercury.chat.client.protocol.impl;

import io.netty.channel.Channel;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.mercury.chat.client.protocol.Session;
import com.mercury.chat.common.MessageType;
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
	
	public void addMessageListener(MessageType messageType, stener messageListener){
		channel.pipeline().get(SecureChatClientHandler.class).addMessageListener(messageListener);
	}

}
