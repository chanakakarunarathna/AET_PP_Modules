package com.viator.connector.domain.placepass.algolia.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaBookingOptionLangSvc {

    private String id;

    private List<KeyValuePair<String, String>> languageServices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<KeyValuePair<String, String>> getLanguageServices() {
        return languageServices;
    }

    public void setLanguageServices(List<KeyValuePair<String, String>> languageServices) {
        this.languageServices = languageServices;
    }
}
