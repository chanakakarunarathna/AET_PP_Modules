package com.placepass.userservice.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.placepass.userservice.domain.partner.PartnerAuthenticationTokenExpirationSpecification;

/**
 * The Interface PartnerAuthenticationTokenExpirationSpecificationRepository.
 */
@Repository
public interface PartnerAuthenticationTokenExpirationSpecificationRepository extends MongoRepository<PartnerAuthenticationTokenExpirationSpecification, String>{
    
    /**
     * Find by partner id.
     *
     * @param partnerId the partner id
     * @return the partner authentication token expiration specification
     */
    public PartnerAuthenticationTokenExpirationSpecification findByPartnerId(String partnerId);

}
