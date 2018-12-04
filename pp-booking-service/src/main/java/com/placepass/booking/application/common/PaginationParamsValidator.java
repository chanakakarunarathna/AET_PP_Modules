package com.placepass.booking.application.common;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;

/**
 * @author chanaka.k
 *
 */
public class PaginationParamsValidator {
    
    private PaginationParamsValidator() {
    }

    public static void validateParams(int hitsPerPage,int pageNumber) {
     
        if(hitsPerPage <= 0) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_HITS_PER_PAGE.toString(),
                    PlacePassExceptionCodes.INVALID_HITS_PER_PAGE.getDescription());
        }
        
        if(pageNumber < 0) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PAGE_NUMBER.toString(),
                    PlacePassExceptionCodes.INVALID_PAGE_NUMBER.getDescription());
        }
        
    }
    
}
