package com.urbanadventures.connector.domain.urbanadventures;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
    "tripCode"
})
@XmlRootElement(name = "clsGetTripInfoRQ")
public class ClsGetTripInfoRQ {

    @XmlElement(name = "Access")
    protected ClsAccessType access;
    @XmlElement(name = "TripCode")
    protected String tripCode;

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

}
