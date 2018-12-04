package com.stripe.payment.connector.application;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.logging.PlatformLoggingKey;
import com.stripe.payment.connector.application.charge.ChargeTransformer;
import com.stripe.payment.connector.application.charge.ConnectorPaymentRequest;
import com.stripe.payment.connector.application.charge.ConnectorPaymentResponse;
import com.stripe.payment.connector.application.refund.ConnectorPaymentReversalRequest;
import com.stripe.payment.connector.application.refund.ConnectorPaymentReversalResponse;
import com.stripe.payment.connector.application.refund.RefundTransformer;
import com.stripe.payment.connector.domain.charge.ChargeTransactionResponse;
import com.stripe.payment.connector.domain.charge.ChargeTransactionService;
import com.stripe.payment.connector.domain.common.LogMessage;
import com.stripe.payment.connector.domain.refund.RefundTransactionResponse;
import com.stripe.payment.connector.domain.refund.RefundTransactionService;

@Service
public class PaymentAppService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentAppService.class);

    @Autowired
    private ChargeTransactionService chargeTransactionService;

    @Autowired
    private ChargeTransformer chargeTransformer;

    @Autowired
    private RefundTransactionService refundTransactionService;

    @Autowired
    private RefundTransformer refundTransformer;

    @Value("${stripe.payment.simulation}")
    private boolean simulation = false;

    public Object handleMessage(Object request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        Object response = null;
        Gson g = new Gson();

        try {

            logger.debug(LogMessage.getLogMessage(logData, "STRIPE connector received request : " + g.toJson(request)));

            if (request != null) {
                if (ConnectorPaymentRequest.class.equals(request.getClass())) {

                    ConnectorPaymentRequest paymentRequest = (ConnectorPaymentRequest) request;

                    logData.put(PlatformLoggingKey.PARTNER_ID.name(), paymentRequest.getPartnerId());
                    logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), paymentRequest.getUserId());
                    logData.put(PlatformLoggingKey.BOOKING_ID.name(), paymentRequest.getBookingId());
                    logData.put(PlatformLoggingKey.PAYMENT_ID.name(), paymentRequest.getPaymentTxId());
                    logData.put(PlatformLoggingKey.PAYMENT_AMOUNT.name(), paymentRequest.getPaymentAmount());

                    if (!simulation) {
                        logger.info(LogMessage.getLogMessage(logData,
                                "Connector Making ConnectorPaymentRequest in LIVE MODE."));
                        response = handlePayment(paymentRequest, logData);
                    } else {
                        logger.info(LogMessage.getLogMessage(logData,
                                "Connector Making ConnectorPaymentRequest in SIMULATION MODE."));
                        response = handlePaymentSimulation(paymentRequest);
                    }

                } else if (ConnectorPaymentReversalRequest.class.equals(request.getClass())) {

                    ConnectorPaymentReversalRequest paymentReversalRequest = (ConnectorPaymentReversalRequest) request;

                    logData.put(PlatformLoggingKey.PARTNER_ID.name(), paymentReversalRequest.getPartnerId());
                    logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), paymentReversalRequest.getUserId());
                    logData.put(PlatformLoggingKey.BOOKING_ID.name(), paymentReversalRequest.getBookingId());
                    logData.put(PlatformLoggingKey.REFUND_ID.name(), paymentReversalRequest.getReversalTxId());

                    if (!simulation) {
                        logger.info(LogMessage.getLogMessage(logData,
                                "Connector Making ConnectorPaymentReversalRequest in LIVE MODE."));
                        response = handlePaymentReversal(paymentReversalRequest, logData);
                    } else {
                        logger.info(LogMessage.getLogMessage(logData,
                                "Connector Making ConnectorPaymentReversalRequest in SIMULATION MODE"));
                        response = handlePaymentReversalSimulation(paymentReversalRequest);
                    }
                }

            }
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Error processing request"), e);

            if (request != null) {
                if (ConnectorPaymentRequest.class.equals(request.getClass())) {
                    ConnectorPaymentResponse paymentResponse = new ConnectorPaymentResponse();
                    paymentResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_PROCESSING_ERROR);
                    response = paymentResponse;
                }
            }
        }

        logger.info(getPaymentResponseLogMessage(response, logData, "STRIPE connector returning response : "));

        return response;

    }

    private ConnectorPaymentResponse handlePayment(ConnectorPaymentRequest paymentRequest,
            Map<String, Object> logData) {

        logger.info("Received 'Make Charge' Request. {}", logData);

        ChargeTransactionResponse chargeResponse = chargeTransactionService.makeCharge(paymentRequest, logData);
        ConnectorPaymentResponse connectorPaymentResponse = chargeTransformer
                .toConnectorPaymentResponse(chargeResponse);

        logger.info("Returning 'Make Charge' Response.");

        return connectorPaymentResponse;
    }

    private ConnectorPaymentReversalResponse handlePaymentReversal(
            ConnectorPaymentReversalRequest paymentReversalRequest, Map<String, Object> logData) {

        logger.info("Received 'Make Refund' Request. {}", logData);

        RefundTransactionResponse refundTransactionResponse = refundTransactionService
                .makeRefund(paymentReversalRequest, logData);

        ConnectorPaymentReversalResponse connectorPaymentReversalResponse = refundTransformer
                .toConnectorPaymentReversalResponse(refundTransactionResponse);

        logger.info("Returning 'Make Refund' Response.");

        return connectorPaymentReversalResponse;
    }

    private ConnectorPaymentResponse handlePaymentSimulation(ConnectorPaymentRequest paymentRequest) {

        Map<String, String> extStatus = new HashMap<String, String>();
        extStatus.put("SUCCESS", "SUCCESS");

        ConnectorPaymentResponse response = new ConnectorPaymentResponse();
        response.setAmountTendered(123);
        response.setExternalStatuses(extStatus);
        response.setExtPaymentTxRefId("extPaymentTxRefId");
        response.setPaymentReversalTriggered(false);
        response.setPaymentStatus(PaymentProcessStatus.PAYMENT_SUCCESS);
        response.setReversalDetails(null);

        return response;
    }

    private ConnectorPaymentReversalResponse handlePaymentReversalSimulation(
            ConnectorPaymentReversalRequest paymentReversalRequest) {

        Map<String, String> extStatus = new HashMap<String, String>();
        extStatus.put("SUCCESS", "SUCCESS");

        ConnectorPaymentReversalResponse response = new ConnectorPaymentReversalResponse();
        response.setPaymentStatus(PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS);
        response.setExternalStatuses(extStatus);

        return response;
    }

    private String getPaymentResponseLogMessage(Object response, Map<String, Object> logData, String message) {

        ConnectorPaymentResponse paymentResponse = null;

        if (logData == null) {
            logData = new HashMap<String, Object>();

        }
        if (response != null) {

            if (ConnectorPaymentResponse.class.equals(response.getClass())) {
                paymentResponse = (ConnectorPaymentResponse) response;

                logData.put(PlatformLoggingKey.PAYMENT_EXT_STATUSES.name(), paymentResponse.getExternalStatuses());
                logData.put(PlatformLoggingKey.PAYMENT_STATUS.name(), String.valueOf(paymentResponse.getPaymentStatus()));
                logData.put(PlatformLoggingKey.PAYMENT_EXT_REFERENCE.name(), paymentResponse.getExtPaymentTxRefId());
                logData.put(PlatformLoggingKey.IS_PAYMENT_REVERSED.name(), Boolean.toString(paymentResponse.isPaymentReversalTriggered()));

                ReversalDetails rd = paymentResponse.getReversalDetails();

                if (rd != null) {

                    logData.put(PlatformLoggingKey.PAYMENT_REVERSAL_EXT_STATUSES.name(), rd.getExternalStatuses());
                    logData.put(PlatformLoggingKey.PAYMENT_REVERSAL_EXT_REFERENCE.name(), rd.getExtReversalTxRefId());
                    logData.put(PlatformLoggingKey.IS_REVERSAL_SUCCESSFUL.name(), Boolean.toString(rd.isReversalSuccessful()));

                }

            }
        }

        return LogMessage.getLogMessage(logData, message);
    }

}
