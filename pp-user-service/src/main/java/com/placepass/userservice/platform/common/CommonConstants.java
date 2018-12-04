package com.placepass.userservice.platform.common;

public interface CommonConstants {

	/** The partner id header. */
	public static String PARTNER_ID_HEADER = "partner-id";

	public static String PARTNER_ID_DESCRIPTION = "Partner ID";

	public static String PARTNER_ID_DATATYPE = "string";

	public static String PARTNER_ID_PARAMTYPE = "header";

	public static String HTTP_METHOD_GET = "GET";

	public static String HTTP_METHOD_POST = "POST";

	public static String HTTP_METHOD_PUT = "PUT";

	public static String HTTP_METHOD_DELETE = "DELETE";

	public static int HTTP_STATUS_SUCCESS_STATUS_CODE = 200;

	public static String HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION = "Success";

	public static int HTTP_STATUS_BAD_REQUEST_STATUS_CODE = 400;

	public static String HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION = "Bad Request";
	
	public static String PARTNER_ID = "PARTNER_ID";
	
	public static String USER_ID = "USER_ID";
	
	public static String EMAIL = "EMAIL";
	
	public static String FIRST_NAME = "FIRST_NAME";
	
	public static String LAST_NAME = "LAST_NAME";
	
	public static String VERIFICATION_URL = "VERIFICATION_URL";
	
	public static String VERIFICATION_CODE = "VERIFICATION_CODE";
	
	public static String FORGOT_PASSWORD_URL = "FORGOT_PASSWORD_URL";
	
	public static String DATE_SEPERATOR = "-";
	
	public static final String CREATED_DATE = "createdDate";
	
	public static final String PLATFORM_EVENT_NAME = "PLATFORM_EVENT_NAME";
	
	public static final String USER_ENTITY_NAME = "User";
	
	public static final int USER_AUTHENTICATION_TOKEN_DOCUMENT_EXPIRY = 0;
	
	public static final String USER_AUTHENTICATION_TOKEN_EXPIRY_DATE = "expiryDate";
	
	public static String DASH = "-";
	
	public static String EMPTY = "";
	
	public static String AUTHORIZATION_DESCRIPTION = "Authorization Header";
	
	public static String AUTHORIZATION_DATATYPE = "string";

	public static String AUTHORIZATION_PARAMTYPE = "header";
	
	public static String GUEST_USER_PREFIX = "guest:";
	
	public static int HTTP_STATUS_UNAUTHORIZED_STATUS_CODE = 401;

	public static String HTTP_STATUS_UNAUTHORIZED_STATUS_DESCRIPTION = "Unauthorized";
	
	public static int HTTP_STATUS_FORBIDDEN_STATUS_CODE = 403;

	public static String HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION = "Forbidden";

    public static String UTF_8_ENCODING = "UTF-8";

    public static String REGEX_SPACE = "\\s";

    public static String PLUS = "+";
	
	public static final String REWARDS_ID = "RewardsID";
	
	public static String TIMEOUT_VALUE = "TIMEOUT_VALUE";
	
	public static String PARTNER_ID_NOT_FOUND_HEADER = "PARTNER_ID_NOT_FOUND";
	
	public static String INVALID_PARTNER_ID_HEADER = "INVALID_PARTNER_ID";
	
}
