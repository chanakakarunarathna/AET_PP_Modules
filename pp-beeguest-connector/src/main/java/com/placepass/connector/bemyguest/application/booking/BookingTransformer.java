package com.placepass.connector.bemyguest.application.booking;

import java.util.List;

import com.placepass.connector.bemyguest.application.util.BeMyGuestUtil;
import com.placepass.connector.bemyguest.application.util.VendorErrorCode;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgBookRequest;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgBookResponse;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgGetBookingStatusResponse;
import com.placepass.connector.bemyguest.domain.bemyguest.bookingcheck.BeMyGuestCheckBookingRQ;
import com.placepass.connector.bemyguest.domain.bemyguest.bookingcheck.BeMyGuestCheckBookingRS;
import com.placepass.connector.common.booking.BookingAnswer;
import com.placepass.connector.common.booking.BookingOption;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.Quantity;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.booking.Total;
import com.placepass.connector.common.booking.Voucher;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.voucher.VoucherType;

public class BookingTransformer {

    private static final String US_CURRENCY_CODE = "USD";

    public static BmgBookRequest toBookingRequest(MakeBookingRQ makeBookingRQ, boolean isTest) {
        BmgBookRequest bmgBookRequest = new BmgBookRequest();

        BookingOption bookingOption = makeBookingRQ.getBookingOptions().get(0);
        String[] idArray = bookingOption.getVendorProductOptionId().contains("&")
                ? bookingOption.getVendorProductOptionId().split("&") : null;
        String productTypeUuid = null;
        String timeSlotUuid = null;

        if (idArray != null) {
            productTypeUuid = idArray[0];
            timeSlotUuid = idArray[1];
        } else {
            productTypeUuid = bookingOption.getVendorProductOptionId();
        }

        bmgBookRequest.setSalutation(makeBookingRQ.getBookerDetails().getTitle());
        bmgBookRequest.setFirstName(makeBookingRQ.getBookerDetails().getFirstName());
        bmgBookRequest.setLastName(makeBookingRQ.getBookerDetails().getLastName());
        bmgBookRequest.setEmail(makeBookingRQ.getBookerDetails().getEmail());
        bmgBookRequest.setPhone(makeBookingRQ.getBookerDetails().getPhoneNumber());
        // bmgBookRequest.setMessage(message);
        bmgBookRequest.setProductTypeUuid(productTypeUuid);
        for (Quantity quantity : bookingOption.getQuantities()) {
            if (quantity.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {
                bmgBookRequest.setPax(quantity.getQuantity());
            } else if (quantity.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId()) {
                bmgBookRequest.setChildren(quantity.getQuantity());
            }
        }

        bmgBookRequest.setTimeSlotUuid(timeSlotUuid);
        bmgBookRequest.setAddons(null);
        bmgBookRequest.setCurrencyCode(US_CURRENCY_CODE);

        // TODO:need some improvements
        if (bookingOption.getBookingAnswers() != null && !bookingOption.getBookingAnswers().isEmpty()) {
            for (BookingAnswer answer : bookingOption.getBookingAnswers()) {
                if (answer.getQuestionId().equals("1")) {
                    bmgBookRequest.setHotelName(answer.getAnswer());
                } else if (answer.getQuestionId().equals("2")) {
                    bmgBookRequest.setHotelAddress(answer.getAnswer());
                } else if (answer.getQuestionId().equals("3")) {
                    bmgBookRequest.setFlightNumber(answer.getAnswer());
                } else if (answer.getQuestionId().equals("4")) {
                    bmgBookRequest.setFlightDateTime(answer.getAnswer());
                } else if (answer.getQuestionId().equals("5")) {
                    bmgBookRequest.setFlightDestination(answer.getAnswer());
                }
            }
        }

        bmgBookRequest.setArrivalDate(bookingOption.getBookingDate().toString());
        bmgBookRequest.setPartnerReference(makeBookingRQ.getCartId().replaceAll("-", ""));
        bmgBookRequest.setUsePromotion(false);
        bmgBookRequest.setIsTest(isTest);

        return bmgBookRequest;
    }

    public static MakeBookingRS toBookingResponse(MakeBookingRS makeBookingResponse, BmgBookResponse bmgBookResponse,
            ResultType resultType) {
        if (makeBookingResponse == null) {
            makeBookingResponse = new MakeBookingRS();
            makeBookingResponse.setCartId(bmgBookResponse.getData().getPartnerReference());
            makeBookingResponse.setCurrency(bmgBookResponse.getData().getRequestCurrencyCode());
            makeBookingResponse.setReferenceNumber(bmgBookResponse.getData().getUuid());
            makeBookingResponse.setResultType(resultType);
            makeBookingResponse.setTotalAmount(
                    BeMyGuestUtil.doubleToFloat(bmgBookResponse.getData().getTotalAmountRequestCurrency()));
        } else {
            if (bmgBookResponse.getData().getConfirmationEmailFiles() != null
                    && !bmgBookResponse.getData().getConfirmationEmailFiles().isEmpty()) {
                Voucher voucher = new Voucher();
                voucher.setUrls(bmgBookResponse.getData().getConfirmationEmailFiles());
                voucher.setReference(bmgBookResponse.getData().getUuid());
                voucher.setType(VoucherType.MULTI_URL);
                makeBookingResponse.setVoucher(voucher);
            }
        }
        return makeBookingResponse;
    }

    public static BeMyGuestCheckBookingRQ tobeMyGuestCheckBookingRQ(GetProductPriceRQ getProductPriceRQ) {

        BeMyGuestCheckBookingRQ beMyGuestCheckBookingRQ = new BeMyGuestCheckBookingRQ();
        
        if (getProductPriceRQ != null) {
            String[] uuIDs = BeMyGuestUtil.getProductTypeAndTimeSlotUUIDs(getProductPriceRQ.getVendorProductOptionId());
            if (uuIDs.length == 0) {
                beMyGuestCheckBookingRQ.setProductTypeUuid(getProductPriceRQ.getVendorProductOptionId());

            } else {
                beMyGuestCheckBookingRQ.setProductTypeUuid(uuIDs[0]);
                beMyGuestCheckBookingRQ.setTimeSlotUuid(uuIDs[1]);

            }
            beMyGuestCheckBookingRQ.setCurrencyCode(US_CURRENCY_CODE);
            beMyGuestCheckBookingRQ.setArrivalDate(getProductPriceRQ.getBookingDate().toString());
            beMyGuestCheckBookingRQ.setUsePromotion(false);

            List<Quantity> quantities = getProductPriceRQ.getQuantities();
            if (!quantities.isEmpty()) {
                for (Quantity qty : quantities) {
                    if (qty.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {
                        beMyGuestCheckBookingRQ.setPax(qty.getQuantity());

                    } else if (qty.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId()) {
                        beMyGuestCheckBookingRQ.setChildren(qty.getQuantity());
                    }
                }
            }
        }
        return beMyGuestCheckBookingRQ;
    }

    public static GetProductPriceRS toGetProductPriceRS(BeMyGuestCheckBookingRS beMyGuestCheckBookingRS) {

        GetProductPriceRS getProductPriceRS = new GetProductPriceRS();
        ResultType resultType = new ResultType();
        Total total = new Total();        
        
        if (beMyGuestCheckBookingRS != null && beMyGuestCheckBookingRS.getData() != null) {
            total.setCurrency(beMyGuestCheckBookingRS.getData().getRequestCurrencyCode());
            total.setCurrencyCode(beMyGuestCheckBookingRS.getData().getRequestCurrencyCode());
            total.setFinalTotal(beMyGuestCheckBookingRS.getData().getTotalAmountRequestCurrency());
            total.setMerchantTotal(beMyGuestCheckBookingRS.getData().getTotalAmountRequestCurrency());
            total.setRetailTotal(beMyGuestCheckBookingRS.getData().getTotalAmountRequestCurrency());
            getProductPriceRS.setTotal(total);
            
            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
            getProductPriceRS.setResultType(resultType);
        }
        return getProductPriceRS;
    }

    public static BookingVoucherRS toVoucherResponse(BmgGetBookingStatusResponse bmgGetBookingStatusResponse) {

        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();
        Voucher voucher = new Voucher();
        ResultType resultType = new ResultType();

        if (bmgGetBookingStatusResponse.getData().getConfirmationEmailFiles() != null
                && !bmgGetBookingStatusResponse.getData().getConfirmationEmailFiles().isEmpty()) {
            voucher.setUrls(bmgGetBookingStatusResponse.getData().getConfirmationEmailFiles());
            voucher.setReference(bmgGetBookingStatusResponse.getData().getUuid());
            voucher.setType(VoucherType.MULTI_URL);
            bookingVoucherRS.setVoucher(voucher);

            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

        } else {
            resultType.setCode(VendorErrorCode.VOUCHER_NOT_FOUND_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VOUCHER_NOT_FOUND_ERROR.getMsg());

        }
        bookingVoucherRS.setResultType(resultType);
        return bookingVoucherRS;
    }

    
}
