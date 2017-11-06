package com.salestock.didik.scheduller;

import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.salestock.didik.helper.CommonUtils;
import com.salestock.didik.helper.Constant;
import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.model.QShippingHistory;
import com.salestock.didik.model.ShippingHistory;
import com.salestock.didik.repository.OrderRepository;
import com.salestock.didik.repository.ShippingHistoryRepository;
import com.salestock.didik.service.OrderService;

/**
 * Scheduller for update or create shipping status/history record
 * @author didikhari
 */
@Service
public class ShippingStatusScheduller {
	private static transient Logger logger = LogManager.getLogger(ShippingStatusScheduller.class);
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShippingHistoryRepository shippingHistoryRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	@Scheduled(cron="0 0/10 * * * *")
	public void run(){
		logger.info("Begin Scheduller");
		
		Iterable<OrderTransaction> listOrderTransaction = orderService.listOrderTransaction(Constant.ORDER_SHIPMENT);
		for (OrderTransaction orderTransaction : listOrderTransaction) {
			String shippingTrackingCode = orderTransaction.getShippingTrackingCode();
			
			QShippingHistory shippingHistoryQuery = QShippingHistory.shippingHistory;
			BooleanExpression withTrackingCode = shippingHistoryQuery.trackingCode.eq(shippingTrackingCode);
			Iterable<ShippingHistory> shippingHistory = shippingHistoryRepository.findAll(
					withTrackingCode, new Sort(Direction.DESC, "createDate"));
			
			if(shippingHistory != null){
				ArrayList<ShippingHistory> listHistory = Lists.newArrayList(shippingHistory);
				if(listHistory.size() < 5){
					ShippingHistory history = new ShippingHistory();
					history.setId(CommonUtils.generateUUID());
					history.setTrackingCode(shippingTrackingCode);
					history.setCreateDate(CommonUtils.getCurrentDateTime());
					history.setDescription("Packet Location Updated :"+listHistory.size());
					history.setService("JNE");
					history.setDestination(orderTransaction.getShippingAddress().toString());
					shippingHistoryRepository.save(history);
				}else{
					orderTransaction.setStatus(Constant.ORDER_RECEIVE);
					orderRepository.save(orderTransaction);
				}
			}
		}
		logger.info("Scheduller DONE");
	}
}
