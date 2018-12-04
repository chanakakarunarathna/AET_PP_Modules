package com.placepass.booking.application.booking;

import java.time.Instant;

import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalRequest;
import com.placepass.booking.application.booking.paymentcondto.PaymentTransaction;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.Payment;
import com.placepass.booking.domain.booking.cancel.Refund;
import com.placepass.utils.currency.AmountFormatter;

public class BookingPaymentConTransformer {

    public static ConnectorPaymentRequest toConnectorPaymentRequest(Payment payment, Booking booking) {

        ConnectorPaymentRequest paymentRequest = new ConnectorPaymentRequest();
        paymentRequest.setPartnerId(booking.getPartnerId());
        paymentRequest.setUserId(booking.getCustomerId());
        paymentRequest.setBookingId(booking.getId());
        paymentRequest.setPaymentAmount(AmountFormatter.getLowestUnit(payment.getPaymentAmount()));
        paymentRequest.setPaymentToken(payment.getPaymentToken());
        paymentRequest.setGatewayName(payment.getPaymentGatewayName());
        paymentRequest.setPaymentTxId(payment.getPaymentId());
        paymentRequest.setPaymentStatementDescriptor(payment.getStatementDescriptor());
        paymentRequest.setTxCreatedTime(payment.getCreatedTime());
        paymentRequest.setFormattedPaymentAmount(AmountFormatter.format(payment.getPaymentAmount(),
                payment.getCurrencyCode()));
        return paymentRequest;
    }

    public static PaymentTransaction toPaymentTransaction(Booking booking, Payment payment) {

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPartnerId(booking.getPartnerId());
        paymentTransaction.setBookingId(booking.getId());
        paymentTransaction.setExtPaymentTxId(payment.getExtPaymentTxId());
        paymentTransaction.setPaymentAmount(AmountFormatter.getLowestUnit(payment.getPaymentAmount()));
        paymentTransaction.setGatewayName(payment.getPaymentGatewayName());
        paymentTransaction.setTxCreatedTime(payment.getCreatedTime());
        paymentTransaction.setPaymentTxId(payment.getPaymentId());
        return paymentTransaction;
    }

    public static ConnectorPaymentReversalRequest toConnectorPaymentRefundRequest(Booking booking, Refund refund) {

        PaymentTransaction originalPaymentTx = toPaymentTransaction(booking, refund.getOriginalPayment());
        ConnectorPaymentReversalRequest reversalRequest = new ConnectorPaymentReversalRequest();
        reversalRequest.setPartnerId(booking.getPartnerId());
        reversalRequest.setUserId(booking.getCustomerId());
        reversalRequest.setOriginalPaymentTx(originalPaymentTx);
        reversalRequest.setReversalReason(refund.getRefundReason());
        reversalRequest.setGatewayName(refund.getPaymentGatewayName());
        reversalRequest.setPaymentToken(refund.getOriginalPayment().getPaymentToken());
        reversalRequest.setReversalTxId(refund.getRefundId());
        reversalRequest.setBookingId(booking.getId());
        reversalRequest.setBookingTxId(null);
        reversalRequest.setTxCreatedTime(Instant.now());
        return reversalRequest;
    }

}
