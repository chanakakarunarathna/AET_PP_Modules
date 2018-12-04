package com.urbanadventures.connector.application.soapclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.urbanadventures.connector.domain.urbanadventures.ClsAccessType;
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

@Repository
public class SoapClientServiceImpl extends WebServiceGatewaySupport implements SoapClientService {

	@Value("${ua.username}")
	private String userName;

	@Value("${ua.password}")
	private String password;

	@Value("${ua.endpointURL}")
	private String endpointURL;

	@Override
	public ClsGetTripInfoRS getProductDetails(ClsGetTripInfoRQ clsGetTripInfoRQ) {

		clsGetTripInfoRQ.setAccess(getAccessObject());
		ClsGetTripInfoRS clsGetTripInfoRS = (ClsGetTripInfoRS) getWebServiceTemplate()
				.marshalSendAndReceive(endpointURL, clsGetTripInfoRQ, new SoapActionCallback(endpointURL));

		return clsGetTripInfoRS;
	}

	@Override
	public ClsTripAvailableDateRS getProductAvailability(ClsTripAvailableDateRQ clsTripAvailableDateRQ) {

		clsTripAvailableDateRQ.setAccess(getAccessObject());
		ClsTripAvailableDateRS clsTripAvailableDateRS = (ClsTripAvailableDateRS) getWebServiceTemplate()
				.marshalSendAndReceive(endpointURL, clsTripAvailableDateRQ, new SoapActionCallback(endpointURL));

		return clsTripAvailableDateRS;
	}

	@Override
	public ClsTripAlmRS getProductAllotment(ClsTripAlmRQ clsTripAlmRQ) {

		clsTripAlmRQ.setAccess(getAccessObject());
		ClsTripAlmRS clsTripAlmRS = (ClsTripAlmRS) getWebServiceTemplate().marshalSendAndReceive(endpointURL,
				clsTripAlmRQ, new SoapActionCallback(endpointURL));

		return clsTripAlmRS;
	}

	@Override
	public ClsGetPriceRS getProductPrice(ClsGetPriceRQ clsGetPriceRQ) {

		clsGetPriceRQ.setAccess(getAccessObject());
		ClsGetPriceRS clsGetPriceRS = (ClsGetPriceRS) getWebServiceTemplate().marshalSendAndReceive(endpointURL,
				clsGetPriceRQ, new SoapActionCallback(endpointURL));
		return clsGetPriceRS;
	}

	@Override
	public ClsBookRS makeBooking(ClsBookRQ clsBookRQ) {

		clsBookRQ.setAccess(getAccessObject());
		ClsBookRS clsBookRS = (ClsBookRS) getWebServiceTemplate().marshalSendAndReceive(endpointURL,
				clsBookRQ, new SoapActionCallback(endpointURL));
		return clsBookRS;
	}
	
	public ClsAccessType getAccessObject() {

		ClsAccessType clsAccessType = new ClsAccessType();
		clsAccessType.setUserName(userName);
		clsAccessType.setPassword(password);
		return clsAccessType;
	}

	@Override
	public ClsGetBookingVoucherRS getBookingVoucher(ClsGetBookingVoucherRQ clsGetBookingVoucherRQ) {
		
		clsGetBookingVoucherRQ.setAccess(getAccessObject());
		ClsGetBookingVoucherRS clsGetBookingVoucherRS = (ClsGetBookingVoucherRS) getWebServiceTemplate().marshalSendAndReceive(endpointURL,
				clsGetBookingVoucherRQ, new SoapActionCallback(endpointURL));
		return clsGetBookingVoucherRS;
	}

	@Override
    public ClsCancelBookingRS cancelBooking(ClsCancelBookingRQ clsCancelBookingRQ) {

        clsCancelBookingRQ.setAccess(getAccessObject());
        ClsCancelBookingRS clsCancelBookingRS = (ClsCancelBookingRS) getWebServiceTemplate()
                .marshalSendAndReceive(endpointURL, clsCancelBookingRQ, new SoapActionCallback(endpointURL));
        return clsCancelBookingRS;
	}

}
