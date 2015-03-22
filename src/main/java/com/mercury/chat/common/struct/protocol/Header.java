package com.mercury.chat.common.struct.protocol;

import java.util.HashMap;
import java.util.Map;

import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.struct.IHeader;

public final class Header implements IHeader{

    private int version = 0xcafe0101;// message version

    private int length;// message length

    private long sessionID;// session id
    
    private int statusCode = StatusCode.OK.key();//status code

    private byte type;// message type

    private byte priority;// message priority
    
    private long requestId;
    
    private Map<String, Object> attachment = new HashMap<String, Object>(); // other information

    public Header requestId(long requestId){
    	this.requestId = requestId;
    	return this;
    }
    
    public long requestId(){
    	return requestId;
    }
    
    public int version() {
		return version;
	}
    
    public void version(int version){
    	this.version = version;
    }

	public int length() {
		return length;
	}

	public void length(int length) {
		this.length = length;
	}

	public long sessionID() {
		return sessionID;
	}

	public void sessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public byte type() {
		return type;
	}

	public byte priority() {
		return priority;
	}

	public void priority(byte priority) {
		this.priority = priority;
	}
	
	public Map<String, Object> attachment() {
		return attachment;
	}

	public void attachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}

	public Header type(byte value) {
		this.type = value;
		return this;
	}
	
	public Header statusCode(int statusCode){
		this.statusCode = statusCode;
		return this;
	}
	
	@Override
	public byte messageType() {
		return type;
	}
	
	public int statusCode() {
		return statusCode;
	}

	@Override
	public String getTo() {
		return (String) attachment.get("to");
	}

	@Override
	public String getFrom() {
		return (String) attachment.get("from");
	}
	
	public Header to(String to){
		attachment.put("to", to);
		return this;
	}
	
	public Header from(String from){
		attachment.put("from", from);
		return this;
	}
	
	public void setTo(String to){
		attachment.put("to", to);
	}
	
	public void setFrom(String from){
		attachment.put("from", from);
	}

	@Override
	public String toString() {
		return "Header [version=" + version + ", length=" + length
				+ ", sessionID=" + sessionID + ", statusCode=" + statusCode
				+ ", type=" + type + ", priority=" + priority + ", attachment="
				+ attachment+ "]";
	}

}
