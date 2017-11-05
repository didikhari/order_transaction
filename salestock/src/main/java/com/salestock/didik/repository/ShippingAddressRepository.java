package com.salestock.didik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.salestock.didik.model.ShippingAddress;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, String>,
		QueryDslPredicateExecutor<ShippingAddress> {

}
