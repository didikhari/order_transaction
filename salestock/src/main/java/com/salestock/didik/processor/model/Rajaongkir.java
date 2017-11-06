package com.salestock.didik.processor.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Rajaongkir {
	@JsonProperty("status")
	private RajaOngkirStatus status;
	@JsonProperty("results")
	private List<RajaOngkirResult> results = null;

    @JsonProperty("origin_details")
    private OriginDetails originDetails;
    @JsonProperty("destination_details")
    private DestinationDetails destinationDetails;
    
	@JsonProperty("status")
	public RajaOngkirStatus getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(RajaOngkirStatus status) {
		this.status = status;
	}

	@JsonProperty("results")
	public List<RajaOngkirResult> getResults() {
		return results;
	}

	@JsonProperty("results")
	public void setResults(List<RajaOngkirResult> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
