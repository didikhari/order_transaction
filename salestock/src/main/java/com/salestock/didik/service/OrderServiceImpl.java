package com.salestock.didik.service;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.salestock.didik.api.request.OrderRequest;
import com.salestock.didik.helper.CommonUtils;
import com.salestock.didik.helper.Constant;
import com.salestock.didik.model.Coupon;
import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.model.PaymentConfirmLog;
import com.salestock.didik.model.ProductDetail;
import com.salestock.didik.model.QOrderTransaction;
import com.salestock.didik.model.QShoppingCart;
import com.salestock.didik.model.ShippingAddress;
import com.salestock.didik.model.ShoppingCart;
import com.salestock.didik.processor.RajaOngkirProcessor;
import com.salestock.didik.processor.model.RajaOngkirCostResponse;
import com.salestock.didik.repository.CouponRepository;
import com.salestock.didik.repository.OrderRepository;
import com.salestock.didik.repository.PaymentConfirmLogRepository;
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
	private RajaOngkirProcessor rajaOngkirProcessor;
	private PaymentConfirmLogRepository confirmLogRepository;
	
    @Value("${rajaongkir.origin}") 
    private String rajaOngkirOrigin;
    
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository,
			ProductDetailRespository productDetailRespository,
			ShoppingCartRepository shoppingCartRepository,
			ShippingAddressRepository shippingAddressRepository, 
			CouponRepository couponRepository, RajaOngkirProcessor rajaOngkirProcessor,
			PaymentConfirmLogRepository confirmLogRepository) {
		
		this.confirmLogRepository =  confirmLogRepository;
		this.shippingAddressRepository = shippingAddressRepository;
		this.orderRepository = orderRepository;
		this.productDetailRespository = productDetailRespository;
		this.shoppingCartRepository = shoppingCartRepository;
		this.couponRepository = couponRepository;
		this.rajaOngkirProcessor = rajaOngkirProcessor;
	}

	@Override
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED, isolation=Isolation.SERIALIZABLE)
	public OrderTransaction submitOrder(OrderRequest requestData, Coupon coupon, String userId) throws Exception{

		OrderTransaction order = new OrderTransaction(CommonUtils.generateUUID());
		order.setOrderDate(CommonUtils.getCurrentDateTime());
		order.setStatus(Constant.ORDER_INITIALIZED);
		order.setPaymentMethod(requestData.getPaymentMethod());
		
		QShoppingCart shoppingCartQuery_ = QShoppingCart.shoppingCart;
		BooleanExpression cartWithIds_ = shoppingCartQuery_.id.in(requestData.getCartIds());
		Iterable<ShoppingCart> carts = shoppingCartRepository.findAll(cartWithIds_, new Sort("updateDate"));

		if(carts == null || !carts.iterator().hasNext()){
			throw new Exception("Cart is empty");
		}
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		Integer totalWeight = 0;
		
		for (ShoppingCart shoppingCart_ : carts) {
			
			shoppingCart_.setOrderTransaction(order);
			Hibernate.initialize(shoppingCart_.getProductDetail());	
			updateProductStock(shoppingCart_.getProductDetail().getId(), shoppingCart_.getQuantity(), false);

			Hibernate.initialize(shoppingCart_.getProduct());
			totalPrice = totalPrice.add(shoppingCart_.getProduct().getPrice());
			totalWeight = totalWeight + shoppingCart_.getProduct().getWeight();
		}

		ShippingAddress address = shippingAddressRepository.findOne(requestData.getShippingAddressId());
		if(address == null || StringUtils.isAnyBlank(address.getEmail(), 
				address.getFullName(), address.getPhone(), address.getAddressLine())){
			throw new Exception("Destination Address is not valid");
		}

		order.setShippingAddress(address);
		
		try {
			RajaOngkirCostResponse rajaOngkirCostResponse = rajaOngkirProcessor.getCost(rajaOngkirOrigin, address.getCityId(), totalWeight, "jne");
			Integer shippingCost = rajaOngkirCostResponse.getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getValue();
			order.setShippingCost(new BigDecimal(shippingCost));
		} catch (Exception e) {
			throw new Exception("Couldn't get Shipping Cost");
		}
		
		order.setCouponDiscount(BigDecimal.ZERO);
		if(coupon != null){
			if(coupon.isPercentage()){
				BigDecimal discountValue = totalPrice.multiply(new BigDecimal(coupon.getValue())).divide(new BigDecimal(100));
				order.setCouponDiscount(discountValue);
			}else{
				order.setCouponDiscount(new BigDecimal(coupon.getValue()));
			}
			order.setCouponCode(requestData.getCouponCode());
		}
		
		order.setUserId(userId);
		order.setSubTotalPrice(totalPrice);
		order.setTotalPrice(order.getSubTotalPrice().add(order.getShippingCost()).subtract(order.getCouponDiscount()));
		order.setShoppingCarts(Sets.newHashSet(carts));
		orderRepository.save(order);
		
		return order;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public OrderTransaction updateOrder(OrderTransaction order){
		OrderTransaction orderTransaction = orderRepository.save(order);
		return orderTransaction;
	}
	
	@Override
	public Page<OrderTransaction> listOrderTransaction(Integer page, Integer size, String status, String userId){
		QOrderTransaction orderQuery_ = QOrderTransaction.orderTransaction;
		BooleanBuilder criteriaBuilder = new BooleanBuilder();
		if(StringUtils.isNotBlank(status)) {
			BooleanExpression sameStatus = orderQuery_.status.eq(status);
			criteriaBuilder.and(sameStatus);
		}
		PageRequest pageable = new PageRequest((page > 0) ? page -1 : page, size, new Sort(Direction.DESC, "orderDate"));
		Page<OrderTransaction> result = orderRepository.findAll(criteriaBuilder, pageable);
		return result;
	}
	
	@Override
	public Iterable<OrderTransaction> listOrderTransaction(String status){
		QOrderTransaction orderQuery_ = QOrderTransaction.orderTransaction;
		BooleanBuilder criteriaBuilder = new BooleanBuilder();
		if(StringUtils.isNotBlank(status)) {
			BooleanExpression sameStatus = orderQuery_.status.eq(status);
			criteriaBuilder.and(sameStatus);
		}
		Iterable<OrderTransaction> result = orderRepository.findAll(criteriaBuilder);
		return result;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public OrderTransaction updateOrder(String orderId, String status, String shippingTrackingCode, String userId) throws Exception{
		OrderTransaction orderTransaction = orderRepository.findOne(orderId);
		
		if(StringUtils.isNotBlank(status))
			orderTransaction.setStatus(status);
		
		if(StringUtils.isNotBlank(shippingTrackingCode))
			orderTransaction.setShippingTrackingCode(shippingTrackingCode);
		
		return orderRepository.save(orderTransaction);
	}
	
	@Override
	public OrderTransaction getOrderById(String orderId) {
		OrderTransaction orderTransaction = orderRepository.findOne(orderId);
		return orderTransaction;
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public synchronized ProductDetail updateProductStock(String productDetailId, Integer quantity, boolean isRetur) throws Exception{
		ProductDetail productDetail = productDetailRespository.findOne(productDetailId);
		Hibernate.initialize(productDetail.getProduct());
		Integer currentStock = productDetail.getStock();
		if (isRetur) {
			productDetail.setStock(currentStock + quantity);
		}else{
			if(currentStock >= quantity)
				productDetail.setStock(currentStock - quantity);
			else
				throw new Exception(productDetail.getProduct().getName()+" Stock Unavailable");
		}
		return productDetailRespository.save(productDetail);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public synchronized Coupon updateCouponStock(String couponCode) throws Exception{
		Coupon coupon = couponRepository.findByCode(couponCode);
		if(coupon == null || !(coupon.getStock() > 0 && CommonUtils.getCurrentDateTime().after(coupon.getStartDate()) &&
				CommonUtils.getCurrentDateTime().before(coupon.getEndDate()))){
			throw new Exception("Coupon "+couponCode+" is Not Valid");
		}
		coupon.setStock(coupon.getStock() - 1);
		return couponRepository.save(coupon);
	}
	
	@Override
	public PaymentConfirmLog saveConfirmation(PaymentConfirmLog confirm){
		return confirmLogRepository.save(confirm);
	}
}
