package com.salestock.didik.service;

import org.springframework.data.domain.Page;

import com.salestock.didik.model.Product;
import com.salestock.didik.model.ProductDetail;

public interface ProductService {

	Page<Product> getProducts(Integer page, Integer size, String filter,
			String categoryId, String sort);

	Product getProduct(String id);
	
	ProductDetail getProductDetail(String detailId);

}
