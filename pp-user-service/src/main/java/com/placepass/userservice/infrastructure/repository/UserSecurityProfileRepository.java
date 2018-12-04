package com.placepass.userservice.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.placepass.userservice.domain.user.UserSecurityProfile;

/**
 * The Interface UserSecurityProfileRepository.
 * 
 * @author shanakak
 */
@Repository
public interface UserSecurityProfileRepository extends MongoRepository<UserSecurityProfile, String> {
			
	/**
	 * Find by user id and partner id.
	 *
	 * @param userId the user id
	 * @param partnerId the partner id
	 * @return the user security profile
	 */
	public UserSecurityProfile findByUserIdAndPartnerId(String userId, String partnerId);

}
