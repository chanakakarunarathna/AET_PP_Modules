package com.placepass.booking.application.booking.admin.dto;

import java.util.Map;

import com.placepass.booking.domain.booking.EventName;
import com.placepass.booking.application.booking.admin.dto.ManualInterventionDetailDTO;
import com.placepass.booking.application.booking.admin.dto.StatusDTO;

public class BookingEventDTO {

	  private String id;
	    
	  private String spanId;
	  
	  private String traceId;

	  // This will hold the order of the booking events
	  private int index;

	  private String createdTime;
	  
	  private String updatedTime;

	  private EventName eventName;

	  // This will capture the statuses like {@link PlatformStatus#PAYMENT_TIMEOUT} , {@link
	  // PlatformStatus#BOOKING_TIMEOUT}, {@link PlatformStatus#REVERSAL_FAILED}, etc with the connector and external
	  // statuses. This field will be null for a event like PAYMENT_REQUEST_SENT.
	  private StatusDTO status;

	  // This will hold any additional details sent/received (e.g. for the event {@link EventName#EMAIL_PROCESSED} there
	  // could be REFUND_MODE=PARTIAL, REFUND_AMOUNT=102.20, EMAIL_TYPE=CANCELLATION_EMAIL, EMAIL_STATUS=Failed,
	  // EMAIL_ERROR=NullPointerException......)
	  private Map<String, String> extendedAttributes;

	  private ManualInterventionDetailDTO manualInterventionDetail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public EventName getEventName() {
		return eventName;
	}

	public void setEventName(EventName eventName) {
		this.eventName = eventName;
	}

	public StatusDTO getStatus() {
		return status;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}

	public Map<String, String> getExtendedAttributes() {
		return extendedAttributes;
	}

	public void setExtendedAttributes(Map<String, String> extendedAttributes) {
		this.extendedAttributes = extendedAttributes;
	}

	public ManualInterventionDetailDTO getManualInterventionDetail() {
		return manualInterventionDetail;
	}

	public void setManualInterventionDetail(ManualInterventionDetailDTO manualInterventionDetail) {
		this.manualInterventionDetail = manualInterventionDetail;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	
}
