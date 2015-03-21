package com.mercury.chat.user.service.storer;

import java.util.Date;
import java.util.List;

import com.mercury.chat.user.entity.Message;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.service.UserService;

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
	public List<Message> find(String userId, Date from, Date to) {
		return null;
	}

	@Override
	public int store(List<Message> messages) {
		return messages.size();
	}

}
