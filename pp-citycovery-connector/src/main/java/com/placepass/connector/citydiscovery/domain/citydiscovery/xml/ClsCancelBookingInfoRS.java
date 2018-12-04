package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

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
 *         &lt;element name="BookingReferenceCityDiscovery" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingCancellationFee">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="Day" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                 &lt;attribute name="Percentage" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="BookingPriceNet" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ProcessType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bookingReferenceCityDiscovery",
    "bookingStatus",
    "bookingCancellationFee",
    "bookingPriceNet"
})
@XmlRootElement(name = "CityDiscovery")
public class ClsCancelBookingInfoRS {

    @XmlElement(name = "BookingReferenceCityDiscovery", required = true)
    protected String bookingReferenceCityDiscovery;
    @XmlElement(name = "BookingStatus", required = true)
    protected String bookingStatus;
    @XmlElement(name = "BookingCancellationFee", required = true)
    protected ClsCancelBookingInfoRS.BookingCancellationFee bookingCancellationFee;
    @XmlElement(name = "BookingPriceNet")
    protected float bookingPriceNet;
    @XmlAttribute(name = "ProcessType")
    protected String processType;

    /**
     * Gets the value of the bookingReferenceCityDiscovery property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingReferenceCityDiscovery() {
        return bookingReferenceCityDiscovery;
    }

    /**
     * Sets the value of the bookingReferenceCityDiscovery property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingReferenceCityDiscovery(String value) {
        this.bookingReferenceCityDiscovery = value;
    }

    /**
     * Gets the value of the bookingStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Sets the value of the bookingStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingStatus(String value) {
        this.bookingStatus = value;
    }

    /**
     * Gets the value of the bookingCancellationFee property.
     * 
     * @return
     *     possible object is
     *     {@link ClsCancelBookingInfoRS.BookingCancellationFee }
     *     
     */
    public ClsCancelBookingInfoRS.BookingCancellationFee getBookingCancellationFee() {
        return bookingCancellationFee;
    }

    /**
     * Sets the value of the bookingCancellationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClsCancelBookingInfoRS.BookingCancellationFee }
     *     
     */
    public void setBookingCancellationFee(ClsCancelBookingInfoRS.BookingCancellationFee value) {
        this.bookingCancellationFee = value;
    }

    /**
     * Gets the value of the bookingPriceNet property.
     * 
     */
    public float getBookingPriceNet() {
        return bookingPriceNet;
    }

    /**
     * Sets the value of the bookingPriceNet property.
     * 
     */
    public void setBookingPriceNet(float value) {
        this.bookingPriceNet = value;
    }

    /**
     * Gets the value of the processType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessType() {
        return processType;
    }

    /**
     * Sets the value of the processType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessType(String value) {
        this.processType = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="Day" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *       &lt;attribute name="Percentage" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class BookingCancellationFee {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "Day")
        protected Byte day;
        @XmlAttribute(name = "Percentage")
        protected Byte percentage;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the day property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getDay() {
            return day;
        }

        /**
         * Sets the value of the day property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setDay(Byte value) {
            this.day = value;
        }

        /**
         * Gets the value of the percentage property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getPercentage() {
            return percentage;
        }

        /**
         * Sets the value of the percentage property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setPercentage(Byte value) {
            this.percentage = value;
        }

    }

}
