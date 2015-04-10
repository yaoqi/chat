package com.mercury.chat.common.struct.protocol;

import java.util.Properties;

import com.mercury.chat.common.struct.IMessage;
import com.mercury.chat.user.entity.ChatMessage;


public final class Message implements IMessage{

    private Header header;

    private Object body;

    public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Message [header=" + header + ", body=" + body + "]";
	}

	public Message header(Header header) {
		this.header = header;
		return this;
	}

	public Message body(Object object) {
		this.body = object;
		return this;
	}

	@Override
	public String getFrom() {
		return (String) header.getFrom();
	}

	@Override
	public String getTo() {
		return (String) header.getTo();
	}
	
	@Override
	public void from(String user) {
		header.setFrom(user);
	}

	@Override
	public void to(String user) {
		header.setFrom(user);
	}
	
	@Override
	public String getFromUser(){
		return header.getFromUser();
	}
	
	@Override
	public String getToUser(){
		return header.getToUser();
	}
	
	@Override
	public void fromUser(String user) {
		header.setFromUser(user);
	}

	@Override
	public void toUser(String user) {
		header.setToUser(user);
	}

	@Override
	public ChatMessage convert() {
		ChatMessage message = new ChatMessage();
		message.from(getFrom()).to(getTo()).message((String)getBody()).shopId((Long)header.getAttachment("shopId"));
		return message;
	}

	@Override
	public long getRequestId() {
		return header.requestId();
	}

	@Override
	public Message requestId(long requestId) {
		header.requestId(requestId);
		return this;
	}

	public void attach(Properties properties) {
		header.attach(properties);
	}
}
