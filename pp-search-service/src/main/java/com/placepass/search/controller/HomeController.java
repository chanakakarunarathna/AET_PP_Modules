package com.placepass.search.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class HomeController {

	@RequestMapping(value = { "", "/" }, produces = MediaType.TEXT_PLAIN_VALUE)
	public String home() {
		return "PlacePass Search Service is up and running!";
	}

}