package com.placepass.connector.citydiscovery.infrastructure;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.placepass.connector.citydiscovery.application.util.VendorErrorCode;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsActivityBookingRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookStatusInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookStatusRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookStatusRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsCancelBookingInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsCancelBookingRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsCancelBookingRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsPriceInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsPriceRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsPriceRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRQ.POS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRQ.POS.Source;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsResultType;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsReviewRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsReviewsInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsReviewsRQ;

@Component
public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    private static final String OTA_ERRORS = "OTA_ErrorRS";

    @Autowired
    @Qualifier("RestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private RestServiceConfig restServiceConfig;

    /*
     * public ClsProductDetailsInfoRS getProductDetails(ClsProductDetailsRQ request) { request.setPOS(accessObject());
     * String xmlRequestString = marshal(request); LinkedMultiValueMap<String, String> bodyMap = new
     * LinkedMultiValueMap<String, String>(); bodyMap.add("data", xmlRequestString); ResponseEntity<String> model =
     * restTemplate.postForEntity(restServiceConfig.getDiscoveryBaseUrl(), bodyMap, String.class); String responseXml =
     * model.getBody(); ClsProductDetailsInfoRS productDetailResponse = unmarshal(ClsProductDetailsInfoRS.class,
     * responseXml); return productDetailResponse; }
     */

    public ClsProductDetailsRS getProductDetails(ClsProductDetailsRQ request) {

        ClsProductDetailsRS clsProductDetailsRS = new ClsProductDetailsRS();
        ModelMapper mapper = new ModelMapper();
        LinkedMultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();

        request.setPOS(mapper.map(accessObject(), ClsProductDetailsRQ.POS.class));
        String requestXml = marshal(request);

        logger.info("POST CityDiscovery Product Details Request : {}", requestXml);
        bodyMap.add("data", requestXml);

        LinkedMultiValueMap<String, String> headers = getHttpHeaders();
        HttpEntity<?> requestBodyAndHeader = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> model = restTemplate.postForEntity(restServiceConfig.getDiscoveryBaseUrl(),
                requestBodyAndHeader, String.class);

        String responseXml = model.getBody();
        logger.info("POST CityDiscovery Product Details Response : {}", responseXml);

        if (!responseXml.contains(OTA_ERRORS)) {

            ClsProductDetailsInfoRS productDetailsResponse = unmarshal(ClsProductDetailsInfoRS.class, responseXml);
            ClsResultType resultType = new ClsResultType();
            resultType.setErrorCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setErrorMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

            clsProductDetailsRS.setResultType(resultType);
            clsProductDetailsRS.setProductDetailsInfoRS(productDetailsResponse);

        } else {

            ClsResultType errorResponse = unmarshal(ClsResultType.class, responseXml);
            clsProductDetailsRS.setResultType(errorResponse);
        }
        return clsProductDetailsRS;

    }

    public ClsReviewRS getProductReviews(ClsReviewsRQ request) {

        ClsReviewRS clsReviewRS = new ClsReviewRS();
        ModelMapper mapper = new ModelMapper();
        LinkedMultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();

        request.setPOS(mapper.map(accessObject(), ClsReviewsRQ.POS.class));
        String requestXml = marshal(request);

        logger.info("POST CityDiscovery Review Request : {}", requestXml);
        bodyMap.add("data", requestXml);

        LinkedMultiValueMap<String, String> headers = getHttpHeaders();
        HttpEntity<?> requestBodyAndHeader = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> model = restTemplate.postForEntity(restServiceConfig.getDiscoveryBaseUrl(),
                requestBodyAndHeader, String.class);

        String responseXml = model.getBody();
        logger.info("POST CityDiscovery Review Response : {}", responseXml);

        if (!responseXml.contains(OTA_ERRORS)) {

            ClsReviewsInfoRS productReviewsResponse = unmarshal(ClsReviewsInfoRS.class, responseXml);
            ClsResultType resultType = new ClsResultType();
            resultType.setErrorCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setErrorMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

            clsReviewRS.setResultType(resultType);
            clsReviewRS.setReviewsInfoRS(productReviewsResponse);

        } else {

            ClsResultType errorResponse = unmarshal(ClsResultType.class, responseXml);
            clsReviewRS.setResultType(errorResponse);
        }

        return clsReviewRS;
    }

    public ClsPriceRS getPriceDetails(ClsPriceRQ request) {

        ClsPriceRS clsPriceRS = new ClsPriceRS();
        ModelMapper mapper = new ModelMapper();
        LinkedMultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();

        request.setPOS(mapper.map(accessObject(), ClsPriceRQ.POS.class));
        String requestXml = marshal(request);

        logger.info("POST CityDiscovery Price Request : {}", requestXml);
        bodyMap.add("data", requestXml);

        LinkedMultiValueMap<String, String> headers = getHttpHeaders();
        HttpEntity<?> requestBodyAndHeader = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> model = restTemplate.postForEntity(restServiceConfig.getDiscoveryBaseUrl(),
                requestBodyAndHeader, String.class);

        String responseXml = model.getBody();
        logger.info("POST CityDiscovery Price Response : {}", responseXml);

        if (!responseXml.contains(OTA_ERRORS)) {

            ClsPriceInfoRS clsPriceInfoRS = unmarshal(ClsPriceInfoRS.class, responseXml);
            ClsResultType resultType = new ClsResultType();
            resultType.setErrorCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setErrorMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

            clsPriceRS.setPriceInfoRS(clsPriceInfoRS);
            clsPriceRS.setResultType(resultType);

        } else {

            ClsResultType resultType = unmarshal(ClsResultType.class, responseXml);
            clsPriceRS.setResultType(resultType);
        }

        return clsPriceRS;
    }

    public ClsBookRS makeBooking(ClsActivityBookingRQ request) {

        ClsBookRS clsBookRS = new ClsBookRS();
        ModelMapper mapper = new ModelMapper();
        LinkedMultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();

        request.setPOS(mapper.map(accessObject(), ClsActivityBookingRQ.POS.class));
        String requestXml = marshal(request);

        logger.info("POST CityDiscovery Make a Booking Request : {}", requestXml);
        bodyMap.add("data", requestXml);

        LinkedMultiValueMap<String, String> headers = getHttpHeaders();
        HttpEntity<?> requestBodyAndHeader = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> model = restTemplate.postForEntity(restServiceConfig.getDiscoveryBaseUrl(),
                requestBodyAndHeader, String.class);

        String responseXml = model.getBody();
        logger.info("POST CityDiscovery Make a Booking Response : {}", responseXml);

        if (!responseXml.contains(OTA_ERRORS)) {

            ClsBookInfoRS clsBookInfoRS = unmarshal(ClsBookInfoRS.class, responseXml);
            ClsResultType resultType = new ClsResultType();
            resultType.setErrorCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setErrorMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

            clsBookRS.setBookInfoRS(clsBookInfoRS);
            clsBookRS.setResultType(resultType);

        } else {

            ClsResultType resultType = unmarshal(ClsResultType.class, responseXml);
            clsBookRS.setResultType(resultType);
        }

        return clsBookRS;
    }

    public ClsBookStatusRS getBookingStatus(ClsBookStatusRQ request) {

        ClsBookStatusRS clsBookStatusRS = new ClsBookStatusRS();
        ModelMapper mapper = new ModelMapper();
        LinkedMultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();

        request.setPOS(mapper.map(accessObject(), ClsBookStatusRQ.POS.class));
        String requestXml = marshal(request);

        logger.info("POST CityDiscovery Booking Status Request : {}", requestXml);
        bodyMap.add("data", requestXml);

        LinkedMultiValueMap<String, String> headers = getHttpHeaders();
        HttpEntity<?> requestBodyAndHeader = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> model = restTemplate.postForEntity(restServiceConfig.getDiscoveryBaseUrl(),
                requestBodyAndHeader, String.class);

        String responseXml = model.getBody();
        logger.info("POST CityDiscovery Booking Status Response : {}", responseXml);

        if (!responseXml.contains(OTA_ERRORS)) {

            ClsBookStatusInfoRS clsBookStatusInfoRS = unmarshal(ClsBookStatusInfoRS.class, responseXml);
            ClsResultType resultType = new ClsResultType();
            resultType.setErrorCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setErrorMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

            clsBookStatusRS.setBookStatusInfoRS(clsBookStatusInfoRS);
            clsBookStatusRS.setResultType(resultType);

        } else {

            ClsResultType resultType = unmarshal(ClsResultType.class, responseXml);
            clsBookStatusRS.setResultType(resultType);
        }

        return clsBookStatusRS;
    }

    public ClsCancelBookingRS cancelBooking(ClsCancelBookingRQ request) {

        ClsCancelBookingRS clsCancelBookingRS = new ClsCancelBookingRS();
        ModelMapper mapper = new ModelMapper();
        LinkedMultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();

        request.setPOS(mapper.map(accessObject(), ClsBookStatusRQ.POS.class));
        String requestXml = marshal(request);

        logger.info("POST CityDiscovery Cancel Booking Details Request : {}", requestXml);
        bodyMap.add("data", requestXml);

        LinkedMultiValueMap<String, String> headers = getHttpHeaders();
        HttpEntity<?> requestBodyAndHeader = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> model = restTemplate.postForEntity(restServiceConfig.getDiscoveryBaseUrl(),
                requestBodyAndHeader, String.class);

        String responseXml = model.getBody();
        logger.info("POST CityDiscovery Cancel Booking Response : {}", responseXml);

        if (!responseXml.contains(OTA_ERRORS)) {

            ClsCancelBookingInfoRS clsCancelBookingInfoRS = unmarshal(ClsCancelBookingInfoRS.class, responseXml);
            ClsResultType resultType = new ClsResultType();
            resultType.setErrorCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setErrorMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

            clsCancelBookingRS.setCancelBookingInfoRS(clsCancelBookingInfoRS);
            clsCancelBookingRS.setResultType(resultType);

        } else {

            ClsResultType resultType = unmarshal(ClsResultType.class, responseXml);
            clsCancelBookingRS.setResultType(resultType);
        }

        return clsCancelBookingRS;
    }

    private POS accessObject() {
        POS pos = new POS();
        Source source = new Source();
        source.setAgentDutyCode(restServiceConfig.getDiscoveryAgentDutyCode());
        source.setAgentSine(restServiceConfig.getDiscoveryAgentSine());
        pos.setSource(source);
        return pos;
    }

    public static <T> String marshal(T object) {
        try {
            StringWriter stringWriter = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            logger.error(String.format("Exception while marshalling: %s", e.getMessage()));
        }
        return null;
    }

    public static <T> T unmarshal(Class clazz, String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller um = context.createUnmarshaller();
            return (T) um.unmarshal(new StringReader(xml));
        } catch (JAXBException je) {
            throw new RuntimeException("Error interpreting XML response", je);
        }
    }

    public LinkedMultiValueMap<String, String> getHttpHeaders() {

        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.add("Ocp-Apim-Subscription-Key", restServiceConfig.getAzureHeaderValue());
        return headers;
    }

}