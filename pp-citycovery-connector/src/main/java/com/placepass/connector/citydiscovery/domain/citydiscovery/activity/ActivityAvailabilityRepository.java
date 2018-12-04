package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityAvailabilityRepository extends MongoRepository<ActivityAvailability, String>{

    public ActivityAvailability findByProductID(String productId);
    
}
