package com.mercury.chat.client.protocol.impl;

import com.mercury.chat.client.protocol.ChatClient;
import com.mercury.chat.client.protocol.Connection;
import com.mercury.chat.client.protocol.SecureChatClient;

public class ChatClientImpl implements ChatClient {

	@Override
	public Connection connect(String host, int port) {
		return SecureChatClient.connect(host, port);
	}

}
