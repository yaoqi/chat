package com.mercury.chat.server.protocol.group;

import static com.mercury.chat.common.constant.Constant.userInfo;
import static com.mercury.chat.common.util.Channels.get;
import static com.mercury.chat.common.util.Channels.has;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;

import java.util.List;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.mercury.chat.user.entity.User;

public class ChatChannelGroup extends DefaultChannelGroup {

	private RemovalListener<String,Channel> removalListener = new RemovalListener<String,Channel>(){

		@Override
		public void onRemoval(RemovalNotification<String, Channel> notification) {
			//TODO
		}
		
	};
	
	private RemovalListener<String,User> removalUserListener = new RemovalListener<String,User>(){

		@Override
		public void onRemoval(RemovalNotification<String, User> notification) {
			//TODO
		}
		
	};
	
	public List<User> getOnlineUser(long shopId){
		return shopUserCache.getIfPresent(shopId);
	}
	
	private Cache<String,Channel> cache = CacheBuilder
												.newBuilder()
												.concurrencyLevel(4)
												.initialCapacity(8)
												.maximumSize(10000)
												.removalListener(removalListener)
												.recordStats()
												.build();
	
	private Cache<String,User> userCache = CacheBuilder
												.newBuilder()
												.concurrencyLevel(4)
												.initialCapacity(8)
												.maximumSize(10000)
												.removalListener(removalUserListener)
												.recordStats()
												.build();
	
	private Cache<Long,List<User>> shopUserCache = CacheBuilder
													.newBuilder()
													.concurrencyLevel(4)
													.initialCapacity(8)
													.maximumSize(10000)
													.recordStats()
													.build();
	public boolean hasUser(String userId){
		return userCache.getIfPresent(userId) != null;
	}
	
	public Cache<String,Channel> getCache(){
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
		if(has(channel,userInfo)){
			User user = get(channel,userInfo);
			cache.put(user.getUserId(), channel);
			userCache.put(user.getUserId(), user);
			if(user.isSales()){
				Long shopId = user.getShopId();
				//FIXME Thread safe?
				List<User> userList = shopUserCache.getIfPresent(shopId);
				if(userList!=null){
					userList.add(user);
				}else{
					userList = Lists.newArrayList(user);
					shopUserCache.put(shopId, userList);
				}
			}
		}
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
		if(has(channel,userInfo)){
			User user = get(channel,userInfo);
			//clear the user info from the channel.
			channel.attr(userInfo).remove();
			cache.invalidate(user.getUserId());
			userCache.invalidate(user.getUserId());
			if(user.isSales()){
				Long shopId = user.getShopId();
				//FIXME Thread safe?
				List<User> userList = shopUserCache.getIfPresent(shopId);
				if(userList!=null){
					userList.remove(user);
				}
			}
		}
		boolean removed = super.remove(o);
		return removed;
	}

	@Override
	public ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher) {
		return super.writeAndFlush(message, matcher);
	}

}
