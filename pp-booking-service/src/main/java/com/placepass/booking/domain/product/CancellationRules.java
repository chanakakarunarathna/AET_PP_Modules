package com.placepass.booking.domain.product;

import java.util.ArrayList;
import java.util.List;

public class CancellationRules {

    private List<Rules> rules;

    private List<String> tags;

    public List<Rules> getRules() {
        if (rules == null) {
            rules = new ArrayList<Rules>();
        }
        return rules;
    }

    public void setRules(List<Rules> rules) {
        this.rules = rules;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
