package com.mercury.chat.server.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.struct.protocol.Message;

public abstract class SimpleMessageHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(SimpleMessageHandler.class);
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		validate(ctx, msg);
		logger.log(Level.INFO, "Received message : ---> "+ msg);
		onMessage(ctx, msg);
	}
	
	protected void validate(ChannelHandlerContext ctx, Message msg) throws Exception {
		
	}

	protected abstract void onMessage(ChannelHandlerContext ctx, Message msg) throws Exception;
	
}
