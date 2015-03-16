package com.mercury.chat.client;

import com.mercury.chat.client.protocol.Connection;


public interface ChatClient {
	
	Connection connect(String host, int port);

}
