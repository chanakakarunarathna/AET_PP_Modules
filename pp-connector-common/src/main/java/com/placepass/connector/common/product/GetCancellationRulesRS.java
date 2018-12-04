package com.placepass.connector.common.product;

import com.placepass.connector.common.common.BaseRS;

public class GetCancellationRulesRS extends BaseRS {

    private CancellationRules cancellationRules;

    public CancellationRules getCancellationRules() {
        return cancellationRules;
    }

    public void setCancellationRules(CancellationRules cancellationRules) {
        this.cancellationRules = cancellationRules;
    }

}
