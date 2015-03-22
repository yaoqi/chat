package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.CRUD;
import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.protocol.Message;

public class CommonCRUDHandler extends SimpleMessageHandler {

	static final Logger logger = LogManager.getLogger(CommonCRUDHandler.class);
	
	@Override
	protected MessageType _() {
		return CRUD;
	}
	
	@Override
	 public void messageReceived(ChannelHandlerContext ctx, Message msg) {
		if (_().$(msg)) {
			logger.log(Level.INFO, "Receive server CRUD response : ---> "+ msg);
			messageBox().put(msg);
		} else
		    ctx.fireChannelRead(msg);
    }
	
	
}
