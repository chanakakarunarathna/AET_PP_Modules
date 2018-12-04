package com.placepass.userservice.domain.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.userservice.infrastructure.repository.VerificationRepository;

/**
 * The Class VerificationServiceImpl.
 * 
 * @author shanakak
 */
@Service
public class VerificationServiceImpl implements VerificationService {
	
	/** The verification repository. */
	@Autowired
	private VerificationRepository verificationRepository;

	@Override
	public VerificationCode createVerificationCode(VerificationCode verificationCode) {
		Date createdDate = new Date();
		verificationCode.setCreatedDate(createdDate);
		verificationCode.setModifiedDate(createdDate);
		return verificationRepository.save(verificationCode);
	}
	
	@Override
	public void updateVerificationCode(VerificationCode verificationCode) {
		verificationCode.setModifiedDate(new Date());
		verificationRepository.save(verificationCode);
	}
	
	@Override
	public VerificationCode retrieveVerificationCode(String partnerId, String code, VerificationType verificationType) {
		return verificationRepository.findByCodeAndPartnerIdAndVerificationType(code, partnerId, verificationType);
	}

}
