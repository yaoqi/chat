package com.mercury.chat.user.service;

import java.util.List;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.User;

public interface UserService {
	
	boolean login(String userId, String passWord);
	
	List<User> getUserList(String userId);
	
	List<IMessage> find(String userId);
	
	int store(List<IMessage> message);
	
}
