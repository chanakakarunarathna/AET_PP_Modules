package com.placepass.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.product.application.pricematch.PriceMatchAppService;
import com.placepass.product.application.pricematch.dto.GetPriceMatchRQ;
import com.placepass.product.application.pricematch.dto.GetPriceMatchRS;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Price Match", description = "Operations on filtering prices.")
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceMatchController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProductOptionsController.class);

    @Autowired
    private PriceMatchAppService priceMatchAppService;

    @ApiOperation(value = "getPriceMatch", nickname = "getPriceMatch")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetPriceMatchRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products/pricematch", method = RequestMethod.POST)
    @ResponseBody
    public GetPriceMatchRS getPriceMatch(@Valid @RequestBody GetPriceMatchRQ getPriceMatchRQ,
            @RequestHeader(value = "partner-id", required = true) String partnerId, HttpServletResponse response) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);

        logger.info("Received 'Get Price Match' Request. {}", logData);

        GetPriceMatchRS getPriceMatchRS = priceMatchAppService.getPriceMatchWithFee(partnerId, getPriceMatchRQ);

        logger.info("Returning 'Get Price Match' Response.");

        return getPriceMatchRS;
    }

}
