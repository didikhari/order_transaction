package com.salestock.didik.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.salestock.didik.helper.CommonUtils;
import com.salestock.didik.model.Product;
import com.salestock.didik.model.ProductDetail;
import com.salestock.didik.model.QShoppingCart;
import com.salestock.didik.model.ShoppingCart;
import com.salestock.didik.repository.ShoppingCartRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
		this.shoppingCartRepository = shoppingCartRepository;
	}
	
	@Override
	public ShoppingCart addToCart(Product product, ProductDetail productDetail, Integer quantity){
		ShoppingCart cart = new ShoppingCart(CommonUtils.generateUUID(), productDetail, quantity);
		cart.setProduct(product);
		cart.setCreateDate(CommonUtils.getCurrentDateTime());
		cart.setUpdateDate(CommonUtils.getCurrentDateTime());
		ShoppingCart shoppingCart = shoppingCartRepository.save(cart);
		return shoppingCart;
	}
	
	@Override
	public ShoppingCart updateCart(ShoppingCart shoppingCart){
		if(shoppingCart != null){
			shoppingCart.setUpdateDate(CommonUtils.getCurrentDateTime());
			return shoppingCartRepository.save(shoppingCart);
		}
		return null;
	}
	
	@Override
	public Page<ShoppingCart> getShoppingCarts(Integer page, Integer size){
		QShoppingCart queryCart_ = QShoppingCart.shoppingCart;
		BooleanExpression notOrdered = queryCart_.orderTransaction.isNull();
		Page<ShoppingCart> result = shoppingCartRepository.findAll(notOrdered, new PageRequest(page, 
				size, new Sort(Direction.DESC, "updateDate")));
		return result;
	}
	
	@Override
	public ShoppingCart getCartItem(String cartId){
		ShoppingCart shoppingCart = shoppingCartRepository.findOne(cartId);
		Hibernate.initialize(shoppingCart.getProduct());
		Hibernate.initialize(shoppingCart.getProductDetail());
		return shoppingCart;
	}
	
	@Override
	public boolean deleteCartItem(String cartId){
		ShoppingCart shoppingCart = shoppingCartRepository.findOne(cartId);
		if(shoppingCart != null){
			shoppingCartRepository.delete(cartId);
			return true;
		}
		return false;
	}
}
