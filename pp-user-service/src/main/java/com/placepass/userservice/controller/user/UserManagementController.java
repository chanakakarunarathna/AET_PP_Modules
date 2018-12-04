package com.placepass.userservice.controller.user;

import static com.placepass.userservice.platform.common.CommonConstants.AUTHORIZATION_DATATYPE;
import static com.placepass.userservice.platform.common.CommonConstants.AUTHORIZATION_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.AUTHORIZATION_PARAMTYPE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_METHOD_GET;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_METHOD_POST;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_METHOD_PUT;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS_CODE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_FORBIDDEN_STATUS_CODE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_SUCCESS_STATUS_CODE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.PARTNER_ID_DATATYPE;
import static com.placepass.userservice.platform.common.CommonConstants.PARTNER_ID_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.PARTNER_ID_HEADER;
import static com.placepass.userservice.platform.common.CommonConstants.PARTNER_ID_PARAMTYPE;
import static com.placepass.userservice.platform.common.CommonConstants.PARTNER_ID_NOT_FOUND_HEADER;
import static com.placepass.userservice.platform.common.CommonConstants.INVALID_PARTNER_ID_HEADER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.userservice.application.UserManagementApplicationService;
import com.placepass.userservice.controller.dto.ResendVerificationRQ;
import com.placepass.userservice.controller.dto.RetrieveEmailRQ;
import com.placepass.userservice.controller.dto.UpdatePasswordRQ;
import com.placepass.userservice.controller.dto.UpdateUserRQ;
import com.placepass.userservice.controller.dto.UserProfileRS;
import com.placepass.userservice.controller.dto.UserRQ;
import com.placepass.userservice.controller.dto.UserRS;
import com.placepass.userservice.domain.user.UserType;
import com.placepass.userservice.domain.user.exception.PartnerInvalidException;
import com.placepass.userservice.domain.user.exception.PartnerNotFoundException;
import com.placepass.userservice.domain.user.exception.AccessDeniedException;
import com.placepass.userservice.platform.common.LoggingUtil;
import com.placepass.userservice.platform.common.RequestMappings;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class UserManagementController.
 * 
 * @author shanakak
 */
@Controller
public class UserManagementController extends HTTPResponseHandler {
    
    /** The logger. */
    private org.slf4j.Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	/** The user management application service. */
	@Autowired
	private UserManagementApplicationService userManagementApplicationService;

	/**
	 * Creates the user.
	 *
	 * @param request the request
	 * @param response the response
	 * @param userRQ the user rq
	 * @return the user rs
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Create User", nickname = "Create a new user")
	@ApiImplicitParams({
			@ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true) })
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION, response = UserRS.class),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION) })
	@RequestMapping(value = RequestMappings.CREATE_USER, method = RequestMethod.POST)
    public @ResponseBody UserRS createUser(HttpServletRequest request, HttpServletResponse response,
            @RequestBody UserRQ userRQ) {
        String partnerId = request.getHeader(PARTNER_ID_HEADER);
        UserRS userRS = userManagementApplicationService.createUser(partnerId, userRQ, UserType.ENROLLED);
        setStatusHeadersToSuccess(response);
        return userRS;
    }
	
	/**
	 * Update user.
	 *
	 * @param request the request
	 * @param response the response
	 * @param updateUserRQ the update user rq
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_PUT, value = "Update User", nickname = "Update the user")
    @ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true),
        @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = AUTHORIZATION_DESCRIPTION, dataType = AUTHORIZATION_DATATYPE, paramType = AUTHORIZATION_PARAMTYPE, required = true)})
    @ApiResponses(value = {
    	@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),	
        @ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION),
        @ApiResponse(code = HTTP_STATUS_FORBIDDEN_STATUS_CODE, message = HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION)})
	@RequestMapping(value = RequestMappings.UPDATE_USER, method = RequestMethod.PUT)
	public void updateUser(HttpServletRequest request, HttpServletResponse response, @RequestBody UpdateUserRQ updateUserRQ) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
	    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		userManagementApplicationService.updateUser(partnerId, updateUserRQ, token);
		setStatusHeadersToSuccess(response);
	}
	
	/**
	 * Retrieve user by email. This is a POST HTTP method since the email is sent.
	 *
	 * @param request the request
	 * @param response the response
	 * @param retrieveEmailRQ the retrieve email rq
	 * @return the user rs
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Retrieve user by email", nickname = "Retrieve the user by email")
    @ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
    @ApiResponses(value = {
    	@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION, response = UserRS.class),
        @ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION)})
	@RequestMapping(value = RequestMappings.RETRIEVE_USER_BY_EMAIL, method = RequestMethod.POST)
	public @ResponseBody UserRS retrieveUserByEmail(HttpServletRequest request, HttpServletResponse response,
			@RequestBody RetrieveEmailRQ retrieveEmailRQ) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
		UserRS userRS = userManagementApplicationService.retrieveUserByEmail(partnerId, retrieveEmailRQ);
		
		setStatusHeadersToSuccess(response);
		return userRS;
	}

	/**
	 * Verify email. There is no partner details in the header since this may be invoked by clicking an email link.
	 *
	 * @param request the request
	 * @param response the response
	 * @param code the code
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_GET, value = "Verify email", nickname = "Verify email")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
    @ApiResponses(value = {
    	@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
        @ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION)})
	@RequestMapping(value = RequestMappings.VERIFICATION, method = RequestMethod.GET)
	public void verification(HttpServletRequest request, HttpServletResponse response, @PathVariable String code) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
		userManagementApplicationService.verifyCode(partnerId, code);
		
		setStatusHeadersToSuccess(response);
		
	}
		
	/**
	 * Update password.
	 *
	 * @param request the request
	 * @param response the response
	 * @param updatePasswordRQ the update password rq
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_PUT, value = "Update password", nickname = "Update password")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true),
        @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = AUTHORIZATION_DESCRIPTION, dataType = AUTHORIZATION_DATATYPE, paramType = AUTHORIZATION_PARAMTYPE, required = true)})
    @ApiResponses(value = {
    	@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
        @ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION),
        @ApiResponse(code = HTTP_STATUS_FORBIDDEN_STATUS_CODE, message = HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION)})
	@RequestMapping(value = RequestMappings.UPDATE_USER_PASSWORD, method = RequestMethod.PUT)
	public void updatePassword(HttpServletRequest request, HttpServletResponse response, @RequestBody UpdatePasswordRQ updatePasswordRQ){
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
	    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
	    userManagementApplicationService.updatePassword(partnerId, updatePasswordRQ, token);
		setStatusHeadersToSuccess(response);
	}
	
	/**
	 * Retrieve user by email.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the user profile rs
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_GET, value = "Retrieve the user profile", nickname = "Retrieve the user profile")
    @ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true),
        @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = AUTHORIZATION_DESCRIPTION, dataType = AUTHORIZATION_DATATYPE, paramType = AUTHORIZATION_PARAMTYPE, required = true)})
    @ApiResponses(value = {
    	@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION, response = UserProfileRS.class),
        @ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION),
        @ApiResponse(code = HTTP_STATUS_FORBIDDEN_STATUS_CODE, message = HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION)})
	@RequestMapping(value = RequestMappings.RETRIEVE_USER_BY_TOKEN, method = RequestMethod.GET)
	public @ResponseBody UserProfileRS retrieveUserByToken(HttpServletRequest request, HttpServletResponse response) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		UserProfileRS userProfileRS = userManagementApplicationService.retrieveUserByToken(partnerId, token);
		
		setStatusHeadersToSuccess(response);
		return userProfileRS;
	}
	
	/**
	 * Resend verification.
	 *
	 * @param request the request
	 * @param response the response
	 * @param resendVerificationRQ the resend verification rq
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Resend Verification", nickname = "Resend Verification")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
    @ApiResponses(value = {
    	@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
        @ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION)})
	@RequestMapping(value = RequestMappings.VERIFICATION_RESEND, method = RequestMethod.POST)
	public void resendVerification(HttpServletRequest request, HttpServletResponse response, @RequestBody ResendVerificationRQ resendVerificationRQ) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
		userManagementApplicationService.resendVerification(partnerId, resendVerificationRQ);
		
		setStatusHeadersToSuccess(response);
		
	}
	
	/**
	 * Handle illegal argument exception.
	 *
	 * @param response the response
	 * @param exception the exception
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(HttpServletResponse response, IllegalArgumentException exception) {
	    LoggingUtil.logExceptionHandler(logger, "Illegal Argument Error : " + HttpStatus.BAD_REQUEST.value(), exception);
		setStatusHeaders(response, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
				exception.getMessage());
	}
	
    /**
     * Handle access denied exception.
     *
     * @param response the response
     * @param exception the exception
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse response, AccessDeniedException exception) {
        LoggingUtil.logExceptionHandler(logger, "Access Denied Error : " + HttpStatus.FORBIDDEN.value(), exception);
        setStatusHeaders(response, HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(),
                exception.getMessage());
    }
    
	/**
	 * Handle partner not found exception.
	 *
	 * @param response the response
	 * @param exception the exception
	 */
	@ExceptionHandler(PartnerNotFoundException.class)
	public void handlePartnerNotFoundException(HttpServletResponse response, PartnerNotFoundException exception) {
	    LoggingUtil.logExceptionHandler(logger, "Partner Not Found Error : " + HttpStatus.UNAUTHORIZED.value(), exception);
		setStatusHeaders(response, HttpStatus.UNAUTHORIZED.value(), PARTNER_ID_NOT_FOUND_HEADER,
				exception.getMessage());
	}
	
	/**
	 * Handle partner invalid exception.
	 *
	 * @param response the response
	 * @param exception the exception
	 */
	@ExceptionHandler(PartnerInvalidException.class)
    public void handlePartnerInvalidException(HttpServletResponse response, PartnerInvalidException exception) {
        LoggingUtil.logExceptionHandler(logger, "Partner Invalid Error : " + HttpStatus.UNAUTHORIZED.value(), exception);
        setStatusHeaders(response, HttpStatus.UNAUTHORIZED.value(), INVALID_PARTNER_ID_HEADER,
                exception.getMessage());
    }

}
