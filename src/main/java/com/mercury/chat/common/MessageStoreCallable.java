package com.mercury.chat.common;

import java.util.List;
import java.util.concurrent.Callable;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.service.UserService;
import com.mercury.chat.user.service.storer.MockUserService;

public class MessageStoreCallable implements Callable<Integer>{
	
	private List<IMessage> messages;
	
	public MessageStoreCallable(List<IMessage> messages) {
		super();
		this.messages = messages;
	}

	@Override
	public Integer call() throws Exception {
		UserService userService = MockUserService.getInstance();
		return userService.store(messages);
	}

}
