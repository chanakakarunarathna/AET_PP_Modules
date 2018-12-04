package com.placepass.connector.sendgrid.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EmailTemplate")
public class EmailTemplate {
    @Id
    private String id;

    private String partnerId;

    private String apiKey;

    private String eventName;

    private String templateId;

    private String fromEmail;

    private String subject;

    private String moreDetailsFullURLWithTemplate;

    private List<String> emailPlaceholders;

    private Map<String, String> vendorImageUrls;

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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the moreDetailsFullURLWithTemplate
     */
    public String getMoreDetailsFullURLWithTemplate() {
        return moreDetailsFullURLWithTemplate;
    }

    /**
     * @param moreDetailsFullURLWithTemplate the moreDetailsFullURLWithTemplate to set
     */
    public void setMoreDetailsFullURLWithTemplate(String moreDetailsFullURLWithTemplate) {
        this.moreDetailsFullURLWithTemplate = moreDetailsFullURLWithTemplate;
    }

    public List<String> getEmailPlaceholders() {
        return emailPlaceholders;
    }

    public void setEmailPlaceholders(List<String> emailPlaceholders) {
        this.emailPlaceholders = emailPlaceholders;
    }

    public Map<String, String> getVendorImageUrls() {
		return vendorImageUrls;
	}

	public void setVendorImageUrls(Map<String, String> vendorImageUrls) {
		this.vendorImageUrls = vendorImageUrls;
	}
}
