package com.stripe.payment.connector.domain.charge;

import java.util.Map;

import com.stripe.model.Charge;
import com.stripe.payment.connector.application.PaymentProcessStatus;

public class ChargeTransactionResponse {

    private Charge charge;

    private Map<String, String> externalStatuses;

    private PaymentProcessStatus paymentStatus;

    public Charge getCharge() {
		return charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
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

	public void setPaymentStatus(PaymentProcessStatus	 paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
