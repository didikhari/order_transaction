package com.salestock.didik.model;

// Generated Nov 3, 2017 5:45:10 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ShippingAddress generated by hbm2java
 */
@Entity
@Table(name = "shipping_address")
public class ShippingAddress implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String fullName;
	private String email;
	private String phone;
	private String addressLine;
	private String subDistrict;
	private String city;
	private String province;
	private String postalCode;
	private Integer userId;
	private Set<OrderTransaction> orders = new HashSet<OrderTransaction>(0);

	public ShippingAddress() {
	}

	public ShippingAddress(String id, String fullName, String email,
			String phone, String addressLine, String subDistrict, String city,
			String province, String postalCode) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.addressLine = addressLine;
		this.subDistrict = subDistrict;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
	}

	public ShippingAddress(String id, String fullName, String email,
			String phone, String addressLine, String subDistrict, String city,
			String province, String postalCode, Integer userId, Set<OrderTransaction> orders) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.addressLine = addressLine;
		this.subDistrict = subDistrict;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.userId = userId;
		this.orders = orders;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 100)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "full_name", nullable = false, length = 250)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "email", nullable = false, length = 300)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", nullable = false, length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address_line", nullable = false, length = 1000)
	public String getAddressLine() {
		return this.addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	@Column(name = "sub_district", nullable = false, length = 100)
	public String getSubDistrict() {
		return this.subDistrict;
	}

	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}

	@Column(name = "city", nullable = false, length = 100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "province", nullable = false, length = 100)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "postal_code", nullable = false, length = 10)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shippingAddress")
	public Set<OrderTransaction> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<OrderTransaction> orders) {
		this.orders = orders;
	}

}