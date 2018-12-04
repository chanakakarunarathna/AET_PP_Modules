package com.placepass.booking.application.booking.dto;

import java.util.List;

public class CancellationRulesDTO {

    private List<RulesDTO> rules;

    private List<String> tags;

    public List<RulesDTO> getRules() {
        return rules;
    }

    public void setRules(List<RulesDTO> rules) {
        this.rules = rules;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
