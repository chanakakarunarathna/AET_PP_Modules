package com.gobe.connector.application.booking;

import com.gobe.connector.application.util.GobeUtil;
import com.gobe.connector.application.util.VendorBookingStatus;
import com.gobe.connector.application.util.VendorErrorCode;
import com.gobe.connector.domain.gobe.book.*;
import com.gobe.connector.domain.gobe.price.GobePrice;
import com.gobe.connector.domain.gobe.product.GobeProduct;
import com.gobe.connector.domain.gobe.product.Variant;
import com.placepass.connector.common.booking.*;
//import com.placepass.connector.common.booking.*;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.Price;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.bookingstatus.ConnectorBookingStatus;
import com.placepass.utils.pricematch.PriceMatchPricePerAgeBand;
import com.placepass.utils.pricematch.PriceMatchQuantity;
import com.placepass.utils.pricematch.PriceMatchTotalPrice;
import com.placepass.utils.voucher.VoucherType;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.EnumUtils;

/**
 * Created on 8/11/2017.
 */
public class BookingTransformer {

    public static final String VENDOR_BOOKING_STATUS = "VENDOR_BOOKING_STATUS";

    public static GobeOrderRQ toBookingRequest(MakeBookingRQ makeBookingRQ, GobeProduct gobeProduct,
            List<GobePrice> gobePrices) {

        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());

        GobeOrderRQ gobeOrderRQ = new GobeOrderRQ();

        //Hardcode the PlacePass Address for billing address for Gobe
        gobeOrderRQ.setLine1("770 Massachusetts Avenue");
        gobeOrderRQ.setTown("Cambridge");
        gobeOrderRQ.setPostalCode("02139");
        gobeOrderRQ.setCountry("US");
        gobeOrderRQ.setRegion("US-MA");
        gobeOrderRQ.setStreetname("770 Massachusetts Avenue");

        //Default title code to Mr
        gobeOrderRQ.setTitleCode("mr");
        if (makeBookingRQ.getBookerDetails() != null && makeBookingRQ.getBookerDetails().getTitle() != null) {
            String titleCode = makeBookingRQ.getBookerDetails().getTitle().toLowerCase();
            if (titleCode.startsWith("ms")) {
                gobeOrderRQ.setTitleCode("ms");
            } else if (titleCode.startsWith("mrs")) {
                gobeOrderRQ.setTitleCode("mrs");
            } else {
                //Do nothing this will default to mr above
            }
        }

        //setting bookingId to Gobe reference number
        gobeOrderRQ.setReferenceID(makeBookingRQ.getBookingId());

        //Gobe said there should only be USD prices
        gobeOrderRQ.setCurrency("USD");
        gobeOrderRQ.setCreationDate(currentDateTime);

        //Email from booker
        String bookerPhoneNumber = "0000000000";
        String travelerName = "N/A";
        if (makeBookingRQ.getBookerDetails() != null) {
            if (makeBookingRQ.getBookerDetails().getPhoneNumber() != null) {
                bookerPhoneNumber = makeBookingRQ.getBookerDetails().getPhoneNumber();
            }
            if (makeBookingRQ.getBookerDetails().getFirstName() != null
                    && makeBookingRQ.getBookerDetails().getLastName() != null) {
                travelerName = makeBookingRQ.getBookerDetails().getFirstName() + " " + makeBookingRQ.getBookerDetails()
                        .getLastName();
            }
        }

        gobeOrderRQ.setEmail("placepass");
        gobeOrderRQ.setPhone(bookerPhoneNumber);
        gobeOrderRQ.setTravellerName(travelerName);

        ArrayList<OrderEntry> orderEntries = new ArrayList<>();
        ArrayList<TravelerDetail> travelerDetails = new ArrayList<>();

        //extract gobe order entries info from booking options
        if (makeBookingRQ.getBookingOptions() != null) {
            //total prices on all booking options
            Float totalPrice = 0f;
            for (BookingOption bookingOption : makeBookingRQ.getBookingOptions()) {
                if (bookingOption.getQuantities() != null) {
                    for (Quantity quantity : bookingOption.getQuantities()) {
                        OrderEntry orderEntry = new OrderEntry();

                        //initial base and total option price
                        Float basePrice = 0f;
                        Float totalOpitionPrice = 0f;

                        //get quantity
                        orderEntry.setQuantity(quantity.getQuantity());

                        //productId. Gobe need the variant id in this field instead of the productId
                        if (gobeProduct.getVariants() != null) {
                            for (Variant variant : gobeProduct.getVariants()) {
                                if (quantity.getAgeBandId() == PlacePassAgeBandType.ADULT.ageBandId && variant.getName()
                                        .toLowerCase().startsWith("adult")) {
                                    orderEntry.setProductId(variant.getCode());
                                    basePrice = GobeUtil
                                            .getGobePriceForQuantity(quantity.getQuantity(), variant.getCode(),
                                                    gobePrices);
                                    totalOpitionPrice = basePrice * quantity.getQuantity();
                                    break;
                                } else if (quantity.getAgeBandId() == PlacePassAgeBandType.CHILD.ageBandId && variant
                                        .getName().toLowerCase().startsWith("child")) {
                                    orderEntry.setProductId(variant.getCode());
                                    basePrice = GobeUtil
                                            .getGobePriceForQuantity(quantity.getQuantity(), variant.getCode(),
                                                    gobePrices);
                                    totalOpitionPrice = basePrice * quantity.getQuantity();
                                    break;
                                } else if (quantity.getAgeBandId() == PlacePassAgeBandType.YOUTH.ageBandId && variant
                                        .getName().toLowerCase().startsWith("youth")) {
                                    orderEntry.setProductId(variant.getCode());
                                    basePrice = GobeUtil
                                            .getGobePriceForQuantity(quantity.getQuantity(), variant.getCode(),
                                                    gobePrices);
                                    totalOpitionPrice = basePrice * quantity.getQuantity();
                                    break;
                                } else if (quantity.getAgeBandId() == PlacePassAgeBandType.INFANT.ageBandId && variant
                                        .getName().toLowerCase().startsWith("infant")) {
                                    orderEntry.setProductId(variant.getCode());
                                    basePrice = GobeUtil
                                            .getGobePriceForQuantity(quantity.getQuantity(), variant.getCode(),
                                                    gobePrices);
                                    totalOpitionPrice = basePrice * quantity.getQuantity();
                                    break;
                                } else if (quantity.getAgeBandId() == PlacePassAgeBandType.SENIOR.ageBandId && variant
                                        .getName().toLowerCase().startsWith("senior")) {
                                    orderEntry.setProductId(variant.getCode());
                                    basePrice = GobeUtil
                                            .getGobePriceForQuantity(quantity.getQuantity(), variant.getCode(),
                                                    gobePrices);
                                    totalOpitionPrice = basePrice * quantity.getQuantity();
                                    break;
                                } else if (quantity.getAgeBandId() == PlacePassAgeBandType.ADULT.ageBandId && variant
                                        .getName().toLowerCase().startsWith("traveler")) {
                                    orderEntry.setProductId(variant.getCode());
                                    basePrice = GobeUtil
                                            .getGobePriceForQuantity(quantity.getQuantity(), variant.getCode(),
                                                    gobePrices);
                                    totalOpitionPrice = basePrice * quantity.getQuantity();
                                    break;
                                } else if (quantity.getAgeBandId() == PlacePassAgeBandType.ADULT.ageBandId && variant
                                        .getName().toLowerCase().startsWith("guest")) {
                                    orderEntry.setProductId(variant.getCode());
                                    basePrice = GobeUtil
                                            .getGobePriceForQuantity(quantity.getQuantity(), variant.getCode(),
                                                    gobePrices);
                                    totalOpitionPrice = basePrice * quantity.getQuantity();
                                    break;
                                } else {
                                    //cases for ones that isn't follow ours modal
                                    //For now we just return nothing and fail the ordering request

                                }
                            }
                        }

                        //Assuming user checked for restrictions before ordering
                        orderEntry.setRestrictionsChecked("true");
                        orderEntry.setRestrictionsCheckedTime(currentDateTime);

                        //unit for product is pieces as in the doc
                        orderEntry.setUnit("pieces");

                        //get tour schedule and time
                        if (bookingOption.getBookingDate() != null) {
                            orderEntry.setTourSchedule(
                                    bookingOption.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            //productOptionId contain time and date, we get the date from the front end and time from the id
                            String productOptionId = bookingOption.getVendorProductOptionId();
                            orderEntry.setStartTime(productOptionId
                                    .substring(productOptionId.length() - 4, productOptionId.length() - 2) + ":"
                                    + productOptionId
                                    .substring(productOptionId.length() - 2, productOptionId.length()));
                        }

                        //pickup location
                        orderEntry.setPickupLocation("");
                        List<BookingAnswer> ppBookingAnswers = bookingOption.getBookingAnswers();
                        if (ppBookingAnswers != null) {
                            for (BookingAnswer ppBookingAnswer : ppBookingAnswers) {
                                //1 is the id of pickup location question
                                if (ppBookingAnswer.getQuestionId().equals("1")) {
                                    orderEntry.setPickupLocation(ppBookingAnswer.getAnswer());
                                }
                            }
                        }

                        //TODO make sure these are correct
                        //drop off time
                        orderEntry.setDropOfTime("");
                        //set need guide to false
                        orderEntry.setNeedGuide("false");

                        //Setting to 0 base on the conversation with Gobe
                        //the front end only send the total
                        orderEntry.setBasePrice(basePrice);
                        orderEntry.setTotalPrice(totalOpitionPrice);

                        orderEntries.add(orderEntry);
                    }
                }

                //Adding all Total price of each bookingOptions
                if (bookingOption.getTotal() != null) {
                    //using FinalTotal here
                    totalPrice += bookingOption.getTotal().getFinalTotal();
                }

                //Traveller Details for this booking option
                TravelerDetail travelerDetail = new TravelerDetail();
                travelerDetail.setGroupId(bookingOption.getVendorProductOptionId());
                List<TravelerDetailsForGroup> travelerDetailsForGroups = new ArrayList<>();

                if (gobeProduct.getTravelerDetailsType() != null) {
                    String travelerDetailsType = gobeProduct.getTravelerDetailsType();

                    if (bookingOption.getTraverlerDetails() != null) {

                        //place the first traveller in first
                        //Assumming there is always a traveller
                        for (Traveler traveler : bookingOption.getTraverlerDetails()) {
                            if (traveler.isLeadTraveler()) {
                                TravelerDetailsForGroup travelerDetailsForGroup = new TravelerDetailsForGroup();
                                if (travelerDetailsType.equals("LEAD_TRAVELLER_NAME") || travelerDetailsType
                                        .equals("ALL_GUESTS_INFO") || travelerDetailsType
                                        .equals("NO_INFO")) {
                                    travelerDetailsForGroup.setFirstName(traveler.getFirstName());
                                    travelerDetailsForGroup.setMiddleName("");
                                    travelerDetailsForGroup.setLastName(traveler.getLastName());

                                } else if (travelerDetailsType.equals("LEAD_TRAVELLER_NAME_EMAIL_PHONE")) {
                                    travelerDetailsForGroup.setFirstName(traveler.getFirstName());
                                    travelerDetailsForGroup.setMiddleName("");
                                    travelerDetailsForGroup.setLastName(traveler.getLastName());
                                    travelerDetailsForGroup.setEmail(traveler.getEmail());
                                    travelerDetailsForGroup.setPhone(
                                            (traveler.getPhoneNumber() != null ? traveler.getPhoneNumber() :
                                                    "0000000000"));

                                    //Assuming the preferred contact method is email
                                    travelerDetailsForGroup.setPreferredContactMethod("email");

                                } else if (travelerDetailsType.equals("ALL_GUESTS_INFO_PASSPORT")) {
                                    //fake data for now
                                    //TODO make sure this is not fake data
                                    Passport passport = new Passport();
                                    passport.setCountry("US");
                                    passport.setExpiration("2020-01-01");
                                    passport.setNumber("12345");
                                    travelerDetailsForGroup.setPassport(passport);

                                    travelerDetailsForGroup.setFirstName(traveler.getFirstName());
                                    travelerDetailsForGroup.setMiddleName("");
                                    travelerDetailsForGroup.setLastName(traveler.getLastName());

                                } else if (travelerDetailsType.equals("ALL_GUESTS_WEIGHT_AGE")) {
                                    travelerDetailsForGroup.setEmail(traveler.getEmail());
                                    travelerDetailsForGroup.setPhone(
                                            (traveler.getPhoneNumber() != null ? traveler.getPhoneNumber() :
                                                    "0000000000"));
                                    travelerDetailsForGroup.setFirstName(traveler.getFirstName());
                                    travelerDetailsForGroup.setMiddleName("");
                                    travelerDetailsForGroup.setLastName(traveler.getLastName());

                                    //Assuming the preferred contact method is email
                                    travelerDetailsForGroup.setPreferredContactMethod("email");
                                    //fake data
                                    travelerDetailsForGroup.setAge(0);
                                    travelerDetailsForGroup.setWeight(0);
                                } else {
                                    //no info needed
                                    travelerDetailsForGroup.setFirstName(traveler.getFirstName());
                                    travelerDetailsForGroup.setMiddleName("");
                                    travelerDetailsForGroup.setLastName(traveler.getLastName());
                                }
                                travelerDetailsForGroups.add(travelerDetailsForGroup);
                            }
                        }

                        for (Traveler traveler : bookingOption.getTraverlerDetails()) {
                            if (!traveler.isLeadTraveler()) {
                                TravelerDetailsForGroup travelerDetailsForGroup = new TravelerDetailsForGroup();

                                if (travelerDetailsType.equals("ALL_GUESTS_INFO")) {

                                    travelerDetailsForGroup.setFirstName(traveler.getFirstName());
                                    travelerDetailsForGroup.setMiddleName("");
                                    travelerDetailsForGroup.setLastName(traveler.getLastName());

                                } else if (travelerDetailsType.equals("ALL_GUESTS_INFO_PASSPORT")) {
                                    travelerDetailsForGroup.setFirstName(traveler.getFirstName());
                                    travelerDetailsForGroup.setMiddleName("");
                                    travelerDetailsForGroup.setLastName(traveler.getLastName());

                                    //TODO fake data for now
                                    Passport passport = new Passport();
                                    passport.setCountry("US");
                                    passport.setExpiration("2020-01-01");
                                    passport.setNumber("12345");

                                    travelerDetailsForGroup.setPassport(passport);

                                } else if (travelerDetailsType.equals("ALL_GUESTS_WEIGHT_AGE")) {
                                    travelerDetailsForGroup.setFirstName(traveler.getFirstName());
                                    travelerDetailsForGroup.setMiddleName("");
                                    travelerDetailsForGroup.setLastName(traveler.getLastName());

                                    //fake data
                                    travelerDetailsForGroup.setAge(0);
                                    travelerDetailsForGroup.setWeight(0);
                                } else {
                                    //No Info needed
                                }
                                travelerDetailsForGroups.add(travelerDetailsForGroup);
                            }
                        }
                    }

                    //TODO: parsing booking questions and answers correctly
                    if (bookingOption.getBookingAnswers() != null) {
                        for (BookingAnswer bookingAnswer : bookingOption.getBookingAnswers()) {
                        }
                    }
                }

                travelerDetail.setTravelerDetailsForGroup(travelerDetailsForGroups);
                travelerDetails.add(travelerDetail);
            }

            gobeOrderRQ.setSubTotal(totalPrice);
            gobeOrderRQ.setOrderEntries(orderEntries);
            gobeOrderRQ.setTravelerDetails(travelerDetails);
            gobeOrderRQ.setTotalPrice(totalPrice);
            gobeOrderRQ.setTotalDiscount(0f);
            gobeOrderRQ.setTotalTax(0f);

        }

        return gobeOrderRQ;
    }

    public static MakeBookingRS toBookingResponse(GobeOrderRS gobeOrderRS) {
        MakeBookingRS makeBookingRS = new MakeBookingRS();
        ResultType resultType = new ResultType();

        if (gobeOrderRS == null) {
            //booking fail
            resultType.setCode(112);
            resultType.setMessage("Null Order Response from Gobe");
            makeBookingRS.setResultType(resultType);
            return makeBookingRS;
        }

        //TODO inspect more status: Order split,...
        if (gobeOrderRS.getTtOrderStatus() != null) {
            resultType.setCode(GobeUtil.mappingGobeStatus(gobeOrderRS.getTtOrderStatus()));
        } else {
            //booking fail
            resultType.setCode(112);
        }

        resultType.setMessage(gobeOrderRS.getTtOrderStatus());
        makeBookingRS.setReferenceNumber(gobeOrderRS.getTtOrderNumber());

        makeBookingRS.setResultType(resultType);
        return makeBookingRS;
    }

    public static List<PriceMatchPricePerAgeBand> toPriceMatchPricePerAgeBandToPriceMatch(List<Price> inputPriceList) {

        List<PriceMatchPricePerAgeBand> priceMatchPricePerAgeBands = new ArrayList<>();

        for (Price price : inputPriceList) {

            PriceMatchPricePerAgeBand priceMatchPricePerAgeBand = new PriceMatchPricePerAgeBand();

            priceMatchPricePerAgeBand.setAgeBandId(price.getAgeBandId());
            priceMatchPricePerAgeBand.setAgeFrom(price.getAgeFrom());
            priceMatchPricePerAgeBand.setAgeTo(price.getAgeTo());
            priceMatchPricePerAgeBand.setCurrencyCode(price.getCurrencyCode());
            priceMatchPricePerAgeBand.setDescription(price.getDescription());
            priceMatchPricePerAgeBand.setFinalPrice(price.getFinalPrice());
            priceMatchPricePerAgeBand.setMaxBuy(price.getMaxBuy());
            priceMatchPricePerAgeBand.setMinBuy(price.getMinBuy());
            priceMatchPricePerAgeBand.setPriceGroupSortOrder(price.getPriceGroupSortOrder());
            priceMatchPricePerAgeBand.setPriceType(price.getPriceType());
            priceMatchPricePerAgeBand.setRetailPrice(price.getRetailPrice());

            priceMatchPricePerAgeBands.add(priceMatchPricePerAgeBand);

        }
        return priceMatchPricePerAgeBands;
    }

    public static List<PriceMatchQuantity> toQuantitiesToPriceMatch(List<Quantity> quantities) {

        List<PriceMatchQuantity> priceMatchQuantities = new ArrayList<>();

        for (Quantity quantity : quantities) {

            PriceMatchQuantity priceMatchQuantity = new PriceMatchQuantity();

            priceMatchQuantity.setAgeBandId(quantity.getAgeBandId());
            priceMatchQuantity.setAgeBandLabel(quantity.getAgeBandLabel());
            priceMatchQuantity.setQuantity(quantity.getQuantity());

            priceMatchQuantities.add(priceMatchQuantity);
        }

        return priceMatchQuantities;
    }

    public static GetProductPriceRS toPricingResponse(PriceMatchTotalPrice priceMatchTotalPrice) {

        GetProductPriceRS getProductPriceRS = new GetProductPriceRS();
        ResultType resultType = new ResultType();
        resultType.setCode(0);

        Total ppTotal = new Total();
        ppTotal.setCurrencyCode(priceMatchTotalPrice.getCurrencyCode());
        ppTotal.setCurrency(priceMatchTotalPrice.getCurrencyCode());
        ppTotal.setFinalTotal(priceMatchTotalPrice.getFinalTotal());
        ppTotal.setRetailTotal(priceMatchTotalPrice.getRetailTotal());
        ppTotal.setMerchantTotal(priceMatchTotalPrice.getMerchantTotal());

        getProductPriceRS.setTotal(ppTotal);
        getProductPriceRS.setResultType(resultType);
        return getProductPriceRS;
    }

    public static GobeCancelOrderRQ toCancelBookingRequest(CancelBookingRQ cancelBookingRQ) {

        GobeCancelOrderRQ gobeCancelBookingRequest = new GobeCancelOrderRQ();

        gobeCancelBookingRequest.setOrderId(cancelBookingRQ.getBookingReferenceId());
        gobeCancelBookingRequest.setFull(true);
        gobeCancelBookingRequest.setCancellationDetails(new ArrayList<>());

        return gobeCancelBookingRequest;
    }

    public static CancelBookingRS toCancelBookingResponse(GobeCancelOrderRS gobeCancelOrderRS) {

        CancelBookingRS cancelBookingRS = new CancelBookingRS();
        List<CancelledBookingItem> cancelledBookedItemsList = new ArrayList<>();
        ResultType resultType = new ResultType();

        //TODO check for Gobe fail canncellation
        //Gobe only return the amount and cancellationId
        if (gobeCancelOrderRS.getCancellationId() != null && gobeCancelOrderRS.getRefundedAmout() != 0) {
            //assuming no zero dollar tour
            // turn ok - pending for now
            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
            cancelBookingRS.setBookingReferenceNo(gobeCancelOrderRS.getCancellationId());
        } else {
            //cannot cancel
            resultType.setCode(VendorErrorCode.CANCEL_UNKNOWN.getId());
            resultType.setMessage(VendorErrorCode.CANCEL_UNKNOWN.getMsg());
        }
        cancelBookingRS.setCancelledBookingItems(cancelledBookedItemsList);
        cancelBookingRS.setResultType(resultType);
        return cancelBookingRS;
    }

    public static BookingVoucherRS toVoucherResponse(GobeOrderStatusRS gobeOrderStatusRS) {

        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();
        ResultType resultType = new ResultType();

        if (gobeOrderStatusRS != null) {

            Voucher voucher = new Voucher();

            if (gobeOrderStatusRS.getBookings().get(0).getPrintTicketURL() != null) {

                voucher.setReference(gobeOrderStatusRS.getBookings().get(0).getConfirmationNumber());
                voucher.setType(VoucherType.SINGLE_URL);

                List<String> urls = new ArrayList<String>();
                urls.add(gobeOrderStatusRS.getBookings().get(0).getPrintTicketURL());
                voucher.setUrls(urls);

            } else {
                voucher.setType(VoucherType.NO_VOUCHER);
            }

            if (gobeOrderStatusRS.getOrderStatus() != null) {

                Map<String, String> extendedAttributes = new HashMap<String, String>();
                extendedAttributes.put(VENDOR_BOOKING_STATUS, gobeOrderStatusRS.getOrderStatus());
                voucher.setExtendedAttributes(extendedAttributes);
            }

            bookingVoucherRS.setVoucher(voucher);

            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } else {

            resultType.setCode(VendorErrorCode.VENDOR_RETURN_EMPTY_RESPONSE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_RETURN_EMPTY_RESPONSE.getMsg());
            bookingVoucherRS.setResultType(resultType);
        }

        return bookingVoucherRS;

    }

    public static GetBookingQuestionsRS toBookingQuestions(GobeProduct gobeProduct) {

        GetBookingQuestionsRS getBookingQuestionsRS = new GetBookingQuestionsRS();
        ResultType resultType = new ResultType();
        List<BookingQuestion> bookingQuestions = new ArrayList<>();

        if (gobeProduct != null && gobeProduct.getHideCheckoutWherePickup() != null && gobeProduct.getHideCheckoutWherePickup()
                .equals("false")) {

            BookingQuestion bookingQuestion = new BookingQuestion();
            bookingQuestion.setMessage("Please provide a pickup location");
            //For now assuming 1 is pickup location question
            bookingQuestion.setQuestionId(1);
            bookingQuestion.setRequired(true);
            bookingQuestion.setStringQuestionId("1");
            bookingQuestion.setSubTitle("");
            bookingQuestion.setTitle("Pickup location");
            bookingQuestions.add(bookingQuestion);
            resultType.setCode(0);
            resultType.setMessage("");

        } else {
            resultType.setCode(1);
            resultType.setMessage("Booking Questions are not available for this product.");
        }

        getBookingQuestionsRS.setBookingQuestions(bookingQuestions);
        getBookingQuestionsRS.setResultType(resultType);
        return getBookingQuestionsRS;
    }
    
    public static BookingStatusRS toBookingStatusResponse(GobeOrderStatusRS gobeOrderStatusRS) {
    	
    	BookingStatusRS bookingStatusRS = new BookingStatusRS();
		ResultType resultType = new ResultType();
		
		Map<String, String> extendedAttributes = new HashMap<String, String>();
		
        if (gobeOrderStatusRS != null && gobeOrderStatusRS.getOrderStatus() != null) {

        	String statusStr = gobeOrderStatusRS.getOrderStatus().toUpperCase();
            extendedAttributes.put("vendorStatus", statusStr);

        	if(EnumUtils.isValidEnum(VendorBookingStatus.class, statusStr)){

	        	VendorBookingStatus status = VendorBookingStatus.valueOf(statusStr);
	           	 
            	switch(status){
            	
            	case CREATED:
            	case PROCESSING:
            	case CONFIRMED:
            	case CHECKED_VALID:
            	case ORDER_SPLIT_VALID:
            	case ON_VALIDATION:
            	case PAYMENT_AUTHORIZED:
            	case PRORATED_DISCOUNTS_CALCULATED:
            	case PAYMENT_AMOUNT_RESERVED:
            	case ORDER_BALANCE_SET:
            	case PAYMENT_CAPTURED:
            	case WAIT_FRAUD_MANUAL_CHECK:
            	case FRAUD_CHECKED:
            	case ORDER_SPLIT:
            	case PENDING_TOUR_OPERATOR_RESPONSE:
            	case CANCELLING:
            	 		
            	 	resultType.setCode(VendorErrorCode.PENDING.getId());
                    resultType.setMessage(VendorErrorCode.PENDING.getMsg());
                    bookingStatusRS.setNewStatus(ConnectorBookingStatus.PENDING);
                    break;
            	 		
            	case PARTIAL_CANCELLED:
            	case CANCELLED:
            		
            		resultType.setCode(VendorErrorCode.CANCELLED.getId());
                    resultType.setMessage(VendorErrorCode.CANCELLED.getMsg());
                    bookingStatusRS.setNewStatus(ConnectorBookingStatus.CANCELLED);
                    break;
                    
            	case COMPLETED:
            		
            		resultType.setCode(VendorErrorCode.CONFIRMED.getId());
                    resultType.setMessage(VendorErrorCode.CONFIRMED.getMsg());
                    bookingStatusRS.setNewStatus(ConnectorBookingStatus.CONFIRMED);
                    break;
                    
            	case SUSPENDED:
            	case PROCESSING_ERROR:
            	case PAYMENT_NOT_AUTHORIZED:
            	case CHECKED_INVALID:
            	case ORDER_SPLIT_INVALID:
            	case PRORATED_DISCOUNTS_NOT_CALCULATED:
            	case PAYMENT_AMOUNT_NOT_RESERVED:
            	case PAYMENT_NOT_CAPTURED:
            	case ERROR:
            		
            		resultType.setCode(VendorErrorCode.REJECTED.getId());
                    resultType.setMessage(VendorErrorCode.REJECTED.getMsg());
                    bookingStatusRS.setNewStatus(ConnectorBookingStatus.REJECTED);
                    break;
        	 
            	default:
            		
            		 resultType.setCode(VendorErrorCode.UNKNOWN.getId());
                     resultType.setMessage(VendorErrorCode.UNKNOWN.getMsg());
                     bookingStatusRS.setNewStatus(ConnectorBookingStatus.UNKNOWN);
                     break;
    				  
            	} 
        		
        	}else {
        		resultType.setCode(VendorErrorCode.UNKNOWN.getId());
                resultType.setMessage(VendorErrorCode.UNKNOWN.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.UNKNOWN);
                
                extendedAttributes.put(String.valueOf(VendorErrorCode.VENDOR_RETURN_UNMONITERED_STATUS.getId()), VendorErrorCode.VENDOR_RETURN_UNMONITERED_STATUS.getMsg());
			}
        	        	
        }else{
        	
        	 resultType.setCode(VendorErrorCode.REJECTED.getId());
             resultType.setMessage(VendorErrorCode.REJECTED.getMsg());
             bookingStatusRS.setNewStatus(ConnectorBookingStatus.REJECTED);

             extendedAttributes.put(String.valueOf(VendorErrorCode.VENDOR_RETURN_EMPTY_RESPONSE.getId()), VendorErrorCode.VENDOR_RETURN_EMPTY_RESPONSE.getMsg());
             
        }
       
        resultType.setExtendedAttributes(extendedAttributes);
        bookingStatusRS.setResultType(resultType);
		
		return bookingStatusRS;
    	
    }
}
