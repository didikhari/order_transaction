package com.salestock.didik.service;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.salestock.didik.api.request.OrderRequest;
import com.salestock.didik.helper.CommonUtils;
import com.salestock.didik.helper.Constant;
import com.salestock.didik.model.Coupon;
import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.model.QCoupon;
import com.salestock.didik.model.QOrderTransaction;
import com.salestock.didik.model.QShoppingCart;
import com.salestock.didik.model.ShippingAddress;
import com.salestock.didik.model.ShoppingCart;
import com.salestock.didik.repository.CouponRepository;
import com.salestock.didik.repository.OrderRepository;
import com.salestock.didik.repository.ProductDetailRespository;
import com.salestock.didik.repository.ShippingAddressRepository;
import com.salestock.didik.repository.ShoppingCartRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	private ProductDetailRespository productDetailRespository;
	private ShoppingCartRepository shoppingCartRepository;
	private ShippingAddressRepository shippingAddressRepository;
	private CouponRepository couponRepository;
	
	private final Object LOCK = new Object();
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository,
			ProductDetailRespository productDetailRespository,
			ShoppingCartRepository shoppingCartRepository,
			ShippingAddressRepository shippingAddressRepository, 
			CouponRepository couponRepository) {
		
		this.shippingAddressRepository = shippingAddressRepository;
		this.orderRepository = orderRepository;
		this.productDetailRespository = productDetailRespository;
		this.shoppingCartRepository = shoppingCartRepository;
		this.couponRepository = couponRepository;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public OrderTransaction submitOrder(OrderRequest requestData) throws Exception{

		OrderTransaction order = new OrderTransaction(CommonUtils.generateUUID());
		QShoppingCart shoppingCartQuery_ = QShoppingCart.shoppingCart;
		BooleanExpression cartWithIds_ = shoppingCartQuery_.id.in(requestData.getCartIds());
		Iterable<ShoppingCart> carts = shoppingCartRepository.findAll(cartWithIds_, new Sort("updateDate"));

		BigDecimal totalPrice_ = BigDecimal.ZERO;
		synchronized (LOCK) {
			Coupon coupon = validateCoupon(requestData.getCouponCode());
			coupon.setValue(StringUtils.defaultIfBlank(coupon.getValue(), "0"));
			if(coupon.isPercentage()){
				BigDecimal discountValue = totalPrice_.multiply(new BigDecimal(coupon.getValue())).divide(new BigDecimal(100));
				order.setCouponDiscount(discountValue);
			}else{
				order.setCouponDiscount(new BigDecimal(coupon.getValue()));
			}
			
			ShippingAddress address = shippingAddressRepository.findOne(requestData.getShippingAddressId());
			if(address == null || StringUtils.isAnyBlank(address.getEmail(), 
					address.getFullName(), address.getPhone(), address.getAddressLine())){
				throw new Exception("Destination Address is not valid");
			}
			order.setShippingAddress(address);
			order.setShippingCost(requestData.getShippingCost());
			order.setTotalPrice(totalPrice_);
			order.setOrderDate(CommonUtils.getCurrentDateTime());
			order.setStatus(Constant.ORDER_INITIALIZED);
			order.setPaymentMethod(requestData.getPaymentMethod());
			order.setCouponCode(requestData.getCouponCode());
			orderRepository.save(order);
			
			for (ShoppingCart shoppingCart_ : carts) {

				shoppingCart_.setOrderTransaction(order);
				
				Hibernate.initialize(shoppingCart_.getProductDetail());	
				Hibernate.initialize(shoppingCart_.getProduct());
				
				if(shoppingCart_.getProductDetail() != null && shoppingCart_.getProduct() != null &&
						shoppingCart_.getProductDetail().getStock() >= shoppingCart_.getQuantity()){
					
					shoppingCart_.getProductDetail().setStock(
							shoppingCart_.getProductDetail().getStock() - shoppingCart_.getQuantity());
					totalPrice_ = totalPrice_.add(shoppingCart_.getProduct().getPrice());
					
				} else{
					String productName = "";
					if(shoppingCart_.getProduct() != null)
						productName = shoppingCart_.getProduct().getName();
					throw new Exception("Item "+productName+" Out of Stock");
				}
			}
			
			shoppingCartRepository.save(carts);
		}
		return order;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public OrderTransaction updateOrder(OrderTransaction order){
		OrderTransaction orderTransaction = orderRepository.save(order);
		return orderTransaction;
	}
	
	@Override
	public Page<OrderTransaction> listOrderTransaction(Integer page, Integer size, String status){
		QOrderTransaction orderQuery_ = QOrderTransaction.orderTransaction;
		BooleanBuilder criteriaBuilder = new BooleanBuilder();
		if(StringUtils.isNotBlank(status)) {
			BooleanExpression sameStatus = orderQuery_.status.eq(status);
			criteriaBuilder.and(sameStatus);
		}
		PageRequest pageable = new PageRequest(page, size, new Sort(Direction.DESC, "orderDate"));
		Page<OrderTransaction> result = orderRepository.findAll(criteriaBuilder, pageable);
		return result;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public OrderTransaction updateOrder(String orderId, String status, String shippingTrackingCode){
		OrderTransaction orderTransaction = orderRepository.findOne(orderId);
		if(StringUtils.isNotBlank(status))
			orderTransaction.setStatus(status);
		if(StringUtils.isNotBlank(shippingTrackingCode))
			orderTransaction.setShippingTrackingCode(shippingTrackingCode);
		return orderRepository.save(orderTransaction);
	}
	
	private Coupon validateCoupon(String couponCode) throws Exception {
		QCoupon couponQuery_ = QCoupon.coupon;
		Coupon coupon = couponRepository.findOne(couponQuery_.code.eq(couponCode));
		
		if(coupon == null || !(coupon.getStock() > 0 && CommonUtils.getCurrentDateTime().after(coupon.getStartDate()) &&
				CommonUtils.getCurrentDateTime().before(coupon.getEndDate()))){
			throw new Exception("Coupon "+couponCode+" is Not Valid");
		}
		
		coupon.setStock(coupon.getStock()-1);
		couponRepository.save(coupon);
		return coupon;
	}

	@Override
	public OrderTransaction getOrderById(String orderId) {
		OrderTransaction orderTransaction = orderRepository.findOne(orderId);
		return orderTransaction;
	}
	
}
