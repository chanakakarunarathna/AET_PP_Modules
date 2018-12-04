package com.placepass.userservice.domain.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.placepass.userservice.infrastructure.repository.UserRepository;

/**
 * The Class UserManagementServiceImpl.
 * 
 * @author shanakak
 */
@Service
public class UserManagementServiceImpl implements UserManagementService {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User user) {

		Date createdDate = new Date();
		user.setCreatedDate(createdDate);
		user.setModifiedDate(createdDate);
		user.setActive(true);
		user.setDeleted(false);
		
		if (StringUtils.hasText(user.getEmail())) {
			user.setEmail(user.getEmail().toLowerCase());
		}
		
		return userRepository.save(user);
	}

	@Override
	public void updateUser(User user) {

		user.setModifiedDate(new Date());
		
		if (StringUtils.hasText(user.getEmail())) {
			user.setEmail(user.getEmail().toLowerCase());
		}
		
		userRepository.save(user);
	}

	@Override
	public User retrieveUserByEmail(String partnerId, String email) {
		return userRepository.findByEmailAndPartnerIdAndDeleted(email.toLowerCase(), partnerId, false);
	}

	@Override
	public User retrieveUser(String partnerId, String id) {
		return userRepository.findByIdAndPartnerIdAndDeleted(id, partnerId, false);
	}
	
	@Override
	public User retrieveUserByExternalUserId(String partnerId, String externalUserId) {
		return userRepository.findByExternalUserIdAndPartnerIdAndDeleted(externalUserId, partnerId, false);
	}

}
