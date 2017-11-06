package com.salestock.didik.api.response;

import com.salestock.didik.model.Product;
import com.salestock.didik.model.ProductDetail;
import com.salestock.didik.model.ShoppingCart;

public class CartResponse {
	private String cartId;
	private Integer quantity;
	private String productId;
	private String name;
	private String productImageUrl;
	private String productOptionId;
	private String color;
	private String size;
	
	public CartResponse(ShoppingCart cart) {
		if(cart != null){
			this.cartId = cart.getId();
			this.quantity = cart.getQuantity();
			
			Product product = cart.getProduct();
			if(product != null){
				this.productId = product.getId();
				this.name = product.getName();
				this.productImageUrl = product.getImageUrl();
			}
			
			ProductDetail detail = cart.getProductDetail();
			if(detail != null){
				this.productOptionId = detail.getId();
				this.color = detail.getColor();
				this.size = detail.getSize();
			}
		}
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public String getProductOptionId() {
		return productOptionId;
	}

	public void setProductOptionId(String productOptionId) {
		this.productOptionId = productOptionId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}