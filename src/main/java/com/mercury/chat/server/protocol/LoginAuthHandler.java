package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HANDSHAKE;
import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.MessageType.LOGOFF;
import static com.mercury.chat.common.constant.StatusCode.FAIL;
import static com.mercury.chat.common.constant.StatusCode.INTERNAL_SERVER_ERROR;
import static com.mercury.chat.common.constant.StatusCode.LOGGED_IN;
import static com.mercury.chat.common.constant.StatusCode.OK;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.Attribute;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.matcher.AntiUserMatcher;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.service.UserService;
import com.mercury.chat.user.service.storer.redis.UserServiceImpl;

public class LoginAuthHandler extends ChannelHandlerAdapter {

	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	static final Logger logger = LogManager.getLogger(LoginAuthHandler.class);
	
	@Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {
            @Override
            public void operationComplete(Future<Channel> future) throws Exception {
				StringBuilder messageBody = new StringBuilder();
				messageBody.append("Welcome to " + InetAddress.getLocalHost().getHostName() +" secure chat service!\n");
				messageBody.append("Your session is protected by " +ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() + " cipher suite.");
				Message msg = new Message().header(new Header().type(HANDSHAKE.value())).body(messageBody.toString());
				ctx.writeAndFlush(msg);
                channels.add(ctx.channel());
                
                ctx.channel().closeFuture().addListener(new ChannelFutureListener(){
        			@Override
        			public void operationComplete(ChannelFuture future) throws Exception {
        				User user = ctx.channel().attr(Constant.userInfo).get();
        				if(user != null){
        					channels.writeAndFlush(message, new AntiUserMatcher(user.getUserId()));
        				}
        			}
        		});
            }
        });
    }
	
    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to
     * the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * 
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
    	Message message = (Message) msg;
	
		Header header = message.getHeader();
		
		if (LOGIN.isThisType(header)) {
			//validate
			boolean hasAttr = ctx.channel().hasAttr(Constant.userInfo);
	    	if(hasAttr){
	    		Message errorMsg = new Message().header(new Header().type(LOGIN.value()).statusCode(LOGGED_IN.getKey()));
	    		ctx.writeAndFlush(errorMsg);
	    		return;
	    	}
			
			UserService userService = UserServiceImpl.getInstance();
			User user = (User) message.getBody();
			StatusCode statusCode = null;
			boolean loginResult = false;;
			try {
				loginResult = userService.login(user.getUserId(), user.getPassword());
				if(loginResult){
		          	Attribute<User> userAttr = ctx.channel().attr(Constant.userInfo);
		          	userAttr.setIfAbsent(user);
		          	statusCode = OK;
				}else{
					statusCode = FAIL;
				}
			} catch (Exception e) {
				statusCode = INTERNAL_SERVER_ERROR;
			}
			Message respMsg = new Message().header(new Header().type(MessageType.LOGIN.value()).statusCode(statusCode.getKey()));
		    ctx.writeAndFlush(respMsg);
		}else if(LOGOFF.isThisType(header)){
	        ctx.close();
		}else {
		    ctx.fireChannelRead(msg);
		}
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	logger.log(Level.ERROR, cause);
		ctx.close();
		ctx.fireExceptionCaught(cause);
    }
}
