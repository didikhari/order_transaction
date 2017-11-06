package com.salestock.didik.api.response;

import java.util.Date;

import com.salestock.didik.model.PaymentConfirmLog;

public class PaymentConfirmResponse {
	private String id;
	private String orderId;
	private Date confirmDate;
	private long amount;
	private String senderName;
	private String receipentAccountNumber;
	private String transferReceiptUrl;
	
	public PaymentConfirmResponse(PaymentConfirmLog confirmLog, String orderId) {
		if(confirmLog != null){
			this.id = confirmLog.getId();
			this.orderId = orderId;
			this.confirmDate = confirmLog.getConfirmDate();
			this.amount = confirmLog.getAmount();
			this.senderName = confirmLog.getSenderName();
			this.receipentAccountNumber = confirmLog.getReceipentAccountNumber();
			this.transferReceiptUrl = confirmLog.getTransferReceiptUrl();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceipentAccountNumber() {
		return receipentAccountNumber;
	}

	public void setReceipentAccountNumber(String receipentAccountNumber) {
		this.receipentAccountNumber = receipentAccountNumber;
	}

	public String getTransferReceiptUrl() {
		return transferReceiptUrl;
	}

	public void setTransferReceiptUrl(String transferReceiptUrl) {
		this.transferReceiptUrl = transferReceiptUrl;
	}
}
