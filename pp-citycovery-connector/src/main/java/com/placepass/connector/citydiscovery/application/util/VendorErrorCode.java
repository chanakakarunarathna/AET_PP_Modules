package com.placepass.connector.citydiscovery.application.util;

public enum VendorErrorCode {

    VENDOR_SUCCESS_MESSAGE(0,""),
    
    VENDOR_WRONG_ARGS(400, "Wrong arguments were input"),
    VENDOR_NOT_FOUND(404, "Resource not found"),
    VENDOR_UNAUTHORIZED(401, "Unauthorized Request"),
    VENDOR_FORBIDDEN(403, "Forbidden Request"),

    VENDOR_CONNECTION_TIMEOUT_ERROR(599, "Connection time-out while connecting to vendor"),
    VENDOR_READ_TIMEOUT_ERROR(598, "Read time-out while connecting to vendor"),
    QUESTION_NOT_FOUND_ERROR(204, "Questions not found for the product"),
    VENDOR_API_ERROR(404, "Vendor API error occured"),
    VENDOR_CONNECTION_ERROR(403, "Error connecting to vendor"),
    VENDOR_RETURN_UNMONITERED_STATUS(204, "VENDOR_RETURN_UNMONITERED_STATUS"),
    VENDOR_RETURN_EMPTY_RESPONSE(204, "Response is empty"),

    VENDOR_BOOKING_CONFIRMED(100,"Confirmed"),
    VENDOR_BOOKING_ON_REQUEST(101,"OnRequest"),
    VENDOR_BOOKING_CANCELLED(111,"Cancelled"),
    VENDOR_BOOKING_ERROR(113,"error"),
    VENDOR_BOOKING_NOT_AVAILABLE(113,"Not Available"),
    
    PRODUCT_NOT_FOUND(1,"Product not found"),
    PRODUCT_OPTION_NOT_FOUND(2,"Product option not found"),
    PRODUCT_OPTIONS_NOT_FOUND_FOR_GIVEN_DATE(2,"Booking Options are not available for this date."),
    INVALID_DATE_FOUND(3,"Invalid date found."),
    INVALID_MILITARY_TIME_FOUND(3,"Invalid military time found."),
    GONE(410, "Resource is no longer available"),
    METHOD_NOT_ALLOWED(405, "That method is not allowed"),
    UNWILLING_TO_PROCESS(431, "The server is unwilling to process the request"),
    OUT_OF_STOCK(430, "This product type is out of stock right now"),
    INTERNAL_ERROR(500, "API failed to process the request"),
    
    
    CONFIRMED(100, "Booking is Confirmed"),
    PENDING(101, "Booking is Pending"),
    AMENDED(102, "Booking is Amended"),
    CANCELLED(111, "Booking is Cancelled"),
    FAILED(112, "Booking is Failed"),
    REJECTED(113, "Booking is Rejected"),
    REQUEST_FAILED(114, "Booking Request Failed"),
    PERMITTED_EMAIL_VALIDATED(114,"Only permitted Emails are allowed"),
    UNKNOWN(115, "Booking Received Unknown Status"),
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
