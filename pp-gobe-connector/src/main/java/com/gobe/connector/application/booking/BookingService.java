package com.gobe.connector.application.booking;

import com.placepass.connector.common.booking.BookingStatusRQ;
import com.placepass.connector.common.booking.BookingStatusRS;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.MakeBookingRQ;

public interface BookingService {

	public GetProductPriceRS getProductPrice(GetProductPriceRQ getProductPriceRQ);

	public MakeBookingRS makeBooking(MakeBookingRQ makeBookingRQ);

	public GetBookingQuestionsRS getBookingQuestions(String productId, String currencyCode);
	
	public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingVoucherRQ);
	
	public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRQ);

	public BookingStatusRS getBookingStatus(BookingStatusRQ bookingStatusRQ);
	
}
