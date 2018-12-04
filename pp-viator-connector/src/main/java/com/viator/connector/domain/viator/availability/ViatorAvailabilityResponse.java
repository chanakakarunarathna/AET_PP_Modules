package com.viator.connector.domain.viator.availability;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorAvailabilityResponse extends ViatorGenericResponse {

    private ViatorAvailabilityResInfo data;

    public ViatorAvailabilityResInfo getData() {
        return data;
    }

    public void setData(ViatorAvailabilityResInfo data) {
        this.data = data;
    }
}
