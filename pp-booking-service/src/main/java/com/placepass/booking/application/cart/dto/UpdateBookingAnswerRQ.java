package com.placepass.booking.application.cart.dto;

import java.util.List;
import javax.validation.Valid;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "UpdateBookingAnswerRequest")
public class UpdateBookingAnswerRQ {

    @Valid
    private List<QuestionAnswerDTO> questionAnswers;

    public List<QuestionAnswerDTO> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswerDTO> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

}
