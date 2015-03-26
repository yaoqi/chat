package com.mercury.chat.common;

import java.util.List;
import java.util.concurrent.Callable;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.repository.MockUserService;
import com.mercury.chat.user.repository.UserRepository;

public class MessageStoreCallable implements Callable<Integer>{
	
	private List<IMessage> messages;
	
	public MessageStoreCallable(List<IMessage> messages) {
		super();
		this.messages = messages;
	}

	@Override
	public Integer call() throws Exception {
		UserRepository userService = MockUserService.getInstance();
		return userService.store(messages);
	}

}
