package com.viator.connector.application.util;

public enum ViatorLogger {

    PRODUCT_ID("PRODUCT_ID"),
    PRODUCT_OPTION_ID("PRODUCT_OPTION_ID"),
    BOOKING_DATE("BOOKING_DATE"),
    TRAVEL_DATE("TRAVEL_DATE"),
    PARTNER_ID("PARTNER_ID"),
    CART_ID("CART_ID"),
    BOOKING_REFERENCE("BOOKING_REFERENCE"),
    BOOKING_ID("BOOKING_ID"),
    BOOKING_STATUS("BOOKING_STATUS"),
    VENDOR_METHOD("VENDOR_METHOD"),
    PRODUCT_OPTION_DATE("PRODUCT_OPTION_DATE"),
    MONTH("MONTH"),
    YEAR("YEAR"),
    CUR_CODE("CURRENCY_CODE"),
    SESSION_ID("SESSION_ID"),
    HOTEL_ID("HOTEL_ID"),
    TOUR_GRADE_CODE("TOUR_GRADE_CODE"),
    PAGE_NUMBER("PAGE_NUMBER"),
    HITS_PER_PAGE("HITS_PER_PAGE"),
    BOOKER_EMAIL("BOOKER_EMAIL"),
    PRICE_VALIDATION("PRICE_VALIDATION"),
    CANCELATION_TYPE("CANCELATION_TYPE");
    
    private String description;
    
    private ViatorLogger(String description){
        this.description = description;
    }
    
}
