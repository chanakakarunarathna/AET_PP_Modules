package com.placepass.userservice.infrastructure.service;

import java.util.Map;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.placepass.userservice.controller.dto.AuthenticationByProviderRQ;

@MessagingGateway
public interface ProviderAuthenticationGatewayService {

    @Gateway(requestChannel = "provider.authentication.request.channel")
    public Map<String, String> authenticateByProvider(AuthenticationByProviderRQ authenticationByProviderRQ);

}
