package com.placepass.userservice.domain.user.strategy;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class DefaultPasswordGenerationStrategyImpl.
 * 
 * @author shanakak
 */
@Service
public class DefaultPasswordGenerationStrategyImpl implements PasswordGenerationStrategy {

	/** The generated password length. */
	@Value("${placepass.user.password.length}")
	private int generatedPasswordLength;

	@Override
	public String generatePassword() {
		return new RandomStringGenerator.Builder().withinRange(32, 122).build().generate(generatedPasswordLength);
	}

}
