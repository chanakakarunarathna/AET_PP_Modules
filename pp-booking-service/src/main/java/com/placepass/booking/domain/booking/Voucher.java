package com.placepass.booking.domain.booking;

import java.util.List;
import java.util.Map;

import com.placepass.utils.voucher.VoucherType;

public class Voucher {

    private String id;

    private String vendorReference;

    private List<String> urls;

    private Map<String, String> extendedAttributes;

    private VoucherType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

    public String getVendorReference() {
        return vendorReference;
    }

    public void setVendorReference(String vendorReference) {
        this.vendorReference = vendorReference;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }

}
