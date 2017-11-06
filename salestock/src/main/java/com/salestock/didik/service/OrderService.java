package com.salestock.didik.service;

import org.springframework.data.domain.Page;

import com.salestock.didik.api.request.OrderRequest;
import com.salestock.didik.model.Coupon;
import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.model.ProductDetail;

public interface OrderService {

	OrderTransaction submitOrder(OrderRequest requestData, Coupon coupon)
			throws Exception;

	OrderTransaction updateOrder(OrderTransaction order);

	Page<OrderTransaction> listOrderTransaction(Integer page, Integer size,
			String status);

	OrderTransaction updateOrder(String orderId, String status,
			String shippingTrackingCode);

	OrderTransaction getOrderById(String orderId);

	Coupon updateCouponStock(String couponId) throws Exception;

	ProductDetail updateProductStock(String productDetailId, Integer quantity,
			boolean isRetur) throws Exception;
}
