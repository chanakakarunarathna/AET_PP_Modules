package com.viator.connector.application.util;

public enum VendorErrorCode {

    VENDOR_SUCCESS_MESSAGE(0,""),

    VENDOR_CONNECTION_TIMEOUT_ERROR(599, "Connection time-out while connecting to vendor."),
    VENDOR_READ_TIMEOUT_ERROR(598, "Read time-out while connecting to vendor."),
    QUESTION_NOT_FOUND_ERROR(204, "Questions not found for the product."),
    VENDOR_API_ERROR(404, "Vendor API error occured."),
    VENDOR_CONNECTION_ERROR(403, "Error connecting to vendor."),
    PRODUCT_NOT_FOUND(1,"Product not found"),
	VENDOR_RETURN_UNMONITERED_STATUS(204, "VENDOR_RETURN_UNMONITERED_STATUS"),

    CONFIRMED(100, "Booking is Confirmed."),
    PENDING(101, "Booking is Pending."),
    AMENDED(102, "Booking is Amended."),
    CANCELLED(111, "Booking is Cancelled."),
    FAILED(112, "Booking is Failed."),
    REQUEST_FAILED(114, "Booking Request Failed."),
	REJECTED(113, "Booking is Rejected."),
    UNKNOWN(115, "Booking Received Unknown Status."),
    CANCEL_UNKNOWN(1115, "Booking Cancelation Received Unknown Status.");

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
