package com.placepass.booking.application.cart.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class QuestionAnswerDTO {

    @NotEmpty(message = "Product id is required")
    private String productId;

    @NotEmpty(message = "Product option id is required")
    private String productOptionId;

    @NotEmpty(message = "Booking question id is required")
    private String bookingQuestionId;

    private String answer;

    public String getBookingQuestionId() {
        return bookingQuestionId;
    }

    public void setBookingQuestionId(String bookingQuestionId) {
        this.bookingQuestionId = bookingQuestionId;
    }

    public String getAnswer() {
        if (this.answer == null) {
            return "";
        }
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(String productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
