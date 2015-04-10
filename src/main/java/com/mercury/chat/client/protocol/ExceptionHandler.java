package com.mercury.chat.client.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.ReadTimeoutException;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.ConnectionListener;
import com.mercury.chat.common.struct.protocol.Message;

public class ExceptionHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(ExceptionHandler.class);
	
	private volatile ConnectionListener connectionListener;
	
    public ExceptionHandler(ConnectionListener connectionListener) {
		super();
		this.connectionListener = connectionListener;
	}
    
    public ExceptionHandler() {
		super();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	logger.log(Level.ERROR, cause);
    	cause.printStackTrace();
    	
    	if (connectionListener != null) {
			connectionListener.onError(cause);
		}
    	
    	if(cause instanceof IOException || cause instanceof ReadTimeoutException){
    		ctx.close();
    	}
    }

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		ctx.fireChannelRead(msg);
	}

	public void setConnectionListener(ConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}
	
}
