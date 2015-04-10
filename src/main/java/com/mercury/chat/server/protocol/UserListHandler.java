package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.USER_LIST;
import static com.mercury.chat.common.util.Messages.buildMessage;
import static com.mercury.chat.server.protocol.group.SessionManager.channels;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.User;

public class UserListHandler  extends ValidatableMessageHandler {

	static final Logger logger = LogManager.getLogger(UserListHandler.class);
	
	@Override
	protected MessageType _() {
		return USER_LIST;
	}
	
	@Override
	 public void onMessage(ChannelHandlerContext ctx, Message msg) throws Exception {
		logger.log(Level.INFO, "Receive client user list request : ---> "+ msg);
		
		long shopId = (long) msg.getBody();
		List<User> onlineUsers = channels.getOnlineUser(shopId);
		ctx.writeAndFlush(buildMessage(_(), msg.getRequestId(), ImmutableList.copyOf(onlineUsers)));
    }
	
}
