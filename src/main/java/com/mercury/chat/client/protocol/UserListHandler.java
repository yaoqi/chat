package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.USER_LIST;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.protocol.Message;

public class UserListHandler extends SimpleMessageHandler {

	public UserListHandler(MessageType messageType) {
		super(messageType);
	}

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
    	Message message = (Message) msg;
		
		if (USER_LIST.$(message)) {
			logger.log(Level.INFO, "Receive server user list response : ---> "+ message);
		} else
		    ctx.fireChannelRead(msg);
    }

	@Override
	protected void onMessage(ChannelHandlerContext ctx, Message msg) {
		// TODO Auto-generated method stub
		
	}
	
}
