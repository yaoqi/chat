package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.USER_LIST;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class UserListHandler extends ChannelHandlerAdapter {

	static final Logger logger = LogManager.getLogger(UserListHandler.class);
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
    	Message message = (Message) msg;
		
		Header header = message.getHeader();
		
		if (USER_LIST.isThisType(header)) {
			logger.log(Level.INFO, "Receive server user list response : ---> "+ message);
		} else
		    ctx.fireChannelRead(msg);
    }
	
}
