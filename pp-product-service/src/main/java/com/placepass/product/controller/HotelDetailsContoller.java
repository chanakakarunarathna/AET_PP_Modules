package com.placepass.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.product.application.hoteldetails.HotelDetailsAppService;
import com.placepass.product.application.hoteldetails.dto.GetHotelDetailsRS;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.logging.PlatformLoggingKey;

@Api(tags = "Product Hotel Details", description = "Operations on hotel details retrieval.")
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HotelDetailsContoller extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(HotelDetailsContoller.class);

    @Autowired
    private HotelDetailsAppService hotelDetailsAppService;

    @ApiOperation(value = "Get Hotel Details", nickname = "getHotelDetails")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetHotelDetailsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products/hotels/{productid}", method = RequestMethod.GET)
    @ResponseBody
    public GetHotelDetailsRS getHotelDetails(@PathVariable("productid") String productId,
            @RequestHeader(value = "partner-id", required = true) String partnerId, HttpServletRequest request,
            HttpServletResponse response) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.PRODUCT_ID.name(), productId);

        logger.info("Received 'Get Hotel Details' Request. {}", logData);

        super.setStatusHeadersToSuccess(response);
        GetHotelDetailsRS getHotelDetailsRS = hotelDetailsAppService.getHotelDetails(partnerId, productId);

        logger.info("Returning 'Get Hotel Details' Response.");
        
        return getHotelDetailsRS;
    }

}
