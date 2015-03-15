package com.mercury.chat.user.service.storer.db;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.dao.MessageMapper;
import com.mercury.chat.user.dao.UserMapper;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.service.UserService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MessageMapper messageMapper;
	
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
