package com.mercury.chat.server.protocol;

import static com.google.common.base.Preconditions.checkNotNull;
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

import java.security.cert.CertificateException;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.user.repository.UserRepository;

@Component(value ="chatServer")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class SecureChatServer extends Thread{

    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

    public static void main(String[] args) throws Exception {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
    	SecureChatServer bean = ctx.getBean(SecureChatServer.class);
    	bean.startUp(PORT);
    }
    
    private int port;
    
    //inject the user service then pass this instance to handler?
	@Autowired
	private UserRepository userService; 
	
	private volatile Channel channel;
	
	private CountDownLatch latch = new CountDownLatch(1);
    
    public SecureChatServer(){
    	super();
    }
    
	public SecureChatServer(int port) {
		super();
		this.port = port;
		//FIXME start up the server?
	}

	@Override
	public void run() {
		try {
			startUpInner(port);
		} catch (CertificateException | SSLException | InterruptedException e) {
			throw new ChatException(e);
		}
	}

	public void startUp(int port) throws CertificateException, SSLException, InterruptedException {
		port(port);
		start();
	}

	private void startUpInner(int port) throws CertificateException, SSLException, InterruptedException {
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
            latch.countDown();
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
	
	public void waitUntilStarted(){
		try {
			latch.await();
		} catch (InterruptedException e) {
			//ignore
		}
	}
	
	public void port(int port){
		this.port = port;
	}
	
}
