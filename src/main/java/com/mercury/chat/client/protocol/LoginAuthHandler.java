package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.StatusCode.OK;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import org.apache.logging.log4j.Level;

import com.mercury.chat.common.MessageBox;
import com.mercury.chat.common.MessageType;

import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class LoginAuthHandler extends SimpleMessageHandler {

	@Override
	protected MessageType _() {
		return LOGIN;
	}
	
	private volatile MessageBox loginMessageBox = new MessageBox();
	
    public MessageBox getLoginMessageBox() {
		return loginMessageBox;
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
		if (LOGIN.$(message)) {
		    if(OK.isThisType(header)){
		    	ctx.fireChannelRead(msg);
		    }
		    
		    loginMessageBox.put(message);//put the login result to message box.
		   
		    logger.log(Level.INFO, message);
		} else
		    ctx.fireChannelRead(msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	logger.log(Level.ERROR, cause);
    	ctx.fireExceptionCaught(cause);
    }

	@Override
	protected void onMessage(ChannelHandlerContext ctx, Message msg) {
		loginMessageBox.put(msg);//put the login result to message box.
		
	}

}
