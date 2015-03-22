package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.CRUD;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.struct.protocol.Message;

public class CommonCRUDHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(CommonCRUDHandler.class);
	
	@Override
	 public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		
		if (CRUD.$(msg)) {
			logger.log(Level.INFO, "Receive server CRUD response : ---> "+ msg);
			
		} else
		    ctx.fireChannelRead(msg);
    }
	
}
