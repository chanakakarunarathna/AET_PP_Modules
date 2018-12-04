package com.placepass.connector.bemyguest.application.booking;

import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;

public interface BookingService {

	public GetProductPriceRS getProductPrice(GetProductPriceRQ getProductPriceRQ);

	public MakeBookingRS makeBooking(MakeBookingRQ makeBookingRQ);

	public GetBookingQuestionsRS getBookingQuestions(String productId, String currencyCode);
	
	public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingVoucherRQ);
	
	public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRQ);

}