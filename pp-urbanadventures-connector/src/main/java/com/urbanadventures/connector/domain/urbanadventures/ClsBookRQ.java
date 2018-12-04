package com.urbanadventures.connector.domain.urbanadventures;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Access" type="{http://tempuri.org/urbanadventures.xsd}clsAccessType" minOccurs="0"/>
 *         &lt;element name="TripCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DepDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DepId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NumAdult" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NumChild" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PromoCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftCert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Traveller" type="{http://tempuri.org/urbanadventures.xsd}clsTravellerInfo" minOccurs="0"/>
 *         &lt;element name="Other" type="{http://tempuri.org/urbanadventures.xsd}clsTravellerOther" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="HotelLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Request" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "access",
    "tripCode",
    "depDate",
    "depId",
    "numAdult",
    "numChild",
    "promoCode",
    "giftCert",
    "traveller",
    "other",
    "hotelLocation",
    "request"
})
@XmlRootElement(name = "clsBookRQ")
public class ClsBookRQ {

    @XmlElement(name = "Access")
    protected ClsAccessType access;
    @XmlElement(name = "TripCode")
    protected String tripCode;
    @XmlElement(name = "DepDate")
    @XmlSchemaType(name = "date")
    protected String depDate;
    @XmlElement(name = "DepId")
    protected Integer depId;
    @XmlElement(name = "NumAdult")
    protected Integer numAdult;
    @XmlElement(name = "NumChild")
    protected Integer numChild;
    @XmlElement(name = "PromoCode")
    protected String promoCode;
    @XmlElement(name = "GiftCert")
    protected String giftCert;
    @XmlElement(name = "Traveller")
    protected ClsTravellerInfo traveller;
    @XmlElement(name = "Other")
    protected List<ClsTravellerOther> other;
    @XmlElement(name = "HotelLocation")
    protected String hotelLocation;
    @XmlElement(name = "Request")
    protected String request;

    /**
     * Gets the value of the access property.
     * 
     * @return
     *     possible object is
     *     {@link ClsAccessType }
     *     
     */
    public ClsAccessType getAccess() {
        return access;
    }

    /**
     * Sets the value of the access property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClsAccessType }
     *     
     */
    public void setAccess(ClsAccessType value) {
        this.access = value;
    }

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
     * Gets the value of the depDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepDate() {
        return depDate;
    }

    /**
     * Sets the value of the depDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepDate(String value) {
        this.depDate = value;
    }

    /**
     * Gets the value of the depId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDepId() {
        return depId;
    }

    /**
     * Sets the value of the depId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDepId(Integer value) {
        this.depId = value;
    }

    /**
     * Gets the value of the numAdult property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAdult() {
        return numAdult;
    }

    /**
     * Sets the value of the numAdult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAdult(Integer value) {
        this.numAdult = value;
    }

    /**
     * Gets the value of the numChild property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumChild() {
        return numChild;
    }

    /**
     * Sets the value of the numChild property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumChild(Integer value) {
        this.numChild = value;
    }

    /**
     * Gets the value of the promoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     * Sets the value of the promoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoCode(String value) {
        this.promoCode = value;
    }

    /**
     * Gets the value of the giftCert property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiftCert() {
        return giftCert;
    }

    /**
     * Sets the value of the giftCert property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiftCert(String value) {
        this.giftCert = value;
    }

    /**
     * Gets the value of the traveller property.
     * 
     * @return
     *     possible object is
     *     {@link ClsTravellerInfo }
     *     
     */
    public ClsTravellerInfo getTraveller() {
        return traveller;
    }

    /**
     * Sets the value of the traveller property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClsTravellerInfo }
     *     
     */
    public void setTraveller(ClsTravellerInfo value) {
        this.traveller = value;
    }

    /**
     * Gets the value of the other property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the other property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOther().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClsTravellerOther }
     * 
     * 
     */
    public List<ClsTravellerOther> getOther() {
        if (other == null) {
            other = new ArrayList<ClsTravellerOther>();
        }
        return this.other;
    }

    /**
     * Gets the value of the hotelLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelLocation() {
        return hotelLocation;
    }

    /**
     * Sets the value of the hotelLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelLocation(String value) {
        this.hotelLocation = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequest(String value) {
        this.request = value;
    }

}
