package com.salestock.didik.processor.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cost {
	@JsonProperty("service")
	private String service;
	@JsonProperty("description")
	private String description;
	@JsonProperty("cost")
	private List<Cost_> cost = null;

	@JsonProperty("service")
	public String getService() {
		return service;
	}

	@JsonProperty("service")
	public void setService(String service) {
		this.service = service;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("cost")
	public List<Cost_> getCost() {
		return cost;
	}

	@JsonProperty("cost")
	public void setCost(List<Cost_> cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
