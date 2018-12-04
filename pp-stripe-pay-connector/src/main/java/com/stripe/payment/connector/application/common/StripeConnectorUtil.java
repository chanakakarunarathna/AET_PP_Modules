package com.stripe.payment.connector.application.common;

import java.time.Instant;
import java.util.Date;

public class StripeConnectorUtil {

    private StripeConnectorUtil() {
    }

    public static Instant getInstantFromLong(Long milliseconds) {
        Date dateObj = new Date(milliseconds * 1000);
        return dateObj.toInstant();
    }
}
