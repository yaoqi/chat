package com.mercury.chat.common.util;

import io.netty.channel.Channel;
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
	
}
