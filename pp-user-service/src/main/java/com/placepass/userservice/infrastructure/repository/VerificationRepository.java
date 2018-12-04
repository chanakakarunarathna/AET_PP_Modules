package com.placepass.userservice.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.placepass.userservice.domain.user.VerificationCode;
import com.placepass.userservice.domain.user.VerificationType;

/**
 * The Interface VerificationRepository.
 */
@Repository
public interface VerificationRepository extends MongoRepository<VerificationCode, String> {
		
	/**
	 * Find by code and partner id and verification type.
	 *
	 * @param code the code
	 * @param partnerId the partner id
	 * @param verificationType the verification type
	 * @return the verification code
	 */
	public VerificationCode findByCodeAndPartnerIdAndVerificationType(String code, String partnerId, VerificationType verificationType);
	
}
