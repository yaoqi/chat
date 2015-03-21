package com.mercury.chat.common;

import java.io.Serializable;

public class OrderSummary implements Serializable{
	
	private static final long serialVersionUID = 3958845095205198860L;
	
	public long orderId;

	public OrderSummary(long orderId) {
		super();
		this.orderId = orderId;
	}
	
}
