package com.placepass.booking.application.cart.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "LanguageOption")
public class LanguageOptionDTO {

    private String languageOptionCode;

    public String getLanguageOptionCode() {
        return languageOptionCode;
    }

    public void setLanguageOptionCode(String languageOptionCode) {
        this.languageOptionCode = languageOptionCode;
    }

}
