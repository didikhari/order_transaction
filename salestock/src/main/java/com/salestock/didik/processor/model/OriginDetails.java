package com.salestock.didik.processor.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OriginDetails {

	@JsonProperty("city_id")
	private String cityId;
	@JsonProperty("province_id")
	private String provinceId;
	@JsonProperty("province")
	private String province;
	@JsonProperty("type")
	private String type;
	@JsonProperty("city_name")
	private String cityName;
	@JsonProperty("postal_code")
	private String postalCode;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
