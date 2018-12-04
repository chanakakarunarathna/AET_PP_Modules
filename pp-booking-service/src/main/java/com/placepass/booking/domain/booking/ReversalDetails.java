package com.placepass.booking.domain.booking;

import java.time.Instant;
import java.util.Map;

public class ReversalDetails {

	private Instant attemptedTime;

	private boolean reversalSuccessful;

	private String reversalReason;

	private Map<String, String> externalStatuses;

	private String reversalTxId;

	private String extReversalTxId;

	private Map<String, String> additionalAttributes;

	private PaymentStatus reversalStatus;

	public Instant getAttemptedTime() {
		return attemptedTime;
	}

	public void setAttemptedTime(Instant attemptedTime) {
		this.attemptedTime = attemptedTime;
	}

	public boolean isReversalSuccessful() {
		return reversalSuccessful;
	}

	public void setReversalSuccessful(boolean reversalSuccessful) {
		this.reversalSuccessful = reversalSuccessful;
	}

	public Map<String, String> getExternalStatuses() {
		return externalStatuses;
	}

	public void setExternalStatuses(Map<String, String> externalStatuses) {
		this.externalStatuses = externalStatuses;
	}

	public Map<String, String> getAdditionalAttributes() {
		return additionalAttributes;
	}

	public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}

	public PaymentStatus getReversalStatus() {
		return reversalStatus;
	}

	public void setReversalStatus(PaymentStatus reversalStatus) {
		this.reversalStatus = reversalStatus;
	}

	public String getReversalTxId() {
		return reversalTxId;
	}

	public void setReversalTxId(String reversalTxId) {
		this.reversalTxId = reversalTxId;
	}

	public String getExtReversalTxId() {
		return extReversalTxId;
	}

	public void setExtReversalTxId(String extReversalTxId) {
		this.extReversalTxId = extReversalTxId;
	}

	public String getReversalReason() {
		return reversalReason;
	}

	public void setReversalReason(String reversalReason) {
		this.reversalReason = reversalReason;
	}

}
