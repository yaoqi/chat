package com.mercury.chat.common.handler.impl;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.handler.LinstenbleHandler;

public class SimpleLinstenbleHandler implements LinstenbleHandler {

	private volatile Collection<MessageListener> listeners = Lists.newArrayList();
	
	@Override
	public void addMessageListener(MessageListener listener){
		listeners.add(listener);
	}
	
	@Override
	public void removeMessageListener(MessageListener listener){
		listeners.remove(listener);
	}
	
	public Collection<MessageListener> listeners(){
		return listeners;
	}

}
