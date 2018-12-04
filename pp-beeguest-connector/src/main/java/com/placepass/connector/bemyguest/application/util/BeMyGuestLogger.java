package com.placepass.connector.bemyguest.application.util;

public enum BeMyGuestLogger {

    PRODUCT_ID("PRODUCT_ID"),
    PRODUCT_OPTION_ID("PRODUCT_OPTION_ID"),
    PRODUCT_OPTION_DATE("PRODUCT_OPTION_DATE"),
    BOOKING_DATE("BOOKING_DATE"),
    PARTNER_ID("PARTNER_ID"),
    BOOKING_REFERENCE("BOOKING_REFERENCE"),
    VENDOR_METHOD("VENDOR_METHOD"),
    VENDOR_PRODUCT_ID("VENDOR_PRODUCT_ID"),
    VENDOR_PRODUCT_OPTION_ID("VENDOR_PRODUCT_OPTION_ID"),
    MONTH("MONTH"),
    CUR_CODE("CURRENCY_CODE"),
    YEAR("YEAR"),
    SESSION_ID("SESSION_ID"),
    HOTEL_ID("HOTEL_ID"),
    TRAVEL_DATE("TRAVEL_DATE"),
    TOUR_GRADE_CODE("TOUR_GRADE_CODE"),
    PAGE_NUMBER("PAGE_NUMBER"),
    HITS_PER_PAGE("HITS_PER_PAGE"),
    CART_ID("CART_ID"),
    BOOKER_EMAIL("BOOKER_EMAIL");
    
    private String description;
    
    private BeMyGuestLogger(String description){
        this.description = description;
    }
    
}
