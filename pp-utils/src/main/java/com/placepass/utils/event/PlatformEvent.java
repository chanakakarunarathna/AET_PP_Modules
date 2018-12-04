package com.placepass.utils.event;

import java.util.HashMap;
import java.util.Map;

public class PlatformEvent {

    private String eventName;

    private Map<String, String> extraHeaders = new HashMap<String, String>();

    private Map<String, String> eventAttributes = new HashMap<String, String>();;

    /**
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the extraHeaders
     */
    public Map<String, String> getExtraHeaders() {
        return extraHeaders;
    }

    /**
     * @param extraHeaders the extraHeaders to set
     */
    public void setExtraHeaders(Map<String, String> extraHeaders) {
        this.extraHeaders = extraHeaders;
    }

    /**
     * @return the eventAttributes
     */
    public Map<String, String> getEventAttributes() {
        return eventAttributes;
    }

    /**
     * @param eventAttributes the eventAttributes to set
     */
    public void setEventAttributes(Map<String, String> eventAttributes) {
        this.eventAttributes = eventAttributes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PlatformEvent [" + (eventName != null ? "eventName=" + eventName + ", " : "")
                + (extraHeaders != null ? "extraHeaders=" + extraHeaders + ", " : "")
                + (eventAttributes != null ? "eventAttributes=" + eventAttributes : "") + "]";
    }

}
