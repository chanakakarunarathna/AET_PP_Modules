package com.placepass.userservice.infrastructure.gigya;

/**
 * The Enum Gigya GSErrorCode.
 */
public enum GSErrorCode {

    SUCCESS(0, "Success"),
    REQUEST_HAS_EXPIRED(403002, "Signature has expired."),
    INVALID_PARAMETER_VALUE(400006, "Invalid externalUserId, providerToken or signatureTimestamp parameter value"),
    GENERAL_SERVER_ERROR(500001, "General Server Error");

    public final int errorCode;
    
    public final String message;

    GSErrorCode(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
    
    

}
