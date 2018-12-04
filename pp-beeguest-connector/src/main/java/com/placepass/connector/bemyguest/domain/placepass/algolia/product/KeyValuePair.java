package com.placepass.connector.bemyguest.domain.placepass.algolia.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyValuePair<T, TV> {

    @JsonProperty("Key")
    private T key;

    @JsonProperty("Value")
    private TV value;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public TV getValue() {
        return value;
    }

    public void setValue(TV value) {
        this.value = value;
    }
}
