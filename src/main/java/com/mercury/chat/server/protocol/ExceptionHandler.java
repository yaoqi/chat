package com.mercury.chat.server.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.struct.protocol.Message;

public class ExceptionHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(ExceptionHandler.class);
	
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	logger.log(Level.ERROR, cause);
    	if(cause instanceof IOException){
    		ctx.close();
    	}
		//ctx.fireExceptionCaught(cause);
    }

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		ctx.fireChannelRead(msg);
	}
	
}
