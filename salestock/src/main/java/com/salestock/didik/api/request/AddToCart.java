package com.salestock.didik.api.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AddToCart {

	@NotNull
	@NotEmpty
	private String productId;
	
	@NotNull
	@NotEmpty
	private String optionId;
	
	@NotNull
	private Integer quantity;
	
	public AddToCart() { }
	
	public AddToCart(String productId, String optionId, Integer quantity) {
		this.productId = productId;
		this.optionId = optionId;
		this.quantity = quantity;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
