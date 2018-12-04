package com.placepass.booking.domain.booking;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookingRepository extends MongoRepository<Booking, String> {

    public Booking findByIdAndCustomerIdAndPartnerIdAndBookingClosed(String id, String customerId, String partnerId,
            boolean bookingClosed);

    public Booking findByIdAndPartnerId(String id, String partnerId);
    
    public Booking findByIdAndPartnerIdAndBookingClosed(String id, String partnerId, boolean bookingClosed);

    public Page<Booking> findByCustomerIdAndPartnerIdAndBookingClosedOrderByCreatedTimeDesc(String customerId,
            String partnerId, boolean bookingClosed, Pageable pageable);

    public Booking findByCustomerIdAndBookingReference(String customerId, String bookingReference);

    @Query(value = "{ 'partnerId' : ?0, 'bookerDetails.email' : ?1 }")
    public List<Booking> findByPartnerIdAndBookerDetailsEmail(String partnerId, String email);

    public Booking findByPartnerIdAndBookingReferenceIgnoreCaseAndBookerDetailsEmailIgnoreCase(String partnerId,
            String bookingReference, String email);

    @Query(value = "{ bookingOptions.voucher._id : ?0 }", fields = "{ bookingOptions.voucher : 1}")
    public Booking findVoucherUrl(String voucherId);
    
    @Query(value = "{ bookingOptions.voucher._id : ?0 }")
    public Booking findVoucher(String voucherId);
    
    public List<Booking> findByPartnerId(String partnerId, Pageable pageable);
    
    public Long countByPartnerId(String partnerId);

    @Query("{'bookingStatus.status' : ?0}")
    public Page<Booking> findByStatus(String status, Pageable pageable);
    
    @Query("{'bookingStatus.status' : ?0}")
    public List<Booking> findByStatus(String status);

    @Query(value = "{ $and: [ {'bookingStatus.status' : ?0}, {'bookingOptions.vendor' : ?1}] }")
    public Page<Booking> findByStatusAndVendor(String status, String vendor,Pageable pageable);

    @Query(value = "{ $and: [ {'bookingStatus.status' : ?0}, {'bookingOptions.vendor' : ?1}] }")
    public List<Booking> findByStatusAndVendor(String status, String vendor);

}
