package com.mercury.chat.client.impl;

import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.StatusCode.OK;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.Connection;
import com.mercury.chat.client.Session;
import com.mercury.chat.client.protocol.LoginAuthHandler;
import com.mercury.chat.common.MessageBox;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.exception.ErrorCode;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.User;

public class ConnectionImpl implements Connection{
	
	static final Logger logger = LogManager.getLogger(ConnectionImpl.class);
	
	private volatile boolean closed;
	
	private volatile boolean connected;
	
	private volatile User currentUser;
	
	private Channel channel;
	
	public ConnectionImpl() {
		super();
		connected = true;
	}
	
	public ConnectionImpl channel(Channel channel){
		this.channel = channel;
		this.channel.closeFuture().addListener(new ChannelFutureListener(){
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				closed = true;
				connected = false;
			}
		});
		return this;
	}
	
	@Override
	public Session login(String userId, String password) {
		if(closed){
			throw new ChatException(ErrorCode.CLOSED);
		}
		try {
			User user = new User(userId,password);
			channel.writeAndFlush(buildMessage(LOGIN, user)).sync();//send login request to chat service
			
			MessageBox loginMessageBox = channel.pipeline().get(LoginAuthHandler.class).messageBox();
			Message responseMsg = loginMessageBox.get();//wait until received the login response.
			
			if(!OK.$(responseMsg)){
				throw new ChatException(StatusCode.valOf(responseMsg));
			}
			
			currentUser = user;
			
			logger.info(responseMsg);
		} catch (InterruptedException e) {
			logger.error(e);
			throw new ChatException(e);
		}
		return new SessionImpl(channel).user(currentUser);
	}

	@Override
	public void close() {
		try {
			channel.close().sync();
			closed = true;
			connected = false;
		} catch (InterruptedException e) {
			logger.error(e);
			throw new ChatException(e);
		}
		
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public boolean isConnected() {
		return connected;
	}
	
}
