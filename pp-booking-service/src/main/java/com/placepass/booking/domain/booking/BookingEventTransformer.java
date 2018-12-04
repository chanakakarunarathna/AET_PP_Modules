package com.placepass.booking.domain.booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.placepass.booking.application.booking.paymentcondto.PaymentProcessStatus;

public class BookingEventTransformer {

	public static Map<String,String> constructExtendedAttributeForPartnerConfigRetrieved(String partnerId){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.PARTNER_ID.name(), partnerId);
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForCartRetrieved(String cartId, String partnerId){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.PARTNER_ID.name(), partnerId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.CART_ID.name(), cartId);
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForCartValidated(String cartId, String partnerId){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.PARTNER_ID.name(), partnerId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.CART_ID.name(), cartId);
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForProductDetailRequestSent(String partnerId, String productId){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.PARTNER_ID.name(), partnerId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.PRODUCT_ID.name(), productId);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForProductDetailResponseReceived(String partnerId, String productId){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.PARTNER_ID.name(), partnerId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.PRODUCT_ID.name(), productId);		
		return extendedAttributes;
	}
	
    public static Map<String, String> constructExtendedAttributeForLoyaltyProcessed(String loyaltyAccountId,
            String loyaltyProgramId, int loyaltyPoints) {
        Map<String, String> extendedAttributes = new HashMap<String, String>();
        extendedAttributes.put(BookingEventExtendedAttributeKey.LOYALTY_ACCOUNT_ID.name(), loyaltyAccountId);
        extendedAttributes.put(BookingEventExtendedAttributeKey.LOYALTY_PROGRAM_ID.name(), loyaltyProgramId);
        extendedAttributes.put(BookingEventExtendedAttributeKey.LOYALTY_POINTS.name(), String.valueOf(loyaltyPoints));

        return extendedAttributes;
    }
    
    public static Map<String, String> constructExtendedAttributeForDiscountProcessed(List<Discount> discounts) {
        Map<String, String> extendedAttributes = new HashMap<String, String>();
        if (discounts != null) {
            extendedAttributes.put(BookingEventExtendedAttributeKey.DISCOUNTS.name(), new Gson().toJson(discounts));
        }

        return extendedAttributes;
    }
    
	
	public static Map<String,String> constructExtendedAttributeForPaymentRequestSent(int amount, String paymentTransactionId, String merchantId, String locationId, String gatewayName, String exchangeName, String paymentToken){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.AMOUNT.name(), String.valueOf(amount));
		extendedAttributes.put(BookingEventExtendedAttributeKey.PAYMENT_TRANSACTION_ID.name(), paymentTransactionId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.MERCHANT_ID.name(), merchantId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.LOCATION_ID.name(), locationId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.GATEWAY_NAME.name(), gatewayName);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EXCHANGE_NAME.name(), exchangeName);
		extendedAttributes.put(BookingEventExtendedAttributeKey.PAYMENT_TOKEN.name(), paymentToken);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForPaymentResponseReceived(PaymentProcessStatus paymentProcessStatus, Map<String,String> externalStatus, int amount, String externalPaymentTransactionId){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		if (externalStatus != null){
			extendedAttributes.putAll(externalStatus);
		}
		extendedAttributes.put(BookingEventExtendedAttributeKey.PAYMENT_STATUS.name(), paymentProcessStatus.name());
		extendedAttributes.put(BookingEventExtendedAttributeKey.AMOUNT.name(), String.valueOf(amount));		
		extendedAttributes.put(BookingEventExtendedAttributeKey.EXTERNAL_PAYMENT_TRANSACTION_ID.name(), externalPaymentTransactionId);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForBookingRequestSent(String bookingId, String vendor, float amount, String productId){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.BOOKING_ID.name(), bookingId);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.AMOUNT.name(), String.valueOf(amount));		
		extendedAttributes.put(BookingEventExtendedAttributeKey.VENDOR.name(), vendor);	
		extendedAttributes.put(BookingEventExtendedAttributeKey.PRODUCT_ID.name(), productId);
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForBookingResponseReceived(String bookingId, String bookingRefercence, Float amount){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.BOOKING_ID.name(), bookingId);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.BOOKING_REFERENCE.name(), bookingRefercence);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.AMOUNT.name(), String.valueOf(amount));
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForCartClosed(String cartId){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.CART_ID.name(), cartId);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForPaymentReversalRequestSent(String paymentReversalReason, String originalPaymentTrnasactionId, String gatewayName, String paymentToken, String refundMode, String refundAmount){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.PAYMENT_REVERSAL_REASON.name(), paymentReversalReason);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.ORIGINAL_PAYMENT_TRANSACTION_ID.name(), originalPaymentTrnasactionId);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.GATEWAY_NAME.name(), gatewayName);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.PAYMENT_TOKEN.name(), paymentToken);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.REFUND_MODE.name(), refundMode);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.REFUND_AMOUNT.name(), refundAmount);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForPaymentReversalResponseReceived(PaymentProcessStatus paymentProcessStatus, Map<String,String> externalStatus, String externalReversalTransactionId, String reversalReason){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		if (externalStatus != null){
			extendedAttributes.putAll(externalStatus);
		}
		extendedAttributes.put(BookingEventExtendedAttributeKey.PAYMENT_STATUS.name(), paymentProcessStatus.name());	
		extendedAttributes.put(BookingEventExtendedAttributeKey.EXTERNAL_REVERSAL_TRANSACTION_ID.name(), externalReversalTransactionId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.PAYMENT_REVERSAL_REASON.name(), reversalReason);
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForPendingEmailProcessed(String emailAddress, String emailStatus, String emailError){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_ADDRESS.name(), emailAddress);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_STATUS.name(), emailStatus);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_ERROR.name(), emailError);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForConfirmationEmailProcessed(String emailAddress, String emailStatus, String emailError){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_ADDRESS.name(), emailAddress);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_STATUS.name(), emailStatus);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_ERROR.name(), emailError);		
		return extendedAttributes;
	}

	public static Map<String,String> constructExtendedAttributeForCancellationEmailProcessed(String emailAddress, String emailStatus, String emailError, String refundMode, String refundAmount){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_ADDRESS.name(), emailAddress);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_STATUS.name(), emailStatus);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_ERROR.name(), emailError);
		extendedAttributes.put(BookingEventExtendedAttributeKey.REFUND_MODE.name(), refundMode);		
		extendedAttributes.put(BookingEventExtendedAttributeKey.REFUND_AMOUNT.name(), refundAmount);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForRejectedEmailProcessed(String emailAddress, String emailStatus, String emailError){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_ADDRESS.name(), emailAddress);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_STATUS.name(), emailStatus);
		extendedAttributes.put(BookingEventExtendedAttributeKey.EMAIL_ERROR.name(), emailError);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForCancelBookingRequestSent(String bookingId, String bookingReference, String vendor, String cancelDescription, String cancellationReasonCode, String cancellationType){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.BOOKING_ID.name(), bookingId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.BOOKING_REFERENCE.name(), bookingReference);
		extendedAttributes.put(BookingEventExtendedAttributeKey.VENDOR.name(), vendor);
		extendedAttributes.put(BookingEventExtendedAttributeKey.CANCEL_DESCRIPTION.name(), cancelDescription);
		extendedAttributes.put(BookingEventExtendedAttributeKey.CANCEL_REASON_CODE.name(), cancellationReasonCode);
		extendedAttributes.put(BookingEventExtendedAttributeKey.CANCELLATION_TYPE.name(), cancellationType);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForCancelBookingResponseReceived(String bookingId, String bookingReference, Float cancellationFee, String status, String connectorStatus){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.BOOKING_ID.name(), bookingId);
		extendedAttributes.put(BookingEventExtendedAttributeKey.BOOKING_REFERENCE.name(), bookingReference);
		extendedAttributes.put(BookingEventExtendedAttributeKey.CANCELLATION_FEE.name(), String.valueOf(cancellationFee));
		extendedAttributes.put(BookingEventExtendedAttributeKey.STATUS.name(), status);
		extendedAttributes.put(BookingEventExtendedAttributeKey.CONNECTOR_STATUS.name(), connectorStatus);		
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForBookingStatusUpdated(String oldStatus, String newStatus){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.OLD_STATUS.name(), oldStatus);
		extendedAttributes.put(BookingEventExtendedAttributeKey.NEW_STATUS.name(), newStatus);
		return extendedAttributes;
	}
	
	public static Map<String,String> constructExtendedAttributeForVoucherRetrievalRequestSent(String bookingReference, String vendor){
		Map<String,String> extendedAttributes = new HashMap<String, String>();
		extendedAttributes.put(BookingEventExtendedAttributeKey.BOOKING_REFERENCE.name(), bookingReference);
		extendedAttributes.put(BookingEventExtendedAttributeKey.VENDOR.name(), vendor);
		return extendedAttributes;
	}
	
}
