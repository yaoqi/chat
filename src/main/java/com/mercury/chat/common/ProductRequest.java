package com.mercury.chat.common;

import java.io.Serializable;

import com.mercury.chat.common.constant.Operation;

public class ProductRequest implements Serializable {

	private static final long serialVersionUID = -3202807063246681332L;

	private byte operation = Operation.LOAD.value();
	
	private long productId;

	public ProductRequest(long productId) {
		super();
		this.setProductId(productId);
	}

	public byte getOperation() {
		return operation;
	}

	public void setOperation(byte operation) {
		this.operation = operation;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

}
