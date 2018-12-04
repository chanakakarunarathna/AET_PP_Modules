package com.placepass.connector.citydiscovery.application.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.placepass.connector.citydiscovery.application.exception.InvalidDateException;
import com.placepass.connector.citydiscovery.application.util.CityDiscoveryUtil;
import com.placepass.connector.citydiscovery.application.util.VendorErrorCode;
import com.placepass.connector.citydiscovery.application.util.VendorQuestions;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsActivityBookingRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsActivityCancellation;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsActivityCancellationPolicy;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookStatusRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookStatusRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsCancelBookingRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsCancelBookingRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsPriceInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsPriceRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsPriceRS;
import com.placepass.connector.common.booking.BookingStatusRQ;
import com.placepass.connector.common.booking.BookingStatusRS;
import com.placepass.connector.common.booking.Booker;
import com.placepass.connector.common.booking.BookingAnswer;
import com.placepass.connector.common.booking.BookingOption;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.Quantity;
import com.placepass.connector.common.booking.Total;
import com.placepass.connector.common.booking.Voucher;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.CancellationRules;
import com.placepass.connector.common.product.Price;
import com.placepass.exutil.NotFoundException;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.bookingstatus.ConnectorBookingStatus;
import com.placepass.utils.currency.AmountFormatter;
import com.placepass.utils.pricematch.PriceMatchPricePerAgeBand;
import com.placepass.utils.pricematch.PriceMatchQuantity;
import com.placepass.utils.pricematch.PriceMatchTotalPrice;
import com.placepass.utils.voucher.VoucherType;

public class BookingTransformer {

    private static final Logger logger = LoggerFactory.getLogger(BookingTransformer.class);

    private static final String LANGUAGE_CODE = "EN";

    private static final String TITLE_MR = "Mr.";

    private static final String TITLE_MRS = "Mrs.";

    private static final String TITLE_MS = "Ms.";

    private static final String MALE = "Male";

    private static final String FEMALE = "Female";

    private static final String POST_PAYMENT = "Y";

    private static final String VOUCHER_COPY = "Y";

    private static final String PROCESS_TYPE_PRICE = "PriceDetails";

    private static final String PROCESS_TYPE_BOOKING = "ActivityBooking";

    private static final String PROCESS_TYPE_VOUCHER = "ActivityBookingStatus";

    private static final String PROCESS_TYPE_BOOKING_STATUS = "ActivityBookingStatus";

    private static final String PROCESS_TYPE_CANCEL_BOOKING = "ActivityBookingCancellation";

    private static final String DEFAULT_VALUE = "N/A";

    private static final String VENDOR_STATUS = "vendorStatus";

    private static final Integer ACTIVITY_NOT_ALLOWED_TO_BE_DISTRIBUTED = 202;

    private static final Integer TOUR_NOT_AVAILABLE_ON_SELECTED_DATE = 203;

    public static ClsPriceRQ toPriceDetailsRequest(GetProductPriceRQ productPriceRQ) throws InvalidDateException {

        ClsPriceRQ clsPriceRQ = new ClsPriceRQ();

        List<Quantity> quantities = productPriceRQ.getQuantities();

        try {
            clsPriceRQ.setActivityDate(CityDiscoveryUtil.convertLocalDateToString(productPriceRQ.getBookingDate()));
        } catch (Exception e) {
            throw new InvalidDateException(VendorErrorCode.INVALID_DATE_FOUND.getMsg());
        }
        clsPriceRQ.setActivityID(Integer.parseInt(productPriceRQ.getVendorProductId()));

        for (Quantity quantity : quantities) {

            if (quantity.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {
                clsPriceRQ.setActivityNumberAdults(String.valueOf(quantity.getQuantity()));
            }

            if (quantity.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId()) {
                clsPriceRQ.setActivityNumberChildren(String.valueOf(quantity.getQuantity()));
            }

            if (quantity.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId()) {
                clsPriceRQ.setActivityNumberInfant(String.valueOf(quantity.getQuantity()));
            }

        }

        clsPriceRQ.setProcessType(PROCESS_TYPE_PRICE);

        return clsPriceRQ;
    }

    public static GetProductPriceRS toPriceDetails(ClsPriceRS priceDetailsResponse, GetProductPriceRQ productPriceRQ) {

        GetProductPriceRS productPriceRS = new GetProductPriceRS();
        ResultType resultType = new ResultType();

        String vendorProductOptionID = "";
        boolean found = false;

        if (priceDetailsResponse.getResultType().getErrorCode() == VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {

            List<ClsPriceInfoRS.ActivityPrices.ActivityPriceId> activityPriceIds = priceDetailsResponse.getPriceInfoRS()
                    .getActivityPrices().getActivityPriceId();

            for (ClsPriceInfoRS.ActivityPrices.ActivityPriceId activityPriceId : activityPriceIds) {

                String[] uuIDs = CityDiscoveryUtil
                        .getProductTypeAndDepatuerTime(productPriceRQ.getVendorProductOptionId());
                if (uuIDs.length != 0) {
                    vendorProductOptionID = uuIDs[0];
                } else {
                    vendorProductOptionID = productPriceRQ.getVendorProductOptionId();
                }
                if (Integer.parseInt(vendorProductOptionID) == activityPriceId.getID()) {

                    Total total = new Total();
                    total.setCurrency(activityPriceId.getActivityPriceCurrency());
                    total.setCurrencyCode(activityPriceId.getActivityPriceCurrency());
                    total.setFinalTotal(AmountFormatter.bigDecimalToFloat(activityPriceId.getActivityTotalPriceUSD()));
                    total.setMerchantTotal(
                            AmountFormatter.bigDecimalToFloat(activityPriceId.getActivityTotalPriceNetUSD()));
                    total.setRetailTotal(AmountFormatter.bigDecimalToFloat(activityPriceId.getActivityTotalPriceUSD()));

                    productPriceRS.setTotal(total);

                    found = true;
                }

            }

            if (!found) {
                throw new NotFoundException(VendorErrorCode.PRODUCT_OPTION_NOT_FOUND.toString(),
                        VendorErrorCode.PRODUCT_OPTION_NOT_FOUND.toString());
            }

            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
            productPriceRS.setResultType(resultType);

        } else {

            resultType.setCode(priceDetailsResponse.getResultType().getErrorCode());
            resultType.setMessage(priceDetailsResponse.getResultType().getErrorMessage());
            productPriceRS.setResultType(resultType);
        }

        return productPriceRS;
    }

    public static ClsActivityBookingRQ toMakeBookingRequest(MakeBookingRQ makeBookingRQ) throws InvalidDateException {

        ClsActivityBookingRQ activityBookingRQ = new ClsActivityBookingRQ();
        BookingOption bookingOption = makeBookingRQ.getBookingOptions().get(0);
        Booker booker = makeBookingRQ.getBookerDetails();

        String[] dateArray = bookingOption.getVendorProductOptionId().split("&");
        String activityPriceOptionDepartureTime = dateArray[1];

        activityBookingRQ.setActivityID(Integer.parseInt(bookingOption.getVendorProductId()));
        activityBookingRQ.setActivityPriceID(Integer.parseInt(dateArray[0]));
        activityBookingRQ.setActivityPriceOptionDepartureTime(activityPriceOptionDepartureTime);

        activityBookingRQ.setBookingPriceUSD(bookingOption.getTotal().getFinalTotal());
        activityBookingRQ.setActivityPriceOption(bookingOption.getTitle());
        activityBookingRQ.setActivityLanguages(LANGUAGE_CODE);

        for (Quantity quantity : bookingOption.getQuantities()) {

            if (quantity.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId())
                activityBookingRQ.setBookingNumberAdults(quantity.getQuantity());
            if (quantity.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId())
                activityBookingRQ.setBookingNumberChildren(quantity.getQuantity());
            if (quantity.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId())
                activityBookingRQ.setBookingNumberInfants(quantity.getQuantity());
        }

        activityBookingRQ.setBookingEmailAddress(booker.getEmail());
        activityBookingRQ.setBookingFirstName(booker.getFirstName());
        activityBookingRQ.setBookingLastName(booker.getLastName());
        activityBookingRQ.setBookingPhone(booker.getPhoneNumber());
        activityBookingRQ.setBookingMobile(booker.getPhoneNumber());
        activityBookingRQ.setBookingReferenceNumber(makeBookingRQ.getBookingId());

        List<BookingAnswer> answers = bookingOption.getBookingAnswers();
        // setting up default values for requierd data
        activityBookingRQ.setNameOfHotel(DEFAULT_VALUE);
        activityBookingRQ.setHotelAddress(DEFAULT_VALUE);
        activityBookingRQ.setBookingNotes(DEFAULT_VALUE);

        // Here system depends on BookingAnswer List, it helps to avoid an extra algolia call
        if (answers != null) {

            for (BookingAnswer bookingAnswer : answers) {

                if (Integer.parseInt(bookingAnswer.getQuestionId()) == VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL
                        .getId())
                    activityBookingRQ.setNameOfHotel(bookingAnswer.getAnswer());
                if (Integer.parseInt(bookingAnswer.getQuestionId()) == VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS
                        .getId())
                    activityBookingRQ.setHotelAddress(bookingAnswer.getAnswer());
                if (Integer.parseInt(bookingAnswer.getQuestionId()) == VendorQuestions.VENDOR_QUESTION_BOOKING_COMMENTS
                        .getId())
                    activityBookingRQ.setBookingNotes(bookingAnswer.getAnswer());
            }

            try {
                activityBookingRQ
                        .setBookingDate(CityDiscoveryUtil.convertLocalDateToString(bookingOption.getBookingDate()));
            } catch (Exception ex) {
                throw new InvalidDateException(VendorErrorCode.INVALID_DATE_FOUND.toString());
            }

            if (TITLE_MR.equals(booker.getTitle())) {
                activityBookingRQ.setBookingGender(MALE);
            } else if (TITLE_MRS.equals(booker.getTitle()) || TITLE_MS.equals(booker.getTitle())) {
                activityBookingRQ.setBookingGender(FEMALE);
            }

            activityBookingRQ.setPostPayment(POST_PAYMENT);
            activityBookingRQ.setProcessType(PROCESS_TYPE_BOOKING);

            return activityBookingRQ;
        }
        return activityBookingRQ;
    }

    public static MakeBookingRS toMakeBooking(ClsBookRS makeBookingResponse, String bookingID, ResultType resultType) {

        MakeBookingRS bookingRS = new MakeBookingRS();

        ClsBookInfoRS bookingInfoRS = makeBookingResponse.getBookInfoRS();

        if (resultType.getCode() == VendorErrorCode.CONFIRMED.getId()
                || resultType.getCode() == VendorErrorCode.PENDING.getId()) {

            ClsActivityCancellation activityCancellation = bookingInfoRS.getActivityCancellation();

            List<ClsActivityCancellationPolicy> activityCancellationPolicies = activityCancellation
                    .getActivityCancellationPolicy();

            bookingRS.setBookingItems(null);
            
            if(bookingID != null) {
                bookingRS.setBookingId(bookingID);
            } else {
                resultType.getMessage().concat("- Response transforming error: BookingId Not Found");
            }
            
            if(bookingInfoRS.getBookingCurrency() != null) {
                bookingRS.setCurrency(bookingInfoRS.getBookingCurrency());
            } else {
                resultType.getMessage().concat("- Response transforming error: Currency Not Found");
            }
            
            if(bookingInfoRS.getBookingReferenceCityDiscovery() != null) {
                bookingRS.setReferenceNumber(bookingInfoRS.getBookingReferenceCityDiscovery());
            } else {
                resultType.getMessage().concat("- Response transforming error: Reference number Not Found"); 
            }
            
            if(bookingInfoRS.getBookingPrice() != 0) {
                bookingRS.setTotalAmount(bookingInfoRS.getBookingPrice());
            } else {
                resultType.getMessage().concat("- Response transforming error: Booking price Not Found"); 
            }
            
            Voucher voucher = new Voucher();
            voucher.setReference(bookingInfoRS.getBookingReferenceCityDiscovery());
            voucher.setType(VoucherType.HTML);
            bookingRS.setVoucher(voucher);

            CancellationRules cancellationRules = CityDiscoveryUtil
                    .createCancellationRules(activityCancellationPolicies);

            if (cancellationRules != null) {
                bookingRS.setCancellationRules(cancellationRules);
            } else {
                resultType.getMessage().concat("- Response transforming error: Cancellation rules Not Found"); 
            }

            bookingRS.setResultType(resultType);
        }

        bookingRS.setResultType(resultType);
        return bookingRS;
    }

    public static ClsBookStatusRQ toVoucherDetailsRequest(BookingVoucherRQ bookingVoucherRQ) {

        ClsBookStatusRQ bookStatusRQ = new ClsBookStatusRQ();

        bookStatusRQ.setBookingReferenceCityDiscovery(bookingVoucherRQ.getReferenceNumber());
        bookStatusRQ.setVoucher(VOUCHER_COPY);
        bookStatusRQ.setProcessType(PROCESS_TYPE_VOUCHER);

        return bookStatusRQ;
    }

    public static BookingVoucherRS toVoucherDetails(ClsBookStatusRS bookStatusResponse) {

        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();
        ResultType resultType = new ResultType();

        if (bookStatusResponse.getResultType().getErrorCode() == VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {

            Voucher voucher = new Voucher();
            voucher.setExtendedAttributes(null);
            voucher.setReference(bookStatusResponse.getBookStatusInfoRS().getBookingReferenceCityDiscovery());
            voucher.setType(VoucherType.HTML);
            voucher.setHtmlContent(bookStatusResponse.getBookStatusInfoRS().getHtmlVoucher());
            voucher.setUrls(null);
            bookingVoucherRS.setVoucher(voucher);

            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } else {

            resultType.setCode(bookStatusResponse.getResultType().getErrorCode());
            resultType.setMessage(bookStatusResponse.getResultType().getErrorMessage());
            bookingVoucherRS.setResultType(resultType);

        }

        return bookingVoucherRS;
    }

    public static ClsCancelBookingRQ toCancelBookingRequest(CancelBookingRQ cancelBookingRequest) {

        ClsCancelBookingRQ cancelBookingRQ = new ClsCancelBookingRQ();
        cancelBookingRQ.setBookingReferenceCityDiscovery(cancelBookingRequest.getBookingReferenceId());
        cancelBookingRQ.setProcessType(PROCESS_TYPE_CANCEL_BOOKING);

        return cancelBookingRQ;
    }

    public static CancelBookingRS toCancelBooking(ClsCancelBookingRS cancelBookResponse) {

        CancelBookingRS cancelBookingRS = new CancelBookingRS();
        ResultType resultType = new ResultType();

        if (cancelBookResponse != null) {

            if (cancelBookResponse.getResultType().getErrorCode() == VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {

                cancelBookingRS.setBookingReferenceNo(
                        cancelBookResponse.getCancelBookingInfoRS().getBookingReferenceCityDiscovery());
                if (org.springframework.util.StringUtils
                        .hasText(cancelBookResponse.getCancelBookingInfoRS().getBookingCancellationFee().getValue())) {
                    cancelBookingRS.setCancellationFee(Float.parseFloat(
                            cancelBookResponse.getCancelBookingInfoRS().getBookingCancellationFee().getValue()));
                } else {
                    cancelBookingRS.setCancellationFee(0.0f);
                }
                cancelBookingRS.setCancelledBookingItems(null);
                cancelBookingRS.setBookingId("");

                resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
                resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
                cancelBookingRS.setResultType(resultType);

            } else {

                resultType.setCode(VendorErrorCode.CANCEL_UNKNOWN.getId());
                resultType.setMessage(VendorErrorCode.CANCEL_UNKNOWN.getMsg() + " - "
                        + cancelBookResponse.getResultType().getErrorCode() + " : "
                        + cancelBookResponse.getResultType().getErrorMessage());
                cancelBookingRS.setResultType(resultType);
            }

        }

        return cancelBookingRS;
    }

    public static ClsBookStatusRQ toBookingStatusRequest(BookingStatusRQ bookingStatusRQ) {

        ClsBookStatusRQ bookStatusRQ = new ClsBookStatusRQ();

        bookStatusRQ.setBookingReferenceCityDiscovery(bookingStatusRQ.getReferenceNumber());
        bookStatusRQ.setProcessType(PROCESS_TYPE_BOOKING_STATUS);

        return bookStatusRQ;
    }

    public static BookingStatusRS toBookingStatus(ClsBookStatusRS bookStatusResponse) {

        BookingStatusRS bookingStatusRS = new BookingStatusRS();
        ResultType resultType = new ResultType();
        Map<String, String> extendedAttributes = new HashMap<>();

        if (bookStatusResponse != null && bookStatusResponse.getResultType() != null && bookStatusResponse
                .getResultType().getErrorCode() == VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {

            String bookingStatus = bookStatusResponse.getBookStatusInfoRS().getBookingStatus();

            extendedAttributes.put(VENDOR_STATUS, bookingStatus);

            if (VendorErrorCode.VENDOR_BOOKING_CONFIRMED.getMsg().equals(bookingStatus)) {

                resultType.setCode(VendorErrorCode.CONFIRMED.getId());
                resultType.setMessage(VendorErrorCode.CONFIRMED.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.CONFIRMED);

            } else if (VendorErrorCode.VENDOR_BOOKING_ON_REQUEST.getMsg().equals(bookingStatus)) {

                resultType.setCode(VendorErrorCode.PENDING.getId());
                resultType.setMessage(VendorErrorCode.PENDING.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.PENDING);

            } else if (VendorErrorCode.VENDOR_BOOKING_NOT_AVAILABLE.getMsg().equals(bookingStatus)) {

                resultType.setCode(VendorErrorCode.REJECTED.getId());
                resultType.setMessage(VendorErrorCode.REJECTED.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.REJECTED);

            } else {

                resultType.setCode(VendorErrorCode.UNKNOWN.getId());
                resultType.setMessage(VendorErrorCode.UNKNOWN.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.UNKNOWN);

                extendedAttributes.put(String.valueOf(VendorErrorCode.VENDOR_RETURN_UNMONITERED_STATUS.getId()),
                        VendorErrorCode.VENDOR_RETURN_UNMONITERED_STATUS.getMsg());

            }

        } else {

            bookingStatusRS.setNewStatus(ConnectorBookingStatus.REJECTED);
            resultType.setCode(VendorErrorCode.REJECTED.getId());
            resultType.setMessage(VendorErrorCode.REJECTED.getMsg());

            if (bookStatusResponse.getResultType() != null) {
                extendedAttributes.put(String.valueOf(bookStatusResponse.getResultType().getErrorCode()),
                        bookStatusResponse.getResultType().getErrorMessage());
            }
            extendedAttributes.put(String.valueOf(VendorErrorCode.VENDOR_RETURN_EMPTY_RESPONSE.getId()),
                    VendorErrorCode.VENDOR_RETURN_EMPTY_RESPONSE.getMsg());

        }

        resultType.setExtendedAttributes(extendedAttributes);
        bookingStatusRS.setResultType(resultType);

        return bookingStatusRS;
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
}
