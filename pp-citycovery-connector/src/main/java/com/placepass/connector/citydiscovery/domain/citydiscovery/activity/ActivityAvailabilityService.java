package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.connector.citydiscovery.application.exception.ProductNotFoundException;
import com.placepass.connector.citydiscovery.application.util.VendorErrorCode;

@Service
public class ActivityAvailabilityService {

    @Autowired
    private ActivityAvailabilityRepository activityAvailabilityRepository;

    public ActivityAvailability getActivityAvailability(String productId) throws ProductNotFoundException {

        ActivityAvailability activityAvailability = activityAvailabilityRepository.findByProductID(productId);

        if (activityAvailability == null) {
            throw new ProductNotFoundException(VendorErrorCode.PRODUCT_NOT_FOUND.toString());
        }

        return activityAvailability;
    }
}
