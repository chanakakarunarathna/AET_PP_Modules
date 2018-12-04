package com.stripe.payment.connector.domain.config;

public class StripeAccountConfig {

    private String partnerId;

    private String privateSecret;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrivateSecret() {
        return privateSecret;
    }

    public void setPrivateSecret(String privateSecret) {
        this.privateSecret = privateSecret;
    }
    
}
