package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.MessageType.USER_LIST;
import static com.mercury.chat.common.constant.StatusCode.NOT_LOGIN;
import static com.mercury.chat.common.util.Channels.has;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.struct.protocol.Message;

public class UserListHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(UserListHandler.class);
	
	@Override
	 public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		
		if (USER_LIST.$(msg)) {
			if(!has(ctx.channel(), Constant.userInfo)){
	    		ctx.writeAndFlush(buildMessage(LOGIN, NOT_LOGIN));
	    		return;
	    	}
			logger.log(Level.INFO, "Receive client user list request : ---> "+ msg);
			
		    //FIXME the find logic need to be implemented.
			ctx.writeAndFlush(buildMessage(USER_LIST));
		} else
		    ctx.fireChannelRead(msg);
    }
	
}
