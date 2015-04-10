package com.mercury.chat.common.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.mercury.chat.client.protocol.HistoricalMessageHandler;
import com.mercury.chat.common.MessageBox;
import com.mercury.chat.common.MessageFuture;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.exception.ErrorCode;
import com.mercury.chat.common.handler.ListenbleHandler;
import com.mercury.chat.common.handler.impl.SimpleLinstenbleHandler;
import com.mercury.chat.common.handler.impl.SimpleMessageHandler;
import com.mercury.chat.common.struct.protocol.Message;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.util.AttributeKey;

@SuppressWarnings("unused")
public class Channels {
	
	public static <T> T get(Channel channel, AttributeKey<T> attributeKey){
		if(has(channel, attributeKey)){
			return channel.attr(attributeKey).get();
		}
		return null;
	}
	
	public static <T> void remove(Channel channel, AttributeKey<T> attributeKey){
		channel.attr(attributeKey).remove();
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
	
	public static <T extends SimpleLinstenbleHandler> Message syncSendMessage(Class<T> handler, Channel channel, Message input) throws InterruptedException{
		MessageBox messageBox = channel.pipeline().get(handler).messageBox();
		messageBox.register(input.getRequestId());
		MessageFuture messageFuture = messageBox.get(input.getRequestId());
		channel.writeAndFlush(input).sync();
		Message message = messageFuture.get(10000,TimeUnit.MILLISECONDS);
		if(message == null){
			throw new ChatException(ErrorCode.TIME_OUT);
		}
		return message;
	}
	
}
