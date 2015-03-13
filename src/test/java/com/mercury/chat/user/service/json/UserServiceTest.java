package com.mercury.chat.user.service.json;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.json.JsonHeader;
import com.mercury.chat.common.struct.json.JsonMessage;
import com.mercury.chat.user.service.UserService;
import com.mercury.chat.user.service.UserServiceImpl;

public class UserServiceTest {
	
	@Test
	public void testStoreMessage(){
		UserService userService = UserServiceImpl.getInstance();
		List<IMessage> messages = Lists.newArrayList();
		JsonMessage msg = new JsonMessage();
		JsonHeader header = new JsonHeader();
		header.setFrom("god");
		header.setTo("bigboy");
		msg.setHeader(header);
		msg.setBody("Hello, Boy");
		messages.add(msg);
		userService.store(messages);
	}
	
	@Test
	public void testFindMessage(){
		UserService userService = UserServiceImpl.getInstance();
		List<IMessage> messages = userService.find("god");
		System.out.println(messages.size());
	}
}
