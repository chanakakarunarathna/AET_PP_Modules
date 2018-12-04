package com.placepass.product.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyConfig {

    @Value("${loyaltyPrgmConfig}")
    private String loyaltyPrgmConfig;

    public String getLoyaltyPrgmConfig() {
        return loyaltyPrgmConfig;
    }

    public void setLoyaltyPrgmConfig(String loyaltyPrgmConfig) {
        this.loyaltyPrgmConfig = loyaltyPrgmConfig;
    }

}
