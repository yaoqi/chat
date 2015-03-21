package com.mercury.chat.client.impl;

import static com.mercury.chat.common.util.Preconditions.checkAllNotNull;

import com.mercury.chat.client.ChatClient;
import com.mercury.chat.client.Connection;
import com.mercury.chat.client.protocol.SecureChatClient;

public class ChatClientImpl implements ChatClient {

	@Override
	public Connection connect(String host, int port) {
		checkAllNotNull(host, port);
		return SecureChatClient.connect(host, port);
	}

}
