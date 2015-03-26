package com.mercury.chat.user.repository.db;

import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;
import static com.mercury.chat.common.util.Messages.buildMessage;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.dao.MessageMapper;
import com.mercury.chat.user.dao.QuickReplyMapper;
import com.mercury.chat.user.dao.UserMapper;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.OrderSummary;
import com.mercury.chat.user.entity.ProductSummary;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.QuickReplyTemplate;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.repository.UserRepository;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Transactional(propagation=Propagation.REQUIRED)
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private QuickReplyMapper quickReplyMapper;
	
	private static class SingletonHolder {
		private static UserRepositoryImpl INSTANCE = null;
	}

	public UserRepositoryImpl() {
	}

	public static final UserRepositoryImpl getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	@PostConstruct
	private void init(){
		SingletonHolder.INSTANCE = this;
	}
	
	@Override
	public boolean login(String userId, String passWord) {
		return userMapper.select(userId, passWord) != null;
	}

	@Override
	public List<User> getUserList(String userId) {
		return null;
	}

	@Override
	public IMessage select(String userId, Long shopId, int offset, int batchSize) {
		List<ChatMessage> messages = messageMapper.select(userId, shopId, offset, batchSize);
		return buildMessage(HISTORICAL_MESSAGE, messages);
	}

	@Override
	public int store(List<IMessage> messages) {
		List<ChatMessage> msgs = Lists.newArrayList();
		for(IMessage message : messages){
			msgs.add(message.convert());
		}
		return messageMapper.insertAll(msgs);
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
		QuickReplyTemplate example = new QuickReplyTemplate();
		example.createCriteria().andSaleidEqualTo(saleId);
		return quickReplyMapper.select(example );
	}

	@Override
	public void updateQuickReply(long saleId, QuickReply quickReply) {
		QuickReplyTemplate example = new QuickReplyTemplate();
		example.createCriteria().andSaleidEqualTo(saleId).andUuidEqualTo(quickReply.getUuid());
		quickReplyMapper.updateByExampleSelective(quickReply, example);
	}

	@Override
	public void deleteReply(long saleId, QuickReply quickReply) {
		QuickReplyTemplate example = new QuickReplyTemplate();
		example.createCriteria().andSaleidEqualTo(saleId).andUuidEqualTo(quickReply.getUuid());
		quickReplyMapper.deleteByExample(example);
	}

	@Override
	public User getUser(String userId) {
		return userMapper.findByUserId(userId);
	}

}
