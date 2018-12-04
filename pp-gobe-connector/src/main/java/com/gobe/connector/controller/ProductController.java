package com.gobe.connector.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.gobe.connector.application.product.ProductService;
import com.gobe.connector.domain.gobe.Inventory.GobeInventoryCheckRS;
import com.gobe.connector.domain.gobe.availability.GobeScheduleRS;
import com.gobe.connector.domain.gobe.book.*;
import com.gobe.connector.domain.gobe.image.GobeImagesRS;
import com.gobe.connector.domain.gobe.price.GobePricesRS;
import com.gobe.connector.domain.gobe.product.GobeProductsRS;
import com.gobe.connector.domain.gobe.review.GobeReviewsRS;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;
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

@Api(tags = "product-controller", description = "Product Controller")
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends HTTPResponseHandler {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private ProductService productService;

    @Autowired
    private OAuth2RestClient oAuth2RestClient;

    @ApiOperation(value = "getAvailability", nickname = "getAvailability")
    // When param description is needed.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "productId", required = true, dataType = "string", paramType = "path", defaultValue = "TR-NAM-USA-STAU-EN-1004"),
            @ApiImplicitParam(name = "month", value = "month", required = true, dataType = "string", paramType = "query", defaultValue = "8"),
            @ApiImplicitParam(name = "year", value = "year", required = true, dataType = "string", paramType = "query", defaultValue = "2017"),})
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetAvailabilityRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products/{productId}/availability", method = RequestMethod.GET)
    @ResponseBody
    public GetAvailabilityRS getAvailability(@PathVariable String productId,
                                          @RequestParam(value = "month", required = true) String month,
                                          @RequestParam(value = "year", required = true) String year, HttpServletResponse response) {

        logger.info("Received Schedule Request");
        GetAvailabilityRS getAvailabilityRS= productService.getAvailability(productId, month, year);
        return getAvailabilityRS;
    }



    @ApiOperation(value = "getProductOptions", nickname = "getProductOptions")
    // When param description is needed.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path", defaultValue = "TR-NAM-USA-STAU-EN-1004"),
            @ApiImplicitParam(name = "date", value = "Date", required = true, dataType = "string", paramType = "query", defaultValue = "yyyy-MM-dd")})
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetProductOptionsRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products/{productid}/bookingoptions", method = RequestMethod.GET)
    @ResponseBody
    public GetProductOptionsRS getProductOptions(@PathVariable String productid,
            @RequestParam(value = "date", required = true) String date, HttpServletResponse response) {

        logger.info("Received Product Options Request");
        return productService.getProductOptions(productid, date);
    }


    @ApiOperation(value = "getProductReviews", nickname = "getProductReviews")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path", defaultValue = "1"),
            @ApiImplicitParam(name = "hitsperpage", value = "hitsPerPage", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pagenumber", value = "pageNumber", required = true, dataType = "int", paramType = "query")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetProductReviewsRS.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "product/{productid}/reviews", method = RequestMethod.GET)
    @ResponseBody
    public GetProductReviewsRS getProductReviews(@PathVariable String productid,
            @RequestParam(value = "hitsperpage", required = true) Integer hitsPerPage,
            @RequestParam(value = "pagenumber", required = true) Integer pageNumber, HttpServletResponse response) {

        logger.info("Received Product Reviews Request");
        return productService.getProductReviews(productid, hitsPerPage, pageNumber);
    }
}
