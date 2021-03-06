package com.salestock.didik.model;

// Generated Nov 3, 2017 5:45:10 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PaymentConfirmLog generated by hbm2java
 */
@Entity
@Table(name = "payment_confirm_log")
public class PaymentConfirmLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private OrderTransaction orderTransaction;
	private Date confirmDate;
	private long amount;
	private String senderName;
	private String receipentAccountNumber;
	private String transferReceiptUrl;

	public PaymentConfirmLog() {
	}

	public PaymentConfirmLog(String id, OrderTransaction order, Date confirmDate,
			long amount, String receipentAccountNumber, String transferReceiptUrl) {
		this.id = id;
		this.orderTransaction = order;
		this.confirmDate = confirmDate;
		this.amount = amount;
		this.receipentAccountNumber = receipentAccountNumber;
		this.transferReceiptUrl = transferReceiptUrl;
	}

	public PaymentConfirmLog(String id, OrderTransaction order, Date confirmDate,
			long amount, String senderName, String receipentAccountNumber,
			String transferReceiptUrl) {
		this.id = id;
		this.orderTransaction = order;
		this.confirmDate = confirmDate;
		this.amount = amount;
		this.senderName = senderName;
		this.receipentAccountNumber = receipentAccountNumber;
		this.transferReceiptUrl = transferReceiptUrl;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	public OrderTransaction getOrderTransaction() {
		return this.orderTransaction;
	}

	public void setOrderTransaction(OrderTransaction order) {
		this.orderTransaction = order;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirm_date", nullable = false, length = 19)
	public Date getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name = "amount", nullable = false, precision = 10, scale = 0)
	public long getAmount() {
		return this.amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Column(name = "sender_name", length = 250)
	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@Column(name = "receipent_account_number", nullable = false)
	public String getReceipentAccountNumber() {
		return this.receipentAccountNumber;
	}

	public void setReceipentAccountNumber(String receipentAccountNumber) {
		this.receipentAccountNumber = receipentAccountNumber;
	}

	@Column(name = "transfer_receipt_url", nullable = false, length = 65535)
	public String getTransferReceiptUrl() {
		return this.transferReceiptUrl;
	}

	public void setTransferReceiptUrl(String transferReceiptUrl) {
		this.transferReceiptUrl = transferReceiptUrl;
	}

}
