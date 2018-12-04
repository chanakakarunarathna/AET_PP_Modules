package com.viator.connector.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.gson.Gson;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.GetBookingQuestionsRQ;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;

/**
 * Simple JSON message converter.
 * 
 * @author wathsala.w
 *
 */
public class CustomMessageConverter implements MessageConverter {

    private static final Logger logger = LoggerFactory.getLogger(CustomMessageConverter.class);

    private Gson gson;

    public CustomMessageConverter() {
        this.gson = new Gson();
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {

        String json = null;
        if (object instanceof MakeBookingRS) {
            json = gson.toJson(object, MakeBookingRS.class);
        } else if (object instanceof CancelBookingRS) {
            json = gson.toJson(object, CancelBookingRS.class);
        } else if (object instanceof GetProductPriceRS) {
            json = gson.toJson(object, GetProductPriceRS.class);
        } else if (object instanceof GetBookingQuestionsRS) {
            json = gson.toJson(object, GetBookingQuestionsRS.class);
        } else if (object instanceof BookingVoucherRS) {
            json = gson.toJson(object, BookingVoucherRS.class);
        } else if (object instanceof GetAvailabilityRS) {
            json = gson.toJson(object, GetAvailabilityRS.class);
        } else if (object instanceof GetProductOptionsRS) {
            json = gson.toJson(object, GetProductOptionsRS.class);
        } else if (object instanceof GetProductReviewsRS) {
            json = gson.toJson(object, GetProductReviewsRS.class);
        } else {
            throw new RuntimeException("Message response not mapped : Message Type " + object.getClass().getName());
        }

        logger.info("toMessage():" + json);

        // These header names should not change. These are used on the receiving
        // end.
        messageProperties.setHeader("ObjectType", object.getClass());
        messageProperties.setHeader("ObjectName", object.getClass().getSimpleName());

        logger.info("messageProperties:" + messageProperties.toString());

        return new Message(json.getBytes(), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        logger.info("fromMessage():" + new String(message.getBody()));

        Object response = null;
        String json = new String(message.getBody());
        String objectName = (String) message.getMessageProperties().getHeaders().get("ObjectName");

        if ("MakeBookingRQ".equals(objectName)) {
            response = gson.fromJson(json, MakeBookingRQ.class);
        } else if ("CancelBookingRQ".equals(objectName)) {
            response = gson.fromJson(json, CancelBookingRQ.class);
        } else if ("GetProductPriceRQ".equals(objectName)) {
            response = gson.fromJson(json, GetProductPriceRQ.class);
        } else if ("GetBookingQuestionsRQ".equals(objectName)) {
            response = gson.fromJson(json, GetBookingQuestionsRQ.class);
        } else if ("BookingVoucherRQ".equals(objectName)) {
            response = gson.fromJson(json, BookingVoucherRQ.class);
        } else if ("GetAvailabilityRQ".equals(objectName)) {
            response = gson.fromJson(json, GetAvailabilityRQ.class);
        } else if ("GetProductOptionsRQ".equals(objectName)) {
            response = gson.fromJson(json, GetProductOptionsRQ.class);
        } else if ("GetProductReviewsRQ".equals(objectName)) {
            response = gson.fromJson(json, GetProductReviewsRQ.class);
        } else {
            throw new AmqpRejectAndDontRequeueException(
                    "Unknown message received request : Message Type " + objectName);
        }

        return response;
    }

}
