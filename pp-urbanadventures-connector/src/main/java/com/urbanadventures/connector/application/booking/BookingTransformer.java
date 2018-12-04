package com.urbanadventures.connector.application.booking;

import java.util.ArrayList;
import java.util.List;

import com.placepass.connector.common.booking.Booker;
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
import com.placepass.connector.common.booking.Traveler;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.booking.Voucher;
import com.placepass.connector.common.common.ResultType;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;
import com.placepass.utils.voucher.VoucherType;
import com.urbanadventures.connector.application.common.UrbanAdventuresUtil;
import com.urbanadventures.connector.domain.urbanadventures.ClsBookRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsBookRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsCancelBookingRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsCancelBookingRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetBookingVoucherRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetBookingVoucherRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTravellerInfo;
import com.urbanadventures.connector.domain.urbanadventures.ClsTravellerOther;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripPrice;

public class BookingTransformer {

    public static Total toTripTotal(ClsTripPrice clsTripPrice) {

        Total total = new Total();
        total.setCurrency(clsTripPrice.getCurrency());
        total.setCurrencyCode(clsTripPrice.getCurrency());
        total.setRetailTotal(AmountFormatter.floatToFloatRounding(clsTripPrice.getTotalAmount()));
        total.setFinalTotal(AmountFormatter.floatToFloatRounding(clsTripPrice.getTotalAmount()));
        Float merchantTotal = clsTripPrice.getTotalAmount() - clsTripPrice.getCommission();
        total.setMerchantTotal(AmountFormatter.floatToFloatRounding(merchantTotal));
        return total;
    }

    public static ClsGetPriceRQ toClsGetPriceRQ(GetProductPriceRQ getProductPriceRQ) {

        ClsGetPriceRQ clsGetPriceRQ = new ClsGetPriceRQ();
        clsGetPriceRQ.setTripCode(getProductPriceRQ.getVendorProductId());
        clsGetPriceRQ.setDepId(Integer.valueOf(getProductPriceRQ.getVendorProductOptionId()));
        String depDate = UrbanAdventuresUtil.getFormattedDate(getProductPriceRQ.getBookingDate());
        clsGetPriceRQ.setDepDate(depDate);

        for (Quantity quantity : getProductPriceRQ.getQuantities()) {

            if (quantity.getAgeBandLabel().equals(PlacePassAgeBandType.ADULT.name()))
                clsGetPriceRQ.setNumAdult(quantity.getQuantity());

            if (quantity.getAgeBandLabel().equals(PlacePassAgeBandType.CHILD.name()))
                clsGetPriceRQ.setNumChild(quantity.getQuantity());
        }
        return clsGetPriceRQ;
    }

    public static ClsBookRQ toClsBookRQ(MakeBookingRQ makeBookingRQ) {

        ClsBookRQ uaClsBookRQ = new ClsBookRQ();
        BookingOption ppBookingOption = makeBookingRQ.getBookingOptions().get(0);
        uaClsBookRQ.setTripCode(ppBookingOption.getVendorProductId());
        uaClsBookRQ.setDepDate(UrbanAdventuresUtil.getFormattedDate(ppBookingOption.getBookingDate()));
        uaClsBookRQ.setDepId(Integer.valueOf(ppBookingOption.getVendorProductOptionId()));

        for (Quantity ppQuantity : ppBookingOption.getQuantities()) {

            if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.ADULT.name()))
                uaClsBookRQ.setNumAdult(ppQuantity.getQuantity());

            if (ppQuantity.getAgeBandLabel().equals(PlacePassAgeBandType.CHILD.name()))
                uaClsBookRQ.setNumChild(ppQuantity.getQuantity());
        }

        // Filling booker details
        Booker ppBooker = makeBookingRQ.getBookerDetails();
        ClsTravellerInfo uaClsTravellerInfo = new ClsTravellerInfo();
        uaClsTravellerInfo.setSalutation(ppBooker.getTitle());
        uaClsTravellerInfo.setFirstName(ppBooker.getFirstName());
        uaClsTravellerInfo.setLastName(ppBooker.getLastName());
        uaClsTravellerInfo.setDOB(ppBooker.getDateOfBirth());
        uaClsTravellerInfo.setStandardCountryID(Integer.valueOf(ppBooker.getCountryCode()));
        uaClsTravellerInfo.setPhone(ppBooker.getPhoneNumber());
        uaClsTravellerInfo.setEmail(ppBooker.getEmail());
        uaClsBookRQ.setTraveller(uaClsTravellerInfo);

        // Filling traveler details
        for (Traveler ppTraveler : ppBookingOption.getTraverlerDetails()) {
            ClsTravellerOther uaClsTravellerOther = new ClsTravellerOther();
            uaClsTravellerOther.setSalutation(ppTraveler.getTitle());
            uaClsTravellerOther.setFirstName(ppTraveler.getFirstName());
            uaClsTravellerOther.setLastName(ppTraveler.getLastName());
            if (ppTraveler.getAgeBandId() == PlacePassAgeBandType.ADULT.ageBandId
                    || ppTraveler.getAgeBandId() == PlacePassAgeBandType.SENIOR.ageBandId) {
                uaClsTravellerOther.setIsAdult(true);
            } else {
                uaClsTravellerOther.setIsAdult(false);
            }
            uaClsBookRQ.getOther().add(uaClsTravellerOther);
        }

        if (ppBookingOption.getIsHotelPickUpEligible()) {
            if (ppBookingOption.getPickupLocation() != null && ppBookingOption.getPickupLocation().getId() != null) {
                uaClsBookRQ.setHotelLocation(ppBookingOption.getPickupLocation().getLocationName());
            } else {
                uaClsBookRQ.setHotelLocation("TBD");
            }
        }
        return uaClsBookRQ;
    }

    public static ClsGetBookingVoucherRQ toClsGetBookingVoucherRQ(BookingVoucherRQ bookingVoucherRQ) {

        ClsGetBookingVoucherRQ clsGetBookingVoucherRQ = new ClsGetBookingVoucherRQ();
        clsGetBookingVoucherRQ.setRefNo(bookingVoucherRQ.getReferenceNumber());

        return clsGetBookingVoucherRQ;
    }

    public static final GetProductPriceRS toGetProductPriceRS(ClsGetPriceRS clsGetPriceRS, ResultType resultType) {

        GetProductPriceRS getProductPriceRS = new GetProductPriceRS();

        if (resultType.getCode() == VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {

            ClsTripPrice clsTripPrice = clsGetPriceRS.getPriceInfo();
            getProductPriceRS.setTotal(BookingTransformer.toTripTotal(clsTripPrice));
        }
        getProductPriceRS.setResultType(resultType);
        return getProductPriceRS;
    }

    public static MakeBookingRS toMakeBookingRS(ClsBookRS clsBookRS, ClsGetBookingVoucherRS clsGetBookingVoucherRS,
            MakeBookingRQ makeBookingRQ, ResultType resultType) {

        MakeBookingRS makeBookingRS = new MakeBookingRS();
        if (resultType.getCode() == VendorErrorCode.CONFIRMED.getId()) {
            makeBookingRS.setBookingId(makeBookingRQ.getBookingId());
            makeBookingRS.setCurrency(clsBookRS.getCurrency());
            makeBookingRS.setReferenceNumber(clsBookRS.getRefNo());
            makeBookingRS.setTotalAmount(clsBookRS.getTotalAmount());

            if (clsGetBookingVoucherRS != null) {
                makeBookingRS.setVoucher(toVoucher(clsGetBookingVoucherRS));
            }
        }
        makeBookingRS.setResultType(resultType);
        return makeBookingRS;
    }

    public static ClsGetBookingVoucherRQ toClsGetBookingVoucherRQ(ClsBookRS clsBookRS) {

        ClsGetBookingVoucherRQ clsGetBookingVoucherRQ = new ClsGetBookingVoucherRQ();
        clsGetBookingVoucherRQ.setRefNo(clsBookRS.getRefNo());

        return clsGetBookingVoucherRQ;
    }

    public static BookingVoucherRS toBookingVoucherRS(ClsGetBookingVoucherRS clsGetBookingVoucherRS) {

        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();
        ResultType resultType = new ResultType();

        if (clsGetBookingVoucherRS.getOpResult().getCode() == 0) {
            bookingVoucherRS.setVoucher(toVoucher(clsGetBookingVoucherRS));
            resultType.setCode(0);
            resultType.setMessage("");

        } else {
            resultType.setCode(clsGetBookingVoucherRS.getOpResult().getCode());
            resultType.setMessage(clsGetBookingVoucherRS.getOpResult().getMessage());
        }
        bookingVoucherRS.setResultType(resultType);

        return bookingVoucherRS;
    }

    public static ClsCancelBookingRQ toClsCancelBookingRQ(CancelBookingRQ cancelBookingRQ) {

        ClsCancelBookingRQ clsCancelBookingRQ = new ClsCancelBookingRQ();
        clsCancelBookingRQ.setRefNo(cancelBookingRQ.getBookingReferenceId());

        return clsCancelBookingRQ;
    }

    public static CancelBookingRS toCancelBookingRS(ClsCancelBookingRS clsCancelBookingRS) {

        CancelBookingRS cancelBookingRS = new CancelBookingRS();
        ResultType resultType = new ResultType();

        if (clsCancelBookingRS.getOpResult().getCode() == 0) {
            cancelBookingRS.setBookingReferenceNo(clsCancelBookingRS.getRefNo());
            cancelBookingRS.setCancellationFee(clsCancelBookingRS.getCancellationFee());
            resultType.setCode(0);
            resultType.setMessage("");

        } else {
            resultType.setCode(VendorErrorCode.CANCEL_UNKNOWN.getId());
            resultType.setMessage(
                    VendorErrorCode.CANCEL_UNKNOWN.getMsg() + " - " + clsCancelBookingRS.getOpResult().getCode() + " : "
                            + clsCancelBookingRS.getOpResult().getMessage());
        }
        cancelBookingRS.setResultType(resultType);

        return cancelBookingRS;
    }

    public static Voucher toVoucher(ClsGetBookingVoucherRS clsGetBookingVoucherRS) {

        Voucher voucher = new Voucher();
        List<String> voucherUrls = new ArrayList<>();
        voucher.setReference(clsGetBookingVoucherRS.getRefNo());
        voucherUrls.add(clsGetBookingVoucherRS.getLinkDownLoad());
        voucher.setUrls(voucherUrls);
        voucher.setType(VoucherType.SINGLE_URL);
        return voucher;
    }

}
