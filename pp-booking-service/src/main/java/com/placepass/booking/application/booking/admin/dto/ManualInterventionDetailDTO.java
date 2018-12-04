package com.placepass.booking.application.booking.admin.dto;

public class ManualInterventionDetailDTO {

    // This will be true if this particular event need a manual intervention
    private boolean isManualInterventionRequired;

    // This will hold the reason for the manual intervention
    private String reasonForManualIntervention;

    // This will be default to false; Can be changed by admin portal once the issue is resolved
    private boolean resolved = false;

    // This will contain the steps to resolve the issue and will be updated by admin portal
    private String resolutionText;

	public boolean isManualInterventionRequired() {
		return isManualInterventionRequired;
	}

	public void setManualInterventionRequired(boolean isManualInterventionRequired) {
		this.isManualInterventionRequired = isManualInterventionRequired;
	}

	public String getReasonForManualIntervention() {
		return reasonForManualIntervention;
	}

	public void setReasonForManualIntervention(
			String reasonForManualIntervention) {
		this.reasonForManualIntervention = reasonForManualIntervention;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public String getResolutionText() {
		return resolutionText;
	}

	public void setResolutionText(String resolutionText) {
		this.resolutionText = resolutionText;
	}

}
