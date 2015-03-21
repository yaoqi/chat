package com.mercury.chat.common.util;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.ListenbleHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.util.AttributeKey;

public class Channels {
	
	public static <T> T get(Channel channel, AttributeKey<T> attributeKey){
		return channel.attr(attributeKey).get();
	}
	
	public static <T> void set(Channel channel, AttributeKey<T> attributeKey, T value){
		channel.attr(attributeKey).setIfAbsent(value);
	}
	
	public static <T> boolean has(Channel channel, AttributeKey<T> attributeKey){
		return channel.hasAttr(attributeKey);
	}
	
	public static ListenbleHandler getListenbleHandler(Channel channel, MessageType messageType) {
		if(messageType.listenble()){
			ChannelHandler channelHandler = channel.pipeline().get(messageType.handler());
			if(channelHandler instanceof ListenbleHandler){
				return (ListenbleHandler) channelHandler;
			}
		}
		return null;
	}
	
}
