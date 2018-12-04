package com.placepass.product.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.placepass.utils.vendorproduct.Vendor;

/**
 * Helps routing to vendor connector based on vendor name. This generates full REST URL to a vendor connector for a
 * particular vendor-connector-api call.
 * 
 * @author wathsala.w
 *
 */
@Deprecated
@Component
public class SimpleVendorConnectorRouter {

    /** The urbanadventures base url. */
    @Value("${urbanadventures.baseurl}")
    private String urbanadventuresBaseUrl;

    /** The viator base url. */
    @Value("${viator.baseurl}")
    private String viatorBaseUrl;

    @Value("${bemyguest.baseurl}")
    private String beMyGuestBaseUrl;

    @Value("${citydiscovery.baseurl}")
    private String cityDiscoveryBaseUrl;

    @Value("${vendorcon.productdetails.url}")
    private String productDetailsUrl;

    @Value("${vendorcon.availability.url}")
    private String availabilityUrl;

    @Value("${vendorcon.bookingoptions.url}")
    private String bookingoptionsUrl;

    @Value("${vendorcon.reviews.url}")
    private String productReviewsUrl;

    @Value("${gobe.baseurl}")
    private String gobeBaseUrl;

    @Value("${marriott.baseurl}")
    private String marriottBaseUrl;

    @Autowired
    private RestConfig restServiceConfig;

    public String getProductDetailsUrl(Vendor vendor) {

        return getConnectorBase(vendor) + productDetailsUrl;
    }

    public String getAvailabilityUrl(Vendor vendor) {
        return getConnectorBase(vendor) + availabilityUrl;
    }

    public String getBookingoptionsUrl(Vendor vendor) {
        return getConnectorBase(vendor) + bookingoptionsUrl;
    }

    public String getProductReviewsUrl(Vendor vendor) {

        return getConnectorBase(vendor) + productReviewsUrl;
    }

    private String getConnectorBase(Vendor vendor) {
        switch (vendor) {
            case URBANA: {
                return urbanadventuresBaseUrl;
            }
            case VIATOR: {
                return viatorBaseUrl;
            }
            case BEMYGT: {
                return beMyGuestBaseUrl;
            }
            case CTYDSY: {
                return cityDiscoveryBaseUrl;
            }
            case GOBEEE: {
                return gobeBaseUrl;
            }
            case MARRTT: {
                return marriottBaseUrl;
            }
            default: {
                // FIXME: log error message and use correct exception from utils
                throw new RuntimeException("Routing to connetor not supported for vendor : " + vendor.name());
            }
        }
    }

}
