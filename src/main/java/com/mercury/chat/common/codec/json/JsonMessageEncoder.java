package com.mercury.chat.common.codec.json;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.mercury.chat.common.struct.json.JsonHeader;
import com.mercury.chat.common.struct.json.JsonMessage;

public class JsonMessageEncoder extends MessageToMessageEncoder<JsonMessage>{

	private boolean serverSide;

	public JsonMessageEncoder(boolean serverSide) {
		super();
		this.serverSide = serverSide;
	}

	@Override
	protected void encode(ChannelHandlerContext context, JsonMessage message, List<Object> out) throws Exception {
		if(serverSide){
			JsonHeader header = message.getHeader();
			ChannelId id = context.pipeline().channel().id();
			header.setSessionID(id.asShortText());
		}
		String jsonMsg = JSON.toJSONString(message);
		out.add(jsonMsg);
	}

}
