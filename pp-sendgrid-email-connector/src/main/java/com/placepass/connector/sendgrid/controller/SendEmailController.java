package com.placepass.connector.sendgrid.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placepass.connector.sendgrid.application.EventApplicationService;
import com.placepass.connector.sendgrid.infrastructure.dto.SendEmailRQ;
import com.placepass.exutil.HTTPResponseHandler;
import com.placepass.utils.logging.PlatformLoggingKey;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SendEmailController extends HTTPResponseHandler{

	@Autowired
	EventApplicationService eventApplicationService;
	
    private static final Logger logger = LoggerFactory.getLogger(SendEmailController.class);

    @ApiOperation(httpMethod = "POST", value = "Send Email", nickname = "Send Email")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "partner-id", value = "Partner ID", dataType = "string", paramType = "header", required = true)})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(value = "/sendemail", method = RequestMethod.POST)
    @ResponseBody
	public void sendEmail(HttpServletResponse response, HttpServletRequest request,
			@RequestBody @Valid SendEmailRQ sendEmailRequest,
			@RequestHeader(value = "partner-id", required = true) String partnerId) { 
		
        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
        logData.put(PlatformLoggingKey.PLATFORM_EVENT_NAME.name(), sendEmailRequest.getPlatformEventName().name());
        logger.info("Received 'Send Email' Request. {}", logData);

        setStatusHeadersToSuccess(response);
        eventApplicationService.sendEmail(partnerId, sendEmailRequest.getPlatformEventName().name(), sendEmailRequest.getEventAttributes());
		
        logger.info("Returning 'Send Email' Response.");
        
	}
	
}
