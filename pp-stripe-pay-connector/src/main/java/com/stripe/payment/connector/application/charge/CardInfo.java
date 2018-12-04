package com.stripe.payment.connector.application.charge;

public class CardInfo {

    private String last4CardNumber;

    private String cardType;

    public String getLast4CardNumber() {
        return last4CardNumber;
    }

    public void setLast4CardNumber(String last4CardNumber) {
        this.last4CardNumber = last4CardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

}
