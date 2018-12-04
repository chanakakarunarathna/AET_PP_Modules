package com.placepass.connector.bemyguest.domain.bemyguest.availability;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BmgProductAvailabilityService {
    @Autowired
    private BmgProductAvailabilityRepository bmgProductAvailabilityRepository;

    public BmgProductAvailability getBmgProductAvailability(String productId) {

        BmgProductAvailability bmgProductAvailability = bmgProductAvailabilityRepository.findByProductID(productId);

        return bmgProductAvailability;
    }
}
