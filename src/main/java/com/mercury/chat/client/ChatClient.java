package com.mercury.chat.client;

import java.util.List;
import java.util.Properties;

import com.mercury.chat.common.ConnectionListener;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.OrderSummary;
import com.mercury.chat.common.ProductSummary;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.ChatMessage;


public interface ChatClient {
	
	void login(String userName, String password, Properties properties) throws ChatException;
	
	void logout();
	
	boolean isConnected();
	
	void sendMessage(Message message) throws ChatException;
	
	void addMessageListener(MessageListener messageListener);
	
	void removeListener(MessageListener messageListener);
	
	void setConnectionListener(ConnectionListener connectionListener);
	
	List<ChatMessage> loadHisChatMessage(long shopId, long userId, int offset, int batchSize);
	
	ProductSummary loadProductSummary(long productId);
	
	OrderSummary loadOrderSummary(long orderId);
	
	Connection connect(String host, int port);

}
