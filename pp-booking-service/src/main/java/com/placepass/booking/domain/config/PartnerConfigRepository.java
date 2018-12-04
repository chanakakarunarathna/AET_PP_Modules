package com.placepass.booking.domain.config;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartnerConfigRepository extends MongoRepository<PartnerConfig, String> {
    
    PartnerConfig findByPartnerId(String partnerId);

}
