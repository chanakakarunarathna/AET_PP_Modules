package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActivityID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActivityPriceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActivityPriceOption" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ActivityPriceOptionDepartureTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingReferenceCityDiscovery" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingVoucherInformation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="BookingCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CreditCardTransactionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CreditCardTransactionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActivityCancellation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ActivityCancellationPolicy" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="Day" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="Percentage" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="ProcessType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Target" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "activityID",
    "activityPriceId",
    "activityPriceOption",
    "activityPriceOptionDepartureTime",
    "bookingReferenceCityDiscovery",
    "bookingStatus",
    "bookingVoucherInformation",
    "bookingPrice",
    "bookingCurrency",
    "creditCardTransactionType",
    "creditCardTransactionID",
    "activityCancellation"
})
@XmlRootElement(name = "CityDiscovery")
public class ClsBookInfoRS {

    @XmlElement(name = "ActivityID")
    protected int activityID;

    @XmlElement(name = "ActivityPriceId")
    protected int activityPriceId;

    @XmlElement(name = "ActivityPriceOption", required = true)
    protected String activityPriceOption;

    @XmlElement(name = "ActivityPriceOptionDepartureTime", required = true)
    protected String activityPriceOptionDepartureTime;

    @XmlElement(name = "BookingReferenceCityDiscovery", required = true)
    protected String bookingReferenceCityDiscovery;

    @XmlElement(name = "BookingStatus", required = true)
    protected String bookingStatus;

    @XmlElement(name = "BookingVoucherInformation", required = true)
    protected String bookingVoucherInformation;

    @XmlElement(name = "BookingPrice")
    protected float bookingPrice;

    @XmlElement(name = "BookingCurrency", required = true)
    protected String bookingCurrency;

    @XmlElement(name = "CreditCardTransactionType", required = true)
    protected String creditCardTransactionType;

    @XmlElement(name = "CreditCardTransactionID")
    protected int creditCardTransactionID;

    @XmlElement(name = "ActivityCancellation", required = true)
    protected ClsActivityCancellation activityCancellation;

    @XmlAttribute(name = "ProcessType")
    protected String processType;

    @XmlAttribute(name = "Target")
    protected String target;

    /**
     * Gets the value of the activityID property.
     * 
     */
    public int getActivityID() {
        return activityID;
    }

    /**
     * Sets the value of the activityID property.
     * 
     */
    public void setActivityID(int value) {
        this.activityID = value;
    }

    /**
     * Gets the value of the activityPriceId property.
     * 
     */
    public int getActivityPriceId() {
        return activityPriceId;
    }

    /**
     * Sets the value of the activityPriceId property.
     * 
     */
    public void setActivityPriceId(int value) {
        this.activityPriceId = value;
    }

    /**
     * Gets the value of the activityPriceOption property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getActivityPriceOption() {
        return activityPriceOption;
    }

    /**
     * Sets the value of the activityPriceOption property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setActivityPriceOption(String value) {
        this.activityPriceOption = value;
    }

    /**
     * Gets the value of the activityPriceOptionDepartureTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getActivityPriceOptionDepartureTime() {
        return activityPriceOptionDepartureTime;
    }

    /**
     * Sets the value of the activityPriceOptionDepartureTime property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setActivityPriceOptionDepartureTime(String value) {
        this.activityPriceOptionDepartureTime = value;
    }

    /**
     * Gets the value of the bookingReferenceCityDiscovery property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBookingReferenceCityDiscovery() {
        return bookingReferenceCityDiscovery;
    }

    /**
     * Sets the value of the bookingReferenceCityDiscovery property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBookingReferenceCityDiscovery(String value) {
        this.bookingReferenceCityDiscovery = value;
    }

    /**
     * Gets the value of the bookingStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Sets the value of the bookingStatus property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBookingStatus(String value) {
        this.bookingStatus = value;
    }

    /**
     * Gets the value of the bookingVoucherInformation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBookingVoucherInformation() {
        return bookingVoucherInformation;
    }

    /**
     * Sets the value of the bookingVoucherInformation property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBookingVoucherInformation(String value) {
        this.bookingVoucherInformation = value;
    }

    /**
     * Gets the value of the bookingPrice property.
     * 
     */
    public float getBookingPrice() {
        return bookingPrice;
    }

    /**
     * Sets the value of the bookingPrice property.
     * 
     */
    public void setBookingPrice(float value) {
        this.bookingPrice = value;
    }

    /**
     * Gets the value of the bookingCurrency property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBookingCurrency() {
        return bookingCurrency;
    }

    /**
     * Sets the value of the bookingCurrency property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBookingCurrency(String value) {
        this.bookingCurrency = value;
    }

    /**
     * Gets the value of the creditCardTransactionType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCreditCardTransactionType() {
        return creditCardTransactionType;
    }

    /**
     * Sets the value of the creditCardTransactionType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCreditCardTransactionType(String value) {
        this.creditCardTransactionType = value;
    }

    /**
     * Gets the value of the creditCardTransactionID property.
     * 
     */
    public int getCreditCardTransactionID() {
        return creditCardTransactionID;
    }

    /**
     * Sets the value of the creditCardTransactionID property.
     * 
     */
    public void setCreditCardTransactionID(int value) {
        this.creditCardTransactionID = value;
    }

    /**
     * Gets the value of the activityCancellation property.
     * 
     * @return possible object is {@link ClsBookInfoRS.ActivityCancellation }
     * 
     */
    public ClsActivityCancellation getActivityCancellation() {
        return activityCancellation;
    }

    /**
     * Sets the value of the activityCancellation property.
     * 
     * @param value allowed object is {@link ClsBookInfoRS.ActivityCancellation }
     * 
     */
    public void setActivityCancellation(ClsActivityCancellation value) {
        this.activityCancellation = value;
    }

    /**
     * Gets the value of the processType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getProcessType() {
        return processType;
    }

    /**
     * Sets the value of the processType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setProcessType(String value) {
        this.processType = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTarget(String value) {
        this.target = value;
    }

}
