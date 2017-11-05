package com.salestock.didik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.salestock.didik.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, String>,
		QueryDslPredicateExecutor<Coupon> {

}
