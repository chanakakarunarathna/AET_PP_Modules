package com.placepass.booking.controller;

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
import com.placepass.booking.application.booking.dto.CreateBookingRQ;
import com.placepass.booking.application.booking.dto.CreateBookingRS;
import com.placepass.booking.application.booking.dto.FindBookingsRS;
import com.placepass.booking.application.booking.dto.GetBookingRS;
import com.placepass.booking.application.booking.dto.GetBookingsHistoryRS;
import com.placepass.booking.application.common.TokenAuthenticationConsts;
import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingApplicationService applicationService;

    @ApiOperation(httpMethod = "POST", value = "Process payment and create booking", nickname = "Process payment and create booking")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true),
        @ApiImplicitParam(name = "authorization", value = "Authorization", dataType = "string", paramType = "header", required = false)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = CreateBookingRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "carts/{cartid}/book", method = RequestMethod.POST)
    @ResponseBody
    public CreateBookingRS createBooking(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("cartid") String cartId, @RequestBody @Valid CreateBookingRQ bookingRequest,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Create Booking' Request. {}", logData);

        String customerId = resolveUserId(request, bookingRequest.getCustomerId());
        bookingRequest.setCustomerId(customerId);
      
        bookingRequest.setPartnerId(partnerId);
        bookingRequest.setCartId(cartId);

        setStatusHeadersToSuccess(response);
        CreateBookingRS createBookingRS = applicationService.processBooking(bookingRequest);

        logger.info("Returning 'Create Booking' Response.");

        return createBookingRS;

    }

    @ApiOperation(httpMethod = "GET", value = "Get Single Booking (for a customer)", nickname = "Get Single Booking (for a customer)")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookingid", value = "Booking ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "customerid", value = "Customer ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true),
        @ApiImplicitParam(name = "authorization", value = "Authorization", dataType = "string", paramType = "header", required = false)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetBookingRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "customers/{customerid}/bookings/{bookingid}", method = RequestMethod.GET)
    @ResponseBody
    public GetBookingRS getCustomerBooking(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("customerid") String customerId, @PathVariable("bookingid") String bookingId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        customerId = resolveUserId(request, customerId);       

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);
        logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), customerId);

        logger.info("Received 'Get Single Booking (for a customer)' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        GetBookingRS getBookingRS = applicationService.getBooking(bookingId, customerId, partnerId);

        logger.info("Returning 'Get Single Booking (for a customer)' Response.");

        return getBookingRS;

    }

    @ApiOperation(httpMethod = "GET", value = "Get Single Booking", nickname = "Get Single Booking")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookingid", value = "Booking ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetBookingRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "bookings/{bookingid}", method = RequestMethod.GET)
    @ResponseBody
    public GetBookingRS getBooking(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("bookingid") String bookingId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKING_ID.name(), bookingId);

        logger.info("Received 'Get Single Booking' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        GetBookingRS getBookingRS = applicationService.getBooking(bookingId, partnerId);

        logger.info("Returning 'Get Single Booking' Response.");

        return getBookingRS;

    }

    @ApiOperation(httpMethod = "GET", value = "View Bookings History", nickname = "View Bookings History")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "customerid", value = "Customer ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "hitsperpage", value = "Hits Per Page", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pagenumber", value = "Page Number", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true),
        @ApiImplicitParam(name = "authorization", value = "Authorization", dataType = "string", paramType = "header", required = false)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetBookingsHistoryRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "customers/{customerid}/bookings", method = RequestMethod.GET)
    @ResponseBody
    public GetBookingsHistoryRS getBookingsHistory(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("customerid") String customerId,
            @Valid @RequestParam(value = "hitsperpage", required = true) Integer hitsPerPage,
            @Valid @RequestParam(value = "pagenumber", required = true) Integer pageNumber,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {
        
        customerId = resolveUserId(request, customerId);       

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), customerId);
        logData.put(PlatformLoggingKey.HITS_PER_PAGE.name(), hitsPerPage);
        logData.put(PlatformLoggingKey.PAGE_NUMBER.name(), pageNumber);

        logger.info("Received 'View Bookings History' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        GetBookingsHistoryRS getBookingsHistoryRS = applicationService.getBookingsHistory(customerId, partnerId,
                hitsPerPage, pageNumber);

        logger.info("Returning 'View Bookings History' Response.");

        return getBookingsHistoryRS;

    }

    @ApiOperation(httpMethod = "GET", value = "Find past booking(by booker email and booking reference)", nickname = "Find past booking(by booker email and booking reference)")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookeremail", value = "Booker Email", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "bookingreference", value = "Booking Reference", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = FindBookingsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/findbookings", method = RequestMethod.GET)
    @ResponseBody
    public FindBookingsRS findBooking(HttpServletResponse response, HttpServletRequest request,
            @Valid @RequestParam(value = "bookeremail", required = true) String bookerEmail,
            @Valid @RequestParam(value = "bookingreference", required = true) String bookingReference,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.BOOKER_EMAIL.name(), bookerEmail);
        logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), bookingReference);

        logger.info("Received 'Find past booking(by booker email and booking reference)' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        FindBookingsRS findBookingsRS = applicationService.findBookingsByEmailBookingReference(partnerId, bookerEmail,
                bookingReference);

        logger.info("Returning 'Find past booking(by booker email and booking reference)' Response.");

        return findBookingsRS;

    }

    private String resolveUserId(HttpServletRequest request, String customerId) {
        String userId = null;
        if (null != request.getAttribute(TokenAuthenticationConsts.USER_ID)) {
            userId = request.getAttribute(TokenAuthenticationConsts.USER_ID).toString();
        }
        userId = (null != userId ? userId : customerId);
        request.removeAttribute(TokenAuthenticationConsts.USER_ID);
        return userId;
    }
}
