package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.USER_LIST;
import io.netty.channel.ChannelHandlerContext;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.impl.SimpleMessageHandler;
import com.mercury.chat.common.struct.protocol.Message;

public class UserListHandler extends SimpleMessageHandler {

	@Override
	protected MessageType _() {
		return USER_LIST;
	}

	@Override
	protected void onMessage(ChannelHandlerContext ctx, Message msg) {
		messageBox().put(msg);
	}
	
}
