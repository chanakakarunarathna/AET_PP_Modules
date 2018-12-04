package com.placepass.connector.bemyguest.application.util;

public enum VendorErrorCode {

    VENDOR_CONNECTION_TIMEOUT_ERROR(599, "Connection time-out while connecting to vendor"),
    VENDOR_READ_TIMEOUT_ERROR(598, "Read time-out while connecting to vendor"),
    QUESTION_NOT_FOUND_ERROR(204, "Questions not found for the product"),
    VOUCHER_NOT_FOUND_ERROR(205, "Vouchers not found for the booking"),
    REVIEWS_NOT_FOUND_ERROR(205, "Reviews not found for the product"),
    VENDOR_API_ERROR(404, "Vendor API error occured"),
    VENDOR_CONNECTION_ERROR(403, "Error connecting to vendor"),
    VENDOR_REQUEST_FAILED_ERROR(403,"Request Failed"),
    VENDOR_SUCCESS_MESSAGE(0,""),

	PRODUCT_NOT_FOUND(1,"Product not found"),
	PRODUCT_OPTIONS_NOT_FOUND(2,"Product options not found for the given date"),

    CONFIRMED(100, "Booking is Confirmed"),
    PENDING(101, "Booking is Pending"),
    AMENDED(102, "Booking is Amended"),
    CANCELLED(111, "Booking is Cancelled"),
    FAILED(112, "Booking is Failed"),
    REQUEST_FAILED(114, "Booking Request Failed");

    private final int id;
    private final String msg;
    
    private VendorErrorCode(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
    
    public int getId() {
        return this.id;
     }
}
