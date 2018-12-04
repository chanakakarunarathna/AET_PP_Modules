package com.placepass.booking.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentResponse;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalResponse;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;

@Component
public class VendorPaymentServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(VendorPaymentServiceImpl.class);

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;
    
    @Value("${stripe.makepayment.url}")
    private String makePaymentUrl;
    
    @Value("${stripe.makerefund.url}")
    private String makeRefundUrl;
    
    @Value("${stripe.baseurl}")
    private String stripeBaseUrl;
    
    public ConnectorPaymentResponse makePayment(ConnectorPaymentRequest paymentRequest){
    	
    	ConnectorPaymentResponse paymentResponse = null;
    	
    	try {

    		String url = stripeBaseUrl + makePaymentUrl;
            HttpEntity<ConnectorPaymentRequest> requestEntity = new HttpEntity<ConnectorPaymentRequest>(paymentRequest);
            ResponseEntity<ConnectorPaymentResponse> response = restTemplate.postForEntity(
                    url, requestEntity, ConnectorPaymentResponse.class);
            paymentResponse = response.getBody();

        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the payment connector", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
            logger.error("HttpClientErrorException occurred while connecting to the payment connector", hce);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_REQUEST.toString(),
                    PlacePassExceptionCodes.BAD_REQUEST.getDescription());

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the payment connector", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the payment connector", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }
        return paymentResponse;
        
    }
    
    public ConnectorPaymentReversalResponse makeRefund(ConnectorPaymentReversalRequest reversalRequest){
    	
    	ConnectorPaymentReversalResponse reversalResponse = null;
    	
    	try {

    		String url = stripeBaseUrl + makeRefundUrl;
            HttpEntity<ConnectorPaymentReversalRequest> requestEntity = new HttpEntity<ConnectorPaymentReversalRequest>(reversalRequest);
            ResponseEntity<ConnectorPaymentReversalResponse> response = restTemplate.postForEntity(
                    url, requestEntity, ConnectorPaymentReversalResponse.class);
            reversalResponse = response.getBody();

        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the vendor connector", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
            logger.error("HttpClientErrorException occurred while connecting to the vendor connector", hce);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_REQUEST.toString(),
                    PlacePassExceptionCodes.BAD_REQUEST.getDescription());

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the vendor connector", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the vendor connector", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }
        return reversalResponse;
        
    }
	
}
