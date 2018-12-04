package com.placepass.exutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

public abstract class HTTPResponseHandler {

    private static final String STATUS_CODE = "pp-code";

    private static final String STATUS_MESSAGE = "pp-message";

    private static final String EXTERNAL_MESSAGE = "pp-external-message";

    private static final Logger logger = LoggerFactory.getLogger(HTTPResponseHandler.class);

    public HTTPResponseHandler() {
    }

    public void setStatusHeaders(HttpServletResponse response, int httpStatus, String code, String message) {
        response.setStatus(httpStatus);
        response.setHeader(STATUS_CODE, code);
        response.setHeader(STATUS_MESSAGE, message);
    }

    public void setStatusHeaders(HttpServletResponse response, int httpStatus, String code, String message,
            String externalMessage) {
        response.setStatus(httpStatus);
        response.setHeader(STATUS_CODE, code);
        response.setHeader(STATUS_MESSAGE, message);
        response.setHeader(EXTERNAL_MESSAGE, externalMessage);
    }

    public void setStatusHeadersToSuccess(HttpServletResponse response) {
        int status = HttpStatus.OK.value();
        setStatusHeaders(response, status, "SUCCESS", "Success");
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public void handleHttpStatusCodeException(HttpStatusCodeException ex, HttpServletRequest request,
            HttpServletResponse response) {

        String code = "";
        String message = "";
        int status = ex.getStatusCode().value();

        try {
            code = ex.getResponseHeaders().get(STATUS_CODE).get(0);
            message = ex.getResponseHeaders().get(STATUS_MESSAGE).get(0);
        } catch (NullPointerException e) {

        }

        logger.error(message, ex);

        setStatusHeaders(response, status, code, message);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
            HttpServletRequest request, HttpServletResponse response) {

        String code = "BAD_REQUEST";
        String message = ex.getMessage();
        int status = HttpStatus.BAD_REQUEST.value();

        logger.error(message, ex);

        setStatusHeaders(response, status, code, message);

    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public void handleServletRequestBindingException(ServletRequestBindingException ex, HttpServletRequest request,
            HttpServletResponse response) {

        String code = "BAD_REQUEST";
        String message = ex.getMessage();
        int status = HttpStatus.BAD_REQUEST.value();

        logger.error(message, ex);

        setStatusHeaders(response, status, code, message);

    }

    @ExceptionHandler(HTTPException.class)
    public void handleHTTPException(HTTPException ex, HttpServletRequest request, HttpServletResponse response) {

        String code = ex.getStatusCode();
        String message = ex.getStatusMessage();
        String externalMessage = ex.getExternalMessage();
        int status = ex.getHttpStatus().value();

        if (externalMessage != null) {
            setStatusHeaders(response, status, code, message, externalMessage);
        } else {
            setStatusHeaders(response, status, code, message);
        }

        logger.error(message, ex);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request,
            HttpServletResponse response) {

        String code = HttpStatus.BAD_REQUEST.name();
        String message = HttpStatus.BAD_REQUEST.name();

        if (ex.getBindingResult() != null && ex.getBindingResult().hasErrors()) {
            List<FieldError> unmodifiableErrorList = ex.getBindingResult().getFieldErrors();
            List<FieldError> errorList = new ArrayList<FieldError>(unmodifiableErrorList);
            Collections.sort(errorList, new Comparator<FieldError>() {

                @Override
                public int compare(FieldError arg0, FieldError arg1) {
                    return arg0.getDefaultMessage().compareTo(arg1.getDefaultMessage());
                }
            });

            message = errorList.get(0).getDefaultMessage();
        }

        int status = HttpStatus.BAD_REQUEST.value();

        setStatusHeaders(response, status, code, message);

        logger.error(message, ex);

    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        String code = HttpStatus.INTERNAL_SERVER_ERROR.name();
        String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        setStatusHeaders(response, status, code, message);

        logger.error(message, ex);

    }

}
