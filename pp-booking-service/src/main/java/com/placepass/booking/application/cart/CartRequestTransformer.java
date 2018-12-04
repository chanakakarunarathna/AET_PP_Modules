package com.placepass.booking.application.cart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.placepass.connector.common.cart.GetProductPriceRS;
import com.placepass.connector.common.cart.ProductOption;
import com.placepass.booking.application.booking.dto.FeeDTO;
import com.placepass.booking.application.cart.dto.AddBookingOptionRS;
import com.placepass.booking.application.cart.dto.BookerDTO;
import com.placepass.booking.application.cart.dto.BookingAnswerDTO;
import com.placepass.booking.application.cart.dto.BookingOptionDTO;
import com.placepass.booking.application.cart.dto.BookingQuestionDTO;
import com.placepass.booking.application.cart.dto.CreateCartRS;
import com.placepass.booking.application.cart.dto.DeleteBookingOptionRS;
import com.placepass.booking.application.cart.dto.LoyaltyDetailDTO;
import com.placepass.booking.application.cart.dto.PickupLocationDTO;
import com.placepass.booking.application.cart.dto.PriceBreakDownDTO;
import com.placepass.booking.application.cart.dto.PricePerAgeBandDTO;
import com.placepass.booking.application.cart.dto.QuantityDTO;
import com.placepass.booking.application.cart.dto.TotalDTO;
import com.placepass.booking.application.cart.dto.TotalPricePerAgeBandDTO;
import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.booking.application.cart.dto.UpdateBookerDetailsRQ;
import com.placepass.booking.application.cart.dto.UpdateBookerDetailsRS;
import com.placepass.booking.application.cart.dto.UpdateBookingAnswerRS;
import com.placepass.booking.application.cart.dto.UpdateHotelPickupRS;
import com.placepass.booking.application.cart.dto.UpdateLanguageOptionRS;
import com.placepass.booking.application.cart.dto.UpdateTravelerDetailsRQ;
import com.placepass.booking.application.cart.dto.UpdateTravelerDetailsRS;
import com.placepass.booking.application.cart.dto.ViewCartRS;
import com.placepass.booking.application.common.BookingServiceUtil;
import com.placepass.booking.domain.booking.Booker;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.BookingQuestion;
import com.placepass.booking.domain.booking.Cart;
import com.placepass.booking.domain.booking.Fee;
import com.placepass.booking.domain.booking.LoyaltyDetail;
import com.placepass.booking.domain.booking.PickupLocation;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.booking.domain.booking.Total;
import com.placepass.booking.domain.booking.Traveler;
import com.placepass.booking.domain.config.LoyaltyProgramConfig;
import com.placepass.booking.domain.product.Price;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utills.loyalty.details.LoyaltyDetailsUtill;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;
import com.placepass.utils.pricematch.PriceMatch;
import com.placepass.utils.pricematch.PriceMatchPriceBreakDown;
import com.placepass.utils.pricematch.PriceMatchPricePerAgeBand;
import com.placepass.utils.pricematch.PriceMatchQuantity;
import com.placepass.utils.pricematch.PriceMatchTotalPricePerAgeBand;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.utils.vendorproduct.VendorProduct;
import com.placepass.utils.vendorproduct.VendorProductOption;

/**
 * @author chanaka.k
 *
 */
public class CartRequestTransformer {

    private static final Logger logger = LoggerFactory.getLogger(CartRequestTransformer.class);

    public static Cart toCart(String partnerId, List<BookingOption> bookingOptions) {

        Map<String, Object> logData = new HashMap<String, Object>();

        Cart cart = new Cart();

        if (bookingOptions != null) {
            cart.setBookingOptions(bookingOptions);
        }

        logData.put("PARTNER_ID : ", partnerId);
        logData.put("CART_ID : ", UUID.randomUUID().toString());
        logger.info("Adding partnerId and cart id to cart object before save  {} ", logData);

        cart.setPartnerId(partnerId);
        cart.setCartId(UUID.randomUUID().toString());

        return cart;
    }

    public static BookingOption toAddBookingOption(BookingOptionDTO cartBookingOption, ProductOption conProductOption,
            ProductHashGenerator productHashGenerator) {

        BookingOption updatedBookingOption = new BookingOption();

        VendorProduct vendorProduct = VendorProduct.getInstance(cartBookingOption.getProductId(), productHashGenerator);
        VendorProductOption vendorProductOption = VendorProductOption
                .getInstance(cartBookingOption.getProductOptionId(), productHashGenerator);

        updatedBookingOption.setVendorProductId(vendorProduct.getVendorProductID());
        updatedBookingOption.setVendor(vendorProduct.getVendor().name());
        updatedBookingOption.setBookingOptionId(UUID.randomUUID().toString());
        updatedBookingOption.setProductOptionId(cartBookingOption.getProductOptionId());
        updatedBookingOption.setProductId(cartBookingOption.getProductId());
        updatedBookingOption.setBookingDate(LocalDate.parse(cartBookingOption.getBookingDate()));
        updatedBookingOption.setVendorProductOptionId(vendorProductOption.getVendorProductOptionID());
        updatedBookingOption.setQuantities(toQuantityList(cartBookingOption.getQuantities()));

        if (null != cartBookingOption.getPickupLocation()) {
            updatedBookingOption.setPickupLocation(toPickupLocation(cartBookingOption.getPickupLocation()));
        }

        // For caching purpose, set following values are updating from connector side values
        updatedBookingOption.setDescription(conProductOption.getDescription());
        updatedBookingOption.setTitle(conProductOption.getName());
        updatedBookingOption.setStartTime(conProductOption.getStartTime());
        updatedBookingOption.setEndTime(conProductOption.getEndTime());
        updatedBookingOption.setPrices(toPriceList(conProductOption.getPrices()));
        updatedBookingOption.setIsHotelPickUpEligible(conProductOption.getIsHotelPickUpEligible());

        Total total = new Total();
        if (cartBookingOption.getTotal() != null) {
            total.setCurrency(cartBookingOption.getTotal().getCurrency());
            total.setCurrencyCode(cartBookingOption.getTotal().getCurrencyCode());
            total.setFinalTotal(cartBookingOption.getTotal().getFinalTotal());
            total.setRoundedFinalTotal(cartBookingOption.getTotal().getRoundedFinalTotal());
            total.setRetailTotal(cartBookingOption.getTotal().getRetailTotal());

            updatedBookingOption.setTotal(total);
        }

        return updatedBookingOption;
    }

    public static Cart toDeleteBookingOptions(Cart cart, String bookingOptionid) {

        List<BookingOption> newOptionList = new ArrayList<BookingOption>();
        List<BookingOption> optionList = cart.getBookingOptions();

        // optionList.removeIf(bo -> bo.getBookingOptionId().equals(bookingOptionid));
        for (BookingOption bookingOption : optionList) {

            if (!bookingOption.getProductOptionId().equals(bookingOptionid)) {
                newOptionList.add(bookingOption);
            }

        }

        cart.setBookingOptions(newOptionList);

        return cart;
    }

    public static ViewCartRS toViewCartResponse(Cart cart) {

        ViewCartRS viewCartdetails = null;

        viewCartdetails = new ViewCartRS();

        if (cart.getBookerDetails() != null) {

            BookerDTO bookerDTO = toBooker(cart);
            viewCartdetails.setBookerDetails(bookerDTO);
        }

        if ((cart.getBookingOptions() != null) && (!cart.getBookingOptions().isEmpty())) {

            List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                BookingOptionDTO bookingOptionDTO = toBookingOptionDTO(bookingOption);

                bookingOptionDTOs.add(bookingOptionDTO);
            }

            viewCartdetails.setBookingOptions(bookingOptionDTOs);
        }

        if (cart.getTotal() != null) {

            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            viewCartdetails.setTotal(totalDTO);

        }
        
        if(!cart.getFees().isEmpty() || cart.getFees() != null ) {
            
            List<FeeDTO> fees = toFees(cart.getFees());
            viewCartdetails.setFees(fees);
        }

        return viewCartdetails;
    }

    private static TotalDTO toCartTotal(Total total) {

        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setCurrency(total.getCurrency());
        totalDTO.setCurrencyCode(total.getCurrencyCode());
        totalDTO.setFinalTotal(total.getFinalTotal());
        totalDTO.setRoundedFinalTotal(AmountFormatter.floatToFloatRoundingFinalTotal(total.getFinalTotal()));
        totalDTO.setRetailTotal(total.getRetailTotal());

        return totalDTO;
    }

    public static Cart toUpdateBookerdetails(Cart cart, UpdateBookerDetailsRQ updateBookerDetailsRQ) {

        BookerDTO bookerRequest = updateBookerDetailsRQ.getBookerRequest();

        Booker booker = null;

        booker = new Booker();

        List<String> ISOCountryCodes = Arrays.asList(java.util.Locale.getISOCountries());

        if (!ISOCountryCodes.contains(bookerRequest.getCountryISOCode())) {

            logger.error("Error occurred due to, invalid ISO country code. ");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE.toString(),
                    PlacePassExceptionCodes.INVALID_ISO_COUNTRY_CODE.getDescription());

        }

        booker.setCountryISOCode(bookerRequest.getCountryISOCode());

        booker.setEmail(bookerRequest.getEmail());
        booker.setFirstName(bookerRequest.getFirstName());
        booker.setLastName(bookerRequest.getLastName());

        String countryCallingCode = BookingServiceUtil.getCountryCallingCode(bookerRequest.getCountryISOCode(),
                bookerRequest.getPhoneNumber());

        if (countryCallingCode == null) {

            logger.error("Error occurred due to, phone number invalid for given country ISO code ");
            throw new BadRequestException(PlacePassExceptionCodes.PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE.toString(),
                    PlacePassExceptionCodes.PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE.getDescription());
        }

        booker.setPhoneNumber(BookingServiceUtil.getFormattedPhoneNumber(bookerRequest.getCountryISOCode(),
                bookerRequest.getPhoneNumber()));
        booker.setDateOfBirth(bookerRequest.getDateOfBirth());
        booker.setTitle(bookerRequest.getTitle());

        cart.setBookerDetails(booker);

        return cart;
    }

    /*
     * Replacing what ever traveler details which already in DB ,from request traveler details
     */
    public static Cart toUpdateTravelerDetails(Cart cart, UpdateTravelerDetailsRQ updateTravelerDetailsRQ) {

        List<Traveler> newTravelers = new ArrayList<Traveler>();

        List<BookingOption> dbBookingOptionList = cart.getBookingOptions();
        String productOptionId = updateTravelerDetailsRQ.getProductOptionId();

        for (TravelerDTO travelerDTO : updateTravelerDetailsRQ.getTravelers()) {

            Traveler newTraveler = new Traveler();

            newTraveler.setDateOfBirth(travelerDTO.getDateOfBirth());
            newTraveler.setEmail(travelerDTO.getEmail());
            newTraveler.setFirstName(travelerDTO.getFirstName());
            newTraveler.setLastName(travelerDTO.getLastName());
            if (travelerDTO.isLeadTraveler() || (!StringUtils.isEmpty(travelerDTO.getCountryISOCode())
                    && !StringUtils.isEmpty(travelerDTO.getPhoneNumber()))) {
                newTraveler.setCountryISOCode(travelerDTO.getCountryISOCode());
                newTraveler.setPhoneNumber(BookingServiceUtil.getFormattedPhoneNumber(travelerDTO.getCountryISOCode(),
                        travelerDTO.getPhoneNumber()));
            }
            newTraveler.setTitle(travelerDTO.getTitle());
            newTraveler.setLeadTraveler(travelerDTO.isLeadTraveler());
            newTraveler.setAgeBandId(travelerDTO.getAgeBandId());

            newTravelers.add(newTraveler);
        }

        for (BookingOption bookingOption : dbBookingOptionList) {

            if (!bookingOption.getProductOptionId().equals(productOptionId)) {

                logger.error("Error occurred due to, given product option id not match.");
                throw new BadRequestException(PlacePassExceptionCodes.PRODUCT_OPTION_ID_NOT_MATCH.toString(),
                        PlacePassExceptionCodes.PRODUCT_OPTION_ID_NOT_MATCH.getDescription());
            }

            bookingOption.setTraverlerDetails(newTravelers);

        }

        return cart;
    }

    public static CreateCartRS toCreateCartResponse(Cart cart) {

        CreateCartRS createCartRS = null;

        createCartRS = new CreateCartRS();
        createCartRS.setCartId(cart.getCartId());
        
        if (cart.getTotal() != null) {
            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            createCartRS.setTotal(totalDTO);
        }

        if (!cart.getFees().isEmpty()) {
            for (Fee fee : cart.getFees()) {
                FeeDTO feeDTO = toFeeDTO(fee);
                createCartRS.getFees().add(feeDTO);
            }
        }

        return createCartRS;
    }

    public static DeleteBookingOptionRS toDeleteBookingOptionResponse(Cart cart) {

        DeleteBookingOptionRS deleteBookingOptionDetails = null;

        deleteBookingOptionDetails = new DeleteBookingOptionRS();

        if (cart.getBookerDetails() != null) {

            BookerDTO bookerDTO = toBooker(cart);
            deleteBookingOptionDetails.setBookerDetails(bookerDTO);
        }

        if ((cart.getBookingOptions() != null) && (!cart.getBookingOptions().isEmpty())) {

            List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                BookingOptionDTO bookingOptionDTO = toBookingOptionDTO(bookingOption);

                bookingOptionDTOs.add(bookingOptionDTO);
            }

            deleteBookingOptionDetails.setBookingOptions(bookingOptionDTOs);
        }

        if (cart.getTotal() != null) {

            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            deleteBookingOptionDetails.setTotal(totalDTO);

        }

        return deleteBookingOptionDetails;

    }

    public static AddBookingOptionRS toAddBookingOptionResponse(Cart cart) {

        AddBookingOptionRS addBookingOptionDetails = null;

        addBookingOptionDetails = new AddBookingOptionRS();

        if (cart.getBookerDetails() != null) {

            BookerDTO bookerDTO = toBooker(cart);
            addBookingOptionDetails.setBookerDetails(bookerDTO);
        }

        if ((cart.getBookingOptions() != null) && (!cart.getBookingOptions().isEmpty())) {

            List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                BookingOptionDTO bookingOptionDTO = toBookingOptionDTO(bookingOption);

                bookingOptionDTOs.add(bookingOptionDTO);
            }

            addBookingOptionDetails.setBookingOptions(bookingOptionDTOs);
        }

        if (cart.getTotal() != null) {

            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            addBookingOptionDetails.setTotal(totalDTO);

        }

        return addBookingOptionDetails;

    }

    public static UpdateBookerDetailsRS toUpdateBookerDetailsResponse(Cart cart) {
        UpdateBookerDetailsRS updateBookerDetails = null;

        updateBookerDetails = new UpdateBookerDetailsRS();

        if (cart.getBookerDetails() != null) {

            BookerDTO bookerDTO = toBooker(cart);
            updateBookerDetails.setBookerDetails(bookerDTO);
        }

        if ((cart.getBookingOptions() != null) && (!cart.getBookingOptions().isEmpty())) {

            List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                BookingOptionDTO bookingOptionDTO = toBookingOptionDTO(bookingOption);

                bookingOptionDTOs.add(bookingOptionDTO);
            }

            updateBookerDetails.setBookingOptions(bookingOptionDTOs);
        }

        if (cart.getTotal() != null) {

            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            updateBookerDetails.setTotal(totalDTO);

        }

        return updateBookerDetails;
    }

    public static UpdateTravelerDetailsRS toTravelerDetailsResponse(Cart cart) {

        UpdateTravelerDetailsRS updateTravelerDetails = null;

        updateTravelerDetails = new UpdateTravelerDetailsRS();

        if (cart.getBookerDetails() != null) {

            BookerDTO bookerDTO = toBooker(cart);
            updateTravelerDetails.setBookerDetails(bookerDTO);
        }

        if ((cart.getBookingOptions() != null) && (!cart.getBookingOptions().isEmpty())) {

            List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                BookingOptionDTO bookingOptionDTO = toBookingOptionDTO(bookingOption);

                bookingOptionDTOs.add(bookingOptionDTO);
            }

            updateTravelerDetails.setBookingOptions(bookingOptionDTOs);
        }

        if (cart.getTotal() != null) {

            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            updateTravelerDetails.setTotal(totalDTO);

        }

        return updateTravelerDetails;
    }

    public static List<String> validateBookerDetails(Booker bookerDetails) {

        List<String> messages = new ArrayList<String>();

        if (bookerDetails.getCountryISOCode() == null || !StringUtils.hasText(bookerDetails.getCountryISOCode()))
            messages.add("Booker country ISO code is required");
        if (bookerDetails.getEmail() == null || !StringUtils.hasText(bookerDetails.getEmail()))
            messages.add("Booker email is required");
        if (bookerDetails.getFirstName() == null || !StringUtils.hasText(bookerDetails.getFirstName()))
            messages.add("Booker first name is required");
        if (bookerDetails.getLastName() == null || !StringUtils.hasText(bookerDetails.getLastName()))
            messages.add("Booker last name is required");
        if (bookerDetails.getPhoneNumber() == null || !StringUtils.hasText(bookerDetails.getPhoneNumber()))
            messages.add("Booker phone number is required");
        if (bookerDetails.getTitle() == null || !StringUtils.hasText(bookerDetails.getTitle())) {
            messages.add("Booker title is required");
        }

        List<String> ISOCountryCodes = Arrays.asList(java.util.Locale.getISOCountries());

        if (!ISOCountryCodes.contains(bookerDetails.getCountryISOCode())) {
            messages.add("Booker, country ISO code is not valid");
        } else {

            String countryCallingCode = BookingServiceUtil.getCountryCallingCode(bookerDetails.getCountryISOCode(),
                    bookerDetails.getPhoneNumber());
            if (countryCallingCode == null) {
                messages.add("Booker, phone number is not valid for the given country");
            }

        }

        return messages;
    }

    public static List<String> validateBookingOptionGeneralDetails(ProductOption connectorProductOption,
            BookingOption requestedBookingOption, List<String> validateMessages) {

        if (requestedBookingOption.getProductId() == null) {
            validateMessages.add("Product id is required");
        }

        if (requestedBookingOption.getBookingOptionId() == null) {
            validateMessages.add("Booking Option id is required");
        }

        if (requestedBookingOption.getBookingDate() == null) {
            validateMessages.add("Booking date is required");
        }

        if ((requestedBookingOption.getQuantities() == null) || (requestedBookingOption.getQuantities().isEmpty())) {
            validateMessages.add("Quntities are required ");
        }

        validateMessages = validateQuantityBookingOption(connectorProductOption, requestedBookingOption, validateMessages);

        /* To validate Traveler details */
        if ((requestedBookingOption.getTraverlerDetails() == null)
                || (requestedBookingOption.getTraverlerDetails().isEmpty())) {
            validateMessages.add("Need one minimum Traverler");
        } else {
            validateMessages = validateTravelerDetails(validateMessages, requestedBookingOption);
        }

        return validateMessages;
    }

    public static List<String> validateTravelerDetails(List<String> messages, BookingOption bookingOption) {
        List<Traveler> travelersDetails = bookingOption.getTraverlerDetails();

        for (Traveler traveler : travelersDetails) {

            // Only we need to check Lead traveler
            if (traveler.isLeadTraveler()) {
                if (traveler.getCountryISOCode() == null || !StringUtils.hasText(traveler.getCountryISOCode()))
                    messages.add("Traveler country code is required");
            }

            // Only we need to check Lead traveler
            if (traveler.isLeadTraveler()) {
                if (traveler.getEmail() == null || !StringUtils.hasText(traveler.getEmail()))
                    messages.add("Lead traveler email address is required");
            }

            if (traveler.getFirstName() == null || !StringUtils.hasText(traveler.getFirstName()))
                messages.add("Traveler first name is required");
            if (traveler.getLastName() == null || !StringUtils.hasText(traveler.getLastName()))
                messages.add("Traveler last name is required");

            // Only we need to check Lead traveler
            if (traveler.isLeadTraveler()) {
                if (traveler.getPhoneNumber() == null || !StringUtils.hasText(traveler.getPhoneNumber()))
                    messages.add("Traveler phone number is required");
            }

            if (traveler.getTitle() == null || !StringUtils.hasText(traveler.getTitle())) {
                messages.add("Traveler title is required");
            }

            // Only we need to check Lead traveler
            if (traveler.isLeadTraveler()) {

                List<String> ISOCountryCodes = Arrays.asList(java.util.Locale.getISOCountries());
                if (!ISOCountryCodes.contains(traveler.getCountryISOCode())) {
                    messages.add("Traveler, country ISO code is not valid");
                } else {
                    String countryCallingCode = BookingServiceUtil.getCountryCallingCode(traveler.getCountryISOCode(),
                            traveler.getPhoneNumber());
                    if (countryCallingCode == null) {
                        messages.add("Traveler, phone number is not valid for the given country");
                    }
                }
            }

        }

        return messages;
    }

    public static BookingOption toBookingOption(BookingOptionDTO bookingOptionDTO, VendorProduct vendorProduct,
            VendorProductOption vendorProductOption, ProductOption conProductOption) {

        BookingOption option = new BookingOption();

        option.setProductId(bookingOptionDTO.getProductId());
        option.setVendorProductId(vendorProduct.getVendorProductID());
        option.setVendor(vendorProduct.getVendor().toString());
        option.setBookingOptionId(UUID.randomUUID().toString());
        option.setProductOptionId(bookingOptionDTO.getProductOptionId());
        option.setVendorProductOptionId(vendorProductOption.getVendorProductOptionID());
        option.setQuantities(toQuantityList(bookingOptionDTO.getQuantities()));
        option.setBookingDate(LocalDate.parse(bookingOptionDTO.getBookingDate()));

        if (null != bookingOptionDTO.getPickupLocation()) {
            option.setPickupLocation(toPickupLocation(bookingOptionDTO.getPickupLocation()));
        }

        // For caching purpose, set following values are updating from connector side values
        option.setTitle(conProductOption.getName());
        option.setDescription(conProductOption.getDescription());
        option.setStartTime(conProductOption.getStartTime());
        option.setEndTime(conProductOption.getEndTime());
        option.setPrices(toPriceList(conProductOption.getPrices()));
        option.setIsHotelPickUpEligible(conProductOption.getIsHotelPickUpEligible());

        Total total = new Total();
        if (bookingOptionDTO.getTotal() != null) {
            total.setCurrency(bookingOptionDTO.getTotal().getCurrency());
            total.setCurrencyCode(bookingOptionDTO.getTotal().getCurrencyCode());
            total.setFinalTotal(bookingOptionDTO.getTotal().getFinalTotal());
            total.setRoundedFinalTotal(bookingOptionDTO.getTotal().getRoundedFinalTotal());
            total.setRetailTotal(bookingOptionDTO.getTotal().getRetailTotal());

            option.setTotal(total);
        }

        return option;
    }

    public static List<Quantity> toQuantityList(List<QuantityDTO> quantityDTOs) {

        List<Quantity> quantities = new ArrayList<Quantity>();

        for (QuantityDTO quantityDTO : quantityDTOs) {

            if (quantityDTO.getQuantity() > 0) {

                Quantity quantity = new Quantity();

                if (PlacePassAgeBandType.ADULT.name().equals(quantityDTO.getAgeBandLabel())) {
                    quantity.setAgeBandId(PlacePassAgeBandType.ADULT.getAgeBandId());
                } else if (PlacePassAgeBandType.SENIOR.name().equals(quantityDTO.getAgeBandLabel())) {
                    quantity.setAgeBandId(PlacePassAgeBandType.SENIOR.getAgeBandId());
                } else if (PlacePassAgeBandType.CHILD.name().equals(quantityDTO.getAgeBandLabel())) {
                    quantity.setAgeBandId(PlacePassAgeBandType.CHILD.getAgeBandId());
                } else if (PlacePassAgeBandType.INFANT.name().equals(quantityDTO.getAgeBandLabel())) {
                    quantity.setAgeBandId(PlacePassAgeBandType.INFANT.getAgeBandId());
                } else if (PlacePassAgeBandType.YOUTH.name().equals(quantityDTO.getAgeBandLabel())) {
                    quantity.setAgeBandId(PlacePassAgeBandType.YOUTH.getAgeBandId());
                }

                quantity.setAgeBandLabel(quantityDTO.getAgeBandLabel());
                quantity.setQuantity(quantityDTO.getQuantity());

                quantities.add(quantity);
            }

        }

        return quantities;
    }

    public static List<Traveler> toTravelerList(List<TravelerDTO> travelerDTOs) {
        List<Traveler> travelers = new ArrayList<Traveler>();
        for (TravelerDTO travelerDTO : travelerDTOs) {

            Traveler traveler = new Traveler();
            traveler.setCountryISOCode(travelerDTO.getCountryISOCode());
            traveler.setDateOfBirth(travelerDTO.getDateOfBirth());
            traveler.setEmail(travelerDTO.getEmail());
            traveler.setFirstName(travelerDTO.getFirstName());
            traveler.setLastName(travelerDTO.getLastName());
            traveler.setPhoneNumber(travelerDTO.getPhoneNumber());
            traveler.setTitle(travelerDTO.getTitle());

            travelers.add(traveler);
        }
        return travelers;
    }

    public static List<String> validateBookingOptionPriceTotalDetails(GetProductPriceRS getProductPriceCRS,
            BookingOption requestBookingoption, List<String> validateMessages) {

        com.placepass.connector.common.cart.Total connectorTotal = getProductPriceCRS.getTotal();
        Total cartTotal = requestBookingoption.getTotal();

        if ((connectorTotal != null) && (cartTotal != null)) {

            if (!connectorTotal.getCurrency().equals(cartTotal.getCurrency())) {
                validateMessages.add("Currency type is mismatch");
            }
            if (!connectorTotal.getCurrencyCode().equals(cartTotal.getCurrencyCode())) {
                validateMessages.add("Currency code is mismatch");
            }
            if (connectorTotal.getFinalTotal() != cartTotal.getFinalTotal()) {
                validateMessages.add("Final totals are mismatch");
            }
            if (connectorTotal.getMerchantTotal() != cartTotal.getMerchantTotal()) {
                validateMessages.add("Merchant totals are mismatch");
            }
            if (connectorTotal.getRetailTotal() != (cartTotal.getRetailTotal())) {
                validateMessages.add("Retail totals are mismatch");
            }
        }

        return validateMessages;
    }

    public static UpdateBookingAnswerRS toUpdateBookingAnswerResponse(Cart cart) {

        UpdateBookingAnswerRS updateBookingAnswerRS = null;

        updateBookingAnswerRS = new UpdateBookingAnswerRS();

        if (cart.getBookerDetails() != null) {

            BookerDTO bookerDTO = toBooker(cart);
            updateBookingAnswerRS.setBookerDetails(bookerDTO);
        }

        if ((cart.getBookingOptions() != null) && (!cart.getBookingOptions().isEmpty())) {

            List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                BookingOptionDTO bookingOptionDTO = toBookingOptionDTO(bookingOption);

                bookingOptionDTOs.add(bookingOptionDTO);
            }

            updateBookingAnswerRS.setBookingOptions(bookingOptionDTOs);
        }

        if (cart.getTotal() != null) {

            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            updateBookingAnswerRS.setTotal(totalDTO);

        }

        return updateBookingAnswerRS;
    }

    public static BookingOptionDTO toBookingOptionDTO(BookingOption bookingOption) {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();

        bookingOptionDTO.setBookingDate(bookingOption.getBookingDate().toString());
        bookingOptionDTO.setProductOptionId(bookingOption.getProductOptionId());
        bookingOptionDTO.setDescription(bookingOption.getDescription());
        bookingOptionDTO.setTitle(bookingOption.getTitle());
        bookingOptionDTO.setProductId(bookingOption.getProductId());
        bookingOptionDTO.setStartTime(bookingOption.getStartTime());
        bookingOptionDTO.setEndTime(bookingOption.getEndTime());

        if (null != bookingOption.getLanguageOptionCode()) {
            bookingOptionDTO.setLanguageOptionCode(bookingOption.getLanguageOptionCode());
        }

        if (null != bookingOption.getPickupLocation()) {
            bookingOptionDTO.setPickupLocation(toPickupLocationDTO(bookingOption.getPickupLocation()));
        }

        bookingOptionDTO.setIsHotelPickUpEligible(bookingOption.getIsHotelPickUpEligible());

        if (null != bookingOption.getLoyaltyDetails() || !bookingOption.getLoyaltyDetails().isEmpty()) {
            bookingOptionDTO.setLoyaltyDetails(toLoyaltyDetails(bookingOption.getLoyaltyDetails()));
        }

        List<QuantityDTO> quantityDTOs = new ArrayList<QuantityDTO>();
        for (Quantity quantity : bookingOption.getQuantities()) {

            QuantityDTO quantityDTO = toQuantity(quantity);
            quantityDTOs.add(quantityDTO);
        }
        bookingOptionDTO.setQuantities(quantityDTOs);

        if (bookingOption.getTotal() != null) {

            TotalDTO totalDTO = toBookingOptionTotal(bookingOption.getTotal());
            bookingOptionDTO.setTotal(totalDTO);
        }

        if (bookingOption.getTraverlerDetails() != null) {
            List<TravelerDTO> travelerDTOs = new ArrayList<TravelerDTO>();
            for (Traveler traveler : bookingOption.getTraverlerDetails()) {

                TravelerDTO travelerDTO = toTraveler(traveler);
                travelerDTOs.add(travelerDTO);
            }
            bookingOptionDTO.setTraverlerDetails(travelerDTOs);
        }

        if (bookingOption.getBookingQuestions() != null) {

            List<BookingQuestionDTO> bookingQuestionDTOs = new ArrayList<BookingQuestionDTO>();
            for (BookingQuestion bookingQuestion : bookingOption.getBookingQuestions()) {

                BookingQuestionDTO bookingQuestionDTO = toBookingQuestion(bookingQuestion,
                        bookingOption.getProductId());

                if (bookingQuestion.getBookingAnswer() != null) {

                    BookingAnswerDTO bookingAnswerDTO = new BookingAnswerDTO();
                    bookingAnswerDTO.setAnswer(bookingQuestion.getBookingAnswer().getAnswer());
                    bookingQuestionDTO.setBookingAnswer(bookingAnswerDTO);
                    bookingQuestionDTO.setProductId(bookingOption.getProductId());
                    bookingQuestionDTO.setProductOptionId(bookingOption.getProductOptionId());
                }

                bookingQuestionDTOs.add(bookingQuestionDTO);
            }
            bookingOptionDTO.setBookingQuestions(bookingQuestionDTOs);
        }

        // Showing best price
        List<PriceBreakDownDTO> priceBreakDowns = getMatchedPriceList(bookingOption.getPrices(),
                bookingOption.getQuantities());

        if (priceBreakDowns != null) {
            bookingOptionDTO.setPriceBreakDowns(priceBreakDowns);
        }
        return bookingOptionDTO;
    }

    public static BookingQuestionDTO getBookingQuestionDTO(
            com.placepass.connector.common.cart.BookingQuestion bookingQuestionCDTO, String productId,
            String productOptionId) {

        BookingQuestionDTO bookingQuestionDTO = new BookingQuestionDTO();

        bookingQuestionDTO.setQuestionId(bookingQuestionCDTO.getQuestionId());
        bookingQuestionDTO.setMessage(bookingQuestionCDTO.getMessage());
        bookingQuestionDTO.setRequired(bookingQuestionCDTO.getRequired());
        bookingQuestionDTO.setSubTitle(bookingQuestionCDTO.getSubTitle());
        bookingQuestionDTO.setTitle(bookingQuestionCDTO.getTitle());
        bookingQuestionDTO.setProductId(productId);
        bookingQuestionDTO.setProductOptionId(productOptionId);

        if (bookingQuestionCDTO.getBookingAnswer() != null) {

            BookingAnswerDTO bookingAnswerDTO = new BookingAnswerDTO();
            bookingAnswerDTO.setAnswer(bookingQuestionCDTO.getBookingAnswer().getAnswer());
            bookingQuestionDTO.setBookingAnswer(bookingAnswerDTO);
        }

        return bookingQuestionDTO;
    }

    public static BookingQuestion getBookingQuestion(
            com.placepass.connector.common.cart.BookingQuestion bookingQuestionCDTO, String productOptionId) {

        BookingQuestion bookingQuestion = new BookingQuestion();

        bookingQuestion.setProductOptionId(productOptionId);
        bookingQuestion.setQuestionId(bookingQuestionCDTO.getQuestionId());
        bookingQuestion.setMessage(bookingQuestionCDTO.getMessage());
        bookingQuestion.setRequired(bookingQuestionCDTO.getRequired());
        bookingQuestion.setSubTitle(bookingQuestionCDTO.getSubTitle());
        bookingQuestion.setTitle(bookingQuestionCDTO.getTitle());

        return bookingQuestion;
    }

    public static List<String> validateUAQuantityBookingOption(ProductOption connectorProductOption,
            BookingOption requestedBookingOption, List<String> validateMessages) {

        int headCount = 0;

        for (Quantity quantity : requestedBookingOption.getQuantities()) {

            if (quantity.getAgeBandLabel() == null)
                validateMessages.add("Age band label is required");

            if (PlacePassAgeBandType.ADULT.toString().equals(quantity.getAgeBandLabel())
                    || PlacePassAgeBandType.CHILD.toString().equals(quantity.getAgeBandLabel())
                    || PlacePassAgeBandType.SENIOR.toString().equals(quantity.getAgeBandLabel())
                    || PlacePassAgeBandType.INFANT.toString().equals(quantity.getAgeBandLabel())
                    || PlacePassAgeBandType.YOUTH.toString().equals(quantity.getAgeBandLabel())) {
                headCount = headCount + quantity.getQuantity();
            }
        }

        if (connectorProductOption.getAvailability() < headCount) {

            validateMessages.add("Total head count should be less than available products : "
                    + connectorProductOption.getAvailability());

        } else {

            // Best price match logic
            boolean isPriceQuantityValid = CartRequestTransformer.priceQuantityMatching(
                    connectorProductOption.getPrices(), toQuantityListDTO(requestedBookingOption.getQuantities()));

            if (!isPriceQuantityValid) {
                validateMessages.add("Price not found for the given quantities");
            }
        }

        return validateMessages;
    }

    public static List<String> validateQuantityBookingOption(ProductOption connectorProductOption,
            BookingOption requestedBookingOption, List<String> validateMessages) {

        if (connectorProductOption.getAvailability() != -1) {
            validateMessages.add("This product has no availabilities");
        }

        // Best price match logic
        boolean isPriceQuantityValid = CartRequestTransformer.priceQuantityMatching(connectorProductOption.getPrices(),
                toQuantityListDTO(requestedBookingOption.getQuantities()));

        if (!isPriceQuantityValid) {
            validateMessages.add("Price not found for the given quantities");
        }

        return validateMessages;
    }

    public static Total getTotal(com.placepass.connector.common.cart.Total conTotal) {

        Total total = new Total();

        total.setCurrency(conTotal.getCurrency());
        total.setCurrencyCode(conTotal.getCurrencyCode());
        total.setFinalTotal(conTotal.getFinalTotal());
        total.setRoundedFinalTotal(AmountFormatter.floatToFloatRoundingFinalTotal(conTotal.getFinalTotal()));
        total.setMerchantTotal(conTotal.getMerchantTotal());
        total.setRetailTotal(conTotal.getRetailTotal());

        return total;
    }

    public static List<Price> toPriceList(List<com.placepass.connector.common.cart.Price> prices) {

        List<Price> priceList = new ArrayList<>();

        for (com.placepass.connector.common.cart.Price priceCDTO : prices) {

            Price price = new Price();

            price.setPriceGroupSortOrder(priceCDTO.getPriceGroupSortOrder());
            price.setAgeBandId(priceCDTO.getAgeBandId());
            price.setCurrencyCode(priceCDTO.getCurrencyCode());
            price.setDescription(priceCDTO.getDescription());
            price.setFinalPrice(priceCDTO.getFinalPrice());
            price.setRoundedFinalPrice(AmountFormatter.floatToFloatRoundingFinalTotal(priceCDTO.getFinalPrice()));
            price.setMaxBuy(priceCDTO.getMaxBuy());
            price.setMinBuy(priceCDTO.getMinBuy());
            price.setMerchantPrice(priceCDTO.getMerchantPrice());
            price.setPriceType(priceCDTO.getPriceType());
            price.setRetailPrice(priceCDTO.getRetailPrice());
            price.setAgeFrom(priceCDTO.getAgeFrom());
            price.setAgeTo(priceCDTO.getAgeTo());
            price.setPricingUnit(priceCDTO.getPricingUnit());

            priceList.add(price);
        }

        return priceList;
    }

    private static BookerDTO toBooker(Cart cart) {

        BookerDTO bookerDTO = new BookerDTO();

        bookerDTO.setCountryISOCode(cart.getBookerDetails().getCountryISOCode());
        bookerDTO.setEmail(cart.getBookerDetails().getEmail());
        bookerDTO.setFirstName(cart.getBookerDetails().getFirstName());
        bookerDTO.setLastName(cart.getBookerDetails().getLastName());
        bookerDTO.setPhoneNumber(cart.getBookerDetails().getPhoneNumber());
        bookerDTO.setDateOfBirth(cart.getBookerDetails().getDateOfBirth());
        bookerDTO.setTitle(cart.getBookerDetails().getTitle());

        return bookerDTO;
    }

    private static QuantityDTO toQuantity(Quantity quantity) {

        QuantityDTO quantityDTO = new QuantityDTO();

        quantityDTO.setAgeBandId(quantity.getAgeBandId());
        quantityDTO.setAgeBandLabel(quantity.getAgeBandLabel());
        quantityDTO.setQuantity(quantity.getQuantity());

        return quantityDTO;
    }

    private static TotalDTO toBookingOptionTotal(Total total) {

        TotalDTO totalDTO = new TotalDTO();

        totalDTO.setCurrency(total.getCurrency());
        totalDTO.setCurrencyCode(total.getCurrencyCode());
        totalDTO.setFinalTotal(total.getFinalTotal());
        totalDTO.setRoundedFinalTotal(AmountFormatter.floatToFloatRoundingFinalTotal(total.getFinalTotal()));
        totalDTO.setRetailTotal(total.getRetailTotal());

        return totalDTO;
    }

    private static TravelerDTO toTraveler(Traveler traveler) {

        TravelerDTO travelerDTO = new TravelerDTO();

        travelerDTO.setCountryISOCode(traveler.getCountryISOCode());
        travelerDTO.setDateOfBirth(traveler.getDateOfBirth());
        travelerDTO.setEmail(traveler.getEmail());
        travelerDTO.setFirstName(traveler.getFirstName());
        travelerDTO.setLastName(traveler.getLastName());
        travelerDTO.setPhoneNumber(traveler.getPhoneNumber());
        travelerDTO.setTitle(traveler.getTitle());
        travelerDTO.setLeadTraveler(traveler.isLeadTraveler());
        travelerDTO.setAgeBandId(traveler.getAgeBandId());

        return travelerDTO;
    }

    private static BookingQuestionDTO toBookingQuestion(BookingQuestion bookingQuestion, String productId) {

        BookingQuestionDTO bookingQuestionDTO = new BookingQuestionDTO();

        bookingQuestionDTO.setProductId(productId);
        bookingQuestionDTO.setProductOptionId(bookingQuestion.getProductOptionId());
        bookingQuestionDTO.setMessage(bookingQuestion.getMessage());
        bookingQuestionDTO.setQuestionId(bookingQuestion.getQuestionId());
        bookingQuestionDTO.setRequired(bookingQuestion.getRequired());
        bookingQuestionDTO.setSubTitle(bookingQuestion.getSubTitle());
        bookingQuestionDTO.setTitle(bookingQuestion.getTitle());

        return bookingQuestionDTO;
    }

    /*
     * private static PriceDTO toPrice(PriceBreakDownDTO priceBreakDownDTO) {
     * 
     * PriceDTO priceDTO = new PriceDTO();
     * 
     * PricePerAgeBandDTO pricePerAgeBandDTO = priceBreakDownDTO.getPricePerAgeBand();
     * 
     * priceDTO.setAgeBandId(pricePerAgeBandDTO.getAgeBandId());
     * priceDTO.setCurrencyCode(pricePerAgeBandDTO.getCurrencyCode());
     * priceDTO.setDescription(pricePerAgeBandDTO.getDescription()); if (pricePerAgeBandDTO.getRetailPrice() != null) {
     * 
     * priceDTO.setFinalPrice(pricePerAgeBandDTO.getFinalPrice());
     * priceDTO.setRetailPrice(pricePerAgeBandDTO.getRetailPrice()); priceDTO.setRoundedFinalPrice(
     * AmountFormatter.floatToFloatRoundingFinalTotal(pricePerAgeBandDTO.getFinalPrice())); }
     * priceDTO.setMaxBuy(pricePerAgeBandDTO.getMaxBuy()); priceDTO.setMinBuy(pricePerAgeBandDTO.getMinBuy());
     * priceDTO.setPriceType(pricePerAgeBandDTO.getPriceType());
     * 
     * return priceDTO; }
     */

    public static PickupLocation toPickupLocation(PickupLocationDTO pickUpLocation) {

        PickupLocation location = new PickupLocation();
        location.setId(pickUpLocation.getId());
        location.setLocationName(pickUpLocation.getLocationName());

        return location;
    }

    private static PickupLocationDTO toPickupLocationDTO(PickupLocation pickUpLocation) {

        PickupLocationDTO location = new PickupLocationDTO();
        location.setId(pickUpLocation.getId());
        location.setLocationName(pickUpLocation.getLocationName());

        return location;
    }

    public static UpdateHotelPickupRS toUpdateHotelPickupLocationResponse(Cart cart) {

        UpdateHotelPickupRS updateHotelPickupLocationDetails = null;

        updateHotelPickupLocationDetails = new UpdateHotelPickupRS();

        if (cart.getBookerDetails() != null) {

            BookerDTO bookerDTO = toBooker(cart);
            updateHotelPickupLocationDetails.setBookerDetails(bookerDTO);
        }

        if ((cart.getBookingOptions() != null) && (!cart.getBookingOptions().isEmpty())) {

            List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                BookingOptionDTO bookingOptionDTO = toBookingOptionDTO(bookingOption);

                bookingOptionDTOs.add(bookingOptionDTO);
            }

            updateHotelPickupLocationDetails.setBookingOptions(bookingOptionDTOs);
        }

        if (cart.getTotal() != null) {

            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            updateHotelPickupLocationDetails.setTotal(totalDTO);

        }

        return updateHotelPickupLocationDetails;
    }

    private static List<LoyaltyDetailDTO> toLoyaltyDetails(List<LoyaltyDetail> loyaltyDetails) {

        List<LoyaltyDetailDTO> details = new ArrayList<>();

        for (LoyaltyDetail loyaltyDetail : loyaltyDetails) {

            LoyaltyDetailDTO detailDTO = new LoyaltyDetailDTO();
            detailDTO.setLoyaltyPoints(loyaltyDetail.getLoyaltyPoints());
            detailDTO.setLoyaltyProgramId(loyaltyDetail.getLoyaltyProgramId());
            detailDTO.setLoyaltyProgramDisplayName(loyaltyDetail.getLoyaltyProgramDisplayName());

            details.add(detailDTO);
        }

        return details;
    }

    // Partner loyalty points calculation for each booking option
    public static List<LoyaltyDetail> toLoyaltyDetail(List<LoyaltyProgramConfig> loyaltyProgramConfigs,
            float finalTotal) {

        List<LoyaltyDetail> loyaltyDetails = new ArrayList<>();

        for (LoyaltyProgramConfig config : loyaltyProgramConfigs) {

            LoyaltyDetail loyaltyDetail = new LoyaltyDetail();

            int loyaltyPoints = LoyaltyDetailsUtill.calculateLoyaltyPoints(config.getPointsAwardRatio(), finalTotal);

            loyaltyDetail.setLoyaltyPoints(loyaltyPoints);
            loyaltyDetail.setLoyaltyProgramId(config.getProgId());
            loyaltyDetail.setLoyaltyProgramDisplayName(config.getProgDisplayName());

            loyaltyDetails.add(loyaltyDetail);

        }

        return loyaltyDetails;
    }

    public static List<PricePerAgeBandDTO> toConPriceToPricePerAgeBandDTO(
            List<com.placepass.connector.common.cart.Price> prices) {

        List<PricePerAgeBandDTO> pricePerAgeBands = new ArrayList<>();

        for (com.placepass.connector.common.cart.Price conPrice : prices) {

            PricePerAgeBandDTO pricePerAgeBand = new PricePerAgeBandDTO();

            pricePerAgeBand.setPriceGroupSortOrder(conPrice.getPriceGroupSortOrder());
            pricePerAgeBand.setAgeBandId(conPrice.getAgeBandId());
            pricePerAgeBand.setPriceType(conPrice.getPriceType());
            pricePerAgeBand.setCurrencyCode(conPrice.getCurrencyCode());
            pricePerAgeBand.setDescription(conPrice.getDescription());
            pricePerAgeBand.setRetailPrice(conPrice.getRetailPrice());
            pricePerAgeBand.setFinalPrice(conPrice.getFinalPrice());
            pricePerAgeBand.setMaxBuy(conPrice.getMaxBuy());
            pricePerAgeBand.setMinBuy(conPrice.getMinBuy());
            pricePerAgeBand.setAgeTo(conPrice.getAgeTo());
            pricePerAgeBand.setAgeFrom(conPrice.getAgeFrom());
            pricePerAgeBand
                    .setRoundedFinalPrice(AmountFormatter.floatToFloatRoundingFinalTotal(conPrice.getFinalPrice()));
            pricePerAgeBand.setPricingUnit(conPrice.getPricingUnit());
            pricePerAgeBands.add(pricePerAgeBand);
        }

        return pricePerAgeBands;
    }

    public static List<PricePerAgeBandDTO> toCartPriceToPricePerAgeBandDTO(List<Price> prices) {
        List<PricePerAgeBandDTO> pricePerAgeBands = new ArrayList<>();

        for (Price cartPrice : prices) {

            PricePerAgeBandDTO pricePerAgeBand = new PricePerAgeBandDTO();

            pricePerAgeBand.setPriceGroupSortOrder(cartPrice.getPriceGroupSortOrder());
            pricePerAgeBand.setAgeBandId(cartPrice.getAgeBandId());
            pricePerAgeBand.setPriceType(cartPrice.getPriceType());
            pricePerAgeBand.setCurrencyCode(cartPrice.getCurrencyCode());
            pricePerAgeBand.setDescription(cartPrice.getDescription());
            pricePerAgeBand.setRetailPrice(cartPrice.getRetailPrice());
            pricePerAgeBand.setFinalPrice(cartPrice.getFinalPrice());
            pricePerAgeBand.setMaxBuy(cartPrice.getMaxBuy());
            pricePerAgeBand.setMinBuy(cartPrice.getMinBuy());
            pricePerAgeBand.setAgeTo(cartPrice.getAgeTo());
            pricePerAgeBand.setAgeFrom(cartPrice.getAgeFrom());
            pricePerAgeBand
                    .setRoundedFinalPrice(AmountFormatter.floatToFloatRoundingFinalTotal(cartPrice.getFinalPrice()));
            pricePerAgeBand.setPricingUnit(cartPrice.getPricingUnit());
            pricePerAgeBands.add(pricePerAgeBand);
        }

        return pricePerAgeBands;
    }

    public static List<QuantityDTO> toQuantityListDTO(List<Quantity> quantities) {

        List<QuantityDTO> quantityDTOs = new ArrayList<QuantityDTO>();

        for (Quantity quantity : quantities) {

            if (quantity.getQuantity() > 0) {

                QuantityDTO quantityDTO = new QuantityDTO();

                if (PlacePassAgeBandType.ADULT.name().equals(quantity.getAgeBandLabel())) {
                    quantityDTO.setAgeBandId(PlacePassAgeBandType.ADULT.getAgeBandId());
                } else if (PlacePassAgeBandType.SENIOR.name().equals(quantity.getAgeBandLabel())) {
                    quantityDTO.setAgeBandId(PlacePassAgeBandType.SENIOR.getAgeBandId());
                } else if (PlacePassAgeBandType.CHILD.name().equals(quantity.getAgeBandLabel())) {
                    quantityDTO.setAgeBandId(PlacePassAgeBandType.CHILD.getAgeBandId());
                } else if (PlacePassAgeBandType.INFANT.name().equals(quantity.getAgeBandLabel())) {
                    quantityDTO.setAgeBandId(PlacePassAgeBandType.INFANT.getAgeBandId());
                } else if (PlacePassAgeBandType.YOUTH.name().equals(quantity.getAgeBandLabel())) {
                    quantityDTO.setAgeBandId(PlacePassAgeBandType.YOUTH.getAgeBandId());
                }

                quantityDTO.setAgeBandLabel(quantity.getAgeBandLabel());
                quantityDTO.setQuantity(quantity.getQuantity());

                quantityDTOs.add(quantityDTO);
            }

        }

        return quantityDTOs;
    }

    // Showing best price
    public static List<PriceBreakDownDTO> getMatchedPriceList(List<Price> prices, List<Quantity> quantities) {

        List<PriceMatchPricePerAgeBand> inputPriceMatchList = toPriceMatchPricePerAgeBandToPriceMatch(prices);

        List<PriceMatchQuantity> priceMatchQuantities = toQuantitiesDtoToPriceMatch(toQuantityListDTO(quantities));

        List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns = PriceMatch.getFilteredPriceList(inputPriceMatchList,
                priceMatchQuantities);

        return toPriceMatchPriceBreakDownsToPriceBreakDownDTO(priceMatchPriceBreakDowns);

    }

    public static boolean priceQuantityMatching(List<com.placepass.connector.common.cart.Price> prices,
            List<QuantityDTO> quantities) {

        boolean isPriceTypeQuantityValid = true;

        List<PriceMatchPricePerAgeBand> inputPriceMatchList = toPriceMatchPricePerAgeBandConDTOToPriceMatch(prices);

        List<PriceMatchQuantity> priceMatchQuantities = toQuantitiesDtoToPriceMatch(quantities);

        List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns = PriceMatch.getFilteredPriceList(inputPriceMatchList,
                priceMatchQuantities);

        List<PriceBreakDownDTO> priceBreakDownList = toPriceMatchPriceBreakDownListToDTOList(priceMatchPriceBreakDowns);

        for (QuantityDTO quantity : quantities) {

            for (PriceBreakDownDTO priceBreakDown : priceBreakDownList) {

                PricePerAgeBandDTO price = priceBreakDown.getPricePerAgeBand();

                if (quantity.getAgeBandLabel().equals(price.getPriceType()) && price.getRetailPrice() == null) {

                    isPriceTypeQuantityValid = false;

                }
            }
        }

        return isPriceTypeQuantityValid;
    }

    public static List<PriceMatchQuantity> toQuantitiesDtoToPriceMatch(List<QuantityDTO> quantities) {

        List<PriceMatchQuantity> priceMatchQuantities = new ArrayList<>();

        for (QuantityDTO quantityDTO : quantities) {

            PriceMatchQuantity priceMatchQuantity = new PriceMatchQuantity();

            priceMatchQuantity.setAgeBandId(quantityDTO.getAgeBandId());
            priceMatchQuantity.setAgeBandLabel(quantityDTO.getAgeBandLabel());
            priceMatchQuantity.setQuantity(quantityDTO.getQuantity());

            priceMatchQuantities.add(priceMatchQuantity);
        }

        return priceMatchQuantities;
    }

    public static List<PriceMatchPricePerAgeBand> toPriceMatchPricePerAgeBandToPriceMatch(List<Price> inputPriceList) {

        List<PriceMatchPricePerAgeBand> matchPricePerAgeBands = new ArrayList<>();

        for (Price price : inputPriceList) {

            PriceMatchPricePerAgeBand priceMatchPricePerAgeBand = new PriceMatchPricePerAgeBand();

            priceMatchPricePerAgeBand.setAgeBandId(price.getAgeBandId());
            priceMatchPricePerAgeBand.setAgeFrom(price.getAgeFrom());
            priceMatchPricePerAgeBand.setAgeTo(price.getAgeTo());
            priceMatchPricePerAgeBand.setCurrencyCode(price.getCurrencyCode());
            priceMatchPricePerAgeBand.setDescription(price.getDescription());
            priceMatchPricePerAgeBand.setFinalPrice(price.getFinalPrice());
            priceMatchPricePerAgeBand.setMaxBuy(price.getMaxBuy());
            priceMatchPricePerAgeBand.setMinBuy(price.getMinBuy());
            priceMatchPricePerAgeBand.setPriceGroupSortOrder(price.getPriceGroupSortOrder());
            priceMatchPricePerAgeBand.setPriceType(price.getPriceType());
            priceMatchPricePerAgeBand.setRetailPrice(price.getRetailPrice());
            priceMatchPricePerAgeBand.setRoundedFinalPrice(price.getRoundedFinalPrice());
            priceMatchPricePerAgeBand.setPricingUnit(price.getPricingUnit());

            matchPricePerAgeBands.add(priceMatchPricePerAgeBand);

        }

        return matchPricePerAgeBands;
    }

    public static List<PriceMatchPricePerAgeBand> toPriceMatchPricePerAgeBandConDTOToPriceMatch(
            List<com.placepass.connector.common.cart.Price> inputPriceList) {

        List<PriceMatchPricePerAgeBand> matchPricePerAgeBands = new ArrayList<>();

        for (com.placepass.connector.common.cart.Price priceCDTO : inputPriceList) {

            PriceMatchPricePerAgeBand priceMatchPricePerAgeBand = new PriceMatchPricePerAgeBand();

            priceMatchPricePerAgeBand.setAgeBandId(priceCDTO.getAgeBandId());
            priceMatchPricePerAgeBand.setAgeFrom(priceCDTO.getAgeFrom());
            priceMatchPricePerAgeBand.setAgeTo(priceCDTO.getAgeTo());
            priceMatchPricePerAgeBand.setCurrencyCode(priceCDTO.getCurrencyCode());
            priceMatchPricePerAgeBand.setDescription(priceCDTO.getDescription());
            priceMatchPricePerAgeBand.setFinalPrice(priceCDTO.getFinalPrice());
            priceMatchPricePerAgeBand.setMaxBuy(priceCDTO.getMaxBuy());
            priceMatchPricePerAgeBand.setMinBuy(priceCDTO.getMinBuy());
            priceMatchPricePerAgeBand.setPriceGroupSortOrder(priceCDTO.getPriceGroupSortOrder());
            priceMatchPricePerAgeBand.setPriceType(priceCDTO.getPriceType());
            priceMatchPricePerAgeBand.setRetailPrice(priceCDTO.getRetailPrice());
            priceMatchPricePerAgeBand.setPricingUnit(priceCDTO.getPricingUnit());

            matchPricePerAgeBands.add(priceMatchPricePerAgeBand);

        }

        return matchPricePerAgeBands;
    }

    public static List<PriceBreakDownDTO> toPriceMatchPriceBreakDownsToPriceBreakDownDTO(
            List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns) {

        List<PriceBreakDownDTO> priceBreakDownDTOs = new ArrayList<>();

        for (PriceMatchPriceBreakDown priceBreakDown : priceMatchPriceBreakDowns) {

            PriceBreakDownDTO priceBreakDownDTO = new PriceBreakDownDTO();

            priceBreakDownDTO.setPricePerAgeBand(toPricePerAgeBandToDTO(priceBreakDown.getPricePerAgeBand()));
            priceBreakDownDTO
                    .setTotalPricePerAgeBand(toTotalPricePerAgeBandToDTO(priceBreakDown.getTotalPricePerAgeBand()));

            priceBreakDownDTOs.add(priceBreakDownDTO);

        }

        return priceBreakDownDTOs;
    }

    public static TotalPricePerAgeBandDTO toTotalPricePerAgeBandToDTO(
            PriceMatchTotalPricePerAgeBand totalPricePerAgeBand) {

        TotalPricePerAgeBandDTO totalPricePerAgeBandDTO = new TotalPricePerAgeBandDTO();

        totalPricePerAgeBandDTO.setAgeBandId(totalPricePerAgeBand.getAgeBandId());
        totalPricePerAgeBandDTO.setCurrencyCode(totalPricePerAgeBand.getCurrencyCode());
        totalPricePerAgeBandDTO.setDescription(totalPricePerAgeBand.getDescription());
        totalPricePerAgeBandDTO.setFinalPrice(totalPricePerAgeBand.getFinalPrice());
        totalPricePerAgeBandDTO.setPriceType(totalPricePerAgeBand.getPriceType());
        totalPricePerAgeBandDTO.setRetailPrice(totalPricePerAgeBand.getRetailPrice());
        totalPricePerAgeBandDTO.setRoundedFinalPrice(totalPricePerAgeBand.getRoundedFinalPrice());

        return totalPricePerAgeBandDTO;
    }

    public static PricePerAgeBandDTO toPricePerAgeBandToDTO(PriceMatchPricePerAgeBand pricePerAgeBand) {

        PricePerAgeBandDTO pricePerAgeBandDTO = new PricePerAgeBandDTO();

        pricePerAgeBandDTO.setAgeBandId(pricePerAgeBand.getAgeBandId());
        pricePerAgeBandDTO.setAgeFrom(pricePerAgeBand.getAgeFrom());
        pricePerAgeBandDTO.setAgeTo(pricePerAgeBand.getAgeTo());
        pricePerAgeBandDTO.setCurrencyCode(pricePerAgeBand.getCurrencyCode());
        pricePerAgeBandDTO.setDescription(pricePerAgeBand.getDescription());
        pricePerAgeBandDTO.setFinalPrice(pricePerAgeBand.getFinalPrice());
        pricePerAgeBandDTO.setMaxBuy(pricePerAgeBand.getMaxBuy());
        pricePerAgeBandDTO.setMinBuy(pricePerAgeBand.getMinBuy());
        pricePerAgeBandDTO.setPriceGroupSortOrder(pricePerAgeBand.getPriceGroupSortOrder());
        pricePerAgeBandDTO.setPriceType(pricePerAgeBand.getPriceType());
        pricePerAgeBandDTO.setRetailPrice(pricePerAgeBand.getRetailPrice());
        pricePerAgeBandDTO.setRoundedFinalPrice(pricePerAgeBand.getRoundedFinalPrice());
        pricePerAgeBandDTO.setPricingUnit(pricePerAgeBand.getPricingUnit());

        return pricePerAgeBandDTO;
    }

    private static List<PriceBreakDownDTO> toPriceMatchPriceBreakDownListToDTOList(
            List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns) {

        List<PriceBreakDownDTO> priceBreakDownDTOs = new ArrayList<>();

        for (PriceMatchPriceBreakDown priceBreakDown : priceMatchPriceBreakDowns) {

            PriceBreakDownDTO priceBreakDownDTO = new PriceBreakDownDTO();

            priceBreakDownDTO.setPricePerAgeBand(toPricePerAgeBandToDTO(priceBreakDown.getPricePerAgeBand()));
            priceBreakDownDTO
                    .setTotalPricePerAgeBand(toTotalPricePerAgeBandToDTO(priceBreakDown.getTotalPricePerAgeBand()));

            priceBreakDownDTOs.add(priceBreakDownDTO);
        }

        return priceBreakDownDTOs;
    }

    public static UpdateLanguageOptionRS toUpdateLanguageOptionCodeResponse(Cart cart) {

        UpdateLanguageOptionRS languageOptionRS = null;

        languageOptionRS = new UpdateLanguageOptionRS();

        if (cart.getBookerDetails() != null) {

            BookerDTO bookerDTO = toBooker(cart);
            languageOptionRS.setBookerDetails(bookerDTO);
        }

        if ((cart.getBookingOptions() != null) && (!cart.getBookingOptions().isEmpty())) {

            List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                BookingOptionDTO bookingOptionDTO = toBookingOptionDTO(bookingOption);

                bookingOptionDTOs.add(bookingOptionDTO);
            }

            languageOptionRS.setBookingOptions(bookingOptionDTOs);
        }

        if (cart.getTotal() != null) {

            TotalDTO totalDTO = toDiscountTotalDTO(cart.getTotal());
            languageOptionRS.setTotal(totalDTO);

        }

        return languageOptionRS;
    }

    public static BookingOptionDTO setQuantityDTOAgeBandLabels(BookingOptionDTO bookingOptionDTO) {

        for (QuantityDTO quantityDTo : bookingOptionDTO.getQuantities()) {
            if (quantityDTo.getAgeBandId() == PlacePassAgeBandType.ADULT.getAgeBandId()) {
                quantityDTo.setAgeBandLabel(PlacePassAgeBandType.ADULT.name());
            } else if (quantityDTo.getAgeBandId() == PlacePassAgeBandType.SENIOR.getAgeBandId()) {
                quantityDTo.setAgeBandLabel(PlacePassAgeBandType.SENIOR.name());
            } else if (quantityDTo.getAgeBandId() == PlacePassAgeBandType.CHILD.getAgeBandId()) {
                quantityDTo.setAgeBandLabel(PlacePassAgeBandType.CHILD.name());
            } else if (quantityDTo.getAgeBandId() == PlacePassAgeBandType.INFANT.getAgeBandId()) {
                quantityDTo.setAgeBandLabel(PlacePassAgeBandType.INFANT.name());
            } else if (quantityDTo.getAgeBandId() == PlacePassAgeBandType.YOUTH.getAgeBandId()) {
                quantityDTo.setAgeBandLabel(PlacePassAgeBandType.YOUTH.name());
            } else {
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_AGE_BAND_ID.toString(),
                        PlacePassExceptionCodes.INVALID_AGE_BAND_ID.getDescription());
            }
        }

        return bookingOptionDTO;
    }

    private static FeeDTO toFeeDTO(Fee fee) {
        String feeType = null;
        String feeCategory = null;
        
        if (fee.getFeeType() != null) {
            feeType = fee.getFeeType().name();
        }

        if (fee.getFeeCategory() != null) {
            feeCategory = fee.getFeeCategory().name();
        }

        FeeDTO feeDTO = new FeeDTO(feeType, feeCategory, fee.getDescription(), fee.getCurrency(),
                fee.getAmount());
        return feeDTO;
    }

    private static TotalDTO toDiscountTotalDTO(Total total) {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setCurrency(total.getCurrency());
        totalDTO.setCurrencyCode(total.getCurrencyCode());
        totalDTO.setFinalTotal(total.getFinalTotal());
        totalDTO.setRetailTotal(total.getRetailTotal());
        totalDTO.setRoundedFinalTotal(total.getRoundedFinalTotal());
        totalDTO.setOriginalTotal(total.getOriginalTotal());
        totalDTO.setDiscountAmount(total.getDiscountAmount());
        totalDTO.setTotalAfterDiscount(total.getTotalAfterDiscount());
        return totalDTO;
    }
    
    private static List<FeeDTO> toFees(List<Fee> fees) {
        
        List<FeeDTO> feeDTOs = new ArrayList<>();
        
        for (Fee fee : fees) {
            
            FeeDTO feeDTO = toFeeDTO(fee);
            feeDTOs.add(feeDTO);
        }
        
        return feeDTOs;
    }
    
}
