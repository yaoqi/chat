package com.mercury.chat.client.json;

import static com.mercury.chat.common.MessageType.HEARTBEAT;
import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.LoginFlag.SUCCESS;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.mercury.chat.common.struct.json.JsonHeader;
import com.mercury.chat.common.struct.json.JsonMessage;

public class HeartBeatHandler extends ChannelHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	
    	JsonMessage message = (JsonMessage) msg;
		
		JsonHeader header = message.getHeader();
		
		if (LOGIN.isThisType(header)) {
			if(SUCCESS.isThisType(message.getBody())){
				heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatHandler.HeartBeatTask(ctx), 0, 5000,TimeUnit.MILLISECONDS);
			}
		} else if (HEARTBEAT.isThisType(header)) {
			
		    //System.out.println("Client receive server heart beat message : ---> " + message);
		    
		} else
		    ctx.fireChannelRead(msg);
		
    }

    private class HeartBeatTask implements Runnable {
		
    	private final ChannelHandlerContext ctx;
	
		public HeartBeatTask(final ChannelHandlerContext ctx) {
		    this.ctx = ctx;
		}

		@Override
		public void run() {
		    JsonMessage heatBeat = new JsonMessage().header(new JsonHeader().type(HEARTBEAT.value()));
		    //System.out.println("Client send heart beat messsage to server : ---> "+ heatBeat);
		    ctx.writeAndFlush(heatBeat);
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (heartBeat != null) {
		    heartBeat.cancel(true);
		    heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
    }
}
