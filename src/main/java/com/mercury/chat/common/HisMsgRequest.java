package com.mercury.chat.common;

import java.io.Serializable;

import com.mercury.chat.common.constant.Operation;


public class HisMsgRequest implements Serializable{
	
	private static final long serialVersionUID = 8546783381574079889L;

	private byte operation = Operation.LOAD.value();
	
	private String userId;
	
	private Long shopId; 
	
	private int offset; 
	
	private int batchSize;
	
	public HisMsgRequest(String userId, Long shopId, int offset, int batchSize) {
		super();
		this.userId = userId;
		this.shopId = shopId;
		this.offset = offset;
		this.batchSize = batchSize;
	}

	public HisMsgRequest userId(String userId){
		this.userId = userId;
		return this;
	}
	
	public HisMsgRequest shopId(Long shopId){
		this.shopId = shopId;
		return this;
	}
	
	public HisMsgRequest offset(int offset){
		this.offset = offset;
		return this;
	}
	
	public HisMsgRequest batchSize(int batchSize){
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

	public byte getOperation() {
		return operation;
	}

	public void setOperation(byte operation) {
		this.operation = operation;
	}
	
	

}
