package com.salestock.didik.processor.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.salestock.didik.api.response.CartResponse;
import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.model.ShoppingCart;

public class OrderListResponse {
	private String id;
	private BigDecimal totalPrice;
	private String status;
	private List<CartResponse> items = new ArrayList<CartResponse>();
	
	public OrderListResponse(OrderTransaction order) {
		if(order != null){
			this.id = order.getId();
			this.totalPrice = order.getTotalPrice();
			this.status = order.getStatus();
			
			Set<ShoppingCart> shoppingCarts = order.getShoppingCarts();
			if(shoppingCarts != null && shoppingCarts.size() > 0){
				for (ShoppingCart shoppingCart : shoppingCarts) {
					CartResponse item = new CartResponse(shoppingCart);
					items.add(item);
				}
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CartResponse> getItems() {
		return items;
	}

	public void setItems(List<CartResponse> items) {
		this.items = items;
	}
}
