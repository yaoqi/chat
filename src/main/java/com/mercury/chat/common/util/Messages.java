package com.mercury.chat.common.util;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class Messages {
	
	public static Message buildMessage(MessageType messageType) {
		return new Message().header(new Header().type(messageType.value()));
	}
	
	public static Message buildMessage(MessageType messageType, StatusCode statusCode) {
		return new Message().header(new Header().type(messageType.value()).statusCode(statusCode.key()));
	}
	
	public static Message buildMessage(MessageType messageType, StatusCode statusCode, Object body) {
		return new Message().header(new Header().type(messageType.value()).statusCode(statusCode.key())).body(body);
	}
	
	public static Message buildMessage(MessageType messageType, Object body) {
		return new Message().header(new Header().type(messageType.value())).body(body);
	}
	
	public static Message buildMessage(MessageType messageType,long requestId, Object body) {
		return new Message().header(new Header().type(messageType.value()).requestId(requestId)).body(body);
	}
	
	public static Message buildMessage(MessageType messageType, StatusCode statusCode, long requestId, Object body) {
		return new Message().header(new Header().type(messageType.value()).statusCode(statusCode.key()).requestId(requestId)).body(body);
	}
	
}
