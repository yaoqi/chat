package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.TaskExecutor.businessGroup;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.ReadTimeoutHandler;

import com.mercury.chat.server.protocol.CommonCRUDHandler;
import com.mercury.chat.common.ChatTimeoutHandler;
import com.mercury.chat.common.codec.protocol.MessageDecoder;
import com.mercury.chat.common.codec.protocol.MessageEncoder;
import com.mercury.chat.user.repository.UserRepository;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class SecureChatServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    
    private final UserRepository userService;

    public SecureChatServerInitializer(SslContext sslCtx, UserRepository userService) {
        this.sslCtx = sslCtx;
        this.userService = userService;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(sslCtx.newHandler(ch.alloc()));

        pipeline.addLast("MessageDecoder", new MessageDecoder(1024 * 1024, 4, 4));
		pipeline.addLast("MessageEncoder", new MessageEncoder());
		pipeline.addLast("ExceptionHandler", new ExceptionHandler());
		pipeline.addLast("ReadTimeoutHandler", new ReadTimeoutHandler(50));
		pipeline.addLast("ChatTimeoutHandler", new ChatTimeoutHandler(300));
		pipeline.addLast(businessGroup, "LoginAuthHandler",new LoginAuthHandler(userService));
		pipeline.addLast("HeartBeatHandler", new HeartBeatHandler());
		pipeline.addLast(businessGroup, "HistoricalMessageHandler",new HistoricalMessageHandler(userService));
		pipeline.addLast(businessGroup, "CommonCRUDHandler", new CommonCRUDHandler(userService));
		pipeline.addLast(businessGroup, "UserListHandler", new UserListHandler());
        // and then business logic.
        pipeline.addLast("ChatHandler", new SecureChatServerHandler());
    }
}
