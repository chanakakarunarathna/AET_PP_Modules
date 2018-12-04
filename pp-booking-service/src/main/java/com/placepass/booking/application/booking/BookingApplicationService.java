package com.placepass.booking.application.booking;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.placepass.booking.application.booking.admin.dto.SearchBookingsRS;
import com.placepass.booking.application.booking.dto.CancelBookingRQ;
import com.placepass.booking.application.booking.dto.CancelBookingRS;
import com.placepass.booking.application.booking.dto.CreateBookingRQ;
import com.placepass.booking.application.booking.dto.CreateBookingRS;
import com.placepass.booking.application.booking.dto.FindBookingsRS;
import com.placepass.booking.application.booking.dto.GetBookingRS;
import com.placepass.booking.application.booking.dto.GetBookingsHistoryRS;
import com.placepass.booking.application.booking.dto.ManualCancelBookingRQ;
import com.placepass.booking.application.booking.dto.ManualCancelBookingRS;
import com.placepass.booking.application.booking.dto.PendingBookingsRS;
import com.placepass.booking.application.booking.dto.ProcessBookingStatusRS;
import com.placepass.booking.application.booking.dto.ResendEmailType;
import com.placepass.booking.application.booking.dto.VendorDTO;
import com.placepass.booking.application.booking.dto.VendorListDTO;
import com.placepass.booking.application.booking.dto.VoucherDTO;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentResponse;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalResponse;
import com.placepass.booking.application.booking.paymentcondto.PaymentProcessStatus;
import com.placepass.booking.application.booking.paymentcondto.PaymentTransaction;
import com.placepass.booking.application.booking.paymentcondto.ReversalDetailsDTO;
import com.placepass.booking.application.cart.CartApplicationService;
import com.placepass.booking.application.cart.CartValidator;
import com.placepass.booking.application.cart.dto.ValidateCartRQ;
import com.placepass.booking.application.cart.dto.ValidateCartRS;
import com.placepass.booking.application.common.BookingServiceUtil;
import com.placepass.booking.application.common.VoucherPropertyService;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.BookingEvent;
import com.placepass.booking.domain.booking.BookingEventService;
import com.placepass.booking.domain.booking.BookingEventTransformer;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.BookingRepository;
import com.placepass.booking.domain.booking.BookingService;
import com.placepass.booking.domain.booking.BookingState;
import com.placepass.booking.domain.booking.Cart;
import com.placepass.booking.domain.booking.CartService;
import com.placepass.booking.domain.booking.CartState;
import com.placepass.booking.domain.booking.Discount;
import com.placepass.booking.domain.booking.EventName;
import com.placepass.booking.domain.booking.LoyaltyAccount;
import com.placepass.booking.domain.booking.ManualInterventionDetail;
import com.placepass.booking.domain.booking.Payment;
import com.placepass.booking.domain.booking.PaymentStatus;
import com.placepass.booking.domain.booking.PaymentType;
import com.placepass.booking.domain.booking.RefundMode;
import com.placepass.booking.domain.booking.Voucher;
import com.placepass.booking.domain.booking.cancel.BookingCancellationType;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.CancelBookingRepository;
import com.placepass.booking.domain.booking.cancel.CancelBookingState;
import com.placepass.booking.domain.booking.cancel.CancelBookingTransaction;
import com.placepass.booking.domain.booking.cancel.ManualRefundType;
import com.placepass.booking.domain.booking.cancel.Refund;
import com.placepass.booking.domain.booking.cancel.RefundSummary;
import com.placepass.booking.domain.booking.strategy.DiscountStrategy;
import com.placepass.booking.domain.common.BookingReferenceGenerator;
import com.placepass.booking.domain.common.LogEventProcessingStage;
import com.placepass.booking.domain.config.LoyaltyProgramConfig;
import com.placepass.booking.domain.config.LoyaltyProgramConfigService;
import com.placepass.booking.domain.config.PartnerConfig;
import com.placepass.booking.domain.config.PartnerConfigService;
import com.placepass.booking.domain.event.EventService;
import com.placepass.booking.domain.platform.BookingStatus;
import com.placepass.booking.domain.platform.NewBookingStatus;
import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.booking.domain.platform.Status;
import com.placepass.booking.domain.product.ProductDetails;
import com.placepass.booking.infrastructure.CryptoService;
import com.placepass.booking.infrastructure.ProductServiceImpl;
import com.placepass.booking.infrastructure.VendorBookingServiceImpl;
import com.placepass.booking.infrastructure.discount.DiscountTransformer;
import com.placepass.connector.common.booking.BookingStatusRS;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.bookingstatus.ConnectorBookingStatus;
import com.placepass.utils.logging.PlatformLoggingKey;
import com.placepass.utils.validate.ValidateScope;
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.utils.voucher.VoucherType;

@Service
public class BookingApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(BookingApplicationService.class);

    @Autowired
    private CartApplicationService cartApplicationService;

    @Autowired
    private CartService cartService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentProcessor paymentProcessor;

    @Autowired
    private BookingProcessor bookingProcessor;

    @Autowired
    private PartnerConfigService partnerConfigService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private BookingReferenceGenerator bookingReferenceGenerator;

    @Autowired
    private LoyaltyProgramConfigService loyaltyProgramConfigService;

    @Autowired
    private EventService eventService;

    @Autowired
    private FullRefundSpecification fullRefundSpec;

    @Autowired
    private PartialRefundSpecification partialRefundSpec;

    @Autowired
    private BookingCancelSpecification bookingCancelSpec;

    @Autowired
    private CancelBookingStatusTranslator cancelBookingStatusTranslator;

    @Autowired
    private CancelBookingRepository cancelBookingRepository;

    @Autowired
    private VendorBookingServiceImpl vendorBookingService;

    @Autowired
    private VoucherPropertyService voucherPropertyService;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private RefundStrategy refundStrategy;

    @Autowired
    private CreateBookingStrategy createBookingStrategy;

    @Autowired
    private PaymentProcessStatusStrategy ppStatusStrategy;

    @Autowired
    private BookingEventService bookingEventService; 

    @Autowired
    private SpanAccessor spanAccessor;

	@Autowired
    private BookingManualCancelSpecification bookingManualCancelSpec;

    @Autowired
    private FullManualRefundSpecification fullManualRefundSpec;

    @Autowired
    private PartialManualRefundSpecification partialManualRefundSpec;

    @Autowired
    ManualCancelBookingStatusTranslator manualCancelBookingStatusTranslator;
    
    @Autowired
    private DiscountStrategy discountStrategy; 

    public CreateBookingRS processBooking(CreateBookingRQ createBookingRequest) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), createBookingRequest.getPartnerId());
        logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), createBookingRequest.getCustomerId());
        logData.put(PlatformLoggingKey.CART_ID.name(), createBookingRequest.getCartId());
        logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(), LogEventProcessingStage.BOOKING_INITIATED);
        logger.info("Booking initiated. {}", logData);

        Span span = this.spanAccessor.getCurrentSpan();
        int bookingEventIndex = 0;
        List<BookingEvent> bookingEvents = new ArrayList<BookingEvent>();
        
        // Retrieve Partner Configuration (this has the payment routing key)
        PartnerConfig partnerConfig = partnerConfigService.getPartnerConfig(createBookingRequest.getPartnerId());
        
    	Map<String, String> partnerConfigRetrievedExtAttr = BookingEventTransformer.constructExtendedAttributeForPartnerConfigRetrieved(createBookingRequest.getPartnerId());
        BookingEvent partnerConfigRetrievedEvent = new BookingEvent(bookingEventIndex, EventName.PARTNER_CONFIG_RETRIEVED, partnerConfigRetrievedExtAttr, span.getSpanId(), span.getTraceId());
        bookingEvents.add(partnerConfigRetrievedEvent);
        bookingEventIndex++;
        
        // sequence of flow described.
        // FIXME to gain performance - MongoDB RAM (cache) enabled and use indexed queries where needed.

        Cart cart = cartService.getCart(createBookingRequest.getCartId(), createBookingRequest.getPartnerId());
        // only an un-closed cart can be checkout for booking
        if (CartState.CLOSE == cart.getCartState()) {
            throw new NotFoundException(PlacePassExceptionCodes.CART_CLOSED.toString(),
                    PlacePassExceptionCodes.CART_CLOSED.getDescription());
        }

    	Map<String, String> cartRetrievedExtAttr = BookingEventTransformer.constructExtendedAttributeForCartRetrieved(createBookingRequest.getCartId(), createBookingRequest.getPartnerId());
        BookingEvent cartRetrievedEvent = new BookingEvent(bookingEventIndex, EventName.CART_RETRIEVED, cartRetrievedExtAttr, span.getSpanId(), span.getTraceId());
        bookingEvents.add(cartRetrievedEvent);
        bookingEventIndex++;
        
        // 1. perform cart item validate with vendor (this will have available + validate cart if supported).
        // It's an internal call to booking service.
        logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                LogEventProcessingStage.BOOKING_AVAILABILITY_AND_VALIDATION);
        logger.info("Booking availability check and cart validation. {}", logData);

        ValidateCartRQ validateCartRQ = new ValidateCartRQ();
        validateCartRQ.setValidateScope(ValidateScope.ALL.name());
        ValidateCartRS validateCartRS = cartApplicationService.validateCart(createBookingRequest.getPartnerId(),
                createBookingRequest.getCartId(), validateCartRQ, cart);

        if ((validateCartRS.getValidateMessages().get(0) != null
                && !"success".equals(validateCartRS.getValidateMessages().get(0)))) {
            throw new BadRequestException(PlacePassExceptionCodes.CART_VALIDATION_FAILED.toString(),
                    validateCartRS.getValidateMessages().toString());
        }
        
    	Map<String, String> cartValidatedExtAttr = BookingEventTransformer.constructExtendedAttributeForCartValidated(createBookingRequest.getCartId(), createBookingRequest.getPartnerId());
        BookingEvent cartValidatedEvent = new BookingEvent(bookingEventIndex, EventName.CART_VALIDATED, cartValidatedExtAttr, span.getSpanId(), span.getTraceId());
        bookingEvents.add(cartValidatedEvent);
        bookingEventIndex++;
               
        // Retrieve product details and save with booking object, since it has location, image, etc to send the email.
        List<ProductDetails> productDetailsList = new ArrayList<>();
        for (BookingOption bookingOption : cart.getBookingOptions()) {
        	
        	Map<String, String> productDetailReqExtAttr = BookingEventTransformer.constructExtendedAttributeForProductDetailRequestSent(createBookingRequest.getPartnerId(), bookingOption.getProductId());
            BookingEvent productDetailReqEvent = new BookingEvent(bookingEventIndex, EventName.PRODUCT_DETAIL_RETRIEVAL_REQUEST_SENT, productDetailReqExtAttr, span.getSpanId(), span.getTraceId());
            bookingEvents.add(productDetailReqEvent);
            bookingEventIndex++;
            
            ProductDetails productDetails = productService.getProductDetails(createBookingRequest.getPartnerId(),
                    bookingOption.getProductId());
            productDetailsList.add(productDetails);
            
            Map<String, String> productDetailResExtAttr = BookingEventTransformer.constructExtendedAttributeForProductDetailResponseReceived(createBookingRequest.getPartnerId(), bookingOption.getProductId());
            BookingEvent productDetailResEvent = new BookingEvent(bookingEventIndex, EventName.PRODUCT_DETAIL_RETRIEVAL_RESPONSE_RECEIVED, productDetailResExtAttr, span.getSpanId(), span.getTraceId());
            bookingEvents.add(productDetailResEvent);
            bookingEventIndex++;

        }
        
        // 2. Generate a bookingReference and instantiate Booking domain object with OPEN State.
        String bookingReference = bookingReferenceGenerator.generate(createBookingRequest.getCustomerId(),
                partnerConfig.getPartnerPrefix());
        
        BookingEvent bookingInitiatedEvent = new BookingEvent(bookingEventIndex, EventName.BOOKING_REFERENCE_GENERATED, null, span.getSpanId(), span.getTraceId());
        bookingEvents.add(bookingInitiatedEvent);
        bookingEventIndex++;

        Booking booking = BookingRequestTransformer.toBooking(createBookingRequest, cart, bookingReference,
                productDetailsList);
        bookingEventService.addEventsToBooking(booking, bookingEvents);
        
        if (partnerConfig.isDiscountServiceEnabled()) {
            List<Discount> discounts = DiscountTransformer.transformDiscount(createBookingRequest.getDiscounts());
            if (discounts != null && !discounts.isEmpty()) {
                booking = discountStrategy
                        .redeemDiscount(createBookingRequest.getPartnerId(), booking, cart, discounts);

                Map<String, String> discountProcessedExtAttr = BookingEventTransformer
                        .constructExtendedAttributeForDiscountProcessed(booking.getDiscounts());
                bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex,
                        EventName.DISCOUNT_PROCESSED, discountProcessedExtAttr);
            }
        }
        
        // Getting loyalty program using partner id and program id
        LoyaltyProgramConfig loyaltyProgramConfig = retrieveLoyaltyProgramConfig(createBookingRequest);

        if (loyaltyProgramConfig != null) {
            // processing loyalty and updating it
            LoyaltyAccount loyaltyAccount = BookingRequestTransformer.toLoyaltyAccount(cart,
                    createBookingRequest.getLoyaltyAccount(), loyaltyProgramConfig);
            booking.setLoyaltyAccount(loyaltyAccount);

            // encrypt loyaltyAccountId, if available
            booking = encryptLoyaltyAcctId(booking);

            Map<String, String> loyaltyProcessedExtAttr = BookingEventTransformer
                    .constructExtendedAttributeForLoyaltyProcessed(loyaltyAccount.getLoyaltyAccountId(),
                            loyaltyAccount.getLoyaltyProgrameId(), loyaltyAccount.getLoyaltyPoints());
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex,
                    EventName.LOYALTY_PROCESSED, loyaltyProcessedExtAttr);
        }

        // creating the sale payment object to be saved with booking object
        Payment payment = BookingRequestTransformer.toSalePayment(booking, createBookingRequest, partnerConfig);
        List<Payment> payments = new ArrayList<>(1);
        payments.add(payment);
        booking.setPayments(payments);
        bookingRepository.save(booking);

        // useful properties are logged.
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), booking.getId());
        logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), booking.getBookingReference());
        logData.put(PlatformLoggingKey.BOOKING_STATE.name(), BookingState.OPEN);
        logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(), LogEventProcessingStage.BOOKING_SAVED);
        logger.info("Booking saved. {}", logData);

        // Populating payment connector request objects
        // PaymentTransaction paymentTransaction = BookingPaymentConTransformer.toPaymentTransaction(booking, payment);
        ConnectorPaymentRequest paymentRequest = BookingPaymentConTransformer.toConnectorPaymentRequest(payment,
                booking);

        // 3. make a sale with payment connector.
        logData.put(PlatformLoggingKey.BOOKING_STATE.name(), BookingState.PAYMENT);
        logData.put(PlatformLoggingKey.PAYMENT_ID.name(), paymentRequest.getPaymentTxId());
        logData.put(PlatformLoggingKey.PAYMENT_AMOUNT.name(), paymentRequest.getPaymentAmount());
        logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                LogEventProcessingStage.PAYMENT_CONNECTOR_SALE_CALL_STARTED);
        logger.info("Booking payment initiated. {}", logData);
        logData.remove(PlatformLoggingKey.PAYMENT_AMOUNT.name());
        
        Map<String, String> paymentReqExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentRequestSent(paymentRequest.getPaymentAmount(), paymentRequest.getPaymentTxId(), paymentRequest.getMerchantId(), paymentRequest.getLocationId(), paymentRequest.getGatewayName(), paymentRequest.getExchangeName(), paymentRequest.getPaymentToken());
        bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REQUEST_SENT, paymentReqExtAttr);
        
        ConnectorPaymentResponse paymentResponse = paymentProcessor.makePayment(paymentRequest);
               
        if (PaymentProcessStatus.PAYMENT_ISSUER_TIMEOUT == paymentResponse.getPaymentStatus()
                || PaymentProcessStatus.PAYMENT_PROCESSING_ERROR == paymentResponse.getPaymentStatus()
                || PaymentProcessStatus.PAYMENT_GATEWAY_CONNECTION_ERROR == paymentResponse.getPaymentStatus()) {
            String reason = "Payment timed out; ";
            if (paymentResponse.getExternalStatuses() != null){
    			reason = reason + "External status: " + paymentResponse.getExternalStatuses().toString();
    		}
        	ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, reason);
            Map<String, String> paymentResExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentResponseReceived(paymentResponse.getPaymentStatus(), paymentResponse.getExternalStatuses(), paymentResponse.getAmountTendered(), paymentResponse.getExtPaymentTxRefId());        
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_RESPONSE_RECEIVED, paymentResExtAttr, manualInterventionDetail);
        }else{
        	 Map<String, String> paymentResExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentResponseReceived(paymentResponse.getPaymentStatus(), paymentResponse.getExternalStatuses(), paymentResponse.getAmountTendered(), paymentResponse.getExtPaymentTxRefId());        
             bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_RESPONSE_RECEIVED, paymentResExtAttr);
        }
        
        // 3.1 Once payment response retrieved >> BookingState.PAYMENT,
        // LogEventProcessingStage.PAYMENT_CONNECTOR_SALE_CALL_COMPLETED,
        // UPDATE payment status (success/failure) in db.
        logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                LogEventProcessingStage.PAYMENT_CONNECTOR_SALE_CALL_COMPLETED);
        logger.info("Booking payment completed. {}", logData);

        booking.setBookingState(BookingState.PAYMENT);
        this.updatePaymentResponse(payment, paymentResponse);
        booking.setUpdatedTime(Instant.now());
        bookingRepository.save(booking);

        if (PaymentStatus.PAYMENT_SUCCESS == payment.getPaymentStatus()) {

            // payment response status and identifies logged.
            logData.put(PlatformLoggingKey.PAYMENT_STATUS.name(), paymentResponse.getPaymentStatus());
            logData.put(PlatformLoggingKey.PAYMENT_EXT_REFERENCE.name(), paymentResponse.getExtPaymentTxRefId());
            logData.put(PlatformLoggingKey.PAYMENT_EXT_STATUSES.name(), paymentResponse.getExternalStatuses());
            logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(), LogEventProcessingStage.PAYMENT_SUCCESS);
            logger.info("Booking payment success. {}", logData);
            logData.remove(PlatformLoggingKey.PAYMENT_STATUS.name());
            logData.remove(PlatformLoggingKey.PAYMENT_EXT_REFERENCE.name());
            logData.remove(PlatformLoggingKey.PAYMENT_EXT_STATUSES.name());

            // 4. If payment success (3.1). BookingState.BOOKING_TENTATIVE,
            // LogEventProcessingStage.VENDOR_CONNECTOR_BOOKING_CALL_STARTED (logged)
            logData.put(PlatformLoggingKey.BOOKING_STATE.name(), BookingState.BOOKING_TENTATIVE);
            logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                    LogEventProcessingStage.VENDOR_CONNECTOR_BOOKING_CALL_STARTED);
            logger.info("Vendor booking call initiated. {}", logData);
            MakeBookingRQ bookingConRQ = BookingVendorConTransformer.toMakeBookingRQ(booking);

            // 5. Confirm booking with vendor.
            // 5.1 connector internally could make an availability check with
            // vendor before booking if it's not a dynamic business rule specified by Booking service (flag in the
            // request to vendor). (This can be turned off for R1)
            // ---------------- sync flow ----------------------
            // 6. Waits for Confirm booking response.
            // Get vendor from 0th bookingOptions vendor, since at the moment we only support single booking option
            // FIXME - Later we need to group the bookingOptions by vendor and route it to appropriate connector
            Vendor vendor = Vendor.getVendor(booking.getBookingOptions().get(0).getVendor());
            
            Map<String, String> bookingReqExtAttr = BookingEventTransformer.constructExtendedAttributeForBookingRequestSent(bookingConRQ.getBookingId(), vendor.name(), bookingConRQ.getTotal().getFinalTotal(), bookingConRQ.getBookingOptions().get(0).getProductId());
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_REQUEST_SENT, bookingReqExtAttr);
            
            MakeBookingRS bookingConRS = bookingProcessor.makeBooking(bookingConRQ, vendor);

            ManualInterventionDetail manualIntervention = null;
            if (bookingConRS.getResultType().getExtendedAttributes() != null) {
                String externalStatusCode = bookingConRS.getResultType().getExtendedAttributes().get("code");

                if (externalStatusCode != null
                        && (externalStatusCode
                                .equals(String.valueOf(VendorErrorCode.VENDOR_CONNECTOR_TIMEOUT_ERROR.getId()))
                                || externalStatusCode.equals(String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR
                                        .getId())) || externalStatusCode.equals(String
                                .valueOf(VendorErrorCode.VENDOR_READ_TIMEOUT_ERROR.getId())))) {
                    String reason = "Booking timed out; External status: "+ bookingConRS.getResultType().getExtendedAttributes().toString();
                    manualIntervention = new ManualInterventionDetail(true, reason);
                }
            }
            
            Map<String, String> bookingResExtAttr = BookingEventTransformer.constructExtendedAttributeForBookingResponseReceived(bookingConRS.getBookingId(), bookingConRS.getReferenceNumber(), bookingConRS.getTotalAmount());
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_RESPONSE_RECEIVED, bookingResExtAttr, manualIntervention);
            
            logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                    LogEventProcessingStage.VENDOR_CONNECTOR_BOOKING_CALL_COMPLETED);
            logger.info("Vendor booking call completed. {}", logData);

            // then UPDATE Booking domain instance. BookingState.BOOKING_CONFIRMATION,
            // UPDATE booking status (success/failure) and other booking related properties in db.

            /*
             * In here we will first check the ResultType code. For ex: CONFIRMED(100), PENDING(101)
             */
            if (bookingConRS.getResultType() != null) {

                if (bookingConRS.getResultType().getCode() == VendorErrorCode.CONFIRMED.getId()) {
                    booking.setBookingState(BookingState.BOOKING_CONFIRMATION);

                } else if (bookingConRS.getResultType().getCode() == VendorErrorCode.PENDING.getId()) {
                    booking.setBookingState(BookingState.BOOKING_PENDING);
                }
            }
            this.updateBookingResponse(booking, bookingConRS);
            booking.setUpdatedTime(Instant.now());
            bookingRepository.save(booking);
            // check if the booking is success or pending
            if (PlatformStatus.SUCCESS == booking.getBookingStatus().getStatus()
                    || PlatformStatus.PENDING == booking.getBookingStatus().getStatus()) {

                // 6.1 If successful LogEventProcessingStage.BOOKING_CONFIRMED (logged). Closing the cart
                // Publish booking confirmation event

                logData.put(PlatformLoggingKey.BOOKING_RESPONSE_CODE.name(), bookingConRS.getResultType().getCode());
                logData.put(PlatformLoggingKey.BOOKING_RESPONSE_MESSAGE.name(),
                        bookingConRS.getResultType().getMessage());
                logData.put(PlatformLoggingKey.BOOKING_EXT_REFERENCE.name(), bookingConRS.getReferenceNumber());
                logData.put(PlatformLoggingKey.BOOKING_STATE.name(), booking.getBookingState());
                logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                        LogEventProcessingStage.BOOKING_CONFIRMED);
                logger.info("Booking success. {}", logData);

                // closing the cart
                cart.setCartState(CartState.CLOSE);
                cartService.saveCart(cart);

                Map<String, String> cartClosedExtAttr = BookingEventTransformer.constructExtendedAttributeForCartClosed(cart.getCartId());
                bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.CART_CLOSED, cartClosedExtAttr);
                
            } else {

                // 6.2 If failure - BookingState.BOOKING_CONFIRMATION,
                // LogEventProcessingStage.BOOKING_FAILED (logged)
                logData.put(PlatformLoggingKey.BOOKING_STATE.name(), BookingState.BOOKING_CONFIRMATION);
                logData.put(PlatformLoggingKey.BOOKING_RESPONSE_CODE.name(), bookingConRS.getResultType().getCode());
                logData.put(PlatformLoggingKey.BOOKING_RESPONSE_MESSAGE.name(),
                        bookingConRS.getResultType().getMessage());
                logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                        LogEventProcessingStage.BOOKING_FAILED);
                logger.error("Booking failed. {}", logData);
                logData.remove(PlatformLoggingKey.BOOKING_RESPONSE_CODE.name());
                logData.remove(PlatformLoggingKey.BOOKING_RESPONSE_MESSAGE.name());

                // BookingState.PAYMENT_REVERSAL,
                // LogEventProcessingStage.PAYMENT_CONNECTOR_SALE_REVERSAL_CALL_STARTED (logged)
                logData.put(PlatformLoggingKey.BOOKING_STATE.name(), BookingState.PAYMENT_REVERSAL);
                logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                        LogEventProcessingStage.PAYMENT_CONNECTOR_SALE_REVERSAL_CALL_STARTED);
                logger.info("Payment reversal initiated. {}", logData);

                String reversalReason = "Booking failed. Issuing a reversal.";
                PaymentTransaction originalPaymentTx = BookingPaymentConTransformer.toPaymentTransaction(booking,
                        payment);
                                
                Map<String, String> paymentRevReqExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentReversalRequestSent(reversalReason, originalPaymentTx.getPaymentTxId(), paymentRequest.getGatewayName(), paymentRequest.getPaymentToken(), RefundMode.FULL.name(), String.valueOf(originalPaymentTx.getPaymentAmount()));
                bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_REQUEST_SENT, paymentRevReqExtAttr);
                
                ConnectorPaymentReversalResponse reversalResponse = paymentProcessor.reversePayment(originalPaymentTx,
                        paymentRequest, reversalReason);

                Map<String, String> paymentRevResExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentReversalResponseReceived(reversalResponse.getPaymentStatus(), reversalResponse.getExternalStatuses(), reversalResponse.getExtReversalTxId(), reversalResponse.getReversalReason());
                if (PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS == reversalResponse.getPaymentStatus()){
                    bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED, paymentRevResExtAttr);
                }else{
                	String reason = "Payment reversal failed, Reversal payment status:" + reversalResponse.getPaymentStatus().name();
                	if (reversalResponse.getExternalStatuses() != null){
                		reason = reason + " , External status: " + reversalResponse.getExternalStatuses().toString();
                	}
                	ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, reason);
                    bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED, paymentRevResExtAttr, manualInterventionDetail);
                }

                logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                        LogEventProcessingStage.PAYMENT_CONNECTOR_SALE_REVERSAL_CALL_COMPLETED);
                logger.info("Payment reversal completed. {}", logData);

                // 6.2.1 & 6.2.2 - reversal success/failure record the status in db
                booking.setBookingState(BookingState.PAYMENT_REVERSAL);
                this.updatePaymentOnReversal(booking, reversalResponse, reversalReason);
                booking.setUpdatedTime(Instant.now());
                bookingRepository.save(booking);

                logData.put(PlatformLoggingKey.PAYMENT_REVERSAL_STATUS.name(), reversalResponse.getPaymentStatus());
                logData.put(PlatformLoggingKey.PAYMENT_REVERSAL_EXT_REFERENCE.name(),
                        reversalResponse.getExtReversalTxId());
                logData.put(PlatformLoggingKey.PAYMENT_REVERSAL_EXT_STATUSES.name(),
                        reversalResponse.getExternalStatuses());
                if (PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS == reversalResponse.getPaymentStatus()) {

                    // 6.2.1 If success >> BookingState.PAYMENT_REVERSAL,
                    // LogEventProcessingStage.REVERSAL_SUCCESS (logged);
                    logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                            LogEventProcessingStage.PAYMENT_REVERSAL_SUCCESS);
                    logger.info("Payment reversal success. {}", logData);
                } else {

                    // 6.2.2 If failed >> BookingState.PAYMENT_REVERSAL, LogEventProcessingStage.REVERSAL_FAILED
                    // (logged); Attempt to reverse failure, need to be handled in a separate
                    // way (maybe manual Refund with customer service or reversal attempted later on).
                    logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(),
                            LogEventProcessingStage.PAYMENT_REVERSAL_FAILED);
                    logger.error("Payment reversal failed. {}", logData);
                }
            }

        } else {

            // 3.2 If failed >> BookingState.PAYMENT, LogEventProcessingStage.PAYMENT_CONNECTOR_SALE_CALL_COMPLETED
            // pay-connector status logged >> UPDATE platform status in db.
            // Return with payment failure reasons.
            logData.put(PlatformLoggingKey.PAYMENT_STATUS.name(), paymentResponse.getPaymentStatus());
            logData.put(PlatformLoggingKey.PAYMENT_EXT_REFERENCE.name(), paymentResponse.getExtPaymentTxRefId());
            logData.put(PlatformLoggingKey.PAYMENT_EXT_STATUSES.name(), paymentResponse.getExternalStatuses());
            logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(), LogEventProcessingStage.PAYMENT_FAILED);
            logger.error("Booking process failed due to payment faiure. {}", logData);

            Status status = new Status();
            status.setStatus(ppStatusStrategy.getPlatformStatus(paymentResponse.getPaymentStatus()));

            booking.setBookingStatus(status);
            booking.setUpdatedTime(Instant.now());
            bookingRepository.save(booking);
        }

        // ---------------- async flow ----------------------

        // 6. returns (with BookingReference) without waiting for Confirm
        // booking response.

        // 7. async processing on Confirm booking response to carry out steps
        // described in 6.1 or 6.2 in the sync flow.

        // only successful booking will returns a response and failures will throw exception with proper codes
        booking.updateBookingClose();
        booking.updateIsInGoodStanding();
        BookingSummaryTransformer.updateBookingSummary(booking);
        bookingRepository.save(booking);

        if (booking.isBookingClosed()) {
            // Exception handling through BookingAsyncExceptionHandler. Test any async platform event invocation
            // failures due to thread spawn rejections based on thread pool settings. Platform event firing
            // shouldn't fail the request thread.

            if (booking.isPending()) {
                eventService.publishBookingPendingEvent(booking.getId(), booking.getPartnerId(), null);

            } else {
                eventService.publishBookingConfirmationEvent(booking.getId(), booking.getPartnerId(), null);
            }

        }
        createBookingStrategy.processPlatformStatus(booking.getBookingStatus(), payment, booking);
        return BookingRequestTransformer.toCreateBookingRS(booking);
    }

    private void updateBookingResponse(Booking booking, MakeBookingRS bookingConRS) {

        // Here overriding cancellation rules which is coming from Algolia search, by vendor cancellation rules. If
        // vendor not sending any cancellation rules, Algolia cancellation rules will save properly
        // **** When API going to support MULTIPLE BOOKING OPTIONS, cancellation rules development totally should
        // change
        if (bookingConRS.getCancellationRules() != null) {

            List<BookingOption> bookingOptions = new ArrayList<>();

            BookingOption bookingOption = booking.getBookingOptions().get(0);

            ProductDetails productDetails = bookingOption.getProductDetails();
            productDetails.setCancellationRules(
                    BookingConTransformer.toCancellationRules(bookingConRS.getCancellationRules()));
            // BookingConTransformer.toCancellationRules(bookingConRS.getCancellationRules())
            bookingOption.setProductDetails(productDetails);
            bookingOptions.add(bookingOption);

            booking.setBookingOptions(bookingOptions);
        }

        Status status = new Status();
        status.setConnectorStatus(bookingConRS.getResultType().getMessage());

        if (bookingConRS.getResultType().getCode() == VendorErrorCode.CONFIRMED.getId()
                || bookingConRS.getResultType().getCode() == VendorErrorCode.PENDING.getId()) {
            if (bookingConRS.getResultType().getCode() == VendorErrorCode.CONFIRMED.getId()) {
                status.setStatus(PlatformStatus.SUCCESS);
                if (bookingConRS.getVoucher() != null) {
                    Voucher voucher = BookingRequestTransformer.toVoucher(bookingConRS.getVoucher());
                    voucher.setId(UUID.randomUUID().toString());
                    booking.getBookingOptions().get(0).setVoucher(voucher);
                } else {
                    /*
                     * In here we set an dummy voucher object, if a timout scenario happens when retriving voucher. This
                     * is needed for the new voucher retrival endpoint.
                     */
                    Voucher voucher = new Voucher();
                    voucher.setId(UUID.randomUUID().toString());
                    booking.getBookingOptions().get(0).setVoucher(voucher);
                }
            } else {
                status.setStatus(PlatformStatus.PENDING);
            }
            booking.setVendorBookingRefId(bookingConRS.getReferenceNumber());

            if (bookingConRS.getBookingItems() != null) {
                booking.setBookingItems(BookingRequestTransformer.toBookingItems(bookingConRS.getBookingItems()));
            }
        } else if (bookingConRS.getResultType().getCode() == VendorErrorCode.REJECTED.getId()) {
            status.setStatus(PlatformStatus.BOOKING_REJECTED);
        } else if (bookingConRS.getResultType().getCode() == VendorErrorCode.VENDOR_CONNECTOR_TIMEOUT_ERROR.getId()
                || bookingConRS.getResultType().getCode() == VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR.getId()
                || bookingConRS.getResultType().getCode() == VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()) {
            status.setStatus(PlatformStatus.BOOKING_TIMEOUT);
        } else {
            status.setStatus(PlatformStatus.BOOKING_FAILED);
        }

        Map<String, String> vendorStatuses = new HashMap<>();
        vendorStatuses.put("BOOKING_EXTERNAL_RESULT",
                bookingConRS.getResultType().getCode() + ":" + bookingConRS.getResultType().getMessage());
        if (bookingConRS.getResultType().getExtendedAttributes() != null) {
            vendorStatuses.putAll(bookingConRS.getResultType().getExtendedAttributes());
        }
        status.setExternalStatus(vendorStatuses);

        booking.setBookingStatus(status);
    }

    private void updatePaymentOnReversal(Booking booking, ConnectorPaymentReversalResponse reversalResponse,
            String reversalReason) {
        ReversalDetailsDTO reversalDetailsDTO = new ReversalDetailsDTO();

        Payment payment = booking.getPayments().stream().filter(pay -> PaymentType.SALE.equals(pay.getPaymentType()))
                .findFirst().get();
        payment.setReversalTriggered(true);

        if (reversalResponse != null) {

            reversalDetailsDTO.setReversalSuccessful(
                    PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS.equals(reversalResponse.getPaymentStatus()));
            reversalDetailsDTO.setAttemptedTime(reversalResponse.getProcessedTime());
            reversalDetailsDTO.setExternalStatuses(reversalResponse.getExternalStatuses());
            reversalDetailsDTO.setExtReversalTxRefId(reversalResponse.getExtReversalTxId());
            reversalDetailsDTO.setReversalStatus(reversalResponse.getPaymentStatus());
            reversalDetailsDTO.setReversalReason(reversalReason);

        } else {
            reversalDetailsDTO.setReversalReason(reversalReason);
            reversalDetailsDTO.setReversalSuccessful(false);
            reversalDetailsDTO.setReversalStatus(PaymentProcessStatus.PAYMENT_ISSUER_TIMEOUT);
        }

        payment.setReversalDetails(BookingRequestTransformer.toReversalDetails(reversalDetailsDTO));
    }

    private void updatePaymentResponse(Payment payment, ConnectorPaymentResponse paymentResponse) {

        payment.setAmountTendered(paymentResponse.getAmountTendered());
        payment.setExternalStatuses(paymentResponse.getExternalStatuses());
        payment.setExtPaymentTxId(paymentResponse.getExtPaymentTxRefId());
        payment.setPaymentStatus(PaymentStatus.getPaymentStatus(paymentResponse.getPaymentStatus().name()));
        payment.setProcessedTime(paymentResponse.getProcessedTime());
        if (paymentResponse.getCardInfo() != null) {
            payment.setLast4CardNumber(paymentResponse.getCardInfo().getLast4CardNumber());
            payment.setCardType(paymentResponse.getCardInfo().getCardType());
        }
        if (paymentResponse.getReversalDetails() != null) {
            payment.setReversalDetails(
                    BookingRequestTransformer.toReversalDetails(paymentResponse.getReversalDetails()));
        }
        payment.setReversalTriggered(paymentResponse.isPaymentReversalTriggered());
    }

    public GetBookingRS getBooking(String bookingId, String customerId, String partnerId) {

        Booking booking = bookingService.getBooking(bookingId, customerId, partnerId);
        return BookingRequestTransformer.toGetBookingRS(booking, voucherPropertyService);
    }

    public GetBookingRS getBooking(String bookingId, String partnerId) {

        Booking booking = bookingService.getBooking(bookingId, partnerId);

        return BookingRequestTransformer.toGetBookingRS(booking, voucherPropertyService);
    }

    public com.placepass.booking.application.booking.admin.dto.GetBookingRS adminGetBooking(String bookingId, String partnerId) {

        Booking booking = bookingService.adminGetBooking(bookingId, partnerId);

        return BookingRequestTransformer.toAdminGetBookingRS(booking, voucherPropertyService);
    }

    public GetBookingsHistoryRS getBookingsHistory(String customerId, String partnerId, int hitsPerPage,
            int pageNumber) {

        Page<Booking> bookingsPage = bookingService.getBookingsHistory(customerId, partnerId, hitsPerPage, pageNumber);

        return BookingRequestTransformer.toGetBookingsHistoryRS(bookingsPage, voucherPropertyService);
    }

    public FindBookingsRS findBookingsByEmailBookingReference(String partnerId, String bookerEmail,
            String bookingReference) {

        // validating Email address
        if (bookerEmail == null) {
            throw new BadRequestException(PlacePassExceptionCodes.EMAIL_ADDRESS_IS_REQUIRED.toString(),
                    PlacePassExceptionCodes.EMAIL_ADDRESS_IS_REQUIRED.getDescription());
        }

        if ("".equals(bookerEmail)) {
            throw new BadRequestException(PlacePassExceptionCodes.EMAIL_ADDRESS_CAN_NOT_BE_EMPTY.toString(),
                    PlacePassExceptionCodes.EMAIL_ADDRESS_CAN_NOT_BE_EMPTY.getDescription());
        }

        if (!CartValidator.isValidEmailAddress(bookerEmail)) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_EMAIL.toString(),
                    PlacePassExceptionCodes.INVALID_EMAIL.getDescription());
        }

        // validating bookingReference
        if (bookingReference == null) {
            throw new BadRequestException(PlacePassExceptionCodes.BOOKING_REFERENCE_IS_REQUIRED.toString(),
                    PlacePassExceptionCodes.BOOKING_REFERENCE_IS_REQUIRED.getDescription());
        }

        if ("".equals(bookingReference)) {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_REFERENCE_CAN_NOT_BE_EMPTY.toString(),
                    PlacePassExceptionCodes.BOOKING_REFERENCE_CAN_NOT_BE_EMPTY.getDescription());
        }

        Booking booking = bookingService.findBookingsByEmailBookingReference(partnerId, bookerEmail, bookingReference);

        return BookingRequestTransformer.toFindBookingsRS(booking, voucherPropertyService);
    }

    // admin function
    public SearchBookingsRS searchBookings(String partnerId, String confNumber, String firstName, String lastName,
            String phoneNumber, String countryISOCode, String email, String startActivityDate, String endActivityDate,
            String startBookingDate, String endBookingDate, String geoLocation, String productId, String vendor,
            String status, boolean goodStanding, String searchOperator, int hitsPerPage, int pageNumber) {

        if (StringUtils.isEmpty(confNumber) && StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName)
                && StringUtils.isEmpty(phoneNumber) && StringUtils.isEmpty(countryISOCode) && StringUtils.isEmpty(email)
                && StringUtils.isEmpty(startActivityDate) && StringUtils.isEmpty(endActivityDate)
                && StringUtils.isEmpty(startBookingDate) && StringUtils.isEmpty(endBookingDate)
                && StringUtils.isEmpty(geoLocation) && StringUtils.isEmpty(productId) && StringUtils.isEmpty(vendor)
                && StringUtils.isEmpty(status) && goodStanding) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "search parameters don't have values");
        }

        if ((!StringUtils.isEmpty(startActivityDate) && StringUtils.isEmpty(endActivityDate))
                || (StringUtils.isEmpty(startActivityDate) && !StringUtils.isEmpty(endActivityDate))) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(),
                    "Both start activity date and end activity date required for activity date search");
        }

        if ((!StringUtils.isEmpty(startBookingDate) && StringUtils.isEmpty(endBookingDate))
                || (StringUtils.isEmpty(startBookingDate) && !StringUtils.isEmpty(endBookingDate))) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(),
                    "Both start booking date and end booking date required for booking date search");
        }

        if ((!StringUtils.isEmpty(phoneNumber) && StringUtils.isEmpty(countryISOCode))
                || (StringUtils.isEmpty(phoneNumber) && !StringUtils.isEmpty(countryISOCode))) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(),
                    "Both phone number and Country ISO Code required for phone number search");
        }

        if ((!StringUtils.isEmpty(phoneNumber) && !StringUtils.isEmpty(countryISOCode))
                && !BookingServiceUtil.isValidPhoneNumber(countryISOCode, phoneNumber)) {
            throw new BadRequestException(PlacePassExceptionCodes.PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE.toString(),
                    PlacePassExceptionCodes.PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE.getDescription());
        }

        PlatformStatus bookingStatus = null;
        if (!StringUtils.isEmpty(status)) {
            try {
                bookingStatus = PlatformStatus.valueOf(status.toUpperCase());
            } catch (Exception e) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Booking status is invalid");
            }

        }

        Page<Booking> bookingsList = bookingService.searchBookings(partnerId, confNumber, firstName, lastName,
                phoneNumber, email, startActivityDate, endActivityDate, startBookingDate, endBookingDate, geoLocation,
                productId, vendor, bookingStatus, goodStanding, searchOperator, hitsPerPage, pageNumber);
        return BookingRequestTransformer.toSearchBookingsRS(bookingsList, voucherPropertyService);
    }

    @Deprecated
    public String getVoucher(String voucherId) {

        Booking booking = bookingService.getVoucherDetails(voucherId);

        if (booking.getBookingOptions() != null && !booking.getBookingOptions().isEmpty()) {
            if (booking.getBookingOptions().get(0).getVoucher() != null
                    && booking.getBookingOptions().get(0).getVoucher().getUrls() != null
                    && !booking.getBookingOptions().get(0).getVoucher().getUrls().isEmpty()) {
                return booking.getBookingOptions().get(0).getVoucher().getUrls().get(0);
            } else {
                logger.error("No voucher URL's found for given Voucher ID {}", voucherId);
                return "";
            }
        } else {
            logger.error("No voucher URL's found for given Voucher ID {}", voucherId);
            return "";
        }
    }

    public VoucherDTO getVoucherDetails(String voucherId) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(PlatformLoggingKey.VOUCHER_ID.name(), voucherId);
        logger.info("Voucher Details Retrieval initiated. {}", logData);

        Booking booking = bookingService.getBookingVoucherDetails(voucherId);
        if (booking.getBookingSummary() != null && booking.getBookingSummary().getOverallStatus() != null
                && (PlatformStatus.CANCELLED == booking.getBookingSummary().getOverallStatus().getStatus())) {
            logger.error("Booking is already in CANCELLED state. {}", logData);
            throw new BadRequestException(PlacePassExceptionCodes.CANCELLED_BOOKING_VOUCHER_RETRIEVAL_FAILED.toString(),
                    PlacePassExceptionCodes.CANCELLED_BOOKING_VOUCHER_RETRIEVAL_FAILED.getDescription());
        }
        Voucher voucher = booking.getBookingOptions().get(0).getVoucher();
        VoucherDTO voucherDTO = new VoucherDTO();

        if (VoucherType.HTML == voucher.getType() || voucher.getType() == null) {
            // call to the vendor connector
            BookingVoucherRQ bookingVoucherCRQ = new BookingVoucherRQ();
            bookingVoucherCRQ.setReferenceNumber(booking.getVendorBookingRefId());
            bookingVoucherCRQ.setBookerEmail(booking.getBookerDetails().getEmail());

            BookingVoucherRS bookingVoucherCRS = vendorBookingService.getVoucherDetails(bookingVoucherCRQ,
                    Vendor.getVendor(booking.getBookingOptions().get(0).getVendor()));

            if (bookingVoucherCRS.getResultType() == null
                    || bookingVoucherCRS.getResultType().getCode() != VendorErrorCode.SUCCESS.getId()) {

                logData.put(PlatformLoggingKey.VOUCHER_RESPONSE_CODE.name(),
                        bookingVoucherCRS.getResultType().getCode());
                logData.put(PlatformLoggingKey.VOUCHER_RESPONSE_MESSAGE.name(),
                        bookingVoucherCRS.getResultType().getMessage());
                logger.error("Voucher retrieval has been failed. {}", logData);
                throw new BadRequestException(PlacePassExceptionCodes.VOUCHER_RETRIEVAL_FAILED.toString(),
                        PlacePassExceptionCodes.VOUCHER_RETRIEVAL_FAILED.getDescription());
            }
            Voucher bookingVoucher = BookingRequestTransformer.toVoucher(bookingVoucherCRS.getVoucher());
            bookingVoucher.setId(voucher.getId());

            booking.getBookingOptions().get(0).setVoucher(bookingVoucher);
            booking.setUpdatedTime(Instant.now());
            bookingRepository.save(booking);

            voucherDTO = VoucherDTOTransformer.toVoucherDTO(bookingVoucher, booking.getBookingOptions());
            voucherDTO.setHtmlContent(bookingVoucherCRS.getVoucher().getHtmlContent());

        } else {
            voucherDTO = VoucherDTOTransformer.toVoucherDTO(voucher, booking.getBookingOptions());
        }
        logger.info("Voucher Details Retrieval completed. {}", logData);
        return voucherDTO;
    }

    public CancelBookingRS cancelBooking(String partnerId, String bookingId, CancelBookingRQ cancelBookingRequest) {

        CancelBookingRS cancelBookingRS = null;
        Map<String, Object> logData = new HashMap<>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);
        logger.info("Cancel booking initiated. {}", logData);

        Booking booking = bookingService.getBookingForUpdate(bookingId, partnerId);

        CancelBooking cancelBooking = cancelBookingRepository.findByPartnerIdAndBookingId(partnerId, bookingId);

        if (bookingCancelSpec.isSatisfiedBy(cancelBookingRequest, booking, cancelBooking)) {

            int bookingEventIndex =  booking.getBookingEvents().size();
        	            
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_CANCEL_PROCESSING, null);
            
            if (cancelBooking == null) {
                cancelBooking = BookingRequestTransformer.toCancelBooking(booking);
            }
            CancelBookingTransaction cancelBookingTransaction = BookingRequestTransformer
                    .toCancelBookingTransaction(cancelBookingRequest, false);

            cancelBooking.getCancelBookingTransactions().add(cancelBookingTransaction);
            cancelBooking.setUpdatedTime(Instant.now());
            cancelBookingRepository.save(cancelBooking);

            logData.put(PlatformLoggingKey.CANCEL_BOOKING_ID.name(), cancelBooking.getId());
            logData.put(PlatformLoggingKey.CANCEL_BOOKING_TX_ID.name(), cancelBookingTransaction.getTransactionId());
            logData.put(PlatformLoggingKey.CANCEL_BOOKING_STATE.name(), CancelBookingState.BOOKING_CANCEL);
            logger.info("Cancel booking saved. {}", logData);

            logger.info("Vendor cancel booking call initiated. {}", logData);
            Vendor vendor = Vendor.getVendor(booking.getBookingOptions().get(0).getVendor());
            com.placepass.connector.common.booking.CancelBookingRQ cancelBookingCRQ = BookingVendorConTransformer
                    .toCancelBookingCRQ(booking, cancelBookingRequest.getCancellationReasonCode());
            
            Map<String,String> cancelRequestSentExtenedAttributes = BookingEventTransformer.constructExtendedAttributeForCancelBookingRequestSent(cancelBookingCRQ.getBookingId(), cancelBookingCRQ.getBookingReferenceId(), vendor.name(), cancelBookingCRQ.getCancelDescription(), cancelBookingCRQ.getCancellationReasonCode(), BookingCancellationType.GENERAL.name());
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_CANCEL_REQUEST_SENT, cancelRequestSentExtenedAttributes);
            
            com.placepass.connector.common.booking.CancelBookingRS cancelBookingCRS = bookingProcessor
                    .cancelBooking(cancelBookingCRQ, vendor);
            logger.info("Vendor cancel booking call completed. {}", logData);

            String vendorStatus = getVendorError(cancelBookingCRS.getResultType().getCode());
            String connectorStatus = "message : "+cancelBookingCRS.getResultType().getMessage();
            if (cancelBookingCRS.getResultType().getExtendedAttributes() != null){
            	connectorStatus = connectorStatus + "; ExtendedAttributes : "+ cancelBookingCRS.getResultType().getExtendedAttributes().toString();
            }
            Map<String,String> cancelResponseReceivedExtenedAttributes = BookingEventTransformer.constructExtendedAttributeForCancelBookingResponseReceived(cancelBookingCRS.getBookingId(), cancelBookingCRS.getBookingReferenceNo(), cancelBookingCRS.getCancellationFee(), vendorStatus , connectorStatus);
            if (VendorErrorCode.VENDOR_CONNECTOR_TIMEOUT_ERROR.getId() == cancelBookingCRS.getResultType().getCode() ||
            		VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId() == cancelBookingCRS.getResultType().getCode() ||
            		VendorErrorCode.VENDOR_READ_TIMEOUT_ERROR.getId() == cancelBookingCRS.getResultType().getCode()){
               	String reason = "Booking cancel timeout";
               	if (cancelBookingCRS.getResultType().getExtendedAttributes() != null){
            		reason = reason + " , External status: " + cancelBookingCRS.getResultType().getExtendedAttributes().toString();
            	}
            	ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, reason);
                bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_CANCEL_RESPONSE_RECEIVED, cancelResponseReceivedExtenedAttributes, manualInterventionDetail); 
            }else{
                bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_CANCEL_RESPONSE_RECEIVED, cancelResponseReceivedExtenedAttributes); 
            }
            
            this.updateCancelBookingResponse(cancelBookingTransaction, cancelBookingCRS, logData);
            cancelBooking.setUpdatedTime(Instant.now());
            cancelBookingRepository.save(cancelBooking);

            ConnectorPaymentReversalResponse refundResponse = null;

            if (fullRefundSpec.isSatisfiedBy(cancelBookingCRS, booking, cancelBooking, cancelBookingTransaction)) {

            	refundResponse = doFullRefund(logData, booking, cancelBooking, cancelBookingTransaction);
            	booking = updateManualInterventionRequiredForCancelBooking(booking, refundResponse);
            	
            } else if (partialRefundSpec.isSatisfiedBy(cancelBookingCRS, booking, cancelBooking,
                    cancelBookingTransaction)) {

            	RefundSummary refundSummary = refundStrategy.getRefundSummary(booking, cancelBooking,
                        cancelBookingTransaction);
                float partialRefundAmount = refundSummary.getDollarAmount();
            	refundResponse = doPartialRefund(logData, booking, cancelBooking, cancelBookingTransaction, refundSummary, partialRefundAmount);
            	booking = updateManualInterventionRequiredForCancelBooking(booking, refundResponse);
      
            } else {

            	doNonRefund(cancelBooking, cancelBookingTransaction);
               
            }

            Status status = cancelBookingStatusTranslator.translate(cancelBookingCRS, refundResponse);
            cancelBookingTransaction.setCancelStatus(status);
            cancelBookingTransaction.setUpdatedTime(Instant.now());
            cancelBookingTransaction.updateIsInGoodStanding();
            cancelBooking.setCancelStatus(status);
            cancelBooking.setUpdatedTime(Instant.now());
            cancelBookingRepository.save(cancelBooking);

            BookingSummaryTransformer.updateBookingSummary(booking, cancelBooking, cancelBookingTransaction);
            booking.setUpdatedTime(Instant.now());
            bookingRepository.save(booking);

            if (PlatformStatus.SUCCESS == cancelBooking.getCancelStatus().getStatus()) {
                eventService.publishBookingCancellationEvent(cancelBooking.getPartnerId(), cancelBooking.getBookingId(),
                        cancelBooking.getId(), cancelBookingTransaction.getTransactionId(), null);
            }

            if (PlatformStatus.BOOKING_CANCEL_FAILED == status.getStatus()
                    || PlatformStatus.BOOKING_CANCEL_TIMEOUT == status.getStatus()) {

                throw new InternalErrorException(PlacePassExceptionCodes.CANCEL_BOOKING_FAILED.toString(),
                        PlacePassExceptionCodes.CANCEL_BOOKING_FAILED.getDescription(),
                        cancelBookingTransaction.getCancelStatus().getExternalStatus() + "");
            } else if (PlatformStatus.REFUND_FAILED == status.getStatus()
                    || PlatformStatus.REFUND_TIMEOUT == status.getStatus()) {
                throw new InternalErrorException(PlacePassExceptionCodes.CANCEL_BOOKING_REFUND_FAILED.toString(),
                        PlacePassExceptionCodes.CANCEL_BOOKING_REFUND_FAILED.getDescription(),
                        cancelBookingTransaction.getCancelStatus().getExternalStatus() + "");

            }
            cancelBookingRS = BookingRequestTransformer.toCancelBookingRS(cancelBooking, cancelBookingTransaction);

        }
        return cancelBookingRS;
    }

    private void updateRefundResponse(ConnectorPaymentReversalResponse refundResponse, Refund refund,
            Map<String, Object> logData) {

        refund.setRefundStatus(PaymentStatus.getPaymentStatus(refundResponse.getPaymentStatus().name()));
        refund.setProcessedTime(refundResponse.getProcessedTime());
        refund.setExtRefundTxId(refundResponse.getExtReversalTxId());
        refund.setExternalStatuses(refundResponse.getExternalStatuses());
        refund.setUpdatedTime(Instant.now());

        logData.put(PlatformLoggingKey.PAYMENT_REFUND_STATUS.name(), refundResponse.getPaymentStatus());
        logData.put(PlatformLoggingKey.PAYMENT_REFUND_EXT_STATUSES.name(), refundResponse.getExternalStatuses());
        if (PaymentStatus.PAYMENT_REVERSAL_SUCCESS == refund.getRefundStatus()) {

            logData.put(PlatformLoggingKey.PAYMENT_REFUND_EXT_REFERENCE.name(), refundResponse.getExtReversalTxId());
            logger.info("Payment refund success. {}", logData);
        } else {
            logger.error("Payment refund failed. {}", logData);
        }
    }

    private void updateCancelBookingResponse(CancelBookingTransaction cancelBookingTransaction,
            com.placepass.connector.common.booking.CancelBookingRS cancelBookingCRS, Map<String, Object> logData) {

        logData.put(PlatformLoggingKey.CANCEL_BOOKING_RESPONSE_CODE.name(), cancelBookingCRS.getResultType().getCode());
        logData.put(PlatformLoggingKey.CANCEL_BOOKING_RESPONSE_MESSAGE.name(),
                cancelBookingCRS.getResultType().getMessage());
        if (VendorErrorCode.SUCCESS.getId() == cancelBookingCRS.getResultType().getCode()) {

            logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), cancelBookingCRS.getBookingReferenceNo());
            logger.info("Cancel booking success . {}", logData);

            cancelBookingTransaction.setCancelledBookingItems(
                    BookingRequestTransformer.toCancelledBookingItems(cancelBookingCRS.getCancelledBookingItems()));
            cancelBookingTransaction.setCancellationFee(cancelBookingCRS.getCancellationFee());
            cancelBookingTransaction.setUpdatedTime(Instant.now());

        } else {
            logger.error("Cancel booking failed. {}", logData);
        }
        logData.remove(PlatformLoggingKey.BOOKING_REFERENCE.name());
        logData.remove(PlatformLoggingKey.CANCEL_BOOKING_RESPONSE_CODE.name());
        logData.remove(PlatformLoggingKey.CANCEL_BOOKING_RESPONSE_MESSAGE.name());
    }

    /*
     * Expose a new endpoint to mark a PENDING booking as SUCCESS or FALIED. If failed we need to call stripe to refund.
     * Only pending bookings can be modified with this endpoint. Throw 400 bad request if status of the booking is
     * already SUCCESS or FAILURE. If success we need to make sure the voucher url is properly set. Also mark
     * isInGoodStanding to true for both SUCCESS or FAILURE(after refund success) status. This is an admin API endpoint
     * and it only needs to be exposed via admin API. This endpoint will be used by the backend job to periodically
     * check the status of pending bookings and update the status.
     */
    public void changePendingBookingStatus(String partnerId, String bookingId, String newStatus) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);
        logData.put(PlatformLoggingKey.NEW_STATUS.name(), newStatus);

        logger.info("Change pending booking status initiated. {}", logData);
        if (!newStatus.equalsIgnoreCase(NewBookingStatus.CONFIRMED.name())
                && !newStatus.equalsIgnoreCase(NewBookingStatus.FAILED.name())
                && !newStatus.equalsIgnoreCase(NewBookingStatus.REJECTED.name())) {

            logger.error("Invalid booking status provided. {}", logData);
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_BOOKING_STATUS.toString(),
                    PlacePassExceptionCodes.INVALID_BOOKING_STATUS.getDescription());
        }

        // temp fix until the repository operations moved to service classes
        Booking booking = bookingService.getBookingForUpdate(bookingId, partnerId);

        if (!booking.isPending()) {

            logger.error("Booking is not in pending status to update to new status. {}", logData);
            throw new BadRequestException(PlacePassExceptionCodes.BOOKING_NOT_PENDING.toString(),
                    PlacePassExceptionCodes.BOOKING_NOT_PENDING.getDescription());
        }

        /*
         * Confirmed scenario
         */
        if (newStatus.equalsIgnoreCase(NewBookingStatus.CONFIRMED.name())) {

            updatePendingBookingAsConfirmed(booking);

            /*
             * Rejected scenario
             */
        } else if (newStatus.equalsIgnoreCase(NewBookingStatus.FAILED.name())
                || newStatus.equalsIgnoreCase(NewBookingStatus.REJECTED.name())) {

            updatePendingBookingAsRejected(booking);

        }

    }

    /**
     * This will trigger Email's of the latest state of the bookings.
     * 
     * @param partnerId
     * @param bookingId
     */
    public void publishEvent(String partnerId, String bookingId, String receiverEmail) {

        Booking booking = bookingService.adminGetBooking(bookingId, partnerId);
        CancelBooking cancelBooking = cancelBookingRepository.findByPartnerIdAndBookingId(partnerId, bookingId);

        if (cancelBooking != null) {

            if (cancelBooking.getCancelBookingTransactions() == null
                    || cancelBooking.getCancelBookingTransactions().isEmpty()) {
                throw new NotFoundException(PlacePassExceptionCodes.NO_CANCEL_BOOKING_TRANSACTION_FOUND.toString(),
                        PlacePassExceptionCodes.NO_CANCEL_BOOKING_TRANSACTION_FOUND.getDescription());
            }

            Optional<CancelBookingTransaction> cancelBookingTransactions = cancelBooking.getCancelBookingTransactions()
                    .stream().filter(cbt -> cbt.getCancelStatus().getStatus() == PlatformStatus.SUCCESS).findFirst();
            if (!cancelBookingTransactions.isPresent()) {
                throw new NotFoundException(PlacePassExceptionCodes.NO_CANCEL_BOOKING_TRANSACTION_FOUND.toString(),
                        PlacePassExceptionCodes.NO_CANCEL_BOOKING_TRANSACTION_FOUND.getDescription());
            }
            // when cancel booking transaction found
            eventService.publishBookingCancellationEvent(partnerId, bookingId, cancelBooking.getId(),
                    cancelBookingTransactions.get().getTransactionId(), receiverEmail);
        } else {
            if (booking.isPending()) {
                eventService.publishBookingPendingEvent(bookingId, partnerId, receiverEmail);

            } else {
                if (booking.getBookingStatus() != null
                        && PlatformStatus.SUCCESS.equals(booking.getBookingStatus().getStatus())) {
                    eventService.publishBookingConfirmationEvent(bookingId, partnerId, receiverEmail);
                } else if (booking.getBookingStatus() != null
                        && PlatformStatus.BOOKING_REJECTED.equals(booking.getBookingStatus().getStatus())) {
                    eventService.publishBookingRejectedEvent(partnerId, bookingId, receiverEmail);
                }
            }
        }
    }

    /**
     * This will trigger Email's of the latest state of the bookings / voucher.
     * 
     * @param partnerId
     * @param bookingId
     * @param emailType
     */
    public void resendEmail(String partnerId, String bookingId, ResendEmailType emailType, String receiverEmail) {

        if (ResendEmailType.VOUCHER.equals(emailType)) {

            Booking booking = bookingService.getBooking(bookingId, partnerId);

            if (booking.isPending()) {

                throw new BadRequestException(PlacePassExceptionCodes.PENDING_BOOKING_VOUCHER_RESEND_FAILED.toString(),
                        PlacePassExceptionCodes.PENDING_BOOKING_VOUCHER_RESEND_FAILED.getDescription());

            } else if (booking.getBookingSummary() != null && booking.getBookingSummary().getOverallStatus() != null
                    && (PlatformStatus.SUCCESS == booking.getBookingSummary().getOverallStatus().getStatus())) {

                eventService.resendVoucherEvent(bookingId, partnerId, receiverEmail);

            } else if (booking.getBookingSummary() != null && booking.getBookingSummary().getOverallStatus() != null
                    && (PlatformStatus.BOOKING_REJECTED == booking.getBookingSummary().getOverallStatus()
                            .getStatus())) {

                throw new BadRequestException(PlacePassExceptionCodes.REJECTED_BOOKING_VOUCHER_RESEND_FAILED.toString(),
                        PlacePassExceptionCodes.REJECTED_BOOKING_VOUCHER_RESEND_FAILED.getDescription());

            } else if (booking.getBookingSummary() != null && booking.getBookingSummary().getOverallStatus() != null
                    && (PlatformStatus.CANCELLED == booking.getBookingSummary().getOverallStatus().getStatus())) {

                throw new BadRequestException(
                        PlacePassExceptionCodes.CANCELLED_BOOKING_VOUCHER_RESEND_FAILED.toString(),
                        PlacePassExceptionCodes.CANCELLED_BOOKING_VOUCHER_RESEND_FAILED.getDescription());

            } else {
                // Fix me
            }

        } else {

            publishEvent(partnerId, bookingId, receiverEmail);

        }

    }

    // encrypts loyalty account id
    private Booking encryptLoyaltyAcctId(Booking booking) {
        if (booking.getLoyaltyAccount() != null) {
            LoyaltyAccount loyaltyAccount = booking.getLoyaltyAccount();
            // Create decryption context
            Map<String, String> encryptionContext = new HashMap<>();
            encryptionContext.put("customer_id", booking.getCustomerId());
            encryptionContext.put("partner_id", booking.getPartnerId());
            encryptionContext.put("booker_email", booking.getBookerDetails().getEmail());
            String loyaltyAccountId = cryptoService.awsKmsEncrypt(loyaltyAccount.getLoyaltyAccountId(),
                    encryptionContext);
            loyaltyAccount.setLoyaltyAccountId(loyaltyAccountId);
        }
        return booking;
    }

    // decrypts and masks loyalty account id
    private Booking decryptLoyaltyAcctId(Booking booking, boolean mask) {
        if (booking.getLoyaltyAccount() != null) {
            LoyaltyAccount loyaltyAccount = booking.getLoyaltyAccount();
            // Create decryption context
            Map<String, String> decryptionContext = new HashMap<>();
            decryptionContext.put("customer_id", booking.getCustomerId());
            decryptionContext.put("partner_id", booking.getPartnerId());
            decryptionContext.put("booker_email", booking.getBookerDetails().getEmail());
            String loyaltyAccountId = cryptoService.awsKmsDecrypt(loyaltyAccount.getLoyaltyAccountId(),
                    decryptionContext);
            if (mask) {
                String maskedAccountId = cryptoService.maskStringToLength(loyaltyAccountId, -4);
                loyaltyAccount.setLoyaltyAccountId(maskedAccountId);
            } else {
                loyaltyAccount.setLoyaltyAccountId(loyaltyAccountId);
            }
        }
        return booking;
    }

    public VendorListDTO retrieveVendorList() {
        VendorListDTO vendorListDTO = new VendorListDTO();
        List<VendorDTO> vendorList = new ArrayList<VendorDTO>();
        for (Vendor pl : Vendor.values()) {
            VendorDTO vendorDTO = new VendorDTO();
            vendorDTO.setVendorKey(pl.name());
            vendorDTO.setName(pl.getLabel());
            vendorList.add(vendorDTO);
        }
        vendorListDTO.setVendors(vendorList);
        return vendorListDTO;
    }

    public PendingBookingsRS getPendingBookings(Integer hitsPerPage, Integer pageNumber, Vendor vendor) {
        List<Booking> pendingBookings = bookingService.getPendingBookings(hitsPerPage, pageNumber, vendor);
        return BookingRequestTransformer.toPendingBookingsRS(pendingBookings);
    }

    public ProcessBookingStatusRS processBookingStatus(String partnerId, String bookingId, String vendorName,
            String vendorBookingRefId, String bookerEmail, String bookingStatus) {

        Vendor vendor = BookingServiceUtil.validateVendor(vendorName);

        if (!StringUtils.hasText(vendorBookingRefId)) {
            throw new BadRequestException(PlacePassExceptionCodes.VENDOR_BOOKING_REFERENCE_IS_REQUIRED.toString(),
                    PlacePassExceptionCodes.VENDOR_BOOKING_REFERENCE_IS_REQUIRED.getDescription());
        }

        if (!StringUtils.hasText(bookingStatus)) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_STATUS.toString(),
                    PlacePassExceptionCodes.INVALID_STATUS.getDescription());
        }

        if (!CartValidator.isValidEmailAddress(bookerEmail)) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_EMAIL.toString(),
                    PlacePassExceptionCodes.INVALID_EMAIL.getDescription());
        }

        BookingStatusRS bookingStatusRS = bookingProcessor.getBookingStatus(vendor, vendorBookingRefId, bookerEmail,
                bookingStatus);
        ProcessBookingStatusRS getBookingStatusRS = BookingRequestTransformer.toGetBookingStatusRS(bookingStatusRS,
                partnerId, bookingId, vendor.name(), vendorBookingRefId, bookerEmail, bookingStatus);

        if (getBookingStatusRS.getNewStatus().equals(ConnectorBookingStatus.CONFIRMED)) {
            Booking booking = bookingService.getBookingByIdAndPartnerIdForUpdate(bookingId, partnerId);

            if (booking.getBookingStatus().getStatus().toString().equals(ConnectorBookingStatus.PENDING.toString())) {
                changePendingBookingStatus(booking, VendorErrorCode.SUCCESS.name(),
                        bookingStatusRS.getResultType().getExtendedAttributes());
                getBookingStatusRS.setBookingStatusUpdated(true);
                this.updateManualInventionRequiredInGetBookingStatus(getBookingStatusRS, booking);
            }
        } else if (getBookingStatusRS.getNewStatus().equals(ConnectorBookingStatus.REJECTED)) {
            Booking booking = bookingService.getBookingByIdAndPartnerIdForUpdate(bookingId, partnerId);
            if (booking.getBookingStatus().getStatus().toString().equals(ConnectorBookingStatus.PENDING.toString())) {
                changePendingBookingStatus(booking, VendorErrorCode.REJECTED.name(),
                        bookingStatusRS.getResultType().getExtendedAttributes());
                getBookingStatusRS.setBookingStatusUpdated(true);
                this.updateManualInventionRequiredInGetBookingStatus(getBookingStatusRS, booking);
            }
        } else if (getBookingStatusRS.getNewStatus().equals(ConnectorBookingStatus.CANCELLED)) {
            Booking booking = bookingService.getBookingByIdAndPartnerIdForUpdate(bookingId, partnerId);
            if (booking.getBookingStatus().getStatus().toString().equals(ConnectorBookingStatus.PENDING.toString())) {
                changePendingBookingStatus(booking, VendorErrorCode.CANCELLED.name(),
                        bookingStatusRS.getResultType().getExtendedAttributes());
                getBookingStatusRS.setBookingStatusUpdated(true);
                this.updateManualInventionRequiredInGetBookingStatus(getBookingStatusRS, booking);
            }
        } else if (getBookingStatusRS.getNewStatus().equals(ConnectorBookingStatus.UNKNOWN)) {
            Booking booking = bookingService.getBookingByIdAndPartnerIdForUpdate(bookingId, partnerId);
            if (booking.getBookingStatus().getStatus().toString().equals(ConnectorBookingStatus.PENDING.toString())) {
                changePendingBookingStatus(booking, VendorErrorCode.UNKNOWN.name(),
                        bookingStatusRS.getResultType().getExtendedAttributes());
                getBookingStatusRS.setManualInterventionRequired(true);
            }
        } else if (getBookingStatusRS.getNewStatus().equals(ConnectorBookingStatus.PENDING)) {
            getBookingStatusRS.setManualInterventionRequired(false);
        } else if (getBookingStatusRS.getNewStatus().equals(ConnectorBookingStatus.FAILED)) {
            Booking booking = bookingService.getBookingByIdAndPartnerIdForUpdate(bookingId, partnerId);
            if (booking.getBookingStatus().getStatus().toString().equals(ConnectorBookingStatus.PENDING.toString())) {
                changePendingBookingStatus(booking, VendorErrorCode.FAILED.name(),
                        bookingStatusRS.getResultType().getExtendedAttributes());
                getBookingStatusRS.setManualInterventionRequired(true);
            }
        }

        return getBookingStatusRS;

    }

    private void updateManualInventionRequiredInGetBookingStatus(ProcessBookingStatusRS getBookingStatusRS,
            Booking booking) {

        if (booking.getBookingSummary() != null) {
            getBookingStatusRS.setManualInterventionRequired(!booking.getBookingSummary().isOverallGoodStanding());
        }

    }

    public void changePendingBookingStatus(Booking booking, String newStatus, Map<String, String> externalStatus) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.NEW_STATUS.name(), newStatus);

        logger.info("Change pending booking status initiated. {}", logData);

        Status existingStatus = booking.getBookingStatus();
        existingStatus.setExternalStatus(externalStatus);
        booking.setBookingStatus(existingStatus);

        if (newStatus.equalsIgnoreCase(VendorErrorCode.SUCCESS.name())) {
            updatePendingBookingAsConfirmed(booking);
        } else if (newStatus.equalsIgnoreCase(VendorErrorCode.REJECTED.name())) {
            updatePendingBookingAsRejected(booking);
        } else if (newStatus.equalsIgnoreCase(VendorErrorCode.CANCELLED.name())) {
            updatePendingBookingAsCancelled(booking);
        } else if (newStatus.equalsIgnoreCase(VendorErrorCode.UNKNOWN.name())) {
            String vendorStatus = null;
            if (externalStatus != null) {
                vendorStatus = externalStatus.get("vendorStatus");
            }
            updatePendingBookingForUnknownStatus(booking, vendorStatus);
        } else if (newStatus.equalsIgnoreCase(VendorErrorCode.FAILED.name())) {
            updatePendingBookingForFailedStatus(booking);
        }

    }

    private void updatePendingBookingAsConfirmed(Booking booking) {

        int bookingEventIndex =  booking.getBookingEvents().size();
           	
        Status existingStatus = booking.getBookingStatus();
        existingStatus.setStatus(PlatformStatus.SUCCESS);
        booking.setBookingStatus(existingStatus);
        booking.setBookingState(BookingState.BOOKING_CONFIRMATION);

        BookingVoucherRQ bookingVoucherCRQ = new BookingVoucherRQ();
        bookingVoucherCRQ.setReferenceNumber(booking.getVendorBookingRefId());
        bookingVoucherCRQ.setBookerEmail(booking.getBookerDetails().getEmail());

        Map<String, String> voucherReqExtAttr = BookingEventTransformer.constructExtendedAttributeForVoucherRetrievalRequestSent(bookingVoucherCRQ.getReferenceNumber(), booking.getBookingOptions().get(0).getVendor());
        bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.VOUCHER_RETRIEVAL_REQUEST_SENT, voucherReqExtAttr);
        
        BookingVoucherRS bookingVoucherCRS = vendorBookingService.getVoucherDetails(bookingVoucherCRQ,
                Vendor.getVendor(booking.getBookingOptions().get(0).getVendor()));
        
        if (bookingVoucherCRS.getResultType() == null
                || bookingVoucherCRS.getResultType().getCode() != VendorErrorCode.SUCCESS.getId()) {

            Map<String, Object> logData = new HashMap<String, Object>();
            logData.put(PlatformLoggingKey.NEW_STATUS.name(), NewBookingStatus.CONFIRMED.name());
            logData.put(PlatformLoggingKey.PARTNER_ID.name(), booking.getPartnerId());
            logData.put(PlatformLoggingKey.BOOKING_ID.name(), booking.getId());
            logger.error("Voucher retrieval has been failed. {}", logData);
            throw new BadRequestException(PlacePassExceptionCodes.VOUCHER_RETRIEVAL_FAILED.toString(),
                    PlacePassExceptionCodes.VOUCHER_RETRIEVAL_FAILED.getDescription());
        }

        bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.VOUCHER_RETRIEVAL_RESPONSE_RECEIVED, null);
        
        Voucher voucher = BookingRequestTransformer.toVoucher(bookingVoucherCRS.getVoucher());
        voucher.setId(UUID.randomUUID().toString());
        booking.getBookingOptions().get(0).setVoucher(voucher);

        Map<String, String> extAttr = BookingEventTransformer.constructExtendedAttributeForBookingStatusUpdated(BookingStatus.PENDING.name(), BookingStatus.CONFIRMED.name());
        bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_STATUS_UPDATED, extAttr);
        
        booking.updateIsInGoodStanding();
        booking.updateBookingClose();
        BookingSummaryTransformer.updateBookingSummary(booking);
        booking.setUpdatedTime(Instant.now());
        bookingRepository.save(booking);

        eventService.publishBookingConfirmationEvent(booking.getId(), booking.getPartnerId(), null);

    }

    private void updatePendingBookingAsRejected(Booking booking) {

        int bookingEventIndex = booking.getBookingEvents().size();
            	
        Status existingStatus = booking.getBookingStatus();
        existingStatus.setStatus(PlatformStatus.BOOKING_REJECTED);
        booking.setBookingStatus(existingStatus);

        String reversalReason = "Booking got Rejected from Pending status. Issuing a refund.";

        Payment payment = booking.getPayments().stream().filter(pay -> PaymentType.SALE.equals(pay.getPaymentType()))
                .findFirst().get();

        PaymentTransaction originalPaymentTx = BookingPaymentConTransformer.toPaymentTransaction(booking, payment);
        ConnectorPaymentRequest paymentRequest = BookingPaymentConTransformer.toConnectorPaymentRequest(payment,
                booking);

        Map<String, String> paymentRevReqExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentReversalRequestSent(reversalReason, originalPaymentTx.getPaymentTxId(), paymentRequest.getGatewayName(), paymentRequest.getPaymentToken(), RefundMode.FULL.name(), String.valueOf(originalPaymentTx.getPaymentAmount()));
        bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_REQUEST_SENT, paymentRevReqExtAttr);

        ConnectorPaymentReversalResponse reversalResponse = paymentProcessor.reversePayment(originalPaymentTx,
                paymentRequest, reversalReason);
        
        Map<String, String> paymentRevResExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentReversalResponseReceived(reversalResponse.getPaymentStatus(), reversalResponse.getExternalStatuses(), reversalResponse.getExtReversalTxId(), reversalResponse.getReversalReason());
        if (reversalResponse.getPaymentStatus() == PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS){
        	bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED, paymentRevResExtAttr);
        }else{
        	String reason = "Payment reversal failed, Reversal payment status:" + reversalResponse.getPaymentStatus().name();
        	if (reversalResponse.getExternalStatuses() != null){
        		reason = reason + " , External status: " + reversalResponse.getExternalStatuses().toString();
        	}
        	ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, reason);
        	bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED, paymentRevResExtAttr, manualInterventionDetail);
        }
        
        Map<String, String> extAttr = BookingEventTransformer.constructExtendedAttributeForBookingStatusUpdated(BookingStatus.PENDING.name(), BookingStatus.REJECTED.name());
        bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_STATUS_UPDATED, extAttr);
        
        booking.setBookingState(BookingState.PAYMENT_REVERSAL);
        this.updatePaymentOnReversal(booking, reversalResponse, reversalReason);
        booking.updateIsInGoodStanding();
        booking.updateBookingClose();
        BookingSummaryTransformer.updateBookingSummary(booking);
        booking.setUpdatedTime(Instant.now());
        bookingRepository.save(booking);

        eventService.publishBookingRejectedEvent(booking.getPartnerId(), booking.getId(), null);

    }

    private void updatePendingBookingAsCancelled(Booking booking) {

        int bookingEventIndex = booking.getBookingEvents().size();
            	
        Status existingStatus = booking.getBookingStatus();
        existingStatus.setStatus(PlatformStatus.CANCELLED);
        booking.setBookingStatus(existingStatus);

        String reversalReason = "Booking got Cancelled from Pending status. Issuing a refund.";

        Payment payment = booking.getPayments().stream().filter(pay -> PaymentType.SALE.equals(pay.getPaymentType()))
                .findFirst().get();

        PaymentTransaction originalPaymentTx = BookingPaymentConTransformer.toPaymentTransaction(booking, payment);
        ConnectorPaymentRequest paymentRequest = BookingPaymentConTransformer.toConnectorPaymentRequest(payment,
                booking);

        Map<String, String> paymentRevReqExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentReversalRequestSent(reversalReason, originalPaymentTx.getPaymentTxId(), paymentRequest.getGatewayName(), paymentRequest.getPaymentToken(), RefundMode.FULL.name(), String.valueOf(originalPaymentTx.getPaymentAmount()));
        bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_REQUEST_SENT, paymentRevReqExtAttr);

        ConnectorPaymentReversalResponse reversalResponse = paymentProcessor.reversePayment(originalPaymentTx,
                paymentRequest, reversalReason);
        
        Map<String, String> paymentRevResExtAttr = BookingEventTransformer.constructExtendedAttributeForPaymentReversalResponseReceived(reversalResponse.getPaymentStatus(), reversalResponse.getExternalStatuses(), reversalResponse.getExtReversalTxId(), reversalResponse.getReversalReason());
        if (reversalResponse.getPaymentStatus() == PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS){
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED, paymentRevResExtAttr);
        }else{
        	String reason = "Payment reversal failed, Reversal payment status:" + reversalResponse.getPaymentStatus().name();
        	if (reversalResponse.getExternalStatuses() != null){
        		reason = reason + " , External status: " + reversalResponse.getExternalStatuses().toString();
        	}
        	ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, reason);
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED, paymentRevResExtAttr, manualInterventionDetail);
        }
        
        Map<String, String> extAttr = BookingEventTransformer.constructExtendedAttributeForBookingStatusUpdated(BookingStatus.PENDING.name(), BookingStatus.CANCELLED.name());
        bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_STATUS_UPDATED, extAttr);
        
        booking.setBookingState(BookingState.PAYMENT_REVERSAL);
        this.updatePaymentOnReversal(booking, reversalResponse, reversalReason);
        booking.updateIsInGoodStanding();
        booking.updateBookingClose();
        BookingSummaryTransformer.updateBookingSummary(booking);
        booking.setUpdatedTime(Instant.now());
        bookingRepository.save(booking);

        eventService.publishBookingRejectedEvent(booking.getPartnerId(), booking.getId(), null);

    }

    private void updatePendingBookingForUnknownStatus(Booking booking, String vendorStatus) {

        booking.updateGoodStandingFromPendingToUnknown(vendorStatus);
        BookingSummaryTransformer.updateBookingSummary(booking);
        booking.setUpdatedTime(Instant.now());
        bookingRepository.save(booking);

    }

    private void updatePendingBookingForFailedStatus(Booking booking) {

        booking.updateIsInGoodStanding();
        booking.updateBookingClose();
        BookingSummaryTransformer.updateBookingSummary(booking);
        booking.setUpdatedTime(Instant.now());
        bookingRepository.save(booking);

    }
    
    private LoyaltyProgramConfig retrieveLoyaltyProgramConfig (CreateBookingRQ createBookingRequest){
    	
    	 LoyaltyProgramConfig loyaltyProgramConfig = null;

         boolean combinationOne = false;
         boolean combinationTwo = false;

         if (createBookingRequest.getLoyaltyAccount() != null) {
             combinationOne = StringUtils.hasText(createBookingRequest.getLoyaltyAccount().getLoyaltyProgramId())
                     && !StringUtils.hasText(createBookingRequest.getLoyaltyAccount().getLoyaltyAccountId());

             combinationTwo = !StringUtils.hasText(createBookingRequest.getLoyaltyAccount().getLoyaltyProgramId())
                     && StringUtils.hasText(createBookingRequest.getLoyaltyAccount().getLoyaltyAccountId());
         }

         if (createBookingRequest.getLoyaltyAccount() != null && ((combinationOne) || (combinationTwo))) {

             throw new BadRequestException(PlacePassExceptionCodes.LOYALTY_ACCOUNT_DETAILS_NOT_FOUND.toString(),
                     PlacePassExceptionCodes.LOYALTY_ACCOUNT_DETAILS_NOT_FOUND.getDescription());

         }

         if (createBookingRequest.getLoyaltyAccount() != null
                 && StringUtils.hasText(createBookingRequest.getLoyaltyAccount().getLoyaltyProgramId())) {
             loyaltyProgramConfig = loyaltyProgramConfigService.getLoyaltyProgramConfigDetail(
                     createBookingRequest.getPartnerId(),
                     createBookingRequest.getLoyaltyAccount().getLoyaltyProgramId());
         }
         
         return loyaltyProgramConfig;
    	
    }

    public ManualCancelBookingRS manualCancelBooking(String partnerId, String bookingId,
            ManualCancelBookingRQ manualCancelBookingRequest) {

        ManualCancelBookingRS manualCancelBookingRS = null;
        ConnectorPaymentReversalResponse refundResponse = null;
        com.placepass.connector.common.booking.CancelBookingRS cancelBookingCRS = null;
        Map<String, Object> logData = new HashMap<>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);
        logger.info("Manual Cancel booking initiated. {}", logData);

        if (!isValidManualRefundType(manualCancelBookingRequest.getRefundType())) {

            logger.info("Invalid manual refund type");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_MANUAL_REFUND_TYPE.toString(),
                    PlacePassExceptionCodes.INVALID_MANUAL_REFUND_TYPE.getDescription());
        }

        Booking booking = bookingService.getBookingForUpdate(bookingId, partnerId);

        CancelBooking cancelBooking = cancelBookingRepository.findByPartnerIdAndBookingId(partnerId, bookingId);

        if (bookingManualCancelSpec.isSatisfiedBy(manualCancelBookingRequest, booking, cancelBooking)) {

            int bookingEventIndex =  booking.getBookingEvents().size();
          
            bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.MANUAL_CANCEL_PROCESSING, null);

            if (cancelBooking == null) {
                cancelBooking = BookingRequestTransformer.toCancelBooking(booking);
            }

            CancelBookingTransaction cancelBookingTransaction = BookingRequestTransformer
                    .toManualCancelBookingTransaction(manualCancelBookingRequest, true);

            RefundSummary refundSummary = refundStrategy.getRefundSummary(booking, cancelBooking, cancelBookingTransaction);
            cancelBooking.getCancelBookingTransactions().add(cancelBookingTransaction);
            cancelBooking.setUpdatedTime(Instant.now());
            cancelBookingRepository.save(cancelBooking);

            logData.put(PlatformLoggingKey.MANUAL_CANCEL_BOOKING_ID.name(), cancelBooking.getId());
            logData.put(PlatformLoggingKey.MANUAL_CANCEL_BOOKING_TX_ID.name(), cancelBookingTransaction.getTransactionId());
            logData.put(PlatformLoggingKey.MANUAL_CANCEL_BOOKING_STATE.name(), CancelBookingState.PAYMENT_REFUND);
            logger.info("Manual cancel booking saved. {}", logData);

            float partialRefundAmount = 0;
            if (ManualRefundType.RULES.name().equals(manualCancelBookingRequest.getRefundType())) {

                if (fullManualRefundSpec.isSatisfiedBy(booking, cancelBooking, cancelBookingTransaction,refundSummary)) {

                    refundResponse = doFullRefund(logData, booking, cancelBooking, cancelBookingTransaction);
                    booking = updateManualInterventionRequiredForManualCancel(booking, refundResponse);
                    
                } else if (partialManualRefundSpec.isSatisfiedBy(booking, cancelBooking, cancelBookingTransaction,refundSummary)) {

                    partialRefundAmount = refundSummary.getDollarAmount();
                    refundResponse = doPartialRefund(logData, booking, cancelBooking, cancelBookingTransaction,
                            refundSummary,partialRefundAmount);
                    booking = updateManualInterventionRequiredForManualCancel(booking, refundResponse);

                } else {

                    doNonRefund(cancelBooking, cancelBookingTransaction);
                }

            } else if (ManualRefundType.AMOUNT.name().equals(manualCancelBookingRequest.getRefundType())) {

                partialRefundAmount = manualCancelBookingRequest.getCancellationAmount();
                refundResponse = doPartialRefund(logData, booking, cancelBooking, cancelBookingTransaction,
                        refundSummary,partialRefundAmount);
                booking = updateManualInterventionRequiredForManualCancel(booking, refundResponse);

            } else if (ManualRefundType.FULL.name().equals(manualCancelBookingRequest.getRefundType())) {

                refundResponse = doFullRefund(logData, booking, cancelBooking, cancelBookingTransaction);
                booking = updateManualInterventionRequiredForManualCancel(booking, refundResponse);

            }

            if (cancelBookingTransaction.getRefundMode().equals(RefundMode.NONE)
                    ||PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS.name().equals(refundResponse.getPaymentStatus().name())) {

            	bookingEventIndex = booking.getBookingEvents().size();
                logger.info("Vendor manual cancel booking call initiated. {}", logData);
                Vendor vendor = Vendor.getVendor(booking.getBookingOptions().get(0).getVendor());
                com.placepass.connector.common.booking.CancelBookingRQ cancelBookingCRQ = BookingVendorConTransformer
                        .toCancelBookingCRQ(booking, manualCancelBookingRequest.getCancellationReasonCode());

                Map<String,String> cancelRequestSentExtenedAttributes = BookingEventTransformer.constructExtendedAttributeForCancelBookingRequestSent(cancelBookingCRQ.getBookingId(), cancelBookingCRQ.getBookingReferenceId(), vendor.name(), cancelBookingCRQ.getCancelDescription(), cancelBookingCRQ.getCancellationReasonCode(), BookingCancellationType.MANUAL.name());
                bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_CANCEL_REQUEST_SENT, cancelRequestSentExtenedAttributes);

                // For Manual cancellation purpose
                cancelBookingCRQ.setCancelationType(BookingCancellationType.MANUAL.name());
                cancelBookingCRS = bookingProcessor.cancelBooking(cancelBookingCRQ, vendor);
                logger.info("Vendor manual cancel booking call completed. {}", logData);

                String vendorStatus = getVendorError(cancelBookingCRS.getResultType().getCode());
                String connectorStatus = "message : "+cancelBookingCRS.getResultType().getMessage();
                if (cancelBookingCRS.getResultType().getExtendedAttributes() != null){
                	connectorStatus = connectorStatus + "; ExtendedAttributes : "+ cancelBookingCRS.getResultType().getExtendedAttributes().toString();
                }
                Map<String,String> cancelResponseReceivedExtenedAttributes = BookingEventTransformer.constructExtendedAttributeForCancelBookingResponseReceived(cancelBookingCRS.getBookingId(), cancelBookingCRS.getBookingReferenceNo(), cancelBookingCRS.getCancellationFee(), vendorStatus, connectorStatus);
                if (VendorErrorCode.SUCCESS.getId() == cancelBookingCRS.getResultType().getCode()) {
                	bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_CANCEL_RESPONSE_RECEIVED, cancelResponseReceivedExtenedAttributes); 
                }else{
                	String reason = "Cancel booking failed, Status: " + cancelBookingCRS.getResultType().getMessage();
                	if (cancelBookingCRS.getResultType().getExtendedAttributes() != null){
                		reason = reason + " , external status: " + cancelBookingCRS.getResultType().getExtendedAttributes().toString();
                	}
                	ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, reason);
                	bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.BOOKING_CANCEL_RESPONSE_RECEIVED, cancelResponseReceivedExtenedAttributes, manualInterventionDetail); 
                }

                this.updateCancelBookingResponse(cancelBookingTransaction, cancelBookingCRS, logData);
                cancelBooking.setUpdatedTime(Instant.now());
                cancelBookingRepository.save(cancelBooking);

            }

            Status status = manualCancelBookingStatusTranslator.translate(cancelBookingCRS, refundResponse);
            cancelBookingTransaction.setCancelStatus(status);
            cancelBookingTransaction.setUpdatedTime(Instant.now());
            cancelBookingTransaction.updateIsInGoodStanding(cancelBookingCRS);
            cancelBooking.setCancelStatus(status);
            cancelBooking.setUpdatedTime(Instant.now());
            cancelBookingRepository.save(cancelBooking);

            BookingSummaryTransformer.updateBookingSummary(booking, cancelBooking, cancelBookingTransaction);
            booking.setUpdatedTime(Instant.now());
            bookingRepository.save(booking);

            if (PlatformStatus.SUCCESS == cancelBooking.getCancelStatus().getStatus()) {
                eventService.publishBookingCancellationEvent(cancelBooking.getPartnerId(), cancelBooking.getBookingId(),
                        cancelBooking.getId(), cancelBookingTransaction.getTransactionId(), null);
            }

            if (PlatformStatus.BOOKING_CANCEL_FAILED == status.getStatus()
                    || PlatformStatus.BOOKING_CANCEL_TIMEOUT == status.getStatus()) {

                throw new InternalErrorException(PlacePassExceptionCodes.CANCEL_BOOKING_FAILED.toString(),
                        PlacePassExceptionCodes.CANCEL_BOOKING_FAILED.getDescription(),
                        cancelBookingTransaction.getCancelStatus().getExternalStatus() + "");

            } else if (PlatformStatus.REFUND_FAILED == status.getStatus()
                    || PlatformStatus.REFUND_TIMEOUT == status.getStatus()) {

                throw new InternalErrorException(PlacePassExceptionCodes.CANCEL_BOOKING_REFUND_FAILED.toString(),
                        PlacePassExceptionCodes.CANCEL_BOOKING_REFUND_FAILED.getDescription(),
                        cancelBookingTransaction.getCancelStatus().getExternalStatus() + "");

            }

            manualCancelBookingRS = BookingRequestTransformer.toManualCancelBookingRS(cancelBooking,
                    cancelBookingTransaction);
        }
        return manualCancelBookingRS;
    }

    public void doNonRefund(CancelBooking cancelBooking, CancelBookingTransaction cancelBookingTransaction) {
        cancelBookingTransaction.setRefundMode(RefundMode.NONE);
        cancelBookingTransaction.setState(CancelBookingState.PAYMENT_REFUND);
        cancelBooking.setUpdatedTime(Instant.now());
        cancelBookingRepository.save(cancelBooking);
    }

    public ConnectorPaymentReversalResponse doPartialRefund(Map<String, Object> logData, Booking booking,
            CancelBooking cancelBooking, CancelBookingTransaction cancelBookingTransaction, RefundSummary refundSummary, float partialRefundAmount) {

        ConnectorPaymentReversalResponse refundResponse;
        Payment payment = booking.getPayments().stream().filter(pay -> PaymentType.SALE.equals(pay.getPaymentType()))
                .findFirst().get();

        String refundReason = "Booking cancelled. Issuing a partial refund.";
        Refund refund = BookingRequestTransformer.toRefund(payment, refundReason, partialRefundAmount);

        List<Refund> refunds = new ArrayList<>(1);
        refunds.add(refund);
        
        if (cancelBooking != null && cancelBookingTransaction != null){
        	cancelBookingTransaction.setRefundMode(RefundMode.PARTIAL);
        	cancelBookingTransaction.setState(CancelBookingState.PAYMENT_REFUND);
        	cancelBookingTransaction.setRefunds(refunds);
        	cancelBooking.setUpdatedTime(Instant.now());
        	cancelBookingRepository.save(cancelBooking);
        }
        
        int bookingEventIndex =  booking.getBookingEvents().size();

        ConnectorPaymentReversalRequest reversalRequest = BookingPaymentConTransformer
                .toConnectorPaymentRefundRequest(booking, refund);

        reversalRequest.setRefundAmount(refundSummary.getCentAmount());
        logData.put(PlatformLoggingKey.CANCEL_BOOKING_STATE.name(), CancelBookingState.PAYMENT_REFUND);
        logData.put(PlatformLoggingKey.REFUND_ID.name(), reversalRequest.getReversalTxId());
        logData.put(PlatformLoggingKey.REFUND_AMOUNT.name(), refundSummary.getDollarAmount());
        logger.info("Partial Payment refund initiated. {}", logData);

    	Map<String,String> reversalRequestSentExtenedAttributes = BookingEventTransformer.constructExtendedAttributeForPaymentReversalRequestSent(reversalRequest.getReversalReason(), reversalRequest.getOriginalPaymentTx().getExtPaymentTxId(), reversalRequest.getGatewayName(), reversalRequest.getPaymentToken(), RefundMode.PARTIAL.name(), String.valueOf(refund.getRefundAmount()));
    	bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_REQUEST_SENT, reversalRequestSentExtenedAttributes);

        refundResponse = paymentProcessor.refundPayment(reversalRequest);

    	Map<String,String> reversalResponseReceivedExtenedAttributes = BookingEventTransformer.constructExtendedAttributeForPaymentReversalResponseReceived(refundResponse.getPaymentStatus(), refundResponse.getExternalStatuses(), refundResponse.getExtReversalTxId(), refundResponse.getReversalReason());
    	bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED, reversalResponseReceivedExtenedAttributes);
  
        logger.info("Partial Payment refund completed. {}", logData);
        logData.remove(PlatformLoggingKey.REFUND_AMOUNT.name());

        this.updateRefundResponse(refundResponse, refund, logData);
        cancelBooking.setUpdatedTime(Instant.now());
        cancelBookingRepository.save(cancelBooking);
        return refundResponse;
    }

    public ConnectorPaymentReversalResponse doFullRefund(Map<String, Object> logData, Booking booking,
            CancelBooking cancelBooking, CancelBookingTransaction cancelBookingTransaction) {

        ConnectorPaymentReversalResponse refundResponse;
        Payment payment = booking.getPayments().stream().filter(pay -> PaymentType.SALE.equals(pay.getPaymentType()))
                .findFirst().get();

        String refundReason = "Booking cancelled. Issuing a refund.";
        Refund refund = BookingRequestTransformer.toRefund(payment, refundReason, payment.getPaymentAmount());
        List<Refund> refunds = new ArrayList<>(1);
        refunds.add(refund);
        
        if (cancelBooking != null && cancelBookingTransaction != null){
        	cancelBookingTransaction.setRefundMode(RefundMode.FULL);
        	cancelBookingTransaction.setState(CancelBookingState.PAYMENT_REFUND);
        	cancelBookingTransaction.setRefunds(refunds);
        	cancelBooking.setUpdatedTime(Instant.now());
        	cancelBookingRepository.save(cancelBooking);
        }
        int bookingEventIndex =  booking.getBookingEvents().size();

        ConnectorPaymentReversalRequest reversalRequest = BookingPaymentConTransformer
                .toConnectorPaymentRefundRequest(booking, refund);

        logData.put(PlatformLoggingKey.CANCEL_BOOKING_STATE.name(), CancelBookingState.PAYMENT_REFUND);
        logData.put(PlatformLoggingKey.REFUND_ID.name(), reversalRequest.getReversalTxId());
        logData.put(PlatformLoggingKey.REFUND_AMOUNT.name(), reversalRequest.getOriginalPaymentTx().getPaymentAmount());
        logger.info("Full Payment refund initiated. {}", logData);

    	Map<String,String> reversalRequestSentExtenedAttributes = BookingEventTransformer.constructExtendedAttributeForPaymentReversalRequestSent(reversalRequest.getReversalReason(), reversalRequest.getOriginalPaymentTx().getExtPaymentTxId(), reversalRequest.getGatewayName(), reversalRequest.getPaymentToken(), RefundMode.FULL.name(), String.valueOf(refund.getRefundAmount()));
    	bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_REQUEST_SENT, reversalRequestSentExtenedAttributes);

        refundResponse = paymentProcessor.refundPayment(reversalRequest);

        Map<String,String> reversalResponseReceivedExtenedAttributes = BookingEventTransformer.constructExtendedAttributeForPaymentReversalResponseReceived(refundResponse.getPaymentStatus(), refundResponse.getExternalStatuses(), refundResponse.getExtReversalTxId(), refundResponse.getReversalReason());
		bookingEventIndex = bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED, reversalResponseReceivedExtenedAttributes);

        logger.info("Full Payment refund completed. {}", logData);
        logData.remove(PlatformLoggingKey.REFUND_AMOUNT.name());

        this.updateRefundResponse(refundResponse, refund, logData);
        cancelBooking.setUpdatedTime(Instant.now());
        cancelBookingRepository.save(cancelBooking);
        return refundResponse;
    }

    public static boolean isValidManualRefundType(String refundType) {

        boolean isvalidType = false;

        try {
            ManualRefundType.valueOf(refundType.trim().toUpperCase());
            isvalidType = true;

        } catch (IllegalArgumentException e) {
            logger.error("Invalid manual refund type",e);
        }

        return isvalidType;
    }
    
    private Booking updateManualInterventionRequiredForCancelBooking(Booking booking, ConnectorPaymentReversalResponse paymentReversalResponse){
    	
    	//Will be removed after the data migration; for backward compatibility
    	if (booking.getBookingEvents() == null){
    		return booking;
    	}
    	
    	BookingEvent paymentReversalResponseEvent = null;
    	for (BookingEvent bookingEvent : booking.getBookingEvents()){
    		if (bookingEvent.getEventName().name().equals(EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED.name())){
    			if (paymentReversalResponseEvent == null || bookingEvent.getIndex() > paymentReversalResponseEvent.getIndex()){
    				paymentReversalResponseEvent = bookingEvent;
    			}    			
    		}
    	}
    	
    	if (paymentReversalResponseEvent != null){
        	if (PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS != paymentReversalResponse.getPaymentStatus()){
        		String reason = "Payment reversal failed, Reversal payment status:" + paymentReversalResponse.getPaymentStatus().name();
        		if (paymentReversalResponse.getExternalStatuses() != null){
        			reason = reason + " , External status: " + paymentReversalResponse.getExternalStatuses().toString();
        		}
        		ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, reason);
        		booking.getBookingEvents().remove(paymentReversalResponseEvent);
        		paymentReversalResponseEvent.setManualInterventionDetail(manualInterventionDetail);
        		paymentReversalResponseEvent.setUpdatedTime(Instant.now());
        		booking.getBookingEvents().add(paymentReversalResponseEvent);
        	}
    	}
    	
    	return booking;
    	
    }
    
 private Booking updateManualInterventionRequiredForManualCancel(Booking booking, ConnectorPaymentReversalResponse paymentReversalResponse){
    	
    	//Will be removed after the data migration; for backward compatibility
    	if (booking.getBookingEvents() == null){
    		return booking;
    	}
    	
    	BookingEvent paymentReversalResponseEvent = null;
    	for (BookingEvent bookingEvent : booking.getBookingEvents()){
    		if (bookingEvent.getEventName().name().equals(EventName.PAYMENT_REVERSAL_RESPONSE_RECEIVED.name())){
    			if (paymentReversalResponseEvent == null || (paymentReversalResponseEvent != null && bookingEvent.getIndex() > paymentReversalResponseEvent.getIndex())){
    				paymentReversalResponseEvent = bookingEvent;
    			}    			
    		}
    	}
    	
    	if (paymentReversalResponseEvent != null){
    		   if (PaymentProcessStatus.PAYMENT_ISSUER_TIMEOUT == paymentReversalResponse.getPaymentStatus()
    	                || PaymentProcessStatus.PAYMENT_PROCESSING_ERROR == paymentReversalResponse.getPaymentStatus()
    	                || PaymentProcessStatus.PAYMENT_GATEWAY_CONNECTION_ERROR == paymentReversalResponse.getPaymentStatus()) {
        		String reason = "Payment reversal timeout; payment status: :" + paymentReversalResponse.getPaymentStatus().name();
        		if (paymentReversalResponse.getExternalStatuses() != null){
        			reason = reason + "; External status: " + paymentReversalResponse.getExternalStatuses().toString();
        		}
        		ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, reason);
        		booking.getBookingEvents().remove(paymentReversalResponseEvent);
        		paymentReversalResponseEvent.setManualInterventionDetail(manualInterventionDetail);
        		paymentReversalResponseEvent.setUpdatedTime(Instant.now());
        		booking.getBookingEvents().add(paymentReversalResponseEvent);
        	}
    	}
    	
    	return booking;
    	
    }
 
 	private String getVendorError(int errorCode){
 		for (VendorErrorCode vendorErrorCode : VendorErrorCode.values()){
 			if (errorCode == vendorErrorCode.getId()){
 				return vendorErrorCode.getMsg();
 			}
 		}
 		return "";
 	}
}