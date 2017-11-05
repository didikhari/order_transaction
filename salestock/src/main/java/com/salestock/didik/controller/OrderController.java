package com.salestock.didik.controller;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salestock.didik.api.request.OrderRequest;
import com.salestock.didik.api.request.UpdateOrder;
import com.salestock.didik.api.response.ApiResponse;
import com.salestock.didik.api.response.ListData;
import com.salestock.didik.api.response.ResponseBuilder;
import com.salestock.didik.helper.Constant;
import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.service.OrderService;

@RestController
@RequestMapping(value="/v1")
public class OrderController {
	private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping(value="submit-order", produces=MediaType.APPLICATION_JSON_VALUE, 
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderTransaction> submitOrder(@Valid @RequestBody OrderRequest requestData){
		
		try {
			OrderTransaction orderTransaction = orderService.submitOrder(requestData);
			return ResponseBuilder.responseSuccess("Success", orderTransaction);
		} catch (Exception e) {
			logger.fatal("submit order failed", e);
			return ResponseBuilder.responseError(e.getMessage());
		}
	}
	
	@GetMapping(value="orders", produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ListData<OrderTransaction>> listOrder(
			@RequestParam(value="page", defaultValue="1") Integer page, 
			@RequestParam(value="size", defaultValue="100") Integer size, 
			@RequestParam(value="status", defaultValue=Constant.ORDER_INITIALIZED) String status){
		
		Page<OrderTransaction> responseData = orderService.listOrderTransaction(page, size, status);
		if(responseData != null && responseData.getTotalElements() > 0){
			ListData<OrderTransaction> listData = new ListData<OrderTransaction>();
			listData.setContents(responseData.getContent());
			listData.setPage(page);
			listData.setSize(size);
			listData.setTotalPage(responseData.getTotalPages());
			return ResponseBuilder.responseSuccess("Success", listData);
		}
		return ResponseBuilder.responseSuccess("Order Empty", null);
	}
	
	@GetMapping(value="orders/{order_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderTransaction> orderDetails(@PathVariable(value="order_id") String orderId){
		OrderTransaction orderTransaction = orderService.getOrderById(orderId);
		if(orderTransaction != null){
			return ResponseBuilder.responseSuccess("Success", orderTransaction);
		}
		return ResponseBuilder.responseError("Order Not Found");
	}
	
	@PutMapping(value="/admin/update-order")
	public ApiResponse<OrderTransaction> updateOrder(@Valid @RequestBody UpdateOrder requestData){
		try {
			OrderTransaction orderTransaction = orderService.updateOrder(requestData.getOrderId(), 
					requestData.getStatus(), requestData.getTrackingCode());
			if(orderTransaction != null)
				return ResponseBuilder.responseSuccess("Order Updated", orderTransaction);
		} catch (Exception e) {
			logger.fatal("update order failed", e);
			return ResponseBuilder.responseError("Update Failed");
		}
		return ResponseBuilder.responseError("Update Failed");
	}
}
