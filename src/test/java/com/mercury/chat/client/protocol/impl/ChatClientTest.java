package com.mercury.chat.client.protocol.impl;

import org.junit.Test;

import com.mercury.chat.client.Connection;
import com.mercury.chat.client.Session;
import com.mercury.chat.client.protocol.SecureChatClient;

import static org.junit.Assert.assertNotNull;

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
	}

}
