package com.mercury.chat.client.impl;

import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.StatusCode.OK;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.Connection;
import com.mercury.chat.client.Session;
import com.mercury.chat.client.protocol.LoginAuthHandler;
import com.mercury.chat.common.MessageBox;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.User;

public class ConnectionImpl implements Connection{
	
	static final Logger logger = LogManager.getLogger(ConnectionImpl.class);
	
	private volatile boolean closed;
	
	private volatile User currentUser;
	
	private Channel channel;
	
	public ConnectionImpl() {
		super();
	}
	
	public ConnectionImpl channel(Channel channel){
		this.channel = channel;
		this.channel.closeFuture().addListener(new ChannelFutureListener(){
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				closed = true;
			}
		});
		return this;
	}
	
	@Override
	public Session login(String userId, String password) {
		if(closed){
			//TODO
			throw new ChatException();
		}
		try {
			User user = new User(userId,password);
			Message message = new Message().header(new Header().type(LOGIN.value())).body(user);
			channel.writeAndFlush(message).sync();//send login request to chat service
			
			MessageBox loginMessageBox = channel.pipeline().get(LoginAuthHandler.class).getLoginMessageBox();
			Message responseMsg = loginMessageBox.get();//wait until receive the login response.
			
			if(!OK.isThisType(responseMsg.getHeader())){
				throw new ChatException();
			}
			
			currentUser = user;
			
			logger.info(responseMsg);
		} catch (InterruptedException e) {
			logger.error(e);
			throw new ChatException();
		}
		return new SessionImpl(channel).user(currentUser);
	}

	@Override
	public void close() {
		try {
			channel.close().sync();
			closed = true;
		} catch (InterruptedException e) {
			logger.error(e);
			throw new ChatException();
		}
		
	}

	@Override
	public boolean isClosed() {
		return closed;
	}
	
}
