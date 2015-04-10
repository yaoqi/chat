package com.mercury.chat.client.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.mercury.chat.client.resource.ResourceManager.getMessage;
import static com.mercury.chat.common.MessageType.CHAT;
import static com.mercury.chat.common.MessageType.CRUD;
import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;
import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.MessageType.LOGOFF;
import static com.mercury.chat.common.MessageType.USER_LIST;
import static com.mercury.chat.common.constant.Constant.FROM_USER;
import static com.mercury.chat.common.constant.Constant.TO_USER;
import static com.mercury.chat.common.constant.StatusCode.OK;
import static com.mercury.chat.common.exception.ErrorCode.LOGINED;
import static com.mercury.chat.common.exception.ErrorCode.NOT_CONNECTED;
import static com.mercury.chat.common.exception.ErrorCode.NOT_LOGINED;
import static com.mercury.chat.common.util.Channels.getListenbleHandler;
import static com.mercury.chat.common.util.Channels.syncSendMessage;
import static com.mercury.chat.common.util.Preconditions.checkAllNotNull;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.ChatClient;
import com.mercury.chat.client.Connection;
import com.mercury.chat.client.protocol.CommonCRUDHandler;
import com.mercury.chat.client.protocol.HistoricalMessageHandler;
import com.mercury.chat.client.protocol.LoginAuthHandler;
import com.mercury.chat.client.protocol.SecureChatClient;
import com.mercury.chat.client.protocol.UserListHandler;
import com.mercury.chat.client.utils.ClientMessages;
import com.mercury.chat.common.ConnectionListener;
import com.mercury.chat.common.HisMsgRequest;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.OrderRequest;
import com.mercury.chat.common.ProductRequest;
import com.mercury.chat.common.QuickReplyRequest;
import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.constant.Operation;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.handler.ListenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.common.util.Channels;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.OrderSummary;
import com.mercury.chat.user.entity.ProductSummary;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.User;

public class ChatClientImpl implements ChatClient {

	static final Logger logger = LogManager.getLogger(ChatClientImpl.class);
	
	private boolean connected = false;
	
	private Channel channel;
	
	private SecureChatClient realClient;

	public ChatClientImpl(String host, int port) {
		super();
		realClient = new SecureChatClient();
		channel = realClient.connectInner(host, port);
		connected = true;
	}
	
	public ChatClientImpl(){
		realClient = new SecureChatClient();
	}
	
	@Override
	public Connection connect(String host, int port) {
		checkAllNotNull(host, port);
		channel = realClient.connectInner(host, port);
		connected = true;
		return SecureChatClient.connect(host, port);
	}

	@Override
	public void login(String userName, String password, Properties properties) throws ChatException {
		checkAllNotNull(userName, password);
		if(!connected){
			throw new ChatException(NOT_CONNECTED);
		}
		
		if (channel != null && Channels.has(channel, Constant.userInfo)) {
			throw new ChatException(LOGINED);
		}
		try {
			User user = new User(userName,password);
			Message message = ClientMessages.getInstance().buildMsg(LOGIN, user, properties);
			Message responseMsg = syncSendMessage(LoginAuthHandler.class, channel, message);//wait until received the login response.
			
			if(!OK.$(responseMsg)){
				throw new ChatException(StatusCode.valOf(responseMsg));
			}
			
			User userTmp = (User) responseMsg.getBody();
			if (channel != null) {
				Channels.set(channel, Constant.userInfo, userTmp);
			}
			
			logger.info(responseMsg);
		} catch (InterruptedException e) {
			logger.error(e);
			throw new ChatException(e);
		}
	}

	@Override
	public void logout() {
		validate();
		try {
			channel.writeAndFlush(ClientMessages.getInstance().buildMsg(LOGOFF)).sync();
			Channels.remove(channel, Constant.userInfo);
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void sendMessage(Message message) throws ChatException {
		validate();
		checkNotNull(message,getMessage("message.notNull"));
		checkNotNull(message.getHeader(), getMessage("header.notNull"));
		checkArgument(CHAT.$(message), getMessage("messageType.invalid"));
		try {
			User currentUser = Channels.get(channel, Constant.userInfo);
			if(currentUser.isSales()){
				checkArgument(message.getHeader().hasAttachment(TO_USER), getMessage("toUser.notNull"));
			}else{
				checkArgument(message.getHeader().hasAttachment(FROM_USER), getMessage("fromUser.notNull"));
			}
			message.getHeader().from(currentUser.getUserId());
			channel.writeAndFlush(message).sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public void addMessageListener(MessageListener messageListener) {
		checkNotNull(messageListener, getMessage("messageListener.notNull"));
		ListenbleHandler listenbleHandler = getListenbleHandler(channel, CHAT);
		if(listenbleHandler!= null){
			listenbleHandler.addMessageListener(messageListener);
		}
	}

	@Override
	public void removeListener(MessageListener messageListener) {
		checkNotNull(messageListener, getMessage("messageListener.notNull"));
		ListenbleHandler listenbleHandler = getListenbleHandler(channel, CHAT);
		if(listenbleHandler!= null){
			listenbleHandler.removeMessageListener(messageListener);
		}
	}

	@Override
	public void setConnectionListener(final ConnectionListener connectionListener) {
		checkNotNull(connectionListener, getMessage("connectionListener.notNull"));
		realClient.setConnectionListener(connectionListener);
	}

	@Override
	public List<ChatMessage> loadHisChatMessage(long shopId, String userId, int offset, int batchSize) {
		validate();
		checkAllNotNull(userId);
		checkArgument(shopId > 0, getMessage("shopId.invalid"));
		checkArgument(offset >= 0, getMessage("offset.invalid"));
		checkArgument(batchSize > 0, getMessage("batchSize.invalid"));
		HisMsgRequest request = new HisMsgRequest(userId, shopId, offset, batchSize);
		try {
			Message responseMsg = syncSendMessage(HistoricalMessageHandler.class, channel, ClientMessages.getInstance().buildMsg(HISTORICAL_MESSAGE, request));
			@SuppressWarnings("unchecked")
			List<ChatMessage> messages = (List<ChatMessage>) responseMsg.getBody();
			return messages;
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public ProductSummary loadProductSummary(long productId) {
		validate();
		checkArgument(productId > 0, getMessage("productId.invalid"));
		try {
			ProductRequest request = new ProductRequest(productId);
			Message responseMsg = syncSendMessage(CommonCRUDHandler.class, channel, ClientMessages.getInstance().buildMsg(CRUD, request));
			ProductSummary product = (ProductSummary) responseMsg.getBody();
			return product;
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public OrderSummary loadOrderSummary(long orderId) {
		validate();
		checkArgument(orderId > 0, getMessage("orderId.invalid"));
		try {
			OrderRequest request = new OrderRequest(orderId);
			Message responseMsg = syncSendMessage(CommonCRUDHandler.class, channel, ClientMessages.getInstance().buildMsg(CRUD, request));
			OrderSummary order = (OrderSummary) responseMsg.getBody();
			return order;
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	private void validate() {
		if(!connected){
			throw new ChatException(NOT_CONNECTED);
		}
		
		if (channel == null || !Channels.has(channel, Constant.userInfo)) {
			throw new ChatException(NOT_LOGINED);
		}
	}

	@Override
	public List<QuickReply> loadQuickReply(long saleId) {
		validate();
		checkArgument(saleId > 0, getMessage("saleId.invalid"));
		QuickReplyRequest request = new QuickReplyRequest().saleId(saleId).operation(Operation.LOAD);
		try {
			Message responseMsg = syncSendMessage(CommonCRUDHandler.class, channel, ClientMessages.getInstance().buildMsg(CRUD, request));
			@SuppressWarnings("unchecked")
			List<QuickReply> quickReplies = (List<QuickReply>) responseMsg.getBody();
			return quickReplies;
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public void updateQuickReply(long saleId, QuickReply quickReply) {
		validate();
		checkAllNotNull(quickReply);
		checkArgument(saleId > 0, getMessage("saleId.invalid"));
		QuickReplyRequest request = new QuickReplyRequest().saleId(saleId).operation(Operation.DELETE).quickReply(quickReply);
		try {
			channel.writeAndFlush(ClientMessages.getInstance().buildMsg(CRUD, request)).sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public void deleteReply(long saleId, QuickReply quickReply) {
		validate();
		checkAllNotNull(quickReply);
		checkArgument(saleId > 0, getMessage("saleId.invalid"));
		QuickReplyRequest request = new QuickReplyRequest().saleId(saleId).operation(Operation.DELETE).quickReply(quickReply);
		try {
			channel.writeAndFlush(ClientMessages.getInstance().buildMsg(CRUD, request)).sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getOnlineUsers(long shopId) {
		validate();
		checkArgument(shopId > 0, getMessage("saleId.invalid"));
		try {
			Message responseMsg = syncSendMessage(UserListHandler.class, channel, ClientMessages.getInstance().buildMsg(USER_LIST, shopId));
			List<User> onlineUsers = (List<User>) responseMsg.getBody();
			return onlineUsers;
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

}
