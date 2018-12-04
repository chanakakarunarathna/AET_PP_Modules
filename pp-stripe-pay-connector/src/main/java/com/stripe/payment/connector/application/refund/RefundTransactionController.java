package com.stripe.payment.connector.application.refund;

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
import com.stripe.payment.connector.domain.common.LogMessage;
import com.stripe.payment.connector.domain.refund.RefundTransactionResponse;
import com.stripe.payment.connector.domain.refund.RefundTransactionService;

@RestController
public class RefundTransactionController {

    private static final Logger logger = LoggerFactory.getLogger(RefundTransactionController.class);

    @Autowired
    private RefundTransactionService refundTransactionService;

    @Autowired
    private RefundTransformer refundTransformer;

    @RequestMapping(value = "/stripe/refund", method = RequestMethod.POST)
    @ResponseBody
    public ConnectorPaymentReversalResponse makeRefund(
            @Valid @RequestBody ConnectorPaymentReversalRequest refundRequest, HttpServletResponse response) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), refundRequest.getPartnerId());
        logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), refundRequest.getUserId());
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), refundRequest.getBookingId());
        logData.put(PlatformLoggingKey.REFUND_ID.name(), refundRequest.getReversalTxId());

        try {
            logger.info("Received 'Make Refund' Request. {}", logData);

            RefundTransactionResponse refundTransactionResponse = refundTransactionService.makeRefund(refundRequest,
                    logData);
            ConnectorPaymentReversalResponse connectorPaymentReversalResponse = refundTransformer
                    .toConnectorPaymentReversalResponse(refundTransactionResponse);
            logger.info("Returning 'Make Refund' Response.");
            return connectorPaymentReversalResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}