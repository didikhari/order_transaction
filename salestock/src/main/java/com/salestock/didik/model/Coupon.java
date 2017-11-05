package com.salestock.didik.model;

// Generated Nov 3, 2017 5:45:10 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Coupon generated by hbm2java
 */
@Entity
@Table(name = "coupon")
public class Coupon implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String code;
	private String value;
	private Date startDate;
	private Date endDate;
	private Integer stock;
	private boolean percentage;
	
	public Coupon() {
	}

	public Coupon(String id) {
		this.id = id;
	}

	public Coupon(String id, String code, String value, Date startDate,
			Date endDate, Integer quantity) {
		this.id = id;
		this.code = code;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.stock = quantity;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 100)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "code", length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "value")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "quantity")
	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer quantity) {
		this.stock = quantity;
	}

	@Column(name = "is_percentage")
	public boolean isPercentage() {
		return percentage;
	}

	public void setPercentage(boolean percentage) {
		this.percentage = percentage;
	}

}
