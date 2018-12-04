package com.placepass.booking.application.cart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.placepass.booking.application.cart.dto.AddBookingOptionRQ;
import com.placepass.booking.application.cart.dto.AddBookingOptionRS;
import com.placepass.booking.application.cart.dto.BookingOptionDTO;
import com.placepass.booking.application.cart.dto.BookingQuestionDTO;
import com.placepass.booking.application.cart.dto.CreateCartRQ;
import com.placepass.booking.application.cart.dto.CreateCartRS;
import com.placepass.booking.application.cart.dto.DeleteBookingOptionRS;
import com.placepass.booking.application.cart.dto.GetBookingQuestionsRS;
import com.placepass.booking.application.cart.dto.PickupLocationDTO;
import com.placepass.booking.application.cart.dto.QuantityDTO;
import com.placepass.booking.application.cart.dto.QuestionAnswerDTO;
import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.booking.application.cart.dto.UpdateBookerDetailsRQ;
import com.placepass.booking.application.cart.dto.UpdateBookerDetailsRS;
import com.placepass.booking.application.cart.dto.UpdateBookingAnswerRQ;
import com.placepass.booking.application.cart.dto.UpdateBookingAnswerRS;
import com.placepass.booking.application.cart.dto.UpdateHotelPickupRQ;
import com.placepass.booking.application.cart.dto.UpdateHotelPickupRS;
import com.placepass.booking.application.cart.dto.UpdateLanguageOptionRQ;
import com.placepass.booking.application.cart.dto.UpdateLanguageOptionRS;
import com.placepass.booking.application.cart.dto.UpdateTravelerDetailsRQ;
import com.placepass.booking.application.cart.dto.UpdateTravelerDetailsRS;
import com.placepass.booking.application.cart.dto.ValidateCartRQ;
import com.placepass.booking.application.cart.dto.ValidateCartRS;
import com.placepass.booking.application.cart.dto.ViewCartRS;
import com.placepass.booking.domain.booking.BookingAnswer;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.BookingQuestion;
import com.placepass.booking.domain.booking.Cart;
import com.placepass.booking.domain.booking.CartService;
import com.placepass.booking.domain.booking.CartState;
import com.placepass.booking.domain.booking.LoyaltyDetail;
import com.placepass.booking.domain.booking.Total;
import com.placepass.booking.domain.booking.strategy.DiscountStrategy;
import com.placepass.booking.domain.common.TotalCalculator;
import com.placepass.booking.domain.config.LoyaltyProgramConfig;
import com.placepass.booking.domain.config.LoyaltyProgramConfigService;
import com.placepass.booking.domain.config.PartnerConfig;
import com.placepass.booking.domain.config.PartnerConfigService;
import com.placepass.booking.infrastructure.VendorBookingServiceImpl;
import com.placepass.booking.infrastructure.VendorProductServiceImpl;
import com.placepass.connector.common.cart.GetProductOptionsRS;
import com.placepass.connector.common.cart.GetProductPriceRQ;
import com.placepass.connector.common.cart.GetProductPriceRS;
import com.placepass.connector.common.cart.Price;
import com.placepass.connector.common.cart.ProductOption;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.pricematch.PriceMatch;
import com.placepass.utils.validate.ValidateScope;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.utils.vendorproduct.VendorProduct;
import com.placepass.utils.vendorproduct.VendorProductOption;

/**
 * @author chanaka.k
 *
 */
@Service
public class CartApplicationService {

    @Autowired
    private CartService cartService;

    @Autowired
    private LoyaltyProgramConfigService loyaltyProgramConfigService;

    @Autowired
    VendorBookingServiceImpl bookingService;

    @Autowired
    VendorProductServiceImpl productService;

    @Autowired
    @Qualifier("productHashGenerator")
    ProductHashGenerator productHashGenerator;

    @Autowired
    private DiscountStrategy discountStrategy;

    @Autowired
    private PartnerConfigService partnerConfigService;

    private static final Logger logger = LoggerFactory.getLogger(CartApplicationService.class);

    public CreateCartRS createCart(String partnerId, CreateCartRQ createCartRQ) {

        logger.info("Starting create cart process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logger.info("Create Cart Invoked. {}", logData);

        Cart cart = null;
        CreateCartRS createCartRS = null;
        List<ProductOption> connectorProductOptions = null;
        List<LoyaltyProgramConfig> loyaltyProgramConfigs = null;

        List<BookingOption> bookingOptionsList = new ArrayList<BookingOption>();

        // Only one booking option will support. In future can be change it.
        if (createCartRQ.getBookingOptions() != null && createCartRQ.getBookingOptions().size() > 1) {

            logger.error("Error while creating cart due to exceed number of booking options");
            throw new BadRequestException(PlacePassExceptionCodes.EXCEED_NO_OF_BOOKING_OPTIONS.toString(),
                    PlacePassExceptionCodes.EXCEED_NO_OF_BOOKING_OPTIONS.getDescription());

        }

        for (BookingOptionDTO bookingOptionDTO : createCartRQ.getBookingOptions()) {

            bookingOptionDTO = CartRequestTransformer.setQuantityDTOAgeBandLabels(bookingOptionDTO);

            // For any booking ADULT or SENIOR is required

            if (!PriceMatch.isAdultOrSeniorExist(
                    CartRequestTransformer.toQuantitiesDtoToPriceMatch(bookingOptionDTO.getQuantities()))) {
                logger.error("Error while creating cart due to, adult or senior required");
                throw new BadRequestException(PlacePassExceptionCodes.ADULT_OR_SENIOR_REQUIRED.toString(),
                        PlacePassExceptionCodes.ADULT_OR_SENIOR_REQUIRED.getDescription());
            }

            // Limit head count to 9
            if (isHeadCountExceeded(bookingOptionDTO)) {

                logger.error("Error occurred due to, maximum head count exceed ");
                throw new BadRequestException(PlacePassExceptionCodes.MAX_HEAD_COUNT_EXCEED.toString(),
                        PlacePassExceptionCodes.MAX_HEAD_COUNT_EXCEED.getDescription());
            }

            if (bookingOptionDTO.getPickupLocation() != null) {
                validatePickupLocation(bookingOptionDTO.getPickupLocation());
            }

            VendorProduct vendorProduct = null;
            VendorProductOption vendorProductOption = null;

            try {

                vendorProduct = VendorProduct.getInstance(bookingOptionDTO.getProductId(), productHashGenerator);

            } catch (Exception e) {

                logger.error("Exception occurred while dehash product id in create cart : ", e);
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                        PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());
            }

            try {

                vendorProductOption = VendorProductOption.getInstance(bookingOptionDTO.getProductOptionId(),
                        productHashGenerator);

            } catch (Exception e) {

                logger.error("Exception occurred while dehash product option id in create cart : ", e);
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_OPTION_ID.toString(),
                        PlacePassExceptionCodes.INVALID_PRODUCT_OPTION_ID.getDescription());
            }

            logData.put("PRODUCT_ID", vendorProduct.getEncodedProductID());
            logData.put("PRODUCT_OPTION_ID", vendorProductOption.getEncodedProductOptionID());
            logData.put("VENDOR_PRODUCT_ID", vendorProduct.getVendorProductID());
            logData.put("VENDOR_PRODUCT_OPTION_ID", vendorProductOption.getVendorProductOptionID());
            logData.put("VENDOR", vendorProductOption.getVendor().toString());
            logData.put("BOOKING_DATE", bookingOptionDTO.getBookingDate());
            logger.info("Start getting product details invoke in create cart {}", logData);

            // Making a REST call to the product services, to caching some of data.
            GetProductOptionsRS productOptionsCRS = getProductOptionDetails(vendorProduct.getVendorProductID(),
                    LocalDate.parse(bookingOptionDTO.getBookingDate()),
                    Vendor.getVendor(vendorProductOption.getVendor().toString()));

            logger.info("End getting product details invoke in create cart {}", logData);

            connectorProductOptions = productOptionsCRS.getProductOptionGroup().getProductOptions();

            for (ProductOption conProductOption : connectorProductOptions) {

                if (vendorProductOption.getVendorProductOptionID().equals(conProductOption.getProductOptionId())) {

                    bookingOptionDTO = ignoreZeroQuantities(bookingOptionDTO);

                    // Quantity validation in Create Cart level
                    quantityValidator(bookingOptionDTO, conProductOption, productHashGenerator);

                    BookingOption bookingOption = CartRequestTransformer.toBookingOption(bookingOptionDTO,
                            vendorProduct, vendorProductOption, conProductOption);

                    bookingOptionsList.add(bookingOption);

                }

            }

            if (createCartRQ.getBookingOptions() != null && bookingOptionsList.isEmpty()) {

                logger.error("Error while creating cart due to, providing invalid product option id ");
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_OPTION_ID.toString(),
                        PlacePassExceptionCodes.INVALID_PRODUCT_OPTION_ID.getDescription());

            }

        }

        // Fetching loyalty program configuration detail list
        loyaltyProgramConfigs = loyaltyProgramConfigService.getLoyaltyProgramConfigDetails(partnerId);

        cart = CartRequestTransformer.toCart(partnerId, bookingOptionsList);

        if (cart.getBookingOptions() != null && !cart.getBookingOptions().isEmpty()) {

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                GetProductPriceRQ productPriceCRQ = CartConnectorTransformer.toGetProductPriceCRQ(bookingOption);
                productPriceCRQ.setVendorValidation(false);

                logger.info("Starting... to retrieve price details from connector side");

                GetProductPriceRS getProductPriceCRS = productService.getProductPrice(productPriceCRQ,
                        Vendor.getVendor(bookingOption.getVendor()));

                logger.info("End... to retrieve price details from connector side");

                if (getProductPriceCRS.getResultType().getCode() != 0) {

                    logger.error("Error while calling getPrice call to connector side with inavlid data");
                    throw new NotFoundException("PRODUCT_NOT_AVAILABLE",
                            getProductPriceCRS.getResultType().getMessage());

                }

                if (getProductPriceCRS.getTotal() != null) {

                    Total total = CartRequestTransformer.getTotal(getProductPriceCRS.getTotal());
                    bookingOption.setTotal(total);

                    List<LoyaltyDetail> loyaltyDetails = CartRequestTransformer.toLoyaltyDetail(loyaltyProgramConfigs,
                            total.getFinalTotal());

                    if (loyaltyDetails != null) {
                        bookingOption.setLoyaltyDetails(loyaltyDetails);
                    }

                }

            }

        }

        Total cartTotal = TotalCalculator.calculate(cart.getBookingOptions());

        logData.put("MERCHANT_TOTAL", cartTotal.getMerchantTotal());
        logData.put("FINAL_TOTAL", cartTotal.getFinalTotal());
        logData.put("RETAIL_TOTAL", cartTotal.getRetailTotal());
        logger.info("Cart price details {}", logData);

        cart.setTotal(cartTotal);

        cartService.saveCart(cart);
        logger.info("Cart saved successfully...");

        PartnerConfig partnerConfig = partnerConfigService.getPartnerConfig(cart.getPartnerId());

        if (partnerConfig.isDiscountServiceEnabled()) {

            logger.info("Applying fees to Cart...");

            try {
                cart = discountStrategy.applyFees(partnerId, cart);
            } catch (Exception e) {
                logger.error("Error occurred while processing discount due to --", e.getMessage());
            }

            if (!cart.getFees().isEmpty()) {
                cartService.updateCart(cart);
                logger.info("Applied fees to Cart...");
            }
        }

        createCartRS = CartRequestTransformer.toCreateCartResponse(cart);
        logger.info("Cart response return successfully...");

        return createCartRS;

    }

    public ViewCartRS viewCart(String partnerId, String cartId) {

        logger.info("Start view cart process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logger.info("View Cart Invoked. {}", logData);

        Cart cart = null;
        ViewCartRS viewCartRS = null;

        cart = getCart(cart, cartId, partnerId);

        viewCartRS = CartRequestTransformer.toViewCartResponse(cart);

        logger.info(" View Cart response return successfully...");

        return viewCartRS;
    }

    public DeleteBookingOptionRS deleteBookingOption(String partnerId, String cartId, String bookingOptionId) {

        logger.info("Start deleting booking option process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logData.put("BOOKING_OPTION_ID", bookingOptionId);
        logger.info("Delete Booking Option Invoked. {}", logData);

        Cart cart = null;
        DeleteBookingOptionRS deleteBookingOptionRS = null;

        cart = getCart(cart, cartId, partnerId);

        if (!cart.getBookingOptions().stream().anyMatch(bo -> bo.getProductOptionId().equals(bookingOptionId))) {

            logger.error("Error while deleting product option due to requested product option id not found");
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.getDescription());
        }

        cart = CartRequestTransformer.toDeleteBookingOptions(cart, bookingOptionId);

        Total cartTotal = TotalCalculator.calculate(cart.getBookingOptions());

        logData.put("MERCHANT_TOTAL", cartTotal.getMerchantTotal());
        logData.put("FINAL_TOTAL", cartTotal.getFinalTotal());
        logData.put("RETAIL_TOTAL", cartTotal.getRetailTotal());
        logger.info("Cart price details {}", logData);

        cart.setTotal(cartTotal);

        cartService.updateCart(cart);
        deleteBookingOptionRS = CartRequestTransformer.toDeleteBookingOptionResponse(cart);
        logger.info("Delete Booking Option response return successfully...");

        return deleteBookingOptionRS;
    }

    public AddBookingOptionRS addBookingOptions(String partnerId, String cartId,
            AddBookingOptionRQ addBookingOptionRQ) {

        logger.info("Start adding booking option process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logger.info("Add Booking Option Invoked. {}", logData);

        Cart cart = null;
        AddBookingOptionRS addBookingOptionRS = null;
        List<ProductOption> connectorProductOptions = null;
        List<BookingOption> updatedBookingOptions = null;
        List<LoyaltyProgramConfig> loyaltyProgramConfigs = null;

        cart = getCart(cart, cartId, partnerId);

        /*
         * Before adding booking option to the cart, need to check is there any booking option already added and
         * requested booking option list size is one . In future can be change this
         */
        if (addBookingOptionRQ.getBookingOptions().size() > 1 || cart.getBookingOptions().size() == 1) {

            logger.error("Error while adding booking option due to multiple booking options");
            throw new BadRequestException(PlacePassExceptionCodes.EXCEED_NO_OF_BOOKING_OPTIONS.toString(),
                    PlacePassExceptionCodes.EXCEED_NO_OF_BOOKING_OPTIONS.getDescription());
        }

        List<BookingOptionDTO> bookingOptions = addBookingOptionRQ.getBookingOptions();

        if (cart.getBookingOptions() == null || cart.getBookingOptions().isEmpty()) {
            updatedBookingOptions = new ArrayList<BookingOption>();
        } else {
            updatedBookingOptions = cart.getBookingOptions();
        }

        for (BookingOptionDTO bookingOption : bookingOptions) {

            bookingOption = CartRequestTransformer.setQuantityDTOAgeBandLabels(bookingOption);

            // Only one booking option will support. In future can be change it.
            if (!PriceMatch.isAdultOrSeniorExistUsingLable(
                    CartRequestTransformer.toQuantitiesDtoToPriceMatch(bookingOption.getQuantities()))) {
                logger.error("Error while creating cart due to exceed number of booking options");
                throw new BadRequestException(PlacePassExceptionCodes.ADULT_OR_SENIOR_REQUIRED.toString(),
                        PlacePassExceptionCodes.ADULT_OR_SENIOR_REQUIRED.getDescription());
            }

            // Limit head count to 9
            if (isHeadCountExceeded(bookingOption)) {

                logger.error("Error occurred due to, maximum head count exceed ");
                throw new BadRequestException(PlacePassExceptionCodes.MAX_HEAD_COUNT_EXCEED.toString(),
                        PlacePassExceptionCodes.MAX_HEAD_COUNT_EXCEED.getDescription());

            }

            if (bookingOption.getPickupLocation() != null) {
                validatePickupLocation(bookingOption.getPickupLocation());
            }

            VendorProduct vendorProduct = null;
            VendorProductOption vendorProductOption = null;
            try {

                vendorProduct = VendorProduct.getInstance(bookingOption.getProductId(), productHashGenerator);

            } catch (Exception e) {

                logger.error("Exception occurred while dehash product id in add booking option process : ", e);
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                        PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());

            }

            try {

                vendorProductOption = VendorProductOption.getInstance(bookingOption.getProductOptionId(),
                        productHashGenerator);

            } catch (Exception e) {

                logger.error("Exception occurred while dehash product option id in add booking option process : ", e);
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_OPTION_ID.toString(),
                        PlacePassExceptionCodes.INVALID_PRODUCT_OPTION_ID.getDescription());

            }

            logData.put("PRODUCT_ID", vendorProduct.getEncodedProductID());
            logData.put("PRODUCT_OPTION_ID", vendorProductOption.getEncodedProductOptionID());
            logData.put("VENDOR_PRODUCT_ID", vendorProduct.getVendorProductID());
            logData.put("VENDOR_PRODUCT_OPTION_ID", vendorProductOption.getVendorProductOptionID());
            logData.put("VENDOR", vendorProductOption.getVendor().toString());
            logData.put("BOOKING_DATE", bookingOption.getBookingDate());
            logger.info("Start getting product details invoke in add booking option process {}", logData);

            // Making a REST call to the product services, to caching some of data.
            GetProductOptionsRS productOptionsCRS = getProductOptionDetails(vendorProduct.getVendorProductID(),
                    LocalDate.parse(bookingOption.getBookingDate()),
                    Vendor.getVendor(vendorProductOption.getVendor().toString()));

            logger.info("End getting product details invoke in add booking option process {}", logData);

            connectorProductOptions = productOptionsCRS.getProductOptionGroup().getProductOptions();

            for (ProductOption conProductOption : connectorProductOptions) {

                if (vendorProductOption.getVendorProductOptionID().equals(conProductOption.getProductOptionId())) {

                    bookingOption = ignoreZeroQuantities(bookingOption);

                    // Quantity validation in Add booking option level
                    quantityValidator(bookingOption, conProductOption, productHashGenerator);

                    BookingOption updatedBookingOption = CartRequestTransformer.toAddBookingOption(bookingOption,
                            conProductOption, productHashGenerator);

                    updatedBookingOptions.add(updatedBookingOption);

                }
            }

        }

        if (bookingOptions != null && updatedBookingOptions.isEmpty()) {

            logger.error("Error while adding product option due to requested product option id not found");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_OPTION_ID.toString(),
                    PlacePassExceptionCodes.INVALID_PRODUCT_OPTION_ID.getDescription());

        }

        // Fetching loyalty program configuration detail list
        loyaltyProgramConfigs = loyaltyProgramConfigService.getLoyaltyProgramConfigDetails(partnerId);

        cart.setBookingOptions(updatedBookingOptions);

        if (cart.getBookingOptions() != null && !cart.getBookingOptions().isEmpty()) {

            for (BookingOption bookingOption : cart.getBookingOptions()) {

                GetProductPriceRQ getProductPriceCRQ = CartConnectorTransformer.toGetProductPriceCRQ(bookingOption);
                getProductPriceCRQ.setVendorValidation(false);

                logger.info("Start getting price details invoke in add booking option process ");

                GetProductPriceRS getProductPriceCRS = productService.getProductPrice(getProductPriceCRQ,
                        Vendor.getVendor(bookingOption.getVendor()));

                logger.info("End getting price details invoke in add booking option process ");

                if (getProductPriceCRS.getResultType().getCode() != 0) {

                    logger.error("Error while calling getPrice call to connector side with inavlid data");
                    throw new NotFoundException("PRODUCT_NOT_AVAILABLE",
                            getProductPriceCRS.getResultType().getMessage());

                }

                if (getProductPriceCRS.getTotal() != null) {

                    Total total = CartRequestTransformer.getTotal(getProductPriceCRS.getTotal());
                    bookingOption.setTotal(total);

                    List<LoyaltyDetail> loyaltyDetails = CartRequestTransformer.toLoyaltyDetail(loyaltyProgramConfigs,
                            total.getFinalTotal());

                    if (loyaltyDetails != null) {
                        bookingOption.setLoyaltyDetails(loyaltyDetails);
                    }

                }

            }

        }
        // calculate cart total by summing booking option totals
        Total cartTotal = TotalCalculator.calculate(cart.getBookingOptions());

        logData.put("MERCHANT_TOTAL", cartTotal.getMerchantTotal());
        logData.put("FINAL_TOTAL", cartTotal.getFinalTotal());
        logData.put("RETAIL_TOTAL", cartTotal.getRetailTotal());
        logger.info("Cart price details {}", logData);

        cart.setTotal(cartTotal);
        cartService.updateCart(cart);
        addBookingOptionRS = CartRequestTransformer.toAddBookingOptionResponse(cart);

        logger.info("Add Booking Option response return successfully...");

        return addBookingOptionRS;
    }

    public ValidateCartRS validateCart(String partnerId, String cartId, ValidateCartRQ validateCartRQ, Cart cart) {

        logger.info("Start validate cart process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logData.put("VALIDATE_SCOPE", validateCartRQ.getValidateScope());
        logger.info("Validate cart Invoked. {}", logData);

        List<String> validateMessages = new ArrayList<String>();

        List<String> messsages = null;
        ValidateCartRS validateCartRS = null;

        messsages = new ArrayList<String>();
        validateCartRS = new ValidateCartRS();

        if (cart == null) {
            cart = getCart(cart, cartId, partnerId);
        }

        /* Booking Option validations */
        if (cart.getBookingOptions() == null || cart.getBookingOptions().isEmpty()) {
            validateMessages.add("Minimum one Booking option is required");
        } else {

            try {
                ValidateScope validateScope = ValidateScope.valueOf(validateCartRQ.getValidateScope().toUpperCase());
                switch (validateScope) {
                    case ALL:
                        validateMessages = validateAll(cart);
                        break;
                    case AVAILABILITY:
                        validateMessages = cart.getBookingOptions().stream()
                                .flatMap(bookingOption -> validateTotalAndAvailability(bookingOption).stream())
                                .collect(Collectors.toList());
                        break;
                }
            } catch (IllegalArgumentException iae) {
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_VALIDATE_SCOPE.toString(),
                        PlacePassExceptionCodes.INVALID_VALIDATE_SCOPE.getDescription());
            }
        }
        if (!validateMessages.isEmpty()) {
            messsages = validateMessages;
        } else {
            messsages.add("success");

        }

        validateCartRS.setValidateMessages(messsages);
        logger.info("Validate cart response return successfully...");

        return validateCartRS;
    }

    public UpdateBookerDetailsRS updateBookerDetails(String partnerId, String cartId,
            UpdateBookerDetailsRQ updateBookerDetailsRQ) {

        logger.info("Start update booker details process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logger.info("Update Booker Details Invoked. {}", logData);

        Cart cart = null;
        Cart updatedCart = null;
        UpdateBookerDetailsRS updateBookerDetailsRS = null;

        cart = getCart(cart, cartId, partnerId);

        // Validating Date of birth
        if (updateBookerDetailsRQ.getBookerRequest().getDateOfBirth() != null
                && !updateBookerDetailsRQ.getBookerRequest().getDateOfBirth().isEmpty()) {
            CartValidator.validateDateOfBirth(updateBookerDetailsRQ.getBookerRequest().getDateOfBirth());
        }

        updatedCart = CartRequestTransformer.toUpdateBookerdetails(cart, updateBookerDetailsRQ);
        cartService.updateCart(updatedCart);
        updateBookerDetailsRS = CartRequestTransformer.toUpdateBookerDetailsResponse(updatedCart);

        logger.info("Update Booker Details response return successfully...");

        return updateBookerDetailsRS;
    }

    public UpdateTravelerDetailsRS updateTravelerDetails(String partnerId, String cartId,
            UpdateTravelerDetailsRQ updateTravelerDetailsRQ) {

        logger.info("Start update traveler details process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logger.info("Update Traveler Details Invoked. {}", logData);

        UpdateTravelerDetailsRS updateTravelerDetailsRS = null;
        Cart savedCart = null;
        Cart updatedCart = null;
        BookingOption savedBookingOption = null;

        savedCart = getCart(savedCart, cartId, partnerId);

        for (BookingOption bookingOption : savedCart.getBookingOptions()) {

            if (bookingOption.getProductOptionId().equals(updateTravelerDetailsRQ.getProductOptionId())) {
                savedBookingOption = bookingOption;
            }
        }

        if (null == savedBookingOption && !savedCart.getBookingOptions().isEmpty()) {

            logger.error("Error due to, given product option id not match ");
            throw new BadRequestException(PlacePassExceptionCodes.PRODUCT_OPTION_ID_NOT_MATCH.toString(),
                    PlacePassExceptionCodes.PRODUCT_OPTION_ID_NOT_MATCH.getDescription());
        }

        // Validate traveler head count
        if (savedBookingOption.getQuantities() != null || !savedBookingOption.getQuantities().isEmpty()) {
            CartValidator.validateTravelerHeadCount(savedBookingOption.getQuantities(), updateTravelerDetailsRQ);
        }

        // Set up lead traveler
        if (updateTravelerDetailsRQ.getTravelers() != null || !updateTravelerDetailsRQ.getTravelers().isEmpty()) {
            CartValidator.setUpLeadTraveler(updateTravelerDetailsRQ.getTravelers());
        }

        for (TravelerDTO travelerDTO : updateTravelerDetailsRQ.getTravelers()) {

            // Validate Age band Id
            CartValidator.validateAgeBandId(travelerDTO.getAgeBandId());

            // validate lead traveler position against age band id (Lead traveler should be ADULT or SENIOR only)
            if (travelerDTO.isLeadTraveler()) {
                CartValidator.checkLeadTravelerAdultOrSenior(travelerDTO.getAgeBandId());
            }

            // Validate Date of birth
            if (travelerDTO.getDateOfBirth() != null && !travelerDTO.getDateOfBirth().isEmpty()) {
                CartValidator.validateDateOfBirth(travelerDTO.getDateOfBirth());
            }

            // Check Traveler Email validation and Email required only for lead traveler
            CartValidator.validateTravelerEmail(travelerDTO);

            // Check Traveler ISO country code validating
            CartValidator.validateTravelerISOCountryCode(travelerDTO);

            // Check Traveler phone Number validating
            CartValidator.validateTravelerPhoneNumber(travelerDTO);

        }

        // Checking Traveler specific age band count with cart quantity specific age band count
        CartValidator.validatingTravelerAgeBandsWithCartAgeBands(savedBookingOption.getQuantities(),
                updateTravelerDetailsRQ.getTravelers());

        updatedCart = CartRequestTransformer.toUpdateTravelerDetails(savedCart, updateTravelerDetailsRQ);
        cartService.updateCart(updatedCart);
        updateTravelerDetailsRS = CartRequestTransformer.toTravelerDetailsResponse(updatedCart);

        logger.info("Update Traveler Details response return successfully...");

        return updateTravelerDetailsRS;

    }

    public GetBookingQuestionsRS getBookingQuestions(String cartId, String partnerId) {

        logger.info("Start get booking question process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logger.info("Update Booking Questions Invoked. {}", logData);

        Cart cart = null;
        GetBookingQuestionsRS bookingQuestionsRS = null;
        List<BookingQuestion> bookingQuestions = null;
        List<BookingQuestionDTO> bookingQuestionList = null;
        VendorProduct vendorProduct = null;

        cart = getCart(cart, cartId, partnerId);

        if (cart.getBookingOptions() == null || cart.getBookingOptions().isEmpty()) {

            logger.error("Error due to booking options are not found/empty ");
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.getDescription());
        }

        for (BookingOption bookingOption : cart.getBookingOptions()) {

            if (bookingOption.getProductId() == null) {

                logger.error("Error due to product id not found ");
                throw new NotFoundException(PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.toString(),
                        PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.getDescription());
            }

            try {

                vendorProduct = VendorProduct.getInstance(bookingOption.getProductId(), productHashGenerator);

            } catch (Exception e) {

                logger.error("Exception occurred while dehash product id in ,getBooking question process : ", e);
                throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                        PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());
            }

            logger.info("Start Booking Questions invoking from connector side ");

            com.placepass.connector.common.cart.GetBookingQuestionsRS bookingQuestionsCRS = bookingService
                    .getBookingQuestions(vendorProduct.getVendorProductID(), vendorProduct.getVendor());

            logger.info("End Booking Questions invoking from connector side ");

            if (bookingQuestionsCRS.getResultType().getCode() != 1
                    && bookingQuestionsCRS.getResultType().getCode() != 0) {

                throw new BadRequestException("PRODUCT_NOT_AVAILABLE",
                        bookingQuestionsCRS.getResultType().getMessage());

            } else if (bookingQuestionsCRS.getResultType().getCode() == 1) {

                bookingQuestionsRS = new GetBookingQuestionsRS();
                bookingQuestionList = new ArrayList<BookingQuestionDTO>(0);

                bookingQuestionsRS.setBookingQuestions(bookingQuestionList);

            } else if (bookingQuestionsCRS.getResultType().getCode() == 0) {

                List<com.placepass.connector.common.cart.BookingQuestion> connectorBookingQuestions = bookingQuestionsCRS
                        .getBookingQuestions();

                bookingQuestionList = new ArrayList<BookingQuestionDTO>();
                bookingQuestionsRS = new GetBookingQuestionsRS();
                bookingQuestions = new ArrayList<BookingQuestion>();

                for (com.placepass.connector.common.cart.BookingQuestion bookingQuestionCDTO : connectorBookingQuestions) {

                    // Adding booking questions for DTO view purpose
                    BookingQuestionDTO bookingQuestionDTO = CartRequestTransformer.getBookingQuestionDTO(
                            bookingQuestionCDTO, bookingOption.getProductId(), bookingOption.getProductOptionId());
                    bookingQuestionList.add(bookingQuestionDTO);

                    // Adding booking questions for saving purpose to the cart object
                    BookingQuestion bookingQuestion = CartRequestTransformer.getBookingQuestion(bookingQuestionCDTO,
                            bookingOption.getProductOptionId());
                    bookingQuestions.add(bookingQuestion);

                    logData.put("QUESTION_ID", bookingQuestion.getQuestionId());

                }

                logData.put("PRODUCT_OPTION", bookingOption.getBookingOptionId());
                logger.info("Booking question ids for the product option {}", logData);

                bookingQuestionsRS.setBookingQuestions(bookingQuestionList);
                bookingOption.setBookingQuestions(bookingQuestions);

            }

            cartService.updateCart(cart);

        }

        logger.info("Get Booking questions response return successfully...");

        return bookingQuestionsRS;
    }

    public UpdateBookingAnswerRS updateBookingAnswers(UpdateBookingAnswerRQ updateBookingAnswerRQ, String cartId,
            String partnerId) {

        logger.info("Start update booking answer process...");

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logger.info("Update Booking Answers Invoked. {}", logData);

        UpdateBookingAnswerRS updateBookingAnswerRS = null;
        Cart cart = null;

        List<BookingOption> bookingOptions = new ArrayList<BookingOption>();

        cart = getCart(cart, cartId, partnerId);

        if (cart.getBookingOptions() == null || cart.getBookingOptions().isEmpty()) {

            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.getDescription());
        }

        for (BookingOption bookingOption : cart.getBookingOptions()) {

            if (bookingOption.getBookingQuestions() == null || bookingOption.getBookingQuestions().isEmpty()) {

                throw new BadRequestException(PlacePassExceptionCodes.BOOKING_QUESTIONS_NOT_AVAILABLE.toString(),
                        PlacePassExceptionCodes.BOOKING_QUESTIONS_NOT_AVAILABLE.getDescription());

            }

            for (BookingQuestion bookingQuestion : bookingOption.getBookingQuestions()) {

                boolean found = false;
                for (QuestionAnswerDTO questionAnswerDTO : updateBookingAnswerRQ.getQuestionAnswers()) {

                    if (questionAnswerDTO.getBookingQuestionId().equals(bookingQuestion.getQuestionId())
                            && questionAnswerDTO.getProductId().equals(bookingOption.getProductId())
                            && questionAnswerDTO.getProductOptionId().equals(bookingOption.getProductOptionId())) {

                        found = true;
                        BookingAnswer bookingAnswer = new BookingAnswer();
                        bookingAnswer.setQuestionId(bookingQuestion.getQuestionId());
                        bookingAnswer.setAnswer(questionAnswerDTO.getAnswer());
                        bookingQuestion.setBookingAnswer(bookingAnswer);
                        break;

                    }
                }

                if (!found) {
                    throw new BadRequestException(PlacePassExceptionCodes.BOOKING_QUESTION_NOT_FOUND.toString(),
                            PlacePassExceptionCodes.BOOKING_QUESTION_NOT_FOUND.getDescription());
                }

            }

            bookingOptions.add(bookingOption);
        }

        cart.setBookingOptions(bookingOptions);
        cartService.updateCart(cart);

        updateBookingAnswerRS = CartRequestTransformer.toUpdateBookingAnswerResponse(cart);

        logger.info("Update Booking Answer response return successfully...");

        return updateBookingAnswerRS;
    }

    public GetProductOptionsRS getProductOptionDetails(String vendorProductID, LocalDate bookingDate, Vendor vendor) {

        GetProductOptionsRS optionsCRS = productService.getProductOption(vendorProductID, bookingDate, vendor);

        logger.info("Get ResultType Code : {}", optionsCRS.getResultType().getCode());

        if (optionsCRS.getResultType().getCode() != 0) {

            throw new BadRequestException(PlacePassExceptionCodes.PRODUCT_NOT_AVAILABLE.toString(),
                    PlacePassExceptionCodes.PRODUCT_NOT_AVAILABLE.getDescription(),
                    optionsCRS.getResultType().getMessage());

        }

        return optionsCRS;
    }

    private Cart getCart(Cart cart, String cartId, String partnerId) {

        cart = cartService.getCart(cartId, partnerId);

        if (CartState.CLOSE == cart.getCartState()) {

            logger.error(PlacePassExceptionCodes.CART_CLOSED.getDescription());
            throw new BadRequestException(PlacePassExceptionCodes.CART_CLOSED.toString(),
                    PlacePassExceptionCodes.CART_CLOSED.getDescription());

        }

        return cart;

    }

    public static boolean isHeadCountExceeded(BookingOptionDTO bookingOption) {

        int headCount = 0;
        boolean isExceeded = false;

        for (QuantityDTO quantityDTO : bookingOption.getQuantities()) {
            if (quantityDTO.getQuantity() > 0) {
                headCount = (headCount + quantityDTO.getQuantity());
            }
        }

        if (headCount > 9) {
            isExceeded = true;
        }

        return isExceeded;
    }

    private void quantityValidator(BookingOptionDTO bookingOptionDTO, ProductOption conProductOption,
            ProductHashGenerator productHashGenerator) {

        // Comparing connector side Age band label and Requesting age band labels
        if (!isAgeBandFound(bookingOptionDTO.getQuantities(), conProductOption.getPrices())) {

            logger.error("Error occured due to, connector side age band lables and requested labels are mismatched.");
            throw new BadRequestException(PlacePassExceptionCodes.REQUEST_INVALID_AGE_BAND_ID.toString(),
                    PlacePassExceptionCodes.REQUEST_INVALID_AGE_BAND_ID.getDescription());

        }

        VendorProduct vendorProduct = VendorProduct.getInstance(bookingOptionDTO.getProductId(), productHashGenerator);

        validateQuantities(bookingOptionDTO, conProductOption);

    }

    /**
     * This is for validate UA quantities in "create cart" and "Add booking option" level. We cannot re-use central
     * validation method because it use domain Booking Option object.Here we are using DTO Booking option object
     */
    public static void validateUAQuantities(BookingOptionDTO requestedBookingOption,
            ProductOption connectorProductOption) {

        int totalHeadCount = 0;

        for (QuantityDTO quantity : requestedBookingOption.getQuantities()) {

            if (PlacePassAgeBandType.ADULT.name().equals(quantity.getAgeBandLabel())
                    || PlacePassAgeBandType.CHILD.name().equals(quantity.getAgeBandLabel())
                    || PlacePassAgeBandType.SENIOR.name().equals(quantity.getAgeBandLabel())
                    || PlacePassAgeBandType.INFANT.name().equals(quantity.getAgeBandLabel())
                    || PlacePassAgeBandType.YOUTH.name().equals(quantity.getAgeBandLabel())) {

                totalHeadCount = totalHeadCount + quantity.getQuantity();
            }
        }

        if (connectorProductOption.getAvailability() < totalHeadCount) {

            logger.error("Total head count should be less than available product option quantity {} ",
                    connectorProductOption.getAvailability());
            throw new BadRequestException("TOTAL_HEAD_COUNT_EXCEEDED_AVAILABILITY",
                    "Total head count should be less than available product option quantity : "
                            + connectorProductOption.getAvailability());

        }

        // Best price match logic
        boolean isPriceQuantityValid = CartRequestTransformer.priceQuantityMatching(connectorProductOption.getPrices(),
                requestedBookingOption.getQuantities());

        if (!isPriceQuantityValid) {
            logger.error("Error occured due to, price not found for the given quantities.");
            throw new BadRequestException(PlacePassExceptionCodes.PRICE_NOT_FOUND_FOR_PROVIDED_QUANTITIES.toString(),
                    PlacePassExceptionCodes.PRICE_NOT_FOUND_FOR_PROVIDED_QUANTITIES.getDescription());
        }

    }

    /**
     * This is for validate VIATOR/BEMYGUEST quantities in "create cart" and "Add booking option" level. We cannot
     * re-use central validation method because it use domain Booking Option object.Here we are using DTO Booking option
     * object
     */
    public static void validateQuantities(BookingOptionDTO requestedBookingOption,
            ProductOption connectorProductOption) {

        if (connectorProductOption.getAvailability() != -1) {

            logger.error("This product has no availabilities");
            throw new BadRequestException("NO_AVAILABILITIES", "This product has no availabilities");

        }

        // Best price match logic
        boolean isPriceQuantityValid = CartRequestTransformer.priceQuantityMatching(connectorProductOption.getPrices(),
                requestedBookingOption.getQuantities());

        if (!isPriceQuantityValid) {
            logger.error("Error occured due to, price not found for the given quantities.");
            throw new BadRequestException(PlacePassExceptionCodes.PRICE_NOT_FOUND_FOR_PROVIDED_QUANTITIES.toString(),
                    PlacePassExceptionCodes.PRICE_NOT_FOUND_FOR_PROVIDED_QUANTITIES.getDescription());
        }
    }

    /* Comparing connector side Age band label and Requesting age band labels */
    public static boolean isAgeBandFound(List<QuantityDTO> quantities, List<Price> prices) {

        boolean isValid = true;

        Map<Integer, Integer> qtyCountWithAgeBand = new HashMap<>();

        for (QuantityDTO ppQuantity : quantities) {

            int reqAgeBand = 0;

            if (ppQuantity.getAgeBandLabel().equals((PlacePassAgeBandType.ADULT.toString()))) {
                reqAgeBand = PlacePassAgeBandType.ADULT.getAgeBandId();
            }

            if (ppQuantity.getAgeBandLabel().equals((PlacePassAgeBandType.SENIOR.toString()))) {
                reqAgeBand = PlacePassAgeBandType.SENIOR.getAgeBandId();
            }

            if (ppQuantity.getAgeBandLabel().equals((PlacePassAgeBandType.CHILD.toString()))) {
                reqAgeBand = PlacePassAgeBandType.CHILD.getAgeBandId();
            }

            if (ppQuantity.getAgeBandLabel().equals((PlacePassAgeBandType.INFANT.toString()))) {
                reqAgeBand = PlacePassAgeBandType.INFANT.getAgeBandId();
            }

            if (ppQuantity.getAgeBandLabel().equals((PlacePassAgeBandType.YOUTH.toString()))) {
                reqAgeBand = PlacePassAgeBandType.YOUTH.getAgeBandId();
            }

            qtyCountWithAgeBand.put(reqAgeBand, 1);

            for (Price priceDTO : prices) {
                if (priceDTO.getAgeBandId() == reqAgeBand) {
                    qtyCountWithAgeBand.put(reqAgeBand, 2);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : qtyCountWithAgeBand.entrySet()) {
            if (1 == entry.getValue()) {
                isValid = false;
            }
        }

        return isValid;
    }

    public UpdateHotelPickupRS updateHotelPickupLocation(UpdateHotelPickupRQ updateHotelPickupRQ, String cartId,
            String bookingOptionId, String partnerId) {

        logger.info("Start update hotel pickup location process...");

        Map<String, Object> logData = new HashMap<>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logger.info("Update hotel pickup location Invoked. {}", logData);

        UpdateHotelPickupRS updateHotelPickupRS = null;
        Cart cart = null;

        validatePickupLocation(updateHotelPickupRQ.getPickupLocation());

        cart = getCart(cart, cartId, partnerId);

        if (cart.getBookingOptions() == null || cart.getBookingOptions().isEmpty()) {

            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.getDescription());
        }

        for (BookingOption bookingOption : cart.getBookingOptions()) {

            if (!bookingOption.getProductOptionId().equals(bookingOptionId)) {

                logger.error("Error due to, given product option id not match ");
                throw new BadRequestException(PlacePassExceptionCodes.PRODUCT_OPTION_ID_NOT_MATCH.toString(),
                        PlacePassExceptionCodes.PRODUCT_OPTION_ID_NOT_MATCH.getDescription());
            }

            bookingOption.setPickupLocation(
                    CartRequestTransformer.toPickupLocation(updateHotelPickupRQ.getPickupLocation()));

        }

        cartService.updateCart(cart);

        updateHotelPickupRS = CartRequestTransformer.toUpdateHotelPickupLocationResponse(cart);

        logger.info("Update hotel pickup location response return successfully...");
        return updateHotelPickupRS;
    }

    // Ignore zero quantity age bands from quantity
    public static BookingOptionDTO ignoreZeroQuantities(BookingOptionDTO bookingOptionDTO) {

        BookingOptionDTO bkOptionDTO = bookingOptionDTO;
        List<QuantityDTO> quantities = new ArrayList<>();

        for (QuantityDTO quantity : bkOptionDTO.getQuantities()) {

            if (quantity.getQuantity() != 0) {
                quantities.add(quantity);
            }
        }

        bkOptionDTO.setQuantities(quantities);

        return bkOptionDTO;
    }

    public UpdateLanguageOptionRS updateLanguageOptionCode(UpdateLanguageOptionRQ updateLanguageOptionRQ, String cartId,
            String bookingOptionId, String partnerId) {

        logger.info("Start update language option code process...");

        Map<String, Object> logData = new HashMap<>();
        logData.put("PARTNER_ID", partnerId);
        logData.put("CART_ID", cartId);
        logger.info("Update language option code invoked. {}", logData);

        UpdateLanguageOptionRS updateLanguageOptionRS = null;
        Cart cart = null;

        cart = getCart(cart, cartId, partnerId);

        if (cart.getBookingOptions() == null || cart.getBookingOptions().isEmpty()) {

            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_OPTION_NOT_FOUND.getDescription());
        }

        for (BookingOption bookingOption : cart.getBookingOptions()) {

            if (!bookingOption.getProductOptionId().equals(bookingOptionId)) {

                logger.error("Error due to, given product option id not match ");
                throw new BadRequestException(PlacePassExceptionCodes.PRODUCT_OPTION_ID_NOT_MATCH.toString(),
                        PlacePassExceptionCodes.PRODUCT_OPTION_ID_NOT_MATCH.getDescription());
            }

            bookingOption.setLanguageOptionCode(updateLanguageOptionRQ.getLanguageOptionCode());

        }

        cartService.updateCart(cart);

        updateLanguageOptionRS = CartRequestTransformer.toUpdateLanguageOptionCodeResponse(cart);

        logger.info("Update language option code response return successfully...");

        return updateLanguageOptionRS;
    }

    private void validatePickupLocation(PickupLocationDTO pickupLocation) {
        if (!StringUtils.isEmpty(pickupLocation.getId()) && StringUtils.isEmpty(pickupLocation.getLocationName())) {
            logger.error("Error due to, pickup location name is not given");
            throw new BadRequestException(PlacePassExceptionCodes.PICKUP_LOCATION_NAME_REQUIRED.toString(),
                    PlacePassExceptionCodes.PICKUP_LOCATION_NAME_REQUIRED.getDescription());
        } else if (StringUtils.isEmpty(pickupLocation.getId())
                && !StringUtils.isEmpty(pickupLocation.getLocationName())) {
            logger.error("Error due to, pickup location id is not given");
            throw new BadRequestException(PlacePassExceptionCodes.PICKUP_LOCATION_ID_REQUIRED.toString(),
                    PlacePassExceptionCodes.PICKUP_LOCATION_ID_REQUIRED.getDescription());
        }
    }

    public List<String> validateTotalAndAvailability(BookingOption bookingOption) {

        List<String> messages = new ArrayList<String>();

        // To validate cart total with connector side total
        GetProductPriceRQ productPriceCRQ = CartConnectorTransformer.toGetProductPriceCRQ(bookingOption);
        productPriceCRQ.setVendorValidation(true);

        logger.info("Start price details invoking from connector side ");

        GetProductPriceRS productPriceCRS = productService.getProductPrice(productPriceCRQ,
                Vendor.getVendor(bookingOption.getVendor()));

        logger.info("End price details invoking from connector side ");

        if (productPriceCRS.getResultType().getCode() != 0) {

            logger.error("Error while getting price details with invalid details ");
            throw new NotFoundException("PRODUCT_NOT_AVAILABLE", productPriceCRS.getResultType().getMessage());

        }
        messages = CartRequestTransformer.validateBookingOptionPriceTotalDetails(productPriceCRS, bookingOption,
                messages);

        return messages;
    }

    public List<String> validateAll(Cart cart) {

        List<String> messages = new ArrayList<String>();

        /* Booker details validations */
        if (cart.getBookerDetails() == null) {
            messages.add("Booker details is mandatory");
        } else {
            messages = CartRequestTransformer.validateBookerDetails(cart.getBookerDetails());
        }

        for (BookingOption requestBookingoption : cart.getBookingOptions()) {

            // To validate Booking questions
            if (requestBookingoption.getBookingQuestions() == null
                    || requestBookingoption.getBookingQuestions().isEmpty()) {

                logger.info("Start getting Booking Questions invokeing from connector side ");

                com.placepass.connector.common.cart.GetBookingQuestionsRS bookingQuestionsCRS = bookingService
                        .getBookingQuestions(requestBookingoption.getVendorProductId(),
                                Vendor.getVendor(requestBookingoption.getVendor()));

                logger.info("End getting Booking Questions invokeing from connector side ");

                if (bookingQuestionsCRS.getResultType().getCode() == 0) {
                    if (bookingQuestionsCRS.getBookingQuestions() != null) {
                        for (com.placepass.connector.common.cart.BookingQuestion bookingQuestion : bookingQuestionsCRS.getBookingQuestions()) {
                            if (bookingQuestion.getRequired() != null && bookingQuestion.getRequired()) {
                                messages.add("This product has questions.Please provide the answers");
                                break;
                            }
                        }
                    }
                }
            }

            // To validate Booking questions and answers
            boolean isAnswersEmpty = false;
            if (requestBookingoption.getBookingQuestions() != null) {
                for (BookingQuestion question : requestBookingoption.getBookingQuestions()) {

                    if (question.getRequired() != null && question.getRequired()) {
                        if ((question.getBookingAnswer() == null || question.getBookingAnswer().equals(""))
                                && question != null) {

                            isAnswersEmpty = true;

                        }
                    }
                }
            }
            if (isAnswersEmpty) {
                messages.add("This product has questions.Please provide the answers");
            }

            // validate total and availability
            messages.addAll(validateTotalAndAvailability(requestBookingoption));

            logger.info("Start product details invoking from connector side ");
            // To validate cart quantity with connector side quantity
            GetProductOptionsRS productOptionsCRS = productService.getProductOption(
                    requestBookingoption.getVendorProductId(), requestBookingoption.getBookingDate(),
                    Vendor.getVendor(requestBookingoption.getVendor()));

            logger.info("End product details invoking from connector side ");

            if (productOptionsCRS.getResultType().getCode() != 0) {

                logger.error("Error while getting Product option with invalid details ");
                throw new NotFoundException("PRODUCT_NOT_AVAILABLE", productOptionsCRS.getResultType().getMessage());

            }

            List<ProductOption> productOptions = productOptionsCRS.getProductOptionGroup().getProductOptions();
            for (ProductOption connectorProductOption : productOptions) {

                if (requestBookingoption.getVendorProductOptionId()
                        .equals(connectorProductOption.getProductOptionId())) {
                    messages = CartRequestTransformer.validateBookingOptionGeneralDetails(connectorProductOption,
                            requestBookingoption, messages);
                }

            }

        }
        return messages;
    }

}
