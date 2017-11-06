package com.salestock.didik.api.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.salestock.didik.processor.model.Cost;
import com.salestock.didik.processor.model.DestinationDetails;
import com.salestock.didik.processor.model.OriginDetails;

public class ShipingCostResponse {
	private String addressId;
	private String origin;
	private String destination;
	private List<CostData> costs = new ArrayList<CostData>();
	
	public ShipingCostResponse(List<Cost> costResponses, DestinationDetails destinationDetails,
			OriginDetails originDetails, String userAddress) {
		
		if(costResponses != null){
			for (Cost cost2 : costResponses) {
				CostData data = new CostData(cost2);
				this.costs.add(data);
			}
		}

		if(destinationDetails != null)
			this.destination = StringUtils.defaultIfBlank(destinationDetails.getCityName(), "");
		if(originDetails != null)
			this.origin = StringUtils.defaultIfBlank(originDetails.getCityName(), "");
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public List<CostData> getCosts() {
		return costs;
	}

	public void setCosts(List<CostData> costs) {
		this.costs = costs;
	}
}

class CostData{

	private String serviceName;
	private BigDecimal cost;
	private String estimation;
	
	public CostData(Cost cost) {
		if(cost != null){
			this.serviceName = cost.getService();
			if(cost.getCost().get(0).getValue() != null)
				this.cost = new BigDecimal(cost.getCost().get(0).getValue());
			else 
				this.cost = new BigDecimal(0);
			this.estimation = cost.getCost().get(0).getEtd();
		}
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getEstimation() {
		return estimation;
	}
	public void setEstimation(String estimation) {
		this.estimation = estimation;
	}

}