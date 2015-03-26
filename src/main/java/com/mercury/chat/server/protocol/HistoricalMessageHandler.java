package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;
import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.StatusCode.NOT_LOGIN;
import static com.mercury.chat.common.util.Channels.has;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.impl.HisMsgRequest;
import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.repository.UserRepository;

public class HistoricalMessageHandler extends SimpleChannelInboundHandler<Message> {

	 static final Logger logger = LogManager.getLogger(HistoricalMessageHandler.class);
	
	 private final UserRepository userService;
	 
	 public HistoricalMessageHandler(UserRepository userService){
		 this.userService = userService;
	 }
	
	@Override
	 public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		
		if (HISTORICAL_MESSAGE.$(msg)) {
			if(!has(ctx.channel(), Constant.userInfo)){
	    		ctx.writeAndFlush(buildMessage(LOGIN, NOT_LOGIN));
	    		return;
	    	}
			logger.log(Level.INFO, "Receive client Historical Message request : ---> "+ msg);

			HisMsgRequest request = (HisMsgRequest) msg.getBody();
			IMessage chatMessage = userService.select(request.getUserId(), request.getShopId(), request.getOffset(), request.getBatchSize());
			ctx.writeAndFlush(chatMessage);
		} else
		    ctx.fireChannelRead(msg);
    }
}
