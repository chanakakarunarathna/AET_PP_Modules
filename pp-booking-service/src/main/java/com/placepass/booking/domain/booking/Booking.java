package com.placepass.booking.domain.booking;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.annotation.Id;

import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.booking.domain.platform.Status;
import com.placepass.connector.common.booking.VendorErrorCode;

public class Booking {

    @Id
    private String id;

    private String partnerId;

    private String customerId;

    /**
     * This is 8 character alphanumeric, generated internally to share with the customer once the booking is placed.
     * This may be recycled within partner or customer and hence will not be globally unique.
     */
    private String bookingReference;

    // FIXME: this again can be a list if vendorBookingReferenceId; if one booking is placed for one cart. But if we go
    // with multiple bookings for one cart (per vendor, a booking is created), then this is fine. For latter payment
    // related complexity comes into play. - these decisions are still pending and multiple vendor booking is not part
    // of P1/R1.
    // external booking id
    private String vendorBookingRefId;

    private List<BookingOption> bookingOptions;

    private Total total;

    private Booker bookerDetails;

    private String cartId;

    /**
     * At a minimum a successful booking would have one payment transaction.
     */
    private List<Payment> payments;

    @Deprecated
    private BookingState bookingState;

    //Will be replaced by BookingSummary->bookingStatus, after the removal
    @Deprecated
    private Status bookingStatus;

    /**
     * Booking is closed, when bookingState is {@link BookingState#BOOKING_CONFIRMATION} or
     * {@link BookingState#BOOKING_PENDING} & status is {@link PlatformStatus#SUCCESS} or {@link PlatformStatus#PENDING}
     * . A closed booking can be cancelled and refunded.
     */
    private boolean bookingClosed;

    // Vouchers or anything that may be returned by a vendor for a booking
    // private List<Deliverable> deliverables;

    // TODO - we may need to maintain a set of rules specified by the vendors.
    // private <BookingRules> bookingRules;

    private Map<String, String> extendedAttributes = new HashMap<String, String>();

    private Instant createdTime;

    private Instant updatedTime;

    private LoyaltyAccount loyaltyAccount;

    private List<BookingItem> bookingItems;

    /**
     * This field indicates if manual intervention is required to reverse a payment or a booking.
     */
    @Deprecated
    private boolean isInGoodStanding;

    /**
     * This will have a note if the booking is not in good standing(If Manual intervention is required)
     */
    @Deprecated
    private String isInGoodStandingDescription;

    private BookingSummary bookingSummary;

    private List<BookingEvent> bookingEvents = new ArrayList<BookingEvent>();
    
    private List<Fee> fees = new ArrayList<Fee>();
    
    private List<Discount> discounts = new ArrayList<Discount>();

    /**
     * Update booking close.
     */
    public void updateBookingClose() {
        if ((this.bookingState == BookingState.BOOKING_CONFIRMATION && (this.bookingStatus != null && this.bookingStatus
                .getStatus() == PlatformStatus.SUCCESS))
                || (this.bookingState == BookingState.BOOKING_PENDING && (this.bookingStatus != null && this.bookingStatus
                        .getStatus() == PlatformStatus.PENDING))) {
            this.bookingClosed = true;
        } else {
            this.bookingClosed = false;
        }
    }

    public void updateGoodStandingFromPendingToCancel() {
        this.isInGoodStanding = false;
        this.isInGoodStandingDescription = "Booking has been cancelled by vendor";
    }

    public void updateGoodStandingFromPendingToUnknown(String vendorStatus) {
        this.isInGoodStanding = false;
        this.isInGoodStandingDescription = "Booking has received unknown status ";
        if (vendorStatus != null) {
            this.isInGoodStandingDescription += vendorStatus;
        }
    }

    public void updateIsInGoodStanding() {
        boolean isInGoodStanding = true;
        String description = "";

        Optional<Payment> salePayments = this.payments.stream()
                .filter(pay -> PaymentType.SALE.equals(pay.getPaymentType())).findFirst();
        if (salePayments.isPresent()) {
            Payment salePayment = salePayments.get();
            if (PaymentStatus.PAYMENT_ISSUER_TIMEOUT == salePayment.getPaymentStatus()
                    || PaymentStatus.PAYMENT_PROCESSING_ERROR == salePayment.getPaymentStatus()
                    || PaymentStatus.PAYMENT_GATEWAY_CONNECTION_ERROR == salePayment.getPaymentStatus()) {
                isInGoodStanding = false;
                description = "Payment timed out; ";
            }
        }

        if (bookingStatus != null && bookingStatus.getExternalStatus() != null) {
            String externalStatusCode = bookingStatus.getExternalStatus().get("code");

            if (externalStatusCode != null
                    && (externalStatusCode
                            .equals(String.valueOf(VendorErrorCode.VENDOR_CONNECTOR_TIMEOUT_ERROR.getId()))
                            || externalStatusCode.equals(String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR
                                    .getId())) || externalStatusCode.equals(String
                            .valueOf(VendorErrorCode.VENDOR_READ_TIMEOUT_ERROR.getId())))) {
                isInGoodStanding = false;
                description += "Booking timed out; ";
            }
        }

        if (((PlatformStatus.BOOKING_FAILED == bookingStatus.getStatus()
                || PlatformStatus.BOOKING_REJECTED == bookingStatus.getStatus() || PlatformStatus.CANCELLED == bookingStatus
                .getStatus()))
                && salePayments.isPresent()
                && !salePayments.get().getReversalDetails().isReversalSuccessful()) {

            if (PaymentStatus.PAYMENT_ISSUER_TIMEOUT == salePayments.get().getReversalDetails().getReversalStatus()
                    || PaymentStatus.PAYMENT_PROCESSING_ERROR == salePayments.get().getReversalDetails()
                            .getReversalStatus()
                    || PaymentStatus.PAYMENT_GATEWAY_CONNECTION_ERROR == salePayments.get().getReversalDetails()
                            .getReversalStatus()) {
                isInGoodStanding = false;
                description += "Reversal timed out after a booking failure or timed out; ";
            }
            if (PaymentStatus.PAYMENT_REVERSAL_FAILED == salePayments.get().getReversalDetails().getReversalStatus()) {
                isInGoodStanding = false;
                description += "Reversal failed after a booking failure or timed out; ";
            }
        }

        this.isInGoodStanding = isInGoodStanding;
        this.isInGoodStandingDescription = description;
    }

    public boolean isPending() {

        return (bookingStatus.getStatus() != null && PlatformStatus.PENDING.name().equals(
                bookingStatus.getStatus().name()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getVendorBookingRefId() {
        return vendorBookingRefId;
    }

    public void setVendorBookingRefId(String vendorBookingRefId) {
        this.vendorBookingRefId = vendorBookingRefId;
    }

    public List<BookingOption> getBookingOptions() {
        return bookingOptions;
    }

    public void setBookingOptions(List<BookingOption> bookingOptions) {
        this.bookingOptions = bookingOptions;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public Booker getBookerDetails() {
        return bookerDetails;
    }

    public void setBookerDetails(Booker bookerDetails) {
        this.bookerDetails = bookerDetails;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<Payment> getPayments() {
        if (payments == null) {
            payments = new ArrayList<>();
        }
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public BookingState getBookingState() {
        return bookingState;
    }

    public void setBookingState(BookingState bookingState) {
        this.bookingState = bookingState;
    }

    public Status getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Status bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public boolean isBookingClosed() {
        return bookingClosed;
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

    public LoyaltyAccount getLoyaltyAccount() {
        return loyaltyAccount;
    }

    public void setLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
        this.loyaltyAccount = loyaltyAccount;
    }

    public List<BookingItem> getBookingItems() {
        return bookingItems;
    }

    public void setBookingItems(List<BookingItem> bookingItems) {
        this.bookingItems = bookingItems;
    }

    public boolean isInGoodStanding() {
        return isInGoodStanding;
    }

    public String getIsInGoodStandingDescription() {
        return isInGoodStandingDescription;
    }

    public BookingSummary getBookingSummary() {
        return bookingSummary;
    }

    public void setBookingSummary(BookingSummary bookingSummary) {
        this.bookingSummary = bookingSummary;
    }

    public List<BookingEvent> getBookingEvents() {
    	
        //Will be removed after the data migration
    	if (bookingEvents == null){
    		return new ArrayList<BookingEvent>();
    	}
    	
        return bookingEvents;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
    
    

}
