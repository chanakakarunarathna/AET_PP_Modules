package com.placepass.userservice.domain.user.strategy;

import com.placepass.userservice.domain.user.VerificationCodeGenerationType;

public interface VerificationCodeGenerationStrategy {

	String generateVerificationCode(VerificationCodeGenerationType verificationCodeGenerationType);
	
}
