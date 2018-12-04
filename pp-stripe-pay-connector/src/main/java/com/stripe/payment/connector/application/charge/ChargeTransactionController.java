package com.stripe.payment.connector.application.charge;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.logging.PlatformLoggingKey;
import com.stripe.payment.connector.domain.charge.ChargeTransactionResponse;
import com.stripe.payment.connector.domain.charge.ChargeTransactionService;
import com.stripe.payment.connector.domain.common.LogMessage;

@RestController
public class ChargeTransactionController {

    private static final Logger logger = LoggerFactory.getLogger(ChargeTransactionController.class);

    @Autowired
    private ChargeTransactionService chargeTransactionService;

    @Autowired
    private ChargeTransformer chargeTransformer;

    @RequestMapping(value = "/stripe/charge", method = RequestMethod.POST)
    @ResponseBody
    public ConnectorPaymentResponse makeCharge(@Valid @RequestBody ConnectorPaymentRequest paymentRequest,
            HttpServletResponse response) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), paymentRequest.getPartnerId());
        logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), paymentRequest.getUserId());
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), paymentRequest.getBookingId());
        logData.put(PlatformLoggingKey.PAYMENT_ID.name(), paymentRequest.getPaymentTxId());
        logData.put(PlatformLoggingKey.PAYMENT_AMOUNT.name(), paymentRequest.getPaymentAmount());

        try {

            logger.info("Received 'Make Charge' Request. {}", logData);

            ChargeTransactionResponse chargeResponse = chargeTransactionService.makeCharge(paymentRequest, logData);
            ConnectorPaymentResponse connectorPaymentResponse = chargeTransformer
                    .toConnectorPaymentResponse(chargeResponse);

            logger.info("Returning 'Make Charge' Response.");

            return connectorPaymentResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}