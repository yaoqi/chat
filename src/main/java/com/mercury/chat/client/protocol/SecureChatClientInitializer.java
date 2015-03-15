package com.mercury.chat.client.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.ReadTimeoutHandler;

import com.mercury.chat.common.codec.protocol.MessageDecoder;
import com.mercury.chat.common.codec.protocol.MessageEncoder;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class SecureChatClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public SecureChatClientInitializer(SslContext sslCtx) {
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
        pipeline.addLast(sslCtx.newHandler(ch.alloc(), SecureChatClient.HOST, SecureChatClient.PORT));

        pipeline.addLast("MessageDecoder", new MessageDecoder(1024 * 1024, 4, 4));
		pipeline.addLast("MessageEncoder", new MessageEncoder());
		//pipeline.addLast("ReadTimeoutHandler", new ReadTimeoutHandler(50)); FIXME
		pipeline.addLast("LoginAuthHandler", new LoginAuthHandler());
		pipeline.addLast("HeartBeatHandler", new HeartBeatHandler());

        // and then business logic.
        pipeline.addLast(new SecureChatClientHandler());
    }
}
