package com.salestock.didik.processor;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RajaOngkirClient extends RestTemplate {
	private static transient Logger logger = LogManager.getLogger(RajaOngkirClient.class);

	private MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
	
	@Autowired
	public RajaOngkirClient(@Value("${rajaongkir.key}") String rajaOngkirKey) {
		super();
		headers.add("key", rajaOngkirKey);
	}
	
	public <T> T sendRequest(String targetUrl, MultiValueMap<String, String> requestObject, Class<T> responseType, HttpMethod method){
		HttpEntity<Object> request = new HttpEntity<Object>(requestObject, headers);
		
		try {
			URI url = new URI(targetUrl);
			T response = null;
			
			if(method.equals(HttpMethod.POST)){
				logger.info("Sending POST request: "+targetUrl);
				response = postForObject(url, request, responseType);
				
			}else{
				
				logger.info("Sending GET request: "+targetUrl);
				if(requestObject != null){
					
					UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParams(requestObject);
					url = builder.buildAndExpand(requestObject).toUri();
					logger.info("Append Query Param to GET request: "+url);
				}
				
				ResponseEntity<T> responseEntity = exchange(url, HttpMethod.GET, request, responseType);
				response = responseEntity.getBody();
			}
			
			if(response != null){
				logger.info("Response from Raja Ongkir: "+response.toString());
			}
			return response;
		} catch (URISyntaxException e) {
			logger.fatal("[ERROR] Failed to Send Request", e);
		}
		return null;
	}
}
