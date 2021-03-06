package com.placepass.connector.bemyguest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class HomeController {

    @Value("${spring.profiles.active:DEFAULT}")
    private String profile;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"", "/"}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String home() {

        String welcomeMsg = "[" + profile + "] PlacePass bemyguest connector is up and running!";

        logger.info(welcomeMsg);

        return welcomeMsg;
    }

}