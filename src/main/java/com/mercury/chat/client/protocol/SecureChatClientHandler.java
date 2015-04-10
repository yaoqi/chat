package com.mercury.chat.client.protocol;

import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.Level;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.impl.SimpleMessageHandler;

import static com.mercury.chat.common.MessageType.CHAT;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleMessageHandler {

	@Override
	protected MessageType _() {
		return CHAT;
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.log(Level.ERROR, cause);
        ctx.close();
    }

}
