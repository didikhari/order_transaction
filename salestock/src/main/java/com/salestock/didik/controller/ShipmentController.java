package com.salestock.didik.controller;

import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.salestock.didik.api.request.CheckShippingCostRequest;
import com.salestock.didik.api.response.ApiResponse;
import com.salestock.didik.api.response.CityListResponse;
import com.salestock.didik.api.response.ListData;
import com.salestock.didik.api.response.ProvinceListResponse;
import com.salestock.didik.api.response.ResponseBuilder;
import com.salestock.didik.api.response.ShipingCostResponse;
import com.salestock.didik.api.response.ShipmentStatusResponse;
import com.salestock.didik.model.QShippingHistory;
import com.salestock.didik.model.ShippingHistory;
import com.salestock.didik.processor.RajaOngkirProcessor;
import com.salestock.didik.processor.model.Cost;
import com.salestock.didik.processor.model.DestinationDetails;
import com.salestock.didik.processor.model.OriginDetails;
import com.salestock.didik.processor.model.RajaOngkirCityResponse;
import com.salestock.didik.processor.model.RajaOngkirCostResponse;
import com.salestock.didik.processor.model.RajaOngkirProvinceResponse;
import com.salestock.didik.processor.model.RajaOngkirResult;
import com.salestock.didik.repository.ShippingHistoryRepository;

@RestController
@RequestMapping(value="/v1")
public class ShipmentController {
	
	private Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private RajaOngkirProcessor rajaOngkirProcessor;
    @Autowired
    private ShippingHistoryRepository shippingHistoryRepository;
	
	@Value("${rajaongkir.origin}") 
    private String rajaOngkirOrigin;
	
	@ApiOperation(value="Check Shipping Cost")
	@PostMapping(value={"/check-shipping-cost"}, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ShipingCostResponse> checkOut(@RequestBody CheckShippingCostRequest requestData) throws Exception{
		
		RajaOngkirCostResponse costResponse = rajaOngkirProcessor.getCost(rajaOngkirOrigin, requestData.getCity(), 
				requestData.getWeight(), requestData.getCourier());
		ShipingCostResponse result = null;
		
		if(costResponse != null && costResponse.getRajaongkir() != null){
			List<RajaOngkirResult> results = costResponse.getRajaongkir().getResults();
			
			if(results != null && results.size() > 0){
				RajaOngkirResult rajaOngkirResult = results.get(0);
				List<Cost> costs = rajaOngkirResult.getCosts();
				DestinationDetails destinationDetails = costResponse.getRajaongkir().getDestinationDetails();
				OriginDetails originDetails = costResponse.getRajaongkir().getOriginDetails();
				result = new ShipingCostResponse(costs, destinationDetails, originDetails, null);
			}
				
			if(result == null){
				logger.error("Cannot find REG service from JNE");
				throw new Exception("Couldn't calculate shipping cost");
			}
		}else{
			logger.error("Raja Ongkir response is null");
			throw new Exception("Couldn't calculate shipping cost");
		}
		return ResponseBuilder.responseSuccess("OK", result);
	}

	/**
	 * Get Available Province for shipment
	 * @param lang
	 * @param auth
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation(value="Get Available Province for shipment")
	@GetMapping(value={"/address/province"}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ListData<ProvinceListResponse>> getProvince()  throws Exception{
		
		List<ProvinceListResponse> provinceList = new ArrayList<ProvinceListResponse>();
		RajaOngkirProvinceResponse province = rajaOngkirProcessor.getProvince();
		
		if(province != null && province.getRajaongkir() != null){
			List<RajaOngkirResult> results = province.getRajaongkir().getResults();
			
			for (RajaOngkirResult rajaOngkirResult : results) {
				ProvinceListResponse tmp = new ProvinceListResponse(rajaOngkirResult);
				provinceList.add(tmp);
			}
		}
		
		ListData<ProvinceListResponse> provinces = new ListData<ProvinceListResponse>();
		provinces.setContents(provinceList);
		return ResponseBuilder.responseSuccess("Success", provinces);
	}
	
	
	/**
	 * Get available City for shipment
	 * @param province
	 * @param lang
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation(value="Get available City for shipment")
	@GetMapping(value={"/address/{province}/cities"}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ListData<CityListResponse>> getCity(
			@PathVariable(value="province", required=false) String province) throws Exception{
		
		List<CityListResponse> cityList = new ArrayList<CityListResponse>();
		RajaOngkirCityResponse cityResponse = rajaOngkirProcessor.getCity(province);
		
		if(cityResponse != null && cityResponse.getRajaongkir() != null){
			List<RajaOngkirResult> results = cityResponse.getRajaongkir().getResults();
			
			for (RajaOngkirResult rajaOngkirResult : results) {
				CityListResponse tmp = new CityListResponse(rajaOngkirResult);
				cityList.add(tmp);
			}
		}
		
		ListData<CityListResponse> datas = new ListData<CityListResponse>();
		datas.setContents(cityList);
		return ResponseBuilder.responseSuccess("Success", datas);
	}
	
	@GetMapping(value={"/check-shipment-status"}, produces=MediaType.APPLICATION_JSON_VALUE)	
	public ApiResponse<ListData<ShipmentStatusResponse>> checkShipmentStatus(
			@RequestParam(value="tracking_code") String trackingCode){
		
		QShippingHistory shippingHistoryQuery = QShippingHistory.shippingHistory;
		BooleanExpression withTrackingCode = shippingHistoryQuery.trackingCode.eq(trackingCode);
		Iterable<ShippingHistory> shippingHistory = shippingHistoryRepository.findAll(
				withTrackingCode, new Sort(Direction.DESC, "createDate"));
		
		if(shippingHistory != null && shippingHistory.iterator().hasNext()){
			List<ShipmentStatusResponse> response = new ArrayList<ShipmentStatusResponse>();
			for (ShippingHistory history : shippingHistory) {
				ShipmentStatusResponse data = new ShipmentStatusResponse(history);
				response.add(data);
			}
			
			ListData<ShipmentStatusResponse> responseData = new ListData<ShipmentStatusResponse>();
			responseData.setContents(response);
			responseData.setPage(1);
			responseData.setTotalPage(1);
			responseData.setSize(response.size());
			return ResponseBuilder.responseSuccess("Success", responseData);
		}else{
			return ResponseBuilder.responseError("No Data Found");
		}
	}
}
