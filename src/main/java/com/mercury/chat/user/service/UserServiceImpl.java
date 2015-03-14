package com.mercury.chat.user.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mercury.chat.common.struct.IHeader;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.json.JsonMessage;
import com.mercury.chat.user.User;
import com.mercury.chat.user.service.storer.JedisPoolUtils;

public class UserServiceImpl implements UserService {

	private static class SingletonHolder {
		private static final UserServiceImpl INSTANCE = new UserServiceImpl();
	}

	private UserServiceImpl() {
	}

	public static final UserServiceImpl getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	@Override
	public List<IMessage> find(String userId) {
		Jedis jedis = JedisPoolUtils.getJedis();
		Set<String> keys = jedis.keys("chat:from:*:to:"+userId);
		List<IMessage> messages = Lists.newArrayList();
		for(String key : keys){
			List<String> msgs = jedis.lrange(key, 0, -1);
			messages.addAll(Lists.transform(msgs, new Function<String, JsonMessage>(){
				@Override
				public JsonMessage apply(String input) {
					return new JsonMessage().body(input);
				}
			}));
		}
		return messages;
	}

	@Override
	public int store(List<IMessage> messages) {
		Jedis jedis = JedisPoolUtils.getJedis();
		for(IMessage msg : messages){
			IHeader header = msg.getHeader();
			Object body = msg.getBody();
			if(body instanceof String){
				jedis.lpush("chat:from:"+header.getFrom()+":to:"+header.getTo(), (String)body);
			}else{
				//FIXME need to implement this logic via string byte array to redis.
			}
		}
		return messages.size();
	}

	@Override
	public boolean login(String userName, String passWord) {
		Jedis jedis = JedisPoolUtils.getJedis();
		Boolean hexists = jedis.hexists("user", userName);
		if(!hexists){
			//user not existed.
			return false;
		}
		String pwd = jedis.hget("user", userName);
		//check whether password is wrong or not
		return StringUtils.equals(passWord, pwd);
	}

	@Override
	public List<User> getUserList(String userId) {
		return null;
	}
	
}
