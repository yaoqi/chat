package com.mercury.chat.client.protocol;

import java.util.Collection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.protocol.Message;

public abstract class SimpleMessageHandler extends SimpleChannelInboundHandler<Message> {
	
	static final Logger logger = LogManager.getLogger(SimpleMessageHandler.class);
	
	private MessageType messageType;
	
	private volatile Collection<MessageListener> listeners = Lists.newArrayList();
	
	public SimpleMessageHandler(MessageType messageType) {
		super();
		this.messageType = messageType;
	}
	
	public void addMessageListener(MessageListener listener){
		listeners.add(listener);
	}

	@Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) {
    	logger.log(Level.INFO, "Received Message:"+msg);
		for (MessageListener listener : listeners) {
			listener.onMessage(msg);
		}
		if(messageType.$(msg)){
			onMessage(ctx, msg);
		}
		ctx.fireChannelRead(msg);
    }
	
    protected abstract void onMessage(ChannelHandlerContext ctx, Message msg);

}
