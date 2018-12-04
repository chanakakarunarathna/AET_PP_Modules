package com.placepass.booking.application.booking.dto;

import java.util.List;
import java.util.Map;

import com.placepass.booking.application.cart.dto.BookingOptionDTO;

public class VoucherDTO {

    private String id;

    private String vendorReference;
    
    private String htmlContent;

    private String voucherType;

    private List<String> urls;

    private Map<String, String> extendedAttributes;

    private List<BookingOptionDTO> bookingOptions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorReference() {
        return vendorReference;
    }

    public void setVendorReference(String vendorReference) {
        this.vendorReference = vendorReference;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

    public List<BookingOptionDTO> getBookingOptions() {
        return bookingOptions;
    }

    public void setBookingOptions(List<BookingOptionDTO> bookingOptions) {
        this.bookingOptions = bookingOptions;
    }
}
