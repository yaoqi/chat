package com.mercury.chat.client.protocol;

import static com.mercury.chat.common.MessageType.HISTORICAL_MESSAGE;

import com.mercury.chat.common.MessageType;

public class HistoricalMessageHandler extends SimpleMessageHandler {

	@Override
	protected MessageType _() {
		return HISTORICAL_MESSAGE;
	}
}
