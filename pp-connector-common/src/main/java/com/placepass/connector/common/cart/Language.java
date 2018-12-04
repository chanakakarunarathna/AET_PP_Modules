package com.placepass.connector.common.cart;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Language")
public class Language {

    private String code;
    
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
