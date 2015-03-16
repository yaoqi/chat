package com.mercury.chat.client.impl;

import io.netty.channel.Channel;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.mercury.chat.client.Session;
import com.mercury.chat.client.protocol.SecureChatClientHandler;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.protocol.Header;
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
		try {
			Message message = new Message().header(new Header().type(MessageType.LOGOFF.value()));
			channel.writeAndFlush(message).sync();
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Message> getHistoricalMessages(String user, Date from, Date to) {
		// TODO
		return Lists.newArrayList();
	}
	
	public void addMessageListener(MessageType messageType, MessageListener messageListener){
		channel.pipeline().get(SecureChatClientHandler.class).addMessageListener(messageListener);
	}

}
