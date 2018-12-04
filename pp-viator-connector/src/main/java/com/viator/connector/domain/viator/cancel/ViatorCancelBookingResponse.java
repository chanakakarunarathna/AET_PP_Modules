package com.viator.connector.domain.viator.cancel;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorCancelBookingResponse extends ViatorGenericResponse{

    private ViatorCancelBookingResDetails data;

    public ViatorCancelBookingResDetails getData() {
        return data;
    }

    public void setData(ViatorCancelBookingResDetails data) {
        this.data = data;
    }
    
}
