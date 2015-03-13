package com.mercury.chat.common.constant;

import io.netty.util.AttributeKey;

import com.mercury.chat.user.User;


public interface Constant {
	
	public final static AttributeKey<User> userInfo = AttributeKey.valueOf("user");
	
}

