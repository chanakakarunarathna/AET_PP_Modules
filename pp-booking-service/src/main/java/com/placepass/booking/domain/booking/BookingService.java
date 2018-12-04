package com.placepass.booking.domain.booking;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.placepass.booking.application.common.PaginationParamsValidator;
import com.placepass.booking.infrastructure.CryptoService;
import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.operator.SearchOperatorType;
import com.placepass.utils.vendorproduct.Vendor;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MongoOperations mongoOperations;
    
    @Autowired
    private CryptoService cryptoService;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_START_TIME = "00:00:00";

    private static final String DATE_END_TIME = "23:59:59";

    private static final String REGEX_START = "^";

    private static final String REGEX_END = "$";

    public Booking getBooking(String bookingId, String customerId, String partnerId) {

        Booking booking = bookingRepository.findByIdAndCustomerIdAndPartnerIdAndBookingClosed(bookingId, customerId,
                partnerId, true);

        if (booking == null) {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_NOT_FOUND.getDescription());
        }

        // decrypt and mask
        booking = decryptLoyaltyAcctId(booking, true);
        return booking;
    }

    public Booking getBooking(String bookingId, String partnerId) {

        return getBooking(bookingId, partnerId, true);

    }
    
    public Booking getBooking(String bookingId, String partnerId, boolean isClosed) {

        Booking booking = bookingRepository.findByIdAndPartnerIdAndBookingClosed(bookingId, partnerId, isClosed);

        if (booking == null) {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_NOT_FOUND.getDescription());
        }

        // decrypt and mask
        booking = decryptLoyaltyAcctId(booking, true);
        return booking;
    }
    
    public Booking adminGetBooking(String bookingId, String partnerId) {

        Booking booking = bookingRepository.findByIdAndPartnerId(bookingId, partnerId);

        if (booking == null) {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_NOT_FOUND.getDescription());
        }

        // decrypt and mask
        booking = decryptLoyaltyAcctId(booking, false);
        return booking;
    }
    
    // temp fix until the repository operations moved to service classes
    public Booking getBookingForUpdate(String bookingId, String partnerId) {

        Booking booking = bookingRepository.findByIdAndPartnerIdAndBookingClosed(bookingId, partnerId, true);

        if (booking == null) {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_NOT_FOUND.getDescription());
        }
        return booking;
    }
    
    public Booking getBookingByIdAndPartnerIdForUpdate(String bookingId, String partnerId) {

        Booking booking = bookingRepository.findByIdAndPartnerId(bookingId, partnerId);

        if (booking == null) {
            throw new NotFoundException(PlacePassExceptionCodes.BOOKING_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.BOOKING_NOT_FOUND.getDescription());
        }
        return booking;
    }

    public Page<Booking> getBookingsHistory(String customerId, String partnerId, int hitsPerPage, int pageNumber) {

        PaginationParamsValidator.validateParams(hitsPerPage, pageNumber);

        /* Pagination purpose */
        Pageable page = new PageRequest(pageNumber, hitsPerPage);
        boolean bookingClosed = true;

        Page<Booking> pageableBookings = bookingRepository.findByCustomerIdAndPartnerIdAndBookingClosedOrderByCreatedTimeDesc(customerId,
                partnerId, bookingClosed, page);
        // decrypt and mask
        pageableBookings.forEach(booking->decryptLoyaltyAcctId(booking, true));
        return pageableBookings;
    }

    public Booking findBookingsByEmailBookingReference(String partnerId, String bookerEmail, String bookingReference) {

        Booking booking = bookingRepository
                .findByPartnerIdAndBookingReferenceIgnoreCaseAndBookerDetailsEmailIgnoreCase(partnerId,
                        bookingReference, bookerEmail);

        if (booking == null) {
            throw new NotFoundException(PlacePassExceptionCodes.FIND_BOOKINGS_BOOKING_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.FIND_BOOKINGS_BOOKING_NOT_FOUND.getDescription());
        }
        // decrypt and mask
        booking = decryptLoyaltyAcctId(booking, true);
        return booking;
    }

    // Admin functionality. Loyalty account data is not masked.
    public Page<Booking> searchBookings(String partnerId, String confNumber, String firstName, String lastName,
            String phoneNumber, String email, String startActivityDateString, String endActivityDateString,
            String startBookingDateString, String endBookingDateString, String geoLocation, String productId,
            String vendor, PlatformStatus status, boolean goodStanding, String searchOperator, int hitsPerPage, int pageNumber) {

        Pageable bookingSearchPageReq = new PageRequest(pageNumber, hitsPerPage);
        Criteria finalCriteria = null;
        List<Criteria> criterias = new ArrayList<>();
        DateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date startActivityDate = null;
        Date endActivityDate = null;
        Date startBookingDate = null;
        Date endBookingDate = null;
        try {
            if (!StringUtils.isEmpty(startActivityDateString) && !StringUtils.isEmpty(endActivityDateString)) {
                startActivityDate = dateTimeFormat.parse(startActivityDateString + " " + DATE_START_TIME);
                endActivityDate = dateTimeFormat.parse(endActivityDateString + " " + DATE_END_TIME);
            }

            if (!StringUtils.isEmpty(startBookingDateString) && !StringUtils.isEmpty(endBookingDateString)) {
                startBookingDate = dateTimeFormat.parse(startBookingDateString + " " + DATE_START_TIME);
                endBookingDate = dateTimeFormat.parse(endBookingDateString + " " + DATE_END_TIME);
            }

        } catch (ParseException e) {
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_STD_DATE.toString(),
                    PlacePassExceptionCodes.INVALID_STD_DATE.getDescription());
        }

        if (!StringUtils.isEmpty(confNumber)) {
            criterias.add(Criteria.where("bookingReference").regex(
                    Pattern.compile(REGEX_START + confNumber + REGEX_END, Pattern.CASE_INSENSITIVE)));
        }
        if (!StringUtils.isEmpty(firstName)) {
            criterias.add(Criteria.where("bookerDetails.firstName").regex(
                    Pattern.compile(firstName, Pattern.CASE_INSENSITIVE)));
        }
        if (!StringUtils.isEmpty(lastName)) {
            criterias.add(Criteria.where("bookerDetails.lastName").regex(
                    Pattern.compile(lastName, Pattern.CASE_INSENSITIVE)));
        }
        if (!StringUtils.isEmpty(phoneNumber)) {
            criterias.add(Criteria.where("bookerDetails.phoneNumber").is(phoneNumber));
        }
        if (!StringUtils.isEmpty(email)) {
            criterias.add(Criteria.where("bookerDetails.email").regex(
                    Pattern.compile(REGEX_START + email + REGEX_END, Pattern.CASE_INSENSITIVE)));
        }
        if (startActivityDate != null && endActivityDate != null) {
            criterias.add(Criteria.where("bookingOptions.bookingDate").gte(startActivityDate).lte(endActivityDate));
        }
        if (startBookingDate != null && endBookingDate != null) {
            criterias.add(Criteria.where("createdTime").gte(startBookingDate).lte(endBookingDate));
        }
        if (!StringUtils.isEmpty(geoLocation)) {
            criterias.add(Criteria.where("bookingOptions.productDetails.webLocation.placeId").is(geoLocation));
        }
        if (!StringUtils.isEmpty(productId)) {
            criterias.add(Criteria.where("bookingOptions.productId").is(productId));
        }
        if (!StringUtils.isEmpty(vendor)) {
            criterias.add(Criteria.where("bookingOptions.vendor").regex(
                    Pattern.compile(REGEX_START + vendor + REGEX_END, Pattern.CASE_INSENSITIVE)));
        }
        if (!StringUtils.isEmpty(status)) {
            criterias.add(Criteria.where("bookingSummary.overallStatus.status").is(status));
        }
        
        if (SearchOperatorType.AND.name().equalsIgnoreCase(searchOperator)) {
            criterias.add(Criteria.where("partnerId").is(partnerId));
            criterias.add(Criteria.where("bookingSummary.overallGoodStanding").is(goodStanding));
            finalCriteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        } else if (SearchOperatorType.OR.name().equalsIgnoreCase(searchOperator)) {
        		finalCriteria = new Criteria().andOperator(Criteria.where("partnerId").is(partnerId),Criteria.where("bookingSummary.overallGoodStanding").is(goodStanding)).orOperator(
                        criterias.toArray(new Criteria[criterias.size()]));
        }

        Query query = new Query(finalCriteria);
        query.with(bookingSearchPageReq);
        long totalCount = mongoOperations.count(query, Booking.class);
        List<Booking> bookingList = mongoOperations.find(query, Booking.class);
        Page<Booking> bookingsPage = new PageImpl<>(bookingList, bookingSearchPageReq, totalCount);

        // decrypt. Not masking cause it's admin functionality
        bookingsPage.forEach(booking->decryptLoyaltyAcctId(booking, false));
        return bookingsPage;
    }

    public Booking getVoucherDetails(String voucherId) {

        Booking booking = bookingRepository.findVoucherUrl(voucherId);
        if (booking == null) {
            throw new NotFoundException(PlacePassExceptionCodes.VOUCHER_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.VOUCHER_NOT_FOUND.getDescription());
        }
        // decrypt and mask
        booking = decryptLoyaltyAcctId(booking, true);
        return booking;
    }

    public Booking getBookingVoucherDetails(String voucherId) {

        Booking booking = bookingRepository.findVoucher(voucherId);
        if (booking == null) {
            throw new NotFoundException(PlacePassExceptionCodes.VOUCHER_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.VOUCHER_NOT_FOUND.getDescription());
        }
        return booking;
    }

    public List<Booking> getBookings(String partnerId, int pageNumber, int hitsPerPage) {
        Pageable page = new PageRequest(pageNumber, hitsPerPage);

        List<Booking> bookingList = bookingRepository.findByPartnerId(partnerId, page);
        // decrypt and mask
        bookingList.forEach(booking->decryptLoyaltyAcctId(booking, true));
        return bookingList;
    }
    
    // temp fix until the repository operations moved to service classes
    public List<Booking> getBookingsForUpdate(String partnerId, int pageNumber, int hitsPerPage) {
        Pageable page = new PageRequest(pageNumber, hitsPerPage);
        List<Booking> bookingList = bookingRepository.findByPartnerId(partnerId, page);
        return bookingList;
    }

    public Long countByPartnerId(String partnerId) {
        return bookingRepository.countByPartnerId(partnerId);
    }
    
    // encrypts loyalty account id
    private Booking encryptLoyaltyAcctId(Booking booking) {
    	if (booking.getLoyaltyAccount() != null) {
    		LoyaltyAccount loyaltyAccount = booking.getLoyaltyAccount();
    		// Create decryption context
    		Map<String, String> encryptionContext = new HashMap<>();
    		encryptionContext.put("customer_id", booking.getCustomerId());
    		encryptionContext.put("partner_id", booking.getPartnerId());
    		encryptionContext.put("booker_email", booking.getBookerDetails().getEmail());
    		String loyaltyAccountId = cryptoService.awsKmsEncrypt(loyaltyAccount.getLoyaltyAccountId(), encryptionContext);
    		loyaltyAccount.setLoyaltyAccountId(loyaltyAccountId);
    	}
    	return booking;
    }
    
    // decrypts and masks loyalty account id
    private Booking decryptLoyaltyAcctId(Booking booking, boolean mask) {
    	if (booking.getLoyaltyAccount() != null) {
    		LoyaltyAccount loyaltyAccount = booking.getLoyaltyAccount();
    		// Create decryption context
    		Map<String, String> decryptionContext = new HashMap<>();
    		decryptionContext.put("customer_id", booking.getCustomerId());
    		decryptionContext.put("partner_id", booking.getPartnerId());
    		decryptionContext.put("booker_email", booking.getBookerDetails().getEmail());
    		String loyaltyAccountId = cryptoService.awsKmsDecrypt(loyaltyAccount.getLoyaltyAccountId(), decryptionContext);
    		if (mask) {
    			String maskedAccountId = cryptoService.maskStringToLength(loyaltyAccountId, -4);
    			loyaltyAccount.setLoyaltyAccountId(maskedAccountId);
    		} else {
    			loyaltyAccount.setLoyaltyAccountId(loyaltyAccountId);
    		}
    	}
    	return booking;
    }

	public List<Booking> getPendingBookings(Integer hitsPerPage, Integer pageNumber, Vendor vendor) {
		List<Booking> pendingBookingsList = new ArrayList<>();
		if (hitsPerPage.equals(-1) || pageNumber.equals(-1)) {
			if (vendor != null) {
				pendingBookingsList = bookingRepository.findByStatusAndVendor(PlatformStatus.PENDING.name(),
						vendor.name());
			} else {
				pendingBookingsList = bookingRepository.findByStatus(PlatformStatus.PENDING.name());
			}
		} else {
			Pageable page = new PageRequest(pageNumber, hitsPerPage);
			Page<Booking> pageablePendingBookings = null;
			if (vendor != null) {
				pageablePendingBookings = bookingRepository.findByStatusAndVendor(PlatformStatus.PENDING.name(),
						vendor.name(), page);
			} else {
				pageablePendingBookings = bookingRepository.findByStatus(PlatformStatus.PENDING.name(), page);
			}
			pendingBookingsList = pageablePendingBookings.getContent();
		}
		return pendingBookingsList;
	}

}
