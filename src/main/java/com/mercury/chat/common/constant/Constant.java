package com.mercury.chat.common.constant;

import io.netty.util.AttributeKey;

import java.util.Collection;

import com.mercury.chat.client.protocol.MessageListener;
import com.mercury.chat.user.User;


public interface Constant {
	
	public final static AttributeKey<User> userInfo = AttributeKey.valueOf("user");
	public final static AttributeKey<Collection<MessageListener>> listeners = AttributeKey.valueOf("listeners");
	
}

