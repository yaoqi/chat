package com.mercury.chat.common.constant;

import io.netty.util.AttributeKey;

import com.mercury.chat.user.entity.User;


public interface Constant {
	
	public final static AttributeKey<User> userInfo = AttributeKey.valueOf("user");
	
	//not used anymore
	//public final static AttributeKey<Collection<MessageListener>> listeners = AttributeKey.valueOf("listeners");
	
}

