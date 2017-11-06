package com.salestock.didik.api.response;

import org.apache.commons.lang3.StringUtils;

import com.salestock.didik.processor.model.RajaOngkirResult;

public class ProvinceListResponse {
	private String id;
	private String name;
	
	public ProvinceListResponse(RajaOngkirResult rajaOngkirResult) {
		if(rajaOngkirResult != null){
			this.id = StringUtils.defaultIfBlank(rajaOngkirResult.getProvinceId(), "");
			this.name = StringUtils.defaultIfBlank(rajaOngkirResult.getProvince(), "");
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
