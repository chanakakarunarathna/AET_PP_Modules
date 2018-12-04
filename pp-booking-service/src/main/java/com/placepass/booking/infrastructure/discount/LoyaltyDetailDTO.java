package com.placepass.booking.infrastructure.discount;

public class LoyaltyDetailDTO {

    private String loyaltyProgramId;

    private String loyaltyProgramDisplayName;

    private int loyaltyPoints;

    public String getLoyaltyProgramId() {
        return loyaltyProgramId;
    }

    public void setLoyaltyProgramId(String loyaltyProgramId) {
        this.loyaltyProgramId = loyaltyProgramId;
    }

    public String getLoyaltyProgramDisplayName() {
        return loyaltyProgramDisplayName;
    }

    public void setLoyaltyProgramDisplayName(String loyaltyProgramDisplayName) {
        this.loyaltyProgramDisplayName = loyaltyProgramDisplayName;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

}
