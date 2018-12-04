package com.placepass.batchprocessor.infrastructure.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.placepass.batchprocessor.domain.PendingBooking;

@Repository
public interface PendingBookingRepository extends MongoRepository<PendingBooking, String> {

    @Query("{ $and: [ {'vendor' : ?0}, {$or: [ {'processStatus' : 'NOT_STARTED'}  , {'processStatus' : 'ERROR'}, {'processStatus' : 'NOT_CHANGED'} ] } ] }")
    public List<PendingBooking> findPendingBookingsByVendor(String vendor);

    @Query("{ 'bookingReference' : ?0 }")
    public PendingBooking findPendingBookingsByBookingReference(String bookingReference);

    public PendingBooking findPendingBookingsById(String id);

    public PendingBooking findPendingBookingsByBookingId(String bookingId);

}
