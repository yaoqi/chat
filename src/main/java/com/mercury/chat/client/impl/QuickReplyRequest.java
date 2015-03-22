package com.mercury.chat.client.impl;

import java.io.Serializable;

import com.mercury.chat.common.QuickReply;

public class QuickReplyRequest implements Serializable{
	
	private static final long serialVersionUID = 1845589560505930462L;
	
	private long saleId;
	
	private QuickReply quickReply;

	public long getSaleId() {
		return saleId;
	}

	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}

	public QuickReply getQuickReply() {
		return quickReply;
	}

	public void setQuickReply(QuickReply quickReply) {
		this.quickReply = quickReply;
	}
	
	
	
}
