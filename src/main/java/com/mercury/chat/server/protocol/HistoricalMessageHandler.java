package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.impl.HistoricalMsgRequest;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.service.MockUserService;
import com.mercury.chat.user.service.UserService;

public class HistoricalMessageHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(UserListHandler.class);
	
	@Override
	 public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		
		if (HISTORICAL_MESSAGE.$(msg)) {
			logger.log(Level.INFO, "Receive client Historical Message request : ---> "+ msg);

			HistoricalMsgRequest request = (HistoricalMsgRequest) msg.getBody();
			UserService userService = MockUserService.getInstance();
			IMessage chatMessage = userService.select(request.getUserId(), request.getShopId(), request.getOffset(), request.getBatchSize());
			
			ctx.writeAndFlush(buildMessage(HISTORICAL_MESSAGE, chatMessage));
		} else
		    ctx.fireChannelRead(msg);
    }
}
