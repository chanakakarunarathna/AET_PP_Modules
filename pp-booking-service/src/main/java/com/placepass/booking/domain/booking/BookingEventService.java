package com.placepass.booking.domain.booking;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.stereotype.Service;

@Service
public class BookingEventService {

    @Autowired
    private SpanAccessor spanAccessor;
	
	public int addEventToBooking(Booking booking, int bookingEventIndex, EventName eventName, Map<String,String> extendedAttributes){
		
		  Span span = this.spanAccessor.getCurrentSpan();
		  
	      BookingEvent bookingEvent = new BookingEvent(bookingEventIndex, eventName, extendedAttributes, span.getSpanId(), span.getTraceId());
	      booking.getBookingEvents().add(bookingEvent);
	      return bookingEventIndex+1;
		
	}
	
	public int addEventToBooking(Booking booking, int bookingEventIndex, EventName eventName, Map<String,String> extendedAttributes, ManualInterventionDetail manualInterventionDetail){
		
		  Span span = this.spanAccessor.getCurrentSpan();
		  
	      BookingEvent bookingEvent = new BookingEvent(bookingEventIndex, eventName, extendedAttributes, span.getSpanId(), span.getTraceId());
	      bookingEvent.setManualInterventionDetail(manualInterventionDetail);
	      booking.getBookingEvents().add(bookingEvent);
	      return bookingEventIndex+1;
		
	}
	
	public void addEventsToBooking(Booking booking, List<BookingEvent> bookingEvent){
		
		 booking.getBookingEvents().addAll(bookingEvent);
		
	}
	
}
