package com.mercury.chat.client;

import com.mercury.chat.client.Connection;


public interface ChatClient {
	
	Connection connect(String host, int port);

}
