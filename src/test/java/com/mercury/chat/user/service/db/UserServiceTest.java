package com.mercury.chat.user.service.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.common.test.DataPrepare2;
import com.mercury.chat.common.test.DbType;
import com.mercury.chat.common.test.TestRuleImpl2;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.service.UserService;

public class UserServiceTest {
	
	String[] locations = {"test-context.xml"};

	@Rule
	public TestRule rule = new TestRuleImpl2(locations, this);
	
	@Autowired
	private UserService userService;

	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginSuccessfully(){
		boolean login = userService.login("google@gmail.com", "welcome1");
		assertTrue(login);
	}
	
	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testLoginFailed(){
		boolean login = userService.login("baidu@baidu.com", "pwd");
		assertFalse(login);
	}
	

	@Test
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testStoreMessage(){
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
	@DataPrepare2(dbTypes = {DbType.H2}, schema = "CHAT", domainClasses = {User.class})
	public void testFindMessage(){
		IMessage message = userService.select("baidu@baidu.com", 1L, 0, 5);
		List<ChatMessage> messages = (List<ChatMessage>) message.getBody();
		assertNotNull(message);
		assertEquals(5, messages.size());
	}
	
	
}
