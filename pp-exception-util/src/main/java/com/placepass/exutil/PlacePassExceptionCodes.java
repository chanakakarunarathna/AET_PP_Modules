package com.placepass.exutil;

public enum PlacePassExceptionCodes {

    ENCRYPTION_FAILED("Error while encrypting"),
    DECRYPTION_FAILED("Error while decrypting"),
    INVALID_DATE("Invalid date value provided. The date should be in MM/dd/yyyy format"),
    INVALID_DATE_WITH_TIMESTAMP("Invalid date value provided. The date should be in yyyy-MM-dd HH:mm:ss format"),
    INVALID_SORT_PARAMETER("Given sort parameter is not valid."),
    INVALID_PAGE_INDEX("Index page cannot be less than to zero."),
    INVALID_PAGE_SIZE("Page size cannot be less than or equal to zero."),
    INVALID_START_INDEX("Start index canot be less than 0"),
    INVALID_UPPER_BOUND("Upper limit cannot be less than or equal to start index"),
    INVALID_STD_DATE("Invalid date value provided. The date should be in yyyy-MM-dd format"),
    INVALID_HITS_PER_PAGE("Hits per page should be a number and should be greater than zero"),
    INVALID_PAGE_NUMBER("Page number should be a number and can not be a negative value"),

    EXCEED_NO_OF_BOOKING_OPTIONS("Cart cannot accept more than one booking option"),
    BOOKING_OPTION_NOT_FOUND("Booking option not found for given cartId and partnerId"),
    BOOKING_OPTION_ID_NOT_FOUND("Booking option id not found"),
    BOOKING_OPTION_NOT_FOUND_IN_BOOKING("Booking option not found in booking with id %s"),
    BOOKER_DETAIL_NOT_FOUND_IN_BOOKING("Booker detail not found in booking with id %s"),
    BOOKING_STATUS_NOT_FOUND_IN_BOOKING("Booking status not found in booking with id %s"),
    PRODUCT_ID_NOT_FOUND("Product id not found"),
    HOTEL_PICKUP_UNAVAILABLE("Hotel pickup unavailable for given productId"),
    BAD_REQUEST("Not a valid request. Please ensure all required parameters are present and in valid format."),
    BAD_GATEWAY("The proxy server received an invalid response from an upstream server"),
    CART_CLOSED("Cart is already closed"),
    CART_VALIDATION_FAILED("Cart validation failed"),
    PRODUCT_OPTION_ID_NOT_FOUND("Relevent product option id not found"),
    BOOKING_NOT_FOUND("Booking not found for the given params"),
    CART_NOT_FOUND("Cart not found for the given CART ID and PARTNER ID"),
    PARTNER_CONFIG_NOT_FOUND("Partner config not found for the give partner id"),
    MULTIPLE_LEAD_TRAVELERS_FOUND("Multiple lead travelers not allow"),
    LEAD_TRAVELER_NOT_FOUND("Lead Traveler not found"),
    PLACE_NOT_FOUND("Place not found for the given place ID"),
    PLACES_NOT_FOUND("No Places found"),
    INVALID_DATE_OF_BIRTH("Date of birth should be a past date"),
    INVALID_PRODUCT_OPTION_ID("Invalid product option id"),
    INVALID_PRODUCT_ID("Invalid product id"),

    VOUCHER_NOT_FOUND("Voucher not found for the given voucher ID"),
    CANCELLED_BOOKING_VOUCHER_RETRIEVAL_FAILED("Booking is Cancelled. Voucher cannot be retrieved"),
    BOOKING_QUESTION_NOT_FOUND("Booking questions not found for the given parameters.(Please check question id,Product Id and Product Option Id)"),
    INVALID_ISO_COUNTRY_CODE("Invalid ISO country code"),
    INVALID_PHONE_NUMBER("Invalid phone number"),
    PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE("Phone number is invalid for the given country ISO code"),
    MAX_HEAD_COUNT_EXCEED("Maximum head count exceed"),
    ADULT_OR_SENIOR_REQUIRED("Adult or Senior required"),
    TRAVELER_DETAILS_DO_NOT_MATCH_TRAVELER_COUNT("Number of traveler details do not match with traveler count"),
    BOOKING_PAYMENT_FAILED("Booking payment failed. Please contact support for more details"),
    BOOKING_FAILED("Booking failed. Please contact support for more details"),
    INSUFFICIENT_FUNDS("Card doesn't have sufficient fund. Please try with another card"),
    INVALID_PAYMENT_DETAILS("Invalid payment details provided. Please re-check and try again"),
    PRODUCT_OPTION_ID_NOT_MATCH("Given product option id not match"),
    EMAIL_ADDRESS_REQUIRED_FOR_LEAD_TRAVELER("Email address is mandatory for lead traveler"),
    INVALID_EMAIL("Invalid Email address"),
    BOOKING_QUESTIONS_NOT_AVAILABLE("This product does not accept any booking answers."),
    INVALID_AGE_BAND_LABEL("Invalid age band label for given age band id"),
    INVALID_AGE_BAND_ID("Invalid age band id"),
    INVALID_VALIDATE_SCOPE("Invalid validate scope"),
    INVALID_DATE_FORMAT("Invalid date of birth(yyyy-mm-dd)"),
    LEAD_TRAVELER_SHOULD_BE_ADULT_OR_SENIOR("Lead traveler should be adult or senior"),

    EMPTY_COUNTRY_ISO_CODE_FOR_LEAD_TRAVELER("Country ISO code can not be empty for lead traveler"),
    COUNTRY_ISO_CODE_IS_REQUIRED_FOR_LEAD_TRAVELER("Country ISO code is required for lead traveler"),
    INVALID_ISO_COUNTRY_CODE_FOR_LEAD_TRAVELER("Invalid ISO country code for lead traveler"),
    EMPTY_COUNTRY_ISO_CODE("Country ISO code can not be empty for traveler"),
    COUNTRY_ISO_CODE_IS_REQUIRED("Country ISO code is required"),
    INVALID_ISO_COUNTRY_CODE_FOR_TRAVELER("Invalid ISO country code"),
    EMPTY_COUNTRY_ISO_CODE_FOR_TRAVELER("Country ISO code can not be empty for traveler with phone number"),
    COUNTRY_ISO_CODE_IS_REQUIRED_FOR_TRAVELER("Country ISO code is required for traveler with phone number"),

    EMPTY_PHONE_NUMBER_FOR_LEAD_TRAVELER("Phone number can not be empty for lead traveler"),
    PHONE_NUMBER_IS_REQUIRED_FOR_LEAD_TRAVELER("Phone number is required for lead traveler"),
    INVALID_PHONE_NUMBER_FOR_LEAD_TRAVELER("Invalid phone number for lead traveler"),
    EMPTY_PHONE_NUMBER("Phone number can not be empty for traveler"),
    PHONE_NUMBER_IS_REQUIRED("Phone number is required for traveler"),
    INVALID_PHONE_NUMBER_FOR_TRAVELER("Invalid phone number for traveler"),

    INVALID_EMAIL_FOR_LEAD_TRAVELER("Invalid Email address for lead traveler"),
    INVALID_EMAIL_FOR_TRAVELER("Invalid Email address for traveler"),
    EMAIL_ADDRESS_CAN_NOT_BE_EMPTY_FOR_LEAD_TRAVELER("Email address can not be empty for lead traveler"),

    LEAD_TRAVELER_PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE("Lead traveler phone number is invalid for the given country ISO code"),
    TRAVELER_PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE("Traveler phone number is invalid for the given country ISO code"),

    REQUEST_INVALID_AGE_BAND_ID("Requested age band id is invalid"),
    TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT("Provided traveler age bands are mismatch with cart quantities"),
    FIND_BOOKINGS_BOOKING_NOT_FOUND("Booking not found for the given params (Partner id/ Booking reference / Booker email)"),
    EMAIL_ADDRESS_IS_REQUIRED("Email address is required"),
    EMAIL_ADDRESS_CAN_NOT_BE_EMPTY("Email address can not be empty"),
    BOOKING_REFERENCE_IS_REQUIRED("Booking reference is required"),
    BOOKING_REFERENCE_CAN_NOT_BE_EMPTY("Booking refernce can not be empty"),

    UNSUPPORTED_AGE_BAND("Age Band is not supported"),
    NO_PRICE_FOUND("Price is not found"),

    PICKUP_LOCATION_ID_REQUIRED("Pickup location Id required"),
    PICKUP_LOCATION_NAME_REQUIRED("Pickup location name required"),

    LOYALTY_PROGRAMME_CONFIG_NOT_FOUND("Loyalty programme configuration not found for the given partner id and program id"),
    PRICE_NOT_FOUND_FOR_PROVIDED_QUANTITIES("Price not found for provided quantities"),
    LOYALTY_ACCOUNT_DETAILS_NOT_FOUND("Loyalty account id and loyalty program id not found"),

    BOOKING_ALREADY_CANCELED("Already the booking is successfully cancelled"),
    CANCEL_BOOKING_ALREADY_TIMEOUT("Previous booking cancel attempt was timeout. Please contact support for more details"),
    CANCEL_BOOKING_ALREADY_SUCCEEDED_REFUND_FAILED("Already the booking is successfully cancelled, however the refund was failed. Please contact support for more details"),
    CANCEL_BOOKING_FAILED("Cancel booking failed. Please contact support for more details"),
    CANCEL_BOOKING_REFUND_FAILED("Booking successfully canceled, however the refund was failed. Please contact support for more details"),
    VENDOR_CONFIG_NOT_FOUND("Vendor configuration details not foeund for the given vendor name"),

    NO_CANCEL_BOOKING_TRANSACTION_FOUND("No cancel booking transaction found"),
    INVALID_EVENT_NAME("Invalid event name. Valid names are BOOKING_CONFIRMATION/BOOKING_CANCEL"),
    INVALID_CANCELLATION_REASON("Invalid cancellation reason"),
    CANCELLATION_RULES_NOT_FOUND("Cannot cancel the booking, since no cancellation rules were found for the booking"),
    INVALID_BOOKING_STATUS("Provided new booking status is invalid"),
    BOOKING_NOT_PENDING("Booking is not in pending status"),
    VOUCHER_RETRIEVAL_FAILED("Voucher retrieval has been failed"),

    PENDING_BOOKING_VOUCHER_RESEND_FAILED("Booking is in pending status. Voucher will be sent to your email, once booking is confirmed"),
    CANCELLED_BOOKING_VOUCHER_RESEND_FAILED("Booking has been cancelled. Cant send voucher for the cancelled bookings"),
    REJECTED_BOOKING_VOUCHER_RESEND_FAILED("Booking has been rejected. Cant send voucher for the rejected bookings"),
    PENDING_BOOKING_CANCELLATION_FAILED("Pending booking can not be cancelled."),
    REJECTED_BOOKING_CANCELLATION_FAILED("Rejected booking can not be cancelled."),
    INVALID_VENDOR("Invalid vendor."),
    VENDOR_BOOKING_REFERENCE_IS_REQUIRED("Vendor booking reference is required"),
    INVALID_STATUS("Invalid status."),

    CARD_EXPIRED("Card expired. Please try with another card"),

    PAYMENT_GATEWAY_TIMEOUT("We were unable to process your booking, please try again."),
    PLACE_PASS_UNKOWN_ERROR("We are unable to confirm availability for this product. Please try another tour or activity."),
    CARD_ISSUES("We are unable to process payment with this credit card. Please try a different card."),
    UNKOWN_VENDOR_ERROR("We are unable to confirm availability for this product. Please try another tour or activity."),

    INTERNAL_SERVER_ERROR("We are unable to process your request at this time. Please try again later."),
    CARD_CVV_FAILURE ("We are unable to process payment with this credit card. Please confirm your credit card and CVV numbers are valid or try a different card."),
    CARD_AVS_FAILURE("We are unable to process payment with this credit card. Please ensure all information, including address, is valid or try a different card."),
    CARD_DECLINED ("We are unable to process payment with this credit card. Please try a different card"),
    CARD_ERROR("We are unable to process payment with this credit card. Please try a different card"),
    UNKNOWN_PAYMENT_GATEWAY_ERROR("We are unable to process payment with this credit card. Please ensure all information is valid or try a different card."),

    PRODUCT_NOT_AVAILABLE("We are unable to confirm availability for this product. Please try another tour or activity."),
    BOOKING_VENDOR_TIMEOUT("We are unable to confirm availability for this product. Please try another tour or activity."),
    BOOKING_REJECTED("We are unable to confirm availability for this product. Please try another tour or activity."),
    
    INVALID_REFUND_AMOUNT("Invalid refund amount"),
    INVALID_MANUAL_REFUND_TYPE("Invalid manual refund type."),
    BOOKING_AMOUNT_ALREADY_REFUND("Booking amount already refund"),
    BOOKING_TIME_EXCEEDED_REFUND_NOT_SUPPORTED("Tour already started, cancellation rule not applicable"),
    
    EMAIL_TEMPLATE_NOT_FOUND("Email template not found"),
    EMAIL_SEND_FAILED("Email send failed"),
    
    INVALID_STRIPE_CONNECTION_MODE("Invalid Stripe conncetion mode"),
    INVALID_SENDGRID_CONNECTION_MODE("Invalid Sendgrid conncetion mode"),
    
    INVALID_ADD_FEE_REQUEST("Cart and PricingDTO cannot both be null"),
    INVALID_DISCOUNT("This discount was not found"),
    NULL_DISCOUNT("The discount cannot be null"),
    MULTIPLE_DISCOUNTS_NOT_SUPPORTED("Discounts cannot be combined"),
    INVALID_DISCOUNT_DATE_RANGE("Discount code is expired"),
    INVALID_DISCOUNT_PRICE("Booking total does not fall between this discount's allowed maximum and minimum total"),
    
    BOOKING_DETAILS_INCOMPLETE("Booking details incomplete"),
    BOOKING_PARAMS_INVALID("Booking parameters invalid"),
    UNKNOWN_VENDOR_ERROR("Unknown vendor error occurred");

    private final String explanation;

    private PlacePassExceptionCodes(String reasonPhrase) {
        this.explanation = reasonPhrase;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getDescription() {
        return explanation;
    }
}
