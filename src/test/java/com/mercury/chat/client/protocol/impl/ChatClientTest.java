package com.mercury.chat.client.protocol.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.mercury.chat.client.Connection;
import com.mercury.chat.client.Session;
import com.mercury.chat.client.protocol.SecureChatClient;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.common.util.Messages;

public class ChatClientTest{

	@Test
	public void testConnect() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
	}
	
	@Test
	public void testLogin() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@google.com", "welcome1");
		assertNotNull(session);
		connection.close();
	}
	
	@Test(expected = ChatException.class)
	public void testLoginFailed() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@google.com", "welcome1");
		session = connection.login("google@google.com", "welcome1");
		assertNotNull(session);
		connection.close();
	}
	
	@Test
	public void testLoginOff() {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@google.com", "welcome1");
		assertNotNull(session);
		session.logoff();
		connection.close();
	}
	
	@Test
	public void testSendMessage() throws InterruptedException {
		Connection connection = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connection);
		Session session = connection.login("google@google.com", "welcome1");
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

}
