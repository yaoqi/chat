package com.mercury.chat.client.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;

import java.util.Collection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.struct.protocol.Message;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(SecureChatClientHandler.class);
	
    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	Attribute<Collection<MessageListener>> listeners = ctx.channel().attr(Constant.listeners);
    	listeners.setIfAbsent(Lists.<MessageListener>newArrayList());
	}

	@Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) {
    	logger.log(Level.INFO, "Received Message:"+msg);
    	Attribute<Collection<MessageListener>> attribute = ctx.channel().attr(Constant.listeners);
    	Collection<MessageListener> listeners = attribute.get();
		for (MessageListener listener : listeners) {
			listener.onMessage(msg);
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.log(Level.ERROR, cause);
        ctx.close();
    }
}
