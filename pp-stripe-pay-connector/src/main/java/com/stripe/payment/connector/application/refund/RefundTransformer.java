package com.stripe.payment.connector.application.refund;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.stripe.payment.connector.application.common.StripeConnectorUtil;
import com.stripe.payment.connector.domain.refund.RefundTransactionResponse;

@Component
public class RefundTransformer {

    public ConnectorPaymentReversalResponse toConnectorPaymentReversalResponse(
            RefundTransactionResponse refundTransactionResponse) {

        ConnectorPaymentReversalResponse response = new ConnectorPaymentReversalResponse();

        if (refundTransactionResponse.getRefund() != null) {
            Instant processedTime = StripeConnectorUtil
                    .getInstantFromLong(refundTransactionResponse.getRefund().getCreated());
            response.setProcessedTime(processedTime);
            response.setReversalReason(refundTransactionResponse.getRefund().getReason());
            response.setExtReversalTxId(refundTransactionResponse.getRefund().getId());

        }

        response.setExternalStatuses(refundTransactionResponse.getExternalStatuses());
        // check OrderProcessStatus
        response.setPaymentStatus(refundTransactionResponse.getPaymentStatus());

        return response;

    }

}
