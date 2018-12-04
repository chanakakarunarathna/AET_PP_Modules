package com.placepass.booking.domain.booking;

public class LoyaltyAccount {

    private String loyaltyProgrameId;

    private String loyaltyAccountId;

    private int loyaltyPoints;

    public String getLoyaltyProgrameId() {
        return loyaltyProgrameId;
    }

    public void setLoyaltyProgrameId(String loyaltyProgrameId) {
        this.loyaltyProgrameId = loyaltyProgrameId;
    }

    public String getLoyaltyAccountId() {
        return loyaltyAccountId;
    }

    public void setLoyaltyAccountId(String loyaltyAccountId) {
        this.loyaltyAccountId = loyaltyAccountId;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

}
