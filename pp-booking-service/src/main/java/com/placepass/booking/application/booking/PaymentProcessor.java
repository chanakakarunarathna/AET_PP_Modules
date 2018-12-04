package com.placepass.booking.application.booking;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentResponse;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalResponse;
import com.placepass.booking.application.booking.paymentcondto.PaymentProcessStatus;
import com.placepass.booking.application.booking.paymentcondto.PaymentTransaction;
import com.placepass.booking.infrastructure.ConnectorGateway;
import com.placepass.booking.infrastructure.VendorPaymentServiceImpl;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;

@Service
public class PaymentProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

    @Autowired
    private ConnectorGateway connectorGateway;

    @Value("${rabbitmq.payment.exchangename}")
    private String paymentExchangeName = null;

    @Value("${com.placepass.bookingservice.paymentsimulation}")
    private boolean simulation = false;
    
    @Value("${stripe.connectionmode:REST}")
    private String stripeConnectionMode;  
    
    @Autowired
    private VendorPaymentServiceImpl paymentService;
    
    private static final String REST = "REST";

    private static final String AMQP = "AMQP";

    public ConnectorPaymentResponse makePayment(ConnectorPaymentRequest paymentRequest) {

        paymentRequest.setExchangeName(paymentExchangeName);
        ConnectorPaymentResponse response = null;

        Gson g = new Gson();
        String paymentRequestJson = g.toJson(paymentRequest);
        logger.debug("ConnectorPaymentRequest JSON : {}", paymentRequestJson);

        try {
            if (!simulation) {
            	
                logger.info("Making ConnectorPaymentRequest request in LIVE MODE");
                if (AMQP.equals(stripeConnectionMode)){
                	response = (ConnectorPaymentResponse) connectorGateway.sendRequest(paymentRequest);
                }else if(REST.equals(stripeConnectionMode)){
                	response = paymentService.makePayment(paymentRequest);
                }else{
                	logger.error("Invalid stripe connection mode : " + stripeConnectionMode);
               	 	throw new InternalErrorException(PlacePassExceptionCodes.INVALID_STRIPE_CONNECTION_MODE.toString(),
                         PlacePassExceptionCodes.INVALID_STRIPE_CONNECTION_MODE.getDescription() + " - " + stripeConnectionMode);
                }

            } else {
                logger.info("Making ConnectorPaymentRequest request in SIMULATION MODE");
                response = mockPaymentResponse(paymentRequest);
            }
        } catch (Exception e) {

            logger.error("Exception occured on payment while communicating with payment connector", e);
            response = new ConnectorPaymentResponse();
            response.setPaymentStatus(PaymentProcessStatus.PAYMENT_PROCESSING_ERROR);

        }

        // If response is null it could be a potential timeout.
        if (response == null) {

            logger.error(
                    "Connector Payment Response received null response from payment connector. Possibly a timeout occured processing the Connector Payment Request");
            response = new ConnectorPaymentResponse();
            response.setPaymentStatus(PaymentProcessStatus.PAYMENT_ISSUER_TIMEOUT);

        } else {
            Map<String, Object> logData = new HashMap<String, Object>();
            logData.put("externalStatus", response.getExternalStatuses());
            logData.put("payment-status", response.getPaymentStatus());
            logData.put("reversal-details", response.getReversalDetails());
            logger.info("Received ConnectorPaymentResponse {}", logData);
        }

        return response;

    }

    // Returns a mock response simulating a payment connector response
    // TODO: may need to add more params/logic for different responses
    private ConnectorPaymentResponse mockPaymentResponse(ConnectorPaymentRequest paymentRequest) {
        Map<String, String> extStatus = new HashMap<>();
        extStatus.put("SUCCESS", "SUCESS");

        ConnectorPaymentResponse response = new ConnectorPaymentResponse();
        response.setAmountTendered(paymentRequest.getPaymentAmount());
        response.setExternalStatuses(extStatus);
        response.setExtPaymentTxRefId("extPaymentTxRefId");
        response.setPaymentReversalTriggered(false);
        response.setPaymentStatus(PaymentProcessStatus.PAYMENT_SUCCESS);
        response.setReversalDetails(null);

        response.setProcessedTime(Instant.now());
        return response;
    }

    public ConnectorPaymentReversalResponse reversePayment(PaymentTransaction paymentTx,
            ConnectorPaymentRequest paymentReq, String reversalReason) {

        ConnectorPaymentReversalRequest reversalRequest = new ConnectorPaymentReversalRequest();
        reversalRequest.setPartnerId(paymentTx.getPartnerId());
        reversalRequest.setUserId(paymentReq.getUserId());
        reversalRequest.setOriginalPaymentTx(paymentTx);
        reversalRequest.setReversalReason(reversalReason);
        reversalRequest.setGatewayName(paymentReq.getGatewayName());
        reversalRequest.setPaymentToken(paymentReq.getPaymentToken());
        reversalRequest.setReversalTxId(UUID.randomUUID().toString());
        reversalRequest.setBookingId(paymentReq.getBookingId());
        reversalRequest.setBookingTxId(paymentReq.getBookingTxId());

        reversalRequest.setTxCreatedTime(Instant.now());

        reversalRequest.setExchangeName(paymentExchangeName);
        ConnectorPaymentReversalResponse response = null;

        Gson g = new Gson();
        String reversalRequestJson = g.toJson(reversalRequest);
        logger.debug("ConnectorPaymentReversalRequest JSON: {}", reversalRequestJson);

        try {

            if (!simulation) {
            	
                logger.info("Making ConnectorPaymentReversalRequest request in LIVE MODE");
                if (AMQP.equals(stripeConnectionMode)){
                	response = (ConnectorPaymentReversalResponse) connectorGateway.sendRequest(reversalRequest);
                }else if (REST.equals(stripeConnectionMode)){
                	response = paymentService.makeRefund(reversalRequest);
                }else{
                	logger.error("Invalid stripe connection mode : " + stripeConnectionMode);
               	 	throw new InternalErrorException(PlacePassExceptionCodes.INVALID_STRIPE_CONNECTION_MODE.toString(),
                         PlacePassExceptionCodes.INVALID_STRIPE_CONNECTION_MODE.getDescription() + " - "+ stripeConnectionMode);
                }
                
            } else {
                logger.info("Making ConnectorPaymentReversalRequest request in SIMULATION MODE");
                response = mockPaymentReversalResponse();
            }

        } catch (Exception e) {

            logger.error("Exception occured on payment reversal while communicating with payment connector", e);
            response = new ConnectorPaymentReversalResponse();
            response.setPaymentStatus(PaymentProcessStatus.PAYMENT_PROCESSING_ERROR);
        }

        // This may be a processor timeout or a connector timeout.
        if (response == null) {

            logger.error(
                    "Connector Payment Reversal Response received null response from payment connector. Possibly a timeout occured while processing the Connector Payment Reversal Request");
            response = new ConnectorPaymentReversalResponse();
            response.setPaymentStatus(PaymentProcessStatus.PAYMENT_ISSUER_TIMEOUT);

        } else {

            Map<String, Object> logData = new HashMap<>();
            logData.put("externalStatus", response.getExternalStatuses());
            logData.put("payment-status", response.getPaymentStatus());
            logger.info("Received ConnectorPaymentReversalResponse {}", logData);
        }

        return response;
    }

    public ConnectorPaymentReversalResponse refundPayment(ConnectorPaymentReversalRequest reversalRequest) {

        reversalRequest.setExchangeName(paymentExchangeName);
        ConnectorPaymentReversalResponse response = null;

        Gson g = new Gson();
        String reversalRequestJson = g.toJson(reversalRequest);
        logger.debug("ConnectorPaymentRefundRequest JSON: {}", reversalRequestJson);

        try {

            if (!simulation) {
                
            	logger.info("Making ConnectorPaymentRefundRequest request in LIVE MODE");
                if (AMQP.equals(stripeConnectionMode)){
                	response = (ConnectorPaymentReversalResponse) connectorGateway.sendRequest(reversalRequest);
                }else if (REST.equals(stripeConnectionMode)){
                	response = paymentService.makeRefund(reversalRequest);
                }else{
                	logger.error("Invalid stripe connection mode : " + stripeConnectionMode);
                	 throw new InternalErrorException(PlacePassExceptionCodes.INVALID_STRIPE_CONNECTION_MODE.toString(),
                             PlacePassExceptionCodes.INVALID_STRIPE_CONNECTION_MODE.getDescription() + " - "+ stripeConnectionMode);
                }
                
            } else {
                logger.info("Making ConnectorPaymentRefundRequest request in SIMULATION MODE");
                response = mockPaymentReversalResponse();
            }

        } catch (Exception e) {

            logger.error("Exception occured on payment refund while communicating with payment connector", e);
            response = new ConnectorPaymentReversalResponse();
            response.setPaymentStatus(PaymentProcessStatus.PAYMENT_PROCESSING_ERROR);
        }

        // This may be a processor timeout or a connector timeout.
        if (response == null) {
            logger.error(
                    "Connector Payment Refund Response received null response from payment connector. Possibly a timeout occured while processing the Connector Payment Refund Request");
            response = new ConnectorPaymentReversalResponse();
            response.setPaymentStatus(PaymentProcessStatus.PAYMENT_ISSUER_TIMEOUT);

        } else {

            Map<String, Object> logData = new HashMap<>();
            logData.put("externalStatus", response.getExternalStatuses());
            logData.put("payment-status", response.getPaymentStatus());
            logger.info("Received ConnectorPaymentRefundResponse {}", logData);
        }

        return response;
    }

    // Returns a mock response simulating a payment reversal connector response
    // TODO: may need to add more params/logic for different responses
    private ConnectorPaymentReversalResponse mockPaymentReversalResponse() {
        Map<String, String> extStatus = new HashMap<>();
        extStatus.put("SUCCESS", "SUCESS");

        ConnectorPaymentReversalResponse response = new ConnectorPaymentReversalResponse();
        response.setPaymentStatus(PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS);
        response.setExternalStatuses(extStatus);
        return response;
    }

}
