package com.mercury.chat.user.service;

import java.util.List;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.entity.User;

public interface UserService {
	
	boolean login(String userId, String passWord);
	
	List<User> getUserList(String userId);
	
	IMessage select(String userId, Long shopId, int offset, int batchSize);
	
	int store(List<IMessage> message);
	
}
