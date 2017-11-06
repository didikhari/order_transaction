package com.salestock.didik.processor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.salestock.didik.processor.model.RajaOngkirCityResponse;
import com.salestock.didik.processor.model.RajaOngkirCostResponse;
import com.salestock.didik.processor.model.RajaOngkirProvinceResponse;

@Service
public class RajaOngkirProcessorImpl implements RajaOngkirProcessor {
	private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private RajaOngkirClient restClient;
	
    @Value("${rajaongkir.get.prpovince.url}") 
    private String rajaOngkirGetProvinceUrl;
    @Value("${rajaongkir.get.city.url}") 
    private String rajaOngkirGetCityUrl;
    @Value("${rajaongkir.check.cost.url}") 
    private String rajaOngkirCheckCostUrl;
    
	@Override
	public RajaOngkirProvinceResponse getProvince(){
		RajaOngkirProvinceResponse response = restClient.sendRequest(rajaOngkirGetProvinceUrl, null, 
				RajaOngkirProvinceResponse.class, HttpMethod.GET);
		return response;
	}
	
	@Override
	public RajaOngkirCityResponse getCity(String provinceId){
		RajaOngkirCityResponse response = null;
		if (StringUtils.isNotBlank(provinceId)) {
			logger.info("RajaOngkirCity Request");
			MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
			param.add("province", provinceId);
			response = restClient.sendRequest(rajaOngkirGetCityUrl, param, 
					RajaOngkirCityResponse.class, HttpMethod.GET);
		}else{
			response = restClient.sendRequest(rajaOngkirGetCityUrl, null, 
				RajaOngkirCityResponse.class, HttpMethod.GET);
			
		}
		return response;
	}
	
	@Override
	public RajaOngkirCostResponse getCost(String origin, String destination, 
			Integer weight, String courier){
		
		weight = (weight == null) ? 1000 : (weight * 1000);
		courier = StringUtils.defaultIfBlank(courier, "jne");
		
		MultiValueMap<String, String> requestObject = new LinkedMultiValueMap<String, String>();
		requestObject.add("origin", origin);
		requestObject.add("destination", destination);
		requestObject.add("weight", weight.toString());
		requestObject.add("courier", courier);
		
		RajaOngkirCostResponse response = restClient.sendRequest(rajaOngkirCheckCostUrl, 
				requestObject, RajaOngkirCostResponse.class, HttpMethod.POST);
		return response;
	}
}
