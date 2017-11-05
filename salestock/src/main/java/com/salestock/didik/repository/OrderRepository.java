package com.salestock.didik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.salestock.didik.model.OrderTransaction;

public interface OrderRepository extends JpaRepository<OrderTransaction, String>,
		QueryDslPredicateExecutor<OrderTransaction> {

}
