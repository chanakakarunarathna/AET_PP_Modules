package com.placepass.connector.bemyguest.domain.placepass.algolia.product;

public class AlgoliaBookingQuestion {

    private Integer sortOrder;

    private String title;

    private String subTitle;

    private Integer questionId;

    private String stringQuestionId;

    private Boolean required;

    private String message;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getStringQuestionId() {
        return stringQuestionId;
    }

    public void setStringQuestionId(String stringQuestionId) {
        this.stringQuestionId = stringQuestionId;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
