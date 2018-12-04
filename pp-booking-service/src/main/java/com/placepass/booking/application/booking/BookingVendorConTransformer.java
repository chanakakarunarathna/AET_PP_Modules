package com.placepass.booking.application.booking;

import java.util.ArrayList;
import java.util.List;

import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.booking.application.common.BookingServiceUtil;
import com.placepass.booking.domain.booking.Booker;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.BookingAnswer;
import com.placepass.booking.domain.booking.BookingItem;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.BookingQuestion;
import com.placepass.booking.domain.booking.PickupLocation;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.booking.domain.booking.Total;
import com.placepass.booking.domain.booking.Traveler;
import com.placepass.booking.domain.booking.cancel.BookingCancellationType;

public class BookingVendorConTransformer {

    public static MakeBookingRQ toMakeBookingRQ(Booking booking) {
        MakeBookingRQ bookingConRQ = new MakeBookingRQ();
        bookingConRQ.setBookerDetails(toBookerCDTO(booking.getBookerDetails()));
        bookingConRQ.setBookingOptions(toBookingOptionCDTO(booking.getBookingOptions()));
        bookingConRQ.setBookingId(booking.getId());
        bookingConRQ.setTotal(toTotalCDTO(booking.getTotal()));
        return bookingConRQ;
    }

    public static CancelBookingRQ toCancelBookingCRQ(Booking booking, String cancellationReasonCode) {

        CancelBookingRQ cancelBookingCRQ = new CancelBookingRQ();
        cancelBookingCRQ.setBookingReferenceId(booking.getVendorBookingRefId());
        cancelBookingCRQ.setBookingId(booking.getId());
        cancelBookingCRQ.setCancelDescription("Cancellation by admin");
        cancelBookingCRQ.setBookingItems(toBookingItemCDTOs(booking.getBookingItems()));
        cancelBookingCRQ.setCancellationReasonCode(cancellationReasonCode);
        cancelBookingCRQ.setBookingStatus(booking.getBookingState().name());
        cancelBookingCRQ.setCancelationType(BookingCancellationType.GENERAL.name());
        return cancelBookingCRQ;
    }

    public static List<com.placepass.connector.common.booking.BookingItem> toBookingItemCDTOs(
            List<BookingItem> bookingItems) {
        List<com.placepass.connector.common.booking.BookingItem> bookingItemCDTOs = null;
        if (bookingItems != null) {
            bookingItemCDTOs = new ArrayList<>();
            for (BookingItem bookingItem : bookingItems) {
                com.placepass.connector.common.booking.BookingItem bookingItemCDTO = new com.placepass.connector.common.booking.BookingItem();
                bookingItemCDTO.setCancellable(bookingItem.isCancellable());
                bookingItemCDTO.setItemId(bookingItem.getItemId());
                bookingItemCDTO.setItemRef(bookingItem.getItemRef());
                bookingItemCDTOs.add(bookingItemCDTO);
            }
        }
        return bookingItemCDTOs;
    }

    public static com.placepass.connector.common.booking.Booker toBookerCDTO(Booker bookerDetails) {

        com.placepass.connector.common.booking.Booker bookerCDTO = new com.placepass.connector.common.booking.Booker();
        bookerCDTO.setTitle(bookerDetails.getTitle());
        bookerCDTO.setFirstName(bookerDetails.getFirstName());
        bookerCDTO.setLastName(bookerDetails.getLastName());
        bookerCDTO.setEmail(bookerDetails.getEmail());
        bookerCDTO.setPhoneNumber(bookerDetails.getPhoneNumber());
        bookerCDTO.setCountryCode(BookingServiceUtil.getCountryCallingCode(bookerDetails.getCountryISOCode(),
                bookerDetails.getPhoneNumber()));
        bookerCDTO.setDateOfBirth(bookerDetails.getDateOfBirth());

        return bookerCDTO;
    }

    public static List<com.placepass.connector.common.booking.BookingOption> toBookingOptionCDTO(
            List<BookingOption> bookingOptions) {
        List<com.placepass.connector.common.booking.BookingOption> bookingOptionCDTOs = null;

        if (bookingOptions != null) {
            bookingOptionCDTOs = new ArrayList<>();
            for (BookingOption bookingOption : bookingOptions) {
                com.placepass.connector.common.booking.BookingOption bookingOptionCDTO = new com.placepass.connector.common.booking.BookingOption();

                bookingOptionCDTO.setVendor(bookingOption.getVendor());
                bookingOptionCDTO.setBookingOptionId(bookingOption.getBookingOptionId());
                bookingOptionCDTO.setProductId(bookingOption.getProductId());
                bookingOptionCDTO.setProductOptionId(bookingOption.getProductOptionId());
                bookingOptionCDTO.setVendorProductId(bookingOption.getVendorProductId());
                bookingOptionCDTO.setVendorProductOptionId(bookingOption.getVendorProductOptionId());
                bookingOptionCDTO.setQuantities(toQuantityCDTO(bookingOption.getQuantities()));
                bookingOptionCDTO.setTotal(toTotalCDTO(bookingOption.getTotal()));
                bookingOptionCDTO.setTraverlerDetails(toTravelerCDTO(bookingOption.getTraverlerDetails()));
                bookingOptionCDTO.setIsHotelPickUpEligible(bookingOption.getIsHotelPickUpEligible());
                if (bookingOption.getPickupLocation() != null) {
                    bookingOptionCDTO.setPickupLocation(toPickupLocationCDTO(bookingOption.getPickupLocation()));
                }

                List<com.placepass.connector.common.booking.BookingAnswer> bookingAnswerCDTOs = null;
                if (bookingOption.getBookingQuestions() != null) {
                    bookingAnswerCDTOs = new ArrayList<com.placepass.connector.common.booking.BookingAnswer>();
                    for (BookingQuestion bookingQuestion : bookingOption.getBookingQuestions())
                        bookingAnswerCDTOs.add(toBookingAnswerCDTO(bookingQuestion.getBookingAnswer()));
                }
                bookingOptionCDTO.setBookingAnswers(bookingAnswerCDTOs);
                bookingOptionCDTO.setBookingDate(bookingOption.getBookingDate());
                bookingOptionCDTO.setLanguageOptionCode(bookingOption.getLanguageOptionCode());
                bookingOptionCDTO.setMetadata(bookingOption.getMetadata());
                bookingOptionCDTOs.add(bookingOptionCDTO);

            }
        }
        return bookingOptionCDTOs;
    }

    private static List<com.placepass.connector.common.booking.Traveler> toTravelerCDTO(
            List<Traveler> traverlerDetails) {

        List<com.placepass.connector.common.booking.Traveler> travelerCDTOs = null;
        if (traverlerDetails != null) {
            travelerCDTOs = new ArrayList<>();
            for (Traveler traveler : traverlerDetails) {
                com.placepass.connector.common.booking.Traveler travelerCDTO = new com.placepass.connector.common.booking.Traveler();
                travelerCDTO.setTitle(traveler.getTitle());
                travelerCDTO.setFirstName(traveler.getFirstName());
                travelerCDTO.setLastName(traveler.getLastName());
                travelerCDTO.setEmail(traveler.getEmail());
                travelerCDTO.setPhoneNumber(traveler.getPhoneNumber());
                travelerCDTO.setDateOfBirth(traveler.getDateOfBirth());
                travelerCDTO.setCountryCode(BookingServiceUtil.getCountryCallingCode(traveler.getCountryISOCode(),
                        traveler.getPhoneNumber()));
                travelerCDTO.setAgeBandId(traveler.getAgeBandId());
                travelerCDTO.setAgeBandLabel(traveler.getAgeBandLabel());
                travelerCDTO.setLeadTraveler(traveler.isLeadTraveler());
                travelerCDTOs.add(travelerCDTO);
            }
        }
        return travelerCDTOs;
    }

    private static List<com.placepass.connector.common.booking.Quantity> toQuantityCDTO(List<Quantity> quantities) {
        List<com.placepass.connector.common.booking.Quantity> quantityCDTOs = null;
        if (quantities != null) {
            quantityCDTOs = new ArrayList<>();
            for (Quantity quantity : quantities) {
                com.placepass.connector.common.booking.Quantity quantityCDTO = new com.placepass.connector.common.booking.Quantity();
                quantityCDTO.setAgeBandId(quantity.getAgeBandId());
                quantityCDTO.setAgeBandLabel(quantity.getAgeBandLabel());
                quantityCDTO.setQuantity(quantity.getQuantity());
                quantityCDTOs.add(quantityCDTO);
            }
        }
        return quantityCDTOs;
    }

    private static com.placepass.connector.common.booking.BookingAnswer toBookingAnswerCDTO(
            BookingAnswer bookingAnswer) {

        com.placepass.connector.common.booking.BookingAnswer bookingAnswerCDTO = new com.placepass.connector.common.booking.BookingAnswer();
        bookingAnswerCDTO.setQuestionId(bookingAnswer.getQuestionId());
        bookingAnswerCDTO.setAnswer(bookingAnswer.getAnswer());
        return bookingAnswerCDTO;
    }

    private static com.placepass.connector.common.booking.PickupLocation toPickupLocationCDTO(
            PickupLocation pickupLocation) {

        com.placepass.connector.common.booking.PickupLocation pickupLocationCDTO = new com.placepass.connector.common.booking.PickupLocation();
        pickupLocationCDTO.setId(pickupLocation.getId());
        pickupLocationCDTO.setLocationName(pickupLocation.getLocationName());
        return pickupLocationCDTO;
    }

    public static com.placepass.connector.common.booking.Total toTotalCDTO(Total total) {

        com.placepass.connector.common.booking.Total totalCDTO = new com.placepass.connector.common.booking.Total();
        totalCDTO.setCurrency(total.getCurrency());
        totalCDTO.setCurrencyCode(total.getCurrencyCode());
        totalCDTO.setFinalTotal(total.getFinalTotal());
        totalCDTO.setMerchantTotal(total.getMerchantTotal());
        totalCDTO.setRetailTotal(total.getRetailTotal());
        return totalCDTO;
    }

}
