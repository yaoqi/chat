package com.mercury.chat.common.struct.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;

import com.mercury.chat.common.constant.StatusCode;
import com.mercury.chat.common.struct.IHeader;
import static com.mercury.chat.common.constant.Constant.FROM;
import static com.mercury.chat.common.constant.Constant.TO;
import static com.mercury.chat.common.constant.Constant.FROM_USER;
import static com.mercury.chat.common.constant.Constant.TO_USER;

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
		return (String) attachment.get(TO);
	}

	@Override
	public String getFrom() {
		return (String) attachment.get(FROM);
	}
	
	public Header to(String to){
		attachment.put(TO, to);
		return this;
	}
	
	public Header from(String from){
		attachment.put(FROM, from);
		return this;
	}
	
	public void setTo(String to){
		attachment.put(TO, to);
	}
	
	public void setFrom(String from){
		attachment.put(FROM, from);
	}
	
	public void attach(Properties properties){
		if(MapUtils.isNotEmpty(properties)){
			Set<Entry<Object, Object>> entrySet = properties.entrySet();
			for(Entry<Object, Object> entry : entrySet){
				attachment().put((String)entry.getKey(), entry.getValue());
			}
		}
	}
	
	public void attach(String key, Object value){
		attachment().put(key, value);
	}
	
	public Object getAttachment(String key){
		return attachment().get(key);
	}
	
	public boolean hasAttachment(String key){
		return attachment().get(key) != null;
	}
	
	public void reverse(){
		Object from = attachment().remove(FROM);
		Object to = attachment().remove(TO);
		if(from!=null){
			attach(TO, from);
		}
		if(to!=null){
			attach(FROM, to);
		}
		Object fromUser = attachment().remove(FROM_USER);
		if(fromUser!=null){
			attach(TO_USER, fromUser);
		}
		Object toUser = attachment().remove(TO_USER);
		if(toUser!=null){
			attach(FROM_USER, toUser);
		}
	}

	@Override
	public String toString() {
		return "Header [version=" + version + ", length=" + length
				+ ", sessionID=" + sessionID + ", statusCode=" + statusCode
				+ ", type=" + type + ", priority=" + priority + ", attachment="
				+ attachment+ "]";
	}

	public String getFromUser() {
		return (String) attachment.get(FROM_USER);
	}

	public String getToUser() {
		return (String) attachment.get(TO_USER);
	}

	public void setFromUser(String user) {
		attachment.put(FROM_USER, user);
	}

	public void setToUser(String user) {
		attachment.put(TO_USER, user);
	}

}
