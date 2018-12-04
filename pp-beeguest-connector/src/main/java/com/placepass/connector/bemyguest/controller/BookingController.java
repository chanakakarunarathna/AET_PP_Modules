package com.placepass.connector.bemyguest.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.connector.bemyguest.application.booking.BookingService;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.exutil.HTTPResponseHandler;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingController extends HTTPResponseHandler {

    private Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

	@ApiOperation(value = "getProductPrice", nickname = "getProductPrice")
	// Documenting status codes (these are samples)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GetProductPriceRS.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@RequestMapping(value = "/booking/price", method = RequestMethod.POST)
	@ResponseBody
	public GetProductPriceRS getProductPrice(@Valid @RequestBody GetProductPriceRQ getProductPriceRQ,
			HttpServletResponse response) {

	    logger.info("Received Product Price Request");
		return bookingService.getProductPrice(getProductPriceRQ);
	}

    @ApiOperation(value = "makeBooking", nickname = "makeBooking")
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = MakeBookingRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/booking/create", method = RequestMethod.POST)
    @ResponseBody
    public MakeBookingRS makeBooking(@Valid @RequestBody MakeBookingRQ makeBookingRQ, HttpServletResponse response) {

        logger.info("Received Booking Request");
        return bookingService.makeBooking(makeBookingRQ);
    }

	@ApiOperation(value = "getBookingQuestions", nickname = "getBookingQuestions")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path"),
			@ApiImplicitParam(name = "currencyCode", value = "Currency Code", required = false, dataType = "String", paramType = "query", defaultValue = "USD")

	})
	// Documenting status codes (these are samples)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GetBookingQuestionsRS.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@RequestMapping(value = "/booking/{productid}/bookingquestions", method = RequestMethod.GET)
	@ResponseBody
	public GetBookingQuestionsRS getBookingQuestions(@PathVariable("productid") String productId,
			@RequestParam(value = "currencyCode", required = true, defaultValue = "USD") String currencyCode,
			HttpServletRequest request, HttpServletResponse response) {
	    
	    logger.info("Received Booking Questions Request");
		super.setStatusHeadersToSuccess(response);
		return bookingService.getBookingQuestions(productId, currencyCode);
	}
	
	@ApiOperation(value = "getVoucherDetails", nickname = "getVoucherDetails")
    // Documenting status codes (these are samples)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = BookingVoucherRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request") })
    @RequestMapping(value = "/booking/voucher", method = RequestMethod.POST)
    @ResponseBody
    public BookingVoucherRS getVoucherDetails(@Valid @RequestBody BookingVoucherRQ bookingVoucherRQ, HttpServletResponse response) {

	    logger.info("Received Voucher Details Request");
        return bookingService.getVoucherDetails(bookingVoucherRQ);
    }
	
	@ApiOperation(value = "cancelBooking", nickname = "cancelBooking")
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = CancelBookingRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/booking/cancel", method = RequestMethod.POST)
    @ResponseBody
    public CancelBookingRS cancelBooking(@Valid @RequestBody CancelBookingRQ cancelBookingRQ, HttpServletResponse response) {

        logger.info("Received Cancel Booking Request");
        return bookingService.cancelBooking(cancelBookingRQ);
    }

}
