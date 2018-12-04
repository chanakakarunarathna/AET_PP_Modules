package com.viator.connector.infrastructure;

import org.springframework.integration.annotation.Router;
import org.springframework.stereotype.Component;

import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.GetBookingQuestionsRQ;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetProductDetailsRQ;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductReviewsRQ;

/**
 * Routing resolver based on inbound message payload.
 * 
 * @author wathsala.w
 *
 */
@Component
public class EndpointRouter {

    @Router(inputChannel = "amqpInputChannel")
    public String route(Object payload) {

        if (payload instanceof MakeBookingRQ) {
            return "makeBookingInputChannel";

        } else if (payload instanceof CancelBookingRQ) {
            return "cancelBookingInputChannel";

        } else if (payload instanceof GetProductPriceRQ) {
            return "getProductPriceInputChannel";

        } else if (payload instanceof GetBookingQuestionsRQ) {
            return "getBookingQuestionsInputChannel";

        } else if (payload instanceof BookingVoucherRQ) {
            return "getBookingVoucherInputChannel";

        } else if (payload instanceof GetProductDetailsRQ) {
            return "getProductDetailsInputChannel";

        } else if (payload instanceof GetAvailabilityRQ) {
            return "getAvailabilityInputChannel";

        } else if (payload instanceof GetProductOptionsRQ) {
            return "getProductOptionsInputChannel";

        } else if (payload instanceof GetProductReviewsRQ) {
            return "getProductReviewsInputChannel";

        }
        return "errorChannel";
    }

}
