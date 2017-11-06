package com.salestock.didik.service;

import org.springframework.data.domain.Page;

import com.salestock.didik.api.request.OrderRequest;
import com.salestock.didik.model.Coupon;
import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.model.PaymentConfirmLog;
import com.salestock.didik.model.ProductDetail;

public interface OrderService {

	OrderTransaction submitOrder(OrderRequest requestData, Coupon coupon, String userId)
			throws Exception;

	OrderTransaction updateOrder(OrderTransaction order);

	OrderTransaction getOrderById(String orderId);

	Coupon updateCouponStock(String couponId) throws Exception;

	ProductDetail updateProductStock(String productDetailId, Integer quantity,
			boolean isRetur) throws Exception;

	PaymentConfirmLog saveConfirmation(PaymentConfirmLog confirm);

	Iterable<OrderTransaction> listOrderTransaction(String status);

	Page<OrderTransaction> listOrderTransaction(Integer page, Integer size,
			String status, String userId);

	OrderTransaction updateOrder(String orderId, String status,
			String shippingTrackingCode, String userId) throws Exception;
}
