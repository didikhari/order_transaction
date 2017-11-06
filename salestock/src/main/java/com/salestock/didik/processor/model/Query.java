package com.salestock.didik.processor.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {

	@JsonProperty("origin")
	private String origin;
	@JsonProperty("destination")
	private String destination;
	@JsonProperty("weight")
	private Integer weight;
	@JsonProperty("courier")
	private String courier;

	@JsonProperty("origin")
	public String getOrigin() {
		return origin;
	}

	@JsonProperty("origin")
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@JsonProperty("destination")
	public String getDestination() {
		return destination;
	}

	@JsonProperty("destination")
	public void setDestination(String destination) {
		this.destination = destination;
	}

	@JsonProperty("weight")
	public Integer getWeight() {
		return weight;
	}

	@JsonProperty("weight")
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@JsonProperty("courier")
	public String getCourier() {
		return courier;
	}

	@JsonProperty("courier")
	public void setCourier(String courier) {
		this.courier = courier;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
