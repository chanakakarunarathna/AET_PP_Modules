package com.placepass.booking.domain.booking;

import java.time.Instant;
import java.util.Map;

import org.bson.types.ObjectId;

import com.placepass.booking.domain.platform.Status;

/**
 * The Class BookingEvent.
 */
public class BookingEvent {
	
	public BookingEvent(){
		
	}
	
    public BookingEvent(int index, EventName eventName, Map<String, String> extendedAttributes, long spanId, long traceId) {
        
    	this.id = ObjectId.get().toString();
        this.createdTime = Instant.now();
        this.index = index;
        this.eventName = eventName;
        this.extendedAttributes = extendedAttributes;
    	this.spanId = spanId;
    	this.traceId = traceId;

    }

    private String id;
    
    private long spanId;
    
    private long traceId;

    // This will hold the order of the booking events
    private int index;

    private Instant createdTime;

    private Instant updatedTime;

    private EventName eventName;

    // This will capture the statuses like {@link PlatformStatus#PAYMENT_TIMEOUT} , {@link
    // PlatformStatus#BOOKING_TIMEOUT}, {@link PlatformStatus#REVERSAL_FAILED}, etc with the connector and external
    // statuses. This field will be null for a event like PAYMENT_REQUEST_SENT.
    private Status status;

    // This will hold any additional details sent/received (e.g. for the event {@link EventName#EMAIL_PROCESSED} there
    // could be REFUND_MODE=PARTIAL, REFUND_AMOUNT=102.20, EMAIL_TYPE=CANCELLATION_EMAIL, EMAIL_STATUS=Failed,
    // EMAIL_ERROR=NullPointerException......)
    private Map<String, String> extendedAttributes;

    private ManualInterventionDetail manualInterventionDetail; 

    public String getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public EventName getEventName() {
        return eventName;
    }

    public void setEventName(EventName eventName) {
        this.eventName = eventName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

	public long getSpanId() {
		return spanId;
	}

	public long getTraceId() {
		return traceId;
	}

	public ManualInterventionDetail getManualInterventionDetail() {
		return manualInterventionDetail;
	}

	public void setManualInterventionDetail(ManualInterventionDetail manualInterventionDetail) {
		this.manualInterventionDetail = manualInterventionDetail;
	}

	public Map<String, String> getExtendedAttributes() {
		return extendedAttributes;
	}

	public void setExtendedAttributes(Map<String, String> extendedAttributes) {
		this.extendedAttributes = extendedAttributes;
	}

	public Instant getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Instant updatedTime) {
		this.updatedTime = updatedTime;
	}
	
}
