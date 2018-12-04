package com.placepass.booking.infrastructure;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.placepass.booking.infrastructure.discount.DiscountFeeRQ;
import com.placepass.booking.infrastructure.discount.DiscountFeeRS;
import com.placepass.booking.infrastructure.discount.RedeemDiscountRQ;
import com.placepass.booking.infrastructure.discount.RedeemDiscountRS;
import com.placepass.booking.infrastructure.discount.ReverseDiscountRQ;
import com.placepass.booking.infrastructure.discount.ReverseDiscountRS;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;

@Service
public class DiscountServiceImpl implements DiscountService {

    /** The Constant PARTNER_ID. */
    private static final String PARTNER_ID = "partner-id";

    /** The Constant PP_CODE_KEY. */
    private static final String PP_CODE_KEY = "pp-code";

    /** The Constant PP_MESSAGE_KEY. */
    private static final String PP_MESSAGE_KEY = "pp-message";

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

    @Value("${discount.service.fee.url}")
    private String discountFeeUrl;

    @Value("${discount.service.redeem.url}")
    private String redeemDiscountUrl;

    @Value("${discount.service.redeem.url}")
    private String reverseDiscountUrl;

    @Autowired
    private RestConfig restConfig;
    
    
    @Override
    public DiscountFeeRS discountFee(String partnerId, DiscountFeeRQ discountFeeRQ) {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(PARTNER_ID, partnerId);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            RequestEntity<DiscountFeeRQ> discountFeeRequestEntity = new RequestEntity<DiscountFeeRQ>(discountFeeRQ, headers, HttpMethod.POST, new URI(discountFeeUrl));
            
            ResponseEntity<DiscountFeeRS> discountFeeResponseEntity = restConfig.restTemplate()
                    .exchange(discountFeeRequestEntity, DiscountFeeRS.class);
            return discountFeeResponseEntity.getBody();

        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the discount fee endpoint", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
            logger.error("HttpClientErrorException occurred while connecting to the discount fee endpoint", hce);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_REQUEST.toString(),
                    PlacePassExceptionCodes.BAD_REQUEST.getDescription());

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the discount fee endpoint", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the discount fee endpoint", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }
    }

    @Override
    public RedeemDiscountRS redeemDiscount(String partnerId, RedeemDiscountRQ redeemDiscountRQ) {

        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(PARTNER_ID, partnerId);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            RequestEntity<RedeemDiscountRQ> redeemDiscountRequestEntity = new RequestEntity<RedeemDiscountRQ>(redeemDiscountRQ, headers, HttpMethod.POST, new URI(redeemDiscountUrl)); 
            
            ResponseEntity<RedeemDiscountRS> redeemDiscountResponseEntity = restConfig.restTemplate()
                    .exchange(redeemDiscountRequestEntity, RedeemDiscountRS.class);
            return redeemDiscountResponseEntity.getBody();

        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the redeem discount endpoint", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
                    
            logger.error("HttpClientErrorException occurred while connecting to the redeem discount endpoint", hce);
            
            String statusCode = PlacePassExceptionCodes.BAD_REQUEST.toString();
            String statusMessage = PlacePassExceptionCodes.BAD_REQUEST.toString();
            
            if (hce.getResponseHeaders() != null) {
                if (hce.getResponseHeaders().get(PP_CODE_KEY) != null) {
                    statusCode = hce.getResponseHeaders().get(PP_CODE_KEY).toString();
                }
                if (hce.getResponseHeaders().get(PP_MESSAGE_KEY) != null) {
                    statusMessage = hce.getResponseHeaders().get(PP_MESSAGE_KEY).toString();
                }
            }
            
            throw new NotFoundException(statusCode, statusMessage);

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the redeem discount endpoint", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the redeem discount endpoint", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }

    }

    @Override
    public ReverseDiscountRS reverseDiscount(String partnerId, ReverseDiscountRQ reverseDiscountRQ) {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(PARTNER_ID, partnerId);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            
            RequestEntity<ReverseDiscountRQ> discountFeeRequestEntity = new RequestEntity<ReverseDiscountRQ>(reverseDiscountRQ, headers, HttpMethod.POST, new URI(reverseDiscountUrl));
            ResponseEntity<ReverseDiscountRS> reverseDiscountResponseEntity = restConfig.restTemplate()
                    .exchange(discountFeeRequestEntity, ReverseDiscountRS.class);
            return reverseDiscountResponseEntity.getBody();
        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the reverse discount endpoint", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
            logger.error("HttpClientErrorException occurred while connecting to the reverse discount endpoint", hce);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_REQUEST.toString(),
                    PlacePassExceptionCodes.BAD_REQUEST.getDescription());

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the reverse discount endpoint", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the reverse discount endpoint", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }

    }

  

}
