package com.placepass.userservice.controller.user;

import static com.placepass.userservice.platform.common.CommonConstants.AUTHORIZATION_DATATYPE;
import static com.placepass.userservice.platform.common.CommonConstants.AUTHORIZATION_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.AUTHORIZATION_PARAMTYPE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_METHOD_POST;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS_CODE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_FORBIDDEN_STATUS_CODE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_SUCCESS_STATUS_CODE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_UNAUTHORIZED_STATUS_CODE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_UNAUTHORIZED_STATUS_DESCRIPTION;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.userservice.application.UserAuthenticationApplicationService;
import com.placepass.userservice.controller.dto.AuthenticationByProviderRQ;
import com.placepass.userservice.controller.dto.AuthenticationByProviderRS;
import com.placepass.userservice.controller.dto.AuthenticationRQ;
import com.placepass.userservice.controller.dto.AuthenticationRS;
import com.placepass.userservice.controller.dto.TokenVerificationRS;
import com.placepass.userservice.domain.user.exception.AuthenticationFailedException;
import com.placepass.userservice.domain.user.exception.PartnerInvalidException;
import com.placepass.userservice.domain.user.exception.PartnerNotFoundException;
import com.placepass.userservice.domain.user.exception.AccessDeniedException;
import com.placepass.userservice.platform.common.CommonConstants;
import com.placepass.userservice.platform.common.LoggingUtil;
import com.placepass.userservice.platform.common.RequestMappings;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class UserAuthenticationController.
 */
@Controller
public class UserAuthenticationController extends HTTPResponseHandler {
    
    /** The logger. */
    private org.slf4j.Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

	/** The user authentication application service. */
	@Autowired
	private UserAuthenticationApplicationService userAuthenticationApplicationService;
	
	/**
	 * Authenticate.
	 *
	 * @param request the request
	 * @param response the response
	 * @param authenticationRQ the authentication rq
	 * @return the authentication rs
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "User Authentication", nickname = "User Authentication")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_UNAUTHORIZED_STATUS_CODE, message = HTTP_STATUS_UNAUTHORIZED_STATUS_DESCRIPTION) })
	@RequestMapping(value = RequestMappings.AUTHENTICATE_USER, method = RequestMethod.POST)
    public @ResponseBody AuthenticationRS authenticate(HttpServletRequest request, HttpServletResponse response,
            @RequestBody AuthenticationRQ authenticationRQ) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
		AuthenticationRS authenticationRS = userAuthenticationApplicationService.authenticate(authenticationRQ, partnerId);
		setStatusHeadersToSuccess(response);
		
		return authenticationRS;
	}

	/**
	 * Verify token.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the token verification rs
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Verify User Authentication Token", nickname = "Verify User Authentication Token")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true),
        @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = AUTHORIZATION_DESCRIPTION, dataType = AUTHORIZATION_DATATYPE, paramType = AUTHORIZATION_PARAMTYPE, required = true)})
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_FORBIDDEN_STATUS_CODE, message = HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION) })
	@RequestMapping(value = RequestMappings.VERIFY_USER_AUTHENTICATION_TOKEN, method = RequestMethod.POST)
	public @ResponseBody TokenVerificationRS verifyToken(HttpServletRequest request, HttpServletResponse response) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		TokenVerificationRS tokenVerificationRS = userAuthenticationApplicationService.verifyToken(token, partnerId);
		setStatusHeadersToSuccess(response);
		return tokenVerificationRS;
	}
	
	/**
	 * Generate guest user authentication token.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the authentication rs
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Generate Guest User Authentication Token", nickname = "Generate Guest User Authentication Token")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_UNAUTHORIZED_STATUS_CODE, message = HTTP_STATUS_UNAUTHORIZED_STATUS_DESCRIPTION)})
	@RequestMapping(value = RequestMappings.GENERATE_GUEST_AUTHENTICATION_TOKEN, method = RequestMethod.POST)
	public @ResponseBody AuthenticationRS generateGuestUserAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
	    AuthenticationRS authenticationRS = userAuthenticationApplicationService.generateGuestUserAuthenticationToken(partnerId);
		setStatusHeadersToSuccess(response);
		return authenticationRS;
	}
	
	/**
	 * Authentication by provider.
	 *
	 * @param request the request
	 * @param response the response
	 * @param authenticationByProviderRQ the authentication by provider rq
	 * @return the authentication by provider rs
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Authentication By Provider", nickname = "Authentication By Provider")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_UNAUTHORIZED_STATUS_CODE, message = HTTP_STATUS_UNAUTHORIZED_STATUS_DESCRIPTION)})
	@RequestMapping(value = RequestMappings.AUTHENTICATION_BY_PROVIDER, method = RequestMethod.POST)
	public @ResponseBody AuthenticationByProviderRS authenticationByProvider(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AuthenticationByProviderRQ authenticationByProviderRQ) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
	    AuthenticationByProviderRS authenticationByProviderRS = userAuthenticationApplicationService.authenticationByProvider(partnerId, authenticationByProviderRQ);
		setStatusHeadersToSuccess(response);
		return authenticationByProviderRS;
	}
	
	/**
	 * Removes the user authentication token.
	 *
	 * @param request the request
	 * @param response the response
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Remove User Authentication Token", nickname = "Remove User Authentication Token")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true),
        @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = AUTHORIZATION_DESCRIPTION, dataType = AUTHORIZATION_DATATYPE, paramType = AUTHORIZATION_PARAMTYPE, required = true)})
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_FORBIDDEN_STATUS_CODE, message = HTTP_STATUS_FORBIDDEN_STATUS_DESCRIPTION) })
	@RequestMapping(value = RequestMappings.REMOVE_USER_AUTHENTICATION_TOKEN, method = RequestMethod.POST)
	public @ResponseBody void removeUserAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
	    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		userAuthenticationApplicationService.removeUserAuthenticationToken(token, partnerId);
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
	 * Handle authentication failed exception.
	 *
	 * @param response the response
	 * @param exception the exception
	 */
	@ExceptionHandler(AuthenticationFailedException.class)
	public void handleAuthenticationFailedException(HttpServletResponse response, AuthenticationFailedException exception) {
	    LoggingUtil.logExceptionHandler(logger, "Authentication Failed Error : " + HttpStatus.UNAUTHORIZED.value(), exception);
		setStatusHeaders(response, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
				exception.getMessage());
	}
	
	/**
	 * Handle unauthorized exception.
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
