package com.mercury.chat.server.protocol.group;

import io.netty.channel.group.ChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SessionManager {
	
	public static final ChannelGroup channels = new ChatChannelGroup(GlobalEventExecutor.INSTANCE);
	
}
