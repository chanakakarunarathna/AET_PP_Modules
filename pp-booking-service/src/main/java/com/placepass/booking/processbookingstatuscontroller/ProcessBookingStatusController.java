package com.placepass.booking.processbookingstatuscontroller;

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
import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.placepass.booking.application.booking.dto.PendingBookingsRS;
import com.placepass.booking.application.booking.dto.ProcessBookingStatusRQ;
import com.placepass.booking.application.booking.dto.ProcessBookingStatusRS;
import com.placepass.booking.application.common.BookingServiceUtil;
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utills.email.ValidateEmail;

import org.springframework.util.StringUtils;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessBookingStatusController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProcessBookingStatusController.class);

    @Autowired
    private BookingApplicationService applicationService;

    @ApiOperation(httpMethod = "GET", value = "Get Pending Bookings", nickname = "Get Pending Bookings")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "hitsperpage", value = "Hits Per Page", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pagenumber", value = "Page Number", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "vendor", value = "Vendor", required = false, dataType = "string", paramType = "query", allowableValues = "MUSEME, GETYGU, VIATOR, URBANA, HADOUT, ISANGO, IFONLY, PROEXP, TIQETS, VIREAL, TKTMST, BEMYGT, CTYDSY, GOBEEE")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PendingBookingsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/bookingstatusprocessor/bookings/pending", method = RequestMethod.GET)
    @ResponseBody
    public PendingBookingsRS getPendingBookings(HttpServletResponse response, HttpServletRequest request,
            @Valid @RequestParam(value = "hitsperpage", required = true) Integer hitsPerPage,
            @Valid @RequestParam(value = "pagenumber", required = true) Integer pageNumber,
            @Valid @RequestParam(value = "vendor", required = false) String vendorName) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.HITS_PER_PAGE.name(), hitsPerPage);
        logData.put(PlatformLoggingKey.PAGE_NUMBER.name(), pageNumber);
        logData.put(PlatformLoggingKey.VENDOR.name(), vendorName);

        logger.info("Received 'Get Pending Bookings' Request. {}", logData);
        
        Vendor vendor = BookingServiceUtil.validateVendor(vendorName);

        setStatusHeadersToSuccess(response);
        PendingBookingsRS pendingBookingsRS = applicationService.getPendingBookings(hitsPerPage, pageNumber, vendor);
        logger.info("Returning 'Get Pending Bookings' Response.");
        return pendingBookingsRS;

    } 
        
    @ApiOperation(httpMethod = "POST", value = "Process Booking Status", nickname = "Process Booking Status")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "bookingid", value = "Booking ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = ProcessBookingStatusRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/bookingstatusprocessor/booking/process/status/{bookingid}", method = RequestMethod.POST)
    @ResponseBody
    public ProcessBookingStatusRS processBookingStatus(HttpServletResponse response, HttpServletRequest request,
             @PathVariable("bookingid") String bookingId,
			 @RequestHeader(value = "partner-id", required = true) String partnerId,
			 @RequestBody ProcessBookingStatusRQ processBookingStatusRequest) {

        logger.info("Received 'Process Booking Status' Request.");
        setStatusHeadersToSuccess(response);
        ProcessBookingStatusRS getBookingStatusRS = applicationService.processBookingStatus(partnerId, bookingId, processBookingStatusRequest.getVendor(), processBookingStatusRequest.getVendorBookingRefId(), processBookingStatusRequest.getBookerEmail(), processBookingStatusRequest.getStatus());
        logger.info("Returning 'Process Booking Status' Response.");
        return getBookingStatusRS;

    }
}
