package com.mercury.chat.common;

import java.util.List;
import java.util.concurrent.Callable;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.repository.UserRepository;
import com.mercury.chat.user.repository.db.UserRepositoryImpl;

public class MessageStoreCallable implements Callable<Integer>{
	
	private List<IMessage> messages;
	
	public MessageStoreCallable(List<IMessage> messages) {
		super();
		this.messages = messages;
	}

	@Override
	public Integer call() throws Exception {
		UserRepository userService = UserRepositoryImpl.getInstance();
		return userService.store(messages);
	}

}
