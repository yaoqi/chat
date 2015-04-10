package com.mercury.chat.client;

import java.util.List;
import java.util.Properties;

import com.mercury.chat.common.ConnectionListener;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.OrderSummary;
import com.mercury.chat.user.entity.ProductSummary;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.User;


public interface ChatClient {
	
	void login(String userName, String password, Properties properties) throws ChatException;
	
	void logout();
	
	boolean isConnected();
	
	List<User> getOnlineUsers(long shopId);
	
	void sendMessage(Message message) throws ChatException;
	
	void addMessageListener(MessageListener messageListener);
	
	void removeListener(MessageListener messageListener);
	
	void setConnectionListener(ConnectionListener connectionListener);
	
	/**
	 * load historical chat messages from database
	 * @param shopId
	 * @param userId chat userId, not shop userId
	 * @param offset
	 * @param batchSize
	 * @return
	 */
	List<ChatMessage> loadHisChatMessage(long shopId, String userId, int offset, int batchSize);
	
	ProductSummary loadProductSummary(long productId);
	
	OrderSummary loadOrderSummary(long orderId);
	
	List<QuickReply> loadQuickReply(long saleId);
	
	void updateQuickReply(long saleId, QuickReply quickReply);
	
	void deleteReply(long saleId, QuickReply quickReply);
	
	Connection connect(String host, int port);

}
