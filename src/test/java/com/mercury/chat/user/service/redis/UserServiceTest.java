package com.mercury.chat.user.service.redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.repository.UserRepository;
import com.mercury.chat.user.repository.redis.UserServiceImpl;


@Ignore //ignore these test cases based on redis
public class UserServiceTest {
	
	
	@Test
	public void testLoginSuccessfully(){
		UserRepository userService = UserServiceImpl.getInstance();
		boolean login = userService.login("google@google.com", "welcome1");
		assertTrue(login);
	}
	
	@Test
	public void testLoginFailed(){
		UserRepository userService = UserServiceImpl.getInstance();
		boolean login = userService.login("baidu@baidu.com", "pwd");
		assertFalse(login);
	}
	

	@Test
	public void testStoreMessage(){
		UserRepository userService = UserServiceImpl.getInstance();
		List<IMessage> messages = Lists.newArrayList();
		Message msg = new Message();
		Header header = new Header();
		header.type(MessageType.CHAT.value());
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
		UserRepository userService = UserServiceImpl.getInstance();
		String userId = null;
		Long shopId = null;
		int offset = 0;
		int batchSize = 0;
		IMessage message = userService.select(userId, shopId, offset, batchSize);
		assertNotNull(message);
	}
	
	
}
