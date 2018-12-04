package com.placepass.product.infrastructure;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;

import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.product.domain.product.ProductDetails;

@MessagingGateway(defaultRequestChannel = "vendorProductOutboundChannel", defaultReplyTimeout = "${rabbitmq.vendor.product.wait.for.reply.timeout}", errorChannel = "errorChannel")
public interface VendorProductGateway {

    @Gateway(headers = {@GatewayHeader(name = "ObjectType", expression = "#args[0].class"),
        @GatewayHeader(name = "ObjectName", value = "GetAvailabilityRQ"),
        @GatewayHeader(name = "RoutingKey", expression = "#args[0].gatewayName")})
    GetAvailabilityRS getAvailability(GetAvailabilityRQ request);

    @Gateway(headers = {@GatewayHeader(name = "ObjectType", expression = "#args[0].class"),
        @GatewayHeader(name = "ObjectName", value = "GetProductOptionsRQ"),
        @GatewayHeader(name = "RoutingKey", expression = "#args[0].gatewayName")})
    GetProductOptionsRS getProductOptions(GetProductOptionsRQ request);

    @Gateway(headers = {@GatewayHeader(name = "ObjectType", expression = "#args[0].class"),
        @GatewayHeader(name = "ObjectName", expression = "#args[0].class.simpleName"),
        @GatewayHeader(name = "RoutingKey", expression = "#args[0].gatewayName")})
    ProductDetails getProductDetails(GetAvailabilityRQ request);

    @Gateway(headers = {@GatewayHeader(name = "ObjectType", expression = "#args[0].class"),
        @GatewayHeader(name = "ObjectName", value = "GetProductReviewsRQ"),
        @GatewayHeader(name = "RoutingKey", expression = "#args[0].gatewayName")})
    GetProductReviewsRS getProductReviews(GetProductReviewsRQ request);

}