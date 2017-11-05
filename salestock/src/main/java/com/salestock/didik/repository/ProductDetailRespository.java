package com.salestock.didik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.salestock.didik.model.ProductDetail;

public interface ProductDetailRespository extends JpaRepository<ProductDetail, String>,
		QueryDslPredicateExecutor<ProductDetail> {

}
