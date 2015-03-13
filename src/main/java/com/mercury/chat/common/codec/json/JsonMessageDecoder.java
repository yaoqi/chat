package com.mercury.chat.common.codec.json;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.mercury.chat.common.struct.json.JsonMessage;

public class JsonMessageDecoder extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext context, String message, List<Object> out) throws Exception {
		JsonMessage msg = JSON.parseObject(message, JsonMessage.class);
		out.add(msg);
	}

}
