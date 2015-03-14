package com.mercury.chat.user.service.json;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.mercury.chat.user.service.UserService;
import com.mercury.chat.user.service.UserServiceImpl;
import com.mercury.chat.user.service.storer.redis.JedisPoolUtils;

public class LoginServiceTest {
	
	@Before
	public void insertUser(){
		Jedis jedis = JedisPoolUtils.getJedis();
		jedis.hset("user", "god", "pwd");
		jedis.hset("user", "bigboy", "pwd");
	}
	
	@Test
	public void testLoginSuccessfully(){
		UserService userService = UserServiceImpl.getInstance();
		boolean login = userService.login("god", "pwd");
		assertTrue(login);
	}
	
	@Test
	public void testLoginFailed(){
		UserService userService = UserServiceImpl.getInstance();
		boolean login = userService.login("god", "XXX");
		assertFalse(login);
	}
	
}
