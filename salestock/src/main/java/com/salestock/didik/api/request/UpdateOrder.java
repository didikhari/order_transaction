package com.salestock.didik.api.request;

import org.hibernate.validator.constraints.NotBlank;

public class UpdateOrder {
	@NotBlank
	private String orderId;
	private String status;
	private String trackingCode;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTrackingCode() {
		return trackingCode;
	}
	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}
}
