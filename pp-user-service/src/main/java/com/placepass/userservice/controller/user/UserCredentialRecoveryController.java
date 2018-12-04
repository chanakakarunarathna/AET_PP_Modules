package com.placepass.userservice.controller.user;

import static com.placepass.userservice.platform.common.CommonConstants.HTTP_METHOD_GET;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_METHOD_POST;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS_CODE;
import static com.placepass.userservice.platform.common.CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION;
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
import com.placepass.userservice.controller.dto.ForgotPasswordRQ;
import com.placepass.userservice.controller.dto.ForgotPasswordVerificationCode;
import com.placepass.userservice.controller.dto.ResetPasswordRQ;
import com.placepass.userservice.domain.user.exception.PartnerInvalidException;
import com.placepass.userservice.domain.user.exception.PartnerNotFoundException;
import com.placepass.userservice.domain.user.exception.VerificationCodeExpiredException;
import com.placepass.userservice.platform.common.LoggingUtil;
import com.placepass.userservice.platform.common.RequestMappings;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class UserCredentialRecoveryController.
 */
@Controller
public class UserCredentialRecoveryController extends HTTPResponseHandler {
    
    /** The logger. */
    private org.slf4j.Logger logger = LoggerFactory.getLogger(UserCredentialRecoveryController.class);

	/** The user management application service. */
	@Autowired
	private UserManagementApplicationService userManagementApplicationService;
	
	/**
	 * Forgot password.
	 *
	 * @param request the request
	 * @param response the response
	 * @param forgotPasswordRQ the forgot password rq
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Forgot password", nickname = "Forgot password")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION) })
	@RequestMapping(value = RequestMappings.FORGOT_PASSWORD, method = RequestMethod.POST)
    public void forgotPassword(HttpServletRequest request, HttpServletResponse response,
            @RequestBody ForgotPasswordRQ forgotPasswordRQ) {
        String partnerId = request.getHeader(PARTNER_ID_HEADER);
        userManagementApplicationService.forgotPassword(partnerId, forgotPasswordRQ);
        setStatusHeadersToSuccess(response);
    }
	
	
	/**
	 * Verify forgot password code.
	 *
	 * @param request the request
	 * @param response the response
	 * @param code the code
	 * @return the forgot password verification code
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_GET, value = "Verify forgot password code", nickname = "Verify forgot password code")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION, response = ForgotPasswordVerificationCode.class),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION) })
	@RequestMapping(value = RequestMappings.FORGOT_PASSWORD_VERIFICATION, method = RequestMethod.GET)
    public @ResponseBody ForgotPasswordVerificationCode verifyForgotPasswordCode(HttpServletRequest request,
            HttpServletResponse response, @PathVariable String code) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
		ForgotPasswordVerificationCode forgotPasswordVerificationCode = userManagementApplicationService.verifyForgotPasswordCode(partnerId, code);
		setStatusHeadersToSuccess(response);
		return forgotPasswordVerificationCode;
	}
	
	/**
	 * Reset password.
	 *
	 * @param response the response
	 * @param partnerId the partner id
	 * @param resetPasswordRQ the reset password rq
	 */
	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Reset password", nickname = "Reset password")
	@ApiImplicitParams({
        @ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true)})
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION) })
	@RequestMapping(value = RequestMappings.FORGOT_PASSWORD_RESET, method = RequestMethod.POST)
    public void resetPassword(HttpServletRequest request, HttpServletResponse response,
            @RequestBody ResetPasswordRQ resetPasswordRQ) {
	    String partnerId = request.getHeader(PARTNER_ID_HEADER);
		userManagementApplicationService.resetPassword(partnerId, resetPasswordRQ);
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
	 * Handle verification code expired exception.
	 *
	 * @param response the response
	 * @param exception the exception
	 */
	@ExceptionHandler(VerificationCodeExpiredException.class)
	public void handleVerificationCodeExpiredException(HttpServletResponse response, VerificationCodeExpiredException exception) {
	    LoggingUtil.logExceptionHandler(logger, "Verification Code Expired Error : " + HttpStatus.BAD_REQUEST.value(), exception);
		setStatusHeaders(response, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
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
