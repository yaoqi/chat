package com.mercury.chat.client.json;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.mercury.chat.common.struct.json.JsonMessage;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<JsonMessage> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, JsonMessage msg) {
        System.err.println(msg.getBody());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
