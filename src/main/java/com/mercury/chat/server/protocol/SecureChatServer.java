package com.mercury.chat.server.protocol;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;

import static com.google.common.base.Preconditions.checkNotNull;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.user.service.UserService;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;


public final class SecureChatServer {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

    public static void main(String[] args) throws Exception {
        new SecureChatServer().startUp(PORT);
    }
    
    private int port;
    
    //inject the user service then pass this instance to handler?
	@Autowired
	private UserService userService; 
	
	private volatile Channel channel;
    
    public SecureChatServer(){
    	super();
    }
    
	public SecureChatServer(int port) {
		super();
		this.port = port;
		//FIXME start up the server?
	}

	public void startUp(int port) throws CertificateException, SSLException, InterruptedException {
		
		SelfSignedCertificate ssc = new SelfSignedCertificate();
        SslContext sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .option(ChannelOption.SO_BACKLOG, 100)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new SecureChatServerInitializer(sslCtx,userService));

            channel = b.bind(port).sync().channel();
			channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
	
	public void close(){
		checkNotNull(channel, "Server Unavailable");
		try {
			channel.close().sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
}
