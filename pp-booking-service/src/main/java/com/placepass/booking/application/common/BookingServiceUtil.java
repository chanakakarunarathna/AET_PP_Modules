package com.placepass.booking.application.common;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.vendorproduct.Vendor;

public class BookingServiceUtil {

    public static String getCountryCallingCode(String countryISOCode, String phoneNumber) {

        boolean isValidForRegion = false;
        String countryCallingCode = null;
        try {

            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            PhoneNumber phoneNumberObj = phoneUtil.parse(phoneNumber, countryISOCode);

            isValidForRegion = phoneUtil.isValidNumberForRegion(phoneNumberObj, countryISOCode);
            if (isValidForRegion) {
                countryCallingCode = String.valueOf(phoneNumberObj.getCountryCode());
            }
        } catch (NumberParseException e) {
            // TODO handle exception
        }

        return countryCallingCode;
    }

    public static String getFormattedPhoneNumber(String countryISOCode, String phoneNumber) {

        boolean isValidForRegion = false;
        String formattedPhoneNum = null;
        try {

            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            PhoneNumber phoneNumberObj = phoneUtil.parse(phoneNumber, countryISOCode);
            isValidForRegion = phoneUtil.isValidNumberForRegion(phoneNumberObj, countryISOCode);
            if (isValidForRegion) {
                formattedPhoneNum = phoneNumber.trim().replaceAll(" +", " ");
            }
        } catch (NumberParseException e) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PHONE_NUMBER.toString(),
                    PlacePassExceptionCodes.INVALID_PHONE_NUMBER.getDescription());
        }

        return formattedPhoneNum;
    }

    public static boolean isValidPhoneNumber(String countryISOCode, String phoneNumber) {

        boolean isValidForRegion = false;
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            PhoneNumber phoneNumberObj = phoneUtil.parse(phoneNumber, countryISOCode);

            isValidForRegion = phoneUtil.isValidNumberForRegion(phoneNumberObj, countryISOCode);

        } catch (NumberParseException e) {
        }

        return isValidForRegion;
    }
    
    public static Vendor validateVendor(String vendorName){
    	
    	Vendor vendor = null;
    	try{
    		vendor = Vendor.valueOf(vendorName);
    	}catch (Exception e) {
    		throw new BadRequestException(PlacePassExceptionCodes.INVALID_VENDOR.toString(),
                   PlacePassExceptionCodes.INVALID_VENDOR.getDescription());	
    	}
    	return vendor;
    	
    }
}
