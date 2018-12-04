package com.placepass.search.application.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.placepass.exutil.HTTPException;

public abstract class HTTPResponseHandler {

    private static final String STATUS_CODE = "pp-code";

    private static final String STATUS_MESSAGE = "pp-message";

    private static final String PARAMS = "query-parameter";

    private static final Logger logger = LoggerFactory.getLogger(HTTPResponseHandler.class);

    public HTTPResponseHandler() {
    }

    public void setStatusHeaders(HttpServletResponse response, int httpStatus, String code, String message,
            String params) {
        response.setStatus(httpStatus);
        response.setHeader(STATUS_CODE, code);
        response.setHeader(STATUS_MESSAGE, message);
        response.setHeader(PARAMS, params);
    }

    public void setStatusHeadersToSuccess(HttpServletResponse response, String algoliaParams) {
        int status = HttpStatus.OK.value();
        String params = algoliaParams;
        setStatusHeaders(response, status,  "SUCCESS", "Success", params);
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

        String params = null;
        setStatusHeaders(response, status, code, message, params);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
            HttpServletRequest request, HttpServletResponse response) {

        String code = "BAD_REQUEST";
        String message = ex.getMessage();
        int status = HttpStatus.BAD_REQUEST.value();

        logger.error(message, ex);

        String params = null;
        setStatusHeaders(response, status, code, message, params);

    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentTypeMismatchException(MethodArgumentNotValidException ex,
            HttpServletRequest request, HttpServletResponse response) {

        String code = "BAD_REQUEST";
        String message = ex.getMessage();
        int status = HttpStatus.BAD_REQUEST.value();

        logger.error(message, ex);

        String params = null;
        setStatusHeaders(response, status, code, message, params);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request,
            HttpServletResponse response) {

        String code = "BAD_REQUEST";
        String message = ex.getMessage();
        int status = HttpStatus.BAD_REQUEST.value();

        logger.error(message, ex);

        String params = null;
        setStatusHeaders(response, status, code, message, params);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
            HttpServletRequest request, HttpServletResponse response) {

        String code = "BAD_REQUEST";
        String message = ex.getMessage();
        int status = HttpStatus.BAD_REQUEST.value();

        logger.error(message, ex);

        String params = null;
        setStatusHeaders(response, status, code, message, params);

    }

    @ExceptionHandler(HTTPException.class)
    public void handleHTTPException(HTTPException ex, HttpServletRequest request, HttpServletResponse response) {

        String code = ex.getStatusCode();
        String message = ex.getStatusMessage();
        int status = ex.getHttpStatus().value();

        String params = null;
        setStatusHeaders(response, status, code, message, params);

        logger.error(message, ex);

    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        String code = HttpStatus.INTERNAL_SERVER_ERROR.name();
        String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        String params = null;
        setStatusHeaders(response, status, code, message, params);

        logger.error(message, ex);

    }

}
