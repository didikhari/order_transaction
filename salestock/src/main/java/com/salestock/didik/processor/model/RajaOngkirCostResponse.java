package com.salestock.didik.processor.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RajaOngkirCostResponse {

	@JsonProperty("rajaongkir")
	private RajaongkirCash rajaongkir;

	@JsonProperty("rajaongkir")
	public RajaongkirCash getRajaongkir() {
		return rajaongkir;
	}

	@JsonProperty("rajaongkir")
	public void setRajaongkir(RajaongkirCash rajaongkir) {
		this.rajaongkir = rajaongkir;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
