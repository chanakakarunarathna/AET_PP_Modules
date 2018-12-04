package com.gobe.connector.gobecontroller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.gobe.connector.domain.gobe.Inventory.GobeInventoryCheckRS;
import com.gobe.connector.domain.gobe.availability.GobeScheduleRS;
import com.gobe.connector.domain.gobe.book.*;
import com.gobe.connector.domain.gobe.image.GobeImagesRS;
import com.gobe.connector.domain.gobe.price.GobePricesRS;
import com.gobe.connector.domain.gobe.product.GobeProductsRS;
import com.gobe.connector.domain.gobe.review.GobeReviewsRS;
import com.gobe.connector.infrastructure.OAuth2RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.placepass.exutil.HTTPResponseHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "gobe-endpoint-controller", description = "Gobe Endpoint Controller")
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GobeEndpointController extends HTTPResponseHandler {

    private Logger logger = LoggerFactory.getLogger(GobeEndpointController.class);

    @Autowired
    private OAuth2RestClient oAuth2RestClient;


    /*
    ********************************************************************************************************************
    * ******************** ALL GOBE SUPPORTED API CALLS ****************************************************************
    ********************************************************************************************************************
    * */


    @ApiOperation(value = "GetScheduleEndpoint", nickname = "GetScheduleEndpoint")
    // When param description is needed.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tourId", value = "Tour ID", required = true, dataType = "string", paramType = "path", defaultValue = "TR-NAM-USA-STAU-EN-1004"),
            @ApiImplicitParam(name = "startDate", value = "Start Date", required = true, dataType = "string", paramType = "query", defaultValue = "2017-08-01"),
            @ApiImplicitParam(name = "endDate", value = "End Date", required = true, dataType = "string", paramType = "query", defaultValue = "2017-08-30"),})
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GobeScheduleRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/schedule/{tourId}", method = RequestMethod.GET)
    @ResponseBody
    public GobeScheduleRS getSchedule(@PathVariable String tourId,
            @RequestParam(value = "startDate", required = true) String startDate,
            @RequestParam(value = "endDate", required = true) String endDate, HttpServletResponse response) {

        logger.info("Received Schedule Request");
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("tourId", tourId);
        urlVariables.put("startDate", startDate);
        urlVariables.put("endDate", endDate);
        GobeScheduleRS gobeScheduleRS = oAuth2RestClient.getSchedule(urlVariables);
        return gobeScheduleRS;
    }

    @ApiOperation(value = "GetProductsEndpoint", nickname = "GetProductsEndpoint")
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GobeProductsRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public GobeProductsRS getProducts() {
        logger.info("Received Products Request");
        GobeProductsRS gobeProductsRS = oAuth2RestClient.getProducts();
        return gobeProductsRS;
    }

    @ApiOperation(value = "GetImagesEndpoint", nickname = "GetImagesEndpoint")
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GobeImagesRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/images", method = RequestMethod.GET)
    @ResponseBody
    public GobeImagesRS getImages() {
        logger.info("Received Images Request");
        GobeImagesRS gobeImagesRS = oAuth2RestClient.getImages();
        return gobeImagesRS;
    }

    @ApiOperation(value = "GetPricesEndpoint", nickname = "GetPricesEndpoint")
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GobePricesRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/prices", method = RequestMethod.GET)
    @ResponseBody
    public GobePricesRS getPrices() {
        logger.info("Received Prices Request");
        GobePricesRS gobePricesRS = oAuth2RestClient.getPrices();
        return gobePricesRS;
    }

    @ApiOperation(value = "CheckInventoryEndpoint", nickname = "CheckInventoryEndpoint")
    // When param description is needed.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "productId", required = true, dataType = "string", paramType = "path", defaultValue = "TR-NAM-USA-STAU-EN-1004"),
            @ApiImplicitParam(name = "tourDate", value = "Tour Date", required = true, dataType = "string", paramType = "query", defaultValue = "2017-08-11"),
            @ApiImplicitParam(name = "startTime", value = "Start Time", required = true, dataType = "string", paramType = "query", defaultValue = "08:00"),
            @ApiImplicitParam(name = "quantity", value = "quantity", required = true, dataType = "string", paramType = "query", defaultValue = "1"),})
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GobeInventoryCheckRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/inventoryCheck/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public GobeInventoryCheckRS checkInventory(@PathVariable String productId,
            @RequestParam(value = "tourDate", required = true) String tourDate,
            @RequestParam(value = "startTime", required = true) String startTime,
            @RequestParam(value = "quantity", required = true) String quantity, HttpServletResponse response) {

        logger.info("Received Inventory Check Request");
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("productId", productId);
        urlVariables.put("tourDate", tourDate);
        urlVariables.put("startTime", startTime);
        urlVariables.put("quantity", quantity);
        GobeInventoryCheckRS gobeInventoryCheckRS = oAuth2RestClient.checkInventory(urlVariables);
        return gobeInventoryCheckRS;
    }

    @ApiOperation(value = "GetReviewsEndpoint", nickname = "GetReviewsEndpoint")
    // When param description is needed.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "productId", required = true, dataType = "string", paramType = "path", defaultValue = "TR-NAM-USA-STAU-EN-1004"),})
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GobeReviewsRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/reviews/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public GobeReviewsRS getReviews(@PathVariable String productId, HttpServletResponse response) {
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("productId", productId);
        logger.info("Received Products Request");
        GobeReviewsRS gobeReviewsRS = oAuth2RestClient.getReviews(urlVariables);
        return gobeReviewsRS;
    }

    @ApiOperation(value = "MakeBookingEndpoint", nickname = "MakeBookingEndpoint")
    // Documenting status codes (these are samples)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GobeOrderRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request") })
    @RequestMapping(value = "/booking/createBooking", method = RequestMethod.POST)
    @ResponseBody
    public GobeOrderRS makeBooking(@Valid @RequestBody GobeOrderRQ gobeOrderRQ, HttpServletResponse response) {

        logger.info("Received Booking Request");
        return oAuth2RestClient.makeBooking(gobeOrderRQ);
    }

    @ApiOperation(value = "CancelBookingEndpoint", nickname = "CancelBookingEndpoint")
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GobeCancelOrderRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/booking/cancelBooking", method = RequestMethod.POST)
    @ResponseBody
    public GobeCancelOrderRS getReviews(@Valid @RequestBody GobeCancelOrderRQ gobeCancelOrderRQ, HttpServletResponse response) {

        logger.info("Received Cancel Booking Request");
        GobeCancelOrderRS gobeCancelOrderRS = oAuth2RestClient.cancelBooking(gobeCancelOrderRQ);
        return gobeCancelOrderRS;
    }

    @ApiOperation(value = "GetOrderStatusEndpoint", nickname = "GetOrderStatusEndpoint")
    // When param description is needed.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "orderId", required = true, dataType = "string", paramType = "path", defaultValue = "TR-NAM-USA-STAU-EN-1004"),})
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GobeOrderStatusRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/booking/status/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public GobeOrderStatusRS getAvailability(@PathVariable String orderId, HttpServletResponse response) {

        logger.info("Received Order Status Request");
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("orderId", orderId);
        GobeOrderStatusRS gobeOrderStatusRS = oAuth2RestClient.getBookingStatus(urlVariables);
        return gobeOrderStatusRS;
    }
}
