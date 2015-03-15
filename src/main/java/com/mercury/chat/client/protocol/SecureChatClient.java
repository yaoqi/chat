package com.mercury.chat.client.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.protocol.impl.ConnectionImpl;

public final class SecureChatClient extends Thread{
    
	static final Logger logger = LogManager.getLogger(SecureChatClient.class);
	
	private EventLoopGroup group = new NioEventLoopGroup();
	
	//default value for client side.
	static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));
    private volatile Channel channel;
    private String host;
    private int port;
    
    private Object lock = new Object();
    
    public Channel getChannel(){
    	synchronized(lock){
    		while(channel==null){
    			try {
					lock.wait();
				} catch (InterruptedException e) {
					//ignore
				}
    		}
    		return channel;
    	}
    }
    
    public SecureChatClient(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		this.start();
	}

	@Override
	public void run() {
		
		Runtime.getRuntime().addShutdownHook(new Thread(){

			@Override
			public void run() {
				logger.info("cilent exit!");
				try {
					channel.closeFuture().sync();
				} catch (InterruptedException e) {
					logger.info(e);
				}
				group.shutdownGracefully();
			}
			
		});
		
		try {
			connectInner(host, port);
		} catch (SSLException | InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}

	public static void main(String[] args) throws Exception {
        connect(HOST, PORT);
    }

    public static Connection connect(String host, int port) {
		SecureChatClient secureChatClient = new SecureChatClient(host, port);
		Connection connection = new ConnectionImpl().channel(secureChatClient.getChannel());
		return connection;
	}

	private void connectInner(String host, int port) throws SSLException,InterruptedException {
		// Configure SSL.
        final SslContext sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .option(ChannelOption.TCP_NODELAY, true)
             .channel(NioSocketChannel.class)
             .handler(new SecureChatClientInitializer(sslCtx));

            // Start the connection attempt.
            ChannelFuture sync = b.connect(host, port).sync();
			Channel ch = sync.channel();
            channel = ch;
            synchronized(lock){
    			lock.notify();
        	}
            ch.closeFuture().sync();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
	}
    
}
