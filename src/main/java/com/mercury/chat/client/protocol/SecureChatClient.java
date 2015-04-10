package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.util.Preconditions.checkAllNotNull;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
import com.mercury.chat.common.ConnectionListener;
import com.mercury.chat.common.exception.ChatException;

public final class SecureChatClient{
    
	static final Logger logger = LogManager.getLogger(SecureChatClient.class);
	
	private EventLoopGroup group = new NioEventLoopGroup();
	
	private ConnectionListener connectionListener;
	
	//default value for client side.
	static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

	public static void main(String[] args) throws Exception {
        connect(HOST, PORT);
    }

    public static Connection connect(String host, int port) {
		return new ConnectionImpl().channel(new SecureChatClient().connectInner(host, port));
	}

	public Channel connectInner(String host, int port) {
		checkAllNotNull(host, port);
		// Configure SSL.
        SslContext sslCtx;
		try {
			sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
			Bootstrap b = new Bootstrap();
	        b.group(group)
	         .option(ChannelOption.TCP_NODELAY, true)
	         .channel(NioSocketChannel.class)
	         .handler(new SecureChatClientInitializer(sslCtx));
	         ChannelFuture channelFuture = b.connect(host, port);
	         
	         channelFuture.addListener(new ChannelFutureListener(){
	 			@Override
				public void operationComplete(ChannelFuture future) throws Exception {
	 				ConnectionListener listener = getConnectionListener();
					if (listener != null) {
						listener.onConnection();
					}
				}
			});
	         
			Channel channel = channelFuture.sync().channel();
			channel.closeFuture().addListener(new ChannelFutureListener(){
	 			@Override
				public void operationComplete(ChannelFuture future) throws Exception {
	 				ConnectionListener listener = getConnectionListener();
					if (listener != null) {
						listener.onClose();
					}
				}
			});
			return channel;
		} catch (Exception e) {
			//handler the exception
			if (connectionListener != null) {
				connectionListener.onError(e);
			}
			group.shutdownGracefully();
			throw new ChatException(e);
		}
	}

	public ConnectionListener getConnectionListener() {
		return connectionListener;
	}

	public void setConnectionListener(ConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}
    
}
