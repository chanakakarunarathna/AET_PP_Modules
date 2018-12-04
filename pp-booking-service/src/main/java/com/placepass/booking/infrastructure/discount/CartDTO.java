package com.placepass.booking.infrastructure.discount;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private String cartId;

    private BookerDTO bookerDetails;

    private List<BookingOptionDTO> bookingOptions = new ArrayList<BookingOptionDTO>();

    private TotalDTO total;

    private List<FeeDTO> fees = new ArrayList<FeeDTO>();

    public CartDTO(String cartId, BookerDTO bookerDetails, List<BookingOptionDTO> bookingOptions, TotalDTO total) {
        super();
        this.cartId = cartId;
        this.bookerDetails = bookerDetails;
        this.bookingOptions = bookingOptions;
        this.total = total;
    }

    public String getCartId() {
        return cartId;
    }

    public BookerDTO getBookerDetails() {
        return bookerDetails;
    }

    public List<BookingOptionDTO> getBookingOptions() {
        return bookingOptions;
    }

    public TotalDTO getTotal() {
        return total;
    }

    public List<FeeDTO> getFees() {
        return fees;
    }

}
