package com.urbanadventures.connector.domain.urbanadventures;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
    "startDate",
    "endDate"
})
@XmlRootElement(name = "clsTripAvailableDateRQ")
public class ClsTripAvailableDateRQ {

    @XmlElement(name = "Access")
    protected ClsAccessType access;
    @XmlElement(name = "TripCode")
    protected String tripCode;
    @XmlElement(name = "StartDate")
    @XmlSchemaType(name = "date")
    protected String startDate;
    @XmlElement(name = "EndDate")
    @XmlSchemaType(name = "date")
    protected String endDate;

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
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

}
