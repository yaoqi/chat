package com.mercury.chat.common.util;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class Channels {
	
	public static <T> T getAttribute(Channel channel, AttributeKey<T> attributeKey){
		return channel.attr(attributeKey).get();
	}
	
	public static <T> void setAttribute(Channel channel, AttributeKey<T> attributeKey, T value){
		channel.attr(attributeKey).setIfAbsent(value);
	}
	
	public static <T> boolean hasAttribute(Channel channel, AttributeKey<T> attributeKey){
		return channel.hasAttr(attributeKey);
	}
	
}
