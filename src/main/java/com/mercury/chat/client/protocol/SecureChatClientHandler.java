package com.mercury.chat.client.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.struct.protocol.Message;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(SecureChatClientHandler.class);
	
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) {
    	logger.log(Level.INFO, "Received Message:"+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.log(Level.ERROR, cause);
        ctx.close();
    }
}
