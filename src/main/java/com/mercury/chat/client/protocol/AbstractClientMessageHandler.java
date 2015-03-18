package com.mercury.chat.client.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.mercury.chat.common.struct.protocol.Message;

public class AbstractClientMessageHandler extends SimpleChannelInboundHandler<Message> {

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
