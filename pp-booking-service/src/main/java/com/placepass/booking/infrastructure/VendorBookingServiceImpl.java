package com.placepass.booking.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.cart.GetBookingQuestionsRS;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.connector.common.booking.BookingStatusRQ;
import com.placepass.connector.common.booking.BookingStatusRS;

/**
 * @author chanaka.k
 *
 */
@Component
public class VendorBookingServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(VendorBookingServiceImpl.class);

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private SimpleVendorConnectorRouter vendorRouter;

    public MakeBookingRS makeBooking(MakeBookingRQ bookingRQ, Vendor vendor) {

        MakeBookingRS bookingRS = null;
        try {

            HttpEntity<MakeBookingRQ> requestEntity = new HttpEntity<>(bookingRQ);
            ResponseEntity<MakeBookingRS> response = restTemplate.postForEntity(vendorRouter.getMakeBookingUrl(vendor),
                    requestEntity, MakeBookingRS.class);

            bookingRS = response.getBody();

        } catch (HttpServerErrorException hse) {

            logger.error("HttpServerErrorException occurred while connecting to the vendor connector", hse);

            ResultType resultType = new ResultType();

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<String, String>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message",
                    VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg() + ", ex-message: " + hse.getMessage());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingRS = new MakeBookingRS();
            bookingRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {

            logger.error("HttpClientErrorException occurred while connecting to the vendor connector", hce);

            ResultType resultType = new ResultType();

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<String, String>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message",
                    VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg() + ", ex-message: " + hce.getMessage());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingRS = new MakeBookingRS();
            bookingRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {

            logger.error("ResourceAccessException occurred while connecting to the vendor connector", rae);

            ResultType resultType = new ResultType();

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<String, String>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()));
            extendedAttributes.put("message",
                    VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg() + ", ex-message: " + rae.getMessage());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingRS = new MakeBookingRS();
            bookingRS.setResultType(resultType);

        } catch (Exception e) {

            logger.error("Exception occurred while connecting to the vendor connector", e);

            ResultType resultType = new ResultType();

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<String, String>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
            extendedAttributes.put("message",
                    VendorErrorCode.VENDOR_API_ERROR.getMsg() + ", ex-message: " + e.getMessage());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingRS = new MakeBookingRS();
            bookingRS.setResultType(resultType);

        }

        return bookingRS;
    }

    public GetBookingQuestionsRS getBookingQuestions(String productId, Vendor vendor) {

        GetBookingQuestionsRS bookingQuestionsCRS = null;
        Map<String, Object> urlVariables = null;

        try {

            urlVariables = new HashMap<String, Object>();
            urlVariables.put("productid", productId);

            bookingQuestionsCRS = restTemplate.getForObject(vendorRouter.getGetBookingQuestionUrl(vendor),
                    GetBookingQuestionsRS.class, urlVariables);

        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the vendor connector", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
            logger.error("HttpClientErrorException occurred while connecting to the vendor connector", hce);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_REQUEST.toString(),
                    PlacePassExceptionCodes.BAD_REQUEST.getDescription());

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the vendor connector", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the vendor connector", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }

        return bookingQuestionsCRS;
    }

    public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRQ, Vendor vendor) {

        CancelBookingRS cancelBookingRS = null;
        try {

            HttpEntity<CancelBookingRQ> requestEntity = new HttpEntity<>(cancelBookingRQ);
            ResponseEntity<CancelBookingRS> response = restTemplate
                    .postForEntity(vendorRouter.getCancelBookingUrl(vendor), requestEntity, CancelBookingRS.class);

            cancelBookingRS = response.getBody();

        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the vendor connector", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
            logger.error("HttpClientErrorException occurred while connecting to the vendor connector", hce);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_REQUEST.toString(),
                    PlacePassExceptionCodes.BAD_REQUEST.getDescription());

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the vendor connector", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the vendor connector", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }

        return cancelBookingRS;
    }

    public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingVoucherCRQ, Vendor vendor) {

        BookingVoucherRS bookingVoucherCRS = null;
        try {

            HttpEntity<BookingVoucherRQ> requestEntity = new HttpEntity<>(bookingVoucherCRQ);
            ResponseEntity<BookingVoucherRS> response = restTemplate.postForEntity(vendorRouter.getVoucherUrl(vendor),
                    requestEntity, BookingVoucherRS.class);

            bookingVoucherCRS = response.getBody();

        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the vendor connector", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
            logger.error("HttpClientErrorException occurred while connecting to the vendor connector", hce);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_REQUEST.toString(),
                    PlacePassExceptionCodes.BAD_REQUEST.getDescription());

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the vendor connector", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the vendor connector", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }

        return bookingVoucherCRS;
    }

	
    public BookingStatusRS getBookingStatus(BookingStatusRQ bookingStatusRQ, Vendor vendor) {
    		BookingStatusRS bookingStatusRS = null;
	        try {
	            HttpEntity<BookingStatusRQ> requestEntity = new HttpEntity<>(bookingStatusRQ);
	            ResponseEntity<BookingStatusRS> response = restTemplate.postForEntity(vendorRouter.getBookingStatusUrl(vendor),
	                    requestEntity, BookingStatusRS.class);
	            bookingStatusRS = response.getBody();
	        } catch (HttpServerErrorException hse) {      	
	            logger.error("HttpServerErrorException occurred while connecting to the vendor connector", hse);
	            ResultType resultType = new ResultType();
	            resultType.setCode(VendorErrorCode.FAILED.getId());
	            resultType.setMessage(VendorErrorCode.FAILED.getMsg());
	            Map<String, String> extendedAttributes = new HashMap<String,String>();
	            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
	            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg() + ", ex-message: "
	                    + hse.getMessage());
	            resultType.setExtendedAttributes(extendedAttributes);
	            bookingStatusRS = new BookingStatusRS();
	            bookingStatusRS.setResultType(resultType);
	        } catch (HttpClientErrorException hce) {
	            logger.error("HttpClientErrorException occurred while connecting to the vendor connector", hce);
        		ResultType resultType = new ResultType();
        		resultType.setCode(VendorErrorCode.FAILED.getId());
        		resultType.setMessage(VendorErrorCode.FAILED.getMsg());
        		Map<String, String> extendedAttributes = new HashMap<String,String>();
        		extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
        		extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg() + ", ex-message: "
                      + hce.getMessage());
        		resultType.setExtendedAttributes(extendedAttributes);
        		bookingStatusRS = new BookingStatusRS();
        		bookingStatusRS.setResultType(resultType);
	        } catch (ResourceAccessException rae) {	
	            logger.error("ResourceAccessException occurred while connecting to the vendor connector", rae);
	            ResultType resultType = new ResultType();
	            resultType.setCode(VendorErrorCode.FAILED.getId());
    			resultType.setMessage(VendorErrorCode.FAILED.getMsg());
    			Map<String, String> extendedAttributes = new HashMap<String,String>();
    			extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()));
    			extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg() + ", ex-message: "
                  + rae.getMessage());
    			resultType.setExtendedAttributes(extendedAttributes);
    			bookingStatusRS = new BookingStatusRS();
    			bookingStatusRS.setResultType(resultType);
	        } catch (Exception e) {
	            logger.error("Exception occurred while connecting to the vendor connector", e);
	            ResultType resultType = new ResultType();
	            resultType.setCode(VendorErrorCode.FAILED.getId());
    			resultType.setMessage(VendorErrorCode.FAILED.getMsg());
    			Map<String, String> extendedAttributes = new HashMap<String,String>();
    			extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
    			extendedAttributes.put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg() + ", ex-message: "
                  + e.getMessage());
    			resultType.setExtendedAttributes(extendedAttributes);
    			bookingStatusRS = new BookingStatusRS();
    			bookingStatusRS.setResultType(resultType);
	        }
	        return bookingStatusRS;
	    }
}
