package com.placepass.booking.domain.event;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.placepass.booking.application.cart.CartRequestTransformer;
import com.placepass.booking.application.cart.dto.PriceBreakDownDTO;
import com.placepass.booking.application.common.VoucherPropertyService;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.BookingEventService;
import com.placepass.booking.domain.booking.BookingEventTransformer;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.BookingRepository;
import com.placepass.booking.domain.booking.BookingService;
import com.placepass.booking.domain.booking.EventName;
import com.placepass.booking.domain.booking.Fee;
import com.placepass.booking.domain.booking.ManualInterventionDetail;
import com.placepass.booking.domain.booking.Payment;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.booking.domain.booking.RefundMode;
import com.placepass.booking.domain.booking.Traveler;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.CancelBookingRepository;
import com.placepass.booking.domain.booking.cancel.CancelBookingTransaction;
import com.placepass.booking.domain.config.LoyaltyProgramConfig;
import com.placepass.booking.domain.config.LoyaltyProgramConfigService;
import com.placepass.eventpublisher.application.EventPublisherService;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.event.PlatformEvent;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.event.PlatformEventName;
import com.placepass.utils.logging.PlatformLoggingKey;
import com.placepass.utils.vendorproduct.Vendor;

@Service
public class EventService {

    private static final String BOOKING_OPTION_ID = "BOOKING_OPTION_ID";

    private static final String EMPTY_STRING = "";

    private static final String SPACE = " ";

    private static final String ORDER_AMOUNT_FORMAT = "0.00";

    private static final String BOOKING_DATE_FORMAT = "EEE. MMM dd, yyyy";

    private static final String SHOW = "display:block;";

    private static final String HIDE = "display:none;";

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventPublisherService eventPublisher;

    @Value("${rabbitmq.platform.events.exchangename}")
    private String platformEventsExchangeName;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CancelBookingRepository cancelBookingRepository;

    @Autowired
    private LoyaltyProgramConfigService loyaltyProgramConfigService;

    @Autowired
    VoucherPropertyService voucherPropertyService;

    @Autowired
    private BookingEventService bookingEventService;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    /**
     * Publish booking confirmation event.
     *
     * @param bookingId the booking id
     * @param partnerId the partner id
     */
    @Async
    public void publishBookingConfirmationEvent(String bookingId, String partnerId, String receiverEmail) {

    	String emailAddress = null;
    	int bookingEventIndex = 0;
    	Booking booking = null;
    	
        try {

            logger.info("Booking confirmation event triggered for partnerId: {}, bookingId: {}", partnerId, bookingId);

            booking = bookingService.getBookingForUpdate(bookingId, partnerId);

            Assert.notNull(booking.getPartnerId(), "PartnerId cannot be null");
            Assert.notNull(booking.getCustomerId(), "CustomerId cannot be null");
            Assert.notNull(booking.getBookingReference(), "BookingReference cannot be null");
            Assert.notNull(booking.getBookerDetails(), "BookerDetails cannot be null");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getFirstName()),
                    "Booker firstName cannot be null or empty");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getLastName()),
                    "Booker lastName cannot be null or empty");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getEmail()),
                    "Booker email cannot be null or empty");
            Assert.notNull(booking.getTotal(), "Total cannot be null");
            Assert.notNull(booking.getTotal().getFinalTotal(), "FinalTotal cannot be null");
            Assert.notEmpty(booking.getBookingOptions(), "BookingOptions cannot be null or empty");

            Map<String, Object> logData = new HashMap<>();
            logData.put(PlatformLoggingKey.PARTNER_ID.name(), booking.getPartnerId());
            logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), booking.getCustomerId());
            logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), booking.getBookingReference());

            if (receiverEmail != null && StringUtils.hasText(receiverEmail)) {
                logData.put(PlatformEventKey.CUSTOMER_EMAIL.name(), receiverEmail);
            } else {
                logData.put(PlatformEventKey.CUSTOMER_EMAIL.name(), booking.getBookerDetails().getEmail());
            }

            logger.info("Preparing to fire booking confirmation event. {}", logData);

            PlatformEvent platformEvent = new PlatformEvent();
            platformEvent.setEventName(PlatformEventName.BOOKING_CONFIRMATION.name());
            
            bookingEventIndex = booking.getBookingEvents().size();
            
            Map<String, String> eventAttributes = new HashMap<>();
            if (receiverEmail != null && StringUtils.hasText(receiverEmail)) {
            	emailAddress = receiverEmail;
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), receiverEmail);
            } else {
            	emailAddress = booking.getBookerDetails().getEmail();
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), booking.getBookerDetails().getEmail());
            }

            eventAttributes.put(PlatformEventKey.CUSTOMER_FIRST_NAME.name(), booking.getBookerDetails().getFirstName());
            eventAttributes.put(PlatformEventKey.BOOKER_FULL_NAME.name(),
                    booking.getBookerDetails().getFirstName() + SPACE + booking.getBookerDetails().getLastName());
            eventAttributes.put(PlatformEventKey.ORDER_ID.name(), booking.getBookingReference());
            eventAttributes.put(PlatformEventKey.BOOKING_ID.name(), booking.getId());

            Payment payment = booking.getPayments().get(0);

            if (payment != null && StringUtils.hasText(payment.getLast4CardNumber())) {
                eventAttributes.put(PlatformEventKey.LAST_4_CARD_NUMBER.name(), payment.getLast4CardNumber());
            } else {
                eventAttributes.put(PlatformEventKey.LAST_4_CARD_NUMBER.name(), EMPTY_STRING);
                logger.warn("Last4 card number is null or empty. {}", logData);
            }

            String orderTotal = getFormattedPrice(booking.getTotal().getFinalTotal());
            eventAttributes.put(PlatformEventKey.ORDER_TOTAL.name(), orderTotal);
            
            float totalFee = 0.0f;
            if (booking.getFees() != null){
            	for(Fee fee : booking.getFees()){
            		totalFee = totalFee + fee.getAmount();
            	}
            }
            if (totalFee > 0.0f){
                eventAttributes.put(PlatformEventKey.FEE.name(), getFormattedPrice(totalFee));
                eventAttributes.put(PlatformEventKey.FEE_VISIBILITY.name(), SHOW);
            }else{
                eventAttributes.put(PlatformEventKey.FEE.name(), "0.00");
                eventAttributes.put(PlatformEventKey.FEE_VISIBILITY.name(), HIDE);
            }

            if (booking.getTotal() != null && booking.getTotal().getDiscountAmount() > 0){
            	eventAttributes.put(PlatformEventKey.DISCOUNT.name(), getFormattedPrice(booking.getTotal().getDiscountAmount()));
                eventAttributes.put(PlatformEventKey.DISCOUNT_VISIBILITY.name(), SHOW);
            }else{
            	eventAttributes.put(PlatformEventKey.DISCOUNT.name(), getFormattedPrice(booking.getTotal().getDiscountAmount()));
                eventAttributes.put(PlatformEventKey.DISCOUNT_VISIBILITY.name(), HIDE);
            }
            
            for (BookingOption bookingOption : booking.getBookingOptions()) {

                Assert.notNull(bookingOption, "BookingOption cannot be null");
                Assert.notNull(bookingOption.getBookingDate(), "BookingDate cannot be null");
                Assert.notEmpty(bookingOption.getTraverlerDetails(), "Traveler details cannot be null or empty");
                Assert.notEmpty(bookingOption.getQuantities(), "Quantities cannot be null or empty");

                // find the lead traveler
                Optional<Traveler> leadTraveler = bookingOption.getTraverlerDetails().stream()
                        .filter(tr -> tr.isLeadTraveler()).findFirst();

                Assert.isTrue(leadTraveler.isPresent(),
                        "Lead traveler is required in bookingOptionId: " + bookingOption.getBookingOptionId());

                eventAttributes.put(PlatformEventKey.LEAD_TRAVELLER_FULL_NAME.name(),
                        leadTraveler.get().getFirstName() + SPACE + leadTraveler.get().getLastName());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BOOKING_DATE_FORMAT);
                String bookingDate = formatter.format(bookingOption.getBookingDate());
                eventAttributes.put(PlatformEventKey.BOOKING_DATE.name(), bookingDate);

                if (StringUtils.hasText(bookingOption.getStartTime())) {
                    eventAttributes.put(PlatformEventKey.BOOKING_TIME.name(), bookingOption.getStartTime());
                    eventAttributes.put(PlatformEventKey.BOOKING_TIME_VISIBILITY.name(), SHOW);

                } else {
                    eventAttributes.put(PlatformEventKey.BOOKING_TIME.name(), EMPTY_STRING);
                    eventAttributes.put(PlatformEventKey.BOOKING_TIME_VISIBILITY.name(), HIDE);
                    logger.warn("Product start time is null or empty. {}", logData);
                }
                if (bookingOption.getProductDetails() != null
                        && StringUtils.hasText(bookingOption.getProductDetails().getTitle())) {

                    String productTitle = com.placepass.utils.common.StringUtils.getUTF8ConvertedContent(bookingOption.getProductDetails().getTitle());
                    eventAttributes.put(PlatformEventKey.PRODUCT_TITLE.name(),
                            productTitle);

                } else {
                    eventAttributes.put(PlatformEventKey.PRODUCT_TITLE.name(), EMPTY_STRING);
                    logger.warn("Product title is null or empty. {}", logData);
                }

                if (bookingOption.getProductDetails() != null
                        && bookingOption.getProductDetails().getWebLocation() != null
                        && bookingOption.getProductDetails().getWebLocation().getLocation() != null) {
                    eventAttributes.put(PlatformEventKey.BOOKING_LOCATION.name(),
                            bookingOption.getProductDetails().getWebLocation().getLocation());

                } else {
                    eventAttributes.put(PlatformEventKey.BOOKING_LOCATION.name(), EMPTY_STRING);
                    logger.warn("Product location is null or empty. {}", logData);
                }
                if (bookingOption.getProductDetails() != null && bookingOption.getProductDetails().getImages() != null
                        && !bookingOption.getProductDetails().getImages().isEmpty()) {
                    eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(),
                            bookingOption.getProductDetails().getImages().get(0));
                } else {
                    eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(), EMPTY_STRING);
                    logger.warn("Product image is null or empty. {}", logData);
                }

                if (bookingOption.getProductDetails() != null
                        && StringUtils.hasText(bookingOption.getProductDetails().getCancellationPolicy())) {
                    eventAttributes.put(PlatformEventKey.CANCELLATION_POLICY.name(),
                            bookingOption.getProductDetails().getCancellationPolicy());
                } else {
                    eventAttributes.put(PlatformEventKey.CANCELLATION_POLICY.name(), EMPTY_STRING);
                    logger.warn("Product cancellation policy is null or empty. {}", logData);
                }

                Vendor vendor = Vendor.getVendor(bookingOption.getVendor());
                eventAttributes.put(PlatformEventKey.VENDOR.name(), vendor.name());
                eventAttributes.put(PlatformEventKey.VENDOR_NAME.name(), vendor.getLabel());

                // find the count of seniors
                long seniorCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.SENIOR.name());
                // find the count of adults
                long adultCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.ADULT.name());
                // find the count of infants
                long infantCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.INFANT.name());
                // find the count of children
                long childCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.CHILD.name());
                // find the count of youth
                long youthCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.YOUTH.name());

                eventAttributes.put(PlatformEventKey.SENIOR_COUNT.name(), seniorCount + EMPTY_STRING);
                eventAttributes.put(PlatformEventKey.ADULT_COUNT.name(), adultCount + EMPTY_STRING);
                eventAttributes.put(PlatformEventKey.INFANT_COUNT.name(), infantCount + EMPTY_STRING);
                eventAttributes.put(PlatformEventKey.CHILD_COUNT.name(), childCount + EMPTY_STRING);
                eventAttributes.put(PlatformEventKey.YOUTH_COUNT.name(), youthCount + EMPTY_STRING);

                if (seniorCount > 0) {
                    eventAttributes.put(PlatformEventKey.SENIOR_VISIBILITY.name(), SHOW);
                } else {
                    eventAttributes.put(PlatformEventKey.SENIOR_VISIBILITY.name(), HIDE);
                }

                if (adultCount > 0) {
                    eventAttributes.put(PlatformEventKey.ADULT_VISIBILITY.name(), SHOW);
                } else {
                    eventAttributes.put(PlatformEventKey.ADULT_VISIBILITY.name(), HIDE);
                }

                if (infantCount > 0) {
                    eventAttributes.put(PlatformEventKey.INFANT_VISIBILITY.name(), SHOW);
                } else {
                    eventAttributes.put(PlatformEventKey.INFANT_VISIBILITY.name(), HIDE);
                }

                if (childCount > 0) {
                    eventAttributes.put(PlatformEventKey.CHILD_VISIBILITY.name(), SHOW);
                } else {
                    eventAttributes.put(PlatformEventKey.CHILD_VISIBILITY.name(), HIDE);
                }

                if (youthCount > 0) {
                    eventAttributes.put(PlatformEventKey.YOUTH_VISIBILITY.name(), SHOW);
                } else {
                    eventAttributes.put(PlatformEventKey.YOUTH_VISIBILITY.name(), HIDE);
                }

                List<Quantity> quantitiesForAgeBands = bookingOption.getQuantities();
                List<PriceBreakDownDTO> matchedPriceList = CartRequestTransformer
                        .getMatchedPriceList(bookingOption.getPrices(), bookingOption.getQuantities());

                for (Quantity qty : quantitiesForAgeBands) {

                    int ageBandId = qty.getAgeBandId();
                    for (PriceBreakDownDTO breakDownDTO : matchedPriceList) {
                        if (breakDownDTO.getPricePerAgeBand().getAgeBandId().intValue() == ageBandId
                                && ageBandId == PlacePassAgeBandType.ADULT.getAgeBandId()) {

                            eventAttributes.put(PlatformEventKey.ADULT_PRICE.name(),
                                    (getFormattedPrice(breakDownDTO.getPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);
                            eventAttributes.put(PlatformEventKey.ADULT_TOTAL.name(),
                                    (getFormattedPrice(breakDownDTO.getTotalPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);

                        } else if (breakDownDTO.getPricePerAgeBand().getAgeBandId().intValue() == ageBandId
                                && ageBandId == PlacePassAgeBandType.SENIOR.getAgeBandId()) {

                            eventAttributes.put(PlatformEventKey.SENIOR_PRICE.name(),
                                    (getFormattedPrice(breakDownDTO.getPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);
                            eventAttributes.put(PlatformEventKey.SENIOR_TOTAL.name(),
                                    (getFormattedPrice(breakDownDTO.getTotalPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);

                        } else if (breakDownDTO.getPricePerAgeBand().getAgeBandId().intValue() == ageBandId
                                && ageBandId == PlacePassAgeBandType.INFANT.getAgeBandId()) {

                            eventAttributes.put(PlatformEventKey.INFANT_PRICE.name(),
                                    (getFormattedPrice(breakDownDTO.getPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);
                            eventAttributes.put(PlatformEventKey.INFANT_TOTAL.name(),
                                    (getFormattedPrice(breakDownDTO.getTotalPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);

                        } else if (breakDownDTO.getPricePerAgeBand().getAgeBandId().intValue() == ageBandId
                                && ageBandId == PlacePassAgeBandType.CHILD.getAgeBandId()) {

                            eventAttributes.put(PlatformEventKey.CHILD_PRICE.name(),
                                    (getFormattedPrice(breakDownDTO.getPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);
                            eventAttributes.put(PlatformEventKey.CHILD_TOTAL.name(),
                                    (getFormattedPrice(breakDownDTO.getTotalPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);

                        } else if (breakDownDTO.getPricePerAgeBand().getAgeBandId().intValue() == ageBandId
                                && ageBandId == PlacePassAgeBandType.YOUTH.getAgeBandId()) {

                            eventAttributes.put(PlatformEventKey.YOUTH_PRICE.name(),
                                    (getFormattedPrice(breakDownDTO.getPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);
                            eventAttributes.put(PlatformEventKey.YOUTH_TOTAL.name(),
                                    (getFormattedPrice(breakDownDTO.getTotalPricePerAgeBand().getFinalPrice().floatValue())) + EMPTY_STRING);
                        }
                    }
                }

                LoyaltyProgramConfig loyaltyProgramConfigDetail = null;

                if (booking.getLoyaltyAccount() != null
                        && StringUtils.hasText(booking.getLoyaltyAccount().getLoyaltyProgrameId())) {
                    loyaltyProgramConfigDetail = loyaltyProgramConfigService.getLoyaltyProgramConfigDetail(
                            booking.getPartnerId(), booking.getLoyaltyAccount().getLoyaltyProgrameId());
                    eventAttributes.put(PlatformEventKey.LOYALTY_PROG_NAME.name(),
                            loyaltyProgramConfigDetail.getProgDisplayName());
                    eventAttributes.put(PlatformEventKey.LOYALTY_POINTS.name(),
                            booking.getLoyaltyAccount().getLoyaltyPoints() + EMPTY_STRING);
                    eventAttributes.put(PlatformEventKey.LOYALTY_VISIBILITY.name(), SHOW);

                } else {
                    eventAttributes.put(PlatformEventKey.LOYALTY_VISIBILITY.name(), HIDE);
                }

                if (bookingOption.getVoucher() != null && StringUtils.hasText(bookingOption.getVoucher().getId())) {
                    eventAttributes.put(PlatformEventKey.BOOKING_VOUCHER_URL.name(), voucherPropertyService
                            .populateVoucherUrl(partnerId, bookingOption.getVoucher().getId(), booking.getId(), booking
                                    .getBookerDetails().getEmail(), booking.getBookingReference()));
                } else {
                    eventAttributes.put(PlatformEventKey.BOOKING_VOUCHER_URL.name(), EMPTY_STRING);
                    logger.warn("Voucher URL is null or empty. {}", logData);
                }

                platformEvent.setEventAttributes(eventAttributes);

                logData.put(BOOKING_OPTION_ID, bookingOption.getBookingOptionId());
                logger.info("Start firing booking confirmation event. {}", logData);

                eventPublisher.sendEvent(booking.getPartnerId(), booking.getCustomerId(), booking.getId(),
                        "Booking", booking.getId(), platformEvent, platformEventsExchangeName);

                Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForConfirmationEmailProcessed(emailAddress, "SUCCESS", null);
                bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.CONFIRMATION_EMAIL_PROCESSED, extendedAttributes);
                bookingRepository.save(booking);
                
                logger.info("End firing booking confirmation event. {}", logData);
            }
        } catch (IllegalArgumentException e) {
        	
            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForConfirmationEmailProcessed(emailAddress, "ERROR", e.getMessage());
            ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, e.getMessage());
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.CONFIRMATION_EMAIL_PROCESSED, extendedAttributes, manualInterventionDetail);
            bookingRepository.save(booking);
        	
            logger.error("Required properties missing to fire booking confirmation event. ", e);

        } catch (Exception e) {
        	
            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForConfirmationEmailProcessed(emailAddress, "ERROR", e.getMessage());
            ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, e.getMessage());
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.CONFIRMATION_EMAIL_PROCESSED, extendedAttributes, manualInterventionDetail);
            bookingRepository.save(booking);
            
            logger.error("Exception occurred while firing booking confirmation event. ", e);

        }

    }

    /**
     * Publish booking pending event.
     *
     * @param bookingId the booking id
     * @param partnerId the partner id
     */
    @Async
    public void publishBookingPendingEvent(String bookingId, String partnerId, String receiverEmail) {

    	String emailAddress = null;
    	int bookingEventIndex = 0;
    	Booking booking = null;
    	
        try {

            logger.info("Booking pending event triggered for partnerId: {}, bookingId: {}", partnerId, bookingId);

            booking = bookingService.getBookingForUpdate(bookingId, partnerId);
            
            Assert.notNull(booking.getPartnerId(), "PartnerId cannot be null");
            Assert.notNull(booking.getBookingReference(), "BookingReference cannot be null");
            Assert.notNull(booking.getBookerDetails(), "BookerDetails cannot be null");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getFirstName()),
                    "Booker firstName cannot be null or empty");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getLastName()),
                    "Booker lastName cannot be null or empty");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getEmail()),
                    "Booker email cannot be null or empty");
            Assert.notEmpty(booking.getBookingOptions(), "BookingOptions cannot be null or empty");
            
            Map<String, Object> logData = new HashMap<>();
            logData.put(PlatformLoggingKey.PARTNER_ID.name(), booking.getPartnerId());
            logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), booking.getBookingReference());

            bookingEventIndex = booking.getBookingEvents().size();
            
            if (receiverEmail != null && StringUtils.hasText(receiverEmail)) {
                emailAddress = receiverEmail;
            	logData.put(PlatformEventKey.CUSTOMER_EMAIL.name(), receiverEmail);
            } else {
            	emailAddress = booking.getBookerDetails().getEmail();
                logData.put(PlatformEventKey.CUSTOMER_EMAIL.name(), booking.getBookerDetails().getEmail());
            }

            logger.info("Preparing to fire booking pending event. {}", logData);

            PlatformEvent platformEvent = new PlatformEvent();
            platformEvent.setEventName(PlatformEventName.BOOKING_PENDING.name());

            Map<String, String> eventAttributes = new HashMap<>();
            if (receiverEmail != null && StringUtils.hasText(receiverEmail)) {
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), receiverEmail);
            } else {
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), booking.getBookerDetails().getEmail());
            }

            eventAttributes.put(PlatformEventKey.BOOKER_FULL_NAME.name(),
                    booking.getBookerDetails().getFirstName() + SPACE + booking.getBookerDetails().getLastName());
            eventAttributes.put(PlatformEventKey.ORDER_ID.name(), booking.getBookingReference());

            for (BookingOption bookingOption : booking.getBookingOptions()) {

                Assert.notNull(bookingOption, "BookingOption cannot be null");
                Assert.notNull(bookingOption.getBookingDate(), "BookingDate cannot be null");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BOOKING_DATE_FORMAT);
                String bookingDate = formatter.format(bookingOption.getBookingDate());
                eventAttributes.put(PlatformEventKey.BOOKING_DATE.name(), bookingDate);

                if (StringUtils.hasText(bookingOption.getStartTime())) {
                    eventAttributes.put(PlatformEventKey.BOOKING_TIME.name(), bookingOption.getStartTime());
                    eventAttributes.put(PlatformEventKey.BOOKING_TIME_VISIBILITY.name(), SHOW);

                } else {
                    eventAttributes.put(PlatformEventKey.BOOKING_TIME.name(), EMPTY_STRING);
                    eventAttributes.put(PlatformEventKey.BOOKING_TIME_VISIBILITY.name(), HIDE);
                    logger.warn("Product start time is null or empty. {}", logData);
                }
                if (bookingOption.getProductDetails() != null
                        && StringUtils.hasText(bookingOption.getProductDetails().getTitle())) {

                    String productTitle = com.placepass.utils.common.StringUtils.getUTF8ConvertedContent(bookingOption.getProductDetails().getTitle());
                    eventAttributes.put(PlatformEventKey.PRODUCT_TITLE.name(),
                            productTitle);

                } else {
                    eventAttributes.put(PlatformEventKey.PRODUCT_TITLE.name(), EMPTY_STRING);
                    logger.warn("Product title is null or empty. {}", logData);
                }

                if (bookingOption.getProductDetails() != null
                        && bookingOption.getProductDetails().getWebLocation() != null
                        && bookingOption.getProductDetails().getWebLocation().getLocation() != null) {
                    eventAttributes.put(PlatformEventKey.BOOKING_LOCATION.name(),
                            bookingOption.getProductDetails().getWebLocation().getLocation());

                } else {
                    eventAttributes.put(PlatformEventKey.BOOKING_LOCATION.name(), EMPTY_STRING);
                    logger.warn("Product location is null or empty. {}", logData);
                }
                if (bookingOption.getProductDetails() != null && bookingOption.getProductDetails().getImages() != null
                        && !bookingOption.getProductDetails().getImages().isEmpty()) {
                    eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(),
                            bookingOption.getProductDetails().getImages().get(0));
                } else {
                    eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(), EMPTY_STRING);
                    logger.warn("Product image is null or empty. {}", logData);
                }

                if (bookingOption.getProductDetails() != null
                        && StringUtils.hasText(bookingOption.getProductDetails().getCancellationPolicy())) {
                    eventAttributes.put(PlatformEventKey.CANCELLATION_POLICY.name(),
                            bookingOption.getProductDetails().getCancellationPolicy());
                } else {
                    eventAttributes.put(PlatformEventKey.CANCELLATION_POLICY.name(), EMPTY_STRING);
                    logger.warn("Product cancellation policy is null or empty. {}", logData);
                }

                Vendor vendor = Vendor.getVendor(bookingOption.getVendor());
                eventAttributes.put(PlatformEventKey.VENDOR.name(), vendor.name());

                platformEvent.setEventAttributes(eventAttributes);

                logData.put(BOOKING_OPTION_ID, bookingOption.getBookingOptionId());
                logger.info("Start firing booking pending event. {}", logData);

                eventPublisher.sendEvent(booking.getPartnerId(), booking.getCustomerId(), booking.getId(),
                        "Booking", booking.getId(), platformEvent, platformEventsExchangeName);
                
                Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForPendingEmailProcessed(emailAddress, "SUCCESS", null);
                bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PENDING_EMAIL_PROCESSED, extendedAttributes);
                bookingRepository.save(booking);
                
                logger.info("End firing booking pending event. {}", logData);
            }
        } catch (IllegalArgumentException e) {

            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForPendingEmailProcessed(emailAddress, "ERROR", e.getMessage());
            ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, e.getMessage());
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PENDING_EMAIL_PROCESSED, extendedAttributes, manualInterventionDetail);
            bookingRepository.save(booking);
            
        	logger.error("Required properties missing to fire booking pending event. ", e);

        } catch (Exception e) {
        	
            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForPendingEmailProcessed(emailAddress, "ERROR", e.getMessage());
            ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, e.getMessage());
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.PENDING_EMAIL_PROCESSED, extendedAttributes, manualInterventionDetail);
            bookingRepository.save(booking);
            
            logger.error("Exception occurred while firing booking pending event. ", e);

        }

    }

    @Async
    public void publishBookingCancellationEvent(String partnerId, String bookingId, String cancelBookingId,
            String cancelBookingTransactionId, String receiverEmail) {

    	String emailAddress = null;
    	int bookingEventIndex = 0;
    	Booking booking = null;
    	String refundModeStr = null;
    	String refundAmount = "0";
    	
        try {
            logger.info(
                    "Booking cancellation event triggered for partnerId: {}, bookingId: {}, cancelBookingId: {}, cancelBookingTransactionId: {}",
                    partnerId, bookingId, cancelBookingId, cancelBookingTransactionId);

            booking = bookingService.getBookingForUpdate(bookingId, partnerId);

            Assert.notNull(booking.getPartnerId(), "PartnerId cannot be null");
            Assert.notNull(booking.getCustomerId(), "CustomerId cannot be null");
            Assert.notNull(booking.getBookingReference(), "BookingReference cannot be null");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getEmail()),
                    "Booker email cannot be null or empty");

            CancelBooking cancelBooking = cancelBookingRepository.findByPartnerIdAndId(partnerId, cancelBookingId);

            Assert.notNull(cancelBooking, "cancelBooking cannot be null");

            Optional<CancelBookingTransaction> cancelBookingTransactions = cancelBooking.getCancelBookingTransactions()
                    .stream().filter(cbt -> cancelBookingTransactionId.equals(cbt.getTransactionId())).findFirst();
            CancelBookingTransaction cancelBookingTransaction = null;
            if (cancelBookingTransactions.isPresent()) {
                cancelBookingTransaction = cancelBookingTransactions.get();
            }

            bookingEventIndex = booking.getBookingEvents().size();
            
            Assert.notNull(cancelBookingTransaction, "cancelBookingTransaction cannot be null");

            Map<String, Object> logData = new HashMap<>();
            logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
            logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), bookingId);
            logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), booking.getBookingReference());

            RefundMode refundMode = cancelBookingTransaction.getRefundMode();
            Assert.notNull(refundMode, "refundMode cannot be null");

            Map<String, String> eventAttributes = new HashMap<>();
            PlatformEvent platformEvent = new PlatformEvent();
            
            refundModeStr = refundMode.name();

            if (RefundMode.FULL == refundMode) {
                platformEvent.setEventName(PlatformEventName.BOOKING_CANCELLED_FULL_REFUND.name());

                String orderTotal = getFormattedPrice(cancelBookingTransaction.getRefunds().get(0).getRefundAmount());
                eventAttributes.put(PlatformEventKey.REFUND_AMOUNT.name(), orderTotal);
                
                refundAmount = orderTotal;
                
            } else if (RefundMode.PARTIAL == refundMode) {
                platformEvent.setEventName(PlatformEventName.BOOKING_CANCELLED_PARTIAL_REFUND.name());

                String orderTotal = getFormattedPrice(cancelBookingTransaction.getRefunds().get(0).getRefundAmount());
                eventAttributes.put(PlatformEventKey.REFUND_AMOUNT.name(), orderTotal);
                
                refundAmount = orderTotal;
                
            } else {
                // RefundMode.NONE
                platformEvent.setEventName(PlatformEventName.BOOKING_CANCELLED_NO_REFUND.name());
                eventAttributes.put(PlatformEventKey.REFUND_AMOUNT.name(), "0");
            }

            BookingOption bookingOption = booking.getBookingOptions().get(0);

            Assert.notNull(bookingOption, "BookingOption cannot be null");
            Assert.notNull(bookingOption.getBookingDate(), "BookingDate cannot be null");
            Assert.notEmpty(bookingOption.getTraverlerDetails(), "Traveler details cannot be null or empty");
            Assert.notEmpty(bookingOption.getQuantities(), "Quantities cannot be null or empty");

            // find the lead traveler
            Optional<Traveler> leadTraveler = bookingOption.getTraverlerDetails().stream()
                    .filter(tr -> tr.isLeadTraveler()).findFirst();

            Assert.isTrue(leadTraveler.isPresent(),
                    "Lead traveler is required in bookingOptionId: " + bookingOption.getBookingOptionId());

            eventAttributes.put(PlatformEventKey.LEAD_TRAVELLER_FULL_NAME.name(),
                    leadTraveler.get().getFirstName() + SPACE + leadTraveler.get().getLastName());

            eventAttributes.put(PlatformEventKey.BOOKER_FULL_NAME.name(),
                    booking.getBookerDetails().getFirstName() + SPACE + booking.getBookerDetails().getLastName());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BOOKING_DATE_FORMAT);
            String bookingDate = formatter.format(bookingOption.getBookingDate());
            eventAttributes.put(PlatformEventKey.BOOKING_DATE.name(), bookingDate);

            if (StringUtils.hasText(bookingOption.getStartTime())) {
                eventAttributes.put(PlatformEventKey.BOOKING_TIME.name(), bookingOption.getStartTime());
                eventAttributes.put(PlatformEventKey.BOOKING_TIME_VISIBILITY.name(), SHOW);
            } else {
                eventAttributes.put(PlatformEventKey.BOOKING_TIME.name(), EMPTY_STRING);
                eventAttributes.put(PlatformEventKey.BOOKING_TIME_VISIBILITY.name(), HIDE);
                logger.warn("Product start time is null or empty. {}", logData);
            }
            if (bookingOption.getProductDetails() != null
                    && StringUtils.hasText(bookingOption.getProductDetails().getTitle())) {

                String productTitle = com.placepass.utils.common.StringUtils.getUTF8ConvertedContent(bookingOption.getProductDetails().getTitle());
                eventAttributes.put(PlatformEventKey.PRODUCT_TITLE.name(),
                        productTitle);

            } else {
                eventAttributes.put(PlatformEventKey.PRODUCT_TITLE.name(), EMPTY_STRING);
                logger.warn("Product title is null or empty. {}", logData);
            }

            if (bookingOption.getProductDetails() != null && bookingOption.getProductDetails().getWebLocation() != null
                    && bookingOption.getProductDetails().getWebLocation().getLocation() != null) {
                eventAttributes.put(PlatformEventKey.BOOKING_LOCATION.name(),
                        bookingOption.getProductDetails().getWebLocation().getLocation());

            } else {
                eventAttributes.put(PlatformEventKey.BOOKING_LOCATION.name(), EMPTY_STRING);
                logger.warn("Product location is null or empty. {}", logData);
            }
            if (bookingOption.getProductDetails() != null && bookingOption.getProductDetails().getImages() != null
                    && !bookingOption.getProductDetails().getImages().isEmpty()) {
                eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(),
                        bookingOption.getProductDetails().getImages().get(0));
            } else {
                eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(), EMPTY_STRING);
                logger.warn("Product image is null or empty. {}", logData);
            }

            if (bookingOption.getProductDetails() != null
                    && StringUtils.hasText(bookingOption.getProductDetails().getCancellationPolicy())) {
                eventAttributes.put(PlatformEventKey.CANCELLATION_POLICY.name(),
                        bookingOption.getProductDetails().getCancellationPolicy());
            } else {
                eventAttributes.put(PlatformEventKey.CANCELLATION_POLICY.name(), EMPTY_STRING);
                logger.warn("Product cancellation policy is null or empty. {}", logData);
            }

            Payment payment = booking.getPayments().get(0);

            if (payment != null && StringUtils.hasText(payment.getLast4CardNumber())) {
                eventAttributes.put(PlatformEventKey.LAST_4_CARD_NUMBER.name(), payment.getLast4CardNumber());
            } else {
                eventAttributes.put(PlatformEventKey.LAST_4_CARD_NUMBER.name(), EMPTY_STRING);
                logger.warn("Last4 card number is null or empty. {}", logData);
            }

            Vendor vendor = Vendor.getVendor(bookingOption.getVendor());
            eventAttributes.put(PlatformEventKey.VENDOR.name(), vendor.name());
            eventAttributes.put(PlatformEventKey.VENDOR_NAME.name(), vendor.getLabel());

            // find the count of seniors
            long seniorCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.SENIOR.name());
            // find the count of adults
            long adultCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.ADULT.name());
            // find the count of infants
            long infantCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.INFANT.name());
            // find the count of children
            long childCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.CHILD.name());
            // find the count of youth
            long youthCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.YOUTH.name());

            eventAttributes.put(PlatformEventKey.SENIOR_COUNT.name(), seniorCount + EMPTY_STRING);
            eventAttributes.put(PlatformEventKey.ADULT_COUNT.name(), adultCount + EMPTY_STRING);
            eventAttributes.put(PlatformEventKey.INFANT_COUNT.name(), infantCount + EMPTY_STRING);
            eventAttributes.put(PlatformEventKey.CHILD_COUNT.name(), childCount + EMPTY_STRING);
            eventAttributes.put(PlatformEventKey.YOUTH_COUNT.name(), youthCount + EMPTY_STRING);

            if (receiverEmail != null && StringUtils.hasText(receiverEmail)) {
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), receiverEmail);
                emailAddress = receiverEmail;
            } else {
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), booking.getBookerDetails().getEmail());
                emailAddress = booking.getBookerDetails().getEmail();
            }
            eventAttributes.put(PlatformEventKey.CUSTOMER_FIRST_NAME.name(), booking.getBookerDetails().getFirstName());
            eventAttributes.put(PlatformEventKey.ORDER_ID.name(), booking.getBookingReference());
            eventAttributes.put(PlatformEventKey.BOOKING_ID.name(), booking.getId());

            platformEvent.setEventAttributes(eventAttributes);
            logger.info("Start firing {} booking cancellation event with {} refund. {}", refundMode, logData);

            eventPublisher.sendEvent(booking.getPartnerId(), booking.getCustomerId(), cancelBooking.getId(),
                    "CancelBooking", cancelBookingTransaction.getTransactionId(), platformEvent,
                    platformEventsExchangeName);

            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForCancellationEmailProcessed(emailAddress, "SUCCESS", null, refundModeStr, refundAmount);
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.CANCELLATION_EMAIL_PROCESSED, extendedAttributes);
            bookingRepository.save(booking);

            logger.info("End firing booking cancellation event with {} refund. {}", refundMode, logData);

        } catch (IllegalArgumentException e) {
        	
            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForCancellationEmailProcessed(emailAddress, "ERROR", e.getMessage(), refundModeStr, refundAmount);
            ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, e.getMessage());
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.CANCELLATION_EMAIL_PROCESSED, extendedAttributes, manualInterventionDetail);
            bookingRepository.save(booking);
            
            logger.error("Required properties missing to fire booking cancellation event. ", e);

        } catch (Exception e) {
        	
            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForCancellationEmailProcessed(emailAddress, "ERROR", e.getMessage(), refundModeStr, refundAmount);
            ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, e.getMessage());
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.CANCELLATION_EMAIL_PROCESSED, extendedAttributes, manualInterventionDetail);
            bookingRepository.save(booking);
            
            logger.error("Exception occurred while firing booking cancellation event. ", e);

        }

    }

    @Async
    public void publishBookingRejectedEvent(String partnerId, String bookingId, String receiverEmail) {

    	String emailAddress = null;
    	int bookingEventIndex = 0;
    	Booking booking = null;
    	
        try {
            logger.info("Booking rejected event triggered for partnerId: {}, bookingId: {}", partnerId, bookingId);

            booking = bookingService.getBookingByIdAndPartnerIdForUpdate(bookingId, partnerId);

            Assert.notNull(booking.getPartnerId(), "PartnerId cannot be null");
            Assert.notNull(booking.getCustomerId(), "CustomerId cannot be null");
            Assert.notNull(booking.getBookingReference(), "BookingReference cannot be null");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getEmail()),
                    "Booker email cannot be null or empty");

            Map<String, Object> logData = new HashMap<>();
            logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
            logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), bookingId);
            logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), booking.getBookingReference());

            bookingEventIndex = booking.getBookingEvents().size();
            
            Map<String, String> eventAttributes = new HashMap<>();
            PlatformEvent platformEvent = new PlatformEvent();

            platformEvent.setEventName(PlatformEventName.BOOKING_REJECTED.name());

            String orderTotal = getFormattedPrice(booking.getTotal().getFinalTotal());
            eventAttributes.put(PlatformEventKey.REFUND_AMOUNT.name(), orderTotal);

            BookingOption bookingOption = booking.getBookingOptions().get(0);

            Assert.notNull(bookingOption, "BookingOption cannot be null");
            Assert.notNull(bookingOption.getBookingDate(), "BookingDate cannot be null");
            Assert.notEmpty(bookingOption.getTraverlerDetails(), "Traveler details cannot be null or empty");
            Assert.notEmpty(bookingOption.getQuantities(), "Quantities cannot be null or empty");

            // find the lead traveler
            Optional<Traveler> leadTraveler = bookingOption.getTraverlerDetails().stream()
                    .filter(tr -> tr.isLeadTraveler()).findFirst();

            Assert.isTrue(leadTraveler.isPresent(),
                    "Lead traveler is required in bookingOptionId: " + bookingOption.getBookingOptionId());

            eventAttributes.put(PlatformEventKey.LEAD_TRAVELLER_FULL_NAME.name(),
                    leadTraveler.get().getFirstName() + SPACE + leadTraveler.get().getLastName());

            eventAttributes.put(PlatformEventKey.BOOKER_FULL_NAME.name(),
                    booking.getBookerDetails().getFirstName() + SPACE + booking.getBookerDetails().getLastName());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BOOKING_DATE_FORMAT);
            String bookingDate = formatter.format(bookingOption.getBookingDate());
            eventAttributes.put(PlatformEventKey.BOOKING_DATE.name(), bookingDate);

            if (StringUtils.hasText(bookingOption.getStartTime())) {
                eventAttributes.put(PlatformEventKey.BOOKING_TIME.name(), bookingOption.getStartTime());
                eventAttributes.put(PlatformEventKey.BOOKING_TIME_VISIBILITY.name(), SHOW);
            } else {
                eventAttributes.put(PlatformEventKey.BOOKING_TIME.name(), EMPTY_STRING);
                eventAttributes.put(PlatformEventKey.BOOKING_TIME_VISIBILITY.name(), HIDE);
                logger.warn("Product start time is null or empty. {}", logData);
            }
            if (bookingOption.getProductDetails() != null
                    && StringUtils.hasText(bookingOption.getProductDetails().getTitle())) {

                String productTitle = com.placepass.utils.common.StringUtils.getUTF8ConvertedContent(bookingOption.getProductDetails().getTitle());
                eventAttributes.put(PlatformEventKey.PRODUCT_TITLE.name(),
                        productTitle);

            } else {
                eventAttributes.put(PlatformEventKey.PRODUCT_TITLE.name(), EMPTY_STRING);
                logger.warn("Product title is null or empty. {}", logData);
            }

            if (bookingOption.getProductDetails() != null && bookingOption.getProductDetails().getWebLocation() != null
                    && bookingOption.getProductDetails().getWebLocation().getLocation() != null) {
                eventAttributes.put(PlatformEventKey.BOOKING_LOCATION.name(),
                        bookingOption.getProductDetails().getWebLocation().getLocation());

            } else {
                eventAttributes.put(PlatformEventKey.BOOKING_LOCATION.name(), EMPTY_STRING);
                logger.warn("Product location is null or empty. {}", logData);
            }
            if (bookingOption.getProductDetails() != null && bookingOption.getProductDetails().getImages() != null
                    && !bookingOption.getProductDetails().getImages().isEmpty()) {
                eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(),
                        bookingOption.getProductDetails().getImages().get(0));
            } else {
                eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(), EMPTY_STRING);
                logger.warn("Product image is null or empty. {}", logData);
            }

            Payment payment = booking.getPayments().get(0);

            if (payment != null && StringUtils.hasText(payment.getLast4CardNumber())) {
                eventAttributes.put(PlatformEventKey.LAST_4_CARD_NUMBER.name(), payment.getLast4CardNumber());
            } else {
                eventAttributes.put(PlatformEventKey.LAST_4_CARD_NUMBER.name(), EMPTY_STRING);
                logger.warn("Last4 card number is null or empty. {}", logData);
            }

            Vendor vendor = Vendor.getVendor(bookingOption.getVendor());
            eventAttributes.put(PlatformEventKey.VENDOR.name(), vendor.name());
            eventAttributes.put(PlatformEventKey.VENDOR_NAME.name(), vendor.getLabel());

            // find the count of seniors
            long seniorCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.SENIOR.name());
            // find the count of adults
            long adultCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.ADULT.name());
            // find the count of infants
            long infantCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.INFANT.name());
            // find the count of children
            long childCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.CHILD.name());
            // find the count of youth
            long youthCount = this.getCount(bookingOption.getQuantities(), PlacePassAgeBandType.YOUTH.name());

            eventAttributes.put(PlatformEventKey.SENIOR_COUNT.name(), seniorCount + EMPTY_STRING);
            eventAttributes.put(PlatformEventKey.ADULT_COUNT.name(), adultCount + EMPTY_STRING);
            eventAttributes.put(PlatformEventKey.INFANT_COUNT.name(), infantCount + EMPTY_STRING);
            eventAttributes.put(PlatformEventKey.CHILD_COUNT.name(), childCount + EMPTY_STRING);
            eventAttributes.put(PlatformEventKey.YOUTH_COUNT.name(), youthCount + EMPTY_STRING);

            if (receiverEmail != null && StringUtils.hasText(receiverEmail)) {
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), receiverEmail);
                emailAddress = receiverEmail;
            } else {
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), booking.getBookerDetails().getEmail());
                emailAddress = booking.getBookerDetails().getEmail();
            }
            eventAttributes.put(PlatformEventKey.CUSTOMER_FIRST_NAME.name(), booking.getBookerDetails().getFirstName());
            eventAttributes.put(PlatformEventKey.ORDER_ID.name(), booking.getBookingReference());
            eventAttributes.put(PlatformEventKey.BOOKING_ID.name(), booking.getId());

            platformEvent.setEventAttributes(eventAttributes);
            logger.info("Start firing {} booking rejected event. {}", logData);

            eventPublisher.sendEvent(booking.getPartnerId(), booking.getCustomerId(), booking.getId(),
                    "RejectedBooking", booking.getId(), platformEvent, platformEventsExchangeName);

            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForRejectedEmailProcessed(emailAddress, "SUCCESS", null);
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.REJECTED_EMAIL_PROCESSED, extendedAttributes);
            bookingRepository.save(booking);
            
            logger.info("End firing booking rejected event. {}", logData);

        } catch (IllegalArgumentException e) {
        	
            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForRejectedEmailProcessed(emailAddress, "ERROR", e.getMessage());
            ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, e.getMessage());
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.REJECTED_EMAIL_PROCESSED, extendedAttributes, manualInterventionDetail);
            bookingRepository.save(booking);
        	
            logger.error("Required properties missing to fire booking rejected event. ", e);

        } catch (Exception e) {
        	
            Map<String,String> extendedAttributes = BookingEventTransformer.constructExtendedAttributeForRejectedEmailProcessed(emailAddress, "ERROR", e.getMessage());
            ManualInterventionDetail manualInterventionDetail = new ManualInterventionDetail(true, e.getMessage());
            bookingEventService.addEventToBooking(booking, bookingEventIndex, EventName.REJECTED_EMAIL_PROCESSED, extendedAttributes, manualInterventionDetail);
            bookingRepository.save(booking);
            
            logger.error("Exception occurred while firing booking rejected event. ", e);

        }

    }

    @Async
    public void resendVoucherEvent(String bookingId, String partnerId, String receiverEmail) {

        try {

            logger.info("Resend voucher event triggered for partnerId: {}, bookingId: {}", partnerId, bookingId);

            Booking booking = bookingService.getBooking(bookingId, partnerId);

            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getFirstName()),
                    "Booker firstName cannot be null or empty");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getLastName()),
                    "Booker lastName cannot be null or empty");
            Assert.isTrue(StringUtils.hasText(booking.getBookerDetails().getEmail()),
                    "Booker email cannot be null or empty");
            Assert.notEmpty(booking.getBookingOptions(), "BookingOptions cannot be null or empty");

            Map<String, Object> logData = new HashMap<>();
            logData.put(PlatformLoggingKey.PARTNER_ID.name(), booking.getPartnerId());
            logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), booking.getCustomerId());
            logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), booking.getBookingReference());

            if (receiverEmail != null && StringUtils.hasText(receiverEmail)) {
                logData.put(PlatformLoggingKey.CUSTOMER_EMAIL.name(), receiverEmail);
            } else {
                logData.put(PlatformLoggingKey.CUSTOMER_EMAIL.name(), booking.getBookerDetails().getEmail());
            }

            logger.info("Preparing to fire resend voucher event. {}", logData);

            PlatformEvent platformEvent = new PlatformEvent();
            platformEvent.setEventName(PlatformEventName.RESEND_VOUCHER.name());

            Map<String, String> eventAttributes = new HashMap<>();
            if (receiverEmail != null && StringUtils.hasText(receiverEmail)) {
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), receiverEmail);
            } else {
                eventAttributes.put(PlatformEventKey.CUSTOMER_EMAIL.name(), booking.getBookerDetails().getEmail());
            }

            eventAttributes.put(PlatformEventKey.CUSTOMER_FIRST_NAME.name(), booking.getBookerDetails().getFirstName());
            eventAttributes.put(PlatformEventKey.BOOKER_FULL_NAME.name(),
                    booking.getBookerDetails().getFirstName() + SPACE + booking.getBookerDetails().getLastName());
            eventAttributes.put(PlatformEventKey.ORDER_ID.name(), booking.getBookingReference());
            eventAttributes.put(PlatformEventKey.BOOKING_ID.name(), booking.getId());

            for (BookingOption bookingOption : booking.getBookingOptions()) {

                if (bookingOption.getVoucher() != null && StringUtils.hasText(bookingOption.getVoucher().getId())) {
                    eventAttributes.put(PlatformEventKey.BOOKING_VOUCHER_URL.name(), voucherPropertyService
                            .populateVoucherUrl(partnerId, bookingOption.getVoucher().getId(), booking.getId(), booking
                                    .getBookerDetails().getEmail(), booking.getBookingReference()));
                } else {
                    eventAttributes.put(PlatformEventKey.BOOKING_VOUCHER_URL.name(), EMPTY_STRING);
                    logger.warn("Voucher URL is null or empty. {}", logData);
                }

                platformEvent.setEventAttributes(eventAttributes);

                eventPublisher.sendEvent(booking.getPartnerId(), booking.getCustomerId(), booking.getId(),
                        "Booking", booking.getId(), platformEvent, platformEventsExchangeName);

                logger.info("End firing resend voucher event. {}", logData);
            }

        } catch (IllegalArgumentException e) {
            logger.error("Required properties missing to fire resend voucher event. ", e);

        } catch (Exception e) {
            logger.error("Exception occurred while firing resend voucher event. ", e);

        }

    }

    /**
     * Gets the count by ageband label.
     *
     * @param quantities the quantities
     * @param ageBandLabel the age band label
     * @return the count
     */
	private long getCount(List<Quantity> quantities, String ageBandLabel) {

        long count = 0;
        Optional<Quantity> quantity = quantities.stream().filter(q -> ageBandLabel.equals(q.getAgeBandLabel()))
                .findFirst();
        if (quantity.isPresent()) {
            count = quantity.get().getQuantity();
        }
        return count;
    }

    /**
     * This method will formats price to two decimal places
     */
    private String getFormattedPrice(float price) {

        DecimalFormat decimalFormat = new DecimalFormat(ORDER_AMOUNT_FORMAT);
        return decimalFormat.format(price);
    }
}
