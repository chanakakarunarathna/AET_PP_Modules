package com.placepass.userservice.domain.user.strategy;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.placepass.userservice.platform.common.CommonConstants;

@Service
public class AuthTokenGenerationStrategyImpl implements AuthTokenGenerationStrategy {

	@Override
	public String generateToken() {
		return UUID.randomUUID().toString().replace(CommonConstants.DASH, CommonConstants.EMPTY);
	}

}
