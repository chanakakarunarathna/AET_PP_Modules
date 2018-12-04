package com.placepass.connector.bemyguest.domain.bemyguest.availability;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BmgProductAvailabilityRepository extends MongoRepository<BmgProductAvailability, String> {

    BmgProductAvailability findByProductID(String productID);

}
