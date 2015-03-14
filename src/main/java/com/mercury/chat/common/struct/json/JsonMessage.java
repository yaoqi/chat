package com.mercury.chat.common.struct.json;

import com.mercury.chat.common.struct.IMessage;


public final class JsonMessage implements IMessage{

    private JsonHeader header;
 
    private String body;
    
    public JsonMessage header(JsonHeader header){
    	this.header = header;
    	return this;
    }
    
    public JsonMessage body(String body){
    	this.body = body;
    	return this;
    }

    /**
     * @return the header
     */
    public final JsonHeader getHeader() {
    	return header;
    }

    /**
     * @param header
     *            the header to set
     */
    public final void setHeader(JsonHeader header) {
    	this.header = header;
    }

    /**
     * @return the body
     */
    public final String getBody() {
    	return body;
    }

    /**
     * @param body
     *            the body to set
     */
    public final void setBody(String body) {
    	this.body = body;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	return "Message [header=" + header + "]";
    }

	@Override
	public String getFrom() {
		return header.getFrom();
	}

	@Override
	public String getTo() {
		return header.getTo();
	}
}
