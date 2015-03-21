package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.HEARTBEAT;
import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.constant.StatusCode.OK;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.handler.impl.SimpleLinstenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;

public class HeartBeatHandler extends SimpleLinstenbleHandler {

	static final Logger logger = LogManager.getLogger(HeartBeatHandler.class);
	
    private volatile ScheduledFuture<?> heartBeat;
    
    @Override
	protected MessageType _() {
		return HEARTBEAT;
	}
    
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		
		if (LOGIN.$(msg)) {
			if(OK.$(msg)){
				heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatHandler.HeartBeatTask(ctx), 0, 5000,TimeUnit.MILLISECONDS);
			}
		} else if (_().$(msg)) {
			for(MessageListener listener : listeners()){
				listener.onMessage(msg);
			}
		    logger.log(Level.INFO, "Client receive server heart beat message : ---> "+ msg);
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
		    ctx.writeAndFlush(buildMessage(HEARTBEAT));
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
