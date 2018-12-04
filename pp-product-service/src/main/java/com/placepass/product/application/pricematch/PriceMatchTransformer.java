package com.placepass.product.application.pricematch;

import com.placepass.product.application.pricematch.dto.GetPriceMatchRS;
import com.placepass.product.application.pricematch.dto.PriceSummary;
import com.placepass.product.application.pricematch.dto.TotalDTO;
import com.placepass.utils.currency.AmountFormatter;

public class PriceMatchTransformer {

    public static GetPriceMatchRS addFeetoPriceMatchRS(PriceSummary priceSummary, GetPriceMatchRS getPriceMatchRS) {
        getPriceMatchRS.setFees(priceSummary.getFees());
        TotalDTO totalDTO = getPriceMatchRS.getTotalPrice();

        if (totalDTO.getFinalTotal() != null && totalDTO.getRoundedFinalTotal() != null) {
            totalDTO.setFinalTotal(priceSummary.getFinalTotalPrice());
            totalDTO.setOriginalTotal(priceSummary.getTotalPrice());
            totalDTO.setTotalAfterDiscount(priceSummary.getTotalPriceAfterDiscount());
            totalDTO.setDiscountAmount(priceSummary.getDiscountAmount());
            totalDTO.setRoundedFinalTotal(AmountFormatter.floatToFloatRoundingFinalTotal(priceSummary.getFinalTotalPrice()));
        }

        return getPriceMatchRS;
    }
}
