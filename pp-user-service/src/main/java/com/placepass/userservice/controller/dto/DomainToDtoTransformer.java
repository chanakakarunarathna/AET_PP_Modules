package com.placepass.userservice.controller.dto;

import java.util.ArrayList;
import java.util.List;

import com.placepass.userservice.domain.user.User;
import com.placepass.userservice.domain.user.UserAuthenticationToken;
import com.placepass.userservice.platform.common.DateUtil;

public class DomainToDtoTransformer {

	public static TokenVerificationRS transformUserToTokenVerificationRS(
			UserAuthenticationToken userAuthenticationToken) {

		if (userAuthenticationToken == null) {
			return null;
		}

		List<UserPermission> userPermissionRSs = null;

		if (userAuthenticationToken.getUserPermissions() != null
				&& !userAuthenticationToken.getUserPermissions().isEmpty()) {

			userPermissionRSs = new ArrayList<UserPermission>();

			for (com.placepass.userservice.domain.user.UserPermission userPermission : userAuthenticationToken
					.getUserPermissions()) {

				List<String> permissionRSs = new ArrayList<String>();

				if (userPermission.getPermissions() != null && !userPermission.getPermissions().isEmpty()) {
					for (String permission : userPermission.getPermissions()) {
						permissionRSs.add(permission);
					}
				}

				UserPermission userPermissionRS = new UserPermission(userPermission.getName(), permissionRSs);
				userPermissionRSs.add(userPermissionRS);
			}
		}

		TokenVerificationRS tokenVerificationRS = new TokenVerificationRS(userAuthenticationToken.getUserId(),
				userAuthenticationToken.getUsername(), userAuthenticationToken.getFirstName(),
				userAuthenticationToken.getLastName(), userPermissionRSs);

		return tokenVerificationRS;
	}

	public static UserProfileRS transformUserToUserProfileRS(User user) {
		UserProfileRS userProfileRS = null;
		
		if (user != null) {
			userProfileRS = new UserProfileRS();
			userProfileRS.setExternalUserId(user.getExternalUserId());
			userProfileRS.setUserId(user.getId());
			userProfileRS.setEmail(user.getEmail());
						
			if (user.getUserProfile() != null) {
				if (user.getUserProfile().getAddress() != null) {
					userProfileRS.setAddress1(user.getUserProfile().getAddress().getAddress1());
					userProfileRS.setAddress2(user.getUserProfile().getAddress().getAddress2());
					userProfileRS.setCity(user.getUserProfile().getAddress().getCity());
					userProfileRS.setCountryCode(user.getUserProfile().getAddress().getCountryCode());
					userProfileRS.setState(user.getUserProfile().getAddress().getState());
					userProfileRS.setZipCode(user.getUserProfile().getAddress().getZipCode());
				}

				userProfileRS.setDateOfBirth(DateUtil.convertToDateText(user.getUserProfile().getDateOfBirth()));
				userProfileRS.setTitle(user.getUserProfile().getTitle());
				userProfileRS.setFirstName(user.getUserProfile().getFirstName());
				userProfileRS.setLastName(user.getUserProfile().getLastName());
				userProfileRS.setPhoneCountryCode(user.getUserProfile().getPhoneCountryCode());
				userProfileRS.setPhone(user.getUserProfile().getPhone());

			}
			
			if (user.getUserSubsciption() != null) {
				userProfileRS.setSubscribedToNewsletter(user.getUserSubsciption().isSubscribedToNewsletter());
			}
			
			if (user.getAdditionalInformation() != null) {
				for(com.placepass.userservice.domain.user.AdditionalInformation additionalInformation : user.getAdditionalInformation())
				userProfileRS.getAdditionalInformation().add(new AdditionalInformation(additionalInformation.getKey(), additionalInformation.getValue()));
			}

		}
		return userProfileRS;
	}

}
