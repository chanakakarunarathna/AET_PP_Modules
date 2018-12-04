package com.urbanadventures.connector.application.soapclient;

import com.urbanadventures.connector.domain.urbanadventures.ClsBookRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsBookRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsCancelBookingRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsCancelBookingRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetBookingVoucherRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetBookingVoucherRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetTripInfoRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetTripInfoRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAlmRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAlmRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAvailableDateRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAvailableDateRS;

public interface SoapClientService {

	public ClsGetTripInfoRS getProductDetails(ClsGetTripInfoRQ clsGetTripInfoRQ);

	public ClsTripAvailableDateRS getProductAvailability(ClsTripAvailableDateRQ clsTripAvailableDateRQ);

	public ClsTripAlmRS getProductAllotment(ClsTripAlmRQ clsTripAlmRQ);

	public ClsGetPriceRS getProductPrice(ClsGetPriceRQ clsGetPriceRQ);
	
	public ClsBookRS makeBooking(ClsBookRQ clsBookRQ);
	
	public ClsGetBookingVoucherRS getBookingVoucher(ClsGetBookingVoucherRQ clsGetBookingVoucherRQ);

	public ClsCancelBookingRS cancelBooking(ClsCancelBookingRQ clsCancelBookingRQ);

}