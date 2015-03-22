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
import com.mercury.chat.user.entity.QuickReply;


public interface ChatClient {
	
	void login(String userName, String password, Properties properties) throws ChatException;
	
	void logout();//just logoff
	
	boolean isConnected();
	
	void sendMessage(Message message) throws ChatException;
	
	void addMessageListener(MessageListener messageListener);
	
	void removeListener(MessageListener messageListener);
	
	void setConnectionListener(ConnectionListener connectionListener);
	
	/**
	 * load historical chat message from db/redis/other storer
	 * @param shopId
	 * @param userId chat userId, not shop userId
	 * @param offset
	 * @param batchSize
	 * @return
	 */
	List<ChatMessage> loadHisChatMessage(long shopId, String userId, int offset, int batchSize);
	
	ProductSummary loadProductSummary(long productId);
	
	OrderSummary loadOrderSummary(long orderId);
	
	//below is the sales quick reply service
	/**
	 * Query the quick reply by saleId
	 * @param saleId
	 * @return
	 */
	List<QuickReply> loadQuickReply(long saleId);
	
	/**
	 * Update the quick reply by saleId
	 * @param saleId
	 * @param quickReply
	 */
	void updateQuickReply(long saleId, QuickReply quickReply);
	
	/**
	 * Delete the quick reply by saleId
	 * @param saleId
	 * @param quickReply
	 */
	void deleteReply(long saleId, QuickReply quickReply);
	
	Connection connect(String host, int port);

}
