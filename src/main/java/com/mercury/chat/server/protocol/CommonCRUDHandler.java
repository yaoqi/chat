package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.mercury.chat.common.MessageType.CRUD;

import com.mercury.chat.common.struct.protocol.Message;

public class CommonCRUDHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(CommonCRUDHandler.class);
	
	@Override
	 public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		
		//FIXME check the authroised user.
		if (CRUD.$(msg)) {
			
			logger.log(Level.INFO, "Receive client CRUD request : ---> "+ msg);
			
		    //FIXME the find logic need to be implemented.
			ctx.writeAndFlush(buildMessage(CRUD));
		} else
		    ctx.fireChannelRead(msg);
    }
	
}
