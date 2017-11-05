package com.salestock.didik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.salestock.didik.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>, QueryDslPredicateExecutor<Product> {

}
