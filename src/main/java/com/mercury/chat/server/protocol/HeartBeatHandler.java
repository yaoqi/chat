package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.HEARTBEAT;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class HeartBeatHandler extends ChannelHandlerAdapter {

	static final Logger logger = LogManager.getLogger(HeartBeatHandler.class);
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
    	Message message = (Message) msg;
		
		Header header = message.getHeader();
		
		if (HEARTBEAT.isThisType(header)) {
			
			logger.log(Level.INFO, "Receive client heart beat message : ---> "+ message);
			
		    Message heatBeat = new Message().header(new Header().type(HEARTBEAT.value()));
		    
		    logger.log(Level.INFO, "Send heart beat response message to client : ---> "+ heatBeat);
			ctx.writeAndFlush(heatBeat);
		} else
		    ctx.fireChannelRead(msg);
    }
	
}
