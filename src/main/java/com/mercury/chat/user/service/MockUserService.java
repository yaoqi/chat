package com.mercury.chat.user.service;

import java.util.List;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.entity.User;

public class MockUserService implements UserService {

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


}
