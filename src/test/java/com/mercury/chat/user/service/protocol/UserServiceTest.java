package com.mercury.chat.user.service.protocol;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.service.UserService;
import com.mercury.chat.user.service.storer.redis.UserServiceImpl;

public class UserServiceTest {
	
	
	@Test
	public void testLoginSuccessfully(){
		UserService userService = UserServiceImpl.getInstance();
		boolean login = userService.login("google@google.com", "welcome1");
		assertTrue(login);
	}
	
	@Test
	public void testLoginFailed(){
		UserService userService = UserServiceImpl.getInstance();
		boolean login = userService.login("baidu@baidu.com", "pwd");
		assertFalse(login);
	}
	

	@Test
	public void testStoreMessage(){
		UserService userService = UserServiceImpl.getInstance();
		List<IMessage> messages = Lists.newArrayList();
		Message msg = new Message();
		Header header = new Header();
		header.setType(MessageType.CHAT.value());
		header.setFrom("google@google.com");
		header.setTo("baidu@baidu.com");
		msg.setHeader(header);
		msg.setBody("Hello, baidu");
		messages.add(msg);
		int count = userService.store(messages);
		assertEquals(1, count);
	}
	
	@Test
	public void testFindMessage(){
		UserService userService = UserServiceImpl.getInstance();
		List<IMessage> messages = userService.find("god");
		System.out.println(messages.size());
	}
	
	
}
