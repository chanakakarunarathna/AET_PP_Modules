package com.placepass.booking.application.booking.dto;

import java.util.ArrayList;
import java.util.List;

import com.placepass.booking.application.cart.dto.TotalDTO;
import com.placepass.booking.domain.platform.Status;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "CreateBookingResponse")
public class CreateBookingRS {

    private String partnerId;

    private String customerId;

    private String cartId;

    private String bookingId;

    private String bookingReference;

    private Status status;

    private List<PaymentDTO> payment;
    
    private List<FeeDTO> fees;
    
    private List<DiscountDTO> discounts;
    
    private TotalDTO total;

    private BookingSummaryDTO bookingSummary;


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

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<PaymentDTO> getPayment() {
        return payment;
    }

    public void setPayment(List<PaymentDTO> payment) {
        this.payment = payment;
    }

    public TotalDTO getTotal() {
        return total;
    }

    public void setTotal(TotalDTO total) {
        this.total = total;
    }

    public List<DiscountDTO> getDiscounts() {
        if (discounts == null) {
            discounts = new ArrayList<DiscountDTO>();
        }

        return discounts;
    }

    public List<FeeDTO> getFees() {
        if (fees == null) {
            fees = new ArrayList<FeeDTO>();
        }
        return fees;
    }
    
	public BookingSummaryDTO getBookingSummary() {
		return bookingSummary;
	}

	public void setBookingSummary(BookingSummaryDTO bookingSummary) {
		this.bookingSummary = bookingSummary;
	}

}
