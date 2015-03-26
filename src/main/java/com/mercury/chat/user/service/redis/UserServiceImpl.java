package com.mercury.chat.user.service.redis;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mercury.chat.common.OrderSummary;
import com.mercury.chat.common.ProductSummary;
import com.mercury.chat.common.struct.IHeader;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.repository.UserRepository;

public class UserServiceImpl  implements UserRepository {

	private static class SingletonHolder {
		private static final UserServiceImpl INSTANCE = new UserServiceImpl();
	}

	private UserServiceImpl() {
	}

	public static final UserServiceImpl getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public List<IMessage> find(String userId, Date from, Date to) {
		Jedis jedis = JedisPoolUtils.getJedis();
		Set<String> keys = jedis.keys("chat:from:*:to:"+userId);
		List<IMessage> messages = Lists.newArrayList();
		for(String key : keys){
			List<String> msgs = jedis.lrange(key, 0, -1);
			messages.addAll(Lists.transform(msgs, new Function<String, Message>(){
				@Override
				public Message apply(String input) {
					return new Message().body(input);
				}
			}));
		}
		return messages;
	}

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

	public List<User> getUserList(String userId) {
		return null;
	}

	@Override
	public IMessage select(String userId, Long shopId, int offset, int batchSize) {
		return null;
	}

	@Override
	public ProductSummary loadProductSummary(long productId) {
		return null;
	}

	@Override
	public OrderSummary loadOrderSummary(long orderId) {
		return null;
	}

	@Override
	public List<QuickReply> loadQuickReply(long saleId) {
		return null;
	}

	@Override
	public void updateQuickReply(long saleId, QuickReply quickReply) {
		
	}

	@Override
	public void deleteReply(long saleId, QuickReply quickReply) {
		
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
