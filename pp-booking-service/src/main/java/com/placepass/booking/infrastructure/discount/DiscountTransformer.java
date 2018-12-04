package com.placepass.booking.infrastructure.discount;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.placepass.booking.domain.booking.Booker;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.Cart;
import com.placepass.booking.domain.booking.Discount;
import com.placepass.booking.domain.booking.Fee;
import com.placepass.booking.domain.booking.FeeCategory;
import com.placepass.booking.domain.booking.FeeType;
import com.placepass.booking.domain.booking.LoyaltyDetail;
import com.placepass.booking.domain.booking.PickupLocation;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.booking.domain.booking.Total;
import com.placepass.booking.domain.booking.Traveler;

/**
 * The Class DiscountTransformer.
 */
public class DiscountTransformer {

    public static DiscountFeeRQ transformDiscountFeeRQ(Cart cart) {

        CartDTO cartDTO = constructCartDTO(cart);

        DiscountFeeRQ discountFeeRQ = new DiscountFeeRQ();
        discountFeeRQ.setCart(cartDTO);
        return discountFeeRQ;
    }
    
    public static RedeemDiscountRQ transformRedeemDiscountRQ(Cart cart, List<Discount> discounts, Booking booking) {
        RedeemDiscountRQ redeemDiscountRQ = new RedeemDiscountRQ();
        for (Discount discount : discounts) {
            DiscountDTO discountDTO = new DiscountDTO(discount.getDiscountCode());
            redeemDiscountRQ.getDiscounts().add(discountDTO);
        }

        CartDTO cartDTO = constructCartDTO(cart);
        redeemDiscountRQ.setCart(cartDTO);

        if (booking != null) {
            redeemDiscountRQ.setBookingId(booking.getId());
            redeemDiscountRQ.setBookingReference(booking.getBookingReference());
            redeemDiscountRQ.setCustomerId(booking.getCustomerId());
            redeemDiscountRQ.setVendorBookingRefId(booking.getVendorBookingRefId());
        }

        return redeemDiscountRQ;
    }

    private static BookingDTO constructBookingDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(booking.getId());
        bookingDTO.setBookingReference(booking.getBookingReference());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setVendorBookingRefId(booking.getVendorBookingRefId());

        return bookingDTO;
    }

    private static CartDTO constructCartDTO(Cart cart) {
        if (cart == null) {
            return null;
        }

        CartDTO cartDTO = new CartDTO(cart.getCartId(), constructBookerDetailDTOs(cart.getBookerDetails()),
                constructBookingOptionDTOs(cart.getBookingOptions()), constructTotal(cart.getTotal()));

        if (!cart.getFees().isEmpty()) {
            for (Fee fee : cart.getFees()) {
                String type = null;
                String category = null;
                
                if (fee.getFeeType() != null) {
                    type = fee.getFeeType().name();
                }
                
                if (fee.getFeeCategory() != null) {
                    category = fee.getFeeCategory().name();
                }
                
                FeeDTO feeDTO = new FeeDTO(type, category, fee.getDescription(), fee.getCurrency(), fee.getAmount());
                cartDTO.getFees().add(feeDTO);
            }

        }

        return cartDTO;
    }

    private static BookerDTO constructBookerDetailDTOs(Booker bookerDetails) {

        if (bookerDetails == null) {
            return null;
        }

        BookerDTO bookerDTO = new BookerDTO();
        bookerDTO.setCountryISOCode(bookerDetails.getCountryISOCode());
        bookerDTO.setDateOfBirth(bookerDetails.getDateOfBirth());
        bookerDTO.setEmail(bookerDetails.getEmail());
        bookerDTO.setFirstName(bookerDetails.getFirstName());
        bookerDTO.setLastName(bookerDetails.getLastName());
        bookerDTO.setPhoneNumber(bookerDetails.getPhoneNumber());
        bookerDTO.setTitle(bookerDetails.getTitle());

        return bookerDTO;
    }

    private static List<BookingOptionDTO> constructBookingOptionDTOs(List<BookingOption> bookingOptions) {

        if (bookingOptions == null) {
            return null;
        }

        List<BookingOptionDTO> bookingOptionDTOs = new ArrayList<BookingOptionDTO>();
        for (BookingOption bookingOption : bookingOptions) {
            BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
            bookingOptionDTO.setBookingDate(bookingOption.getBookingDate());
            bookingOptionDTO.setBookingOptionId(bookingOption.getBookingOptionId());
            bookingOptionDTO.setDescription(bookingOption.getDescription());
            bookingOptionDTO.setEndTime(bookingOption.getEndTime());
            bookingOptionDTO.setImageUrl(bookingOption.getImageUrl());
            bookingOptionDTO.setIsHotelPickUpEligible(bookingOption.getIsHotelPickUpEligible());
            bookingOptionDTO.setLanguageOptionCode(bookingOption.getLanguageOptionCode());
            bookingOptionDTO.setLoyaltyDetails(constructLoyaltyDetails(bookingOption.getLoyaltyDetails()));
            bookingOptionDTO.setMetadata(bookingOption.getMetadata());
            bookingOptionDTO.setPickupLocation(constructPickupLocation(bookingOption.getPickupLocation()));
            bookingOptionDTO.setPrices(bookingOption.getPrices());
            bookingOptionDTO.setProductDetails(bookingOption.getProductDetails());
            bookingOptionDTO.setProductId(bookingOption.getProductId());
            bookingOptionDTO.setProductOptionId(bookingOption.getProductOptionId());
            bookingOptionDTO.setQuantities(constructQuantities(bookingOption.getQuantities()));
            bookingOptionDTO.setStartTime(bookingOption.getStartTime());
            bookingOptionDTO.setTitle(bookingOption.getTitle());
            bookingOptionDTO.setTotal(constructTotal(bookingOption.getTotal()));
            bookingOptionDTO.setTraverlerDetails(constructTraverlerDetails(bookingOption.getTraverlerDetails()));
            bookingOptionDTO.setVendor(bookingOption.getVendor());
            bookingOptionDTO.setVendorProductId(bookingOption.getVendorProductId());
            bookingOptionDTO.setVendorProductOptionId(bookingOption.getVendorProductOptionId());
            bookingOptionDTOs.add(bookingOptionDTO);
        }

        return bookingOptionDTOs;
    }

    private static List<TravelerDTO> constructTraverlerDetails(List<Traveler> traverlerDetails) {

        if (traverlerDetails == null) {
            return null;
        }

        List<TravelerDTO> travelerDTOs = new ArrayList<TravelerDTO>();

        for (Traveler traveler : traverlerDetails) {
            TravelerDTO travelerDTO = new TravelerDTO();
            travelerDTO.setAgeBandId(traveler.getAgeBandId());
            travelerDTO.setAgeBandLabel(traveler.getAgeBandLabel());
            travelerDTO.setCountryISOCode(traveler.getCountryISOCode());
            travelerDTO.setDateOfBirth(traveler.getDateOfBirth());
            travelerDTO.setEmail(traveler.getEmail());
            travelerDTO.setFirstName(traveler.getFirstName());
            travelerDTO.setLastName(traveler.getLastName());
            travelerDTO.setLeadTraveler(traveler.isLeadTraveler());
            travelerDTO.setPhoneNumber(traveler.getPhoneNumber());
            travelerDTO.setTitle(traveler.getTitle());
            travelerDTOs.add(travelerDTO);
        }

        return travelerDTOs;
    }

    private static TotalDTO constructTotal(Total total) {

        if (total == null) {
            return null;
        }

        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setCurrency(total.getCurrency());
        totalDTO.setCurrencyCode(total.getCurrencyCode());
        totalDTO.setDiscountAmount(total.getDiscountAmount());
        totalDTO.setFinalTotal(total.getFinalTotal());
        totalDTO.setMerchantTotal(total.getMerchantTotal());
        totalDTO.setOriginalTotal(total.getOriginalTotal());
        totalDTO.setRetailTotal(total.getRetailTotal());
        totalDTO.setRoundedFinalTotal(total.getRoundedFinalTotal());
        totalDTO.setTotalAfterDiscount(total.getTotalAfterDiscount());

        return totalDTO;
    }

    private static List<QuantityDTO> constructQuantities(List<Quantity> quantities) {

        if (quantities == null) {
            return null;
        }

        List<QuantityDTO> quantityDTOs = new ArrayList<QuantityDTO>();

        for (Quantity quantity : quantities) {
            QuantityDTO quantityDTO = new QuantityDTO();
            quantityDTO.setAgeBandId(quantity.getAgeBandId());
            quantityDTO.setAgeBandLabel(quantity.getAgeBandLabel());
            quantityDTO.setQuantity(quantity.getQuantity());

            quantityDTOs.add(quantityDTO);
        }

        return quantityDTOs;
    }

    private static PickupLocationDTO constructPickupLocation(PickupLocation pickupLocation) {

        if (pickupLocation == null) {
            return null;
        }

        PickupLocationDTO pickupLocationDTO = new PickupLocationDTO();
        pickupLocationDTO.setId(pickupLocation.getId());
        pickupLocationDTO.setLocationName(pickupLocation.getLocationName());

        return pickupLocationDTO;
    }

    private static List<LoyaltyDetailDTO> constructLoyaltyDetails(List<LoyaltyDetail> loyaltyDetails) {

        if (loyaltyDetails == null) {
            return null;
        }

        List<LoyaltyDetailDTO> loyaltyDetailDTOs = new ArrayList<LoyaltyDetailDTO>();

        for (LoyaltyDetail loyaltyDetail : loyaltyDetails) {
            LoyaltyDetailDTO loyaltyDetailDTO = new LoyaltyDetailDTO();
            loyaltyDetailDTO.setLoyaltyPoints(loyaltyDetail.getLoyaltyPoints());
            loyaltyDetailDTO.setLoyaltyProgramDisplayName(loyaltyDetail.getLoyaltyProgramDisplayName());
            loyaltyDetailDTO.setLoyaltyProgramId(loyaltyDetail.getLoyaltyProgramId());
            loyaltyDetailDTOs.add(loyaltyDetailDTO);
        }

        return loyaltyDetailDTOs;
    }

    public static List<Discount> transformDiscount(List<com.placepass.booking.application.booking.dto.DiscountDTO> discountDTOs) {
        
        if (discountDTOs == null) {
            return null;
        }
        
        List<Discount> discounts = new ArrayList<Discount>();
        for (com.placepass.booking.application.booking.dto.DiscountDTO discountDTO : discountDTOs) {
            Discount discount = new Discount(discountDTO.getDiscountCode());
            discounts.add(discount);
        }
        
        return discounts;
    }
    
    public static Fee constructFee(FeeDTO feeDTO) {
        FeeType feeType = null;
        FeeCategory feeCategory = null;

        if (StringUtils.hasText(feeDTO.getType())) {
            feeType = FeeType.valueOf(feeDTO.getType());
        }

        if (StringUtils.hasText(feeDTO.getCategory())) {
            feeCategory = FeeCategory.valueOf(feeDTO.getCategory());
        }

        Fee fee = new Fee(feeType, feeCategory, feeDTO.getDescription(), feeDTO.getCurrency(),
                feeDTO.getAmount());
        return fee;
    }


}
