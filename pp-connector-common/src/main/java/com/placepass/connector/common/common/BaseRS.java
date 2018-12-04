package com.placepass.connector.common.common;

public abstract class BaseRS {

    protected ResultType resultType = new ResultType();

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }
}
