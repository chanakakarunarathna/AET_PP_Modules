package com.placepass.booking.domain.booking.cancel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.placepass.booking.domain.booking.PaymentStatus;
import com.placepass.booking.domain.booking.RefundMode;
import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.booking.domain.platform.Status;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.VendorErrorCode;

public class CancelBookingTransaction {

    private String transactionId;

    private CancelBookingState state;

    private List<Refund> refunds;

    private RefundMode refundMode;

    private Float cancellationFee;

    private List<CancelledBookingItem> cancelledBookingItems;

    private Status cancelStatus;

    private Map<String, String> extendedAttributes;

    private Instant createdTime;

    private Instant updatedTime;

    private String cancellationReasonCode;

    private boolean isManualCancel;

    /**
     * This field indicates if manual intervention is required in case of a booking cancellation time out or refund
     * failure/timeout.
     */
    private boolean isInGoodStanding;

    /**
     * This will have a note if the cancel booking transaction is not in good standing(If Manual intervention is
     * required)
     */
    private String isInGoodStandingDescription;

    public void updateIsInGoodStanding() {
        boolean isInGoodStanding = true;
        String description = "";

        if (PlatformStatus.BOOKING_CANCEL_TIMEOUT == this.cancelStatus.getStatus()) {
            isInGoodStanding = false;
            description += "Booking cancel timed out; ";
        }

        if (this.refunds != null && !this.refunds.isEmpty()) {
            Refund refund = refunds.get(0);
            if (PaymentStatus.PAYMENT_ISSUER_TIMEOUT == refund.getRefundStatus()
                    || PaymentStatus.PAYMENT_PROCESSING_ERROR == refund.getRefundStatus()
                    || PaymentStatus.PAYMENT_GATEWAY_CONNECTION_ERROR == refund.getRefundStatus()) {
                isInGoodStanding = false;
                description += "Refund timed out after a successful booking cancellation; ";
            } else if (PaymentStatus.PAYMENT_REVERSAL_SUCCESS != refund.getRefundStatus()) {
                isInGoodStanding = false;
                description += "Refund failed after a successful booking cancellation; ";
            }
        }

        this.isInGoodStanding = isInGoodStanding;
        this.isInGoodStandingDescription = description;
    }

    public void updateIsInGoodStanding(CancelBookingRS cancelBookingCRS) {
        boolean isInGoodStanding = true;
        String description = "";

        if ((cancelBookingCRS != null) && (VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId() == cancelBookingCRS.getResultType().getCode()
                || VendorErrorCode.VENDOR_READ_TIMEOUT_ERROR.getId() == cancelBookingCRS.getResultType().getCode()
                || VendorErrorCode.VENDOR_CONNECTOR_TIMEOUT_ERROR.getId() == cancelBookingCRS.getResultType().getCode()
                || VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR.getId() == cancelBookingCRS.getResultType().getCode())) {
            isInGoodStanding = false;
            description += "Booking cancel timed out; ";
        }

        if ((cancelBookingCRS != null) && (VendorErrorCode.CANCEL_UNKNOWN.getId() == cancelBookingCRS.getResultType().getCode())) {
            isInGoodStanding = false;
            description += "Booking cancellation unknown; ";
        }

        if (PlatformStatus.REFUND_TIMEOUT == this.cancelStatus.getStatus()) {
            isInGoodStanding = false;
            description += "Refund time out; ";
        }

        if (this.refunds != null && !this.refunds.isEmpty()) {

            Refund refund = refunds.get(0);

            if (PaymentStatus.PAYMENT_REVERSAL_SUCCESS == refund.getRefundStatus()
                    && PlatformStatus.BOOKING_CANCEL_FAILED == this.cancelStatus.getStatus()) {

                isInGoodStanding = false;
                description += "Force booking cancellation fail after a successful refund; ";

            } else if (PaymentStatus.PAYMENT_REVERSAL_SUCCESS == refund.getRefundStatus()
                    && PlatformStatus.BOOKING_CANCEL_TIMEOUT == this.cancelStatus.getStatus()) {

                isInGoodStanding = false;
                description += "Manual booking cancellation time out after a successful refund; ";
            }
        }

        this.isInGoodStanding = isInGoodStanding;
        this.isInGoodStandingDescription = description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public CancelBookingState getState() {
        return state;
    }

    public void setState(CancelBookingState state) {
        this.state = state;
    }

    public List<Refund> getRefunds() {
        if (refunds == null) {
            refunds = new ArrayList<>();
        }
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public RefundMode getRefundMode() {
        return refundMode;
    }

    public void setRefundMode(RefundMode refundMode) {
        this.refundMode = refundMode;
    }

    public Float getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Float cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public List<CancelledBookingItem> getCancelledBookingItems() {
        return cancelledBookingItems;
    }

    public void setCancelledBookingItems(List<CancelledBookingItem> cancelledBookingItems) {
        this.cancelledBookingItems = cancelledBookingItems;
    }

    public Status getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Status cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isInGoodStanding() {
        return isInGoodStanding;
    }

    public void setInGoodStanding(boolean isInGoodStanding) {
        this.isInGoodStanding = isInGoodStanding;
    }

    public String getIsInGoodStandingDescription() {
        return isInGoodStandingDescription;
    }

    public void setIsInGoodStandingDescription(String isInGoodStandingDescription) {
        this.isInGoodStandingDescription = isInGoodStandingDescription;
    }

    public String getCancellationReasonCode() {
        return cancellationReasonCode;
    }

    public void setCancellationReasonCode(String cancellationReasonCode) {
        this.cancellationReasonCode = cancellationReasonCode;
    }

    public boolean isManualCancel() {
        return isManualCancel;
    }

    public void setManualCancel(boolean isManualCancel) {
        this.isManualCancel = isManualCancel;
    }

}