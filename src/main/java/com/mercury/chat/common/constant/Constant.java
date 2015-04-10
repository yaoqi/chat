package com.mercury.chat.common.constant;

import io.netty.util.AttributeKey;

import com.mercury.chat.user.entity.User;


public interface Constant {
	
	public final static AttributeKey<User> userInfo = AttributeKey.valueOf("user");
	
	public final static String FROM = "from";
	
	public final static String TO = "to";
	
	public final static String FROM_USER = "fromUser";
	
	public final static String TO_USER = "toUser";
	
	public final static String SHOP_ID = "shopId";
	
	//not used anymore
	//public final static AttributeKey<Collection<MessageListener>> listeners = AttributeKey.valueOf("listeners");
	
}

