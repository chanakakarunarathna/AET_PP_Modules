package com.placepass.booking.domain.config;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "partner_config")
public class PartnerConfig {

    @Id
    private String id;

    private String partnerId;

    private String partnerName;

    private String partnerPrefix;

    private String paymentGateway;

    private Map<String, String> metadata;

    private String voucherUrl;
    
    private boolean discountServiceEnabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerPrefix() {
        return partnerPrefix;
    }

    public void setPartnerPrefix(String partnerPrefix) {
        this.partnerPrefix = partnerPrefix;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getVoucherUrl() {
        return voucherUrl;
    }

    public void setVoucherUrl(String voucherUrl) {
        this.voucherUrl = voucherUrl;
    }

    public boolean isDiscountServiceEnabled() {
        return discountServiceEnabled;
    }

    public void setDiscountServiceEnabled(boolean discountServiceEnabled) {
        this.discountServiceEnabled = discountServiceEnabled;
    }
    
}
