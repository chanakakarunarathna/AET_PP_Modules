package com.placepass.product.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.gson.Gson;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;

/**
 * Vendor connector JSON message converter.
 * 
 * @author wathsala.w
 *
 */
public class VendorConnectorMessageConverter implements MessageConverter {

    private static final Logger logger = LoggerFactory.getLogger(VendorConnectorMessageConverter.class);

    private Gson gson;

    public VendorConnectorMessageConverter() {
        this.gson = new Gson();
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {

        logger.info("toMessage messageProperties:" + messageProperties.toString());

        String json = null;
        if (object instanceof GetAvailabilityRQ) {
            json = gson.toJson(object, GetAvailabilityRQ.class);
        } else if (object instanceof GetProductOptionsRQ) {
            json = gson.toJson(object, GetProductOptionsRQ.class);
        } else if (object instanceof GetProductReviewsRQ) {
            json = gson.toJson(object, GetProductReviewsRQ.class);
        } else {
            throw new RuntimeException("Message request not mapped : Message Type " + object.getClass().getName());
        }

        logger.info("toMessage():" + json);

        return new Message(json.getBytes(), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        logger.info("fromMessage():" + new String(message.getBody()));

        Object response = null;
        String json = new String(message.getBody());
        String objectName = (String) message.getMessageProperties().getHeaders().get("ObjectName");

        if ("GetAvailabilityRS".equals(objectName)) {
            response = gson.fromJson(json, GetAvailabilityRS.class);
        } else if ("GetProductOptionsRS".equals(objectName)) {
            response = gson.fromJson(json, GetProductOptionsRS.class);
        } else if ("GetProductReviewsRS".equals(objectName)) {
            response = gson.fromJson(json, GetProductReviewsRS.class);
        } else {
            throw new RuntimeException("Unknown message received response : Message Type " + objectName);
        }

        return response;
    }

}
