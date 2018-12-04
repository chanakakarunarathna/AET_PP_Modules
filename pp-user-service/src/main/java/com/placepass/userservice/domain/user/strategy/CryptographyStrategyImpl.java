package com.placepass.userservice.domain.user.strategy;

import java.security.SecureRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CryptographyStrategyImpl implements CryptographyStrategy {

	@Value("${placepass.cryptography.strength:12}")
	private int cryptographyStrength;

	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void initPasswordEncoder() {
		// TODO: Add athiva crypto implementation
		SecureRandom secureRandom = new SecureRandom();
		// seed secure random
		secureRandom.nextBytes(new byte[1]);
		passwordEncoder = new BCryptPasswordEncoder(cryptographyStrength, secureRandom);
	}
	
	@Override
	public String hash(CharSequence password) {
		// TODO : Change this with encryption services
		return passwordEncoder.encode(password);
	}

	@Override
	public boolean verifyHash(CharSequence password, String hash) {
		// TODO : Change this with encryption services
		return passwordEncoder.matches(password, hash);
	}

}
