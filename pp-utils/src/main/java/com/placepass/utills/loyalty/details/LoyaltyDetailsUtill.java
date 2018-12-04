package com.placepass.utills.loyalty.details;

import com.placepass.utils.currency.AmountFormatter;

public class LoyaltyDetailsUtill {

    private LoyaltyDetailsUtill() {
    }

    public static int calculateLoyaltyPoints(int pointsAwardRatio, float finalTotal) {

        float roundedFinalTotal = AmountFormatter.floatToFloatRoundingFinalTotal(finalTotal);

        return pointsAwardRatio * (int) roundedFinalTotal;
    }

}
