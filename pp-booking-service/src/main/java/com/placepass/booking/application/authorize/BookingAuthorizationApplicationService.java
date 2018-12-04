package com.placepass.booking.application.authorize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.placepass.booking.application.common.TokenAuthenticationConsts;

@Service
public class BookingAuthorizationApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(BookingAuthorizationApplicationService.class);

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Value("${user.service.baseurl}")
    private String userServiceBaseUrl;

    @Value("${user.service.verifytokenurl}")
    private String verifyTokenUrl;

    public TokenVerification getTokenVerification(String token, String partnerId) {

        logger.info("Invoking token verification service ");

        String url = userServiceBaseUrl + verifyTokenUrl;

        logger.info("Invoking token verification service url " + url);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, TokenAuthenticationConsts.CONTENT_TYPE_APP_JSON);
        httpHeaders.add(TokenAuthenticationConsts.PARTNER_ID_HEADER, partnerId);

        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        TokenVerification tokenVerification = new TokenVerification();

        try {
            ResponseEntity<TokenVerification> response = restTemplate.postForEntity(url, request,
                    TokenVerification.class);

            logger.info("Token verification service response " + response.getStatusCode());

            tokenVerification = response.getBody();
            tokenVerification.setMessage(TokenAuthenticationConsts.HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION);
            tokenVerification.setStatusCode(String.valueOf(HttpStatus.OK.value()));

        } catch (HttpClientErrorException hce) {

            if (hce.getRawStatusCode() == HttpStatus.FORBIDDEN.value()) {

                logger.info("Token verification service error occured : " + hce.getRawStatusCode());

                tokenVerification.setMessage(TokenAuthenticationConsts.HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION);
                tokenVerification.setStatusCode(String.valueOf(hce.getRawStatusCode()));

            } else if (hce.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()) {

                logger.info("Token verification service error occured : " + hce.getRawStatusCode());
                tokenVerification.setMessage(TokenAuthenticationConsts.HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION);
                tokenVerification.setStatusCode(String.valueOf(hce.getRawStatusCode()));
            }
        } catch (Exception e) {

            logger.info("Token verification service error occured : " + e.getMessage());

            tokenVerification.setMessage(TokenAuthenticationConsts.HTTP_STATUS_INTERNAL_SERVER_ERROR);
            tokenVerification
                    .setStatusCode(String.valueOf(TokenAuthenticationConsts.HTTP_STATUS_INTERNAL_SERVER_ERROR_CODE));
        }
        return tokenVerification;
    }
}
