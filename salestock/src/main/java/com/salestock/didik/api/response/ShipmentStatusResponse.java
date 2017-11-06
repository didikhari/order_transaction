package com.salestock.didik.api.response;

import java.util.Date;

import com.salestock.didik.model.ShippingHistory;

public class ShipmentStatusResponse {
	private Date createDate;
	private String status;
	
	public ShipmentStatusResponse(ShippingHistory history) {
		if(history != null){
			this.createDate = history.getCreateDate();
			this.status = history.getDescription();
		}
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
