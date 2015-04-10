package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.HisMsgRequest;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.repository.UserRepository;

public class HistoricalMessageHandler  extends ValidatableMessageHandler {

	 static final Logger logger = LogManager.getLogger(HistoricalMessageHandler.class);
	
	 private final UserRepository userService;
	 
	 public HistoricalMessageHandler(UserRepository userService){
		 this.userService = userService;
	 }
	 
	 @Override
	 protected MessageType _() {
		return HISTORICAL_MESSAGE;
	 }
	
	@Override
	 protected void onMessage(ChannelHandlerContext ctx, Message msg) throws Exception {
		logger.log(Level.INFO, "Receive client Historical Message request : ---> "+ msg);

		HisMsgRequest request = (HisMsgRequest) msg.getBody();
		List<ChatMessage> messages = userService.select(request.getUserId(), request.getShopId(), request.getOffset(), request.getBatchSize());
		Message chatMessage = buildMessage(_(), msg.getRequestId(), messages);
		chatMessage.getHeader().requestId(msg.getRequestId());
		ctx.writeAndFlush(chatMessage);
    }
	
}
