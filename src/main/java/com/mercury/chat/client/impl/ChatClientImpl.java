package com.mercury.chat.client.impl;

import static com.mercury.chat.common.util.Preconditions.checkAllNotNull;

import java.util.List;
import java.util.Properties;

import com.mercury.chat.client.ChatClient;
import com.mercury.chat.client.Connection;
import com.mercury.chat.client.protocol.SecureChatClient;
import com.mercury.chat.common.ConnectionListener;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.OrderSummary;
import com.mercury.chat.common.ProductSummary;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.ChatMessage;

public class ChatClientImpl implements ChatClient {

	@Override
	public Connection connect(String host, int port) {
		checkAllNotNull(host, port);
		return SecureChatClient.connect(host, port);
	}

	@Override
	public void login(String userName, String password, Properties properties) throws ChatException {
		
	}

	@Override
	public void logout() {
		
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public void sendMessage(Message message) throws ChatException {
		
	}

	@Override
	public void addMessageListener(MessageListener messageListener) {
		
	}

	@Override
	public void removeListener(MessageListener messageListener) {
		
	}

	@Override
	public void setConnectionListener(ConnectionListener connectionListener) {
		
	}

	@Override
	public List<ChatMessage> loadHisChatMessage(long shopId, long userId, int offset, int batchSize) {
		return null;
	}

	@Override
	public ProductSummary loadProductSummary(long productId) {
		return null;
	}

	@Override
	public OrderSummary loadOrderSummary(long orderId) {
		return null;
	}

}
