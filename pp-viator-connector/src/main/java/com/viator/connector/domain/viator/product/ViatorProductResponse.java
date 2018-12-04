package com.viator.connector.domain.viator.product;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorProductResponse extends ViatorGenericResponse {

    private ViatorProductResInfo data;

    public ViatorProductResInfo getData() {
        return data;
    }

    public void setData(ViatorProductResInfo data) {
        this.data = data;
    }

}
