package com.viator.connector.domain.viator.tourgrades;

import java.util.List;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorTourgradesResponse extends ViatorGenericResponse {

    private List<ViatorTourgradesResInfo> data;

    public List<ViatorTourgradesResInfo> getData() {
        return data;
    }

    public void setData(List<ViatorTourgradesResInfo> data) {
        this.data = data;
    }
}
