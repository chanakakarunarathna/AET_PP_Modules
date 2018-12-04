package com.stripe.payment.connector.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.gson.Gson;
import com.stripe.payment.connector.application.charge.ConnectorPaymentRequest;
import com.stripe.payment.connector.application.common.ConnectorErrorResponse;
import com.stripe.payment.connector.application.refund.ConnectorPaymentReversalRequest;

public class CustomMessageConverter implements MessageConverter {

    private static final Logger logger = LoggerFactory.getLogger(CustomMessageConverter.class);

    private Gson gson;

    public CustomMessageConverter() {
        this.gson = new Gson();
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {

        String s = gson.toJson(object);
        // These header names should not change. These are used on the receiving
        // end.
        messageProperties.setHeader("ObjectType", object.getClass());
        messageProperties.setHeader("ObjectName", object.getClass().getSimpleName());
        return new Message(s.getBytes(), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        Object response = null;

        try {
            String s = new String(message.getBody());
            String objectName = (String) message.getMessageProperties().getHeaders().get("ObjectName");

            if ("ConnectorPaymentRequest".equals(objectName)) {
                response = gson.fromJson(s, ConnectorPaymentRequest.class);
            } else if ("ConnectorPaymentReversalRequest".equals(objectName)) {
                response = gson.fromJson(s, ConnectorPaymentReversalRequest.class);
            }
        } catch (Exception e) {
            ConnectorErrorResponse errorResponse = new ConnectorErrorResponse();
            Map<String, String> externalStatuses = new HashMap<String, String>();
            externalStatuses.put("ERROR_MESSAGE", e.getLocalizedMessage());
            errorResponse.setExternalStatuses(externalStatuses);
            response = errorResponse;
        }

        return response;

    }

}
