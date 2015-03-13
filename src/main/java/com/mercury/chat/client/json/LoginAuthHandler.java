package com.mercury.chat.client.json;

import static com.mercury.chat.common.MessageType.LOGIN;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import com.mercury.chat.common.constant.LoginFlag;
import com.mercury.chat.common.struct.json.JsonHeader;
import com.mercury.chat.common.struct.json.JsonMessage;

public class LoginAuthHandler extends ChannelHandlerAdapter {

	/**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to
     * the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * 
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	
	    JsonMessage message = (JsonMessage) msg;
	
		JsonHeader header = message.getHeader();
		
		if (LOGIN.isThisType(header)) {
		    LoginFlag result = LoginFlag.valOf(message.getBody());
		    if(result.isSuccess()){
		    	ctx.fireChannelRead(msg);
		    }
		    System.out.println(result.message());
		} else
		    ctx.fireChannelRead(msg);
		
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	ctx.fireExceptionCaught(cause);
    }
}
