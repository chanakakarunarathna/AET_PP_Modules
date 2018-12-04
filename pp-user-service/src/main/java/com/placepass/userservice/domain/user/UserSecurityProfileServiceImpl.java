package com.placepass.userservice.domain.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.userservice.infrastructure.repository.UserSecurityProfileRepository;

/**
 * The Class UserSecurityProfileServiceImpl.
 * 
 * @author shanakak
 */
@Service
public class UserSecurityProfileServiceImpl implements UserSecurityProfileService {

	@Autowired
	private UserSecurityProfileRepository userSecurityProfileRepository;
	
	@Override
	public UserSecurityProfile createUserSecurityProfile(UserSecurityProfile userSecurityProfile) {
		Date createdDate = new Date();
		userSecurityProfile.setCreatedDate(createdDate);
		userSecurityProfile.setModifiedDate(createdDate);
		userSecurityProfile = userSecurityProfileRepository.save(userSecurityProfile);
		return userSecurityProfile;
	}

	@Override
	public void updateUserSecurityProfile(UserSecurityProfile userSecurityProfile) {
		userSecurityProfile.setModifiedDate(new Date());
		userSecurityProfileRepository.save(userSecurityProfile);
		
	}

	@Override
	public UserSecurityProfile retrieveUserSecurityProfile(String partnerId, String userId) {
		
		return userSecurityProfileRepository.findByUserIdAndPartnerId(userId, partnerId);
	}


}
