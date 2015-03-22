package com.mercury.chat.common;

import java.io.Serializable;
import java.util.Collection;

public class OrderSummary implements Serializable{
	
	private static final long serialVersionUID = 3958845095205198860L;
	
	private long orderId;
	
	private Collection<ProductSummary> products;

	public OrderSummary() {
		super();
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Collection<ProductSummary> getProducts() {
		return products;
	}

	public void setProducts(Collection<ProductSummary> products) {
		this.products = products;
	}
	
}
