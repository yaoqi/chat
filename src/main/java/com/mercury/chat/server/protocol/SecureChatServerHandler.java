package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.CHAT;
import static com.mercury.chat.common.TaskExecutor.taskExecutor;
import static com.mercury.chat.common.constant.StatusCode.NOT_LOGIN;
import static com.mercury.chat.common.util.Channels.get;
import static com.mercury.chat.common.util.Channels.has;
import static com.mercury.chat.common.util.Messages.buildMessage;
import static com.mercury.chat.server.protocol.group.SessionManager.channels;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelMatcher;
import static com.mercury.chat.common.constant.Constant.SHOP_ID;

import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageStoreCallable;
import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.matcher.UserMatcher;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.server.protocol.group.SessionManager;
import com.mercury.chat.user.entity.User;

/**
 * Handles a server-side channel.
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(SecureChatServerHandler.class);
	
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
    	//validate
    	if(!has(ctx.channel(), Constant.userInfo)){
    		ctx.writeAndFlush(buildMessage(CHAT, NOT_LOGIN, msg.getRequestId()));
    		return;
    	}
    	
    	//send message to target user
    	Header header = msg.getHeader();
		ChannelMatcher matcher = new UserMatcher(header.getTo());
		
		User currentUser = get(ctx.channel(), Constant.userInfo);
		long shopId;
		
		//如果是客服
		if(currentUser.isSales()){
			shopId = currentUser.getShopId();
			//获取目标客户
			//String toUser = msg.getToUser();
		}else{
		//如果代理客户端	
			shopId = SessionManager.uerCache.get(header.getTo()).getShopId();
			//获取来源客户
			//String fromUser = msg.getFromUser();
		}
		msg.getHeader().attach(SHOP_ID, shopId);
		
		channels.writeAndFlush(msg, matcher);
		
		//submit store message to thread pool.
		Future<Integer> future = taskExecutor.submit(new MessageStoreCallable(Lists.<IMessage>newArrayList(msg)));
		boolean wait = Boolean.parseBoolean(System.getProperty("chat.server.message.saved.wait", "false"));
		if(wait){
			future.get();
		}
    }
    
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.log(Level.ERROR, cause);
        cause.printStackTrace();
        if(cause instanceof IOException){
        	ctx.close();
        }
    }
}
