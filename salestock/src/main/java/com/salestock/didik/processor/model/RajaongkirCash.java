package com.salestock.didik.processor.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RajaongkirCash {

    @JsonProperty("query")
    private Query query;
    @JsonProperty("status")
    private RajaOngkirStatus status;
    @JsonProperty("origin_details")
    private OriginDetails originDetails;
    @JsonProperty("destination_details")
    private DestinationDetails destinationDetails;
    @JsonProperty("results")
    private List<RajaOngkirResult> results = null;

    @JsonProperty("query")
    public Query getQuery() {
        return query;
    }

    @JsonProperty("query")
    public void setQuery(Query query) {
        this.query = query;
    }

    @JsonProperty("status")
    public RajaOngkirStatus getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(RajaOngkirStatus status) {
        this.status = status;
    }

    @JsonProperty("origin_details")
    public OriginDetails getOriginDetails() {
        return originDetails;
    }

    @JsonProperty("origin_details")
    public void setOriginDetails(OriginDetails originDetails) {
        this.originDetails = originDetails;
    }

    @JsonProperty("destination_details")
    public DestinationDetails getDestinationDetails() {
        return destinationDetails;
    }

    @JsonProperty("destination_details")
    public void setDestinationDetails(DestinationDetails destinationDetails) {
        this.destinationDetails = destinationDetails;
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
