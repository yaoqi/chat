package com.mercury.chat.server.protocol.group;

import static com.mercury.chat.common.constant.Constant.userInfo;
import static com.mercury.chat.common.util.Channels.get;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class ChatChannelGroup extends DefaultChannelGroup {

	private CacheLoader<String,Channel> cacheLoader = new CacheLoader<String,Channel>(){
				@Override
				public Channel load(String key) throws Exception {
					return null;
				}
	};
	
	private RemovalListener<String,Channel> removalListener = new RemovalListener<String,Channel>(){

		@Override
		public void onRemoval(RemovalNotification<String, Channel> notification) {
			//XXX
		}
		
	};
	
	private LoadingCache<String,Channel> cache = CacheBuilder
												.newBuilder()
												.concurrencyLevel(4)
												.initialCapacity(8)
												.maximumSize(10000)
												.removalListener(removalListener)
												.recordStats()
												.build(cacheLoader);
	
	public LoadingCache<String,Channel> getCache(){
		return cache;
	}
	
	public ChatChannelGroup(EventExecutor executor) {
		super(executor);
	}

	public ChatChannelGroup(String name, EventExecutor executor) {
		super(name, executor);
	}

	@Override
	public boolean add(Channel channel) {
		boolean added = super.add(channel);
		cache.put(get(channel,userInfo).getUserId(), channel);
		return added;
	}

	@Override
	public boolean remove(Object o) {
		Channel channel = null;
		if (o instanceof ChannelId) {
			ChannelId id = (ChannelId) o;
			channel = find(id);
		}else if(o instanceof Channel){
			channel = (Channel) o;
		}
		cache.invalidate(get(channel ,userInfo).getUserId());
		boolean removed = super.remove(o);
		return removed;
	}
	
	

}
