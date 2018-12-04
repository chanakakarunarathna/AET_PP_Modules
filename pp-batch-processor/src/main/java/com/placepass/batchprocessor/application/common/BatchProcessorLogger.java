package com.placepass.batchprocessor.application.common;

public enum BatchProcessorLogger {

    VENDOR("VENDOR NAME"), HITS_PER_PAGE("HITS_PER_PAGE"), PAGE_NUMBER("PAGE_NUMBER"), REFERENCE_NUMBER(
            "REFERENCE_NUMBER"), BOOKER_EMAIL("BOOKER_EMAIL"), CONNECTOR_BOOKING_STATUS(
                    "CONNECTOR_BOOKING_STATUS"), BOOKING_ID("BOOKING_ID"), PARTNER_ID(
                            "PARTNER_ID"), VENDOR_BOOKING_REFERENCE_ID("VENDOR_BOOKING_REFERENCE_ID");

    String name;

    private BatchProcessorLogger(String name) {
        this.name = name;
    }
}
