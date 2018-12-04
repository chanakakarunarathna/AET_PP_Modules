package com.placepass.connector.common.booking;

import java.util.List;

import com.placepass.connector.common.common.BaseRS;

public class GetBookingQuestionsRS extends BaseRS {

	private List<BookingQuestion> bookingQuestions;

	public List<BookingQuestion> getBookingQuestions() {
		return bookingQuestions;
	}

	public void setBookingQuestions(List<BookingQuestion> bookingQuestions) {
		this.bookingQuestions = bookingQuestions;
	}

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetBookingQuestionsRS [" + (bookingQuestions != null ? "bookingQuestions=" + bookingQuestions : "")
                + "]";
    }

}
