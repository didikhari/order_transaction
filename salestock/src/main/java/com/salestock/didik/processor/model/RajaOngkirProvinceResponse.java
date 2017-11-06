package com.salestock.didik.processor.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RajaOngkirProvinceResponse {
	@JsonProperty("rajaongkir")
	private Rajaongkir rajaongkir;

	@JsonProperty("rajaongkir")
	public Rajaongkir getRajaongkir() {
		return rajaongkir;
	}

	@JsonProperty("rajaongkir")
	public void setRajaongkir(Rajaongkir rajaongkir) {
		this.rajaongkir = rajaongkir;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
