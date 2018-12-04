package com.stripe.payment.connector.application.common;

import java.util.Map;

public class ConnectorErrorResponse {

    private Map<String, String> externalStatuses;

    public Map<String, String> getExternalStatuses() {
        return externalStatuses;
    }

    public void setExternalStatuses(Map<String, String> externalStatuses) {
        this.externalStatuses = externalStatuses;
    }

}
