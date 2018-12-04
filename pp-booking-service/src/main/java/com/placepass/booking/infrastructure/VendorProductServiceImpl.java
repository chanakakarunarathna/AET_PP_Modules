package com.placepass.booking.infrastructure;

import java.time.LocalDate;
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

import com.placepass.connector.common.cart.GetProductOptionsRS;
import com.placepass.connector.common.cart.GetProductPriceRQ;
import com.placepass.connector.common.cart.GetProductPriceRS;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.vendorproduct.Vendor;

/**
 * @author chanaka.k
 *
 */
@Component
public class VendorProductServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(VendorProductServiceImpl.class);

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private SimpleVendorConnectorRouter vendorRouter;

    public GetProductPriceRS getProductPrice(GetProductPriceRQ productPriceCRQ, Vendor vendor) {

        GetProductPriceRS productPriceCRS = null;
        try {

            HttpEntity<GetProductPriceRQ> requestEntity = new HttpEntity<GetProductPriceRQ>(productPriceCRQ);
            ResponseEntity<GetProductPriceRS> response = restTemplate.postForEntity(
                    vendorRouter.getGetBookingOptionPriceUrl(vendor), requestEntity, GetProductPriceRS.class);

            productPriceCRS = response.getBody();

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
        return productPriceCRS;
    }

    public GetProductOptionsRS getProductOption(String productId, LocalDate bookingDate, Vendor vendor) {

        GetProductOptionsRS productOptionGroupRS = null;
        Map<String, Object> urlVariables = null;

        try {

            urlVariables = new HashMap<String, Object>();
            urlVariables.put("productid", productId);
            urlVariables.put("date", bookingDate);

            productOptionGroupRS = restTemplate.getForObject(vendorRouter.getGetProductOptionUrl(vendor),
                    GetProductOptionsRS.class, urlVariables);

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

        return productOptionGroupRS;

    }

}
