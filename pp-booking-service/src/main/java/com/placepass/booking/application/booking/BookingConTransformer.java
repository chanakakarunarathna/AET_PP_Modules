package com.placepass.booking.application.booking;

import java.util.ArrayList;
import java.util.List;

import com.placepass.connector.common.product.CancellationRules;
import com.placepass.connector.common.product.Rules;

public class BookingConTransformer {

    public static com.placepass.booking.domain.product.CancellationRules toCancellationRules(
            CancellationRules cancelRules) {

        com.placepass.booking.domain.product.CancellationRules cancellationRules = new com.placepass.booking.domain.product.CancellationRules();
        cancellationRules.setRules(toRules(cancelRules.getRules()));
        cancellationRules.setTags(cancelRules.getTags());
        return cancellationRules;

    }

    private static List<com.placepass.booking.domain.product.Rules> toRules(List<Rules> rulesList) {

        List<com.placepass.booking.domain.product.Rules> rules = new ArrayList<>();

        for (com.placepass.connector.common.product.Rules conRule : rulesList) {

            com.placepass.booking.domain.product.Rules rule = new com.placepass.booking.domain.product.Rules();
            rule.setMaxHoursInAdvance(conRule.getMaxHoursInAdvance());
            rule.setMinHoursInAdvance(conRule.getMinHoursInAdvance());
            rule.setRefundMultiplier(conRule.getRefundMultiplier());

            rules.add(rule);

        }

        return rules;
    }

    /*
     * public static CancellationRules toCancellationRules( com.placepass.connector.common.product.CancellationRules
     * cancelRules) {
     * 
     * CancellationRules cancellationRules = new CancellationRules();
     * cancellationRules.setRules(toRules(cancelRules.getRules())); cancellationRules.setTags(cancelRules.getTags());
     * return cancellationRules; }
     * 
     * public static List<Rules> toRules(List<com.placepass.connector.common.product.Rules> list) {
     * 
     * List<Rules> rules = new ArrayList<>();
     * 
     * for (com.placepass.connector.common.product.Rules conRule : list) {
     * 
     * Rules rule = new Rules(); rule.setMaxHoursInAdvance(conRule.getMaxHoursInAdvance());
     * rule.setMinHoursInAdvance(conRule.getMinHoursInAdvance());
     * rule.setRefundMultiplier(conRule.getRefundMultiplier());
     * 
     * rules.add(rule);
     * 
     * }
     * 
     * return rules; }
     */

}
