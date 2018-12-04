package com.urbanadventures.connector.application.booking;

import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;

public interface BookingServices {

	public GetProductPriceRS getProductPrice(GetProductPriceRQ getProductPriceRQ);

	public MakeBookingRS makeBooking(MakeBookingRQ makeBookingRQ);
	
	public GetBookingQuestionsRS getBookingQuestions();
	
	public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingResultDetail);
	
	public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRQ);
}
