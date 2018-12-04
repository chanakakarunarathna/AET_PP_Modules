package com.placepass.booking.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.booking.application.booking.BookingApplicationService;
import com.placepass.booking.application.booking.dto.VoucherDTO;
import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class BookingVoucherController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(BookingVoucherController.class);

    @Autowired
    private BookingApplicationService applicationService;

    @ApiOperation(httpMethod = "GET", value = "Get Voucher URL", nickname = "Get Voucher URL")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "voucherid", value = "Voucher ID", required = true, dataType = "string", paramType = "path")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "vouchers/{voucherid}", method = RequestMethod.GET)
    public String getVoucher(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("voucherid") String voucherId) {
        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.VOUCHER_ID.name(), voucherId);

        logger.info("Received 'Get Voucher' Request. {}", logData);

        String responseString = "redirect:" + applicationService.getVoucher(voucherId);

        logger.info("Returning 'Get Voucher' Response.");

        return responseString;
    }

    @ApiOperation(httpMethod = "GET", value = "Get Voucher Details", nickname = "Get Voucher Details")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "voucherid", value = "Voucher ID", required = true, dataType = "string", paramType = "path")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = VoucherDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "vouchers/v2/{voucherid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public VoucherDTO getVoucherV2(HttpServletResponse response, HttpServletRequest request,
            @PathVariable("voucherid") String voucherId) {
        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.VOUCHER_ID.name(), voucherId);

        logger.info("Received 'Get Voucher Details' Request. {}", logData);

        VoucherDTO voucher = applicationService.getVoucherDetails(voucherId);

        logger.info("Returning 'Get Voucher Details' Response.");

        return voucher;
    }

}
