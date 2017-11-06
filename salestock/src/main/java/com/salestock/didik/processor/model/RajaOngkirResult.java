package com.salestock.didik.processor.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RajaOngkirResult {
	@JsonProperty("province_id")
	private String provinceId;
	
	@JsonProperty("province")
	private String province;
	
	@JsonProperty("city_id")
	private String cityId;
	@JsonProperty("type")
	private String type;
	@JsonProperty("city_name")
	private String cityName;
	@JsonProperty("postal_code")
	private String postalCode;
	@JsonProperty("code")
	private String code;
	@JsonProperty("name")
	private String name;
	@JsonProperty("costs")
	private List<Cost> costs = null;

	@JsonProperty("city_id")
	public String getCityId() {
		return cityId;
	}

	@JsonProperty("city_id")
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@JsonProperty("province_id")
	public String getProvinceId() {
		return provinceId;
	}

	@JsonProperty("province_id")
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	@JsonProperty("province")
	public String getProvince() {
		return province;
	}

	@JsonProperty("province")
	public void setProvince(String province) {
		this.province = province;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("city_name")
	public String getCityName() {
		return cityName;
	}

	@JsonProperty("city_name")
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@JsonProperty("postal_code")
	public String getPostalCode() {
		return postalCode;
	}

	@JsonProperty("postal_code")
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("costs")
	public List<Cost> getCosts() {
		return costs;
	}

	@JsonProperty("costs")
	public void setCosts(List<Cost> costs) {
		this.costs = costs;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
