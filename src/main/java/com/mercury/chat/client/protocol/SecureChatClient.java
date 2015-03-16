package com.mercury.chat.client.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.Connection;
import com.mercury.chat.client.impl.ConnectionImpl;
import com.mercury.chat.common.exception.ChatException;

public final class SecureChatClient{
    
	static final Logger logger = LogManager.getLogger(SecureChatClient.class);
	
	private EventLoopGroup group = new NioEventLoopGroup();
	
	//default value for client side.
	static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

	public static void main(String[] args) throws Exception {
        connect(HOST, PORT);
    }

    public static Connection connect(String host, int port) {
		return new ConnectionImpl().channel(new SecureChatClient().connectInner(host, port));
	}

	private Channel connectInner(String host, int port) {
		// Configure SSL.
        SslContext sslCtx;
		try {
			sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
			Bootstrap b = new Bootstrap();
	        b.group(group)
	         .option(ChannelOption.TCP_NODELAY, true)
	         .channel(NioSocketChannel.class)
	         .handler(new SecureChatClientInitializer(sslCtx));
	         return b.connect(host, port).sync().channel();
		} catch (Exception e) {
			group.shutdownGracefully();
			throw new ChatException(e);
		}
	}
    
}
