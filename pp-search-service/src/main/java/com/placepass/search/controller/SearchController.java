package com.placepass.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.math.NumberUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.search.application.common.HTTPResponseHandler;
import com.placepass.search.application.search.SearchService;
import com.placepass.search.application.search.request.ProductSearchRequest;
import com.placepass.search.application.search.request.ProductsRequest;
import com.placepass.search.application.search.request.ShelvesRequest;
import com.placepass.search.application.search.response.Product;
import com.placepass.search.application.search.response.ProductsResponse;
import com.placepass.search.application.search.response.SearchResponse;
import com.placepass.search.application.search.response.ShelvesResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "search-controller")
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController extends HTTPResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @ApiOperation(httpMethod = "POST", value = "Product Search", nickname = "Product Search")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true),
        @ApiImplicitParam(name = "vendor-id", value = "Vendor ID", dataType = "string", paramType = "header", required = false)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = SearchResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(method = RequestMethod.POST, value = "/search")
    @ResponseBody
    public SearchResponse postSearch(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody ProductSearchRequest searchRequest,
            @RequestHeader(value = "partner-id", required = true) String partnerId,
            @RequestHeader(value = "tags", required = false) String tags,
            @RequestHeader(value = "vendor-id", required = false) String vendorId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logger.info("Received Product Search Request, {}", logData);

        SearchResponse res = searchService.getSearchResults(partnerId, vendorId, tags, searchRequest);
        super.setStatusHeadersToSuccess(response, res.getParams());
        return res;
    }

    @ApiOperation(httpMethod = "POST", value = "Product Shelves", nickname = "Product Shelves")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = ShelvesResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(method = RequestMethod.POST, value = "/search/shelves")
    @ResponseBody
    public ShelvesResponse postShelves(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody ShelvesRequest shelvesRequest,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logger.info("Received Product Search Request, {}", logData);

        ShelvesResponse res = searchService.getShelfResults(partnerId, shelvesRequest);
        return res;
    }

    @ApiOperation(httpMethod = "POST", value = "Products by Ids", nickname = "Products by Ids")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Product.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(method = RequestMethod.POST, value = "/search/products")
    @ResponseBody
    public List<Product> postProducts(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody ProductsRequest productsRequest,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {

        List<Product> res = searchService.getProducts(partnerId, productsRequest);
        super.setStatusHeadersToSuccess(response, "");
        return res;
    }

    @ApiOperation(httpMethod = "GET", value = "Products by Ids - GET", nickname = "Products by Ids - GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productids", value = "Product IDs", required = true, dataType = "string", paramType = "path"),        
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ProductsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(method = RequestMethod.GET, value = "/search/products/{productids}")
    @ResponseBody
    public ProductsResponse getProducts(HttpServletRequest request, HttpServletResponse response,
    			@PathVariable("productids") String productIds,
            @RequestHeader(value = "partner-id", required = true) String partnerId) {
        ProductsResponse res = searchService.getProducts(partnerId, productIds);
        super.setStatusHeadersToSuccess(response, "");
        return res;
    }
    
    @ApiOperation(httpMethod = "GET", value = "Related Products", nickname = "Related Products")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "Product ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "hitsPerPage", value = "hitsPerPage", required = false, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true),
        @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = false, dataType = "int", paramType = "query")})

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = SearchResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/search/{productid}", method = RequestMethod.GET)
    @ResponseBody
    public SearchResponse getRelatedProducts(@PathVariable("productid") String productId,
            @RequestParam(value = "hitsPerPage", required = false) String hitsPerPage,
            @RequestParam(value = "pageNumber", required = false) String pageNumber, HttpServletRequest request,
            HttpServletResponse response, @RequestHeader(value = "partner-id", required = true) String partnerId) {

        if (hitsPerPage != null) {
            if (!hitsPerPage.matches("\\d+") || Integer.parseInt(hitsPerPage) < 1) {
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_HITS_PER_PAGE.toString(),
                        PlacePassExceptionCodes.INVALID_HITS_PER_PAGE.getDescription());
            }
        }
        if (pageNumber != null) {
            if (!pageNumber.matches("\\d+")) {
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_PAGE_NUMBER.toString(),
                        PlacePassExceptionCodes.INVALID_PAGE_NUMBER.getDescription());
            }
        }

        SearchResponse res = searchService.getRelatedProducts(partnerId, productId,
                NumberUtils.createInteger(hitsPerPage), NumberUtils.createInteger(pageNumber));
        super.setStatusHeadersToSuccess(response, res.getParams());
        return res;
    }

}
