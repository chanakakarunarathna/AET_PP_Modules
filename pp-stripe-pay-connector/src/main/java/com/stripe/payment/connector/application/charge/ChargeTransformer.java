package com.stripe.payment.connector.application.charge;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.stripe.model.Card;
import com.stripe.payment.connector.application.common.StripeConnectorUtil;
import com.stripe.payment.connector.domain.charge.ChargeTransactionResponse;

@Component
public class ChargeTransformer {

	public ConnectorPaymentResponse toConnectorPaymentResponse(ChargeTransactionResponse chargeResponse) {

		ConnectorPaymentResponse response = new ConnectorPaymentResponse();
		
		if (chargeResponse.getCharge() != null) {
		    Instant processedTime = StripeConnectorUtil.getInstantFromLong(chargeResponse.getCharge().getCreated());
            response.setProcessedTime(processedTime);
            response.setAmountTendered(chargeResponse.getCharge().getAmount().intValue());
			response.setExtPaymentTxRefId(chargeResponse.getCharge().getId());
            if ("card".equals(chargeResponse.getCharge().getSource().getObject())) {
                Card card = (Card) chargeResponse.getCharge().getSource();

                CardInfo cardInfo = new CardInfo();
                cardInfo.setCardType(card.getBrand());
                cardInfo.setLast4CardNumber(card.getLast4());
                response.setCardInfo(cardInfo);
            }

		}

		response.setExternalStatuses(chargeResponse.getExternalStatuses());
		// check OrderProcessStatus
		response.setPaymentStatus(chargeResponse.getPaymentStatus());
		
		response.setPaymentReversalTriggered(false);
		/*if (OrderProcessStatus.PAYMENT_PROCESSOR_TIMEOUT.equals(chargeResponse.getPaymentStatus())) {
			ReversalDetails reversalDetails = new ReversalDetails();
			response.setPaymentReversalTriggered(true);
			reversalDetails.setReversalSuccessful(false);
			response.setReversalDetails(reversalDetails);
		} else {
			response.setPaymentReversalTriggered(false);
		}*/

		return response;

	}

}
