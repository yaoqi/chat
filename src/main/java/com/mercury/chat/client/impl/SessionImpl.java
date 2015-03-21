package com.mercury.chat.client.impl;

import static com.mercury.chat.common.MessageType.LOGOFF;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.mercury.chat.client.Session;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.ListenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.User;

public class SessionImpl implements Session {

	private Channel channel;
	
	private volatile User currentUser;
	
	public SessionImpl(Channel channel) {
		super();
		this.channel = channel;
	}
	
	public SessionImpl user(User user){
		currentUser = user;
		return this;
	}

	@Override
	public boolean sendMessage(String to, Message message) {
		try {
			message.getHeader().from(currentUser.getUserId()).to(to);
			channel.writeAndFlush(message).sync();
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean logoff() {
		try {
			channel.writeAndFlush(buildMessage(LOGOFF)).sync();
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
		if(messageType.listenble()){
			ChannelHandler channelHandler = channel.pipeline().get(messageType.handler());
			if(channelHandler instanceof ListenbleHandler){
				ListenbleHandler listenbleHandler = (ListenbleHandler) channelHandler;
				listenbleHandler.addMessageListener(messageListener);
			}
		}
	}
	
	public void removeMessageListener(MessageType messageType, MessageListener messageListener){
		if(messageType.listenble()){
			ChannelHandler channelHandler = channel.pipeline().get(messageType.handler());
			if(channelHandler instanceof ListenbleHandler){
				ListenbleHandler listenbleHandler = (ListenbleHandler) channelHandler;
				listenbleHandler.removeMessageListener(messageListener);
			}
		}
	}

}
