package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.LoginFlag.NOT_LOGIN;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageStoreCallable;
import com.mercury.chat.common.TaskExecutor;
import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.matcher.UserMatcher;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

/**
 * Handles a server-side channel.
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<Message> {

	static final Logger logger = LogManager.getLogger(SecureChatServerHandler.class);
	
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
    	//validate
    	boolean hasAttr = ctx.channel().hasAttr(Constant.userInfo);
    	if(!hasAttr){
    		Message errorMsg = new Message().header(new Header().type(LOGIN.value())).body(NOT_LOGIN.key());
    		ctx.writeAndFlush(errorMsg);
    		return;
    	}
    	
    	//send message to target user
    	@SuppressWarnings("static-access")
		final ChannelGroup channels = ctx.pipeline().get(LoginAuthHandler.class).channels;
    	Header header = msg.getHeader();
		ChannelMatcher matcher = new UserMatcher(header.getTo());
		channels.writeAndFlush(msg, matcher);
		
		//submit store message to thread pool.
		ExecutorService taskExecutor = TaskExecutor.getInstance().taskExecutor;
		taskExecutor.submit(new MessageStoreCallable(Lists.<IMessage>newArrayList(msg)));
    }
    
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.log(Level.ERROR, cause);
        if(cause instanceof IOException){
        	ctx.close();
        }
    }
}
