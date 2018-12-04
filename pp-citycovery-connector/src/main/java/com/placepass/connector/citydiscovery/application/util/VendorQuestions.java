package com.placepass.connector.citydiscovery.application.util;

public enum VendorQuestions {

    VENDOR_QUESTION_NAME_OF_HOTEL(1, "What is the name of the hotel ?", "Hotel Pickup"), 
    VENDOR_QUESTION_HOTEL_ADDRESS(2, "What is the address of the hotel ?" , "Hotel Pickup"),
    VENDOR_QUESTION_BOOKING_COMMENTS(3, "Do you have any comments regarding the booking ?", "Booking Notes");

    private final String question;

    private final int id;

    private final String title;

    private VendorQuestions(int id, String question, String title) {
        this.question = question;
        this.id = id;
        this.title = title;
    }

    public String getQuestion() {
        return this.question;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
