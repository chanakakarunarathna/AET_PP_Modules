package com.placepass.booking.domain.event;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// FIXME: MOVE to right place
public class JsonUtils {

    public static String toJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // Object to JSON in String
        return mapper.writeValueAsString(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonParseException, JsonMappingException,
            IOException {

        ObjectMapper mapper = new ObjectMapper();

        // JSON from String to Object
        return mapper.readValue(json, classOfT);
    }

}
