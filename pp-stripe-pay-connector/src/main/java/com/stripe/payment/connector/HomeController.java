package com.stripe.payment.connector;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The application home page of Stripe Payment connector
 */
@RestController
public class HomeController {

    @Value("${spring.profiles.active:DEFAULT}")
    private String profile;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"", "/"}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String home(Locale locale, Model model) {

        String welcomeMsg = "[" + profile + "] PlacePass Stripe Connector is up and running!";

        logger.info(welcomeMsg);

        return welcomeMsg;
    }
}
