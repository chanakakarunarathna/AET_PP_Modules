package com.stripe.payment.connector.domain.refund;

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
import com.stripe.model.Refund;
import com.stripe.net.RequestOptions;
import com.stripe.payment.connector.application.PaymentProcessStatus;
import com.stripe.payment.connector.application.TransactionFieldsConstant;
import com.stripe.payment.connector.application.refund.ConnectorPaymentReversalRequest;
import com.stripe.payment.connector.domain.common.CardExceptionStrategy;
import com.stripe.payment.connector.domain.common.LogMessage;
import com.stripe.payment.connector.domain.config.StripeAccountConfig;

@Service
public class RefundTransactionService {

	private static final Logger logger = LoggerFactory.getLogger(RefundTransactionService.class);

	@Autowired
	private BeanFactory beanFactory;

	@Value("${stripe.account.config}")
    private String stripeAccountConfig;
	
    @Autowired
    private CardExceptionStrategy cardExceptionStrategy;

	public RefundTransactionResponse makeRefund(ConnectorPaymentReversalRequest connectorPaymentReversalRequest,
			Map<String, Object> logData) {

		RefundTransactionResponse refundResponse = new RefundTransactionResponse();
		Map<String, String> externalStatus = new HashMap<>();

		try {
			logger.info(LogMessage.getLogMessage(logData, "Sending refund request to Stripe Gateway"));

			RequestOptions requestOptions = (RequestOptions) beanFactory.getBean("stripeRequestOptions");
            List<StripeAccountConfig> listOfStripeAccountConfigs = new Gson().fromJson(stripeAccountConfig, new TypeToken<List<StripeAccountConfig>>(){}.getType());
            Optional<StripeAccountConfig> stripeAccountConfig = listOfStripeAccountConfigs.stream().filter(accountConfigs -> accountConfigs.getPartnerId().equals(connectorPaymentReversalRequest.getPartnerId())).findAny();
            if (stripeAccountConfig.isPresent()) {
                requestOptions = requestOptions.toBuilder().setIdempotencyKey(connectorPaymentReversalRequest.getOriginalPaymentTx().getPaymentTxId()).setApiKey(stripeAccountConfig.get().getPrivateSecret()).build();
            } else {
                throw new NotFoundException(PlacePassExceptionCodes.PARTNER_CONFIG_NOT_FOUND.toString(), PlacePassExceptionCodes.PARTNER_CONFIG_NOT_FOUND.getDescription());
            }

			Map<String, Object> refundMap = new HashMap<String, Object>();
			refundMap.put("charge", connectorPaymentReversalRequest.getOriginalPaymentTx().getExtPaymentTxId());
            if (connectorPaymentReversalRequest.getRefundAmount() > 0) {
                refundMap.put("amount", connectorPaymentReversalRequest.getRefundAmount());
            }

			Refund refund = Refund.create(refundMap, requestOptions);
			refundResponse.setRefund(refund);

			if (refund != null && refund.getStatus().equals("succeeded")) {

				logger.info(LogMessage.getLogMessage(logData, "Received a success response from Stripe gateway"));

				refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS);

			} else if (refund != null && refund.getStatus().equals("failed")) {

				logger.info(LogMessage.getLogMessage(logData, "Received a failed response from Stripe gateway"));

				refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_REVERSAL_FAILED);

			} else if (refund != null && refund.getStatus().equals("pending")) {

				logger.info(LogMessage.getLogMessage(logData, "Received a pending response from Stripe gateway"));

				refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_REVERSAL_FAILED);

			}

		} catch (CardException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            refundResponse.setExternalStatuses(externalStatus);
            refundResponse.setPaymentStatus(cardExceptionStrategy.getPaymentProcessStatus(e));
        } catch (RateLimitException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            refundResponse.setExternalStatuses(externalStatus);
            refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_RATE_LIMIT_ERROR);
        } catch (InvalidRequestException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            refundResponse.setExternalStatuses(externalStatus);
            refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_REQ_ERROR);
        } catch (PermissionException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            refundResponse.setExternalStatuses(externalStatus);
            refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_PERMISSION_ERROR);
        } catch (AuthenticationException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            refundResponse.setExternalStatuses(externalStatus);
            refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_AUTHENTICATION_ERROR);
        } catch (APIConnectionException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            refundResponse.setExternalStatuses(externalStatus);
            refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_CONNECTION_ERROR);
        } catch (APIException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            refundResponse.setExternalStatuses(externalStatus);
            refundResponse.setPaymentStatus(PaymentProcessStatus.PAYMENT_GATEWAY_API_ERROR);
        } catch (NotFoundException e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving partner configuration"), e);
            refundResponse.setPaymentStatus(PaymentProcessStatus.PARTNER_CONFIG_NOT_FOUND);
        } catch (Exception e) {
            logger.error(LogMessage.getLogMessage(logData, "Error retrieving transaction"), e);
            externalStatus.put(TransactionFieldsConstant.ERROR_MESSAGE.name(), e.getMessage());
            refundResponse.setExternalStatuses(externalStatus);
            refundResponse.setPaymentStatus(PaymentProcessStatus.UNKNOWN_PAYMENT_GATEWAY_ERROR);
        }

		return refundResponse;
	}

}
