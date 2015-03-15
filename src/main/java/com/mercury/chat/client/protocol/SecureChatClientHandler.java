package com.mercury.chat.client.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Collection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.mercury.chat.common.struct.protocol.Message;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(SecureChatClientHandler.class);
	
	private volatile Collection<MessageListener> listeners = Lists.newArrayList();
	
	public void addMessageListener(MessageListener listener){
		listeners.add(listener);
	}
	
	@Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) {
    	logger.log(Level.INFO, "Received Message:"+msg);
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
