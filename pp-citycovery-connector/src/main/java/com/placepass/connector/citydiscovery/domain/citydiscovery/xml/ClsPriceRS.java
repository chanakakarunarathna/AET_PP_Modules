package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

public class ClsPriceRS {

    private ClsPriceInfoRS priceInfoRS;

    private ClsResultType resultType;

    public ClsPriceInfoRS getPriceInfoRS() {
        return priceInfoRS;
    }

    public void setPriceInfoRS(ClsPriceInfoRS priceInfoRS) {
        this.priceInfoRS = priceInfoRS;
    }

    public ClsResultType getResultType() {
        return resultType;
    }

    public void setResultType(ClsResultType resultType) {
        this.resultType = resultType;
    }

}
