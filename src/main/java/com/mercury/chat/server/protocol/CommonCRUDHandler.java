package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.CRUD;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.impl.QuickReplyRequest;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.service.UserService;

public class CommonCRUDHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(CommonCRUDHandler.class);
	
	private final UserService userService;
	
	public CommonCRUDHandler(UserService userService){
		this.userService = userService;
	}
	
	@Override
	 public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		
		//FIXME check the authroised user.
		if (CRUD.$(msg)) {
			
			logger.log(Level.INFO, "Receive client CRUD request : ---> "+ msg);

			Object request = msg.getBody();
			if(request instanceof QuickReplyRequest){
				
			}else{
				//add other handler logic.
			}
			
		    //FIXME the find logic need to be implemented.
			ctx.writeAndFlush(buildMessage(CRUD));
		} else
		    ctx.fireChannelRead(msg);
    }
	
}