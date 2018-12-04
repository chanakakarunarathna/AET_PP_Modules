package com.placepass.userservice.platform.common;

public interface RequestMappings {

	public static String CONTEXT_PATH = "";

	public static String CONTEXT_PATH_ROOT = "/";

	public static String CREATE_USER = "/user";

	public static String CREATE_GUEST_USER = "/user/guest";

	public static String UPDATE_USER = "/user/update";

	public static String RETRIEVE_USER_BY_EMAIL = "/user/retrieve";
	
	public static String RETRIEVE_USER_BY_TOKEN = "/user/profile/retrieve";

	public static String RETRIEVE_USERS = "/users";
	
	public static String UPDATE_USER_PASSWORD = "/user/updatepassword";
	
	public static String VERIFICATION = "/verification/{code}";
	
	public static String FORGOT_PASSWORD = "/forgotpassword";
	
	public static String FORGOT_PASSWORD_VERIFICATION = "/forgotpassword/verification/{code}";
	
	public static String FORGOT_PASSWORD_RESET = "/resetpassword";
	
	public static String VERIFICATION_RESEND = "/verification/resend";
	
	public static String AUTHENTICATE_USER = "/auth";
	
	public static String VERIFY_USER_AUTHENTICATION_TOKEN = "/auth/token";
	
	public static String GENERATE_GUEST_AUTHENTICATION_TOKEN = "/auth/token/generate";
	
	public static String REMOVE_USER_AUTHENTICATION_TOKEN = "/auth/token/remove";

	public static String AUTHENTICATION_BY_PROVIDER = "/auth/provider";

}
