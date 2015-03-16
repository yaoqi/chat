package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.StatusCode.OK;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageBox;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class LoginAuthHandler extends ChannelHandlerAdapter {

	static final Logger logger = LogManager.getLogger(LoginAuthHandler.class);
	
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
		if (LOGIN.isThisType(header)) {
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
}
