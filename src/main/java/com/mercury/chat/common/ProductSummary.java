package com.mercury.chat.common;

import java.io.Serializable;

public class ProductSummary implements Serializable{
	
	private static final long serialVersionUID = -4105637507450221852L;

	private long productId;
	
	private String productCode;
	
	private String summary;

	public ProductSummary(long productId, String productCode, String summary) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.summary = summary;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
	
}
