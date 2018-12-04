package com.placepass.booking.application.booking.dto;

public class LoyaltyAccountDTO {

    private String loyaltyProgramId;

    private String loyaltyAccountId;

    private int loyaltyPoints;

    public String getLoyaltyProgramId() {
        return loyaltyProgramId;
    }

    public void setLoyaltyProgramId(String loyaltyProgramId) {
        this.loyaltyProgramId = loyaltyProgramId;
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
