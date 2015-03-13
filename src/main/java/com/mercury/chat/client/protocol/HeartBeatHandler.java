package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.HEARTBEAT;
import static com.mercury.chat.common.MessageType.LOGIN;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.json.JsonHeader;
import com.mercury.chat.common.struct.json.JsonMessage;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class HeartBeatHandler extends ChannelHandlerAdapter {

	static final Logger logger = LogManager.getLogger(HeartBeatHandler.class);
	
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
    	Message message = (Message) msg;
    	
    	Header header = message.getHeader();
		
		if (LOGIN.isThisType(header)) {
		    heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatHandler.HeartBeatTask(ctx), 0, 5000,TimeUnit.MILLISECONDS);
		} else if (HEARTBEAT.isThisType(header)) {
		    logger.log(Level.INFO, "Client receive server heart beat message : ---> "+ message);
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
			Message heatBeat = new Message().header(new Header().type(HEARTBEAT.value()));
		    logger.log(Level.INFO, "Client send heart beat messsage to server : ---> "+ heatBeat);
		    ctx.writeAndFlush(heatBeat);
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	logger.log(Level.ERROR, cause);
		if (heartBeat != null) {
		    heartBeat.cancel(true);
		    heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
    }
}
