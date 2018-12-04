package com.placepass.connector.bemyguest.domain.bemyguest.product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BmgProductDetailRepository extends MongoRepository<BmgProductDetail, String> {

    public BmgProductDetail findByUuid(String uuid);

}
