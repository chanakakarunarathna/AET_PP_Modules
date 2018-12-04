package com.placepass.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placepass.userservice.platform.common.RequestMappings;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class HomeController {

	@Value("${spring.profiles.active}")
	private String profile;

	@RequestMapping(value = { RequestMappings.CONTEXT_PATH,
			RequestMappings.CONTEXT_PATH_ROOT }, produces = MediaType.TEXT_PLAIN_VALUE)
	public String home() {
		return "[" + profile + "] PlacePass User Service is up and running!";
	}

}