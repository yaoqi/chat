package com.mercury.chat.client.impl;

import java.io.Serializable;

import com.mercury.chat.common.constant.Operation;

public class OrderRequest implements Serializable {

	private static final long serialVersionUID = 3683505097318678029L;

	private byte operation = Operation.LOAD.value();
	
	private long orderId;

	public OrderRequest(long orderId) {
		super();
		this.setOperation(operation);
		this.setOrderId(orderId);
	}

	public byte getOperation() {
		return operation;
	}

	public void setOperation(byte operation) {
		this.operation = operation;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	

}
