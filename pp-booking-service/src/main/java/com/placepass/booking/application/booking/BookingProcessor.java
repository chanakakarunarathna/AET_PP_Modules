package com.placepass.booking.application.booking;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.placepass.booking.domain.booking.cancel.VendorCancellationReasons;
import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.booking.infrastructure.VendorBookingServiceImpl;
import com.placepass.connector.common.booking.BookingStatusRQ;
import com.placepass.connector.common.booking.BookingStatusRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.common.ResultType;
import com.placepass.utils.bookingstatus.ConnectorBookingStatus;
import com.placepass.utils.vendorproduct.Vendor;

@Service
public class BookingProcessor {

    private static final Logger logger = LoggerFactory.getLogger(BookingProcessor.class);

    @Value("${com.placepass.bookingservice.bookingsimulation}")
    private boolean simulation = false;

    @Autowired
    private VendorBookingServiceImpl bookingService;

    public MakeBookingRS makeBooking(MakeBookingRQ makeBookingRQ, Vendor vendor) {

        MakeBookingRS response = null;

        logger.debug("MakeBookingRQ JSON : {}", new Gson().toJson(makeBookingRQ));

        try {
            if (!simulation) {
                logger.info("Making booking request in LIVE MODE");
                response = bookingService.makeBooking(makeBookingRQ, vendor);

            } else {
                logger.info("Making booking request in SIMULATION MODE");
                response = mockBookingResponse(makeBookingRQ, vendor);
            }
        } catch (Exception e) {

            logger.error("Exception occured on Make Booking while communicating with vendor connector", e);
            ResultType resultType = new ResultType();

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<String, String>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message",
                    VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR.getMsg() + ", ex-message: " + e.getMessage());
            resultType.setExtendedAttributes(extendedAttributes);

            response = new MakeBookingRS();
            response.setResultType(resultType);
        }

        logger.debug("MakeBookingRS JSON: {}", new Gson().toJson(response));

        return response;

    }

    public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRequest, Vendor vendor) {
        CancelBookingRS response = null;

        logger.debug("CancelBookingCRQ JSON : {}", new Gson().toJson(cancelBookingRequest));

        try {
            if (!simulation) {
                response = bookingService.cancelBooking(cancelBookingRequest, vendor);
            } else {
                response = mockCancelBookingResponse(cancelBookingRequest, vendor);
            }

        } catch (Exception e) {

            logger.error("Exception occured on Cancel Booking while communicating with vendor connector", e);
            ResultType resultType = new ResultType();
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR.getId());
            resultType.setMessage(
                    VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR.getMsg() + ", ex-message: " + e.getMessage());
            response = new CancelBookingRS();
            response.setResultType(resultType);
        }

        // If response is null it could be a potential timeout.
        if (response == null) {

            logger.error(
                    "Cancel Booking received null response from vendor connector. Possibly a timeout occured processing the Cancel Booking Request");
            ResultType resultType = new ResultType();
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTOR_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTOR_TIMEOUT_ERROR.getMsg());
            response = new CancelBookingRS();
            response.setResultType(resultType);

        }

        logger.debug("CancelBookingCRS JSON: {}", new Gson().toJson(response));

        return response;
    }

    /**
     * Mock booking response. //TODO - need to mock failure responses as well
     *
     * @param makeBookingRQ the make booking RQ
     * @param vendor the vendor
     * @return the make booking RS
     */
    private MakeBookingRS mockBookingResponse(MakeBookingRQ makeBookingRQ, Vendor vendor) {

        MakeBookingRS bookingRS = new MakeBookingRS();
        ResultType resultType = new ResultType();
        if ("bookingfail@aeturnum.com".equals(makeBookingRQ.getBookerDetails().getEmail())) {
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage("Booking failed on simulation mode");
        } else if ("testplacepassaetrunum@gmail.com".equals(makeBookingRQ.getBookerDetails().getEmail())) {
            resultType.setCode(VendorErrorCode.PENDING.getId());
            resultType.setMessage("101:Booking is Pending - ");
        } else {
            resultType.setCode(VendorErrorCode.CONFIRMED.getId());
            resultType.setMessage(VendorErrorCode.CONFIRMED.getMsg());

        }
        bookingRS.setBookingId(makeBookingRQ.getBookingId());
        bookingRS.setCurrency(makeBookingRQ.getTotal().getCurrency());
        bookingRS.setReferenceNumber("0000");
        bookingRS.setTotalAmount(makeBookingRQ.getTotal().getFinalTotal());
        bookingRS.setResultType(resultType);
        return bookingRS;
    }

    private CancelBookingRS mockCancelBookingResponse(CancelBookingRQ cancelBookingCRQ, Vendor vendor) {

        CancelBookingRS cancelBookingCRS = new CancelBookingRS();
        ResultType resultType = new ResultType();
        
        //If we use 00 as a cancellation reason code,its redirect to negative simulation of force cancellation scenario 
        if(VendorCancellationReasons.getViatorCancellationReasons().get(cancelBookingCRQ.getCancellationReasonCode()).equals("Testing")) {
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            cancelBookingCRS.setBookingId(cancelBookingCRQ.getBookingId());
            cancelBookingCRS.setCancellationFee(0F);
            cancelBookingCRS.setCancelledBookingItems(null);
            cancelBookingCRS.setBookingReferenceNo("");
            cancelBookingCRS.setResultType(resultType);
        } else {
            resultType.setCode(VendorErrorCode.SUCCESS.getId());
            resultType.setMessage(VendorErrorCode.SUCCESS.getMsg());
            cancelBookingCRS.setBookingId(cancelBookingCRQ.getBookingId());
            cancelBookingCRS.setCancellationFee(1F);
            cancelBookingCRS.setCancelledBookingItems(null);
            cancelBookingCRS.setBookingReferenceNo("MM123456");
            cancelBookingCRS.setResultType(resultType);
        }
        
        return cancelBookingCRS;
    }

    public BookingStatusRS getBookingStatus(Vendor vendor, String vendorBookingRefId, String bookerEmail,
            String bookingStatus) {

        BookingStatusRQ bookingStatusRQ = new BookingStatusRQ();
        bookingStatusRQ.setReferenceNumber(vendorBookingRefId);
        bookingStatusRQ.setBookerEmail(bookerEmail);

        if (PlatformStatus.SUCCESS.name().equals(bookingStatus)) {
            bookingStatusRQ.setStatus(ConnectorBookingStatus.CONFIRMED);
        } else if (PlatformStatus.PENDING.name().equals(bookingStatus)) {
            bookingStatusRQ.setStatus(ConnectorBookingStatus.PENDING);
        } else if (PlatformStatus.BOOKING_FAILED.name().equals(bookingStatus)) {
            bookingStatusRQ.setStatus(ConnectorBookingStatus.FAILED);
        } else if (PlatformStatus.CANCELLED.name().equals(bookingStatus)) {
            bookingStatusRQ.setStatus(ConnectorBookingStatus.CANCELLED);
        } else if (PlatformStatus.BOOKING_REJECTED.name().equals(bookingStatus)) {
            bookingStatusRQ.setStatus(ConnectorBookingStatus.REJECTED);
        }

        BookingStatusRS response = null;

        logger.debug("BookingStatusRQ JSON : {}", new Gson().toJson(bookingStatusRQ));

        try {
            if (!simulation) {
                logger.info("Get Booking Status request in LIVE MODE");
                response = bookingService.getBookingStatus(bookingStatusRQ, vendor);

            } else {
                logger.info("Get Booking Status request in SIMULATION MODE");
                response = mockBookingStatusResponse(bookingStatusRQ, vendor);
            }
        } catch (Exception e) {
            logger.error("Exception occured on Get Booking Status while communicating with vendor connector", e);
            ResultType resultType = new ResultType();
            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());
            Map<String, String> extendedAttributes = new HashMap<String, String>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message",
                    VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR.getMsg() + ", ex-message: " + e.getMessage());
            resultType.setExtendedAttributes(extendedAttributes);
            response = new BookingStatusRS();
            response.setResultType(resultType);
        }

        logger.debug("BookingStatusRS JSON: {}", new Gson().toJson(response));
        return response;
    }

    private BookingStatusRS mockBookingStatusResponse(BookingStatusRQ bookingStatusRQ, Vendor vendor) {
        BookingStatusRS bookingRS = new BookingStatusRS();
        ResultType resultType = new ResultType();

        if (bookingStatusRQ.getReferenceNumber().equals("ref_sucess")) {
            bookingRS.setNewStatus(ConnectorBookingStatus.CONFIRMED);
            resultType.setCode(VendorErrorCode.CONFIRMED.getId());
            resultType.setMessage(VendorErrorCode.CONFIRMED.getMsg());
        } else if (bookingStatusRQ.getReferenceNumber().equals("ref_pending")) {
            bookingRS.setNewStatus(ConnectorBookingStatus.PENDING);
            resultType.setCode(VendorErrorCode.PENDING.getId());
            resultType.setMessage(VendorErrorCode.PENDING.getMsg());
        } else if (bookingStatusRQ.getReferenceNumber().equals("ref_rejected")) {
            bookingRS.setNewStatus(ConnectorBookingStatus.REJECTED);
            resultType.setCode(VendorErrorCode.REJECTED.getId());
            resultType.setMessage(VendorErrorCode.REJECTED.getMsg());
        } else if (bookingStatusRQ.getReferenceNumber().equals("ref_failed")) {
            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());
        } else if (bookingStatusRQ.getReferenceNumber().equals("ref_cancelled")) {
            bookingRS.setNewStatus(ConnectorBookingStatus.CANCELLED);
            resultType.setCode(VendorErrorCode.CANCELLED.getId());
            resultType.setMessage(VendorErrorCode.CANCELLED.getMsg());
        } else if (bookingStatusRQ.getReferenceNumber().equals("ref_unknown")) {
            bookingRS.setNewStatus(ConnectorBookingStatus.UNKNOWN);
            resultType.setCode(VendorErrorCode.UNKNOWN.getId());
            resultType.setMessage(VendorErrorCode.UNKNOWN.getMsg());
            Map<String, String> extendedAttributes = new HashMap<String, String>();
            extendedAttributes.put("vendorStatus", "ABC");
            resultType.setExtendedAttributes(extendedAttributes);
        }
        bookingRS.setResultType(resultType);
        return bookingRS;
    }
}
