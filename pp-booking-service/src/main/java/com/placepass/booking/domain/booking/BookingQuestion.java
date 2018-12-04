package com.placepass.booking.domain.booking;

public class BookingQuestion {

    private String productOptionId;

    private String questionId;

    private String title;

    private String subTitle;

    private String message;

    private Boolean required;

    private BookingAnswer bookingAnswer;

    public String getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(String productOptionId) {
        this.productOptionId = productOptionId;
    }

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

    public BookingAnswer getBookingAnswer() {
        return bookingAnswer;
    }

    public void setBookingAnswer(BookingAnswer bookingAnswer) {
        this.bookingAnswer = bookingAnswer;
    }

}
