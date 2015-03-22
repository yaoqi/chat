package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.TaskExecutor.businessGroup;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

import com.mercury.chat.common.ReadTimeoutHandler;
import com.mercury.chat.common.codec.protocol.MessageDecoder;
import com.mercury.chat.common.codec.protocol.MessageEncoder;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class SecureChatServerInitializer extends ChannelInitializer<SocketChannel> {

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
