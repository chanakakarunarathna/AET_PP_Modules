package com.placepass.booking.infrastructure;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.placepass.booking.application.authorize.BookingAuthorizationApplicationService;
import com.placepass.booking.application.authorize.TokenVerification;
import com.placepass.booking.application.common.TokenAuthenticationConsts;

public class BookingAuthorizationFilter extends GenericFilterBean {

    private BookingAuthorizationApplicationService bookingAuthorizationApplicationService;

    public BookingAuthorizationFilter(BookingAuthorizationApplicationService bookingAuthorizationApplicationService) {
        this.bookingAuthorizationApplicationService = bookingAuthorizationApplicationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        boolean isAuthorizedUser = true;
        if (StringUtils.hasText(servletRequest.getHeader(HttpHeaders.AUTHORIZATION))) {

            String partnerId = servletRequest.getHeader(TokenAuthenticationConsts.PARTNER_ID_HEADER);
            String token = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);

            if (!TokenAuthenticationConsts.GUEST_USER_ID.equals(token)) {

                TokenVerification tokenVerificationResponse = bookingAuthorizationApplicationService
                        .getTokenVerification(token, partnerId);

                if (String.valueOf(HttpStatus.OK.value()).equals(tokenVerificationResponse.getStatusCode())) {
                    servletRequest.setAttribute(TokenAuthenticationConsts.USER_ID,
                            tokenVerificationResponse.getUserId());
                } else {
                    isAuthorizedUser = false;
                }

                httpResponse.setHeader("pp-code", tokenVerificationResponse.getStatusCode());
                httpResponse.setHeader("pp-message", tokenVerificationResponse.getMessage());

            } else {
                servletRequest.setAttribute(TokenAuthenticationConsts.USER_ID, TokenAuthenticationConsts.GUEST_USER_ID);
            }
        }

        if (isAuthorizedUser) {
            chain.doFilter(request, response);
        }
    }
}
