package com.stripe.payment.connector.domain.refund;

import java.util.Map;

import com.stripe.model.Refund;
import com.stripe.payment.connector.application.PaymentProcessStatus;

public class RefundTransactionResponse {

	private Refund refund;

	private Map<String, String> externalStatuses;

	private PaymentProcessStatus paymentStatus;

	public Refund getRefund() {
		return refund;
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}

	public Map<String, String> getExternalStatuses() {
		return externalStatuses;
	}

	public void setExternalStatuses(Map<String, String> externalStatuses) {
		this.externalStatuses = externalStatuses;
	}

	public PaymentProcessStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentProcessStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
