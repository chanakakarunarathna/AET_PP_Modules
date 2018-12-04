package com.viator.connector.application.common;

import java.util.Map;

import org.slf4j.MDC;

import com.google.gson.Gson;

public class LogMessage {

    private static final Gson gson = new Gson();

    private static final String logDataKey = "logData";

    // TODO: May want to perform the actual logging here to remove data after logging
    public static String getLogMessage(Map<String, Object> logData, String message) {
        if (logData != null && !logData.isEmpty()) {
            MDC.put(logDataKey, gson.toJson(logData));
        } else {
            MDC.remove(logDataKey);
        }
        return message;
    }

    public static String getLogMessage(String message) {
        MDC.remove(logDataKey);
        return message;
    }

}