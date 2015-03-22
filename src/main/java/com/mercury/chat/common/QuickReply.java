package com.mercury.chat.common;

import java.io.Serializable;

public class QuickReply implements Serializable{
	
	private static final long serialVersionUID = 1845589560505930462L;
	
	private long uuid;
	private long saleId;
	private String quickMessage;
	
	public long getUuid() {
		return uuid;
	}
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}
	public long getSaleId() {
		return saleId;
	}
	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}
	public String getQuickMessage() {
		return quickMessage;
	}
	public void setQuickMessage(String quickMessage) {
		this.quickMessage = quickMessage;
	}
	
	
	
}
