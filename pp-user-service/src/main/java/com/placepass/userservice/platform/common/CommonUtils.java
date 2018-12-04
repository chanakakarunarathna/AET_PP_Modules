package com.placepass.userservice.platform.common;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class CommonUtils.
 */
public class CommonUtils {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static <T> T convertToObject(Class<T> clazz, String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (T) objectMapper.readValue(content, clazz);

        } catch (Exception e) {
            logger.error("Failed to convert content: " + e.getMessage());

        }

        return null;
    }

}
