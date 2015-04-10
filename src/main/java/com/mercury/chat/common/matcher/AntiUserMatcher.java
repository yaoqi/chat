package com.mercury.chat.common.matcher;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;
import io.netty.util.Attribute;

import org.apache.commons.lang.StringUtils;

import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.user.entity.User;

/**
 * Chat channel matcher
 * this class is used to find the matched 
 * channel to send message.
 * @author Yao
 *
 */
public class AntiUserMatcher implements ChannelMatcher {
	
	private String targetUserId;
	
	public AntiUserMatcher(String targetUserId) {
		this.targetUserId = targetUserId;
	}

	@Override
	public boolean matches(Channel channel) {
		Attribute<User> attr = channel.attr(Constant.userInfo);
		User user = attr.get();
		if (user == null) {
			return false;
		}
		return !StringUtils.equals(user.getUserId(), targetUserId);
	}

}
