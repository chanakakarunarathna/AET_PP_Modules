package com.placepass.connector.common.booking;

import java.util.List;
import java.util.Map;

import com.placepass.utils.voucher.VoucherType;

public class Voucher {

    private String reference;
    
    private List<String> urls;
    
    private String htmlContent;
    
    private Map<String, String> extendedAttributes;
    
    private VoucherType type;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "Voucher [" + (reference != null ? "Reference=" + reference + ", " : "")
                + (urls != null ? "VoucherUrls=" + urls + ", " : "")
                + (htmlContent != null ? "htmlContent=" + htmlContent + ", " : "")
                + (extendedAttributes != null ? "extendedAttributes=" + extendedAttributes + ", " : "")
                + (type != null ? "voucherType=" + type + ", " : "")+ "]";
    }
}
