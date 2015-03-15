package com.mercury.chat.common.struct.protocol;

import com.mercury.chat.common.struct.IMessage;


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
		return (String) header.getAttachment().get("from");
	}

	@Override
	public String getTo() {
		return (String) header.getAttachment().get("to");
	}
}
