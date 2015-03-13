package com.mercury.chat.server.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.Executors;

import com.mercury.chat.common.codec.json.JsonMessageDecoder;
import com.mercury.chat.common.codec.json.JsonMessageEncoder;

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

        // Add SSL handler first to encrypt and decrypt everything.
        // In this example, we use a bogus certificate in the server side
        // and accept any invalid certificates in the client side.
        // You will need something more complicated to identify both
        // and server in the real world.
        pipeline.addLast(sslCtx.newHandler(ch.alloc()));

        pipeline.addLast(new JsonObjectDecoder());
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast("message decoder",new JsonMessageDecoder());
        pipeline.addLast("message encoder",new JsonMessageEncoder(true));
        pipeline.addLast(businessGroup, "login",new LoginAuthHandler());
        pipeline.addLast("heart beat",new HeartBeatHandler());
        pipeline.addLast("readTimeoutHandler",new ReadTimeoutHandler(500));

        // and then business logic.
        pipeline.addLast(new SecureChatServerHandler());
    }
}
