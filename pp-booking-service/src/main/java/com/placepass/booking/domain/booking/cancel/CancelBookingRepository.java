package com.placepass.booking.domain.booking.cancel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CancelBookingRepository extends MongoRepository<CancelBooking, String> {

    public CancelBooking findByPartnerIdAndBookingId(String partnerId, String bookingId);

    public CancelBooking findByPartnerIdAndId(String partnerId, String id);
}
