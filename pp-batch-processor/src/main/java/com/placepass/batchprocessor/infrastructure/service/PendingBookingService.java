package com.placepass.batchprocessor.infrastructure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.batchprocessor.domain.PendingBooking;
import com.placepass.batchprocessor.infrastructure.repository.PendingBookingRepository;

@Service
public class PendingBookingService {

    @Autowired
    private PendingBookingRepository pendingBookingRepository;

    public PendingBooking savePendingBooking(PendingBooking pendingBookingToSave) {
        return pendingBookingRepository.save(pendingBookingToSave);
    }

    public void updatePendingBooking(PendingBooking pendingBookingToUpdate) {
        pendingBookingRepository.save(pendingBookingToUpdate);
    }

    public PendingBooking getPendingBookingById(String id) {
        return pendingBookingRepository.findPendingBookingsById(id);
    }

    public PendingBooking getPendingBookingByBookingId(String bookingId) {
        return pendingBookingRepository.findPendingBookingsByBookingId(bookingId);
    }

    public List<PendingBooking> getPendingBookingByVendor(String vendor) {
        return pendingBookingRepository.findPendingBookingsByVendor(vendor);
    }
}
