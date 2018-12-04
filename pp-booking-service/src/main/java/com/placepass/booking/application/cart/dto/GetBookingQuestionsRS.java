package com.placepass.booking.application.cart.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "GetBookingQuestionsResponse")
public class GetBookingQuestionsRS {

    private List<BookingQuestionDTO> bookingQuestions;

    public List<BookingQuestionDTO> getBookingQuestions() {
        return bookingQuestions;
    }

    public void setBookingQuestions(List<BookingQuestionDTO> bookingQuestions) {
        this.bookingQuestions = bookingQuestions;
    }

}
