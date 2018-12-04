package com.placepass.product.application.common;

public abstract class BaseRS {

    protected ResultTypeCDTO resultType = new ResultTypeCDTO();

    public ResultTypeCDTO getResultType() {
        return resultType;
    }

    public void setResultType(ResultTypeCDTO resultType) {
        this.resultType = resultType;
    }
}
