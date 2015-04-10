package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HEARTBEAT;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.impl.SimpleMessageHandler;
import com.mercury.chat.common.struct.protocol.Message;

public class HeartBeatHandler  extends SimpleMessageHandler {

	static final Logger logger = LogManager.getLogger(HeartBeatHandler.class);
	
	@Override
	protected MessageType _() {
		return HEARTBEAT;
	}
	
	@Override
	protected void onMessage(ChannelHandlerContext ctx, Message msg) throws Exception {
		logger.log(Level.INFO, "Receive client heart beat message : ---> "+ msg);
		ctx.writeAndFlush(buildMessage(_()));
	}
	
}
