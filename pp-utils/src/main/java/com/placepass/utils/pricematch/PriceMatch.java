package com.placepass.utils.pricematch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.ageband.PlacePassPricingUnit;
import com.placepass.utils.currency.AmountFormatter;

public class PriceMatch {

    /* Best Price logic - Same as product service */
    public static List<PriceMatchPriceBreakDown> getFilteredPriceList(List<PriceMatchPricePerAgeBand> inputPriceList,
            List<PriceMatchQuantity> quantitieList) {

        List<PriceMatchPricePerAgeBand> inputPrices = inputPriceList;
        List<PriceMatchQuantity> quantities = quantitieList;
        List<PriceMatchPriceBreakDown> priceBreakdowns = new ArrayList<>();

        PriceMatchPricePerAgeBand adultPrice = null;
        PriceMatchPricePerAgeBand childPrice = null;
        PriceMatchPricePerAgeBand infantPrice = null;
        PriceMatchPricePerAgeBand seniorPrice = null;
        PriceMatchPricePerAgeBand youthPrice = null;

        int adultQty = 0;
        int childQty = 0;
        int infantQty = 0;
        int seniorQty = 0;
        int youthQty = 0;
        int totalHeadCount = 0;

        List<PriceMatchQuantity> filteredQuantities = new ArrayList<>();

        if (quantities != null) {
            for (PriceMatchQuantity ppQuantity : quantities) {
                if (ppQuantity.getAgeBandLabel() != null) {
                    ppQuantity = supportAgeBandLabels(ppQuantity);
                }
                if (ppQuantity.getQuantity() > 0) {
                    filteredQuantities.add(ppQuantity);
                }
            }
        }

        if (filteredQuantities != null && filteredQuantities.size() > 0) {

            if (isAdultOrSeniorExist(filteredQuantities) && isSupportedAgeBand(filteredQuantities, inputPrices)) {

                for (PriceMatchQuantity ppQuantity : filteredQuantities) {

                    if (ppQuantity.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {
                        adultQty = ppQuantity.getQuantity();
                        totalHeadCount = totalHeadCount + adultQty;
                    } else if (ppQuantity.getAgeBandId() == PlacePassAgeBandType.SENIOR.getAgeBandId()) {
                        seniorQty = ppQuantity.getQuantity();
                        totalHeadCount = totalHeadCount + seniorQty;
                    } else if (ppQuantity.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId()) {
                        childQty = ppQuantity.getQuantity();
                        totalHeadCount = totalHeadCount + childQty;
                    } else if (ppQuantity.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId()) {
                        infantQty = ppQuantity.getQuantity();
                        totalHeadCount = totalHeadCount + infantQty;
                    } else if (ppQuantity.getAgeBandId() == PlacePassAgeBandType.YOUTH.getAgeBandId()) {
                        youthQty = ppQuantity.getQuantity();
                        totalHeadCount = totalHeadCount + youthQty;
                    }

                    if (totalHeadCount > 9) {
                        adultQty = 0;
                        childQty = 0;
                        infantQty = 0;
                        seniorQty = 0;
                        youthQty = 0;
                        break;
                    }
                }

            }

        } else {
            adultQty = -1;
            seniorQty = -1;
        }

        HashMap<Integer, List<PriceMatchPricePerAgeBand>> inputPriceGroups = new HashMap<>();

        for (PriceMatchPricePerAgeBand ppAgeBandPrice : inputPrices) {

            int priceGroupId = ppAgeBandPrice.getPriceGroupSortOrder();
            List<PriceMatchPricePerAgeBand> list = inputPriceGroups.get(priceGroupId);

            if (list == null) {
                list = new ArrayList<>();
                list.add(ppAgeBandPrice);
                inputPriceGroups.put(priceGroupId, list);
            } else {
                list.add(ppAgeBandPrice);
                inputPriceGroups.put(priceGroupId, list);
            }

            if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {

                if (adultPrice == null) {
                    adultPrice = ppAgeBandPrice;
                }

                if(adultQty == -1 && ((ppAgeBandPrice.getMinBuy() <= 1)
                        && ((ppAgeBandPrice.getMaxBuy() >= 1) || (ppAgeBandPrice.getMaxBuy() == -1)))){
                    
                    if ((adultPrice.getMinBuy()>1)) {
                        adultPrice = ppAgeBandPrice;
                    }
                }
                
                if(adultQty == -1 && (adultPrice.getMinBuy()>1)){
                    adultPrice.setRetailPrice(null);
                    adultPrice.setFinalPrice(null);
                    adultPrice.setRoundedFinalPrice(null);
                }

            } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.SENIOR.getAgeBandId()) {

                if (seniorPrice == null) {
                    seniorPrice = ppAgeBandPrice;
                }

                if(seniorQty == -1 && ((ppAgeBandPrice.getMinBuy() <= 1)
                        && ((ppAgeBandPrice.getMaxBuy() >= 1) || (ppAgeBandPrice.getMaxBuy() == -1)))){
                    
                    if ((seniorPrice.getMinBuy()>1)) {
                        seniorPrice = ppAgeBandPrice;
                    }
                    
                }                               
                
                if(seniorQty == -1 && (seniorPrice.getMinBuy()>1)){
                    seniorPrice.setRetailPrice(null);
                    seniorPrice.setFinalPrice(null);
                    seniorPrice.setRoundedFinalPrice(null);
                }


            } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId()) {

                if (childPrice == null) {
                    childPrice = ppAgeBandPrice;
                }

                if (childQty == -1 && childPrice != null
                        && childPrice.getRetailPrice() > ppAgeBandPrice.getRetailPrice()) {
                    childPrice = ppAgeBandPrice;
                }

            } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId()) {

                if (infantPrice == null) {
                    infantPrice = ppAgeBandPrice;
                }

                if (infantQty == -1 && infantPrice != null
                        && infantPrice.getRetailPrice() > ppAgeBandPrice.getRetailPrice()) {
                    infantPrice = ppAgeBandPrice;
                }

            } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.YOUTH.getAgeBandId()) {

                if (youthPrice == null) {
                    youthPrice = ppAgeBandPrice;
                }

                if (youthQty == -1 && youthPrice != null
                        && youthPrice.getRetailPrice() > ppAgeBandPrice.getRetailPrice()) {
                    youthPrice = ppAgeBandPrice;
                }

            }

        }

        HashMap<Integer, List<PriceMatchPricePerAgeBand>> filteredPriceGroups = new HashMap<>(inputPriceGroups);

        if (adultQty != -1) {

            for (Map.Entry<Integer, List<PriceMatchPricePerAgeBand>> entry : inputPriceGroups.entrySet()) {

                boolean adultFound = true;
                boolean seniorFound = true;
                boolean childFound = true;
                boolean infantFound = true;
                boolean youthFound = true;

                if (adultQty > 0) {
                    adultFound = false;
                }
                if (seniorQty > 0) {
                    seniorFound = false;
                }
                if (childQty > 0) {
                    childFound = false;
                }
                if (infantQty > 0) {
                    infantFound = false;
                }
                if (youthQty > 0) {
                    youthFound = false;
                }

                int groupId = entry.getKey();

                for (PriceMatchPricePerAgeBand ppAgeBandPrice : entry.getValue()) {

                    if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {

                        adultFound = false;
                        if ((ppAgeBandPrice.getMinBuy() <= adultQty)
                                && ((ppAgeBandPrice.getMaxBuy() >= adultQty) || (ppAgeBandPrice.getMaxBuy() == -1))) {
                            adultFound = true;
                        }

                    } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.SENIOR.getAgeBandId()) {

                        seniorFound = false;
                        if ((ppAgeBandPrice.getMinBuy() <= seniorQty)
                                && ((ppAgeBandPrice.getMaxBuy() >= seniorQty) || (ppAgeBandPrice.getMaxBuy() == -1))) {
                            seniorFound = true;
                        }

                    } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId()) {

                        childFound = false;
                        if ((ppAgeBandPrice.getMinBuy() <= childQty)
                                && ((ppAgeBandPrice.getMaxBuy() >= childQty) || (ppAgeBandPrice.getMaxBuy() == -1))) {
                            childFound = true;
                        }

                    } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId()) {

                        infantFound = false;
                        if ((ppAgeBandPrice.getMinBuy() <= infantQty)
                                && ((ppAgeBandPrice.getMaxBuy() >= infantQty) || (ppAgeBandPrice.getMaxBuy() == -1))) {
                            infantFound = true;
                        }
                    } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.YOUTH.getAgeBandId()) {

                        youthFound = false;
                        if ((ppAgeBandPrice.getMinBuy() <= youthQty)
                                && ((ppAgeBandPrice.getMaxBuy() >= youthQty) || (ppAgeBandPrice.getMaxBuy() == -1))) {
                            youthFound = true;
                        }
                    }

                }

                if ((!adultFound) || (!seniorFound) || (!childFound) || (!infantFound) || (!youthFound)) {

                    filteredPriceGroups.remove(groupId);

                }

            }

            for (Map.Entry<Integer, List<PriceMatchPricePerAgeBand>> entry : filteredPriceGroups.entrySet()) {

                for (PriceMatchPricePerAgeBand ppAgeBandPrice : entry.getValue()) {

                    if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {

                        adultPrice = ppAgeBandPrice;

                    } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.SENIOR.getAgeBandId()) {

                        seniorPrice = ppAgeBandPrice;

                    } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId()) {

                        childPrice = ppAgeBandPrice;

                    } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId()) {

                        infantPrice = ppAgeBandPrice;
                    } else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.YOUTH.getAgeBandId()) {

                        youthPrice = ppAgeBandPrice;
                    }

                }

            }
        }

        if (adultPrice != null) {

            PriceMatchPriceBreakDown adultPriceBreakDown = new PriceMatchPriceBreakDown();
            PriceMatchTotalPricePerAgeBand totalAdultPriceObject = null;

            if (adultQty > 0 && filteredPriceGroups.size() > 0) {
                adultPriceBreakDown.setPricePerAgeBand(adultPrice);
                totalAdultPriceObject = generateTotalAgeBandPriceWithObjectValues(adultPrice, adultQty);
                adultPriceBreakDown.setTotalPricePerAgeBand(totalAdultPriceObject);
            }

            if (adultQty == 0 || adultQty > 0 && filteredPriceGroups.size() == 0) {

                adultPrice.setRetailPrice(null);
                adultPrice.setFinalPrice(null);
                adultPrice.setRoundedFinalPrice(null);
                adultPrice.setMerchantPrice(null);
                adultPriceBreakDown.setPricePerAgeBand(adultPrice);
                totalAdultPriceObject = generateTotalAgeBandPriceWithNullValues(adultPrice);
                adultPriceBreakDown.setTotalPricePerAgeBand(totalAdultPriceObject);
            }

            if (adultQty == -1) {
                adultPriceBreakDown.setPricePerAgeBand(adultPrice);
                totalAdultPriceObject = generateTotalAgeBandPriceWithObjectValues(adultPrice, 1);
                adultPriceBreakDown.setTotalPricePerAgeBand(totalAdultPriceObject);
            }
            priceBreakdowns.add(adultPriceBreakDown);
        }

        if (childPrice != null) {

            PriceMatchPriceBreakDown childPriceBreakDown = new PriceMatchPriceBreakDown();
            PriceMatchTotalPricePerAgeBand childTotalPricePerAgeBand = null;

            if (childQty > 0 && filteredPriceGroups.size() > 0) {
                childPriceBreakDown.setPricePerAgeBand(childPrice);
                childTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(childPrice, childQty);
                childPriceBreakDown.setTotalPricePerAgeBand(childTotalPricePerAgeBand);
            }

            if (childQty == 0 || childQty > 0 && filteredPriceGroups.size() == 0) {

                childPrice.setRetailPrice(null);
                childPrice.setFinalPrice(null);
                childPrice.setRoundedFinalPrice(null);
                childPrice.setMerchantPrice(null);
                childPriceBreakDown.setPricePerAgeBand(childPrice);
                childTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(childPrice);
                childPriceBreakDown.setTotalPricePerAgeBand(childTotalPricePerAgeBand);
            }
            priceBreakdowns.add(childPriceBreakDown);
        }

        if (seniorPrice != null) {

            PriceMatchPriceBreakDown seniorPriceBreakDown = new PriceMatchPriceBreakDown();
            PriceMatchTotalPricePerAgeBand seniorTotalPricePerAgeBand = null;

            if (seniorQty > 0 && filteredPriceGroups.size() > 0) {
                seniorPriceBreakDown.setPricePerAgeBand(seniorPrice);
                seniorTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(seniorPrice, seniorQty);
                seniorPriceBreakDown.setTotalPricePerAgeBand(seniorTotalPricePerAgeBand);

            }

            if (seniorQty == 0 || seniorQty > 0 && filteredPriceGroups.size() == 0) {

                seniorPrice.setRetailPrice(null);
                seniorPrice.setFinalPrice(null);
                seniorPrice.setRoundedFinalPrice(null);
                seniorPrice.setMerchantPrice(null);
                seniorPriceBreakDown.setPricePerAgeBand(seniorPrice);
                seniorTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(seniorPrice);
                seniorPriceBreakDown.setTotalPricePerAgeBand(seniorTotalPricePerAgeBand);
            }

            if (seniorQty == -1) {

                if (adultPrice == null) {
                    seniorPriceBreakDown.setPricePerAgeBand(seniorPrice);
                    seniorTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(seniorPrice, 1);
                    seniorPriceBreakDown.setTotalPricePerAgeBand(seniorTotalPricePerAgeBand);
                }
                if (adultPrice != null) {
                    seniorPrice.setRetailPrice(null);
                    seniorPrice.setFinalPrice(null);
                    seniorPrice.setRoundedFinalPrice(null);
                    seniorPrice.setMerchantPrice(null);
                    seniorPriceBreakDown.setPricePerAgeBand(seniorPrice);
                    seniorTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(seniorPrice);
                    seniorPriceBreakDown.setTotalPricePerAgeBand(seniorTotalPricePerAgeBand);
                }

            }
            priceBreakdowns.add(seniorPriceBreakDown);
        }

        if (infantPrice != null) {

            PriceMatchPriceBreakDown infantPriceBreakDown = new PriceMatchPriceBreakDown();
            PriceMatchTotalPricePerAgeBand infantTotalPricePerAgeBand = null;

            if (infantQty > 0 && filteredPriceGroups.size() > 0) {
                infantPriceBreakDown.setPricePerAgeBand(infantPrice);
                infantTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(infantPrice, infantQty);
                infantPriceBreakDown.setTotalPricePerAgeBand(infantTotalPricePerAgeBand);
            }
            if (infantQty == 0 || infantQty > 0 && filteredPriceGroups.size() == 0) {

                infantPrice.setRetailPrice(null);
                infantPrice.setFinalPrice(null);
                infantPrice.setRoundedFinalPrice(null);
                infantPrice.setMerchantPrice(null);
                infantPriceBreakDown.setPricePerAgeBand(infantPrice);
                infantTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(infantPrice);
                infantPriceBreakDown.setTotalPricePerAgeBand(infantTotalPricePerAgeBand);
            }
            priceBreakdowns.add(infantPriceBreakDown);
        }

        if (youthPrice != null) {

            PriceMatchPriceBreakDown youthPriceBreakDown = new PriceMatchPriceBreakDown();
            PriceMatchTotalPricePerAgeBand youthTotalPricePerAgeBand = null;

            if (youthQty > 0 && filteredPriceGroups.size() > 0) {
                youthPriceBreakDown.setPricePerAgeBand(youthPrice);
                youthTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(youthPrice, youthQty);
                youthPriceBreakDown.setTotalPricePerAgeBand(youthTotalPricePerAgeBand);
            }
            if (youthQty == 0 || youthQty > 0 && filteredPriceGroups.size() == 0) {

                youthPrice.setRetailPrice(null);
                youthPrice.setFinalPrice(null);
                youthPrice.setRoundedFinalPrice(null);
                youthPrice.setMerchantPrice(null);
                youthPriceBreakDown.setPricePerAgeBand(youthPrice);
                youthTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(youthPrice);
                youthPriceBreakDown.setTotalPricePerAgeBand(youthTotalPricePerAgeBand);
            }
            priceBreakdowns.add(youthPriceBreakDown);
        }
        return priceBreakdowns;
    }

    public static boolean isAdultOrSeniorExistUsingLable(List<PriceMatchQuantity> qtyList) {

        boolean isValid = true;
        if (!(qtyList.stream()
                .anyMatch(q -> PlacePassAgeBandType.ADULT.toString().equals(q.getAgeBandLabel()) && q.getQuantity() > 0
                        || PlacePassAgeBandType.SENIOR.toString().equals(q.getAgeBandLabel())
                                && q.getQuantity() > 0))) {

            isValid = false;

        }

        return isValid;
    }
    
    public static boolean isAdultOrSeniorExist(List<PriceMatchQuantity> qtyList) {

        boolean isValid = true;
        if (!(qtyList.stream()
                .anyMatch(q -> PlacePassAgeBandType.ADULT.getAgeBandId() == q.getAgeBandId() && q.getQuantity() > 0
                        || PlacePassAgeBandType.SENIOR.getAgeBandId() == q.getAgeBandId()
                                && q.getQuantity() > 0))) {

            isValid = false;

        }

        return isValid;
    }

    public static boolean isSupportedAgeBand(List<PriceMatchQuantity> quantities,
            List<PriceMatchPricePerAgeBand> inputPrices) {

        boolean isValid = true;

        for (PriceMatchQuantity ppQuantity : quantities) {

            isValid = validateAgeBandId(ppQuantity.getAgeBandId());
            if (!isValid) {
                return isValid;
            }
        }

        Map<Integer, Integer> qtyCountWithAgeBand = new HashMap<>();

        for (PriceMatchQuantity ppQuantity : quantities) {
            int reqAgeBand = ppQuantity.getAgeBandId();
            qtyCountWithAgeBand.put(reqAgeBand, 1);

            for (PriceMatchPricePerAgeBand PriceMatchPricePerAgeBand : inputPrices) {
                if (PriceMatchPricePerAgeBand.getAgeBandId() == reqAgeBand) {
                    qtyCountWithAgeBand.put(reqAgeBand, 2);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : qtyCountWithAgeBand.entrySet()) {
            if (1 == entry.getValue()) {
                isValid = false;
            }
        }

        return isValid;
    }

    public static boolean validateAgeBandId(Integer ageBandId) {

        for (PlacePassAgeBandType tt : PlacePassAgeBandType.values()) {
            if (tt.getAgeBandId() == ageBandId) {
                return true;
            }
        }
        return false;
    }

    public static PriceMatchQuantity supportAgeBandLabels(PriceMatchQuantity ppQuantity) {

        if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.ADULT.toString())) {
            ppQuantity.setAgeBandId(PlacePassAgeBandType.ADULT.getAgeBandId());
        }
        if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.SENIOR.toString())) {
            ppQuantity.setAgeBandId(PlacePassAgeBandType.SENIOR.getAgeBandId());
        }
        if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.CHILD.toString())) {
            ppQuantity.setAgeBandId(PlacePassAgeBandType.CHILD.getAgeBandId());
        }
        if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.INFANT.toString())) {
            ppQuantity.setAgeBandId(PlacePassAgeBandType.INFANT.getAgeBandId());
        }
        if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.YOUTH.toString())) {
            ppQuantity.setAgeBandId(PlacePassAgeBandType.YOUTH.getAgeBandId());
        }
        return ppQuantity;
    }

    public static PriceMatchTotalPricePerAgeBand generateTotalAgeBandPriceWithObjectValues(
            PriceMatchPricePerAgeBand pricePerAgeBandDTO, int agebandQty) {

        PriceMatchTotalPricePerAgeBand totalPricePerAgeBand = new PriceMatchTotalPricePerAgeBand();
        totalPricePerAgeBand.setAgeBandId(pricePerAgeBandDTO.getAgeBandId());
        totalPricePerAgeBand.setCurrencyCode(pricePerAgeBandDTO.getCurrencyCode());
        totalPricePerAgeBand.setDescription(pricePerAgeBandDTO.getDescription());
        totalPricePerAgeBand.setPriceType(pricePerAgeBandDTO.getPriceType());
        totalPricePerAgeBand.setRetailPrice(null);
        totalPricePerAgeBand.setFinalPrice(null);
        totalPricePerAgeBand.setRoundedFinalPrice(null);
        totalPricePerAgeBand.setMerchantPrice(null);

        if (pricePerAgeBandDTO.getRetailPrice() != null) {

            //TODO: Flat rate per age band only, since Viator only have flat rate for Adult. We will implement Flat rate for total ageband if needed
            if (pricePerAgeBandDTO.getPricingUnit() != null && pricePerAgeBandDTO.getPricingUnit().equals(PlacePassPricingUnit.FLAT_RATE.name())) {
                agebandQty = 1;
            }

            float merchantPrice = 0;
            float totalRetailPrice = calculateAgeBandTotal(pricePerAgeBandDTO.getRetailPrice(), agebandQty);
            float finalPrice = calculateAgeBandTotal(pricePerAgeBandDTO.getFinalPrice(), agebandQty);
            float roundedFinalPrice = AmountFormatter.floatToFloatRoundingFinalTotal(finalPrice);

            if (pricePerAgeBandDTO.getMerchantPrice() != null) {
                merchantPrice = calculateAgeBandTotal(pricePerAgeBandDTO.getMerchantPrice(), agebandQty);
            }

            totalPricePerAgeBand.setMerchantPrice(merchantPrice);
            totalPricePerAgeBand.setRetailPrice(totalRetailPrice);
            totalPricePerAgeBand.setFinalPrice(finalPrice);
            totalPricePerAgeBand.setRoundedFinalPrice(roundedFinalPrice);

        }
        
        return totalPricePerAgeBand;

    }

    public static PriceMatchTotalPricePerAgeBand generateTotalAgeBandPriceWithNullValues(
            PriceMatchPricePerAgeBand pricePerAgeBandDTO) {

        PriceMatchTotalPricePerAgeBand totalPricePerAgeBand = new PriceMatchTotalPricePerAgeBand();
        totalPricePerAgeBand.setAgeBandId(pricePerAgeBandDTO.getAgeBandId());
        totalPricePerAgeBand.setCurrencyCode(pricePerAgeBandDTO.getCurrencyCode());
        totalPricePerAgeBand.setDescription(pricePerAgeBandDTO.getDescription());
        totalPricePerAgeBand.setPriceType(pricePerAgeBandDTO.getPriceType());
        totalPricePerAgeBand.setRetailPrice(null);
        totalPricePerAgeBand.setFinalPrice(null);
        totalPricePerAgeBand.setRoundedFinalPrice(null);
        totalPricePerAgeBand.setMerchantPrice(null);

        return totalPricePerAgeBand;

    }

    public static PriceMatchTotalPrice getTotalForQuantities(List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns) {

        PriceMatchTotalPrice priceMatchTotalPrice = new PriceMatchTotalPrice();

        String currencyCode = "";
        BigDecimal merchantTotal = new BigDecimal("0");
        BigDecimal retailTotal = new BigDecimal("0");
        BigDecimal finalTotal = new BigDecimal("0");
        float roundedFinalTotal = 0;

        for (PriceMatchPriceBreakDown priceMatchPriceBreakDown : priceMatchPriceBreakDowns) {

            currencyCode = priceMatchPriceBreakDown.getTotalPricePerAgeBand().getCurrencyCode();

            if (priceMatchPriceBreakDown.getTotalPricePerAgeBand().getRetailPrice() != null) {

                retailTotal = retailTotal
                        .add(new BigDecimal(priceMatchPriceBreakDown.getTotalPricePerAgeBand().getRetailPrice()));
                finalTotal = finalTotal
                        .add(new BigDecimal(priceMatchPriceBreakDown.getTotalPricePerAgeBand().getFinalPrice()));
                merchantTotal = merchantTotal
                        .add(new BigDecimal(priceMatchPriceBreakDown.getTotalPricePerAgeBand().getMerchantPrice()));

            }
        }

        if (retailTotal.intValue() > 0) {
            roundedFinalTotal = AmountFormatter.floatToFloatRoundingFinalTotal(finalTotal.floatValue());
            priceMatchTotalPrice.setCurrencyCode(currencyCode);
            priceMatchTotalPrice.setMerchantTotal(calculateTotal(merchantTotal));
            priceMatchTotalPrice.setRetailTotal(calculateTotal(retailTotal));
            priceMatchTotalPrice.setFinalTotal(calculateTotal(finalTotal));
            priceMatchTotalPrice.setRoundedFinalTotal(roundedFinalTotal);
        } else {
            priceMatchTotalPrice.setCurrencyCode(currencyCode);
            priceMatchTotalPrice.setMerchantTotal(null);
            priceMatchTotalPrice.setRetailTotal(null);
            priceMatchTotalPrice.setFinalTotal(null);
            priceMatchTotalPrice.setRoundedFinalTotal(null);
        }
        return priceMatchTotalPrice;
    }

    public static float calculateAgeBandTotal(Float inputPrice, int agebandQty) {

        BigDecimal price = new BigDecimal(inputPrice);
        BigDecimal quantity = new BigDecimal(agebandQty);
        BigDecimal totalPrice = price.multiply(quantity);

        return calculateTotal(totalPrice);
    }
    
	public static float calculateTotal(BigDecimal inputTotal) {

		DecimalFormat decimalFormat = new DecimalFormat("#.###");
		BigDecimal total = new BigDecimal(decimalFormat.format(inputTotal));
		total.setScale(2, RoundingMode.CEILING);

		return total.floatValue();
    }

}
