package com.placepass.product.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.product.application.review.ReviewAppService;
import com.placepass.product.application.review.dto.GetReviewsRS;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.logging.PlatformLoggingKey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Product Reviews", description = "Product Reviews.")
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    ReviewAppService reviewAppService;

    @ApiOperation(value = "Get Product Reviews", nickname = "getProductReviews")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path", defaultValue = "1"),
        @ApiImplicitParam(name = "language", value = "Language Code", required = false, dataType = "string", paramType = "query", defaultValue = "en"),
        @ApiImplicitParam(name = "hitsperpage", value = "hitsPerPage", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pagenumber", value = "pageNumber", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetReviewsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "products/{productid}/reviews", method = RequestMethod.GET)
    @ResponseBody
    public GetReviewsRS getReviews(@PathVariable String productid,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "hitsperpage", required = true) String hitsPerPage,
            @RequestParam(value = "pagenumber", required = true) String pageNumber,
            @RequestHeader(value = "partner-id", required = true) String partnerId, HttpServletRequest request,
            HttpServletResponse response) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.PRODUCT_ID.name(), productid);
        logData.put(PlatformLoggingKey.LANGUAGE.name(), language);
        logData.put(PlatformLoggingKey.HITS_PER_PAGE.name(), hitsPerPage);
        logData.put(PlatformLoggingKey.PAGE_NUMBER.name(), pageNumber);

        logger.info("Received 'Get Product Reviews' Request. {}", logData);

        if (!hitsPerPage.matches("\\d+") || Integer.parseInt(hitsPerPage) < 1) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_HITS_PER_PAGE.toString(),
                    PlacePassExceptionCodes.INVALID_HITS_PER_PAGE.getDescription());
        }

        if (!pageNumber.matches("\\d+")) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PAGE_NUMBER.toString(),
                    PlacePassExceptionCodes.INVALID_PAGE_NUMBER.getDescription());
        }

        super.setStatusHeadersToSuccess(response);
        GetReviewsRS getReviewsRS = reviewAppService.getProductReviews(productid, Integer.parseInt(hitsPerPage),
                Integer.parseInt(pageNumber));

        logger.info("Returning 'Get Product Reviews' Response.");

        return getReviewsRS;
    }

}
