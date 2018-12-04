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
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.booking.application.cart.CartApplicationService;
import com.placepass.booking.application.cart.dto.AddBookingOptionRQ;
import com.placepass.booking.application.cart.dto.AddBookingOptionRS;
import com.placepass.booking.application.cart.dto.CreateCartRQ;
import com.placepass.booking.application.cart.dto.CreateCartRS;
import com.placepass.booking.application.cart.dto.DeleteBookingOptionRS;
import com.placepass.booking.application.cart.dto.GetBookingQuestionsRS;
import com.placepass.booking.application.cart.dto.UpdateBookerDetailsRQ;
import com.placepass.booking.application.cart.dto.UpdateBookerDetailsRS;
import com.placepass.booking.application.cart.dto.UpdateBookingAnswerRQ;
import com.placepass.booking.application.cart.dto.UpdateBookingAnswerRS;
import com.placepass.booking.application.cart.dto.UpdateHotelPickupRQ;
import com.placepass.booking.application.cart.dto.UpdateHotelPickupRS;
import com.placepass.booking.application.cart.dto.UpdateLanguageOptionRQ;
import com.placepass.booking.application.cart.dto.UpdateLanguageOptionRS;
import com.placepass.booking.application.cart.dto.UpdateTravelerDetailsRQ;
import com.placepass.booking.application.cart.dto.UpdateTravelerDetailsRS;
import com.placepass.booking.application.cart.dto.ValidateCartRQ;
import com.placepass.booking.application.cart.dto.ValidateCartRS;
import com.placepass.booking.application.cart.dto.ViewCartRS;
import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author chanaka.k
 *
 */
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartApplicationService cartApplicationService;

    /*
     * Create shopping cart
     */

    @ApiOperation(httpMethod = "POST", value = "Create shopping cart", nickname = "Create shopping cart")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = CreateCartRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts", method = RequestMethod.POST)
    @ResponseBody
    public CreateCartRS createCart(HttpServletResponse response, HttpServletRequest request,
            @Valid @RequestBody CreateCartRQ createCartRQ,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);

        logger.info("Received 'Create Shopping Cart' Request. {}", logData);

        CreateCartRS createCartRS = cartApplicationService.createCart(partnerId, createCartRQ);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Create Shopping Cart' Response.");

        return createCartRS;

    }

    /*
     * View shopping cart
     */

    @ApiOperation(httpMethod = "GET", value = "View shopping cart", nickname = "View shopping cart")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = ViewCartRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts/{cartid}", method = RequestMethod.GET)
    @ResponseBody
    public ViewCartRS viewCartDetails(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("cartid") String cartId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'View Shopping Cart' Request. {}", logData);

        ViewCartRS viewCartRS = cartApplicationService.viewCart(partnerId, cartId);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'View Shopping Cart' Response.");

        return viewCartRS;
    }

    /*
     * Remove items from shopping cart
     */

    @ApiOperation(httpMethod = "DELETE", value = "Remove items from shopping cart", nickname = "Remove items from shopping cart")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "bookingoptionid", value = "Booking Option ID", required = true, dataType = "string", paramType = "path", defaultValue = "bo1"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = DeleteBookingOptionRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "carts/{cartid}/bookingoptions/{bookingoptionid}", method = RequestMethod.DELETE)
    @ResponseBody
    public DeleteBookingOptionRS deleteBookingOption(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("cartid") String cartId, @PathVariable("bookingoptionid") String bookingOptionId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);
        logData.put(PlatformLoggingKey.BOOKING_OPTION_ID.name(), bookingOptionId);

        logger.info("Received 'Delete Booking Option' Request. {}", logData);

        DeleteBookingOptionRS deleteBookingOptionRS = cartApplicationService.deleteBookingOption(partnerId, cartId,
                bookingOptionId);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Delete Booking Option' Response.");

        return deleteBookingOptionRS;

    }

    /*
     * Add booking options to Cart
     */

    @ApiOperation(httpMethod = "PUT", value = "Add booking options to Cart", nickname = "Add booking options to Cart")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = AddBookingOptionRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts/{cartid}/bookingoptions", method = RequestMethod.PUT)
    @ResponseBody
    public AddBookingOptionRS addBookingOptions(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("cartid") String cartId, @Valid @RequestBody AddBookingOptionRQ addBookingOptionRQ,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Add Booking Options' Request. {}", logData);

        AddBookingOptionRS addBookingOptionRS = cartApplicationService.addBookingOptions(partnerId, cartId,
                addBookingOptionRQ);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Add Booking Options' Response.");

        return addBookingOptionRS;

    }

    /*
     * Validate Cart
     */

    @ApiOperation(httpMethod = "POST", value = "Validate a Cart", nickname = "Validate a Cart")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = ValidateCartRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts/{cartid}/validate", method = RequestMethod.POST)
    @ResponseBody
    public ValidateCartRS validateCart(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("cartid") String cartId, @Valid @RequestBody ValidateCartRQ validateCartRQ,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Validate Cart' Request. {}", logData);

        ValidateCartRS validateCartRS = cartApplicationService.validateCart(partnerId, cartId, validateCartRQ, null);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Validate Cart' Response.");

        return validateCartRS;

    }

    /*
     * Update booker details
     */

    @ApiOperation(httpMethod = "PUT", value = "Update booker details", nickname = "Update booker details")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UpdateBookerDetailsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts/{cartid}/bookerdetails", method = RequestMethod.PUT)
    @ResponseBody
    public UpdateBookerDetailsRS updateBookerDetails(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("cartid") String cartId, @Valid @RequestBody UpdateBookerDetailsRQ updateBookerDetailsRQ,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Update Booker Details' Request. {}", logData);

        UpdateBookerDetailsRS updateBookerDetailsRS = cartApplicationService.updateBookerDetails(partnerId, cartId,
                updateBookerDetailsRQ);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Update Booker Details' Response.");

        return updateBookerDetailsRS;

    }

    /*
     * Update traveler details
     */

    @ApiOperation(httpMethod = "PUT", value = "Update traveler details", nickname = "Update traveler details")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UpdateTravelerDetailsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts/{cartid}/travelers", method = RequestMethod.PUT)
    @ResponseBody
    public UpdateTravelerDetailsRS updateTravelerDetails(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("cartid") String cartId, @Valid @RequestBody UpdateTravelerDetailsRQ updateTravelerDetailsRQ,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Update Traveler Details' Request. {}", logData);

        UpdateTravelerDetailsRS updateTravelerDetailsRS = cartApplicationService.updateTravelerDetails(partnerId,
                cartId, updateTravelerDetailsRQ);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Update Traveler Details' Response.");

        return updateTravelerDetailsRS;

    }

    /*
     * Get Booking Questions
     * 
     */

    @ApiOperation(httpMethod = "GET", value = "Get booking questions", nickname = "Get booking questions")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetBookingQuestionsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})

    @RequestMapping(value = "/carts/{cartid}/bookingquestions", method = RequestMethod.GET)
    @ResponseBody
    public GetBookingQuestionsRS getBookingQuestions(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("cartid") String cartId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Get Booking Questions' Request. {}", logData);

        GetBookingQuestionsRS getBookingQuestionsRS = cartApplicationService.getBookingQuestions(cartId, partnerId);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Get Booking Questions' Response.");

        return getBookingQuestionsRS;

    }

    /*
     * Update Booking Answers
     * 
     */

    @ApiOperation(httpMethod = "PUT", value = "Update booking answers", nickname = "Update booking answers")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UpdateBookingAnswerRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts/{cartid}/bookinganswers", method = RequestMethod.PUT)
    @ResponseBody
    public UpdateBookingAnswerRS updateBookingAnswers(HttpServletResponse response, HttpServletRequest request,
            @Valid @RequestBody UpdateBookingAnswerRQ updateBookingAnswerRQ, @PathVariable("cartid") String cartId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Update Booking Answers' Request. {}", logData);

        UpdateBookingAnswerRS updateBookingAnswerRS = cartApplicationService.updateBookingAnswers(updateBookingAnswerRQ,
                cartId, partnerId);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Update Booking Answers' Response.");

        return updateBookingAnswerRS;

    }

    /*
     * Update Hotel pickup location
     * 
     */

    @ApiOperation(httpMethod = "PUT", value = "Update hotel pickup location", nickname = "Update hotel pickup location")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UpdateHotelPickupRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts/{cartid}/bookingoptions/{bookingoptionid}/hotelpickup", method = RequestMethod.PUT)
    @ResponseBody
    public UpdateHotelPickupRS updateHotelPickupLocation(HttpServletResponse response, HttpServletRequest request,
            @Valid @RequestBody UpdateHotelPickupRQ updateHotelPickupRQ, @PathVariable("cartid") String cartId,
            @PathVariable("bookingoptionid") String bookingOptionId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Update Hotel Pickup Location' Request. {}", logData);

        UpdateHotelPickupRS updateHotelPickupRS = cartApplicationService.updateHotelPickupLocation(updateHotelPickupRQ,
                cartId, bookingOptionId, partnerId);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Update Hotel Pickup Location' Response.");

        return updateHotelPickupRS;

    }
    
    /*
     * Update language option code
     * 
     */
    @ApiOperation(httpMethod = "PUT", value = "Update language option code", nickname = "Update language option code")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "cartid", value = "Cart ID", required = true, dataType = "string", paramType = "path", defaultValue = "0a6517eb-23df-4338-9644-8f809cc834f5"),
        @ApiImplicitParam(name = "partner-id", value = "partner-id", dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UpdateLanguageOptionRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/carts/{cartid}/bookingoptions/{bookingoptionid}/languageoptioncode", method = RequestMethod.PUT)
    @ResponseBody
    public UpdateLanguageOptionRS updateLanguageOptionCode(HttpServletResponse response, HttpServletRequest request,
            @Valid @RequestBody UpdateLanguageOptionRQ updateLanguageOptionRQ, @PathVariable("cartid") String cartId,
            @PathVariable("bookingoptionid") String bookingOptionId,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.CART_ID.name(), cartId);

        logger.info("Received 'Update Language Option Code' Request. {}", logData);

        UpdateLanguageOptionRS updateLanguageOptionRS = cartApplicationService
                .updateLanguageOptionCode(updateLanguageOptionRQ, cartId, bookingOptionId, partnerId);

        setStatusHeadersToSuccess(response);

        logger.info("Returning 'Update Language Option Code' Response.");

        return updateLanguageOptionRS;

    }

}
