package com.mercury.chat.common.util;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class MessageUtil {
	
	public static Message buildMessage(MessageType messageType, StatusCode statusCode, Object body) {
		return new Message().header(new Header().type(messageType.value())
												.statusCode(statusCode.getKey()))
							.body(body);
	}
	
	
}
