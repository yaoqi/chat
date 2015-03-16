package com.mercury.chat.client.protocol.impl;

import static com.mercury.chat.common.MessageType.LOGIN;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;

import com.mercury.chat.client.protocol.Connection;
import com.mercury.chat.client.protocol.LoginAuthHandler;
import com.mercury.chat.client.protocol.MessageBox;
import com.mercury.chat.client.protocol.Session;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.User;

public class ConnectionImpl implements Connection{
	
	static final Logger logger = LogManager.getLogger(ConnectionImpl.class);
	
	private volatile boolean closed;
	
	private Channel channel;
	
	public ConnectionImpl() {
		super();
	}
	
	public ConnectionImpl channel(Channel channel){
		this.channel = channel;
		return this;
	}
	
	@Override
	public Session login(String userId, String password) {
		try {
			Message message = new Message().header(new Header().type(LOGIN.value())).body(new User(userId,password));
			channel.writeAndFlush(message).sync();//send login request to chat service
			MessageBox loginMessageBox = channel.pipeline().get(LoginAuthHandler.class).getLoginMessageBox();
			Message responseMsg = loginMessageBox.get();//wait until receive the login response.
			logger.info(responseMsg);
		} catch (InterruptedException e) {
			logger.error(e);
			return null;
		}
		return new SessionImpl(channel);
	}

	@Override
	public void close() {
		try {
			channel.close().sync();
			closed = true;
		} catch (InterruptedException e) {
			//ignore
		}
		
	}

	@Override
	public boolean isClosed() {
		return closed;
	}
	
}
