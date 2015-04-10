package com.mercury.chat.common.handler.impl;

import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.handler.ListenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;

public abstract class SimpleMessageHandler extends SimpleLinstenbleHandler implements ListenbleHandler{
	
	protected static final Logger logger = LogManager.getLogger(SimpleMessageHandler.class);
	
	@Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
    	if(_().$(msg)){
    		logger.log(Level.INFO, _()+" - Received Message:"+msg);
    		if(!validate(ctx, msg)){
    			return;
    		}
    		for (MessageListener listener : listeners()) {
    			listener.onMessage(msg);
    		}
    		onMessage(ctx, msg);
    	}else{
    		ctx.fireChannelRead(msg);
    	}
    }
	
    protected void onMessage(ChannelHandlerContext ctx, Message msg)  throws Exception { 
    }
    
    protected boolean validate(ChannelHandlerContext ctx, Message msg) throws Exception {
    	return true;
	}

}
