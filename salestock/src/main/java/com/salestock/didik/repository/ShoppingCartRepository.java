package com.salestock.didik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.salestock.didik.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String>,
		QueryDslPredicateExecutor<ShoppingCart> {

}
