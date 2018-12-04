package com.placepass.product.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.product.application.productoptions.ProductOptionsAppService;
import com.placepass.product.application.productoptions.dto.GetProductOptionsRS;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Product Options", description = "Operations on bookable product option.")
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductOptionsController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProductOptionsController.class);

    @Autowired
    private ProductOptionsAppService productOptionsAppService;

    @ApiOperation(value = "Get Product Options", nickname = "getProductOptions")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "date", value = "Date", required = true, dataType = "string", paramType = "query", defaultValue = "2018-01-01"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetProductOptionsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products/{productid}/bookingoptions", method = RequestMethod.GET)
    @ResponseBody
    public GetProductOptionsRS getProductOptions(@PathVariable("productid") String productId,
            @RequestParam(value = "date", required = true) String date,
            @RequestHeader(value = "partner-id", required = true) String partnerId, HttpServletRequest request,
            HttpServletResponse response) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.PRODUCT_ID.name(), productId);
        logData.put(PlatformLoggingKey.DATE.name(), date);

        logger.info("Received 'Get Product Options' Request. {}", logData);

        try {
            if (date != null && !date.isEmpty()) {
                LocalDate.parse(date);
            }
            super.setStatusHeadersToSuccess(response);
            GetProductOptionsRS getProductOptionsRS = productOptionsAppService.getProductOptions(productId, date);

            logger.info("Returning 'Get Product Options' Response.");

            return getProductOptionsRS;
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format.", e);
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Date should be in YYYY-MM-DD format.");
        }

    }

}
