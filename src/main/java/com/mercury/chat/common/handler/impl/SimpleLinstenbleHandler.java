package com.mercury.chat.common.handler.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageBox;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.ListenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;

public abstract class SimpleLinstenbleHandler extends SimpleChannelInboundHandler<Message>  implements ListenbleHandler {

	private volatile Collection<MessageListener> listeners = Lists.newArrayList();
	
	private volatile MessageBox messageBox = new MessageBox();
	
	protected abstract MessageType _();
	
	@Override
	public void addMessageListener(MessageListener listener){
		listeners.add(listener);
	}
	
	@Override
	public void removeMessageListener(MessageListener listener){
		listeners.remove(listener);
	}
	
	protected Collection<MessageListener> listeners(){
		return listeners;
	}
	
	public MessageBox messageBox(){
		return messageBox;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		
	}

}
