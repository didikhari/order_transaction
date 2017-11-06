package com.salestock.didik.processor.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cost_ {
	@JsonProperty("value")
	private Integer value;
	@JsonProperty("etd")
	private String etd;
	@JsonProperty("note")
	private String note;

	@JsonProperty("value")
	public Integer getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(Integer value) {
		this.value = value;
	}

	@JsonProperty("etd")
	public String getEtd() {
		return etd;
	}

	@JsonProperty("etd")
	public void setEtd(String etd) {
		this.etd = etd;
	}

	@JsonProperty("note")
	public String getNote() {
		return note;
	}

	@JsonProperty("note")
	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
