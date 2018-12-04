package com.placepass.connector.common.cart;

import com.placepass.connector.common.common.ResultType;

public class GetProductPriceRS {

    private ResultType resultType;

    // Total for this booking option.
    private Total total;

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

}
