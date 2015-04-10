package com.mercury.chat.client.utils;

import static com.mercury.chat.common.util.Messages.buildMessage;

import java.util.Properties;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.common.util.IdentifierUtils;
import com.mercury.chat.user.entity.User;


public class ClientMessages {
	
	private IdentifierUtils identifierUtils = new IdentifierUtils();
	
	private static class SingletonHolder {
		private static final ClientMessages INSTANCE = new ClientMessages();
	}

	private ClientMessages() {
	}

	public static final ClientMessages getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public Message buildMsg(MessageType messageType, Object body) {
		Message message = buildMessage(messageType, body);
		setRequestId(message);
		return message;
	}
	
	public Message buildMsg(MessageType messageType) {
		Message message = buildMessage(messageType);
		setRequestId(message);
		return message;
	}
	
	public Message buildMsg(MessageType login, Object body, Properties properties) {
		Message msg = buildMsg(login, body);
		msg.attach(properties);
		return msg;
	}
	
	public Message buildMsg(MessageType messageType, StatusCode statusCode) {
		return buildMessage(messageType, statusCode);
	}
	
	public Message buildMsg(MessageType messageType, StatusCode statusCode, Object body) {
		return buildMessage(messageType, statusCode, body);
	}
	
	private void setRequestId(Message message) {
		message.getHeader().requestId(identifierUtils.generateLongID());
	}
	
}
