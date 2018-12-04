package com.placepass.connector.common.booking;

public enum VendorErrorCode {

    SUCCESS(0, "Success"),
    VENDOR_SUCCESS_MESSAGE(0,""),
    VENDOR_CONNECTOR_TIMEOUT_ERROR(-1, "Connection time-out while connecting to vendor connector"),
    VENDOR_CONNECTOR_CONNECTION_ERROR(-2, "Exception occurred while connecting to vendor connector"),
    //FIXME: temp enum value, need to remove later (specific for UA Connector)
    AUTHERIZATION_FAILED(100, "Provided booker email is not authorized to make a booking"),
    
    CONFIRMED(100, "Booking is Confirmed"),
    PENDING(101, "Booking is Pending"),
    AMENDED(102, "Booking is Amended"),
    CANCELLED(111, "Booking is Cancelled"),
    FAILED(112, "Booking is Failed"),
    REQUEST_FAILED(114, "Booking Request Failed"),
    REJECTED(113, "Booking is Rejected"),
    UNKNOWN(115, "Booking Received Unknown Status"),
    CANCEL_UNKNOWN(1115, "Booking Cancelation Received Unknown Status."),
    
    PRODUCT_NOT_FOUND(201,"Product not found"),
    PRODUCT_UNAVAILABLE(203,"Product unavailable"),
    QUESTION_NOT_FOUND_ERROR(204, "Questions not found for the product"),
    VENDOR_RETURN_UNMONITERED_STATUS(204, "Vendor return unmonitered status"),
    BOOKING_DETAILS_INCOMPLETE(205, "Booking details incomplete"),
    BOOKING_PARAMS_INVALID(206,"Booking parameters invalid"),
    
    PAYMENT_DECLINED(300, "Payment Declined"),
    
    BOOKING_REQUEST_BINDING_ERROR(400, "Booking Request Binding error occured"),
    VENDOR_CONNECTION_ERROR(403, "Error connecting to vendor"),
    VENDOR_API_ERROR(404, "Vendor API error occured"),
    UNKNOWN_VENDOR_ERROR(405, "Unknown vendor error occurred"),
    
    VENDOR_READ_TIMEOUT_ERROR(598, "Read time-out while connecting to vendor"),
    VENDOR_CONNECTION_TIMEOUT_ERROR(599, "Connection time-out while connecting to vendor");
    
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
