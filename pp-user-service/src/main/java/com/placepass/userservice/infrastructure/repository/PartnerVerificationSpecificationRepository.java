package com.placepass.userservice.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.placepass.userservice.domain.partner.PartnerVerificationSpecification;

/**
 * The Interface VerificationRepository.
 */
@Repository
public interface PartnerVerificationSpecificationRepository extends MongoRepository<PartnerVerificationSpecification, String> {
			
	/**
	 * Find by partner id.
	 *
	 * @param partnerId the partner id
	 * @return the partner verification specification
	 */
	public PartnerVerificationSpecification findByPartnerId(String partnerId);
	
}
