package com.placepass.connector.common.cart;

import java.util.List;

import com.placepass.connector.common.common.ResultType;

public class GetBookingQuestionsRS {

    private ResultType resultType;

    private List<BookingQuestion> bookingQuestions;

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public List<BookingQuestion> getBookingQuestions() {
        return bookingQuestions;
    }

    public void setBookingQuestions(List<BookingQuestion> bookingQuestions) {
        this.bookingQuestions = bookingQuestions;
    }

}
