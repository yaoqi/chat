package com.mercury.chat.client.protocol;


public interface ChatClient {
	
	Connection connect(String host, int port);

}
