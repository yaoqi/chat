package com.mercury.chat.client.impl;

import static com.mercury.chat.common.MessageType.LOGOFF;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.mercury.chat.client.Session;
import com.mercury.chat.client.protocol.HeartBeatHandler;
import com.mercury.chat.client.protocol.LoginAuthHandler;
import com.mercury.chat.client.protocol.SecureChatClientHandler;
import com.mercury.chat.client.protocol.UserListHandler;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
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
		if(LOGOFF != messageType){
			
		}
		switch(messageType){
			case LOGIN:
				channel.pipeline().get(LoginAuthHandler.class).addMessageListener(messageListener);
				break;
			case LOGOFF:
				break;
			case CHAT:
				channel.pipeline().get(SecureChatClientHandler.class).addMessageListener(messageListener);
				break;
			case USER_LIST:
				channel.pipeline().get(UserListHandler.class).addMessageListener(messageListener);
				break;
			case HEARTBEAT:
				channel.pipeline().get(HeartBeatHandler.class).addMessageListener(messageListener);
				break;
			default:
				break;
		}
		
	}

}
