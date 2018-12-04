package com.placepass.booking.application.booking.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.placepass.booking.application.cart.dto.BookerDTO;
import com.placepass.booking.application.cart.dto.BookingOptionDTO;
import com.placepass.booking.application.cart.dto.TotalDTO;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Booking")
public class GetBookingDTO {

    private String bookingId;

    private String partnerId;

    private String customerId;

    private String bookingReference;

    private List<BookingOptionDTO> bookingOptions;

    private TotalDTO total;

    private BookerDTO bookerDetails;

    private List<PaymentDTO> payment;

    private BookingSummaryDTO bookingSummary;

    private Map<String, String> extendedAttributes;

    private String createdTime;

    private String updatedTime;

    private LoyaltyAccountDTO loyaltyAccount;

    private List<FeeDTO> fees;

    private List<DiscountDTO> discounts;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public List<BookingOptionDTO> getBookingOptions() {
        return bookingOptions;
    }

    public void setBookingOptions(List<BookingOptionDTO> bookingOptions) {
        this.bookingOptions = bookingOptions;
    }

    public TotalDTO getTotal() {
        return total;
    }

    public void setTotal(TotalDTO total) {
        this.total = total;
    }

    public BookerDTO getBookerDetails() {
        return bookerDetails;
    }

    public void setBookerDetails(BookerDTO bookerDetails) {
        this.bookerDetails = bookerDetails;
    }

    public List<PaymentDTO> getPayment() {
        return payment;
    }

    public void setPayment(List<PaymentDTO> payment) {
        this.payment = payment;
    }

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LoyaltyAccountDTO getLoyaltyAccount() {
        return loyaltyAccount;
    }

    public void setLoyaltyAccount(LoyaltyAccountDTO loyaltyAccount) {
        this.loyaltyAccount = loyaltyAccount;
    }

    public BookingSummaryDTO getBookingSummary() {
        return bookingSummary;
    }

    public void setBookingSummary(BookingSummaryDTO bookingSummary) {
        this.bookingSummary = bookingSummary;
    }

    public List<DiscountDTO> getDiscounts() {
        if (discounts == null) {
            discounts = new ArrayList<>();
        }

        return discounts;
    }

    public List<FeeDTO> getFees() {
        if (fees == null) {
            fees = new ArrayList<>();
        }
        return fees;
    }

}
