package com.placepass.booking.application.cart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.booking.application.cart.dto.UpdateTravelerDetailsRQ;
import com.placepass.booking.application.common.BookingServiceUtil;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utills.email.ValidateEmail;
import com.placepass.utils.ageband.PlacePassAgeBandType;

/**
 * @author chanaka.k
 *
 */
public class CartValidator {

    private static final Logger logger = LoggerFactory.getLogger(CartValidator.class);

    private static final String PHONE_NUMBER_FORMAT = "[ ]*[+]?[0-9 ]+";

    private static final String COUNTRY_CODE_FORMAT = "[A-Z a-z]+";

    private static final String DATE_FORMAT = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    private static final String EMAIL_FORMAT = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private CartValidator() {
    }

    public static boolean isValidEmailAddress(String email) {

        return ValidateEmail.isValidEmail(email);

    }

    public static boolean isValidPhoneNumber(String phoneNumber) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(PHONE_NUMBER_FORMAT);
        matcher = pattern.matcher(phoneNumber);
        return matcher.matches();

    }

    public static boolean isValidCountryCode(String countryCode) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(COUNTRY_CODE_FORMAT);
        matcher = pattern.matcher(countryCode);
        return matcher.matches();

    }

    public static boolean isValidDateFormat(String dateOfBirth) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(DATE_FORMAT);
        matcher = pattern.matcher(dateOfBirth);
        return matcher.matches();

    }

    /* To validate Traveler Email */
    public static void validateTravelerEmail(TravelerDTO traveler) {

        if (traveler.isLeadTraveler()) {

            /* To check lead traveler email is required or not */
            if (traveler.getEmail() == null) {

                logger.error("Error occurred due to, email address comming as NULL for lead traveler.");
                throw new BadRequestException(
                        PlacePassExceptionCodes.EMAIL_ADDRESS_REQUIRED_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.EMAIL_ADDRESS_REQUIRED_FOR_LEAD_TRAVELER.getDescription());
            }

            /* To check lead traveler email is empty or not */
            if (StringUtils.isEmpty(traveler.getEmail())) {

                logger.error("Error occurred due to, email address comming as empty for lead traveler.");
                throw new BadRequestException(
                        PlacePassExceptionCodes.EMAIL_ADDRESS_CAN_NOT_BE_EMPTY_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.EMAIL_ADDRESS_CAN_NOT_BE_EMPTY_FOR_LEAD_TRAVELER.getDescription());
            }

            if (!isValidEmailAddress(traveler.getEmail())) {

                logger.error("Error occurred due to, invalid email address. ");
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_EMAIL_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.INVALID_EMAIL_FOR_LEAD_TRAVELER.getDescription());

            }

        } else {

            if (StringUtils.hasText(traveler.getEmail()) && !isValidEmailAddress(traveler.getEmail())) {

                logger.error("Error occurred due to, invalid email address. ");
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_EMAIL_FOR_TRAVELER.toString(),
                        PlacePassExceptionCodes.INVALID_EMAIL_FOR_TRAVELER.getDescription());

            }

        }

    }

    /* To validate Age Band ID */
    public static void validateAgeBandId(Integer ageBandId) {

        if (!contains(ageBandId)) {

            logger.error("Error occurred due to, invalid age band id");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_AGE_BAND_ID.toString(),
                    PlacePassExceptionCodes.INVALID_AGE_BAND_ID.getDescription());

        }

    }

    private static boolean contains(Integer ageBandId) {

        for (PlacePassAgeBandType tt : PlacePassAgeBandType.values()) {
            if (tt.getAgeBandId() == ageBandId) {
                return true;
            }
        }
        return false;
    }

    /* Date Of Birth Validation */
    public static void validateDateOfBirth(String dateOfBirth) {

        // Checking date format.(Should be yyyy-mm-dd)
        if (!isValidDateFormat(dateOfBirth)) {
            logger.error("Error occurred due to, invalid date format.Date format should be yyyy-mm-dd");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_DATE_FORMAT.toString(),
                    PlacePassExceptionCodes.INVALID_DATE_FORMAT.getDescription());
        }

        Calendar calDOB = Calendar.getInstance();
        Calendar calToday = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            calDOB.setTime(sdf.parse(dateOfBirth));

        } catch (ParseException e) {
            logger.error("Error occurred due to, invalid date format ");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_DATE_FORMAT.toString(),
                    PlacePassExceptionCodes.INVALID_DATE_FORMAT.getDescription());
        }

        Date dob = calDOB.getTime();
        Date today = calToday.getTime();

        if (!dob.before(today)) {

            logger.error("Error occurred due to, invalid date date of birth ");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_DATE_OF_BIRTH.toString(),
                    PlacePassExceptionCodes.INVALID_DATE_OF_BIRTH.getDescription());
        }

    }

    /* Checking lead traveler is ADULT or SENIOR */
    public static void checkLeadTravelerAdultOrSenior(int ageBandId) {

        if (PlacePassAgeBandType.ADULT.getAgeBandId() != ageBandId
                && PlacePassAgeBandType.SENIOR.getAgeBandId() != ageBandId) {

            logger.error("Error occurred due to, CHILD or INFANT assigned as lead traveler ");
            throw new BadRequestException(PlacePassExceptionCodes.LEAD_TRAVELER_SHOULD_BE_ADULT_OR_SENIOR.toString(),
                    PlacePassExceptionCodes.LEAD_TRAVELER_SHOULD_BE_ADULT_OR_SENIOR.getDescription());
        }

    }

    /* Validating traveler ISO country code */
    public static void validateTravelerISOCountryCode(TravelerDTO travelerDTO) {

        // For Lead traveler
        if (travelerDTO.isLeadTraveler()) {

            if ("".equals(travelerDTO.getCountryISOCode())) {
                logger.error("Error occurred due to,lead traveler country ISO code coming as empty");
                throw new BadRequestException(
                        PlacePassExceptionCodes.EMPTY_COUNTRY_ISO_CODE_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.EMPTY_COUNTRY_ISO_CODE_FOR_LEAD_TRAVELER.getDescription());
            }

            if (travelerDTO.getCountryISOCode() == null) {
                logger.error("Error occurred due to,lead traveler country ISO code coming as null");
                throw new BadRequestException(
                        PlacePassExceptionCodes.COUNTRY_ISO_CODE_IS_REQUIRED_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.COUNTRY_ISO_CODE_IS_REQUIRED_FOR_LEAD_TRAVELER.getDescription());
            }

            if (!isValidCountryCode(travelerDTO.getCountryISOCode())) {
                logger.error("Error occurred due to,lead traveler invalid country ISO code");
                throw new BadRequestException(
                        PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE_FOR_LEAD_TRAVELER.getDescription());
            }

            List<String> ISOCountryCodes = Arrays.asList(java.util.Locale.getISOCountries());
            if (!ISOCountryCodes.contains(travelerDTO.getCountryISOCode())) {

                logger.error("Error occurred due to,lead traveler invalid ISO country code. ");
                throw new BadRequestException(
                        PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE_FOR_LEAD_TRAVELER.getDescription());

            }

        } else {

            // For normal traveler
            if (!"".equals(travelerDTO.getCountryISOCode()) && travelerDTO.getCountryISOCode() != null) {

                if (!isValidCountryCode(travelerDTO.getCountryISOCode())) {
                    logger.error("Error occurred due to,traveler invalid country ISO code");
                    throw new BadRequestException(
                            PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE_FOR_TRAVELER.toString(),
                            PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE_FOR_TRAVELER.getDescription());
                }

                List<String> ISOCountryCodes = Arrays.asList(java.util.Locale.getISOCountries());
                if (!ISOCountryCodes.contains(travelerDTO.getCountryISOCode())) {

                    logger.error("Error occurred due to,traveler invalid ISO country code. ");
                    throw new BadRequestException(
                            PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE_FOR_TRAVELER.toString(),
                            PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE_FOR_TRAVELER.getDescription());

                }
            }

        }

    }

    /* Validating traveler phone number */
    public static void validateTravelerPhoneNumber(TravelerDTO travelerDTO) {

        // For Lead traveler
        if (travelerDTO.isLeadTraveler()) {

            if ("".equals(travelerDTO.getPhoneNumber())) {
                logger.error("Error occurred due to, lead traveler phone number coming as empty");
                throw new BadRequestException(PlacePassExceptionCodes.EMPTY_PHONE_NUMBER_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.EMPTY_PHONE_NUMBER_FOR_LEAD_TRAVELER.getDescription());
            }

            if (travelerDTO.getPhoneNumber() == null) {
                logger.error("Error occurred due to, lead traveler phone number coming as null");
                throw new BadRequestException(
                        PlacePassExceptionCodes.PHONE_NUMBER_IS_REQUIRED_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.PHONE_NUMBER_IS_REQUIRED_FOR_LEAD_TRAVELER.getDescription());
            }

            if (!isValidPhoneNumber(travelerDTO.getPhoneNumber())) {
                logger.error("Error occurred due to, lead traveler invalid phone number");
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_PHONE_NUMBER_FOR_LEAD_TRAVELER.toString(),
                        PlacePassExceptionCodes.INVALID_PHONE_NUMBER_FOR_LEAD_TRAVELER.getDescription());
            }

            String countryCallingCode = BookingServiceUtil.getCountryCallingCode(travelerDTO.getCountryISOCode(),
                    travelerDTO.getPhoneNumber());

            if (countryCallingCode == null) {

                logger.error("Error occurred due to, lead traveler phone number invalid for given country ISO code ");
                throw new BadRequestException(
                        PlacePassExceptionCodes.LEAD_TRAVELER_PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE.toString(),
                        PlacePassExceptionCodes.LEAD_TRAVELER_PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE
                                .getDescription());
            }

        } else {

            // For normal traveler
            if (!"".equals(travelerDTO.getPhoneNumber()) && travelerDTO.getPhoneNumber() != null) {

                if (!isValidPhoneNumber(travelerDTO.getPhoneNumber())) {
                    logger.error("Error occurred due to, traveler invalid phone number");
                    throw new BadRequestException(PlacePassExceptionCodes.INVALID_PHONE_NUMBER_FOR_TRAVELER.toString(),
                            PlacePassExceptionCodes.INVALID_PHONE_NUMBER_FOR_TRAVELER.getDescription());
                }

                if (travelerDTO.getCountryISOCode() == null) {

                    logger.error("Error occurred due to, traveler ISO code is null");
                    throw new BadRequestException(
                            PlacePassExceptionCodes.COUNTRY_ISO_CODE_IS_REQUIRED_FOR_TRAVELER.toString(),
                            PlacePassExceptionCodes.COUNTRY_ISO_CODE_IS_REQUIRED_FOR_TRAVELER.getDescription());

                } else if ("".equals(travelerDTO.getCountryISOCode())) {

                    logger.error("Error occurred due to, traveler ISO code is empty");
                    throw new BadRequestException(
                            PlacePassExceptionCodes.EMPTY_COUNTRY_ISO_CODE_FOR_TRAVELER.toString(),
                            PlacePassExceptionCodes.EMPTY_COUNTRY_ISO_CODE_FOR_TRAVELER.getDescription());

                } else {

                    String countryCallingCode = BookingServiceUtil
                            .getCountryCallingCode(travelerDTO.getCountryISOCode(), travelerDTO.getPhoneNumber());

                    if (countryCallingCode == null) {

                        logger.error(
                                "Error occurred due to, traveler phone number invalid for given country ISO code ");
                        throw new BadRequestException(
                                PlacePassExceptionCodes.TRAVELER_PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE.toString(),
                                PlacePassExceptionCodes.TRAVELER_PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE
                                        .getDescription());
                    }
                }
            }
        }
    }

    /* Checking Traveler specific age band count with cart quantity specific age band count */
    public static void validatingTravelerAgeBandsWithCartAgeBands(List<Quantity> quantities,
            List<TravelerDTO> travelers) {

        int totalTravelerAdultCount = 0;
        int totalTravelerSeniorCount = 0;
        int totalTravelerChildCount = 0;
        int totalTravelerInfantCount = 0;
        int totalTravelerYouthCount = 0;

        totalTravelerAdultCount = (int) travelers.stream()
                .filter(tr -> tr.getAgeBandId().equals(PlacePassAgeBandType.ADULT.getAgeBandId())).count();
        totalTravelerSeniorCount = (int) travelers.stream()
                .filter(tr -> tr.getAgeBandId().equals(PlacePassAgeBandType.SENIOR.getAgeBandId())).count();
        totalTravelerChildCount = (int) travelers.stream()
                .filter(tr -> tr.getAgeBandId().equals(PlacePassAgeBandType.CHILD.getAgeBandId())).count();
        totalTravelerInfantCount = (int) travelers.stream()
                .filter(tr -> tr.getAgeBandId().equals(PlacePassAgeBandType.INFANT.getAgeBandId())).count();
        totalTravelerYouthCount = (int) travelers.stream()
                .filter(tr -> tr.getAgeBandId().equals(PlacePassAgeBandType.YOUTH.getAgeBandId())).count();

        for (Quantity quantitiy : quantities) {

            if ((quantitiy.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId())
                    && (quantitiy.getQuantity() != totalTravelerAdultCount)) {

                logger.error(
                        "Error occurred due to, traveler count with specific agebands not match with the quantity in create cart");
                throw new BadRequestException(
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .toString(),
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .getDescription());
            }

            if ((quantitiy.getAgeBandId() == PlacePassAgeBandType.SENIOR.getAgeBandId())
                    && (quantitiy.getQuantity() != totalTravelerSeniorCount)) {
                logger.error(
                        "Error occurred due to, traveler count with specific agebands not match with the quantity in create cart");
                throw new BadRequestException(
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .toString(),
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .getDescription());
            }

            if ((quantitiy.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId())
                    && (quantitiy.getQuantity() != totalTravelerChildCount)) {
                logger.error(
                        "Error occurred due to, traveler count with specific agebands not match with the quantity in create cart");
                throw new BadRequestException(
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .toString(),
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .getDescription());
            }

            if ((quantitiy.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId())
                    && (quantitiy.getQuantity() != totalTravelerInfantCount)) {
                logger.error(
                        "Error occurred due to, traveler count with specific agebands not match with the quantity in create cart");
                throw new BadRequestException(
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .toString(),
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .getDescription());
            }

            if ((quantitiy.getAgeBandId() == PlacePassAgeBandType.YOUTH.getAgeBandId())
                    && (quantitiy.getQuantity() != totalTravelerYouthCount)) {
                logger.error(
                        "Error occurred due to, traveler count with specific agebands not match with the quantity in create cart");
                throw new BadRequestException(
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .toString(),
                        PlacePassExceptionCodes.TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT
                                .getDescription());
            }

        }

    }

    // Validate traveler head count
    public static void validateTravelerHeadCount(List<Quantity> quantities,
            UpdateTravelerDetailsRQ updateTravelerDetailsRQ) {

        int headCount = 0;

        for (Quantity quantity : quantities) {

            if (quantity.getQuantity() > 0) {
                headCount = (headCount + quantity.getQuantity());
            }
        }

        if (headCount != updateTravelerDetailsRQ.getTravelers().size()) {

            logger.error("Error due to, number of traveler details do not match with traveler count ");
            throw new BadRequestException(
                    PlacePassExceptionCodes.TRAVELER_DETAILS_DO_NOT_MATCH_TRAVELER_COUNT.toString(),
                    PlacePassExceptionCodes.TRAVELER_DETAILS_DO_NOT_MATCH_TRAVELER_COUNT.getDescription());

        }

    }

    // Set up lead traveler
    public static void setUpLeadTraveler(List<TravelerDTO> travelers) {

        int leadTravelerCount = getLeadTravelerHeadCount(travelers);

        // Starting lead traveler setup
        if (travelers.size() == 1) {

            travelers.get(0).setLeadTraveler(true);

        } else {

            if (leadTravelerCount == 0) {

                logger.error("Error occurred due to, Lead traveler not mention ");
                throw new NotFoundException(PlacePassExceptionCodes.LEAD_TRAVELER_NOT_FOUND.toString(),
                        PlacePassExceptionCodes.LEAD_TRAVELER_NOT_FOUND.getDescription());
            }

            if (leadTravelerCount > 1) {

                logger.error("Error occurred due to, multiple lead travelers are define ");
                throw new BadRequestException(PlacePassExceptionCodes.MULTIPLE_LEAD_TRAVELERS_FOUND.toString(),
                        PlacePassExceptionCodes.MULTIPLE_LEAD_TRAVELERS_FOUND.getDescription());
            }

        }

    }

    // Getting isLeadTraveler=TRUE traveler counts
    public static int getLeadTravelerHeadCount(List<TravelerDTO> travelers) {

        return (int) travelers.stream().filter(tr -> tr.isLeadTraveler()).count();

    }

}
