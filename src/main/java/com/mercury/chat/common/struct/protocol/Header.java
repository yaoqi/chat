package com.mercury.chat.common.struct.protocol;

import java.util.HashMap;
import java.util.Map;

import com.mercury.chat.common.struct.IHeader;

public final class Header implements IHeader{

    private int version = 0xcafe0101;// message version

    private int length;// message length

    private long sessionID;// session id

    private byte type;// message type

    private byte priority;// message priority
    
    private Map<String, Object> attachment = new HashMap<String, Object>(); // other information

    public int getVersion() {
		return version;
	}
    
	public void setVersion(int version) {
		this.version = version;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}
	
	public Map<String, Object> getAttachment() {
		return attachment;
	}

	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}

	public Header type(byte value) {
		this.type = value;
		return this;
	}
	
	@Override
	public byte getMessageType() {
		return type;
	}
	
	@Override
	public String getTo() {
		//FIXME
		return null;
	}

	@Override
	public String getFrom() {
		//FIXME
		return null;
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
