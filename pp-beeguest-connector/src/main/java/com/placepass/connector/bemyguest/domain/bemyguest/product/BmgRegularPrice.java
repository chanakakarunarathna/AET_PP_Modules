package com.placepass.connector.bemyguest.domain.bemyguest.product;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgRegularPrice {

    @JsonProperty("adult")
    private HashMap<Integer, Double> adult;

    @JsonProperty("child")
    private double child;

    @JsonProperty("children")
    private HashMap<Integer, Double> children;

    @JsonProperty("cancellationPolicy")
    private List<BmgCancellationPolicies> cancellationPolicy;

    public HashMap<Integer, Double> getAdult() {
        return adult;
    }

    public void setAdult(HashMap<Integer, Double> adult) {
        this.adult = adult;
    }

    public double getChild() {
        return child;
    }

    public void setChild(double child) {
        this.child = child;
    }

    public HashMap<Integer, Double> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Integer, Double> children) {
        this.children = children;
    }

    public List<BmgCancellationPolicies> getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(List<BmgCancellationPolicies> cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

}
