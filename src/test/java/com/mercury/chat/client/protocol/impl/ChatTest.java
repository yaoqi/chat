package com.mercury.chat.client.protocol.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.SSLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.mercury.chat.client.ChatClient;
import com.mercury.chat.client.Connection;
import com.mercury.chat.client.Session;
import com.mercury.chat.client.impl.ChatClientImpl;
import com.mercury.chat.client.protocol.SecureChatClient;
import com.mercury.chat.common.ConnectionListener;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.common.test.DataPrepare2;
import com.mercury.chat.common.test.DbType;
import com.mercury.chat.common.test.TestRuleImpl2;
import com.mercury.chat.common.util.Messages;
import com.mercury.chat.server.protocol.SecureChatServer;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.OrderSummary;
import com.mercury.chat.user.entity.ProductSummary;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.repository.UserRepository;

public class ChatTest{

	String[] locations = {"test-context.xml"};

	@Rule
	public TestRule rule = new TestRuleImpl2(locations, this);
	
	@Autowired
	private SecureChatServer secureChatServer;
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setUp() throws CertificateException, SSLException, InterruptedException{
		secureChatServer.startUp(8992);
		secureChatServer.waitUntilStarted();
		System.setProperty("chat.server.message.saved.wait", "true");
	}
	
	@After
	public void tearDown() throws CertificateException, SSLException, InterruptedException{
		secureChatServer.close();
		System.clearProperty("chat.server.message.saved.wait");
	}

	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testConnectFailed() {
		try {
			Connection connection = SecureChatClient.connect("XXXX", 8992);
			assertNotNull(connection);
		} catch (ChatException e) {
			assertTrue(e.getCause() instanceof UnknownHostException);
		}
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testConnectException() {
		try {
			Connection connection = SecureChatClient.connect("127.0.0.1", 8991);
			assertNotNull(connection);
		} catch (ChatException e) {
			assertTrue(e.getCause() instanceof ConnectException);
		}
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testConnect() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLogin() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@gmail.com", "welcome1");
		assertNotNull(session);
		connection.close();
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginByClient() {
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testSetConnectionListener() {
		ChatClient chatClient = new ChatClientImpl();
		chatClient.setConnectionListener(new ConnectionListener(){
			@Override
			public void onConnection() {
				System.out.println("onConnection");
			}

			@Override
			public void onClose() {
				System.out.println("onClose");
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("onError");
			}
			
		});
		chatClient.connect("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginByClient2Times() {
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		chatClient = new ChatClientImpl("127.0.0.1", 8992);
		try {
			chatClient.login("google@gmail.com", "welcome1", null);
		} catch (ChatException e) {
			assertEquals("Invalid operation:already logged in", e.getMessage());
		}
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginFailedByClient() {
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		try {
			chatClient.login("google@gmail.com", "welcome", null);
		} catch (ChatException e) {
			assertEquals("Operate Failure", e.getMessage());
		}
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginFailed1() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		try {
			Session session = connection.login("google@google.com", "welcome1");
			assertNotNull(session);
		} catch (ChatException e) {
			assertEquals("Operate Failure", e.getMessage());
		}
		connection.close();
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginFailed2() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@gmail.com", "welcome1");
		try {
			session = connection.login("google@gmail.com", "welcome1");
		} catch (ChatException e) {
			assertEquals("Invalid operation:already logged in", e.getMessage());
		}
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginFailed2ByClient() {
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		try {
			chatClient.login("google@gmail.com", "welcome1", null);
		} catch (ChatException e) {
			assertEquals("Logined", e.getMessage());
		}
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLogout() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@gmail.com", "welcome1");
		assertNotNull(session);
		assertTrue(session.logoff());
		connection.close();
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLogoutThenLogin() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@gmail.com", "welcome1");
		assertNotNull(session);
		assertTrue(session.logoff());
		session = connection.login("google@gmail.com", "welcome1");
		assertNotNull(session);
		connection.close();
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLogoutThenLoginByClient() {
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		chatClient.logout();
		chatClient.login("google@gmail.com", "welcome1", null);
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testSendMessage() throws InterruptedException {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@gmail.com", "welcome1");
		assertNotNull(session);
		
		Connection connection2 = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection2);
		Session session2 = connection2.login("baidu@baidu.com", "welcome1");
		assertNotNull(session2);
		
		final CountDownLatch cdl = new CountDownLatch(1);
		
		final List<Message> msgs = Lists.newArrayList();
		
		MessageListener messageListener = new MessageListener(){
			@Override
			public void onMessage(Message message) {
				msgs.add(message);
				cdl.countDown();
			}
		};
		
		session2.addMessageListener(MessageType.CHAT, messageListener );
		
		Message message = Messages.buildMessage(MessageType.CHAT, "Hello baidu.");
		session.sendMessage("baidu@baidu.com", message );
		
		cdl.await();
		
		assertEquals("Hello baidu.", msgs.get(0).getBody());
		
		List<ChatMessage> messages = userRepository.select("baidu@baidu.com", 1l, 0, 20);
		assertEquals(9, messages.size());
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testSendMessageByClient() throws InterruptedException {
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		
		ChatClient chatClient2 =  new ChatClientImpl("127.0.0.1", 8992);;
		chatClient2.login("baidu@baidu.com", "welcome1", null);
		
		final CountDownLatch cdl = new CountDownLatch(1);
		
		final List<Message> msgs = Lists.newArrayList();
		
		MessageListener messageListener = new MessageListener(){
			@Override
			public void onMessage(Message message) {
				msgs.add(message);
				cdl.countDown();
			}
		};
		
		chatClient2.addMessageListener(messageListener );
		
		Message message = Messages.buildMessage(MessageType.CHAT, "Hello baidu.");
		message.getHeader().attach("toUser", "baidu1");
		message.getHeader().to("baidu@baidu.com");
		chatClient.sendMessage(message );
		
		cdl.await();
		
		assertEquals("Hello baidu.", msgs.get(0).getBody());
		
		List<ChatMessage> messages = userRepository.select("baidu@baidu.com", 1l, 0, 20);
		assertEquals(8, messages.size());
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testGetHisMessage() throws InterruptedException {
		
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		List<ChatMessage> chatMsgs = chatClient.loadHisChatMessage(1l, "baidu@baidu.com", 0, 5);
		assertEquals(5, chatMsgs.size());
		
		chatMsgs = chatClient.loadHisChatMessage(2l, "baidu@baidu.com", 0, 2);
		assertEquals(0, chatMsgs.size());
		
		try {
			chatMsgs = chatClient.loadHisChatMessage(-1l, "baidu@baidu.com", 0, 2);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "shopId must greater than zero");
		}
		
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoadQuickReplies() throws InterruptedException {
		
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		List<QuickReply> qrs = chatClient.loadQuickReply(1);
		assertEquals(2, qrs.size());
		
	}
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testUpdateQuickReply() throws InterruptedException {
		
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		QuickReply quickReply = new QuickReply();
		quickReply.setUuid(1L);
		quickReply.setMessage("XXXXX");
		chatClient.updateQuickReply(1L, quickReply);
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testDeleteQuickReply() throws InterruptedException {
		
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		QuickReply quickReply = new QuickReply();
		quickReply.setUuid(1L);
		quickReply.setMessage("XXXXX");
		chatClient.deleteReply(1L, quickReply);
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testGetOnLineUsers() throws InterruptedException {
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		
		ChatClient chatClient2 =  new ChatClientImpl("127.0.0.1", 8992);;
		chatClient2.login("baidu@baidu.com", "welcome1", null);
		
		List<User> onlineUsers = chatClient.getOnlineUsers(1L);
		assertEquals(1, onlineUsers.size());
		
		onlineUsers = chatClient2.getOnlineUsers(1L);
		assertEquals(1, onlineUsers.size());
		
		chatClient.logout();
		Thread.sleep(1000);
		onlineUsers = chatClient2.getOnlineUsers(1L);
		assertEquals(0, onlineUsers.size());
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoadProductSummary() throws InterruptedException {
		
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		ProductSummary productSummary = chatClient.loadProductSummary(1L);
		assertNotNull(productSummary);
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoadOrderSummary() throws InterruptedException {
		
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		OrderSummary orderSummary = chatClient.loadOrderSummary(1L);
		assertNotNull(orderSummary);
		Collection<ProductSummary> products = orderSummary.getProducts();
		assertEquals(2, products.size());
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoadOrderSummary2Times() throws InterruptedException {
		
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		OrderSummary orderSummary = chatClient.loadOrderSummary(1L);
		assertNotNull(orderSummary);
		orderSummary = chatClient.loadOrderSummary(2L);
		assertNull(orderSummary);
	}

}
