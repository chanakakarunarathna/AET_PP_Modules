package com.placepass.booking.application.cart.dto;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "UpdateTravelerDetailsRequest")
public class UpdateTravelerDetailsRQ {

    @NotEmpty(message = "Product option id is required")
    private String productOptionId;

    @Valid
    @NotEmpty(message = "Travelers list is required")
    private List<TravelerDTO> travelers;

    public String getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(String productOptionId) {
        this.productOptionId = productOptionId;
    }

    public List<TravelerDTO> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<TravelerDTO> travelers) {
        this.travelers = travelers;
    }

}
