package com.mercury.chat.client.protocol.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.cert.CertificateException;
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
import com.mercury.chat.user.entity.User;

public class ChatTest{

	String[] locations = {"test-context.xml"};

	@Rule
	public TestRule rule = new TestRuleImpl2(locations, this);
	
	@Autowired
	private SecureChatServer secureChatServer;
	
	@Before
	public void setUp() throws CertificateException, SSLException, InterruptedException{
		secureChatServer.startUp(8992);
	}
	
	@After
	public void tearDown() throws CertificateException, SSLException, InterruptedException{
		secureChatServer.close();
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
	
	@Test(expected = ChatException.class)
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginFailed1() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@google.com", "welcome1");
		assertNotNull(session);
		connection.close();
	}
	
	@Test(expected = ChatException.class)
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginFailed2() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@gmail.com", "welcome1");
		session = connection.login("google@gmail.com", "welcome1");
		assertNotNull(session);
		connection.close();
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
		
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testGetHisMessage() throws InterruptedException {
		
		ChatClient chatClient = new ChatClientImpl("127.0.0.1", 8992);
		chatClient.login("google@gmail.com", "welcome1", null);
		List<ChatMessage> chatMsgs = chatClient.loadHisChatMessage(1l, "baidu@baidu.com", 0, 5);
		assertEquals(5, chatMsgs.size());
		
	}

}
