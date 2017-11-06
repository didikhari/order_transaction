package com.salestock.didik.api.response;

import com.salestock.didik.api.request.CreateAddressRequest;
import com.salestock.didik.model.ShippingAddress;

public class AddressResponse extends CreateAddressRequest {
	private String id;
	
	public AddressResponse(ShippingAddress address) {
		if(address != null){
			this.id = address.getId();
			this.setFullName(address.getFullName());
			this.setPhone(address.getPhone());
			this.setEmail(address.getEmail());
			this.setAddressLine(address.getAddressLine());
			this.setSubDistrict(address.getSubDistrict());
			this.setCity(address.getCity());
			this.setPostalCode(address.getPostalCode());
			this.setProvince(address.getProvince());
			this.setCityId(address.getCityId());
			this.setUserId(address.getUserId());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
