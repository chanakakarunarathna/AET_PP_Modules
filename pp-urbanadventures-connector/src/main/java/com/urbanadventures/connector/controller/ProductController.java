package com.urbanadventures.connector.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urbanadventures.connector.application.product.ProductService;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "getProductDetails", nickname = "getProductDetails")
    // When param description is needed.
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path", defaultValue = "1")})
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetProductDetailsRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products/{productid}", method = RequestMethod.GET)
    @ResponseBody
    public GetProductDetailsRS getProductDetails(@PathVariable("productid") String productId,
            HttpServletResponse response) {

        logger.info("Received Product Details Request");
        return productService.getProductDetails(productId);
    }

    @ApiOperation(value = "getAvailability", nickname = "getAvailability")
    // When param description is needed.
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path", defaultValue = "1"),
        @ApiImplicitParam(name = "month", value = "Month", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "year", value = "Year", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "fromDate", value = "From Date", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "toDate", value = "To Date", required = false, dataType = "string", paramType = "query")})
    // Documenting status codes (these are samples)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetAvailabilityRS.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/products/{productid}/availability", method = RequestMethod.GET)
    @ResponseBody
    public GetAvailabilityRS getAvailability(@PathVariable String productid,
            @RequestParam(value = "month", required = true) String month,
            @RequestParam(value = "year", required = true) String year,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate, HttpServletResponse response) {

        logger.info("Received Availability Request");
        return productService.getAvailability(productid, month, year, fromDate, toDate);
    }

    @ApiOperation(value = "getProductOptions", nickname = "getProductOptions")
    // When param description is needed.
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path", defaultValue = "1"),
        @ApiImplicitParam(name = "date", value = "Date", required = true, dataType = "string", paramType = "query")})
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
        return productService.getProductReviews();
    }
}
