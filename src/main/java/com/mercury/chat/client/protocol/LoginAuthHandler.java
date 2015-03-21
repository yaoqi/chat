package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.StatusCode.OK;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.impl.SimpleLinstenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;

public class LoginAuthHandler extends SimpleLinstenbleHandler {

	static final Logger logger = LogManager.getLogger(LoginAuthHandler.class);
	
	@Override
	protected MessageType _() {
		return LOGIN;
	}
	
	/**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to
     * the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * 
     * Sub-classes may override this method to change behavior.
     */	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		Message message = (Message) msg;
		if (LOGIN.$(message)) {
		    if(OK.$(message)){
		    	ctx.fireChannelRead(msg);
		    }
		    
		    messageBox().put(message);//put the login result to message box.
		   
		    logger.log(Level.INFO, message);
		} else
		    ctx.fireChannelRead(msg);
	}

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	logger.log(Level.ERROR, cause);
    	ctx.fireExceptionCaught(cause);
    }


}
