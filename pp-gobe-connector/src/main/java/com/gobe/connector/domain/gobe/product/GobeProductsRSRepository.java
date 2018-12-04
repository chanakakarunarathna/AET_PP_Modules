package com.gobe.connector.domain.gobe.product;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created on 8/7/2017.
 */
public interface GobeProductsRSRepository extends MongoRepository<GobeProduct, String>{
}
