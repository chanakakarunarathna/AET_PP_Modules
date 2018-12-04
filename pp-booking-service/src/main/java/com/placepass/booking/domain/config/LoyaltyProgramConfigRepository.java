package com.placepass.booking.domain.config;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoyaltyProgramConfigRepository extends MongoRepository<LoyaltyProgramConfig, String> {

    public List<LoyaltyProgramConfig> findByPartnerId(String partnerId);

    public LoyaltyProgramConfig findByPartnerIdAndProgId(String partnerId, String loyaltyProgramId);
    
}
