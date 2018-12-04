package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.connector.citydiscovery.application.exception.ProductNotFoundException;
import com.placepass.connector.citydiscovery.application.util.VendorErrorCode;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityDisplay getActivityDisplay(int activityId) throws ProductNotFoundException {

        ActivityDisplay activityDisplay = activityRepository.findByActivityID(activityId);

        if (activityDisplay == null) {
            throw new ProductNotFoundException(VendorErrorCode.PRODUCT_NOT_FOUND.toString());
        }

        return activityDisplay;
    }

}
