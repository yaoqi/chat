package com.mercury.chat.server.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.Executors;

import com.mercury.chat.server.protocol.HeartBeatHandler;
import com.mercury.chat.server.protocol.LoginAuthHandler;
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

        // Add SSL handler first to encrypt and decrypt everything.
        // In this example, we use a bogus certificate in the server side
        // and accept any invalid certificates in the client side.
        // You will need something more complicated to identify both
        // and server in the real world.
        pipeline.addLast(sslCtx.newHandler(ch.alloc()));

        pipeline.addLast("MessageDecoder", new MessageDecoder(1024 * 1024, 4, 4));
		pipeline.addLast("MessageEncoder", new MessageEncoder());
		pipeline.addLast("ReadTimeoutHandler", new ReadTimeoutHandler(50));
		pipeline.addLast(businessGroup, "login",new LoginAuthHandler());
		pipeline.addLast("HeartBeatHandler", new HeartBeatHandler());

        // and then business logic.
        pipeline.addLast(new SecureChatServerHandler());
    }
}
