package com.salestock.didik.model;

// Generated Nov 3, 2017 5:45:10 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Order generated by hbm2java
 */
@Entity
@Table(name = "order_transaction")
public class OrderTransaction implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private ShippingAddress shippingAddress;
	private BigDecimal shippingCost;
	private Date orderDate;
	private String paymentMethod;
	private String couponCode;
	private BigDecimal subTotalPrice;
	private BigDecimal totalPrice;
	private String status;
	private String shippingTrackingCode;
	private BigDecimal couponDiscount;
	private Set<PaymentConfirmLog> paymentConfirmLogs = new HashSet<PaymentConfirmLog>(0);
	private Set<ShoppingCart> shoppingCarts = new HashSet<ShoppingCart>(0);
	private String userId;
	
	public OrderTransaction() {
	}

	public OrderTransaction(String id) {
		this.id = id;
	}

	public OrderTransaction(String id, ShippingAddress shippingAddress,
			BigDecimal shippingCost, Date orderDate, String paymentMethod,
			String couponCode, BigDecimal totalPrice, String status,
			String shippingTrackingCode, BigDecimal couponDiscount,
			Set<PaymentConfirmLog> paymentConfirmLogs, Set<ShoppingCart> shoppingCarts) {
		this.id = id;
		this.shippingAddress = shippingAddress;
		this.shippingCost = shippingCost;
		this.orderDate = orderDate;
		this.paymentMethod = paymentMethod;
		this.couponCode = couponCode;
		this.totalPrice = totalPrice;
		this.status = status;
		this.shippingTrackingCode = shippingTrackingCode;
		this.couponDiscount = couponDiscount;
		this.paymentConfirmLogs = paymentConfirmLogs;
		this.shoppingCarts = shoppingCarts;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 100)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_address_id")
	public ShippingAddress getShippingAddress() {
		return this.shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Column(name = "shipping_cost", precision = 10, scale = 0)
	public BigDecimal getShippingCost() {
		return this.shippingCost;
	}

	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date", length = 19)
	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "payment_method", length = 50)
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "coupon_code", length = 100)
	public String getCouponCode() {
		return this.couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Column(name = "total_price", scale = 0)
	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "shipping_tracking_code")
	public String getShippingTrackingCode() {
		return this.shippingTrackingCode;
	}

	public void setShippingTrackingCode(String shippingTrackingCode) {
		this.shippingTrackingCode = shippingTrackingCode;
	}

	@Column(name = "coupon_discount", precision = 10, scale = 0)
	public BigDecimal getCouponDiscount() {
		return this.couponDiscount;
	}

	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderTransaction")
	public Set<PaymentConfirmLog> getPaymentConfirmLogs() {
		return this.paymentConfirmLogs;
	}

	public void setPaymentConfirmLogs(Set<PaymentConfirmLog> paymentConfirmLogs) {
		this.paymentConfirmLogs = paymentConfirmLogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderTransaction", cascade=CascadeType.ALL)
	public Set<ShoppingCart> getShoppingCarts() {
		return this.shoppingCarts;
	}

	public void setShoppingCarts(Set<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

	@Column(name = "subtotal_price", scale = 0)
	public BigDecimal getSubTotalPrice() {
		return subTotalPrice;
	}

	public void setSubTotalPrice(BigDecimal subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
