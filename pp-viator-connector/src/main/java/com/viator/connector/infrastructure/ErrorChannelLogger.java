package com.viator.connector.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * Logs any Spring Integration issues sent to error channel.
 * 
 * @author wathsala.w
 *
 */
@Component
public class ErrorChannelLogger {

    private static final Logger logger = LoggerFactory.getLogger(ErrorChannelLogger.class);

    @Transformer(inputChannel = "errorChannel")
    public Object logDetails(Message<?> message) {

        Map<String, Object> logData = new HashMap<>();

        try {
            Object errorPayload = message.getPayload();

            if (errorPayload instanceof MessagingException) {

                MessagingException me = (MessagingException) errorPayload;
                logData.put("FailedMessage", me.getFailedMessage().getPayload().toString());

                MessageHeaders headers = message.getHeaders();
                if (headers != null) {
                    for (String key : headers.keySet()) {
                        logData.put(key, "" + headers.get(key));
                    }
                }

                logger.error("Channel Error Failed Message. {}", logData);
                logger.error(me.getMessage(), me.getCause());
            }

        } catch (Exception e) {

            logger.error("Failed to process error message. {}", logData);
            logger.error(e.getMessage(), e);
        }

        return message;
    }
}