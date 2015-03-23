package com.mercury.chat.client.protocol;

import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.handler.ListenbleHandler;
import com.mercury.chat.common.handler.impl.SimpleLinstenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;

public abstract class SimpleMessageHandler extends SimpleLinstenbleHandler implements ListenbleHandler{
	
	static final Logger logger = LogManager.getLogger(SimpleMessageHandler.class);
	
	@Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) {
    	if(_().$(msg)){
    		logger.log(Level.INFO, _()+" - Received Message:"+msg);
    		for (MessageListener listener : listeners()) {
    			listener.onMessage(msg);
    		}
    		onMessage(ctx, msg);
    	}
		ctx.fireChannelRead(msg);
    }
	
    protected void onMessage(ChannelHandlerContext ctx, Message msg){
    }

}
