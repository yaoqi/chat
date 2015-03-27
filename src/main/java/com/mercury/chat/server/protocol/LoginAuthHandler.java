package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HANDSHAKE;
import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.MessageType.LOGOFF;
import static com.mercury.chat.common.MessageType.USER_LIST;
import static com.mercury.chat.common.constant.StatusCode.FAIL;
import static com.mercury.chat.common.constant.StatusCode.INTERNAL_SERVER_ERROR;
import static com.mercury.chat.common.constant.StatusCode.LOGGED_IN;
import static com.mercury.chat.common.constant.StatusCode.OK;
import static com.mercury.chat.common.constant.StatusCode.USER_LOGIN;
import static com.mercury.chat.common.constant.StatusCode.USER_LOGOFF;
import static com.mercury.chat.common.util.Channels.has;
import static com.mercury.chat.common.util.Channels.set;
import static com.mercury.chat.common.util.Messages.buildMessage;
import static com.mercury.chat.server.protocol.group.SessionManager.channels;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetAddress;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.matcher.AntiUserMatcher;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.server.protocol.group.SessionManager;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.repository.UserRepository;

public class LoginAuthHandler extends SimpleChannelInboundHandler<Message> {

	private final UserRepository userService;
	
	public LoginAuthHandler(UserRepository userService){
		this.userService = userService;
	}
	
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
				Message msg = buildMessage(HANDSHAKE,messageBody.toString());
				ctx.writeAndFlush(msg);
                
                ctx.channel().closeFuture().addListener(new ChannelFutureListener(){
        			@Override
        			public void operationComplete(ChannelFuture future) throws Exception {
        				User user = ctx.channel().attr(Constant.userInfo).get();
        				if(user != null){
        					Message message = buildMessage(USER_LIST, USER_LOGOFF, user);
							channels.writeAndFlush(message , new AntiUserMatcher(user.getUserId()));
        				}
        			}

        		});
            }
        });
    }
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		if (LOGIN.$(msg)) {
			//validate
	    	if(has(ctx.channel(), Constant.userInfo)){
	    		ctx.writeAndFlush(buildMessage(LOGIN, LOGGED_IN));
	    		return;
	    	}
			
			User user = (User) msg.getBody();
			StatusCode statusCode = null;
			try {
				User loginUser = userService.login(user.getUserId(), user.getPassword());
				if (loginUser != null) {
					//put the login user into cache.
					SessionManager.uerCache.put(user.getUserId(), loginUser);
					
					ctx.writeAndFlush(buildMessage(USER_LIST, USER_LOGIN, user));
		          	set(ctx.channel(), Constant.userInfo, user);
		          	statusCode = OK;
		          	//if login successfully,will add this channel to channel group
		          	channels.add(ctx.channel());
				}else{
					statusCode = FAIL;
				}
			} catch (Exception e) {
				statusCode = INTERNAL_SERVER_ERROR;
			}
		    ctx.writeAndFlush(buildMessage(LOGIN, statusCode));
		}else if(LOGOFF.$(msg)){
			logger.log(Level.ERROR, msg);
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
