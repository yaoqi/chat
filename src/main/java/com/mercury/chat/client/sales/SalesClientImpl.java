package com.mercury.chat.client.sales;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
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
import static com.mercury.chat.common.util.Preconditions.checkAllNotNull;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import static com.mercury.chat.common.util.Channels.syncSendMessage;

import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.client.ChatClient;
import com.mercury.chat.client.Connection;
import com.mercury.chat.client.protocol.CommonCRUDHandler;
import com.mercury.chat.client.protocol.ExceptionHandler;
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
import com.mercury.chat.common.constant.Operation;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.handler.ListenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.OrderSummary;
import com.mercury.chat.user.entity.ProductSummary;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.User;

public class SalesClientImpl implements ChatClient {

	static final Logger logger = LogManager.getLogger(SalesClientImpl.class);
	
	private Channel channel;
	
	private User currentUser;
	
	private String host;
	
	private int port;
	
	private boolean connected = false;

	public SalesClientImpl(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		connect();
	}

	public void connect(){
		channel = new SecureChatClient().connectInner(host, port);
		connected = true;
	}
	
	@Override
	public Connection connect(String host, int port) {
		checkAllNotNull(host, port);
		return SecureChatClient.connect(host, port);
	}

	@Override
	public void login(String userName, String password, Properties properties) throws ChatException {
		checkAllNotNull(userName, password);
		if(!connected){
			throw new ChatException(NOT_CONNECTED);
		}
		if (currentUser != null) {
			throw new ChatException(LOGINED);
		}
		try {
			User user = new User(userName,password);
			Message message = ClientMessages.getInstance().buildMsg(LOGIN, user);
			message.getHeader().attach(properties);
			
			Message responseMsg = syncSendMessage(LoginAuthHandler.class, channel, message);//wait until received the login response.
			
			if(!OK.$(responseMsg)){
				throw new ChatException(StatusCode.valOf(responseMsg));
			}
			
			currentUser = (User) responseMsg.getBody();
			
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
			currentUser = null;
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
		checkNotNull(message,"message can't be null");
		checkNotNull(message.getHeader(), "header can't be null");
		checkArgument(CHAT.$(message), "message type must be chat");
		try {
			if(currentUser.isSales()){
				checkArgument(message.getHeader().hasAttachment(TO_USER), "toUser can't be null");
			}else{
				checkArgument(message.getHeader().hasAttachment(FROM_USER), "fromUser can't be null");
			}
			message.getHeader().from(currentUser.getUserId());
			channel.writeAndFlush(message).sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public void addMessageListener(MessageListener messageListener) {
		checkNotNull(messageListener, "messageListener can't be null");
		ListenbleHandler listenbleHandler = getListenbleHandler(channel, CHAT);
		if(listenbleHandler!= null){
			listenbleHandler.addMessageListener(messageListener);
		}
	}

	@Override
	public void removeListener(MessageListener messageListener) {
		checkNotNull(messageListener, "messageListener can't be null");
		ListenbleHandler listenbleHandler = getListenbleHandler(channel, CHAT);
		if(listenbleHandler!= null){
			listenbleHandler.removeMessageListener(messageListener);
		}
	}

	@Override
	public void setConnectionListener(final ConnectionListener connectionListener) {
		checkNotNull(connectionListener, "connectionListener can't be null");
		channel.pipeline().get(ExceptionHandler.class).setConnectionListener(connectionListener);
		channel.closeFuture().addListener(new ChannelFutureListener(){
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				connectionListener.onClose();
			}
		});
	}

	@Override
	public List<ChatMessage> loadHisChatMessage(long shopId, String userId, int offset, int batchSize) {
		validate();
		checkAllNotNull(shopId,userId,offset,batchSize);
		checkArgument(shopId > 0, "shopId must greater than zero");
		checkArgument(offset >= 0, "offset mustn't less than zero");
		checkArgument(batchSize > 0, "batchSize mustn greater than zero");
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
		checkNotNull(productId, "productId can't be null");
		checkArgument(productId > 0, "productId must greater than zero");
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
		checkNotNull(orderId, "orderId can't be null");
		checkArgument(orderId > 0, "orderId must greater than zero");
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
		if (currentUser == null) {
			throw new ChatException(NOT_LOGINED);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public List<QuickReply> loadQuickReply(long saleId) {
		validate();
		checkNotNull(saleId, "saleId can't be null");
		checkArgument(saleId > 0, "saleId must greater than zero");
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
		checkAllNotNull(saleId, quickReply);
		checkArgument(saleId > 0, "saleId must greater than zero");
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
		checkAllNotNull(saleId, quickReply);
		checkArgument(saleId > 0, "saleId must greater than zero");
		QuickReplyRequest request = new QuickReplyRequest().saleId(saleId).operation(Operation.DELETE).quickReply(quickReply);
		try {
			channel.writeAndFlush(ClientMessages.getInstance().buildMsg(CRUD, request)).sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public List<User> getOnlineUsers(long shopId) {
		validate();
		checkArgument(shopId > 0, "shopId must greater than zero");
		try {
			Message responseMsg = syncSendMessage(UserListHandler.class, channel, ClientMessages.getInstance().buildMsg(USER_LIST, shopId));
			List<User> onlineUsers = (List<User>) responseMsg.getBody();
			return onlineUsers;
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

}
