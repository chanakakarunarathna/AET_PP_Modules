package com.placepass.product.application.availability;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.placepass.exutil.BadRequestException;
import com.placepass.connector.common.product.Availability;
import com.placepass.product.application.availability.dto.AvailabilityDTO;
import com.placepass.product.application.availability.dto.GetAvailabilityRS;
import com.placepass.product.application.availability.dto.PriceDTO;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;

public class AvailabilityTransformer {

    public static GetAvailabilityRS toGetAvailabilityRS(
            com.placepass.connector.common.product.GetAvailabilityRS availabilities) {
        List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();

        if (availabilities.getResultType().getCode() == 0) {

            for (Availability availability : availabilities.getAvailability()) {
                AvailabilityDTO availabilityDTO = new AvailabilityDTO();
                availabilityDTO.setDate(availability.getDate());

                if (availability.getPrice() != null) {
                    PriceDTO priceDTO = new PriceDTO();
                    priceDTO.setCurrencyCode(availability.getPrice().getCurrencyCode());
                    priceDTO.setDescription(availability.getPrice().getDescription());
                    priceDTO.setPriceType(availability.getPrice().getPriceType());
                    if (PlacePassAgeBandType.ADULT.name().equals(availability.getPrice().getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                    } else if (PlacePassAgeBandType.SENIOR.name().equals(availability.getPrice().getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.SENIOR.ageBandId);
                    } else if (PlacePassAgeBandType.CHILD.name().equals(availability.getPrice().getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.CHILD.ageBandId);
                    } else if (PlacePassAgeBandType.INFANT.name().equals(availability.getPrice().getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.INFANT.ageBandId);
                    } else if (PlacePassAgeBandType.YOUTH.name().equals(availability.getPrice().getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.YOUTH.ageBandId);
                    }
                    priceDTO.setFinalPrice(availability.getPrice().getFinalPrice());
                    priceDTO.setRoundedFinalPrice(
                            AmountFormatter.floatToFloatRoundingFinalTotal(availability.getPrice().getFinalPrice()));
                    priceDTO.setRetailPrice(availability.getPrice().getRetailPrice());

                    availabilityDTO.setPrice(priceDTO);
                }
                availabilityDTO.setSoldOut(availability.isSoldOut());

                availabilityDTOs.add(availabilityDTO);
            }

        } else if (availabilities.getResultType().getCode() == 1) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), availabilities.getResultType().getMessage());
        }

        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
        getAvailabilityRS.setAvailabilityList(availabilityDTOs);

        return getAvailabilityRS;
    }
}
