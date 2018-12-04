package com.placepass.booking.application.booking.admin.dto;

import java.util.Map;

import com.placepass.booking.domain.platform.PlatformStatus;

public class StatusDTO {

    /**
     * Domain service work flow status. For any domain API call, this is input or sent out as it is.
     */
    private PlatformStatus status;

    /**
     * Internal integration status of vendor, payment connector if available. This the translated connector domain
     * status.
     */
    private String connectorStatus;

    /**
     * Raw non translated status as sent by external parties such vendor, payment gateway etc..
     */
    private Map<String, String> externalStatus;

	public PlatformStatus getStatus() {
		return status;
	}

	public void setStatus(PlatformStatus status) {
		this.status = status;
	}

	public String getConnectorStatus() {
		return connectorStatus;
	}

	public void setConnectorStatus(String connectorStatus) {
		this.connectorStatus = connectorStatus;
	}

	public Map<String, String> getExternalStatus() {
		return externalStatus;
	}

	public void setExternalStatus(Map<String, String> externalStatus) {
		this.externalStatus = externalStatus;
	}
	
}
