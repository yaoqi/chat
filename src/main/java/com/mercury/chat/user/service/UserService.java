package com.mercury.chat.user.service;

import java.util.Date;
import java.util.List;

import com.mercury.chat.user.entity.Message;
import com.mercury.chat.user.entity.User;

public interface UserService {
	
	boolean login(String userId, String passWord);
	
	List<User> getUserList(String userId);
	
	List<Message> find(String userId, Date from, Date to);
	
	int store(List<Message> message);
	
}
