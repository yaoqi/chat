package com.mercury.chat.client.impl;

import static com.mercury.chat.common.MessageType.CHAT;
import static com.mercury.chat.common.MessageType.CRUD;
import static com.mercury.chat.common.MessageType.HANDSHAKE;
import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;
import static com.mercury.chat.common.MessageType.LOGIN;
import static com.mercury.chat.common.MessageType.LOGOFF;
import static com.mercury.chat.common.constant.StatusCode.OK;
import static com.mercury.chat.common.exception.ErrorCode.LOGINED;
import static com.mercury.chat.common.exception.ErrorCode.NOT_CONNECTED;
import static com.mercury.chat.common.exception.ErrorCode.NOT_LOGINED;
import static com.mercury.chat.common.util.Channels.getListenbleHandler;
import static com.mercury.chat.common.util.Messages.buildMessage;
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
import com.mercury.chat.common.ConnectionListener;
import com.mercury.chat.common.MessageBox;
import com.mercury.chat.common.MessageListener;
import com.mercury.chat.common.OrderSummary;
import com.mercury.chat.common.ProductSummary;
import com.mercury.chat.common.constant.Operation;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.exception.ChatException;
import com.mercury.chat.common.handler.ListenbleHandler;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.User;

public class ChatClientImpl implements ChatClient {

	static final Logger logger = LogManager.getLogger(ChatClientImpl.class);

	private Channel channel;
	
	private User currentUser;
	
	private String host;
	
	private int port;
	
	private boolean connected = false;
	

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
			channel.writeAndFlush(buildMessage(LOGIN, user)).sync();//send login request to chat service
			
			MessageBox loginMessageBox = channel.pipeline().get(LoginAuthHandler.class).messageBox();
			Message responseMsg = loginMessageBox.get();//wait until received the login response.
			
			if(!OK.$(responseMsg)){
				throw new ChatException(StatusCode.valOf(responseMsg));
			}
			
			currentUser = user;
			
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
			channel.writeAndFlush(buildMessage(LOGOFF)).sync();
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
		checkAllNotNull(message);
		checkAllNotNull(message.getHeader());
		try {
			message.getHeader().from(currentUser.getUserId());
			channel.writeAndFlush(message).sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public void addMessageListener(MessageListener messageListener) {
		checkAllNotNull(messageListener);
		ListenbleHandler listenbleHandler = getListenbleHandler(channel, CHAT);
		if(listenbleHandler!= null){
			listenbleHandler.addMessageListener(messageListener);
		}
	}

	@Override
	public void removeListener(MessageListener messageListener) {
		checkAllNotNull(messageListener);
		ListenbleHandler listenbleHandler = getListenbleHandler(channel, CHAT);
		if(listenbleHandler!= null){
			listenbleHandler.removeMessageListener(messageListener);
		}
	}

	@Override
	public void setConnectionListener(ConnectionListener connectionListener) {
		checkAllNotNull(connectionListener);
		//FIXME need to implement this logic
		ListenbleHandler listenbleHandler = getListenbleHandler(channel, HANDSHAKE);
		if(listenbleHandler!= null){
			//need to refactor this logic because connection listener is an instance of 
			//message listener
			//listenbleHandler.addMessageListener(connectionListener);
		}
	}

	@Override
	public List<ChatMessage> loadHisChatMessage(long shopId, String userId, int offset, int batchSize) {
		validate();
		checkAllNotNull(shopId,userId,offset,batchSize);
		HisMsgRequest request = new HisMsgRequest(userId, shopId, offset, batchSize);
		try {
			channel.writeAndFlush(buildMessage(HISTORICAL_MESSAGE, request)).sync();
			MessageBox messageBox = channel.pipeline().get(HistoricalMessageHandler.class).messageBox();
			Message responseMsg = messageBox.get();
			
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
		return null;
	}

	@Override
	public OrderSummary loadOrderSummary(long orderId) {
		validate();
		return null;
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
		checkAllNotNull(saleId);
		QuickReplyRequest request = new QuickReplyRequest().saleId(saleId).operation(Operation.LOAD);
		try {
			channel.writeAndFlush(buildMessage(CRUD, request)).sync();
			MessageBox messageBox = channel.pipeline().get(CommonCRUDHandler.class).messageBox();
			Message responseMsg = messageBox.get();
			
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
		checkAllNotNull(saleId);
		QuickReplyRequest request = new QuickReplyRequest().saleId(saleId).operation(Operation.DELETE).quickReply(quickReply);
		try {
			channel.writeAndFlush(buildMessage(CRUD, request)).sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

	@Override
	public void deleteReply(long saleId, QuickReply quickReply) {
		validate();
		checkAllNotNull(saleId);
		QuickReplyRequest request = new QuickReplyRequest().saleId(saleId).operation(Operation.DELETE).quickReply(quickReply);
		try {
			channel.writeAndFlush(buildMessage(CRUD, request)).sync();
		} catch (InterruptedException e) {
			throw new ChatException(e);
		}
	}

}
