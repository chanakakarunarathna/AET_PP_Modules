package com.placepass.booking.application.booking;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.placepass.booking.application.booking.admin.dto.BookingEventDTO;
import com.placepass.booking.application.booking.admin.dto.ManualInterventionDetailDTO;
import com.placepass.booking.application.booking.admin.dto.SearchBookingsRS;
import com.placepass.booking.application.booking.dto.BookingSummaryDTO;
import com.placepass.booking.application.booking.dto.CancelBookingRQ;
import com.placepass.booking.application.booking.dto.CancelBookingRS;
import com.placepass.booking.application.booking.dto.CancellationRulesDTO;
import com.placepass.booking.application.booking.dto.CreateBookingRQ;
import com.placepass.booking.application.booking.dto.CreateBookingRS;
import com.placepass.booking.application.booking.dto.DiscountDTO;
import com.placepass.booking.application.booking.dto.FeeDTO;
import com.placepass.booking.application.booking.dto.FindBookingsRS;
import com.placepass.booking.application.booking.dto.GetBookingDTO;
import com.placepass.booking.application.booking.dto.GetBookingRS;
import com.placepass.booking.application.booking.dto.GetBookingsHistoryRS;
import com.placepass.booking.application.booking.dto.LoyaltyAccountDTO;
import com.placepass.booking.application.booking.dto.ManualCancelBookingRQ;
import com.placepass.booking.application.booking.dto.ManualCancelBookingRS;
import com.placepass.booking.application.booking.dto.PaymentDTO;
import com.placepass.booking.application.booking.dto.PendingBookingDTO;
import com.placepass.booking.application.booking.dto.PendingBookingsRS;
import com.placepass.booking.application.booking.dto.ProcessBookingStatusRS;
import com.placepass.booking.application.booking.dto.ProductDetailsDTO;
import com.placepass.booking.application.booking.dto.RulesDTO;
import com.placepass.booking.application.booking.dto.StatusDTO;
import com.placepass.booking.application.booking.paymentcondto.ReversalDetailsDTO;
import com.placepass.booking.application.cart.dto.BookerDTO;
import com.placepass.booking.application.cart.dto.BookingOptionDTO;
import com.placepass.booking.application.cart.dto.PickupLocationDTO;
import com.placepass.booking.application.cart.dto.QuantityDTO;
import com.placepass.booking.application.cart.dto.TotalDTO;
import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.booking.application.common.VoucherPropertyService;
import com.placepass.booking.domain.booking.Booker;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.BookingEvent;
import com.placepass.booking.domain.booking.BookingItem;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.BookingState;
import com.placepass.booking.domain.booking.Cart;
import com.placepass.booking.domain.booking.Discount;
import com.placepass.booking.domain.booking.Fee;
import com.placepass.booking.domain.booking.LoyaltyAccount;
import com.placepass.booking.domain.booking.Payment;
import com.placepass.booking.domain.booking.PaymentStatus;
import com.placepass.booking.domain.booking.PaymentType;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.booking.domain.booking.ReversalDetails;
import com.placepass.booking.domain.booking.Total;
import com.placepass.booking.domain.booking.Traveler;
import com.placepass.booking.domain.booking.Voucher;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.CancelBookingState;
import com.placepass.booking.domain.booking.cancel.CancelBookingTransaction;
import com.placepass.booking.domain.booking.cancel.CancelledBookingItem;
import com.placepass.booking.domain.booking.cancel.Refund;
import com.placepass.booking.domain.config.LoyaltyProgramConfig;
import com.placepass.booking.domain.config.PartnerConfig;
import com.placepass.booking.domain.product.CancellationRules;
import com.placepass.booking.domain.product.ProductDetails;
import com.placepass.booking.domain.product.Rules;
import com.placepass.connector.common.booking.BookingStatusRS;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utills.loyalty.details.LoyaltyDetailsUtill;
import com.placepass.utils.bookingstatus.ConnectorBookingStatus;
import com.placepass.utils.common.StringUtils;
import com.placepass.utils.currency.AmountFormatter;

public class BookingRequestTransformer {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String STRIPE_STATEMENT_DESC_REGEX = "[<>'\"]";

    public static Booking toBooking(CreateBookingRQ bookingRequest, Cart cart, String bookingReference,
            List<ProductDetails> productDetailsList) {

        Booking booking = new Booking();
        booking.setPartnerId(bookingRequest.getPartnerId());
        booking.setBookerDetails(cart.getBookerDetails());
        booking.setCustomerId(bookingRequest.getCustomerId());
        booking.setBookingOptions(cart.getBookingOptions());

        for (BookingOption bookingOption : booking.getBookingOptions()) {
            ProductDetails productDetails = productDetailsList.stream()
                    .filter(pd -> bookingOption.getProductId().equals(pd.getProductId())).findFirst().get();
            bookingOption.setProductDetails(productDetails);
        }

        booking.setBookingReference(bookingReference);
        booking.setBookingState(BookingState.OPEN);
        booking.setCartId(cart.getCartId());
        booking.setExtendedAttributes(bookingRequest.getExtendedAttributes());
        booking.setCreatedTime(Instant.now());
        booking.setTotal(cart.getTotal());
        booking.getFees().addAll(cart.getFees());

        return booking;

    }

    public static List<BookingItem> toBookingItems(
            List<com.placepass.connector.common.booking.BookingItem> bookingItemCDTOs) {
        List<BookingItem> bookingItems = new ArrayList<>();
        for (com.placepass.connector.common.booking.BookingItem bookingItemCDTO : bookingItemCDTOs) {
            BookingItem bookingItem = new BookingItem();
            bookingItem.setCancellable(bookingItemCDTO.isCancellable());
            bookingItem.setItemId(bookingItemCDTO.getItemId());
            bookingItem.setItemRef(bookingItemCDTO.getItemRef());

            bookingItems.add(bookingItem);
        }
        return bookingItems;
    }

    public static List<CancelledBookingItem> toCancelledBookingItems(
            List<com.placepass.connector.common.booking.CancelledBookingItem> cancelledBookingItemCDTOs) {
        List<CancelledBookingItem> cancelledBookingItems = null;
        if (cancelledBookingItemCDTOs != null) {
            cancelledBookingItems = new ArrayList<>();
            for (com.placepass.connector.common.booking.CancelledBookingItem bookingItemCDTO : cancelledBookingItemCDTOs) {
                CancelledBookingItem cancelledBookingItem = new CancelledBookingItem();
                cancelledBookingItem
                        .setCancellationResponseStatusCode(bookingItemCDTO.getCancellationResponseStatusCode());
                cancelledBookingItem
                        .setCancellationResponseDescription(bookingItemCDTO.getCancellationResponseDescription());
                cancelledBookingItem.setItemId(bookingItemCDTO.getItemId());
                cancelledBookingItem.setItemRef(bookingItemCDTO.getItemRef());
                cancelledBookingItems.add(cancelledBookingItem);
            }
        }
        return cancelledBookingItems;
    }

    public static CancelBooking toCancelBooking(Booking booking) {
        CancelBooking cancelBooking = new CancelBooking();
        cancelBooking.setBookingId(booking.getId());
        cancelBooking.setCartId(booking.getCartId());
        cancelBooking.setCustomerId(booking.getCustomerId());
        cancelBooking.setPartnerId(booking.getPartnerId());
        cancelBooking.setBookingReference(booking.getBookingReference());
        cancelBooking.setCreatedTime(Instant.now());
        return cancelBooking;
    }

    public static CancelBookingTransaction toCancelBookingTransaction(CancelBookingRQ cancelBookingRequest,
            boolean isManualCancel) {
        CancelBookingTransaction cancelBookingTransaction = new CancelBookingTransaction();
        cancelBookingTransaction.setTransactionId(UUID.randomUUID().toString());
        cancelBookingTransaction.setState(CancelBookingState.BOOKING_CANCEL);
        cancelBookingTransaction.setExtendedAttributes(cancelBookingRequest.getExtendedAttributes());
        cancelBookingTransaction.setCreatedTime(Instant.now());
        cancelBookingTransaction.setCancellationReasonCode(cancelBookingRequest.getCancellationReasonCode());
        cancelBookingTransaction.setManualCancel(isManualCancel);
        return cancelBookingTransaction;
    }

    public static CreateBookingRS toCreateBookingRS(Booking booking) {

        CreateBookingRS createBookingRS = new CreateBookingRS();
        createBookingRS.setBookingId(booking.getId());
        createBookingRS.setBookingReference(booking.getBookingReference());
        createBookingRS.setCartId(booking.getCartId());
        createBookingRS.setPartnerId(booking.getPartnerId());
        createBookingRS.setCustomerId(booking.getCustomerId());
        createBookingRS.setStatus(booking.getBookingStatus());

        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : booking.getPayments()) {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentId(payment.getPaymentId());
            paymentDTO.setCurrencyCode(payment.getCurrencyCode());
            paymentDTO.setExpiryDate(payment.getExpiryDate());
            paymentDTO.setHolderName(payment.getHolderName());
            paymentDTO.setLast4CardNumber(payment.getLast4CardNumber());
            paymentDTO.setCardType(payment.getCardType());
            paymentDTO.setPaymentAmount(payment.getPaymentAmount());
            paymentDTO.setPaymentType(payment.getPaymentType().toString());
            paymentDTOs.add(paymentDTO);
        }
        createBookingRS.setPayment(paymentDTOs);
        
        if (booking.getTotal() != null) {
            TotalDTO totalDTO = toDiscountTotalDTO(booking.getTotal());
            createBookingRS.setTotal(totalDTO);
        }

        if (!booking.getFees().isEmpty()) {
            for (Fee fee : booking.getFees()) {
                FeeDTO feeDTO = toFeeDTO(fee);
                createBookingRS.getFees().add(feeDTO);
            }
        }

        if (!booking.getDiscounts().isEmpty()) {
            for (Discount discount : booking.getDiscounts()) {
                DiscountDTO discountDTO = toDiscountDTO(discount);
                createBookingRS.getDiscounts().add(discountDTO);
            }
        }

        BookingSummaryDTO bookingSummaryDTO = new BookingSummaryDTO();
        //Conditional check will be removed after the data migration
        if(booking.getBookingSummary().getBookingStatus() != null){
        	bookingSummaryDTO.setBookingStatus(booking.getBookingSummary().getBookingStatus());
        }
        bookingSummaryDTO.setExtendedAttributes(booking.getBookingSummary().getExtendedAttributes());
        bookingSummaryDTO.setOverallGoodStanding(booking.getBookingSummary().isOverallGoodStanding());
        bookingSummaryDTO
                .setOverallGoodStandingDescription(booking.getBookingSummary().getOverallGoodStandingDescription());

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setStatus(booking.getBookingSummary().getOverallStatus().getStatus().name());
        statusDTO.setExternalStatus(booking.getBookingSummary().getExtendedAttributes());
        bookingSummaryDTO.setOverallStatus(statusDTO);
        createBookingRS.setBookingSummary(bookingSummaryDTO);
        
        return createBookingRS;
    }    

    public static CancelBookingRS toCancelBookingRS(CancelBooking cancelBooking,
            CancelBookingTransaction cancelBookingTransaction) {

        CancelBookingRS cancelBookingRS = new CancelBookingRS();
        cancelBookingRS.setPartnerId(cancelBooking.getPartnerId());
        cancelBookingRS.setBookingId(cancelBooking.getBookingId());
        cancelBookingRS.setCartId(cancelBooking.getCartId());
        cancelBookingRS.setCustomerId(cancelBooking.getCustomerId());
        cancelBookingRS.setCancellationFee(cancelBookingTransaction.getCancellationFee());
        cancelBookingRS.setStatus(cancelBookingTransaction.getCancelStatus());
        return cancelBookingRS;
    }

    public static Payment toSalePayment(Booking booking, CreateBookingRQ bookingRQ, PartnerConfig partnerConfig) {
        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setPaymentAmount(booking.getTotal().getFinalTotal());
        payment.setCurrencyCode(booking.getTotal().getCurrencyCode());
        payment.setPaymentToken(bookingRQ.getPayment().getTokenizedNumber());
        payment.setStatementDescriptor(
                StringUtils
                        .trimString(
                                partnerConfig.getPartnerName() + " - " + booking.getBookingOptions().get(0)
                                        .getProductDetails().getTitle().replaceAll(STRIPE_STATEMENT_DESC_REGEX, ""),
                                20));
        payment.setPaymentType(PaymentType.SALE);

        payment.setPaymentGatewayName(partnerConfig.getPaymentGateway());
        payment.setCreatedTime(Instant.now());
        return payment;
    }

    public static Refund toRefund(Payment salePayment, String refundReason, float refundAmount) {
        Refund refundPayment = new Refund();
        refundPayment.setRefundId(UUID.randomUUID().toString());
        refundPayment.setRefundAmount(refundAmount);
        refundPayment.setCurrencyCode(salePayment.getCurrencyCode());
        refundPayment.setPaymentGatewayName(salePayment.getPaymentGatewayName());
        refundPayment.setRefundReason(refundReason);
        refundPayment.setOriginalPayment(salePayment);
        refundPayment.setCreatedTime(Instant.now());
        return refundPayment;
    }

    public static ReversalDetails toReversalDetails(ReversalDetailsDTO reversalDetailsDTO) {
        ReversalDetails reversalDetails = new ReversalDetails();
        reversalDetails.setReversalTxId(reversalDetailsDTO.getReversalTxId());
        reversalDetails.setReversalSuccessful(reversalDetailsDTO.isReversalSuccessful());
        reversalDetails
                .setReversalStatus(PaymentStatus.getPaymentStatus(reversalDetailsDTO.getReversalStatus().name()));
        reversalDetails.setReversalReason(reversalDetailsDTO.getReversalReason());
        reversalDetails.setExtReversalTxId(reversalDetailsDTO.getExtReversalTxRefId());
        reversalDetails.setExternalStatuses(reversalDetailsDTO.getExternalStatuses());
        reversalDetails.setAttemptedTime(reversalDetailsDTO.getAttemptedTime());
        reversalDetails.setAdditionalAttributes(reversalDetailsDTO.getAdditionalAttributes());
        return reversalDetails;
    }

    public static GetBookingRS toGetBookingRS(Booking booking, VoucherPropertyService voucherPropertyService) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        GetBookingRS bookingRS = new GetBookingRS();

        GetBookingDTO bookingDTO = new GetBookingDTO();
        bookingDTO.setBookingId(booking.getId());
        bookingDTO.setPartnerId(booking.getPartnerId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setExtendedAttributes(booking.getExtendedAttributes());
        bookingDTO.setBookingReference(booking.getBookingReference());

        if (booking.getLoyaltyAccount() != null) {
            bookingDTO.setLoyaltyAccount(toLoyaltyAcountDTO(booking.getLoyaltyAccount()));
        }

        Date careatedDateTime = Date.from(booking.getCreatedTime());
        String formattedCreatedDate = formatter.format(careatedDateTime);
        bookingDTO.setCreatedTime(formattedCreatedDate);

        if (booking.getUpdatedTime() != null) {
            Date updatedDateTime = Date.from(booking.getUpdatedTime());
            String formattedUpdatedDate = formatter.format(updatedDateTime);
            bookingDTO.setUpdatedTime(formattedUpdatedDate);
        }
        bookingDTO.setBookerDetails(toBookerDTO(booking.getBookerDetails()));
        bookingDTO.setTotal(toDiscountTotalDTO(booking.getTotal()));

        if (!booking.getFees().isEmpty()) {
            for (Fee fee : booking.getFees()) {
                FeeDTO feeDTO = toFeeDTO(fee);
                bookingDTO.getFees().add(feeDTO);
            }
        }

        if (!booking.getDiscounts().isEmpty()) {
            for (Discount discount : booking.getDiscounts()) {
                DiscountDTO discountDTO = toDiscountDTO(discount);
                bookingDTO.getDiscounts().add(discountDTO);
            }
        }

        BookingSummaryDTO bookingSummaryDTO = new BookingSummaryDTO();
        //Conditional check will be removed after the data migration
        if(booking.getBookingSummary().getBookingStatus() != null){
        	bookingSummaryDTO.setBookingStatus(booking.getBookingSummary().getBookingStatus());
        }
        bookingSummaryDTO.setCancelBookingId(booking.getBookingSummary().getCancelBookingId());
        bookingSummaryDTO.setCancellationFee(booking.getBookingSummary().getCancellationFee());
        bookingSummaryDTO.setCancelSuccessful(booking.getBookingSummary().isCancelSuccessful());
        bookingSummaryDTO.setCancelTriggerred(booking.getBookingSummary().isCancelTriggerred());
        bookingSummaryDTO.setCancellationReasonCode(booking.getBookingSummary().getCancellationReasonCode());
        bookingSummaryDTO.setExtendedAttributes(booking.getBookingSummary().getExtendedAttributes());
        bookingSummaryDTO.setOverallGoodStanding(booking.getBookingSummary().isOverallGoodStanding());
        bookingSummaryDTO
                .setOverallGoodStandingDescription(booking.getBookingSummary().getOverallGoodStandingDescription());

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setStatus(booking.getBookingSummary().getOverallStatus().getStatus().name());
        statusDTO.setExternalStatus(booking.getBookingSummary().getExtendedAttributes());

        bookingSummaryDTO.setOverallStatus(statusDTO);
        bookingSummaryDTO.setRefundAmount(booking.getBookingSummary().getRefundAmount());
        if (booking.getBookingSummary().getRefundMode() != null) {
            bookingSummaryDTO.setRefundMode(booking.getBookingSummary().getRefundMode().name());
        }
        bookingDTO.setBookingSummary(bookingSummaryDTO);

        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : booking.getPayments()) {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentId(payment.getPaymentId());
            paymentDTO.setCurrencyCode(payment.getCurrencyCode());
            paymentDTO.setExpiryDate(payment.getExpiryDate());
            paymentDTO.setHolderName(payment.getHolderName());
            paymentDTO.setLast4CardNumber(payment.getLast4CardNumber());
            paymentDTO.setCardType(payment.getCardType());
            paymentDTO.setPaymentAmount(payment.getPaymentAmount());
            paymentDTO.setPaymentType(payment.getPaymentType().toString());
            paymentDTOs.add(paymentDTO);
        }
        bookingDTO.setPayment(paymentDTOs);

        List<BookingOptionDTO> bookingOptions = new ArrayList<>();
        for (BookingOption bookingOption : booking.getBookingOptions()) {
            BookingOptionDTO bookingOptionDTO = toBookingOptionDTOWithVoucher(bookingOption);

            bookingOptions.add(bookingOptionDTO);
        }
        bookingDTO.setBookingOptions(bookingOptions);

        bookingRS.setBooking(bookingDTO);

        return bookingRS;
    }
    
    public static com.placepass.booking.application.booking.admin.dto.GetBookingRS toAdminGetBookingRS(Booking booking, VoucherPropertyService voucherPropertyService) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        com.placepass.booking.application.booking.admin.dto.GetBookingRS bookingRS = new com.placepass.booking.application.booking.admin.dto.GetBookingRS();

        com.placepass.booking.application.booking.admin.dto.GetBookingDTO bookingDTO = new com.placepass.booking.application.booking.admin.dto.GetBookingDTO();
        bookingDTO.setBookingId(booking.getId());
        bookingDTO.setPartnerId(booking.getPartnerId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setExtendedAttributes(booking.getExtendedAttributes());
        bookingDTO.setBookingReference(booking.getBookingReference());

        if (booking.getLoyaltyAccount() != null) {
            bookingDTO.setLoyaltyAccount(toLoyaltyAcountDTO(booking.getLoyaltyAccount()));
        }

        Date careatedDateTime = Date.from(booking.getCreatedTime());
        String formattedCreatedDate = formatter.format(careatedDateTime);
        bookingDTO.setCreatedTime(formattedCreatedDate);

        if (booking.getUpdatedTime() != null) {
            Date updatedDateTime = Date.from(booking.getUpdatedTime());
            String formattedUpdatedDate = formatter.format(updatedDateTime);
            bookingDTO.setUpdatedTime(formattedUpdatedDate);
        }
        bookingDTO.setBookerDetails(toBookerDTO(booking.getBookerDetails()));
        bookingDTO.setTotal(toDiscountTotalDTO(booking.getTotal()));

        BookingSummaryDTO bookingSummaryDTO = new BookingSummaryDTO();
        //Conditional check will be removed after the data migration
        if(booking.getBookingSummary().getBookingStatus() != null){
        	bookingSummaryDTO.setBookingStatus(booking.getBookingSummary().getBookingStatus());
        }
        bookingSummaryDTO.setCancelBookingId(booking.getBookingSummary().getCancelBookingId());
        bookingSummaryDTO.setCancellationFee(booking.getBookingSummary().getCancellationFee());
        bookingSummaryDTO.setCancelSuccessful(booking.getBookingSummary().isCancelSuccessful());
        bookingSummaryDTO.setCancelTriggerred(booking.getBookingSummary().isCancelTriggerred());
        bookingSummaryDTO.setCancellationReasonCode(booking.getBookingSummary().getCancellationReasonCode());
        bookingSummaryDTO.setExtendedAttributes(booking.getBookingSummary().getExtendedAttributes());
        bookingSummaryDTO.setOverallGoodStanding(booking.getBookingSummary().isOverallGoodStanding());
        bookingSummaryDTO
                .setOverallGoodStandingDescription(booking.getBookingSummary().getOverallGoodStandingDescription());

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setStatus(booking.getBookingSummary().getOverallStatus().getStatus().name());
        statusDTO.setExternalStatus(booking.getBookingSummary().getExtendedAttributes());

        bookingSummaryDTO.setOverallStatus(statusDTO);
        bookingSummaryDTO.setRefundAmount(booking.getBookingSummary().getRefundAmount());
        if (booking.getBookingSummary().getRefundMode() != null) {
            bookingSummaryDTO.setRefundMode(booking.getBookingSummary().getRefundMode().name());
        }
        bookingDTO.setBookingSummary(bookingSummaryDTO);

        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : booking.getPayments()) {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentId(payment.getPaymentId());
            paymentDTO.setCurrencyCode(payment.getCurrencyCode());
            paymentDTO.setExpiryDate(payment.getExpiryDate());
            paymentDTO.setHolderName(payment.getHolderName());
            paymentDTO.setLast4CardNumber(payment.getLast4CardNumber());
            paymentDTO.setCardType(payment.getCardType());
            paymentDTO.setPaymentAmount(payment.getPaymentAmount());
            paymentDTO.setPaymentType(payment.getPaymentType().toString());
            paymentDTOs.add(paymentDTO);
        }
        bookingDTO.setPayment(paymentDTOs);

        List<BookingOptionDTO> bookingOptions = new ArrayList<>();
        for (BookingOption bookingOption : booking.getBookingOptions()) {
            BookingOptionDTO bookingOptionDTO = toBookingOptionDTOWithVoucher(bookingOption);

            bookingOptions.add(bookingOptionDTO);
        }
        bookingDTO.setBookingOptions(bookingOptions);

        bookingRS.setBooking(bookingDTO);
        
        if (booking.getBookingEvents() != null){
            
        	List<BookingEventDTO>  bookingEventDTOs = new ArrayList<BookingEventDTO>();

        	for (BookingEvent bookingEvent : booking.getBookingEvents()){
        		
        		BookingEventDTO bookingEventDTO = new BookingEventDTO();
        		bookingEventDTO.setId(bookingEvent.getId());
        		bookingEventDTO.setSpanId(String.format("%05X", bookingEvent.getSpanId()).toLowerCase());
        		bookingEventDTO.setTraceId(String.format("%05X", bookingEvent.getTraceId()).toLowerCase());
        		bookingEventDTO.setIndex(bookingEvent.getIndex());
        		bookingEventDTO.setEventName(bookingEvent.getEventName());
        		bookingEventDTO.setExtendedAttributes(bookingEvent.getExtendedAttributes());
        		
        		Date bookingEventCreatedTime = Date.from(bookingEvent.getCreatedTime());
        		bookingEventDTO.setCreatedTime(formatter.format(bookingEventCreatedTime));
        		
        		if (bookingEvent.getUpdatedTime() != null){
            		Date bookingEventUpdatedTime = Date.from(bookingEvent.getUpdatedTime());
        			bookingEventDTO.setUpdatedTime(formatter.format(bookingEventUpdatedTime));
        		}
        		
        		if (bookingEvent.getStatus() != null){
        			com.placepass.booking.application.booking.admin.dto.StatusDTO eventStatusDTO = new com.placepass.booking.application.booking.admin.dto.StatusDTO();
        			eventStatusDTO.setConnectorStatus(bookingEvent.getStatus().getConnectorStatus());
        			eventStatusDTO.setExternalStatus(bookingEvent.getStatus().getExternalStatus());
        			eventStatusDTO.setStatus(bookingEvent.getStatus().getStatus());
        			bookingEventDTO.setStatus(eventStatusDTO);
        		}
        		
        		if (bookingEvent.getManualInterventionDetail() != null){
        			ManualInterventionDetailDTO manualInterventionDetailDTO = new ManualInterventionDetailDTO();
        			manualInterventionDetailDTO.setManualInterventionRequired(bookingEvent.getManualInterventionDetail().isManualInterventionRequired());
        			manualInterventionDetailDTO.setReasonForManualIntervention(bookingEvent.getManualInterventionDetail().getReasonForManualIntervention());
        			manualInterventionDetailDTO.setResolved(bookingEvent.getManualInterventionDetail().isResolved());
        			manualInterventionDetailDTO.setResolutionText(bookingEvent.getManualInterventionDetail().getResolutionText());
        			bookingEventDTO.setManualInterventionDetail(manualInterventionDetailDTO);
        		}
        		
        		bookingEventDTOs.add(bookingEventDTO);
        		
        	}
        	
        	bookingDTO.setBookingEvents(bookingEventDTOs);
            
        }

        return bookingRS;
    }

    public static BookingOptionDTO toBookingOptionDTOWithVoucher(BookingOption bookingOption) {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
        bookingOptionDTO = toBookingOptionDTO(bookingOption);

        if (bookingOption.getVoucher() != null && bookingOption.getVoucher().getId() != null) {
            bookingOptionDTO.setVoucher(VoucherDTOTransformer.toVoucherDTO(bookingOption.getVoucher()));
        }
        return bookingOptionDTO;
    }

    public static BookingOptionDTO toBookingOptionDTO(BookingOption bookingOption) {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
        bookingOptionDTO.setTitle(bookingOption.getTitle());
        bookingOptionDTO.setDescription(bookingOption.getDescription());
        bookingOptionDTO.setProductId(bookingOption.getProductId());
        bookingOptionDTO.setProductOptionId(bookingOption.getProductOptionId());
        bookingOptionDTO.setBookingDate(bookingOption.getBookingDate().toString());
        bookingOptionDTO.setStartTime(bookingOption.getStartTime());
        bookingOptionDTO.setEndTime(bookingOption.getEndTime());
        bookingOptionDTO.setTotal(toTotalDTO(bookingOption.getTotal()));

        List<QuantityDTO> quantities = new ArrayList<>();
        for (Quantity quantity : bookingOption.getQuantities()) {
            quantities.add(toQuantityDTO(quantity));
        }
        bookingOptionDTO.setQuantities(quantities);

        List<TravelerDTO> travelers = new ArrayList<>();
        for (Traveler traveler : bookingOption.getTraverlerDetails()) {
            travelers.add(toTravelerDTO(traveler));
        }
        bookingOptionDTO.setTraverlerDetails(travelers);

        if (bookingOption.getPickupLocation() != null) {
            PickupLocationDTO pickupLocationDTO = new PickupLocationDTO();
            pickupLocationDTO.setId(bookingOption.getPickupLocation().getId());
            pickupLocationDTO.setLocationName(bookingOption.getPickupLocation().getLocationName());

            bookingOptionDTO.setPickupLocation(pickupLocationDTO);
        }
        bookingOptionDTO.setIsHotelPickUpEligible(bookingOption.getIsHotelPickUpEligible());
        bookingOptionDTO.setLanguageOptionCode(bookingOption.getLanguageOptionCode());
        bookingOptionDTO.setProductDetails(toProductDetails(bookingOption.getProductDetails()));
        return bookingOptionDTO;
    }

    public static GetBookingsHistoryRS toGetBookingsHistoryRS(Page<Booking> bookingsPage,
            VoucherPropertyService voucherPropertyService) {

        GetBookingsHistoryRS bookingsHistory = new GetBookingsHistoryRS();

        List<GetBookingDTO> bookingHistoryDetails = new ArrayList<>();

        for (Booking booking : bookingsPage.getContent()) {

            bookingHistoryDetails.add(toGetBookingRS(booking, voucherPropertyService).getBooking());
        }

        bookingsHistory.setTotalRecords(bookingsPage.getTotalElements());
        bookingsHistory.setTotalPages(bookingsPage.getTotalPages());
        bookingsHistory.setBookingsHistory(bookingHistoryDetails);

        return bookingsHistory;
    }

    public static FindBookingsRS toFindBookingsRS(Booking booking, VoucherPropertyService voucherPropertyService) {

        FindBookingsRS findBookingsRS = new FindBookingsRS();

        findBookingsRS.setBooking(toGetBookingRS(booking, voucherPropertyService).getBooking());

        return findBookingsRS;
    }

    public static SearchBookingsRS toSearchBookingsRS(Page<Booking> bookingsPage,
            VoucherPropertyService voucherPropertyService) {

        SearchBookingsRS searchBookingsRS = new SearchBookingsRS();

        List<com.placepass.booking.application.booking.admin.dto.GetBookingDTO> getBookingDTOList = new ArrayList<>();

        for (Booking booking : bookingsPage.getContent()) {

            getBookingDTOList.add(toAdminGetBookingRS(booking, voucherPropertyService).getBooking());
        }

        searchBookingsRS.setBookings(getBookingDTOList);
        searchBookingsRS.setTotalRecords(bookingsPage.getTotalElements());
        searchBookingsRS.setTotalPages(bookingsPage.getTotalPages());

        return searchBookingsRS;
    }

    private static TotalDTO toTotalDTO(Total total) {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setCurrency(total.getCurrency());
        totalDTO.setCurrencyCode(total.getCurrencyCode());
        totalDTO.setFinalTotal(total.getFinalTotal());
        totalDTO.setRoundedFinalTotal(AmountFormatter.floatToFloatRoundingFinalTotal(total.getFinalTotal()));
        totalDTO.setRetailTotal(total.getRetailTotal());
        return totalDTO;
    }

    private static BookerDTO toBookerDTO(Booker booker) {
        BookerDTO bookerDTO = new BookerDTO();
        bookerDTO.setTitle(booker.getTitle());
        bookerDTO.setCountryISOCode(booker.getCountryISOCode());
        bookerDTO.setEmail(booker.getEmail());
        bookerDTO.setFirstName(booker.getFirstName());
        bookerDTO.setLastName(booker.getLastName());
        bookerDTO.setPhoneNumber(booker.getPhoneNumber());
        bookerDTO.setDateOfBirth(booker.getDateOfBirth());
        return bookerDTO;
    }

    private static QuantityDTO toQuantityDTO(Quantity quantity) {
        QuantityDTO quantityDTO = new QuantityDTO();
        quantityDTO.setAgeBandId(quantity.getAgeBandId());
        quantityDTO.setAgeBandLabel(quantity.getAgeBandLabel());
        quantityDTO.setQuantity(quantity.getQuantity());
        return quantityDTO;
    }

    private static TravelerDTO toTravelerDTO(Traveler traveler) {
        TravelerDTO travelerDTO = new TravelerDTO();
        travelerDTO.setTitle(traveler.getTitle());
        travelerDTO.setFirstName(traveler.getFirstName());
        travelerDTO.setLastName(traveler.getLastName());
        travelerDTO.setEmail(traveler.getEmail());
        travelerDTO.setDateOfBirth(traveler.getDateOfBirth());
        travelerDTO.setPhoneNumber(traveler.getPhoneNumber());
        travelerDTO.setCountryISOCode(traveler.getCountryISOCode());
        travelerDTO.setLeadTraveler(traveler.isLeadTraveler());
        travelerDTO.setAgeBandId(traveler.getAgeBandId());
        return travelerDTO;
    }

    /**
     * @deprecated To loyalty account.
     *
     * @param bookingOptions the booking options
     * @param loyaltyAccountDTO the loyalty account DTO
     * @param loyaltyProgramConfig the loyalty program config
     * @return the loyalty account
     */
    public static LoyaltyAccount toLoyaltyAccount(List<BookingOption> bookingOptions,
            LoyaltyAccountDTO loyaltyAccountDTO, LoyaltyProgramConfig loyaltyProgramConfig) {

        LoyaltyAccount loyaltyAccount = new LoyaltyAccount();

        int loyaltyPoints = 0;

        for (BookingOption bookingOption : bookingOptions) {

            loyaltyPoints = loyaltyPoints + LoyaltyDetailsUtill.calculateLoyaltyPoints(
                    loyaltyProgramConfig.getPointsAwardRatio(), bookingOption.getTotal().getFinalTotal());

        }

        loyaltyAccount.setLoyaltyAccountId(loyaltyAccountDTO.getLoyaltyAccountId());
        loyaltyAccount.setLoyaltyPoints(loyaltyPoints);
        loyaltyAccount.setLoyaltyProgrameId(loyaltyAccountDTO.getLoyaltyProgramId());

        return loyaltyAccount;
    }
    
    public static LoyaltyAccount toLoyaltyAccount(Cart cart,
            LoyaltyAccountDTO loyaltyAccountDTO, LoyaltyProgramConfig loyaltyProgramConfig) {

        LoyaltyAccount loyaltyAccount = new LoyaltyAccount();

        float finalTotal = cart.getTotal().getFinalTotal();
        if (cart.getFees() != null) {
            for (Fee fee : cart.getFees()) {
                finalTotal -= fee.getAmount();
            }
        }

        int loyaltyPoints = LoyaltyDetailsUtill.calculateLoyaltyPoints(
                    loyaltyProgramConfig.getPointsAwardRatio(), finalTotal);

        loyaltyAccount.setLoyaltyAccountId(loyaltyAccountDTO.getLoyaltyAccountId());
        loyaltyAccount.setLoyaltyPoints(loyaltyPoints);
        loyaltyAccount.setLoyaltyProgrameId(loyaltyAccountDTO.getLoyaltyProgramId());

        return loyaltyAccount;
    }

    public static LoyaltyAccountDTO toLoyaltyAcountDTO(LoyaltyAccount loyaltyAccount) {

        LoyaltyAccountDTO loyaltyAccountDTO = new LoyaltyAccountDTO();

        loyaltyAccountDTO.setLoyaltyAccountId(loyaltyAccount.getLoyaltyAccountId());
        loyaltyAccountDTO.setLoyaltyPoints(loyaltyAccount.getLoyaltyPoints());
        loyaltyAccountDTO.setLoyaltyProgramId(loyaltyAccount.getLoyaltyProgrameId());

        return loyaltyAccountDTO;
    }

    public static ProductDetailsDTO toProductDetails(ProductDetails productDetails) {

        ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();

        if (productDetails.getCancellationPolicy() != null) {
            productDetailsDTO.setCancellationPolicy(productDetails.getCancellationPolicy());
        }

        if (productDetails.getCancellationRules() != null) {
            productDetailsDTO.setCancellationRules(toCancellationPolicyRules(productDetails.getCancellationRules()));
        }

        return productDetailsDTO;
    }

    private static CancellationRulesDTO toCancellationPolicyRules(CancellationRules cancellationRules) {

        CancellationRulesDTO cancellationRulesDTO = new CancellationRulesDTO();

        if (cancellationRules.getTags() != null) {
            cancellationRulesDTO.setTags(cancellationRules.getTags());
        }

        if (!cancellationRules.getRules().isEmpty()) {
            cancellationRulesDTO.setRules(toRules(cancellationRules.getRules()));
        }

        return cancellationRulesDTO;
    }

    private static List<RulesDTO> toRules(List<Rules> rules) {

        List<RulesDTO> rulesDTOs = new ArrayList<>();

        for (Rules rule : rules) {

            RulesDTO rulesDTO = new RulesDTO();

            rulesDTO.setMaxHoursInAdvance(rule.getMaxHoursInAdvance());
            rulesDTO.setMinHoursInAdvance(rule.getMinHoursInAdvance());
            rulesDTO.setRefundMultiplier(rule.getRefundMultiplier());

            rulesDTOs.add(rulesDTO);
        }

        return rulesDTOs;
    }

    public static Voucher toVoucher(com.placepass.connector.common.booking.Voucher voucerDetails) {

        Voucher voucher = new Voucher();
        voucher.setVendorReference(voucerDetails.getReference());
        voucher.setUrls(voucerDetails.getUrls());
        voucher.setType(voucerDetails.getType());
        voucher.setExtendedAttributes(voucerDetails.getExtendedAttributes());

        return voucher;
    }

    public static PendingBookingsRS toPendingBookingsRS(List<Booking> pageablePendingBookings) {
        PendingBookingsRS pendingBookingsRS = new PendingBookingsRS();
        List<PendingBookingDTO> pendingBookingList = new ArrayList<>();
        for (Booking booking : pageablePendingBookings) {
            pendingBookingList.add(toPendingBooking(booking));
        }
        pendingBookingsRS.setPendingBookings(pendingBookingList);
        return pendingBookingsRS;
    }

    public static PendingBookingDTO toPendingBooking(Booking booking) {
        PendingBookingDTO pendingBooking = new PendingBookingDTO();
        pendingBooking.setId(booking.getId());
        pendingBooking.setPartnerId(booking.getPartnerId());
        pendingBooking.setBookingReference(booking.getBookingReference());
        pendingBooking.setVendorBookingRefId(booking.getVendorBookingRefId());

        if (booking.getBookingSummary() != null && booking.getBookingSummary().getOverallStatus() != null
                && booking.getBookingSummary().getOverallStatus().getStatus() != null) {
            pendingBooking.setBookingStatus(booking.getBookingSummary().getOverallStatus().getStatus());
        } else {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_STATUS_NOT_FOUND_IN_BOOKING.toString(),
                    String.format(PlacePassExceptionCodes.BOOKING_STATUS_NOT_FOUND_IN_BOOKING.getDescription(),
                            booking.getId()));
        }

        if (booking.getBookingOptions() != null && booking.getBookingOptions().size() > 0) {
            pendingBooking.setVendor(booking.getBookingOptions().get(0).getVendor());
        } else {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND_IN_BOOKING.toString(),
                    String.format(PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND_IN_BOOKING.getDescription(),
                            booking.getId()));
        }

        if (booking.getBookerDetails() != null) {
            pendingBooking.setBookerEmail(booking.getBookerDetails().getEmail());
        } else {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKER_DETAIL_NOT_FOUND_IN_BOOKING.toString(),
                    String.format(PlacePassExceptionCodes.BOOKER_DETAIL_NOT_FOUND_IN_BOOKING.getDescription(),
                            booking.getId()));
        }

        return pendingBooking;
    }

    public static ProcessBookingStatusRS toGetBookingStatusRS(BookingStatusRS bookingStatusRS, String partnerId,
            String bookingId, String vendor, String vendorBookingRefId, String bookerEmail, String status) {
        ProcessBookingStatusRS getBookingStatusRS = new ProcessBookingStatusRS();

        ConnectorBookingStatus connectorBookingStatus = bookingStatusRS.getNewStatus();

        if (connectorBookingStatus != null) {

            switch (connectorBookingStatus) {

                case CONFIRMED:
                    getBookingStatusRS.setNewStatus(ConnectorBookingStatus.CONFIRMED);
                    break;
                case PENDING:
                    getBookingStatusRS.setNewStatus(ConnectorBookingStatus.PENDING);
                    break;
                case REJECTED:
                    getBookingStatusRS.setNewStatus(ConnectorBookingStatus.REJECTED);
                    break;
                case CANCELLED:
                    getBookingStatusRS.setNewStatus(ConnectorBookingStatus.CANCELLED);
                    break;
                case UNKNOWN:
                    getBookingStatusRS.setNewStatus(ConnectorBookingStatus.UNKNOWN);
                    break;
                default:
                    getBookingStatusRS.setNewStatus(ConnectorBookingStatus.FAILED);
                    break;

            }

        } else {
            getBookingStatusRS.setNewStatus(ConnectorBookingStatus.FAILED);
        }

        getBookingStatusRS.setBookingId(bookingId);
        getBookingStatusRS.setPartnerId(partnerId);
        getBookingStatusRS.setVendor(vendor);
        getBookingStatusRS.setVendorBookingRefId(vendorBookingRefId);
        getBookingStatusRS.setBookerEmail(bookerEmail);
        getBookingStatusRS.setStatus(status);
        String message = "";
        int index = 0;
        for (Map.Entry<String, String> entry : bookingStatusRS.getResultType().getExtendedAttributes().entrySet()) {
            message = message + entry.getKey() + " - " + entry.getValue();
            if (index != bookingStatusRS.getResultType().getExtendedAttributes().entrySet().size() - 1) {
                message = message + ", ";
            }
            index++;
        }
        getBookingStatusRS.setMessage(message);
        return getBookingStatusRS;
    }

    public static CancelBookingTransaction toManualCancelBookingTransaction(
            ManualCancelBookingRQ manualCancelBookingRequest, boolean isManualCancel) {

        CancelBookingTransaction cancelBookingTransaction = new CancelBookingTransaction();
        cancelBookingTransaction.setTransactionId(UUID.randomUUID().toString());
        cancelBookingTransaction.setState(CancelBookingState.PAYMENT_REFUND);
        cancelBookingTransaction.setExtendedAttributes(manualCancelBookingRequest.getExtendedAttributes());
        cancelBookingTransaction.setCreatedTime(Instant.now());
        cancelBookingTransaction.setCancellationReasonCode(manualCancelBookingRequest.getCancellationReasonCode());
        cancelBookingTransaction.setManualCancel(isManualCancel);
        return cancelBookingTransaction;
    }

    public static ManualCancelBookingRS toManualCancelBookingRS(CancelBooking cancelBooking,
            CancelBookingTransaction cancelBookingTransaction) {

        ManualCancelBookingRS manualCancelBookingRS = new ManualCancelBookingRS();
        manualCancelBookingRS.setPartnerId(cancelBooking.getPartnerId());
        manualCancelBookingRS.setBookingId(cancelBooking.getBookingId());
        manualCancelBookingRS.setCartId(cancelBooking.getCartId());
        manualCancelBookingRS.setCustomerId(cancelBooking.getCustomerId());
        manualCancelBookingRS.setCancellationFee(cancelBookingTransaction.getCancellationFee());
        manualCancelBookingRS.setStatus(cancelBookingTransaction.getCancelStatus());
        return manualCancelBookingRS;
    }
    
    private static DiscountDTO toDiscountDTO(Discount discount) {
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setDescription(discount.getDescription());
        discountDTO.setDiscountAmount(discount.getDiscountAmount());
        discountDTO.setDiscountCode(discount.getDiscountCode());
        discountDTO.setDiscountId(discount.getDiscountId());
        discountDTO.setDiscountStatus(discount.getDiscountStatus());
        discountDTO.setDiscountType(discount.getDiscountType());
        discountDTO.setDiscountValue(discount.getDiscountValue());
        discountDTO.setExpiryDate(discount.getExpiryDate());
        discountDTO.setTitle(discount.getTitle());
        return discountDTO;
    }

    private static FeeDTO toFeeDTO(Fee fee) {
        String feeType = null;
        String feeCategory = null;
        
        if (fee.getFeeType() != null) {
            feeType = fee.getFeeType().name();
        }

        if (fee.getFeeCategory() != null) {
            feeCategory = fee.getFeeCategory().name();
        }

        FeeDTO feeDTO = new FeeDTO(feeType, feeCategory, fee.getDescription(), fee.getCurrency(),
                fee.getAmount());
        return feeDTO;
    }

    private static TotalDTO toDiscountTotalDTO(Total total) {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setCurrency(total.getCurrency());
        totalDTO.setCurrencyCode(total.getCurrencyCode());
        totalDTO.setFinalTotal(total.getFinalTotal());
        totalDTO.setRetailTotal(total.getRetailTotal());
        totalDTO.setRoundedFinalTotal(total.getRoundedFinalTotal());
        totalDTO.setOriginalTotal(total.getOriginalTotal());
        totalDTO.setDiscountAmount(total.getDiscountAmount());
        totalDTO.setTotalAfterDiscount(total.getTotalAfterDiscount());
        return totalDTO;
    }
}
