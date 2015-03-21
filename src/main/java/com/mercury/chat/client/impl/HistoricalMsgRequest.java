package com.mercury.chat.client.impl;

import java.io.Serializable;


public class HistoricalMsgRequest implements Serializable{
	
	private static final long serialVersionUID = 8546783381574079889L;

	private String userId;
	
	private Long shopId; 
	
	private int offset; 
	
	private int batchSize;
	
	public HistoricalMsgRequest(String userId, Long shopId, int offset, int batchSize) {
		super();
		this.userId = userId;
		this.shopId = shopId;
		this.offset = offset;
		this.batchSize = batchSize;
	}

	public HistoricalMsgRequest userId(String userId){
		this.userId = userId;
		return this;
	}
	
	public HistoricalMsgRequest shopId(Long shopId){
		this.shopId = shopId;
		return this;
	}
	
	public HistoricalMsgRequest offset(int offset){
		this.offset = offset;
		return this;
	}
	
	public HistoricalMsgRequest batchSize(int batchSize){
		this.batchSize = batchSize;
		return this;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	
	

}
