package com.urbanadventures.connector.domain.urbanadventures;


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clsTripBriefInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clsTripBriefInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TripCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TripName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UADestinationID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdultPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="ChildPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="NetAdultPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="NetChildPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BriefIntro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TripStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HotelLocationRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Photo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clsTripBriefInfo", propOrder = {
    "tripCode",
    "tripName",
    "uaDestinationID",
    "duration",
    "adultPrice",
    "childPrice",
    "netAdultPrice",
    "netChildPrice",
    "currency",
    "briefIntro",
    "tripStyle",
    "hotelLocationRequired",
    "photo"
})
public class ClsTripBriefInfo {

    @XmlElement(name = "TripCode")
    protected String tripCode;
    @XmlElement(name = "TripName", required = true)
    protected String tripName;
    @XmlElement(name = "UADestinationID")
    protected int uaDestinationID;
    @XmlElement(name = "Duration")
    protected String duration;
    @XmlElement(name = "AdultPrice")
    protected Float adultPrice;
    @XmlElement(name = "ChildPrice")
    protected Float childPrice;
    @XmlElement(name = "NetAdultPrice")
    protected Float netAdultPrice;
    @XmlElement(name = "NetChildPrice")
    protected Float netChildPrice;
    @XmlElement(name = "Currency")
    protected String currency;
    @XmlElement(name = "BriefIntro")
    protected String briefIntro;
    @XmlElementRef(name = "TripStyle", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> tripStyle;
    @XmlElement(name = "HotelLocationRequired")
    protected Boolean hotelLocationRequired;
    @XmlElement(name = "Photo")
    protected String photo;

    /**
     * Gets the value of the tripCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTripCode() {
        return tripCode;
    }

    /**
     * Sets the value of the tripCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTripCode(String value) {
        this.tripCode = value;
    }

    /**
     * Gets the value of the tripName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTripName() {
        return tripName;
    }

    /**
     * Sets the value of the tripName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTripName(String value) {
        this.tripName = value;
    }

    /**
     * Gets the value of the uaDestinationID property.
     * 
     */
    public int getUADestinationID() {
        return uaDestinationID;
    }

    /**
     * Sets the value of the uaDestinationID property.
     * 
     */
    public void setUADestinationID(int value) {
        this.uaDestinationID = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Gets the value of the adultPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getAdultPrice() {
        return adultPrice;
    }

    /**
     * Sets the value of the adultPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setAdultPrice(Float value) {
        this.adultPrice = value;
    }

    /**
     * Gets the value of the childPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getChildPrice() {
        return childPrice;
    }

    /**
     * Sets the value of the childPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setChildPrice(Float value) {
        this.childPrice = value;
    }

    /**
     * Gets the value of the netAdultPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getNetAdultPrice() {
        return netAdultPrice;
    }

    /**
     * Sets the value of the netAdultPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setNetAdultPrice(Float value) {
        this.netAdultPrice = value;
    }

    /**
     * Gets the value of the netChildPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getNetChildPrice() {
        return netChildPrice;
    }

    /**
     * Sets the value of the netChildPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setNetChildPrice(Float value) {
        this.netChildPrice = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the briefIntro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBriefIntro() {
        return briefIntro;
    }

    /**
     * Sets the value of the briefIntro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBriefIntro(String value) {
        this.briefIntro = value;
    }

    /**
     * Gets the value of the tripStyle property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTripStyle() {
        return tripStyle;
    }

    /**
     * Sets the value of the tripStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTripStyle(JAXBElement<String> value) {
        this.tripStyle = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the hotelLocationRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHotelLocationRequired() {
        return hotelLocationRequired;
    }

    /**
     * Sets the value of the hotelLocationRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHotelLocationRequired(Boolean value) {
        this.hotelLocationRequired = value;
    }

    /**
     * Gets the value of the photo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets the value of the photo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoto(String value) {
        this.photo = value;
    }

}
