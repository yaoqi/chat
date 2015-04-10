package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;
import io.netty.channel.ChannelHandlerContext;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.impl.SimpleMessageHandler;
import com.mercury.chat.common.struct.protocol.Message;

public class HistoricalMessageHandler extends SimpleMessageHandler {

	@Override
	protected MessageType _() {
		return HISTORICAL_MESSAGE;
	}

	@Override
	protected void onMessage(ChannelHandlerContext ctx, Message msg) {
		messageBox().put(msg);
		messageBox().unRegister(msg.getRequestId());
	}
	
	
	
}
