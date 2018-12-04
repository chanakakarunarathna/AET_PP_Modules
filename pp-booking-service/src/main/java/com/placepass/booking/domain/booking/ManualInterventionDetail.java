package com.placepass.booking.domain.booking;

public class ManualInterventionDetail {

	public ManualInterventionDetail(boolean isManualInterventionRequired,
			String reasonForManualIntervention) {
		super();
		this.isManualInterventionRequired = isManualInterventionRequired;
		this.reasonForManualIntervention = reasonForManualIntervention;
		this.resolved = false;
	}

	public ManualInterventionDetail(boolean isManualInterventionRequired,
			String reasonForManualIntervention, boolean resolved,
			String resolutionText) {
		super();
		this.isManualInterventionRequired = isManualInterventionRequired;
		this.reasonForManualIntervention = reasonForManualIntervention;
		this.resolved = resolved;
		this.resolutionText = resolutionText;
	}

	public ManualInterventionDetail(){
		super();
	}
	
	
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

	public String getReasonForManualIntervention() {
		return reasonForManualIntervention;
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
