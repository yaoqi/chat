package com.mercury.chat.client.impl;

import java.io.Serializable;

import com.mercury.chat.common.constant.Operation;
import com.mercury.chat.user.entity.QuickReply;

public class QuickReplyRequest implements Serializable{
	
	private static final long serialVersionUID = 1845589560505930462L;
	
	private long saleId;
	
	private byte operation;
	
	private QuickReply quickReply;

	public QuickReplyRequest saleId(long saleId){
		this.saleId = saleId;
		return this;
	}
	
	public QuickReplyRequest operation(byte operation){
		this.operation = operation;
		return this;
	}
	
	public QuickReplyRequest operation(Operation operation){
		this.operation = operation.value();
		return this;
	}
	
	public QuickReplyRequest quickReply(QuickReply quickReply){
		this.quickReply = quickReply;
		return this;
	}

	public long getSaleId() {
		return saleId;
	}

	public byte getOperation() {
		return operation;
	}

	public QuickReply getQuickReply() {
		return quickReply;
	}
	
}
