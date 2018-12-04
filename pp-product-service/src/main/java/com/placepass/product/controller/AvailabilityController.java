package com.placepass.product.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.placepass.product.application.availability.AvailabilityAppService;
import com.placepass.product.application.availability.dto.GetAvailabilityRS;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Product Availability", description = "Operations on product availability.")
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AvailabilityController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(AvailabilityController.class);

    @Autowired
    private AvailabilityAppService availabilityAppService;

    @ApiOperation(value = "Get Availability", nickname = "getAvailability")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "month", value = "Month", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "year", value = "Year", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "language", value = "Language Code", required = false, dataType = "string", paramType = "query", defaultValue = "en"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetAvailabilityRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products/{productid}/availability", method = RequestMethod.GET)
    @ResponseBody
    public GetAvailabilityRS getAvailability(@PathVariable("productid") String productId,
            @RequestParam(value = "month", required = true) String month,
            @RequestParam(value = "year", required = true) String year,
            @RequestParam(value = "language", required = false) String language,
            @RequestHeader(value = "partner-id", required = true) String partnerId, HttpServletRequest request,
            HttpServletResponse response) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.PRODUCT_ID.name(), productId);
        logData.put(PlatformLoggingKey.MONTH.name(), month);
        logData.put(PlatformLoggingKey.YEAR.name(), year);
        logData.put(PlatformLoggingKey.LANGUAGE.name(), language);

        logger.info("Received 'Get Availability' Request. {}", logData);

        // TODO:need to push validations to a common place
        try {
            if (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
                logger.error("Invalid month.month shoud be in the range [1,12]");
                throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "month shoud be in the range [1,12]");
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid month.", e);
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Invalid month");
        }

        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        Calendar dateAfterTwoYears = Calendar.getInstance();
        dateAfterTwoYears.add(Calendar.YEAR, 2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(year + "-" + month + "-" + currentDate.get(Calendar.DAY_OF_MONTH));
            if (date.after(dateAfterTwoYears.getTime()) || date.before(currentDate.getTime())) {
                logger.error("Availability can be only checked for two years from the current date.");
                throw new BadRequestException(HttpStatus.BAD_REQUEST.name(),
                        "Availability can be only checked for two years from the current date.");
            }
        } catch (ParseException e) {
            logger.error("Invalid month or year.", e);
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Invalid month or year");
        }

        super.setStatusHeadersToSuccess(response);
        GetAvailabilityRS getAvailabilityRS = availabilityAppService.getAvailability(productId, month, year);

        logger.info("Returning 'Get Availability' Response.");

        return getAvailabilityRS;

    }

}
