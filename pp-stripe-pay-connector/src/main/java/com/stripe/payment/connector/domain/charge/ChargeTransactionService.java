package com.stripe.payment.connector.domain.charge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.PermissionException;
import com.stripe.exception.RateLimitException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import com.stripe.payment.connector.application.PaymentProcessStatus;
import com.stripe.payment.connector.application.TransactionFieldsConstant;
import com.stripe.payment.connector.application.charge.ConnectorPaymentRequest;
import com.stripe.payment.connector.domain.common.CardExceptionStrategy;
import com.stripe.payment.connector.domain.common.LogMessage;
import com.stripe.payment.connector.domain.config.StripeAccountConfig;

@Service
public class ChargeTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(ChargeTransactionService.class);

    @Autowired
    private BeanFactory beanFactory;

    @Value("${stripe.account.config}")
    private String stripeAccountConfig;

    @Autowired
    private CardExceptionStrategy cardExceptionStrategy;

    public ChargeTransactionResponse makeCharge(ConnectorPaymentRequest connectorPaymentRequest,
            Map<String, Object> logData) {

        ChargeTransactionResponse chargeResponse = new ChargeTransactionResponse();
        Map<String, String> externalStatus = new HashMap<>();

        try {
            logger.info(LogMessage.getLogMessage(logData, "Sending charge request to Stripe Gateway"));

            RequestOptions requestOptions = (RequestOptions) beanFactory.getBean("stripeRequestOptions");
            List<StripeAccountConfig> listOfStripeAccountConfigs = new Gson().fromJson(stripeAccountConfig,
                    new TypeToken<List<StripeAccountConfig>>() {
                    }.getType());
            Optional<StripeAccountConfig> stripeAccountConfig = listOfStripeAccountConfigs.stream().filter(
                    accountConfigs -> accountConfigs.getPartnerId().equals(connectorPaymentRequest.getPartnerId()))
                    .findAny();
            if (stripeAccountConfig.isPresent()) {
                requestOptions = requestOptions.toBuilder().setIdempotencyKey(connectorPaymentRequest.getBookingId())
                        .setApiKey(stripeAccountConfig.get().getPrivateSecret()).build();
            } else {
                throw new NotFoundException(PlacePassExceptionCodes.PARTNER_CONFIG_NOT_FOUND.toString(),
                        PlacePassExceptionCodes.PARTNER_CONFIG_NOT_FOUND.getDescription());
            }

            Map<String, Object> chargeMap = new HashMap<String, Object>();
            chargeMap.put("currency", "USD");
            chargeMap.put("amount", connectorPaymentRequest.getPaymentAmount());
            chargeMap.put("source", connectorPaymentRequest.getPaymentToken());
            chargeMap.put("description", connectorPaymentRequest.getBookingId());
            chargeMap.put("statement_descriptor", connectorPaymentRequest.getPaymentStatementDescriptor());

            Charge charge = Charge.create(chargeMap, requestOptions);
            chargeResponse.setCharge(charge);

            if (charge != null && charge.getStatus().equals("succeeded")) {

                logger.info(LogMessage.getLogMessage(logData, "Received a success response from Stripe gateway"));

                externalStatus.put(TransactionFieldsConstant.TX_AMOUNT.name(),
                        chargeResponse.getCharge().getAmount().toString());
                externalStatus.put(TransactionFieldsConstant.TX_STATUS.name(), chargeResponse.getCharge().getStatus());
                externalStatus.put(TransactionFieldsConstant.TX_CURRENCY.name(),
                        chargeResponse.getCharge().getCurrency());
                chargeResponse.setExternalStatuses(externalStatus);
                chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_SUCCESS);

            } else if (charge != null && charge.getStatus().equals("failed")) {

                logger.info(LogMessage.getLogMessage(logData, "Received a failed response from Stripe gateway"));

                externalStatus.put(TransactionFieldsConstant.TX_AMOUNT.name(),
                        chargeResponse.getCharge().getAmount().toString());
                externalStatus.put(TransactionFieldsConstant.TX_STATUS.name(), chargeResponse.getCharge().getStatus());
                externalStatus.put(TransactionFieldsConstant.TX_CURRENCY.name(),
                        chargeResponse.getCharge().getCurrency());
                chargeResponse.setExternalStatuses(externalStatus);
                chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_FAILED);

            } else if (charge != null && charge.getStatus().equals("pending")) {

                logger.info(LogMessage.getLogMessage(logData, "Received a pending response from Stripe gateway"));

                externalStatus.put(TransactionFieldsConstant.TX_AMOUNT.name(),
                        chargeResponse.getCharge().getAmount().toString());
                externalStatus.put(TransactionFieldsConstant.TX_STATUS.name(), chargeResponse.getCharge().getStatus());
                externalStatus.put(TransactionFieldsConstant.TX_CURRENCY.name(),
                        chargeResponse.getCharge().getCurrency());
                chargeResponse.setExternalStatuses(externalStatus);
                chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_PENDING);

            }
        } catch (CardException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            chargeResponse.setExternalStatuses(externalStatus);
            chargeResponse.setPaymentStatus(cardExceptionStrategy.getPaymentProcessStatus(e));
        } catch (RateLimitException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            chargeResponse.setExternalStatuses(externalStatus);
            chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_RATE_LIMIT_ERROR);
        } catch (InvalidRequestException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            chargeResponse.setExternalStatuses(externalStatus);
            chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_REQ_ERROR);
        } catch (PermissionException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            chargeResponse.setExternalStatuses(externalStatus);
            chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_PERMISSION_ERROR);
        } catch (AuthenticationException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            chargeResponse.setExternalStatuses(externalStatus);
            chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_AUTHENTICATION_ERROR);
        } catch (APIConnectionException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            chargeResponse.setExternalStatuses(externalStatus);
            chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_CONNECTION_ERROR);
        } catch (APIException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            chargeResponse.setExternalStatuses(externalStatus);
            chargeResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_API_ERROR);
        } catch (NotFoundException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving partner configuration"), e);
            chargeResponse.setPaymentStatus(PaymentProcessStatus.PARTNER_CONFIG_NOT_FOUND);
        } catch (Exception e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            chargeResponse.setExternalStatuses(externalStatus);
            chargeResponse.setPaymentStatus(PaymentProcessStatus.UNKNOWN_PAYMENT_GATEWAY_ERROR);
        }

        return chargeResponse;
    }

}
