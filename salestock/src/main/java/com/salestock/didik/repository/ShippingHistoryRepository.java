package com.salestock.didik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.salestock.didik.model.ShippingHistory;

public interface ShippingHistoryRepository extends JpaRepository<ShippingHistory, String>,
		QueryDslPredicateExecutor<ShippingHistory> {

}
