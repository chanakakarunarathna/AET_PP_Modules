package com.placepass.userservice.domain.user.strategy;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.placepass.userservice.domain.user.VerificationCodeGenerationType;

/**
 * The Class VerificationCodeGenerationStrategy.
 */
@Service
public class VerificationCodeGenerationStrategyImpl implements VerificationCodeGenerationStrategy {

	@Override
	public String generateVerificationCode(VerificationCodeGenerationType verificationCodeGenerationType) {
		switch (verificationCodeGenerationType) {
		case UUID_GENERATION:
			return UUID.randomUUID().toString();
		default:
			throw new IllegalArgumentException("Invalid verificationCodeGenerationType provided.");
		}

	}

}
