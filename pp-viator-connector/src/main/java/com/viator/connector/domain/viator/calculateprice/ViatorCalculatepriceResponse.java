package com.viator.connector.domain.viator.calculateprice;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorCalculatepriceResponse extends ViatorGenericResponse {

    private ViatorCalculatepriceResInfo data;

    public ViatorCalculatepriceResInfo getData() {
        return data;
    }

    public void setData(ViatorCalculatepriceResInfo data) {
        this.data = data;
    }

}
