package com.placepass.booking.infrastructure;

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
@Component
public class SimpleVendorConnectorRouter {

    /** The urbanadventures base url. */
    @Value("${urbanadventures.baseurl}")
    private String urbanadventuresBaseUrl;

    /** The viator base url. */
    @Value("${viator.baseurl}")
    private String viatorBaseUrl;

    /** The bemyguest base url. */
    @Value("${bemyguest.baseurl}")
    private String bemyguestBaseUrl;

	/** The citydiscovery base url. */
    @Value("${citydiscovery.baseurl}")
    private String cityDiscoveryBaseUrl;

    @Value("${vendorcon.makebooking.url}")
    private String makeBookingUrl;

    @Value("${vendorcon.getproductoption.url}")
    private String getProductOptionUrl;

    @Value("${vendorcon.getbookingoptionprice.url}")
    private String getBookingOptionPriceUrl;

    @Value("${vendorcon.getbookingquestion.url}")
    private String getBookingQuestionUrl;
    
    @Value("${vendorcon.cancelbooking.url}")
    private String cancelBookingUrl;
    
    @Value("${vendorcon.voucher.url}")
    private String getVoucherUrl;

    @Value("${gobe.baseurl}")
    private String gobeBaseUrl;

    @Value("${marriott.baseurl}")
    private String marriottBaseUrl;

    @Value("${vendorcon.getbookingstatus.url}")
    private String getBookingStatusUrl;
    
    public String getMakeBookingUrl(Vendor vendor) {
        return getConnectorBase(vendor) + makeBookingUrl;
    }

    public String getGetProductOptionUrl(Vendor vendor) {
        return getConnectorBase(vendor) + getProductOptionUrl;
    }

    public String getGetBookingOptionPriceUrl(Vendor vendor) {
        return getConnectorBase(vendor) + getBookingOptionPriceUrl;
    }

    public String getGetBookingQuestionUrl(Vendor vendor) {
        return getConnectorBase(vendor) + getBookingQuestionUrl;
    }
    
    public String getCancelBookingUrl(Vendor vendor) {
        return getConnectorBase(vendor) + cancelBookingUrl;
    }
    
    public String getVoucherUrl(Vendor vendor) {
        return getConnectorBase(vendor) + getVoucherUrl;
    }

    public String getBookingStatusUrl(Vendor vendor) {
        return getConnectorBase(vendor) + getBookingStatusUrl;
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
                return bemyguestBaseUrl;
            }
            case GOBEEE: {
                return gobeBaseUrl;
            }
            case CTYDSY: {
                return cityDiscoveryBaseUrl;
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
