package com.placepass.userservice.domain.user.strategy;

public interface CryptographyStrategy {

	String hash(CharSequence password);
	
	boolean verifyHash(CharSequence password, String hash);
	
}
