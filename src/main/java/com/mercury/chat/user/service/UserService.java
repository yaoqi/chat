package com.mercury.chat.user.service;

import java.util.Date;
import java.util.List;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.entity.User;

public interface UserService {
	
	boolean login(String userId, String passWord);
	
	List<User> getUserList(String userId);
	
	List<IMessage> find(String userId, Date from, Date to);
	
	int store(List<IMessage> message);
	
}
