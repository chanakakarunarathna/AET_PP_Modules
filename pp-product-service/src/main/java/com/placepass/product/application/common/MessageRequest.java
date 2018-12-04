package com.placepass.product.application.common;

public class MessageRequest {

    protected String partnerId;

    protected String userId;

    // Mandatory: Determines payment gateway
    protected String gatewayName;

    // Mandatory: Determines payment exchange
    protected String exchangeName;

    /**
     * @return the partnerId
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * @param partnerId the partnerId to set
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the gatewayName
     */
    public String getGatewayName() {
        return gatewayName;
    }

    /**
     * @param gatewayName the gatewayName to set
     */
    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    /**
     * @return the exchangeName
     */
    public String getExchangeName() {
        return exchangeName;
    }

    /**
     * @param exchangeName the exchangeName to set
     */
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

}
