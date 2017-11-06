package com.salestock.didik.processor;

import com.salestock.didik.processor.model.RajaOngkirCityResponse;
import com.salestock.didik.processor.model.RajaOngkirCostResponse;
import com.salestock.didik.processor.model.RajaOngkirProvinceResponse;

public interface RajaOngkirProcessor {
	RajaOngkirProvinceResponse getProvince();

	RajaOngkirCityResponse getCity(String province);

	/**
	 * 
	 * @param origin
	 * @param destination
	 * @param weight on Kilo Gram
	 * @param courier
	 * @return
	 */
	RajaOngkirCostResponse getCost(String origin, String destination,
			Integer weight, String courier);
}
