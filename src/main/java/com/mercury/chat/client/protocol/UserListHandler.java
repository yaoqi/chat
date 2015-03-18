package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.USER_LIST;

import com.mercury.chat.common.MessageType;

public class UserListHandler extends SimpleMessageHandler {

	@Override
	protected MessageType _() {
		return USER_LIST;
	}
}
