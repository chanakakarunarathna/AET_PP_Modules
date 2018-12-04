package com.placepass.product.application.pricematch.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Quantity")
public class QuantityDTO {
	
	@NotNull(message = "Age band id is required")
    private Integer ageBandId;

    private String ageBandLabel;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    public Integer getAgeBandId() {
        return ageBandId;
    }

    public void setAgeBandId(Integer ageBandId) {
        this.ageBandId = ageBandId;
    }

    public String getAgeBandLabel() {
        return ageBandLabel;
    }

    public void setAgeBandLabel(String ageBandLabel) {
        this.ageBandLabel = ageBandLabel;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
