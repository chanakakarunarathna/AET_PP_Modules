package com.urbanadventures.connector.domain.urbanadventures;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pickupDropoffType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pickupDropoffType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="PickupInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="DropoffInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="LocationName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DateTime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pickupDropoffType")
public class PickupDropoffType {

    @XmlAttribute(name = "PickupInd", namespace = "http://tempuri.org/urbanadventures.xsd")
    protected Boolean pickupInd;
    @XmlAttribute(name = "DropoffInd", namespace = "http://tempuri.org/urbanadventures.xsd")
    protected Boolean dropoffInd;
    @XmlAttribute(name = "LocationName", namespace = "http://tempuri.org/urbanadventures.xsd")
    protected String locationName;
    @XmlAttribute(name = "DateTime", namespace = "http://tempuri.org/urbanadventures.xsd")
    protected String dateTime;

    /**
     * Gets the value of the pickupInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPickupInd() {
        return pickupInd;
    }

    /**
     * Sets the value of the pickupInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPickupInd(Boolean value) {
        this.pickupInd = value;
    }

    /**
     * Gets the value of the dropoffInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDropoffInd() {
        return dropoffInd;
    }

    /**
     * Sets the value of the dropoffInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDropoffInd(Boolean value) {
        this.dropoffInd = value;
    }

    /**
     * Gets the value of the locationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets the value of the locationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationName(String value) {
        this.locationName = value;
    }

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTime(String value) {
        this.dateTime = value;
    }

}
