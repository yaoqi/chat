package com.mercury.chat;

import static com.mercury.chat.common.constant.Constant.userInfo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import io.netty.util.Attribute;
import io.netty.util.DefaultAttributeMap;

import org.junit.Test;

import com.mercury.chat.user.entity.User;

public class DefaultAttributeMapTest {
	
	@Test
	public void testSetNullThenHas(){
		DefaultAttributeMap am = new DefaultAttributeMap();
		Attribute<User> attr = am.attr(userInfo);
		attr.setIfAbsent(new User("111","222"));
		assertTrue(am.hasAttr(userInfo));
		attr.remove();
		assertFalse(am.hasAttr(userInfo));
	}
	
}
