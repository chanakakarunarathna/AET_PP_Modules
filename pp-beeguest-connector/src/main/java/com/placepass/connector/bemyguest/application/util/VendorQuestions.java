package com.placepass.connector.bemyguest.application.util;

public enum VendorQuestions {

    VENDOR_QUESTION_NAME_OF_HOTEL(1,
            "What is the name of the hotel for pickup/dropoff ?"), VENDOR_QUESTION_HOTEL_ADDRESS(2,
                    "What is the address of the hotel for pickup/dropoff ?"),

    VENDOR_QUESTION_FLIGHT_NUMBER(3,
            "What is the flight number for pickup/dropoff ?"), VENDOR_QUESTION_FLIGHT_DATE_AND_TIME(4,
                    "What is the flight arrival date time (YYYY-MM-DD HH:MM) for pickup/dropoff ?"), VENDOR_QUESTION_FLIGHT_DESTINATION(
                            5, "What is the flight destination airport IATA code for pickup/dropoff ?"),

    VENDOR_QUESTION_AIRPORT_PICKUP_TITLE(0, "Airport Pickup"), VENDOR_QUESTION_HOTEL_PICKUP_TITLE(0, "Hotel Pickup");

    private final String questionInfo;

    private final int id;

    private VendorQuestions(int id, String question) {
        this.questionInfo = question;
        this.id = id;
    }

    public String getQuestionInfo() {
        return questionInfo;
    }

    public int getId() {
        return id;
    }

}
