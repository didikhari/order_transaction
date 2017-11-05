package com.salestock.didik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.salestock.didik.model.PaymentConfirmLog;

public interface PaymentConfirmLogRepository extends JpaRepository<PaymentConfirmLog, String>,
		QueryDslPredicateExecutor<PaymentConfirmLog> {

}
