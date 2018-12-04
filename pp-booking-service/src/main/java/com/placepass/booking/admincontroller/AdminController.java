package com.placepass.booking.admincontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.booking.application.booking.BookingApplicationService;
import com.placepass.booking.application.booking.dto.CancelBookingRQ;
import com.placepass.booking.application.booking.dto.CancelBookingRS;
import com.placepass.booking.application.booking.dto.ManualCancelBookingRQ;
import com.placepass.booking.application.booking.dto.ManualCancelBookingRS;
import com.placepass.booking.application.booking.admin.dto.GetBookingRS;
import com.placepass.booking.application.booking.dto.ResendEmailRQ;
import com.placepass.booking.application.booking.admin.dto.SearchBookingsRS;
import com.placepass.booking.application.booking.dto.VendorListDTO;
import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private BookingApplicationService applicationService;

    @ApiOperation(httpMethod = "GET", value = "Search bookings", nickname = "Search bookings")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "confnumber", value = "Confirmation Number", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "firstname", value = "First Name", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "lastname", value = "Last Name", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phonenumber", value = "Phone Number", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "countryisocode", value = "Country ISO Code", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "email", value = "Email", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "startactivitydate", value = "Activity Start Date", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "endactivitydate", value = "Activity End Date", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "startbookingdate", value = "Start Booking Date", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "endbookingdate", value = "End Booking Date", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "geolocation", value = "Geo Location", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productid", value = "Product Id", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "vendor", value = "Vendor", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "status", value = "Status", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "goodstanding", value = "Good Standing", required = false, dataType = "boolean", paramType = "query", allowableValues = "true, false"),
        @ApiImplicitParam(name = "searchoperator", value = "Search Operator", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "hitsperpage", value = "Hits Per Page", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pagenumber", value = "Page Number", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = SearchBookingsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/adminsearchbookings", method = RequestMethod.GET)
    @ResponseBody
    public SearchBookingsRS searchBookings(HttpServletResponse response, HttpServletRequest request,
            @Valid @RequestParam(value = "confnumber", required = false) String confNumber,
            @Valid @RequestParam(value = "firstname", required = false) String firstName,
            @Valid @RequestParam(value = "lastname", required = false) String lastName,
            @Valid @RequestParam(value = "phonenumber", required = false) String phoneNumber,
            @Valid @RequestParam(value = "countryisocode", required = false) String countryISOCode,
            @Valid @RequestParam(value = "email", required = false) String email,
            @Valid @RequestParam(value = "startactivitydate", required = false) String startActivityDate,
            @Valid @RequestParam(value = "endactivitydate", required = false) String endActivityDate,
            @Valid @RequestParam(value = "startbookingdate", required = false) String startBookingDate,
            @Valid @RequestParam(value = "endbookingdate", required = false) String endBookingDate,
            @Valid @RequestParam(value = "geolocation", required = false) String geoLocation,
            @Valid @RequestParam(value = "productid", required = false) String productId,
            @Valid @RequestParam(value = "vendor", required = false) String vendor,
            @Valid @RequestParam(value = "status", required = false) String status,
            @Valid @RequestParam(value = "goodstanding", required = false, defaultValue = "true") boolean goodStanding,
            @Valid @RequestParam(value = "searchoperator", required = true) String searchOperator,
            @Valid @RequestParam(value = "hitsperpage", required = true) Integer hitsPerPage,
            @Valid @RequestParam(value = "pagenumber", required = true) Integer pageNumber,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.SEARCH_OPERATOR.name(), searchOperator);
        logData.put(PlatformLoggingKey.HITS_PER_PAGE.name(), hitsPerPage);
        logData.put(PlatformLoggingKey.PAGE_NUMBER.name(), pageNumber);

        logger.info("Received 'Admin Search Bookings' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        SearchBookingsRS searchBookingsRS = applicationService.searchBookings(partnerId, confNumber, firstName,
                lastName, phoneNumber, countryISOCode, email, startActivityDate, endActivityDate, startBookingDate,
                endBookingDate, geoLocation, productId, vendor, status, goodStanding, searchOperator, hitsPerPage,
                pageNumber);

        logger.info("Returning 'Admin Search Bookings' Response.");

        return searchBookingsRS;

    }

    @ApiOperation(httpMethod = "PUT", value = "Cancel Booking", nickname = "Cancel Booking")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookingid", value = "Booking ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = CancelBookingRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "bookings/{bookingid}/cancel", method = RequestMethod.PUT)
    @ResponseBody
    public CancelBookingRS cancelBooking(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("bookingid") String bookingId, @RequestBody @Valid CancelBookingRQ cancelBookingRequest,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);

        logger.info("Received 'Cancel Booking' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        CancelBookingRS cancelBookingRS = applicationService.cancelBooking(partnerId, bookingId, cancelBookingRequest);

        logger.info("Returning 'Cancel Booking' Response.");

        return cancelBookingRS;

    }

    /*
     * Expose a new endpoint to mark a PENDING booking as SUCCESS or FALIED. If failed we need to call stripe to refund.
     * Only pending bookings can be modified with this endpoint. Throw 400 bad request if status of the booking is
     * already SUCCESS or FAILURE. If success we need to make sure the voucher url is properly set. Also mark
     * isInGoodStanding to true for both SUCCESS or FAILURE(after refund success) status. This is an admin API endpoint
     * and it only needs to be exposed via admin API. This endpoint will be used by the backend job to periodically
     * check the status of pending bookings and update the status.
     */
    @ApiOperation(httpMethod = "GET", value = "Mark PENDING booking as CONFIRMED or FAILED (REJECTED)", nickname = "Mark PENDING booking as CONFIRMED or FAILED (REJECTED)")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "newstatus", value = "newstatus", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/bookings/{bookingid}/changependingstatus", method = RequestMethod.GET)
    public void changePendingBookingStatus(HttpServletResponse response, HttpServletRequest request,
            @RequestParam(value = "newstatus", required = true) String newStatus,
            @PathVariable("bookingid") String bookingId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        logger.info("Received 'Change Pending Status' Request.");
        setStatusHeadersToSuccess(response);
        applicationService.changePendingBookingStatus(partnerId, bookingId, newStatus);

        logger.info("Completed processing 'Change Pending Status' Request.");

    }

    @ApiOperation(httpMethod = "POST", value = "Publish Event", nickname = "Publish Event")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookingid", value = "Booking ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "eventname", value = "Event Name", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @RequestMapping(value = "bookings/{bookingid}/publishevent", method = RequestMethod.POST)
    @ResponseBody
    public void publishEvent(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("bookingid") String bookingId, @RequestParam("eventname") String eventName,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);

        logger.info("Received 'Email Event Publish' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        applicationService.publishEvent(partnerId, bookingId, null);

        logger.info("Returning 'Email Event Publish' Response.");

    }

    @ApiOperation(httpMethod = "POST", value = "Resend Email", nickname = "Resend Email")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookingid", value = "Booking ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @RequestMapping(value = "bookings/{bookingid}/resendEmail", method = RequestMethod.POST)
    @ResponseBody
    public void resendEmail(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("bookingid") String bookingId,
            @RequestHeader(value = "partner-id", required = true) String partnerId,
            @RequestBody ResendEmailRQ resendEmailRequest) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);

        logger.info("Received 'Resend Email' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        applicationService.resendEmail(partnerId, bookingId, resendEmailRequest.getEmailType(),
                resendEmailRequest.getReceiverEmail());

        logger.info("Returning 'Resend Email' Response.");

    }

    @ApiOperation(httpMethod = "GET", value = "Admin Get Single Booking", nickname = "Admin Get Single Booking")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookingid", value = "Booking ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetBookingRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "admin/bookings/{bookingid}", method = RequestMethod.GET)
    @ResponseBody
    public GetBookingRS getBooking(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("bookingid") String bookingId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);

        logger.info("Received 'Admin Get Single Booking' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        GetBookingRS getBookingRS = applicationService.adminGetBooking(bookingId, partnerId);

        logger.info("Returning 'Admin Get Single Booking' Response.");

        return getBookingRS;

    }

    @ApiOperation(httpMethod = "GET", value = "Retrieve Vendor List", nickname = "Retrieve Vendor List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = VendorListDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/admin/vendors", method = RequestMethod.GET)
    @ResponseBody
    public VendorListDTO retrieveVendorList(HttpServletResponse response, HttpServletRequest request) {

        logger.info("Retrieve Vendor List. {}");
        setStatusHeadersToSuccess(response);
        VendorListDTO vendorListDTO = applicationService.retrieveVendorList();
        logger.info("Completed Retrieve Vendor List");
        return vendorListDTO;

    }

    @ApiOperation(httpMethod = "PUT", value = "Manual Cancel Booking", nickname = "Manual Cancel Booking")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookingid", value = "Booking ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = ManualCancelBookingRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "bookings/{bookingid}/manualcancel", method = RequestMethod.PUT)
    @ResponseBody
    public ManualCancelBookingRS manualCancelBooking(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("bookingid") String bookingId,
            @RequestBody @Valid ManualCancelBookingRQ manualCancelBookingRequest,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);

        logger.info("Received 'Manual Cancel Booking' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        ManualCancelBookingRS manualCancelBookingRS = applicationService.manualCancelBooking(partnerId, bookingId,
                manualCancelBookingRequest);

        logger.info("Returning 'Manual Cancel Booking' Response.");

        return manualCancelBookingRS;

    }

}
