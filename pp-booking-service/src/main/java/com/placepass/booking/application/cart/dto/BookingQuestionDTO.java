package com.placepass.booking.application.cart.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "BookingQuestion")
public class BookingQuestionDTO {

    private String productId;

    private String productOptionId;

    private String questionId;

    private String title;

    private String subTitle;

    private String message;

    private Boolean required;

    private BookingAnswerDTO bookingAnswer;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public BookingAnswerDTO getBookingAnswer() {
        return bookingAnswer;
    }

    public void setBookingAnswer(BookingAnswerDTO bookingAnswer) {
        this.bookingAnswer = bookingAnswer;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(String productOptionId) {
        this.productOptionId = productOptionId;
    }

}
