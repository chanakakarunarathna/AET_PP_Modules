package com.placepass.connector.common.booking;

public class BookingVoucherRQ {

	private String referenceNumber;

    private String bookerEmail;

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BookingVoucherRQ [" + (referenceNumber != null ? "referenceNumber=" + referenceNumber + ", " : "")
                + (bookerEmail != null ? "bookerEmail=" + bookerEmail : "") + "]";
    }

}
