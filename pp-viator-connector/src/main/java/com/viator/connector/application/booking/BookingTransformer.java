package com.viator.connector.application.booking;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.viator.connector.domain.viator.availability.ViatorAvailabilityRequest;
import com.viator.connector.domain.viator.availability.ViatorAvailabilityResAvailability;
import com.viator.connector.domain.viator.availability.ViatorAvailabilityResInfo;
import com.viator.connector.domain.viator.availability.ViatorAvailabilityResponse;
import com.viator.connector.domain.viator.common.AgeBand;
import com.viator.connector.domain.viator.product.ViatorProductResBookingQuestion;
import com.viator.connector.domain.viator.product.ViatorProductResponse;
import org.springframework.util.StringUtils;

import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.bookingstatus.ConnectorBookingStatus;
import com.placepass.utils.pricematch.PriceMatchPricePerAgeBand;
import com.placepass.utils.pricematch.PriceMatchQuantity;
import com.placepass.utils.pricematch.PriceMatchTotalPrice;
import com.placepass.utils.voucher.VoucherType;
import com.viator.connector.application.util.ViatorUtil;
import com.viator.connector.domain.placepass.algolia.product.AlgoliaBookingQuestion;
import com.viator.connector.domain.placepass.algolia.product.GetAlgoliaProductDetailsRS;
import com.placepass.connector.common.booking.BookingStatusRS;
import com.placepass.connector.common.booking.Booker;
import com.placepass.connector.common.booking.BookingAnswer;
import com.placepass.connector.common.booking.BookingItem;
import com.placepass.connector.common.booking.BookingOption;
import com.placepass.connector.common.booking.BookingQuestion;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.CancelledBookingItem;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.Quantity;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.booking.Total;
import com.placepass.connector.common.booking.Traveler;
import com.placepass.connector.common.booking.Voucher;
import com.placepass.connector.common.product.Price;
import com.viator.connector.domain.viator.book.ViatorBookReqBooker;
import com.viator.connector.domain.viator.book.ViatorBookReqItem;
import com.viator.connector.domain.viator.book.ViatorBookReqPartnerDetail;
import com.viator.connector.domain.viator.book.ViatorBookReqPartnerItemDetail;
import com.viator.connector.domain.viator.book.ViatorBookReqQuestionAnswer;
import com.viator.connector.domain.viator.book.ViatorBookReqTraveller;
import com.viator.connector.domain.viator.book.ViatorBookRequest;
import com.viator.connector.domain.viator.book.ViatorBookResDetails;
import com.viator.connector.domain.viator.book.ViatorBookResItemSummary;
import com.viator.connector.domain.viator.book.ViatorBookResponse;
import com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceRequest;
import com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceResponse;
import com.viator.connector.domain.viator.cancel.ViatorCancelBookingReqItineraryItem;
import com.viator.connector.domain.viator.cancel.ViatorCancelBookingRequest;
import com.viator.connector.domain.viator.cancel.ViatorCancelBookingResDetails;
import com.viator.connector.domain.viator.cancel.ViatorCancelBookingResponse;
import com.viator.connector.domain.viator.cancel.ViatorCancelledBookingResCancelItem;
import com.viator.connector.domain.viator.mybookings.ViatorMybookingsRequest;
import com.viator.connector.domain.viator.mybookings.ViatorMybookingsResponse;

public class BookingTransformer {

    public static final String CANCELLATION_STATUS_CODE = "Confirmed";

    public static ViatorBookRequest toBookingRequest(MakeBookingRQ makeBookingRQ, boolean setDemo) {

        ViatorBookRequest viatorBookingRequest = new ViatorBookRequest();

        viatorBookingRequest.setSessionId("");
        viatorBookingRequest.setIpAddress("");
        // FIXME: do we get CurrencyCode from API requests??
        viatorBookingRequest.setCurrencyCode("USD"); // TODO multiple currency
        viatorBookingRequest.setDemo(setDemo); // TODO add property
        viatorBookingRequest.setNewsletterSignUp(false);
        // viatorBookingRequest.setAid();
        // viatorBookingRequest.setPromoCode(null);
        // viatorBookingRequest.setOtherDetail(null);

        // Partner Mapping
        ViatorBookReqPartnerDetail partnerDetail = new ViatorBookReqPartnerDetail();
        partnerDetail.setDistributorRef(makeBookingRQ.getBookingId());
        viatorBookingRequest.setPartnerDetail(partnerDetail);

        // Booker Mapping
        ViatorBookReqBooker viatorBooker = new ViatorBookReqBooker();
        Booker placePassBooker = makeBookingRQ.getBookerDetails();

        viatorBooker.setTitle(placePassBooker.getTitle());
        viatorBooker.setFirstname(placePassBooker.getFirstName());
        viatorBooker.setSurname(placePassBooker.getLastName());
        viatorBooker.setEmail(placePassBooker.getEmail());
        viatorBooker.setCellPhoneCountryCode(placePassBooker.getCountryCode());
        viatorBooker.setCellPhone(placePassBooker.getPhoneNumber());
        viatorBookingRequest.setBooker(viatorBooker);

        List<ViatorBookReqItem> items = new ArrayList<>();
        List<BookingOption> bookingOptions = makeBookingRQ.getBookingOptions();

        for (BookingOption bookingOption : bookingOptions) {

            ViatorBookReqItem item = new ViatorBookReqItem();
            ViatorBookReqPartnerItemDetail partnerItemDetail = new ViatorBookReqPartnerItemDetail();
            partnerItemDetail.setDistributorItemRef(bookingOption.getBookingOptionId());
            item.setPartnerItemDetail(partnerItemDetail);
            item.setTravelDate(bookingOption.getBookingDate().toString());
            item.setProductCode(bookingOption.getVendorProductId());
            item.setTourGradeCode(bookingOption.getVendorProductOptionId());
            // item.setLanguageOptionCode("en/SERVICE_GUIDE");// TODO Add lang
            item.setSpecialRequirements("");

            if (bookingOption.getIsHotelPickUpEligible() != null && bookingOption.getIsHotelPickUpEligible()
                    && bookingOption.getPickupLocation() != null
                    && !StringUtils.isEmpty(bookingOption.getPickupLocation().getId())) {
                item.setHotelId(bookingOption.getPickupLocation().getId());
            }

            // Answers Mapping
            List<BookingAnswer> ppBookingAnswers = bookingOption.getBookingAnswers();
            List<ViatorBookReqQuestionAnswer> viatorBookQuestionAnswers = new ArrayList<ViatorBookReqQuestionAnswer>();
            item.setBookingQuestionAnswers(viatorBookQuestionAnswers);
            item.setLanguageOptionCode(bookingOption.getLanguageOptionCode());

            if (ppBookingAnswers != null) {
                for (BookingAnswer ppBookingAnswer : ppBookingAnswers) {
                    ViatorBookReqQuestionAnswer viatorQA = new ViatorBookReqQuestionAnswer();
                    viatorQA.setQuestionId(Integer.parseInt(ppBookingAnswer.getQuestionId()));
                    viatorQA.setAnswer(ppBookingAnswer.getAnswer());
                    viatorBookQuestionAnswers.add(viatorQA);
                }
            }

            // Travelers Mapping
            List<Quantity> ppQuantities = bookingOption.getQuantities();
            List<Traveler> ppTravelers = bookingOption.getTraverlerDetails();
            List<ViatorBookReqTraveller> viatorTravellers = new ArrayList<ViatorBookReqTraveller>();

            /*
             * for (Quantity ppQuantity : ppQuantities) {
             * 
             * for (int i = 0; i < ppQuantity.getQuantity(); i++) {
             * 
             * ViatorBookReqTraveller viatorTraveller = new ViatorBookReqTraveller();
             * 
             * // FIXME: these key words should come from PP common (utils) if
             * (ppQuantity.getAgeBandLabel().equals("ADULT")) { viatorTraveller.setBandId(1); } else if
             * (ppQuantity.getAgeBandLabel().equals("CHILD")) { viatorTraveller.setBandId(2); } else if
             * (ppQuantity.getAgeBandLabel().equals("INFANT")) { viatorTraveller.setBandId(3); } else if
             * (ppQuantity.getAgeBandLabel().equals("SENIOR")) { viatorTraveller.setBandId(5); }
             * 
             * if (viatorTravellers.size() == 0) { viatorTraveller.setLeadTraveller(true); }
             * 
             * viatorTravellers.add(viatorTraveller); } }
             */

            // Using traveler array instead of quantities since the api validated travelers per quantity
            for (int i = 0; i < ppTravelers.size(); i++) {
                ViatorBookReqTraveller viatorTraveller = new ViatorBookReqTraveller();
                viatorTraveller.setTitle(ppTravelers.get(i).getTitle());
                viatorTraveller.setFirstname(ppTravelers.get(i).getFirstName());
                viatorTraveller.setSurname(ppTravelers.get(i).getLastName());
                // Placepass use same ageBandIds mapping as viator
                viatorTraveller.setBandId(ppTravelers.get(i).getAgeBandId());

                if (ppTravelers.get(i).isLeadTraveler()) {
                    viatorTraveller.setLeadTraveller(true);
                }

                viatorTravellers.add(viatorTraveller);

            }

            item.setTravellers(viatorTravellers);
            items.add(item);
        }

        viatorBookingRequest.setItems(items);
        return viatorBookingRequest;

    }

    public static MakeBookingRS toBookingResponse(ViatorBookResponse viatorBookDetails,
            ViatorMybookingsResponse viatorBookingVoucherRS, ResultType resultType) {

        List<BookingItem> bookingItems = new ArrayList<>();
        MakeBookingRS makeBookingRS = new MakeBookingRS();
        if (resultType.getCode() == VendorErrorCode.CONFIRMED.getId()
                || resultType.getCode() == VendorErrorCode.PENDING.getId()) {

            ViatorBookResDetails viatorBookResDetails = viatorBookDetails.getData();

            makeBookingRS.setBookingId(viatorBookResDetails.getDistributorRef());
            makeBookingRS.setCurrency(viatorBookResDetails.getCurrencyCode());

            if (viatorBookResDetails.getItineraryId() != null) {
                makeBookingRS.setReferenceNumber(viatorBookResDetails.getItineraryId().toString());
            } else {
                resultType.getMessage().concat("- Response transforming error: ItineraryId Not Found");
            }

            if (viatorBookResDetails.getTotalPrice() != null) {
                makeBookingRS.setTotalAmount(viatorBookResDetails.getTotalPrice().floatValue());
            } else {
                resultType.getMessage().concat("- Response transforming error: TotalPrice Not Found");
            }

            List<ViatorBookResItemSummary> itemSummaries = viatorBookDetails.getData().getItemSummaries();
            if (!itemSummaries.isEmpty()) {
                for (ViatorBookResItemSummary viatorBookResItemSummary : itemSummaries) {
                    BookingItem bookingItem = new BookingItem();
                    bookingItem.setItemRef(viatorBookResItemSummary.getDistributorItemRef());
                    bookingItem.setCancellable(viatorBookResItemSummary.getMerchantCancellable());

                    if (viatorBookResItemSummary.getItemId() != null) {
                        bookingItem.setItemId(viatorBookResItemSummary.getItemId().toString());
                    } else {
                        resultType.getMessage()
                                .concat("- Response transforming error: ViatorBookResItemSummary Not Found");
                    }

                    bookingItems.add(bookingItem);
                }
            } else {
                resultType.getMessage().concat("- Response transforming error: ItemSummaries Not Found");
            }
            makeBookingRS.setBookingItems(bookingItems);

            if (viatorBookingVoucherRS != null && viatorBookingVoucherRS.getSuccess()) {
                if (viatorBookingVoucherRS.getData() != null && !viatorBookingVoucherRS.getData().isEmpty()) {
                    makeBookingRS.setVoucher(toVoucher(viatorBookingVoucherRS));
                }
            }
        }
        makeBookingRS.setResultType(resultType);
        return makeBookingRS;
    }

    public static ViatorCalculatepriceRequest toPricingRequest(GetProductPriceRQ getProductPriceRQ) {

        ViatorCalculatepriceRequest viatorPricingRequest = new ViatorCalculatepriceRequest();
        viatorPricingRequest.setCurrencyCode("USD"); // TODO: Do not hard code
        List<com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceReqItem> items = new ArrayList<>();
        com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceReqItem item = new com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceReqItem();
        item.setProductCode(getProductPriceRQ.getVendorProductId());
        item.setSpecialReservation(false);// Do we need this in the request object
        item.setTourGradeCode(getProductPriceRQ.getVendorProductOptionId());
        item.setTravelDate(getProductPriceRQ.getBookingDate().toString());
        List<Quantity> ppItemQuantities = getProductPriceRQ.getQuantities();
        List<com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceReqTraveller> travellers = new ArrayList<>();
        for (Quantity ppQuantity : ppItemQuantities) {
            for (int i = 1; i <= ppQuantity.getQuantity(); i++) {
                com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceReqTraveller viatorTraveller = new com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceReqTraveller();
                if (ppQuantity.getAgeBandId() != 0) {
                    // Placepass use same ageBandIds mapping as viator
                    viatorTraveller.setBandId(ppQuantity.getAgeBandId());
                } else {
                    // Placepass use same ageBandIds mapping as viator
                    if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.ADULT.name())) {
                        viatorTraveller.setBandId(PlacePassAgeBandType.ADULT.ageBandId);
                    } else if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.CHILD.name())) {
                        viatorTraveller.setBandId(PlacePassAgeBandType.CHILD.ageBandId);
                    } else if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.INFANT.name())) {
                        viatorTraveller.setBandId(PlacePassAgeBandType.INFANT.ageBandId);
                    } else if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.SENIOR.name())) {
                        viatorTraveller.setBandId(PlacePassAgeBandType.SENIOR.ageBandId);
                    } else if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.YOUTH.name())) {
                        viatorTraveller.setBandId(PlacePassAgeBandType.YOUTH.ageBandId);
                    }
                }
                travellers.add(viatorTraveller);
            }

        }
        item.setTravellers(travellers);
        items.add(item);
        viatorPricingRequest.setItems(items);
        return viatorPricingRequest;
    }

    public static ViatorAvailabilityRequest toAvailabilityRequest(GetProductPriceRQ getProductPriceRQ) {
        ViatorAvailabilityRequest viatorAvailabilityRequest = new ViatorAvailabilityRequest();
        viatorAvailabilityRequest.setCurrencyCode("USD");
        viatorAvailabilityRequest.setProductCode(getProductPriceRQ.getVendorProductId());
        String bookingDate = getProductPriceRQ.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        viatorAvailabilityRequest.setMonth(bookingDate.substring(5, 7));
        viatorAvailabilityRequest.setYear(bookingDate.substring(0, 4));
        List<AgeBand> ageBands = new ArrayList<>();

        for (Quantity quantity : getProductPriceRQ.getQuantities()) {
            AgeBand ageBand = new AgeBand();
            ageBand.setBandId(quantity.getAgeBandId());
            ageBand.setCount(quantity.getQuantity());
            ageBands.add(ageBand);
        }

        viatorAvailabilityRequest.setAgeBands(ageBands);

        return viatorAvailabilityRequest;
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

    public static GetBookingQuestionsRS toBookingQuestions(GetAlgoliaProductDetailsRS algoliaProductDetails) {

        GetBookingQuestionsRS getBookingQuestionsRS = new GetBookingQuestionsRS();
        ResultType resultType = new ResultType();
        List<BookingQuestion> bookingQuestions = new ArrayList<>();

        if (algoliaProductDetails.getBookingQuestions() != null
                && !algoliaProductDetails.getBookingQuestions().isEmpty()) {
            for (AlgoliaBookingQuestion algoliaBookingQuestion : algoliaProductDetails.getBookingQuestions()) {
                BookingQuestion bookingQuestion = new BookingQuestion();
                bookingQuestion.setMessage(algoliaBookingQuestion.getMessage());
                bookingQuestion.setQuestionId(algoliaBookingQuestion.getQuestionId());
                bookingQuestion.setRequired(algoliaBookingQuestion.getRequired());
                bookingQuestion.setStringQuestionId(algoliaBookingQuestion.getStringQuestionId());
                bookingQuestion.setSubTitle(algoliaBookingQuestion.getSubTitle());
                bookingQuestion.setTitle(algoliaBookingQuestion.getTitle());

                bookingQuestions.add(bookingQuestion);
            }
            getBookingQuestionsRS.setBookingQuestions(bookingQuestions);
            resultType.setCode(0);
            resultType.setMessage("");

        } else {
            resultType.setCode(1);
            resultType.setMessage("Booking Questions are not available for this product.");
        }

        getBookingQuestionsRS.setResultType(resultType);
        return getBookingQuestionsRS;
    }

    public static GetBookingQuestionsRS toBookingQuestions(ViatorProductResponse viatorProductResponse) {
        GetBookingQuestionsRS getBookingQuestionsRS = new GetBookingQuestionsRS();
        ResultType resultType = new ResultType();
        List<BookingQuestion> bookingQuestions = new ArrayList<>();

        if (viatorProductResponse != null && viatorProductResponse.getSuccess()
                && viatorProductResponse.getData() != null && viatorProductResponse.getData().getBookingQuestions() != null
                && !viatorProductResponse.getData().getBookingQuestions().isEmpty()) {
            for (ViatorProductResBookingQuestion viatorProductResBookingQuestion : viatorProductResponse.getData().getBookingQuestions()) {
                BookingQuestion bookingQuestion = new BookingQuestion();
                bookingQuestion.setMessage(viatorProductResBookingQuestion.getMessage());
                bookingQuestion.setQuestionId(viatorProductResBookingQuestion.getQuestionId());
                bookingQuestion.setRequired(viatorProductResBookingQuestion.getRequired());
                bookingQuestion.setStringQuestionId(viatorProductResBookingQuestion.getStringQuestionId());
                bookingQuestion.setSubTitle(viatorProductResBookingQuestion.getSubTitle());
                bookingQuestion.setTitle(viatorProductResBookingQuestion.getTitle());

                bookingQuestions.add(bookingQuestion);
            }
            getBookingQuestionsRS.setBookingQuestions(bookingQuestions);
            resultType.setCode(0);
            resultType.setMessage("");

        } else {
            resultType.setCode(1);
            resultType.setMessage("Booking Questions are not available for this product.");
        }

        getBookingQuestionsRS.setResultType(resultType);
        return getBookingQuestionsRS;
    }

    public static ViatorMybookingsRequest toVoucherRequest(BookingVoucherRQ bookingVoucherRQ) {

        ViatorMybookingsRequest viatorMybookingsRequest = new ViatorMybookingsRequest();
        viatorMybookingsRequest.setRefNo(bookingVoucherRQ.getReferenceNumber());
        return viatorMybookingsRequest;
    }

    public static BookingVoucherRS toVoucherResponse(ViatorMybookingsResponse viatorMybookingsResponse) {

        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();
        ResultType resultType = new ResultType();
        resultType = ViatorUtil.getResultTypeObj(viatorMybookingsResponse);
        if (viatorMybookingsResponse != null) {
            if (viatorMybookingsResponse.getSuccess()) {
                bookingVoucherRS.setVoucher(toVoucher(viatorMybookingsResponse));
            }
        }
        bookingVoucherRS.setResultType(resultType);
        return bookingVoucherRS;
    }

    public static ViatorCancelBookingRequest toCancelBookingRequest(CancelBookingRQ cancelBookingRQ) {

        ViatorCancelBookingRequest viatorCancelBookingRequest = new ViatorCancelBookingRequest();
        List<ViatorCancelBookingReqItineraryItem> items = new ArrayList<>();

        viatorCancelBookingRequest.setItineraryId(Integer.parseInt(cancelBookingRQ.getBookingReferenceId()));
        viatorCancelBookingRequest.setDistributorRef(cancelBookingRQ.getBookingId());

        for (BookingItem bookingItem : cancelBookingRQ.getBookingItems()) {
            ViatorCancelBookingReqItineraryItem cancelItems = new ViatorCancelBookingReqItineraryItem();
            cancelItems.setItemId(Integer.parseInt(bookingItem.getItemId()));
            cancelItems.setDistributorItemRef(bookingItem.getItemRef());
            cancelItems.setCancelCode(cancelBookingRQ.getCancellationReasonCode());
            items.add(cancelItems);
        }

        viatorCancelBookingRequest.setCancelItems(items);
        return viatorCancelBookingRequest;
    }

    public static CancelBookingRS toViatorCancelBookingResponse(
            ViatorCancelBookingResponse viatorCancelBookingResponse) {

        CancelBookingRS cancelBookingRS = new CancelBookingRS();
        List<CancelledBookingItem> cancelledBookedItemsList = new ArrayList<>();
        ResultType resultType = ViatorUtil.getResultTypeObj(viatorCancelBookingResponse);

        if (viatorCancelBookingResponse.getSuccess()) {
            ViatorCancelBookingResDetails viatorCancelBookingResDetails = viatorCancelBookingResponse.getData();
            cancelBookingRS.setBookingReferenceNo(viatorCancelBookingResDetails.getItineraryId());
            cancelBookingRS.setBookingId(viatorCancelBookingResDetails.getDistributorRef());

            if (viatorCancelBookingResDetails.getCancelItems().size() > 0) {
                for (ViatorCancelledBookingResCancelItem viatorCancelledItems : viatorCancelBookingResDetails
                        .getCancelItems()) {
                    CancelledBookingItem cancelledBookedItem = new CancelledBookingItem();
                    cancelledBookedItem.setItemId(viatorCancelledItems.getItemId());
                    cancelledBookedItem.setItemRef(viatorCancelledItems.getDistributorItemRef());
                    cancelledBookedItem.setCancellationResponseStatusCode(
                            viatorCancelledItems.getCancellationResponseStatusCode());
                    cancelledBookedItem.setCancellationResponseDescription(
                            viatorCancelledItems.getCancellationResponseDescription());
                    cancelledBookedItemsList.add(cancelledBookedItem);

                    if (!viatorCancelledItems.getCancellationResponseStatusCode().equals(CANCELLATION_STATUS_CODE)) {
                        resultType.setCode(VendorErrorCode.CANCEL_UNKNOWN.getId());
                        resultType.setMessage(VendorErrorCode.CANCEL_UNKNOWN.getMsg() + " - " + viatorCancelledItems.getCancellationResponseStatusCode() + " - "
                                + viatorCancelledItems.getCancellationResponseDescription());
                    }
                }

            }
        }
        cancelBookingRS.setCancelledBookingItems(cancelledBookedItemsList);
        cancelBookingRS.setResultType(resultType);
        return cancelBookingRS;
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
            priceMatchPricePerAgeBand.setPricingUnit(price.getPricingUnit());

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

    public static Voucher toVoucher(ViatorMybookingsResponse viatorMybookingsResponse) {

        Voucher voucher = new Voucher();
        List<String> voucherUrls = new ArrayList<>();
        voucherUrls.add(viatorMybookingsResponse.getData().get(0).getVoucherURL());
        voucher.setUrls(voucherUrls);
        voucher.setReference(viatorMybookingsResponse.getData().get(0).getVoucherKey());
        voucher.setType(VoucherType.SINGLE_URL);
        return voucher;
    }

    public static BookingStatusRS toBookingStatusResponse(ViatorMybookingsResponse viatorMybookingsResponse) {

        BookingStatusRS bookingStatusRS = new BookingStatusRS();
        ResultType resultType = new ResultType();
        resultType = ViatorUtil.getResultTypeObj(viatorMybookingsResponse);
        if (viatorMybookingsResponse != null) {
            if (viatorMybookingsResponse.getSuccess()
                    && viatorMybookingsResponse.getData().get(0).getBookingStatus().getConfirmed()
                    && !viatorMybookingsResponse.getData().get(0).getBookingStatus().getFailed()) {

                resultType.setCode(VendorErrorCode.CONFIRMED.getId());
                resultType.setMessage(VendorErrorCode.CONFIRMED.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.CONFIRMED);

            } else if (viatorMybookingsResponse.getSuccess()
                    && viatorMybookingsResponse.getData().get(0).getBookingStatus().getPending()) {

                resultType.setCode(VendorErrorCode.PENDING.getId());
                resultType.setMessage(VendorErrorCode.PENDING.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.PENDING);

            } else if (viatorMybookingsResponse.getSuccess()
                    && viatorMybookingsResponse.getData().get(0).getBookingStatus().getCancelled()) {

                resultType.setCode(VendorErrorCode.CANCELLED.getId());
                resultType.setMessage(VendorErrorCode.CANCELLED.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.CANCELLED);

            } else if (viatorMybookingsResponse.getSuccess()
                    && viatorMybookingsResponse.getData().get(0).getBookingStatus().getFailed()) {

                resultType.setCode(VendorErrorCode.REJECTED.getId());
                resultType.setMessage(VendorErrorCode.REJECTED.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.REJECTED);

                if (viatorMybookingsResponse.getErrorMessage() != null) {

                    Map<String, String> extendedAttributes = new HashMap<String, String>();
                    String message = "";
                    for (String errorMessage : viatorMybookingsResponse.getErrorMessage()) {
                        message = message + errorMessage + " ";
                    }
                    extendedAttributes.put("message", message);
                    resultType.setExtendedAttributes(extendedAttributes);
                }

            } else if (viatorMybookingsResponse.getSuccess()) {

                resultType.setCode(VendorErrorCode.UNKNOWN.getId());
                resultType.setMessage(VendorErrorCode.UNKNOWN.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.UNKNOWN);
                String message = "";
                if (viatorMybookingsResponse.getData() != null && viatorMybookingsResponse.getData().size() > 0
                        && viatorMybookingsResponse.getData().get(0).getBookingStatus() != null) {
                    message = "failed:" + viatorMybookingsResponse.getData().get(0).getBookingStatus().getFailed()
                            + ", pending:" + viatorMybookingsResponse.getData().get(0).getBookingStatus().getPending()
                            + ", confirmed:"
                            + viatorMybookingsResponse.getData().get(0).getBookingStatus().getConfirmed()
                            + ", cancelled:"
                            + viatorMybookingsResponse.getData().get(0).getBookingStatus().getCancelled() + ", amended:"
                            + viatorMybookingsResponse.getData().get(0).getBookingStatus().getAmended();
                }
                Map<String, String> extendedAttributes = new HashMap<String, String>();
                extendedAttributes.put(String.valueOf(VendorErrorCode.VENDOR_RETURN_UNMONITERED_STATUS.getId()),
                        VendorErrorCode.VENDOR_RETURN_UNMONITERED_STATUS.getMsg() + " - " + message);

                resultType.setExtendedAttributes(extendedAttributes);
            } else {

                resultType.setCode(VendorErrorCode.REJECTED.getId());
                resultType.setMessage(VendorErrorCode.REJECTED.getMsg());
                bookingStatusRS.setNewStatus(ConnectorBookingStatus.REJECTED);

                if (viatorMybookingsResponse.getErrorMessage() != null) {

                    Map<String, String> extendedAttributes = new HashMap<String, String>();
                    String message = "";
                    for (String errorMessage : viatorMybookingsResponse.getErrorMessage()) {
                        message = message + errorMessage + " ";
                    }
                    extendedAttributes.put("message", message);
                    resultType.setExtendedAttributes(extendedAttributes);
                }
            }
        }
        bookingStatusRS.setResultType(resultType);
        return bookingStatusRS;
    }

    public static ResultType toValidateProductResponse(ViatorCalculatepriceResponse viatorPriceDetails) {

        return ViatorUtil.getResultTypeObj(viatorPriceDetails);
    }

    public static ResultType toValidateAvailabilityResponse(ViatorAvailabilityResponse viatorAvailabilityResponse, String bookingDate) {

        ResultType resultType = new ResultType();
        resultType.setCode(VendorErrorCode.PRODUCT_NOT_FOUND.getId());
        resultType.setMessage(VendorErrorCode.PRODUCT_NOT_FOUND.getMsg());

        if (viatorAvailabilityResponse.getData() != null && viatorAvailabilityResponse.getData().getAvailability() != null) {
            for (ViatorAvailabilityResAvailability viatorAvailabilityResAvailability : viatorAvailabilityResponse.getData().getAvailability()) {
                if (viatorAvailabilityResAvailability.getBookingDate().equals(bookingDate)) {
                    resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
                    resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
                }
            }
        }

        return resultType;
    }

}
