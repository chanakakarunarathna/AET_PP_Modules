package com.placepass.booking.domain.booking;

import java.util.Map;

import com.placepass.booking.domain.platform.BookingStatus;
import com.placepass.booking.domain.platform.Status;

public class BookingSummary {

    private Status overallStatus;

    private BookingStatus bookingStatus;

    private boolean cancelTriggerred;

    private boolean cancelSuccessful;

    private String cancelBookingId;

    private RefundMode refundMode;

    private float refundAmount;

    private Float cancellationFee;

    private String cancellationReasonCode;

    private boolean overallGoodStanding;

    private String overallGoodStandingDescription;

    private Map<String, String> extendedAttributes;

    public Status getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(Status overallStatus) {
        this.overallStatus = overallStatus;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public boolean isCancelTriggerred() {
        return cancelTriggerred;
    }

    public void setCancelTriggerred(boolean cancelTriggerred) {
        this.cancelTriggerred = cancelTriggerred;
    }

    public boolean isCancelSuccessful() {
        return cancelSuccessful;
    }

    public void setCancelSuccessful(boolean cancelSuccessful) {
        this.cancelSuccessful = cancelSuccessful;
    }

    public String getCancelBookingId() {
        return cancelBookingId;
    }

    public void setCancelBookingId(String cancelBookingId) {
        this.cancelBookingId = cancelBookingId;
    }

    public RefundMode getRefundMode() {
        return refundMode;
    }

    public void setRefundMode(RefundMode refundMode) {
        this.refundMode = refundMode;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Float getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Float cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public String getCancellationReasonCode() {
        return cancellationReasonCode;
    }

    public void setCancellationReasonCode(String cancellationReasonCode) {
        this.cancellationReasonCode = cancellationReasonCode;
    }

    public boolean isOverallGoodStanding() {
        return overallGoodStanding;
    }

    public void setOverallGoodStanding(boolean overallGoodStanding) {
        this.overallGoodStanding = overallGoodStanding;
    }

    public String getOverallGoodStandingDescription() {
        return overallGoodStandingDescription;
    }

    public void setOverallGoodStandingDescription(String overallGoodStandingDescription) {
        this.overallGoodStandingDescription = overallGoodStandingDescription;
    }

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

}
