package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * <xs:schema attributeFormDefault="unqualified" elementFormDefault="qualified" xmlns:xs=
 * "http://www.w3.org/2001/XMLSchema"> <xs:element name="CityDiscovery"> <xs:complexType> <xs:sequence>
 * <xs:element name="POS"> <xs:complexType> <xs:sequence> <xs:element name="Source"> <xs:complexType> <xs:simpleContent>
 * <xs:extension base="xs:string"> <xs:attribute type="xs:string" name="AgentSine"/>
 * <xs:attribute type="xs:int" name="AgentDutyCode"/> </xs:extension> </xs:simpleContent> </xs:complexType>
 * </xs:element> </xs:sequence> </xs:complexType> </xs:element> <xs:element type="xs:int" name="ActivityID"/>
 * <xs:element type="xs:int" name="ActivityPriceID"/> <xs:element type="xs:float" name="BookingPriceUSD"/>
 * <xs:element type="xs:string" name="ActivityPriceOption"/>
 * <xs:element type="xs:string" name="ActivityPriceOptionDepartureTime"/>
 * <xs:element type="xs:string" name="ActivityLanguages"/> <xs:element type="xs:date" name="BookingDate"/>
 * <xs:element type="xs:int" name="BookingNumberAdults"/> <xs:element type="xs:int" name="BookingNumberChildren"/>
 * <xs:element type="xs:int" name="BookingNumberInfants"/> <xs:element type="xs:string" name="BookingLastName"/>
 * <xs:element type="xs:string" name="BookingFirstName"/> <xs:element type="xs:string" name="BookingReferenceNumber"/>
 * <xs:element type="xs:string" name="BookingNotes"/> <xs:element type="xs:date" name="ArrivalDate"/>
 * <xs:element type="xs:date" name="DepartureDate"/> <xs:element type="xs:string" name="CreditCardName"/>
 * <xs:element type="xs:string" name="NameOfHotel"/> <xs:element type="xs:string" name="HotelAddress"/>
 * <xs:element type="xs:string" name="BookingPhone"/> <xs:element type="xs:long" name="BookingMobile"/>
 * <xs:element type="xs:string" name="BookingEmailAddress"/> <xs:element type="xs:int" name="BookingFax"/>
 * <xs:element type="xs:string" name="BookingGender"/> <xs:element type="xs:string" name="CreditCardBillingAddress"/>
 * <xs:element type="xs:short" name="CreditCardBillingZip"/> <xs:element type="xs:string" name="CreditCardBillingCity"/>
 * <xs:element type="xs:string" name="CreditCardBillingCountry"/> <xs:element type="xs:string" name="CreditCardType"/>
 * <xs:element type="xs:long" name="CreditCardNum"/> <xs:element type="xs:string" name="CreditCardExpiryDate"/>
 * <xs:element type="xs:short" name="CreditCardSecCode"/> <xs:element type="xs:string" name="ActivityPromoCode"/>
 * <xs:element type="xs:string" name="ActivityMiles"/> </xs:sequence>
 * <xs:attribute type="xs:string" name="ProcessType"/> </xs:complexType> </xs:element> </xs:schema>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pos", 
    "activityID", 
    "activityPriceID", 
    "bookingPriceUSD", 
    "activityPriceOption",
    "activityPriceOptionDepartureTime", 
    "activityLanguages", 
    "BookingDate", 
    "bookingNumberAdults",
    "bookingNumberChildren", 
    "bookingNumberInfants", 
    "bookingLastName", 
    "bookingFirstName", 
    "bookingReferenceNumber",
    "bookingNotes",
    "creditCardName", 
    "creditCardBillingAddress",
    "creditCardBillingZip", 
    "creditCardBillingCity", 
    "creditCardBillingCountry", 
    "creditCardType", 
    "creditCardNum",
    "creditCardExpiryDate", 
    "creditCardSecCode",  
    "arrivalDate", 
    "departureDate", 
    "nameOfHotel", 
    "hotelAddress", 
    "bookingPhone",
    "bookingMobile", 
    "bookingEmailAddress", 
    "bookingFax", 
    "bookingGender", 
    "activityPromoCode", 
    "activityMiles", 
    "postPayment",
    "processType"})
@XmlRootElement(name = "CityDiscovery")
public class ClsActivityBookingRQ {

    @XmlElement(name = "POS", required = true)
    protected ClsActivityBookingRQ.POS pos;

    @XmlElement(name = "ActivityID", required = true)
    protected int activityID;

    @XmlElement(name = "ActivityPriceID", required = true)
    protected int activityPriceID;

    @XmlElement(name = "BookingPriceUSD")
    protected float bookingPriceUSD;

    @XmlElement(name = "ActivityPriceOption")
    protected String activityPriceOption;

    @XmlElement(name = "ActivityPriceOptionDepartureTime", required = true)
    protected String activityPriceOptionDepartureTime;

    @XmlElement(name = "ActivityLanguages")
    protected String activityLanguages;

    @XmlElement(name = "BookingDate", required = true)
    protected String BookingDate;

    @XmlElement(name = "BookingNumberAdults", required = true)
    protected int bookingNumberAdults;

    @XmlElement(name = "BookingNumberChildren")
    protected int bookingNumberChildren;

    @XmlElement(name = "BookingNumberInfants")
    protected int bookingNumberInfants;

    @XmlElement(name = "BookingLastName", required = true)
    protected String bookingLastName;

    @XmlElement(name = "BookingFirstName", required = true)
    protected String bookingFirstName;

    @XmlElement(name = "BookingReferenceNumber", required = true)
    protected String bookingReferenceNumber;

    @XmlElement(name = "BookingNotes")
    protected String bookingNotes;

    @XmlElement(name = "ArrivalDate")
    protected String arrivalDate;

    @XmlElement(name = "DepartureDate")
    protected String departureDate;

    @XmlElement(name = "CreditCardName")
    protected String creditCardName;

    @XmlElement(name = "NameOfHotel", required = true)
    protected String nameOfHotel;

    @XmlElement(name = "HotelAddress", required = true)
    protected String hotelAddress;

    @XmlElement(name = "BookingPhone")
    protected String bookingPhone;

    @XmlElement(name = "BookingMobile")
    protected String bookingMobile;

    @XmlElement(name = "BookingEmailAddress")
    protected String bookingEmailAddress;

    @XmlElement(name = "BookingFax")
    protected String bookingFax;

    @XmlElement(name = "BookingGender")
    protected String bookingGender;

    @XmlElement(name = "CreditCardBillingAddress")
    protected String creditCardBillingAddress;

    @XmlElement(name = "CreditCardBillingZip")
    protected int creditCardBillingZip;

    @XmlElement(name = "CreditCardBillingCity")
    protected String creditCardBillingCity;

    @XmlElement(name = "CreditCardBillingCountry")
    protected String creditCardBillingCountry;

    @XmlElement(name = "CreditCardType")
    protected String creditCardType;

    @XmlElement(name = "CreditCardNum")
    protected String creditCardNum;

    @XmlElement(name = "CreditCardExpiryDate")
    protected String creditCardExpiryDate;

    @XmlElement(name = "CreditCardSecCode")
    protected int creditCardSecCode;

    @XmlElement(name = "ActivityPromoCode")
    protected String activityPromoCode;

    @XmlElement(name = "ActivityMiles")
    protected String activityMiles;
    
    @XmlElement(name = "PostPayment")
    protected String postPayment;
    
    @XmlAttribute(name = "ProcessType")
    protected String processType;

    public ClsActivityBookingRQ.POS getPOS() {
        return pos;
    }

    public void setPOS(ClsActivityBookingRQ.POS value) {
        this.pos = value;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int value) {
        this.activityID = value;
    }

    public int getActivityPriceID() {
        return activityPriceID;
    }

    public void setActivityPriceID(int activityPriceID) {
        this.activityPriceID = activityPriceID;
    }

    public float getBookingPriceUSD() {
        return bookingPriceUSD;
    }

    public void setBookingPriceUSD(float bookingPriceUSD) {
        this.bookingPriceUSD = bookingPriceUSD;
    }

    public String getActivityPriceOption() {
        return activityPriceOption;
    }

    public void setActivityPriceOption(String activityPriceOption) {
        this.activityPriceOption = activityPriceOption;
    }

    public String getActivityPriceOptionDepartureTime() {
        return activityPriceOptionDepartureTime;
    }

    public void setActivityPriceOptionDepartureTime(String activityPriceOptionDepartureTime) {
        this.activityPriceOptionDepartureTime = activityPriceOptionDepartureTime;
    }

    public String getActivityLanguages() {
        return activityLanguages;
    }

    public void setActivityLanguages(String activityLanguages) {
        this.activityLanguages = activityLanguages;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public int getBookingNumberAdults() {
        return bookingNumberAdults;
    }

    public void setBookingNumberAdults(int bookingNumberAdults) {
        this.bookingNumberAdults = bookingNumberAdults;
    }

    public int getBookingNumberChildren() {
        return bookingNumberChildren;
    }

    public void setBookingNumberChildren(int bookingNumberChildren) {
        this.bookingNumberChildren = bookingNumberChildren;
    }

    public int getBookingNumberInfants() {
        return bookingNumberInfants;
    }

    public void setBookingNumberInfants(int bookingNumberInfants) {
        this.bookingNumberInfants = bookingNumberInfants;
    }

    public String getBookingLastName() {
        return bookingLastName;
    }

    public void setBookingLastName(String bookingLastName) {
        this.bookingLastName = bookingLastName;
    }

    public String getBookingFirstName() {
        return bookingFirstName;
    }

    public void setBookingFirstName(String bookingFirstName) {
        this.bookingFirstName = bookingFirstName;
    }

    public String getBookingReferenceNumber() {
        return bookingReferenceNumber;
    }

    public void setBookingReferenceNumber(String bookingReferenceNumber) {
        this.bookingReferenceNumber = bookingReferenceNumber;
    }

    public String getBookingNotes() {
        return bookingNotes;
    }

    public void setBookingNotes(String bookingNotes) {
        this.bookingNotes = bookingNotes;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public String getNameOfHotel() {
        return nameOfHotel;
    }

    public void setNameOfHotel(String nameOfHotel) {
        this.nameOfHotel = nameOfHotel;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getBookingPhone() {
        return bookingPhone;
    }

    public void setBookingPhone(String bookingPhone) {
        this.bookingPhone = bookingPhone;
    }

    public String getBookingMobile() {
        return bookingMobile;
    }

    public void setBookingMobile(String bookingMobile) {
        this.bookingMobile = bookingMobile;
    }

    public String getBookingEmailAddress() {
        return bookingEmailAddress;
    }

    public void setBookingEmailAddress(String bookingEmailAddress) {
        this.bookingEmailAddress = bookingEmailAddress;
    }

    public String getBookingFax() {
        return bookingFax;
    }

    public void setBookingFax(String bookingFax) {
        this.bookingFax = bookingFax;
    }

    public String getBookingGender() {
        return bookingGender;
    }

    public void setBookingGender(String bookingGender) {
        this.bookingGender = bookingGender;
    }

    public String getCreditCardBillingAddress() {
        return creditCardBillingAddress;
    }

    public void setCreditCardBillingAddress(String creditCardBillingAddress) {
        this.creditCardBillingAddress = creditCardBillingAddress;
    }

    public int getCreditCardBillingZip() {
        return creditCardBillingZip;
    }

    public void setCreditCardBillingZip(int creditCardBillingZip) {
        this.creditCardBillingZip = creditCardBillingZip;
    }

    public String getCreditCardBillingCity() {
        return creditCardBillingCity;
    }

    public void setCreditCardBillingCity(String creditCardBillingCity) {
        this.creditCardBillingCity = creditCardBillingCity;
    }

    public String getCreditCardBillingCountry() {
        return creditCardBillingCountry;
    }

    public void setCreditCardBillingCountry(String creditCardBillingCountry) {
        this.creditCardBillingCountry = creditCardBillingCountry;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public String getCreditCardExpiryDate() {
        return creditCardExpiryDate;
    }

    public void setCreditCardExpiryDate(String creditCardExpiryDate) {
        this.creditCardExpiryDate = creditCardExpiryDate;
    }

    public int getCreditCardSecCode() {
        return creditCardSecCode;
    }

    public void setCreditCardSecCode(int creditCardSecCode) {
        this.creditCardSecCode = creditCardSecCode;
    }

    public String getActivityPromoCode() {
        return activityPromoCode;
    }

    public void setActivityPromoCode(String value) {
        this.activityPromoCode = value;
    }

    public String getActivityMiles() {
        return activityMiles;
    }

    public void setActivityMiles(String activityMiles) {
        this.activityMiles = activityMiles;
    }

    public String getPostPayment() {
        return postPayment;
    }

    public void setPostPayment(String postPayment) {
        this.postPayment = postPayment;
    }
    
    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String value) {
        this.processType = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Source">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="AgentSine" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="AgentDutyCode" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"source"})
    public static class POS {

        @XmlElement(name = "Source", required = true)
        protected ClsActivityBookingRQ.POS.Source source;

        /**
         * Gets the value of the source property.
         * 
         * @return possible object is {@link ClsActivityBookingRQ.POS.Source }
         * 
         */
        public ClsActivityBookingRQ.POS.Source getSource() {
            return source;
        }

        /**
         * Sets the value of the source property.
         * 
         * @param value allowed object is {@link ClsActivityBookingRQ.POS.Source }
         * 
         */
        public void setSource(ClsActivityBookingRQ.POS.Source value) {
            this.source = value;
        }

        /**
         * <p>
         * Java class for anonymous complex type.
         * 
         * <p>
         * The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="AgentSine" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="AgentDutyCode" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Source {

            @XmlAttribute(name = "AgentSine")
            protected String agentSine;

            @XmlAttribute(name = "AgentDutyCode")
            protected String agentDutyCode;

            /**
             * Gets the value of the agentSine property.
             * 
             * @return possible object is {@link String }
             * 
             */
            public String getAgentSine() {
                return agentSine;
            }

            /**
             * Sets the value of the agentSine property.
             * 
             * @param value allowed object is {@link String }
             * 
             */
            public void setAgentSine(String value) {
                this.agentSine = value;
            }

            /**
             * Gets the value of the agentDutyCode property.
             * 
             * @return possible object is {@link Integer }
             * 
             */
            public String getAgentDutyCode() {
                return agentDutyCode;
            }

            /**
             * Sets the value of the agentDutyCode property.
             * 
             * @param value allowed object is {@link Integer }
             * 
             */
            public void setAgentDutyCode(String value) {
                this.agentDutyCode = value;
            }

        }

    }

}
