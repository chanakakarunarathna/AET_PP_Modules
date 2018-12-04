package com.placepass.booking.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.placepass.booking.domain.product.ProductDetails;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;

@Component
public class ProductServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Value("${product.service.baseurl}")
    private String productServiceBaseUrl;

    @Value("${product.service.getproductdetails.url}")
    private String getProductDetailsUrl;

    public ProductDetails getProductDetails(String partnerId, String productId) {

        ProductDetails productDetails = null;
        try {

            Map<String, Object> uriVariables = new HashMap<String, Object>();
            uriVariables.put("productid", productId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("partner-id", partnerId);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

            ResponseEntity<ProductDetails> productDetailsRespEntity = restTemplate.exchange(
                    this.getGetProductDetailsUrl(), HttpMethod.GET, requestEntity, ProductDetails.class, uriVariables);
            productDetails = productDetailsRespEntity.getBody();

        } catch (HttpServerErrorException hse) {
            logger.error("HttpServerErrorException occurred while connecting to the product service", hse);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());

        } catch (HttpClientErrorException hce) {
            logger.error("HttpClientErrorException occurred while connecting to the product service", hce);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_REQUEST.toString(),
                    PlacePassExceptionCodes.BAD_REQUEST.getDescription());

        } catch (ResourceAccessException rae) {
            logger.error("ResourceAccessException occurred while connecting to the product service", rae);
            throw new NotFoundException(PlacePassExceptionCodes.BAD_GATEWAY.toString(),
                    PlacePassExceptionCodes.BAD_GATEWAY.getDescription());

        } catch (Exception e) {
            logger.error("Exception occurred while connecting to the product service", e);
            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription());
        }

        return productDetails;

    }

    private String getGetProductDetailsUrl() {
        return productServiceBaseUrl + getProductDetailsUrl;
    }

}
