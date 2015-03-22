package com.mercury.chat.server.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.Executors;

import com.mercury.chat.common.ReadTimeoutHandler;
import com.mercury.chat.common.codec.protocol.MessageDecoder;
import com.mercury.chat.common.codec.protocol.MessageEncoder;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class SecureChatServerInitializer extends ChannelInitializer<SocketChannel> {

	final static EventExecutorGroup businessGroup = new DefaultEventLoopGroup(50, Executors.newFixedThreadPool(50));
	
    private final SslContext sslCtx;

    public SecureChatServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(sslCtx.newHandler(ch.alloc()));

        pipeline.addLast("MessageDecoder", new MessageDecoder(1024 * 1024, 4, 4));
		pipeline.addLast("MessageEncoder", new MessageEncoder());
		pipeline.addLast("ExceptionHandler", new ExceptionHandler());
		pipeline.addLast("ReadTimeoutHandler", new ReadTimeoutHandler(500));
		pipeline.addLast(businessGroup, "LoginAuthHandler",new LoginAuthHandler());
		pipeline.addLast("HeartBeatHandler", new HeartBeatHandler());
		pipeline.addLast(businessGroup, "HistoricalMessageHandler",new HistoricalMessageHandler());

        // and then business logic.
        pipeline.addLast("ChatHandler", new SecureChatServerHandler());
    }
}
