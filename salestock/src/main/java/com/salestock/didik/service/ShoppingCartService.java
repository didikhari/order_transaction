package com.salestock.didik.service;

import org.springframework.data.domain.Page;

import com.salestock.didik.model.Product;
import com.salestock.didik.model.ProductDetail;
import com.salestock.didik.model.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart addToCart(Product product, ProductDetail productDetail,
			Integer quantity, String userId);

	ShoppingCart updateCart(ShoppingCart shoppingCart);

	Page<ShoppingCart> getShoppingCarts(Integer page, Integer size, String userId);

	ShoppingCart getCartItem(String cartId);

	boolean deleteCartItem(String cartId);

}
