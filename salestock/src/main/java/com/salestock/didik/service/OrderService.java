package com.salestock.didik.service;

import org.springframework.data.domain.Page;

import com.salestock.didik.api.request.OrderRequest;
import com.salestock.didik.model.OrderTransaction;

public interface OrderService {

	OrderTransaction submitOrder(OrderRequest requestData)
			throws Exception;

	OrderTransaction updateOrder(OrderTransaction order);

	Page<OrderTransaction> listOrderTransaction(Integer page, Integer size,
			String status);

	OrderTransaction updateOrder(String orderId, String status,
			String shippingTrackingCode);

	OrderTransaction getOrderById(String orderId);
}
