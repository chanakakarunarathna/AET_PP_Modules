package com.viator.connector.domain.viator.mybookings;

import java.util.List;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorMybookingsResponse extends ViatorGenericResponse {

    private List<ViatorMybookingsResDetails> data;

    public List<ViatorMybookingsResDetails> getData() {
        return data;
    }

    public void setData(List<ViatorMybookingsResDetails> data) {
        this.data = data;
    }

}
