package com.mercury.chat.codec.json;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.struct.json.JsonHeader;
import com.mercury.chat.common.struct.json.JsonMessage;

import static org.junit.Assert.assertEquals;

public class MessageCodecTest {
	
	@Test
	public void testMessageCodec(){
		JsonMessage msg = new JsonMessage();
		JsonHeader header = new JsonHeader();
		header.setType(MessageType.LOGIN.value());
		header.setFrom("god");
		msg.setHeader(header);
		msg.setBody("godisaboy");
		String jsonString = JSON.toJSONString(msg);
		
		JsonMessage message = JSON.parseObject(jsonString, JsonMessage.class);
		
		assertEquals(msg.getHeader().getType(), message.getHeader().getType());
		assertEquals(msg.getHeader().getFrom(), message.getHeader().getFrom());
		assertEquals(msg.getBody(), message.getBody());
		
	}
	
	@Test
	public void testChatCodec(){
		JsonMessage msg = new JsonMessage();
		JsonHeader header = new JsonHeader();
		header.setType(MessageType.CHAT.value());
		header.setFrom("bigboy");
		header.setTo("god");
		msg.setHeader(header);
		msg.setBody("Hi God!");
		String jsonString = JSON.toJSONString(msg);
		JsonMessage message = JSON.parseObject(jsonString, JsonMessage.class);
		
		assertEquals(msg.getHeader().getType(), message.getHeader().getType());
		assertEquals(msg.getHeader().getFrom(), message.getHeader().getFrom());
		assertEquals(msg.getBody(), message.getBody());
		
	}
	
}
