package com.mercury.chat.server.protocol.group;

import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;

public class ChatChannelGroup extends DefaultChannelGroup {

	public ChatChannelGroup(EventExecutor executor) {
		super(executor);
	}

	public ChatChannelGroup(String name, EventExecutor executor) {
		super(name, executor);
	}

	@Override
	public boolean add(Channel channel) {
		return super.add(channel);
	}

	@Override
	public boolean remove(Object o) {
		return super.remove(o);
	}
	
	

}
