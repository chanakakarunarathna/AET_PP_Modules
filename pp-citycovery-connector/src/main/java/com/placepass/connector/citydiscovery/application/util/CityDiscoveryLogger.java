package com.placepass.connector.citydiscovery.application.util;

public enum CityDiscoveryLogger {

    BOOKING_ID("BOOKING_ID"),
    PRODUCT_ID("PRODUCT_ID"),
    PRODUCT_OPTION_ID("PRODUCT_OPTION_ID"),
    BOOKING_DATE("BOOKING_DATE"),
    PARTNER_ID("PARTNER_ID"),
    BOOKING_REFERENCE("BOOKING_REFERENCE"),
    VENDOR_METHOD("VENDOR_METHOD"),
    PRODUCT_OPTION_DATE("PRODUCT_OPTION_DATE"),
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
    BOOKER_EMAIL("BOOKER_EMAIL"),
    CANCELATION_TYPE("CANCELATION_TYPE"),
    BOOKING_STATUS("BOOKING_STATUS");
    
    private String description;
    
    private CityDiscoveryLogger(String description){
        this.description = description;
    }
    
}
