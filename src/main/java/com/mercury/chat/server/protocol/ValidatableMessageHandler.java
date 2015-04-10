package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.constant.StatusCode.NOT_LOGIN;
import static com.mercury.chat.common.util.Channels.has;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;

import com.mercury.chat.common.constant.Constant;
import com.mercury.chat.common.handler.impl.SimpleMessageHandler;
import com.mercury.chat.common.struct.protocol.Message;

public abstract class ValidatableMessageHandler extends SimpleMessageHandler {

	@Override
	protected boolean validate(ChannelHandlerContext ctx, Message msg) throws Exception {
		if(!has(ctx.channel(), Constant.userInfo)){
    		ctx.writeAndFlush(buildMessage(_(), NOT_LOGIN).requestId(msg.getRequestId()));
    		return false;
    	}
		return true;
	}

}
