package com.urbanadventures.connector.domain.urbanadventures;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for clsTripFullInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clsTripFullInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TripCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TripName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Destination" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ValidFrom" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ValidTo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Available" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PickupLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DropoffLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GroupMin" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="GroupMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="AdultPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="ChildrenPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="NetAdultPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="NetChildPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="CancelPolicy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HighLights" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Style" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HotelLocationRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ActiveLang" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PhysicalRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CulturalRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CarbonNeutral" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Introduction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Itinerary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Inclusion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Exclusion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VoucherExchange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdditionalNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Confirmation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="YourTrip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DressStandard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Closure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChildPolicy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MinChildAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MaxChildAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Departure" type="{http://tempuri.org/urbanadventures.xsd}clsTripDeparture" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Photo" type="{http://tempuri.org/urbanadventures.xsd}clsTripPhoto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clsTripFullInfo", propOrder = {
    "tripCode",
    "tripName",
    "destination",
    "validFrom",
    "validTo",
    "duration",
    "available",
    "pickupLocation",
    "dropoffLocation",
    "groupMin",
    "groupMax",
    "adultPrice",
    "childrenPrice",
    "netAdultPrice",
    "netChildPrice",
    "cancelPolicy",
    "currency",
    "highLights",
    "style",
    "hotelLocationRequired",
    "activeLang",
    "physicalRate",
    "culturalRate",
    "carbonNeutral",
    "introduction",
    "itinerary",
    "inclusion",
    "exclusion",
    "voucherExchange",
    "additionalNote",
    "confirmation",
    "yourTrip",
    "dressStandard",
    "tipping",
    "closure",
    "childPolicy",
    "minChildAge",
    "maxChildAge",
    "departure",
    "photo",
    "contactPhone"
})
public class ClsTripFullInfo {

    @XmlElement(name = "TripCode")
    protected String tripCode;
    @XmlElement(name = "TripName", required = true)
    protected String tripName;
    @XmlElement(name = "Destination")
    protected Integer destination;
    @XmlElement(name = "ValidFrom")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validFrom;
    @XmlElement(name = "ValidTo")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validTo;
    @XmlElement(name = "Duration")
    protected String duration;
    @XmlElement(name = "Available")
    protected String available;
    @XmlElement(name = "PickupLocation")
    protected String pickupLocation;
    @XmlElement(name = "DropoffLocation")
    protected String dropoffLocation;
    @XmlElement(name = "GroupMin")
    protected Integer groupMin;
    @XmlElement(name = "GroupMax")
    protected Integer groupMax;
    @XmlElement(name = "AdultPrice")
    protected Float adultPrice;
    @XmlElement(name = "ChildrenPrice")
    protected Float childrenPrice;
    @XmlElement(name = "NetAdultPrice")
    protected Float netAdultPrice;
    @XmlElement(name = "NetChildPrice")
    protected Float netChildPrice;
    @XmlElementRef(name = "CancelPolicy", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> cancelPolicy;
    @XmlElement(name = "Currency")
    protected String currency;
    @XmlElementRef(name = "HighLights", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> highLights;
    @XmlElementRef(name = "Style", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> style;
    @XmlElementRef(name = "HotelLocationRequired", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<Boolean> hotelLocationRequired;
    @XmlElementRef(name = "ActiveLang", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> activeLang;
    @XmlElementRef(name = "PhysicalRate", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> physicalRate;
    @XmlElementRef(name = "CulturalRate", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> culturalRate;
    @XmlElementRef(name = "CarbonNeutral", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> carbonNeutral;
    @XmlElement(name = "Introduction")
    protected String introduction;
    @XmlElement(name = "Itinerary")
    protected String itinerary;
    @XmlElementRef(name = "Inclusion", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> inclusion;
    @XmlElementRef(name = "Exclusion", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> exclusion;
    @XmlElementRef(name = "VoucherExchange", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> voucherExchange;
    @XmlElementRef(name = "AdditionalNote", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> additionalNote;
    @XmlElementRef(name = "Confirmation", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> confirmation;
    @XmlElementRef(name = "YourTrip", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> yourTrip;
    @XmlElementRef(name = "DressStandard", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> dressStandard;
    @XmlElementRef(name = "Tipping", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> tipping;
    @XmlElementRef(name = "Closure", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> closure;
    @XmlElementRef(name = "ChildPolicy", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> childPolicy;
    @XmlElement(name = "MinChildAge")
    protected Integer minChildAge;
    @XmlElement(name = "MaxChildAge")
    protected Integer maxChildAge;
    @XmlElement(name = "Departure", nillable = true)
    protected List<ClsTripDeparture> departure;
    @XmlElement(name = "Photo", nillable = true)
    protected List<ClsTripPhoto> photo;
    @XmlElementRef(name = "ContactPhone", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<String> contactPhone;

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
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDestination(Integer value) {
        this.destination = value;
    }

    /**
     * Gets the value of the validFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the value of the validFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidFrom(XMLGregorianCalendar value) {
        this.validFrom = value;
    }

    /**
     * Gets the value of the validTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidTo() {
        return validTo;
    }

    /**
     * Sets the value of the validTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidTo(XMLGregorianCalendar value) {
        this.validTo = value;
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
     * Gets the value of the available property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvailable() {
        return available;
    }

    /**
     * Sets the value of the available property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvailable(String value) {
        this.available = value;
    }

    /**
     * Gets the value of the pickupLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPickupLocation() {
        return pickupLocation;
    }

    /**
     * Sets the value of the pickupLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPickupLocation(String value) {
        this.pickupLocation = value;
    }

    /**
     * Gets the value of the dropoffLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDropoffLocation() {
        return dropoffLocation;
    }

    /**
     * Sets the value of the dropoffLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDropoffLocation(String value) {
        this.dropoffLocation = value;
    }

    /**
     * Gets the value of the groupMin property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGroupMin() {
        return groupMin;
    }

    /**
     * Sets the value of the groupMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGroupMin(Integer value) {
        this.groupMin = value;
    }

    /**
     * Gets the value of the groupMax property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGroupMax() {
        return groupMax;
    }

    /**
     * Sets the value of the groupMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGroupMax(Integer value) {
        this.groupMax = value;
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
     * Gets the value of the childrenPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getChildrenPrice() {
        return childrenPrice;
    }

    /**
     * Sets the value of the childrenPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setChildrenPrice(Float value) {
        this.childrenPrice = value;
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
     * Gets the value of the cancelPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCancelPolicy() {
        return cancelPolicy;
    }

    /**
     * Sets the value of the cancelPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCancelPolicy(JAXBElement<String> value) {
        this.cancelPolicy = ((JAXBElement<String> ) value);
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
     * Gets the value of the highLights property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHighLights() {
        return highLights;
    }

    /**
     * Sets the value of the highLights property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHighLights(JAXBElement<String> value) {
        this.highLights = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the style property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStyle(JAXBElement<String> value) {
        this.style = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the hotelLocationRequired property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getHotelLocationRequired() {
        return hotelLocationRequired;
    }

    /**
     * Sets the value of the hotelLocationRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setHotelLocationRequired(JAXBElement<Boolean> value) {
        this.hotelLocationRequired = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the activeLang property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getActiveLang() {
        return activeLang;
    }

    /**
     * Sets the value of the activeLang property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setActiveLang(JAXBElement<String> value) {
        this.activeLang = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the physicalRate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPhysicalRate() {
        return physicalRate;
    }

    /**
     * Sets the value of the physicalRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPhysicalRate(JAXBElement<String> value) {
        this.physicalRate = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the culturalRate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCulturalRate() {
        return culturalRate;
    }

    /**
     * Sets the value of the culturalRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCulturalRate(JAXBElement<String> value) {
        this.culturalRate = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the carbonNeutral property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCarbonNeutral() {
        return carbonNeutral;
    }

    /**
     * Sets the value of the carbonNeutral property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCarbonNeutral(JAXBElement<String> value) {
        this.carbonNeutral = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the introduction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * Sets the value of the introduction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntroduction(String value) {
        this.introduction = value;
    }

    /**
     * Gets the value of the itinerary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItinerary() {
        return itinerary;
    }

    /**
     * Sets the value of the itinerary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItinerary(String value) {
        this.itinerary = value;
    }

    /**
     * Gets the value of the inclusion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInclusion() {
        return inclusion;
    }

    /**
     * Sets the value of the inclusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInclusion(JAXBElement<String> value) {
        this.inclusion = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the exclusion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getExclusion() {
        return exclusion;
    }

    /**
     * Sets the value of the exclusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setExclusion(JAXBElement<String> value) {
        this.exclusion = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the voucherExchange property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVoucherExchange() {
        return voucherExchange;
    }

    /**
     * Sets the value of the voucherExchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVoucherExchange(JAXBElement<String> value) {
        this.voucherExchange = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the additionalNote property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAdditionalNote() {
        return additionalNote;
    }

    /**
     * Sets the value of the additionalNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAdditionalNote(JAXBElement<String> value) {
        this.additionalNote = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the confirmation property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getConfirmation() {
        return confirmation;
    }

    /**
     * Sets the value of the confirmation property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setConfirmation(JAXBElement<String> value) {
        this.confirmation = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the yourTrip property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getYourTrip() {
        return yourTrip;
    }

    /**
     * Sets the value of the yourTrip property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setYourTrip(JAXBElement<String> value) {
        this.yourTrip = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dressStandard property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDressStandard() {
        return dressStandard;
    }

    /**
     * Sets the value of the dressStandard property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDressStandard(JAXBElement<String> value) {
        this.dressStandard = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tipping property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipping() {
        return tipping;
    }

    /**
     * Sets the value of the tipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipping(JAXBElement<String> value) {
        this.tipping = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the closure property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClosure() {
        return closure;
    }

    /**
     * Sets the value of the closure property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClosure(JAXBElement<String> value) {
        this.closure = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the childPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getChildPolicy() {
        return childPolicy;
    }

    /**
     * Sets the value of the childPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setChildPolicy(JAXBElement<String> value) {
        this.childPolicy = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the minChildAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinChildAge() {
        return minChildAge;
    }

    /**
     * Sets the value of the minChildAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinChildAge(Integer value) {
        this.minChildAge = value;
    }

    /**
     * Gets the value of the maxChildAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxChildAge() {
        return maxChildAge;
    }

    /**
     * Sets the value of the maxChildAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxChildAge(Integer value) {
        this.maxChildAge = value;
    }

    /**
     * Gets the value of the departure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the departure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeparture().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClsTripDeparture }
     * 
     * 
     */
    public List<ClsTripDeparture> getDeparture() {
        if (departure == null) {
            departure = new ArrayList<ClsTripDeparture>();
        }
        return this.departure;
    }

    /**
     * Gets the value of the photo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the photo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhoto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClsTripPhoto }
     * 
     * 
     */
    public List<ClsTripPhoto> getPhoto() {
        if (photo == null) {
            photo = new ArrayList<ClsTripPhoto>();
        }
        return this.photo;
    }

    /**
     * Gets the value of the contactPhone property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContactPhone() {
        return contactPhone;
    }

    /**
     * Sets the value of the contactPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContactPhone(JAXBElement<String> value) {
        this.contactPhone = ((JAXBElement<String> ) value);
    }

}
