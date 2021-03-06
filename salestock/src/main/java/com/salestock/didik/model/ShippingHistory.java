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
 * ShippingHistory generated by hbm2java
 */
@Entity
@Table(name = "shipping_history")
public class ShippingHistory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String trackingCode;
	private String description;
	private Date createDate;
	private String origin;
	private String destination;
	private String service;

	public ShippingHistory() {
	}

	public ShippingHistory(String id) {
		this.id = id;
	}

	public ShippingHistory(String id, String trackingCode, String description,
			Date createDate, String origin, String destination, String service) {
		this.id = id;
		this.trackingCode = trackingCode;
		this.description = description;
		this.createDate = createDate;
		this.origin = origin;
		this.destination = destination;
		this.service = service;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 100)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "tracking_code", length = 100)
	public String getTrackingCode() {
		return this.trackingCode;
	}

	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "origin")
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "destination")
	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Column(name = "service")
	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

}
