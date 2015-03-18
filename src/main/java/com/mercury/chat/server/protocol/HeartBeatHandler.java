package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HEARTBEAT;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.struct.protocol.Message;

public class HeartBeatHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(HeartBeatHandler.class);
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		if (HEARTBEAT.$(msg)) {
			logger.log(Level.INFO, "Receive client heart beat message : ---> "+ msg);
			ctx.writeAndFlush(buildMessage(HEARTBEAT));
		} else
		    ctx.fireChannelRead(msg);
		
	}
	
}
