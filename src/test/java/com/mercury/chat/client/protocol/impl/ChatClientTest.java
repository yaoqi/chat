package com.mercury.chat.client.protocol.impl;

import org.junit.Test;

import com.mercury.chat.client.protocol.Connection;
import com.mercury.chat.client.protocol.SecureChatClient;
import static org.junit.Assert.assertNotNull;

public class ChatClientTest{

	@Test
	public void testConnect() {
		Connection connect = SecureChatClient.connect("127.0.0.1", 8992);
		assertNotNull(connect);
	}

}
