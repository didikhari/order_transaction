package com.salestock.didik.processor.model;

import java.math.BigDecimal;
import java.util.Date;

import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.model.ShippingAddress;

public class OrderSingleResponse extends OrderListResponse {

	private Address address;
	private Date orderDate;
	private String couponCode;
	private String paymentMethod;
	private PaymentDetail paymentDetails;
	private String trackingCode;
	
	public OrderSingleResponse(OrderTransaction order) {
		super(order);
		if(order != null){
			this.address = new Address(order.getShippingAddress());
			this.orderDate = order.getOrderDate();
			this.couponCode = order.getCouponCode();
			this.paymentMethod = order.getPaymentMethod();
			this.paymentDetails = new PaymentDetail(order);
			this.trackingCode = order.getShippingTrackingCode();
		}
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentDetail getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetail paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public String getTrackingCode() {
		return trackingCode;
	}

	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}

}

class PaymentDetail{
	private BigDecimal shippingCost;
	private BigDecimal subTotalPrice;
	private BigDecimal totalPrice;
	private BigDecimal couponDiscount;
	public PaymentDetail(OrderTransaction order) {
		this.shippingCost = order.getShippingCost();
		this.couponDiscount = order.getCouponDiscount();
		this.totalPrice = order.getTotalPrice();
		this.subTotalPrice = order.getSubTotalPrice();
	}
	public BigDecimal getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}
	public BigDecimal getSubTotalPrice() {
		return subTotalPrice;
	}
	public void setSubTotalPrice(BigDecimal subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	
}

class Address{
	private String id;
	private String fullName;
	private String email;
	private String phone;
	private String addressLine;
	private String subDistrict;
	private String city;
	private String province;
	private String postalCode;
	
	public Address(ShippingAddress shippingAddress) {
		if(shippingAddress != null){
			this.id = shippingAddress.getId();
			this.fullName = shippingAddress.getFullName();
			this.email = shippingAddress.getEmail();
			this.phone = shippingAddress.getPhone();
			this.addressLine = shippingAddress.getAddressLine();
			this.subDistrict = shippingAddress.getSubDistrict();
			this.city = shippingAddress.getCity();
			this.province = shippingAddress.getProvince();
			this.postalCode = shippingAddress.getPostalCode();
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getSubDistrict() {
		return subDistrict;
	}
	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}