package com.mercury.chat.server.json;

import static com.mercury.chat.common.MessageType.HEARTBEAT;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.mercury.chat.common.struct.json.JsonHeader;
import com.mercury.chat.common.struct.json.JsonMessage;

public class HeartBeatHandler extends ChannelHandlerAdapter {
    
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
    	JsonMessage message = (JsonMessage) msg;
		
		JsonHeader header = message.getHeader();
		
		if (HEARTBEAT.isThisType(header)) {
		    //System.out.println("Receive client heart beat message : ---> "+ message.getHeader());
			
		    JsonMessage heatBeat = new JsonMessage().header(new JsonHeader().type(HEARTBEAT.value()));
		    
		    //System.out.println("Send heart beat response message to client : ---> "+ heatBeat.getHeader());
			ctx.writeAndFlush(heatBeat);
		} else
		    ctx.fireChannelRead(msg);
    }
	
}
