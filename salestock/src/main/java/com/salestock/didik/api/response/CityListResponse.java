package com.salestock.didik.api.response;

import org.apache.commons.lang3.StringUtils;

import com.salestock.didik.processor.model.RajaOngkirResult;

public class CityListResponse {
	private String id;
	private String provinceId;
	private String name;
	private String postalCode;
	
	public CityListResponse(RajaOngkirResult rajaOngkirResult) {
		if(rajaOngkirResult != null){
			this.id = StringUtils.defaultIfBlank(rajaOngkirResult.getCityId(), "");
			this.provinceId = StringUtils.defaultIfBlank(rajaOngkirResult.getProvinceId(), "");
			this.name = StringUtils.defaultIfBlank(rajaOngkirResult.getCityName(), "");
			this.postalCode = StringUtils.defaultIfBlank(rajaOngkirResult.getPostalCode(), "");
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String cityId) {
		this.id = cityId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
