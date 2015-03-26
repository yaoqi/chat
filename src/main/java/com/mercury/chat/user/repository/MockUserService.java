package com.mercury.chat.user.repository;

import java.util.List;

import com.mercury.chat.common.OrderSummary;
import com.mercury.chat.common.ProductSummary;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.User;

public class MockUserService implements UserRepository {

	private static class SingletonHolder {
		private static final MockUserService INSTANCE = new MockUserService();
	}

	private MockUserService() {
	}

	public static final MockUserService getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	@Override
	public boolean login(String userId, String passWord) {
		return true;
	}

	@Override
	public List<User> getUserList(String userId) {
		return null;
	}

	@Override
	public IMessage select(String userId, Long shopId, int offset, int batchSize) {
		return null;
	}

	@Override
	public int store(List<IMessage> messages) {
		return messages.size();
	}

	@Override
	public ProductSummary loadProductSummary(long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderSummary loadOrderSummary(long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuickReply> loadQuickReply(long saleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateQuickReply(long saleId, QuickReply quickReply) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteReply(long saleId, QuickReply quickReply) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}


}
