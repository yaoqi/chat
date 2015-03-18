package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HEARTBEAT;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class HeartBeatHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(HeartBeatHandler.class);
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		if (HEARTBEAT.$(msg)) {
			logger.log(Level.INFO, "Receive client heart beat message : ---> "+ msg);
		    Message heatBeat = new Message().header(new Header().type(HEARTBEAT.value()));
			ctx.writeAndFlush(heatBeat);
		} else
		    ctx.fireChannelRead(msg);
		
	}
	
}
