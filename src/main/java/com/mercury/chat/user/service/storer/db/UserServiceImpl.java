package com.mercury.chat.user.service.storer.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.dao.MessageMapper;
import com.mercury.chat.user.dao.UserMapper;
import com.mercury.chat.user.entity.ChatMessage;
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
		return userMapper.select(userId, passWord) != null;
	}

	@Override
	public List<User> getUserList(String userId) {
		return null;
	}

	@Override
	public IMessage select(String userId, Long shopId, int offset, int batchSize) {
		List<ChatMessage> messages = messageMapper.select(userId, shopId, offset, batchSize);
		Message msg = new Message().body(messages);
		return msg;
	}

	@Override
	public int store(List<IMessage> messages) {
		for(IMessage message : messages){
			messageMapper.insert(message.convert());
		}
		return messages.size();
	}

}
