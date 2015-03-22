package com.mercury.chat.user.service.db;

import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;
import static com.mercury.chat.common.util.Messages.buildMessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.chat.common.OrderSummary;
import com.mercury.chat.common.ProductSummary;
import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.dao.MessageMapper;
import com.mercury.chat.user.dao.QuickReplyMapper;
import com.mercury.chat.user.dao.UserMapper;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.QuickReplyTemplate;
import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.service.UserService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private QuickReplyMapper quickReplyMapper;
	
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
		for(IMessage message : messages){
			messageMapper.insert(message.convert());
		}
		return messages.size();
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
		example.createCriteria().andSaleidEqualTo(saleId);
		quickReplyMapper.updateByExampleSelective(quickReply, example);
	}

	@Override
	public void deleteReply(long saleId, QuickReply quickReply) {
		QuickReplyTemplate example = new QuickReplyTemplate();
		example.createCriteria().andSaleidEqualTo(saleId).andUuidEqualTo(quickReply.getUuid());
		quickReplyMapper.deleteByExample(example);
	}

}
