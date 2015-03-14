package com.mercury.chat.server.json;

import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.LoginFlag.NOT_LOGIN;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;

import java.util.concurrent.ExecutorService;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageStoreCallable;
import com.mercury.chat.common.TaskExecutor;
import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.matcher.UserMatcher;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.json.JsonHeader;
import com.mercury.chat.common.struct.json.JsonMessage;

/**
 * Handles a server-side channel.
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<JsonMessage> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, JsonMessage msg) throws Exception {
    	//validate
    	boolean hasAttr = ctx.channel().hasAttr(Constant.userInfo);
    	if(!hasAttr){
    		JsonMessage errorMsg = new JsonMessage().header(new JsonHeader().type(LOGIN.value())).body(NOT_LOGIN.value());
    		ctx.writeAndFlush(errorMsg);
    		return;
    	}
    	
    	//send message to target user
    	@SuppressWarnings("static-access")
		final ChannelGroup channels = ctx.pipeline().get(LoginAuthHandler.class).channels;
    	JsonHeader header = msg.getHeader();
		ChannelMatcher matcher = new UserMatcher(header.getTo());
		channels.writeAndFlush(msg, matcher);
		
		//submit store message to thread pool.
		ExecutorService taskExecutor = TaskExecutor.getInstance().taskExecutor;
		taskExecutor.submit(new MessageStoreCallable(Lists.<IMessage>newArrayList(msg)));
    }
    
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
