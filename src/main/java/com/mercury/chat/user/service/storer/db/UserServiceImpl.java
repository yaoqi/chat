package com.mercury.chat.user.service.storer.db;

import java.util.Date;
import java.util.List;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.User;
import com.mercury.chat.user.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public boolean login(String userId, String passWord) {
		return false;
	}

	@Override
	public List<User> getUserList(String userId) {
		return null;
	}

	@Override
	public List<IMessage> find(String userId, Date from, Date to) {
		return null;
	}

	@Override
	public int store(List<IMessage> message) {
		return 0;
	}

}