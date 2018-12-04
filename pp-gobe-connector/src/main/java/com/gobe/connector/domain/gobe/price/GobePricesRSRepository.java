package com.gobe.connector.domain.gobe.price;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created on 8/7/2017.
 */
public interface GobePricesRSRepository extends MongoRepository<GobePrice, String>{

    public List<GobePrice> findByTourIdRegex(String tourId);
}
