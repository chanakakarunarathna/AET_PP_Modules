package com.placepass.product.application.pricematch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.placepass.product.application.pricematch.dto.*;
import com.placepass.product.infrastructure.RestClient;
import com.placepass.utils.ageband.PlacePassPricingUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.placepass.product.domain.config.LoyaltyProgramConfig;
import com.placepass.product.infrastructure.LoyaltyConfig;
import com.placepass.utills.loyalty.details.LoyaltyDetailsUtill;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;
import com.placepass.utils.pricematch.PriceMatch;
import com.placepass.utils.pricematch.PriceMatchPriceBreakDown;
import com.placepass.utils.pricematch.PriceMatchPricePerAgeBand;
import com.placepass.utils.pricematch.PriceMatchQuantity;
import com.placepass.utils.pricematch.PriceMatchTotalPricePerAgeBand;

@Service
public class PriceMatchAppService {

    @Autowired
    private LoyaltyConfig loyaltyConfig;

    @Autowired
	private RestClient restClient;

    public GetPriceMatchRS getPriceMatch(String partnerId, GetPriceMatchRQ getPriceMatchRQ) {

    	List<PriceBreakDownDTO> priceBreakdowns = new ArrayList<>();
		List<LoyaltyDetailDTO> loyaltyDetailsList = new ArrayList<>();
		TotalDTO totalDTO = new TotalDTO();        						    	
		
		List<QuantityDTO> filteredQuantities = getFilteredQuantities(getPriceMatchRQ.getQuantities());
		if (filteredQuantities != null && filteredQuantities.size() > 0) {
			
		    // Showing best price
			priceBreakdowns = getMatchedPriceList(getPriceMatchRQ.getPrices(),
			        getPriceMatchRQ.getQuantities());
			totalDTO = getTotalForQuantities(priceBreakdowns);
			
		} else {
			
			List<QuantityDTO> defaultQuantities = new ArrayList<>();
            QuantityDTO defaultQuantity = new QuantityDTO();
            defaultQuantity.setAgeBandId(PlacePassAgeBandType.ADULT.getAgeBandId());
            defaultQuantity.setQuantity(1);
            defaultQuantities.add(defaultQuantity);
            getPriceMatchRQ.setQuantities(defaultQuantities);
            
			List<PricePerAgeBandDTO> clonedPrices = new ArrayList<>();
			for (PricePerAgeBandDTO orginalPrice : getPriceMatchRQ.getPrices()) {

				PricePerAgeBandDTO clonePrice = new PricePerAgeBandDTO();
				clonePrice.setPriceGroupSortOrder(orginalPrice.getPriceGroupSortOrder());
				clonePrice.setPriceType(orginalPrice.getPriceType());
				clonePrice.setAgeBandId(orginalPrice.getAgeBandId());
				clonePrice.setCurrencyCode(orginalPrice.getCurrencyCode());
				clonePrice.setDescription(orginalPrice.getDescription());
				clonePrice.setRetailPrice(orginalPrice.getRetailPrice());
				clonePrice.setFinalPrice(orginalPrice.getFinalPrice());
				clonePrice.setRoundedFinalPrice(orginalPrice.getRoundedFinalPrice());
				clonePrice.setMaxBuy(orginalPrice.getMaxBuy());
				clonePrice.setMinBuy(orginalPrice.getMinBuy());
				clonePrice.setAgeFrom(orginalPrice.getAgeFrom());
				clonePrice.setAgeTo(orginalPrice.getAgeTo());
				clonePrice.setRetailPrice(orginalPrice.getRetailPrice());
				clonedPrices.add(clonePrice);
			}

			// Showing best price
			priceBreakdowns = getMatchedPriceList(getPriceMatchRQ.getPrices(),
                    getPriceMatchRQ.getQuantities());
			totalDTO = getTotalForQuantities(priceBreakdowns);

			if (!isAdultPriceBandFound(priceBreakdowns)) {				
				getPriceMatchRQ.getQuantities().get(0).setAgeBandId(PlacePassAgeBandType.SENIOR.getAgeBandId());
				getPriceMatchRQ.setPrices(clonedPrices);
				// Showing best price
				priceBreakdowns = getMatchedPriceList(getPriceMatchRQ.getPrices(),
	                    getPriceMatchRQ.getQuantities());
				totalDTO = getTotalForQuantities(priceBreakdowns);
			}
		}

    	Gson gson = new Gson();
        String loyaltyPrgmConfigString = loyaltyConfig.getLoyaltyPrgmConfig();
        LoyaltyProgramConfig[] loyaltyProgramConfigArray = gson.fromJson(loyaltyPrgmConfigString,LoyaltyProgramConfig[].class);

        for (LoyaltyProgramConfig loyaltyProgramConfig : loyaltyProgramConfigArray) {
            if (loyaltyProgramConfig.getPartnerId().equals(partnerId)) {
                LoyaltyDetailDTO loyaltyDetailDTO = new LoyaltyDetailDTO();
                loyaltyDetailDTO.setLoyaltyProgramDisplayName(loyaltyProgramConfig.getProgDisplayName());
                loyaltyDetailDTO.setLoyaltyProgramId(loyaltyProgramConfig.getProgId());
				if (totalDTO != null && totalDTO.getFinalTotal() != null) {
					int loyaltyPoints = LoyaltyDetailsUtill.calculateLoyaltyPoints(
							loyaltyProgramConfig.getPointsAwardRatio(), totalDTO.getFinalTotal());
					loyaltyDetailDTO.setLoyaltyPoints(loyaltyPoints);
				}
                loyaltyDetailsList.add(loyaltyDetailDTO);
            }
        }

        GetPriceMatchRS getPriceMatchRS = new GetPriceMatchRS();
        getPriceMatchRS.setPriceBreakdowns(priceBreakdowns);
        getPriceMatchRS.setTotalPrice(totalDTO);
        getPriceMatchRS.setLoyaltyDetails(loyaltyDetailsList);

        return getPriceMatchRS;

    }

    /*public static List<PriceBreakDownDTO> getFilteredPriceList(GetPriceMatchRQ priceMatchRQ) {
		
		List<PricePerAgeBandDTO> inputPrices = priceMatchRQ.getPrices();
		List<QuantityDTO> quantities = priceMatchRQ.getQuantities();
		List<PriceBreakDownDTO> priceBreakdowns = new ArrayList<>();

		PricePerAgeBandDTO adultPrice = null;
		PricePerAgeBandDTO childPrice = null;
		PricePerAgeBandDTO infantPrice = null;
		PricePerAgeBandDTO seniorPrice = null;
        PricePerAgeBandDTO youthPrice = null;

		int adultQty = 0;
		int childQty = 0;
		int infantQty = 0;
		int seniorQty = 0;
        int youthQty = 0;
		int totalHeadCount = 0;
		
		List<QuantityDTO> filteredQuantities = new ArrayList<>();
		
		if (quantities != null) {
			for (QuantityDTO ppQuantity : quantities) {
				if (ppQuantity.getQuantity() > 0) {
					filteredQuantities.add(ppQuantity);
				}
			}
		}
		
		if (filteredQuantities != null && filteredQuantities.size() > 0) {

			if (isAdultOrSeniorExist(filteredQuantities) && isSupportedAgeBand(filteredQuantities, inputPrices)) {

				for (QuantityDTO ppQuantity : filteredQuantities) {

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

		HashMap<Integer, List<PricePerAgeBandDTO>> inputPriceGroups = new HashMap<>();
		
		for (PricePerAgeBandDTO ppAgeBandPrice : inputPrices) {

			int priceGroupId = ppAgeBandPrice.getPriceGroupSortOrder();
			List<PricePerAgeBandDTO> list = inputPriceGroups.get(priceGroupId);

			if (list == null) {
				list = new ArrayList<>();
				list.add(ppAgeBandPrice);
				inputPriceGroups.put(priceGroupId, list);
			} else {
				list.add(ppAgeBandPrice);
				inputPriceGroups.put(priceGroupId, list);
			}

			if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {
				
				
				if (adultPrice == null ) {
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
				
				if (seniorPrice == null ) {
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
				
				
				if (childPrice == null ) {
					childPrice = ppAgeBandPrice;
				}
				
				if(childQty == -1 && childPrice != null && childPrice.getRetailPrice()>ppAgeBandPrice.getRetailPrice()){
					childPrice = ppAgeBandPrice;
				}

			} else if (ppAgeBandPrice.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId()) {
				
				if (infantPrice == null ) {
					infantPrice = ppAgeBandPrice;
				}
				
				if(infantQty == -1 && infantPrice != null && infantPrice.getRetailPrice()>ppAgeBandPrice.getRetailPrice()){
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

		HashMap<Integer, List<PricePerAgeBandDTO>> filteredPriceGroups = new HashMap<>(inputPriceGroups);
		
		if (adultQty != -1) {
			
			for (Map.Entry<Integer, List<PricePerAgeBandDTO>> entry : inputPriceGroups.entrySet()) {

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

				for (PricePerAgeBandDTO ppAgeBandPrice : entry.getValue()) {

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

			for (Map.Entry<Integer, List<PricePerAgeBandDTO>> entry : filteredPriceGroups.entrySet()) {

				for (PricePerAgeBandDTO ppAgeBandPrice : entry.getValue()) {

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
			
			PriceBreakDownDTO adultPriceBreakDown = new PriceBreakDownDTO();
			TotalPricePerAgeBandDTO totalAdultPriceObject = null;

			if (adultQty > 0 && filteredPriceGroups.size() > 0) {
				adultPriceBreakDown.setPricePerAgeBand(adultPrice);
				totalAdultPriceObject = generateTotalAgeBandPriceWithObjectValues(adultPrice, adultQty);
				adultPriceBreakDown.setTotalPricePerAgeBand(totalAdultPriceObject);
			}

			if (adultQty == 0 || adultQty > 0 && filteredPriceGroups.size() == 0) {

				adultPrice.setRetailPrice(null);
				adultPrice.setFinalPrice(null);
				adultPrice.setRoundedFinalPrice(null);
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
			
			PriceBreakDownDTO childPriceBreakDown = new PriceBreakDownDTO();
			TotalPricePerAgeBandDTO childTotalPricePerAgeBand = null;
			
			if (childQty > 0 && filteredPriceGroups.size() > 0) {
				childPriceBreakDown.setPricePerAgeBand(childPrice);
				childTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(childPrice, childQty);
				childPriceBreakDown.setTotalPricePerAgeBand(childTotalPricePerAgeBand);
			}

			if (childQty == 0 || childQty > 0 && filteredPriceGroups.size() == 0) {

				childPrice.setRetailPrice(null);
				childPrice.setFinalPrice(null);
				childPrice.setRoundedFinalPrice(null);
				childPriceBreakDown.setPricePerAgeBand(childPrice);
				childTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(childPrice);
				childPriceBreakDown.setTotalPricePerAgeBand(childTotalPricePerAgeBand);
			}
			priceBreakdowns.add(childPriceBreakDown);
		}

		if (seniorPrice != null) {

			PriceBreakDownDTO seniorPriceBreakDown = new PriceBreakDownDTO();
			TotalPricePerAgeBandDTO seniorTotalPricePerAgeBand = null;

			if (seniorQty > 0 && filteredPriceGroups.size() > 0) {
				seniorPriceBreakDown.setPricePerAgeBand(seniorPrice);
				seniorTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(seniorPrice, seniorQty);
				seniorPriceBreakDown.setTotalPricePerAgeBand(seniorTotalPricePerAgeBand);

			}

			if (seniorQty == 0 || seniorQty > 0 && filteredPriceGroups.size() == 0) {

				seniorPrice.setRetailPrice(null);
				seniorPrice.setFinalPrice(null);
				seniorPrice.setRoundedFinalPrice(null);
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
				if (adultPrice != null && adultPrice.getRetailPrice() != null) {
					seniorPrice.setRetailPrice(null);
					seniorPrice.setFinalPrice(null);
					seniorPrice.setRoundedFinalPrice(null);
					seniorPriceBreakDown.setPricePerAgeBand(seniorPrice);
					seniorTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(seniorPrice);
					seniorPriceBreakDown.setTotalPricePerAgeBand(seniorTotalPricePerAgeBand);
				}
				
				if (adultPrice != null && adultPrice.getRetailPrice() == null) {
					seniorPriceBreakDown.setPricePerAgeBand(seniorPrice);
					seniorTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(seniorPrice, 1);
					seniorPriceBreakDown.setTotalPricePerAgeBand(seniorTotalPricePerAgeBand);
				}

			}
			priceBreakdowns.add(seniorPriceBreakDown);
		}

		if (infantPrice != null) {

			PriceBreakDownDTO infantPriceBreakDown = new PriceBreakDownDTO();
			TotalPricePerAgeBandDTO infantTotalPricePerAgeBand = null;

			if (infantQty > 0 && filteredPriceGroups.size() > 0) {
				infantPriceBreakDown.setPricePerAgeBand(infantPrice);
				infantTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(infantPrice, infantQty);
				infantPriceBreakDown.setTotalPricePerAgeBand(infantTotalPricePerAgeBand);
			}
			if (infantQty == 0 || infantQty > 0 && filteredPriceGroups.size() == 0) {

				infantPrice.setRetailPrice(null);
				infantPrice.setFinalPrice(null);
				infantPrice.setRoundedFinalPrice(null);
				infantPriceBreakDown.setPricePerAgeBand(infantPrice);
				infantTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(infantPrice);
				infantPriceBreakDown.setTotalPricePerAgeBand(infantTotalPricePerAgeBand);
			}
			priceBreakdowns.add(infantPriceBreakDown);
        }

        if (youthPrice != null) {

            PriceBreakDownDTO youthPriceBreakDown = new PriceBreakDownDTO();
            TotalPricePerAgeBandDTO youthTotalPricePerAgeBand = null;

            if (youthQty > 0 && filteredPriceGroups.size() > 0) {
                youthPriceBreakDown.setPricePerAgeBand(youthPrice);
                youthTotalPricePerAgeBand = generateTotalAgeBandPriceWithObjectValues(youthPrice, youthQty);
                youthPriceBreakDown.setTotalPricePerAgeBand(youthTotalPricePerAgeBand);
            }
            if (youthQty == 0 || youthQty > 0 && filteredPriceGroups.size() == 0) {

                youthPrice.setRetailPrice(null);
                youthPrice.setFinalPrice(null);
                youthPrice.setRoundedFinalPrice(null);
                youthPriceBreakDown.setPricePerAgeBand(youthPrice);
                youthTotalPricePerAgeBand = generateTotalAgeBandPriceWithNullValues(youthPrice);
                youthPriceBreakDown.setTotalPricePerAgeBand(youthTotalPricePerAgeBand);
            }
            priceBreakdowns.add(youthPriceBreakDown);
        }
        return priceBreakdowns;
    }
*/   	
	public static TotalDTO getTotalForQuantities(List<PriceBreakDownDTO> priceBreakdowns) {
   		
		TotalDTO totalDTO = new TotalDTO();
   		
		 String currencyCode = "";
	     BigDecimal retailTotal = new BigDecimal("0");
	     BigDecimal finalTotal = new BigDecimal("0");
	     float roundedFinalTotal = 0;
   		
		for (PriceBreakDownDTO priceBreakdownDTO : priceBreakdowns) {

			currencyCode = priceBreakdownDTO.getTotalPricePerAgeBand().getCurrencyCode();

			if (priceBreakdownDTO.getTotalPricePerAgeBand().getRetailPrice() != null) {

			retailTotal = retailTotal
                        .add(new BigDecimal(priceBreakdownDTO.getTotalPricePerAgeBand().getRetailPrice()));
            finalTotal = finalTotal
                    .add(new BigDecimal(priceBreakdownDTO.getTotalPricePerAgeBand().getFinalPrice()));				
			}
		}

		if (retailTotal.intValue() > 0) {
			roundedFinalTotal = AmountFormatter.floatToFloatRoundingFinalTotal(finalTotal.floatValue());
			totalDTO.setCurrencyCode(currencyCode);
			totalDTO.setRetailTotal(calculateTotal(retailTotal));
			totalDTO.setFinalTotal(calculateTotal(finalTotal));
			totalDTO.setRoundedFinalTotal(roundedFinalTotal);
		} else {
			totalDTO.setCurrencyCode(currencyCode);
			totalDTO.setRetailTotal(null);
			totalDTO.setFinalTotal(null);
			totalDTO.setRoundedFinalTotal(null);
		}
		return totalDTO;
	}

	/*public static TotalPricePerAgeBandDTO generateTotalAgeBandPriceWithNullValues(
			PricePerAgeBandDTO pricePerAgeBandDTO) {

		TotalPricePerAgeBandDTO totalPricePerAgeBand = new TotalPricePerAgeBandDTO();
		totalPricePerAgeBand.setAgeBandId(pricePerAgeBandDTO.getAgeBandId());
		totalPricePerAgeBand.setCurrencyCode(pricePerAgeBandDTO.getCurrencyCode());
		totalPricePerAgeBand.setDescription(pricePerAgeBandDTO.getDescription());
		totalPricePerAgeBand.setPriceType(pricePerAgeBandDTO.getPriceType());
		totalPricePerAgeBand.setRetailPrice(null);
		totalPricePerAgeBand.setFinalPrice(null);
		totalPricePerAgeBand.setRoundedFinalPrice(null);

		return totalPricePerAgeBand;

	}*/

	/*public static TotalPricePerAgeBandDTO generateTotalAgeBandPriceWithObjectValues(
			PricePerAgeBandDTO pricePerAgeBandDTO, int agebandQty) {

		TotalPricePerAgeBandDTO totalPricePerAgeBand = new TotalPricePerAgeBandDTO();
		totalPricePerAgeBand.setAgeBandId(pricePerAgeBandDTO.getAgeBandId());
		totalPricePerAgeBand.setCurrencyCode(pricePerAgeBandDTO.getCurrencyCode());
		totalPricePerAgeBand.setDescription(pricePerAgeBandDTO.getDescription());
		totalPricePerAgeBand.setPriceType(pricePerAgeBandDTO.getPriceType());
		totalPricePerAgeBand.setRetailPrice(null);
		totalPricePerAgeBand.setFinalPrice(null);
		totalPricePerAgeBand.setRoundedFinalPrice(null);

		if (pricePerAgeBandDTO.getRetailPrice() != null) {

			if (pricePerAgeBandDTO.getPricingUnit() != null && pricePerAgeBandDTO.getPricingUnit().equals(PlacePassPricingUnit.FLAT_RATE.name())) {
				agebandQty = 1;
			}

			float totalRetailPrice = calculateAgeBandTotal(pricePerAgeBandDTO.getRetailPrice(), agebandQty);
			float finalPrice = calculateAgeBandTotal(pricePerAgeBandDTO.getFinalPrice(), agebandQty);
			float roundedFinalPrice = AmountFormatter.floatToFloatRoundingFinalTotal(finalPrice);

			totalPricePerAgeBand.setRetailPrice(totalRetailPrice);
			totalPricePerAgeBand.setFinalPrice(finalPrice);
			totalPricePerAgeBand.setRoundedFinalPrice(roundedFinalPrice);
		}
		return totalPricePerAgeBand;

	}
*/
	/*public static float calculateAgeBandTotal(Float inputPrice, int agebandQty) {

        BigDecimal price = new BigDecimal(inputPrice);
        BigDecimal quantity = new BigDecimal(agebandQty);
        BigDecimal totalPrice = price.multiply(quantity);

        return calculateTotal(totalPrice);
    }*/
	
	public static float calculateTotal(BigDecimal inputTotal) {

		DecimalFormat decimalFormat = new DecimalFormat("#.###");
		BigDecimal total = new BigDecimal(decimalFormat.format(inputTotal));
		total.setScale(2, RoundingMode.CEILING);
		
		return total.floatValue();
	}
	
	public static boolean isAdultPriceBandFound(List<PriceBreakDownDTO> priceBreakdowns) {

		boolean isValid = false;

		if ((priceBreakdowns.stream()
				.anyMatch(q -> PlacePassAgeBandType.ADULT.getAgeBandId() == q.getPricePerAgeBand().getAgeBandId()
						&& q.getPricePerAgeBand().getRetailPrice() != null))) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isAdultOrSeniorExist(List<QuantityDTO> quantities) {

		boolean isValid = false;

		if ((quantities.stream()
				.anyMatch(q -> PlacePassAgeBandType.ADULT.getAgeBandId() == q.getAgeBandId() && q.getQuantity() > 0
						|| PlacePassAgeBandType.SENIOR.getAgeBandId() == q.getAgeBandId() && q.getQuantity() > 0))) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isSupportedAgeBand(List<QuantityDTO> quantities, List<PricePerAgeBandDTO> prices) {

		boolean isValid = true;
		
		for (QuantityDTO ppQuantity : quantities) {
			isValid = validateAgeBandId(ppQuantity.getAgeBandId());
			if (!isValid) {
				return isValid;
			}
		}

		Map<Integer, Integer> qtyCountWithAgeBand = new HashMap<>();
        
		for (QuantityDTO ppQuantity : quantities) {
			int reqAgeBand = ppQuantity.getAgeBandId();
			qtyCountWithAgeBand.put(reqAgeBand, 1);

			for (PricePerAgeBandDTO PricePerAgeBandDTO : prices) {
				if (PricePerAgeBandDTO.getAgeBandId() == reqAgeBand) {
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
	
	public static List<QuantityDTO> getFilteredQuantities(List<QuantityDTO> quantities) {

		List<QuantityDTO> filteredQuantities = new ArrayList<>();
		if (quantities != null) {
			for (QuantityDTO quantity : quantities) {
				if (quantity.getQuantity() > 0) {
					filteredQuantities.add(quantity);
				}
			}
		}

		return filteredQuantities;
	}

	public GetPriceMatchRS getPriceMatchWithFee(String partnerId, GetPriceMatchRQ getPriceMatchRQ) {

    	GetPriceMatchRS getPriceMatchRS = getPriceMatch(partnerId, getPriceMatchRQ);

    	PriceSummary priceSummary = restClient.getFeeForPriceMatch(getPriceMatchRS, partnerId);

    	return PriceMatchTransformer.addFeetoPriceMatchRS(priceSummary, getPriceMatchRS);
	}
	
	// Showing best price
    public static List<PriceBreakDownDTO> getMatchedPriceList(List<PricePerAgeBandDTO> prices, List<QuantityDTO> quantities) {

        List<PriceMatchPricePerAgeBand> inputPriceMatchList = toPricePerAgeBandDTOToPriceMatchPricePerAgeBand(prices);

        List<PriceMatchQuantity> priceMatchQuantities = toQuantityDTOToPriceMatchQuantity(quantities);

        List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns = PriceMatch.getFilteredPriceList(inputPriceMatchList,
                priceMatchQuantities);

        return toPriceMatchPriceBreakDownsToPriceBreakDownDTO(priceMatchPriceBreakDowns);

    }

    private static List<PriceMatchQuantity> toQuantityDTOToPriceMatchQuantity(List<QuantityDTO> quantities) {

        List<PriceMatchQuantity> priceMatchQuantities = new ArrayList<>();

        if (quantities != null) {
            for (QuantityDTO quantityDTO : quantities) {

                PriceMatchQuantity priceMatchQty = new PriceMatchQuantity();
                priceMatchQty.setAgeBandId(quantityDTO.getAgeBandId());
                priceMatchQty.setAgeBandLabel(quantityDTO.getAgeBandLabel());
                priceMatchQty.setQuantity(quantityDTO.getQuantity());
                priceMatchQuantities.add(priceMatchQty);
            }
        }
        return priceMatchQuantities;
    }

    private static List<PriceMatchPricePerAgeBand> toPricePerAgeBandDTOToPriceMatchPricePerAgeBand(
            List<PricePerAgeBandDTO> prices) {
        
        List<PriceMatchPricePerAgeBand> priceMatchPricePerAgeBands = new ArrayList<>();
        
        for (PricePerAgeBandDTO pricePerAgeBand : prices) {
            
            PriceMatchPricePerAgeBand pMatchPerAgeBand = new PriceMatchPricePerAgeBand();
            pMatchPerAgeBand.setAgeBandId(pricePerAgeBand.getAgeBandId());
            pMatchPerAgeBand.setAgeFrom(pricePerAgeBand.getAgeFrom());
            pMatchPerAgeBand.setAgeTo(pricePerAgeBand.getAgeTo());
            pMatchPerAgeBand.setCurrencyCode(pricePerAgeBand.getCurrencyCode());
            pMatchPerAgeBand.setDescription(pricePerAgeBand.getDescription());
            pMatchPerAgeBand.setFinalPrice(pricePerAgeBand.getFinalPrice());
            pMatchPerAgeBand.setMaxBuy(pricePerAgeBand.getMaxBuy());
            pMatchPerAgeBand.setMinBuy(pricePerAgeBand.getMinBuy());
            pMatchPerAgeBand.setPriceGroupSortOrder(pricePerAgeBand.getPriceGroupSortOrder());
            pMatchPerAgeBand.setPriceType(pricePerAgeBand.getPriceType());
            pMatchPerAgeBand.setPricingUnit(pricePerAgeBand.getPricingUnit());
            pMatchPerAgeBand.setRetailPrice(pricePerAgeBand.getRetailPrice());
            pMatchPerAgeBand.setRoundedFinalPrice(pricePerAgeBand.getRoundedFinalPrice());
            
            priceMatchPricePerAgeBands.add(pMatchPerAgeBand);
            
        }
        
        return priceMatchPricePerAgeBands;
    }
    
    public static List<PriceBreakDownDTO> toPriceMatchPriceBreakDownsToPriceBreakDownDTO(
            List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns) {

        List<PriceBreakDownDTO> priceBreakDownDTOs = new ArrayList<>();

        for (PriceMatchPriceBreakDown priceBreakDown : priceMatchPriceBreakDowns) {

            PriceBreakDownDTO priceBreakDownDTO = new PriceBreakDownDTO();

            priceBreakDownDTO.setPricePerAgeBand(toPricePerAgeBandToDTO(priceBreakDown.getPricePerAgeBand()));
            priceBreakDownDTO
                    .setTotalPricePerAgeBand(toTotalPricePerAgeBandToDTO(priceBreakDown.getTotalPricePerAgeBand()));

            priceBreakDownDTOs.add(priceBreakDownDTO);

        }

        return priceBreakDownDTOs;
    }
    
    public static PricePerAgeBandDTO toPricePerAgeBandToDTO(PriceMatchPricePerAgeBand pricePerAgeBand) {

        PricePerAgeBandDTO pricePerAgeBandDTO = new PricePerAgeBandDTO();

        pricePerAgeBandDTO.setAgeBandId(pricePerAgeBand.getAgeBandId());
        pricePerAgeBandDTO.setAgeFrom(pricePerAgeBand.getAgeFrom());
        pricePerAgeBandDTO.setAgeTo(pricePerAgeBand.getAgeTo());
        pricePerAgeBandDTO.setCurrencyCode(pricePerAgeBand.getCurrencyCode());
        pricePerAgeBandDTO.setDescription(pricePerAgeBand.getDescription());
        pricePerAgeBandDTO.setFinalPrice(pricePerAgeBand.getFinalPrice());
        pricePerAgeBandDTO.setMaxBuy(pricePerAgeBand.getMaxBuy());
        pricePerAgeBandDTO.setMinBuy(pricePerAgeBand.getMinBuy());
        pricePerAgeBandDTO.setPriceGroupSortOrder(pricePerAgeBand.getPriceGroupSortOrder());
        pricePerAgeBandDTO.setPriceType(pricePerAgeBand.getPriceType());
        pricePerAgeBandDTO.setRetailPrice(pricePerAgeBand.getRetailPrice());
        pricePerAgeBandDTO.setRoundedFinalPrice(pricePerAgeBand.getRoundedFinalPrice());
        pricePerAgeBandDTO.setPricingUnit(pricePerAgeBand.getPricingUnit());

        return pricePerAgeBandDTO;
    }
    
    public static TotalPricePerAgeBandDTO toTotalPricePerAgeBandToDTO(
            PriceMatchTotalPricePerAgeBand totalPricePerAgeBand) {

        TotalPricePerAgeBandDTO totalPricePerAgeBandDTO = new TotalPricePerAgeBandDTO();

        totalPricePerAgeBandDTO.setAgeBandId(totalPricePerAgeBand.getAgeBandId());
        totalPricePerAgeBandDTO.setCurrencyCode(totalPricePerAgeBand.getCurrencyCode());
        totalPricePerAgeBandDTO.setDescription(totalPricePerAgeBand.getDescription());
        totalPricePerAgeBandDTO.setFinalPrice(totalPricePerAgeBand.getFinalPrice());
        totalPricePerAgeBandDTO.setPriceType(totalPricePerAgeBand.getPriceType());
        totalPricePerAgeBandDTO.setRetailPrice(totalPricePerAgeBand.getRetailPrice());
        totalPricePerAgeBandDTO.setRoundedFinalPrice(totalPricePerAgeBand.getRoundedFinalPrice());

        return totalPricePerAgeBandDTO;
    }
}