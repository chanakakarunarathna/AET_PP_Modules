package com.placepass.batchprocessor.application;

import java.util.ArrayList;
import java.util.List;
import com.placepass.batchprocessor.application.booking.domain.GetBookingStatusRS;
import com.placepass.batchprocessor.application.booking.domain.PendingBookingsRS;
import com.placepass.batchprocessor.application.booking.domain.PlatformStatus;
import com.placepass.batchprocessor.domain.PendingBooking;
import com.placepass.batchprocessor.domain.ProcessStatus;
import com.placepass.batchprocessor.application.booking.domain.BookingStatusRQ;
import com.placepass.batchprocessor.application.booking.domain.BookingStatusRS;
import com.placepass.utils.bookingstatus.ConnectorBookingStatus;

public class BookingTransformer {

	public static BookingStatusRS toBookingStatusRS(BookingStatusRS bookingStatusRS, GetBookingStatusRS bookingStatusResponse) {
		
		bookingStatusRS.setReferenceNumber(bookingStatusResponse.getVendorBookingRefId());
        String status = bookingStatusResponse.getStatus();
        if (PlatformStatus.SUCCESS.name().equals(status)) {
            bookingStatusRS.setOldStatus(ConnectorBookingStatus.CONFIRMED);
        } else if (PlatformStatus.PENDING.name().equals(status)) {
            bookingStatusRS.setOldStatus(ConnectorBookingStatus.PENDING);
        } else if (PlatformStatus.BOOKING_FAILED.name().equals(status)) {
            bookingStatusRS.setOldStatus(ConnectorBookingStatus.FAILED);
        } else if (PlatformStatus.CANCELLED.name().equals(status)) {
            bookingStatusRS.setOldStatus(ConnectorBookingStatus.CANCELLED);
        } else if (PlatformStatus.BOOKING_REJECTED.name().equals(status)) {
            bookingStatusRS.setOldStatus(ConnectorBookingStatus.REJECTED);
        }
        bookingStatusRS.setManualInterventionRequired(bookingStatusResponse.isManualInterventionRequired());
        bookingStatusRS.setNewStatus(bookingStatusResponse.getNewStatus());
        bookingStatusRS.setBookingStatusUpdated(bookingStatusResponse.isBookingStatusUpdated());
        bookingStatusRS.setMessage(bookingStatusResponse.getMessage());
        return bookingStatusRS;
        
	}
	
	public static List<PendingBooking> pendingBookingsRSToPendingBooking(PendingBookingsRS pendingBookingsDTO) {

        List<PendingBooking> pendingBookingsList = new ArrayList<>();
        if (pendingBookingsDTO != null && !pendingBookingsDTO.getPendingBookings().isEmpty()) {
            for (com.placepass.batchprocessor.application.booking.domain.PendingBooking pendingBookingDTO : pendingBookingsDTO
                    .getPendingBookings()) {
                PendingBooking pendingBooking = new PendingBooking();
                pendingBooking.setBookingId(pendingBookingDTO.getId());
                pendingBooking.setBookerEmail(pendingBookingDTO.getBookerEmail());
                pendingBooking.setBookingReference(pendingBookingDTO.getBookingReference());
                pendingBooking.setPartnerId(pendingBookingDTO.getPartnerId());
                pendingBooking.setVendor(pendingBookingDTO.getVendor());
                pendingBooking.setVendorBookingRefId(pendingBookingDTO.getVendorBookingRefId());
                if (pendingBookingDTO.getBookingStatus() != null) {
                    pendingBooking.setBookingStatus(pendingBookingDTO.getBookingStatus().name());
                }
                pendingBookingsList.add(pendingBooking);
            }
        }
        return pendingBookingsList;
        
	}
	
	public static void bookingStatusRStoPendingBooking(PendingBooking pendingBooking,
            BookingStatusRS bookingStatusResponse) {

		pendingBooking.setInterventionRequired(bookingStatusResponse.isManualInterventionRequired());
        ConnectorBookingStatus connectorBookingStatus = bookingStatusResponse.getNewStatus();
		if (connectorBookingStatus != null){
			switch(connectorBookingStatus){
		
			case CONFIRMED :
				pendingBooking.setProcessStatus(ProcessStatus.SUCCESS);
				break;
			case REJECTED :
				pendingBooking.setProcessStatus(ProcessStatus.SUCCESS);
				break;
			case CANCELLED :
				pendingBooking.setProcessStatus(ProcessStatus.SUCCESS);
				break;
			case FAILED :
				pendingBooking.setProcessStatus(ProcessStatus.ERROR);
				break;
			case UNKNOWN :
				pendingBooking.setProcessStatus(ProcessStatus.SUCCESS);
				break;
			default:
				pendingBooking.setProcessStatus(ProcessStatus.NOT_CHANGED);
				break;
			}
		}else{
			pendingBooking.setProcessStatus(ProcessStatus.NOT_CHANGED);
		}
		pendingBooking.setLatestBookingStatus(bookingStatusResponse.getNewStatus().name());
		pendingBooking.setMessage(bookingStatusResponse.getMessage());
    }
	
	public static BookingStatusRQ pendingBookingToBookingStatusRQ(PendingBooking pendingBooking){
		
		BookingStatusRQ bookingStatusRQ = new BookingStatusRQ();
        bookingStatusRQ.setVendor(pendingBooking.getVendor());
        bookingStatusRQ.setBookerEmail(pendingBooking.getBookerEmail());
        bookingStatusRQ.setBookingId(pendingBooking.getBookingId());
        bookingStatusRQ.setPartnerId(pendingBooking.getPartnerId());
        bookingStatusRQ.setReferenceNumber(pendingBooking.getBookingReference());
        bookingStatusRQ.setStatus(pendingBooking.getBookingStatus());
        bookingStatusRQ.setVendorBookingRefId(pendingBooking.getVendorBookingRefId());
        return bookingStatusRQ;
        
	}
}
