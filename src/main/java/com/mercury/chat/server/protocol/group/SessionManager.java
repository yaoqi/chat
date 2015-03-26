package com.mercury.chat.server.protocol.group;

import io.netty.channel.group.ChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.service.UserRepository;

public class SessionManager {
	
	public static final ChannelGroup channels = new ChatChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public LoadingCache<String,User> uerCache = CacheBuilder
			.newBuilder()
			.concurrencyLevel(4)
			.initialCapacity(8)
			.maximumSize(10000)
			.recordStats()
			.build(new CacheLoader<String,User>(){
				@Override
				public User load(String userId) throws Exception {
					UserRepository userService = null;
					return userService.getUser(userId);
				}
			});
	
}
