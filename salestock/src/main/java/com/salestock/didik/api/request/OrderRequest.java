package com.salestock.didik.api.request;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class OrderRequest {
	private String couponCode;
	@NotEmpty
	private List<String> cartIds;
	@NotBlank
	private String shippingAddressId;
	private String shippingService;
	private String paymentMethod;

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public List<String> getCartIds() {
		return cartIds;
	}

	public void setCartIds(List<String> cartId) {
		this.cartIds = cartId;
	}

	public String getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getShippingService() {
		return shippingService;
	}

	public void setShippingService(String shippingService) {
		this.shippingService = shippingService;
	}
}
