package com.mercury.chat.common.struct.json;

import java.util.HashMap;
import java.util.Map;

import com.mercury.chat.common.struct.IHeader;

public final class JsonHeader implements IHeader{

    private Integer version = 0xcafe0101;//message version

    private Byte type;//message type

    private Integer length;// message length

    private String sessionID;// session id 

    private Byte priority;// priority
    
    private String from;//message from
    
    private String to;//message to

    private Map<String, Object> attachment = new HashMap<String, Object>();
    
    public JsonHeader type(Byte type){
    	this.type = type;
    	return this;
    }
    
    public JsonHeader from(String from){
    	this.from = from;
    	return this;
    }
    
    public JsonHeader to(String to){
    	this.to = to;
    	return this;
    }

    public JsonHeader sessionId(String sessionId){
    	this.sessionID = sessionId;
    	return this;
    }
    
    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public Byte getPriority() {
		return priority;
	}

	public void setPriority(Byte priority) {
		this.priority = priority;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Map<String, Object> getAttachment() {
		return attachment;
	}

	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}

	@Override
	public byte getMessageType() {
		return type;
	}

	/*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
		return "Header [version=" + version + ", length=" + length
			+ ", sessionID=" + sessionID + ", type=" + type + ", priority="
			+ priority + ", attachment=" + attachment + "]";
    }


}
