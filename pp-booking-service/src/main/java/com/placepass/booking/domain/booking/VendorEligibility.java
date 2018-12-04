package com.placepass.booking.domain.booking;

import org.springframework.data.annotation.Id;

/**
 * Vendor configuration specification. Could indicate if certain flow steps are supported, such as cart functionality
 * and sequence of calls supported.
 * 
 * <p>
 * 
 * 
 * @author wathsala.w
 *
 */
public class VendorEligibility {

    @Id
    private String id;
    
    // 
    private boolean cartAvailable;

    private boolean bookQuestionsAvailable;

}
