package com.placepass.booking.infrastructure;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.gson.Gson;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentResponse;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalRequest;
import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalResponse;

public class CustomMessageConverter implements MessageConverter {

    private Gson gson;

    public CustomMessageConverter() {
        this.gson = new Gson();
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {

        String json = null;
        String routingKey = null;
        if (object.getClass().equals(ConnectorPaymentRequest.class)) {
            ConnectorPaymentRequest req = (ConnectorPaymentRequest) object;
            routingKey = req.getGatewayName();
            json = gson.toJson(req);

        } else if (object.getClass().equals(ConnectorPaymentReversalRequest.class)) {
            ConnectorPaymentReversalRequest req = (ConnectorPaymentReversalRequest) object;
            routingKey = req.getGatewayName();
            json = gson.toJson(req);

        }
        // else if (object.getClass().equals(InitOrderRequestDTO.class)) {
        // InitOrderRequestDTO req = (InitOrderRequestDTO) object;
        // routingKey = req.getGatewayName();
        // json = gson.toJson(req);
        // }
        else {
            // TODO Handle Error!
        }

        // These header names should not change. These are used on the receiving
        // end.
        messageProperties.setHeader("ObjectType", object.getClass());
        messageProperties.setHeader("ObjectName", object.getClass().getSimpleName());
        messageProperties.setHeader("RoutingKey", routingKey);

        return new Message(json.getBytes(), messageProperties);

    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        Object response = null;

        String s = new String(message.getBody());
        String objectName = (String) message.getMessageProperties().getHeaders().get("ObjectName");

        if ("ConnectorPaymentResponse".equals(objectName)) {
            response = gson.fromJson(s, ConnectorPaymentResponse.class);
        } else if ("ConnectorPaymentReversalResponse".equals(objectName)) {
            response = gson.fromJson(s, ConnectorPaymentReversalResponse.class);
        }
        // else if ("InitOrderResponseDTO".equals(objectName)) {
        // response = gson.fromJson(s, InitOrderResponseDTO.class);
        // }
        else {
            throw new RuntimeException("Unknown message received : Message Type " + objectName);
        }

        return response;
    }

}
