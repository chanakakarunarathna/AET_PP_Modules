package com.placepass.booking.application.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.placepass.booking.domain.config.PartnerConfig;
import com.placepass.booking.domain.config.PartnerConfigService;

@Component
public class VoucherPropertyService {
    
    private static final String PLACEHOLDER_BOOKING_REFERENCE = "{bookingreference}";

    private static final String PLACEHOLDER_BOOKER_EMAIL = "{bookeremail}";

    private static final String PLACEHOLDER_BOOKING_ID = "{bookingid}";

    private static final String PLACEHOLDER_VOUCHER_ID = "{voucherid}";

    @Value("${booking.service.baseurl}")
    private String bookingServiceBaseUrl;

    @Value("${booking.getvoucher.url}")
    private String bookingGetVoucherUrlSuffix;

    @Autowired
    private PartnerConfigService partnerConfigService;

    /**
     * Populate internal voucher url.
     *
     * @param voucherId the voucher id
     * @return the string
     */
    @Deprecated
    public String populateInternalVoucherUrl(String voucherId) {

        String suffix = bookingGetVoucherUrlSuffix.replace(PLACEHOLDER_VOUCHER_ID, voucherId);
        return bookingServiceBaseUrl + suffix;
    }

    /**
     * Populate voucher url.
     *
     * @param partnerId the partner id
     * @param voucherId the voucher id
     * @param bookingId the booking id
     * @param bookerEmail the booker email
     * @param bookingReference the booking reference
     * @return the string
     */
    public String populateVoucherUrl(String partnerId, String voucherId, String bookingId, String bookerEmail,
            String bookingReference) {

        PartnerConfig partnerConfig = partnerConfigService.getPartnerConfig(partnerId);
        return partnerConfig.getVoucherUrl().replace(PLACEHOLDER_VOUCHER_ID, voucherId)
                .replace(PLACEHOLDER_BOOKING_ID, bookingId).replace(PLACEHOLDER_BOOKER_EMAIL, bookerEmail)
                .replace(PLACEHOLDER_BOOKING_REFERENCE, bookingReference);

    }

}
