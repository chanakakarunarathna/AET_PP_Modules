package com.urbanadventures.connector.domain.urbanadventures;


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="UACountryID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="UADestinationID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="DepartureFrom" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DepartureTo" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
    "uaCountryID",
    "uaDestinationID",
    "departureFrom",
    "departureTo"
})
@XmlRootElement(name = "clsGetTripListRQ")
public class ClsGetTripListRQ {

    @XmlElement(name = "Access")
    protected ClsAccessType access;
    @XmlElementRef(name = "UACountryID", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<Integer> uaCountryID;
    @XmlElementRef(name = "UADestinationID", namespace = "http://tempuri.org/urbanadventures.xsd", type = JAXBElement.class)
    protected JAXBElement<Integer> uaDestinationID;
    @XmlElement(name = "DepartureFrom", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar departureFrom;
    @XmlElement(name = "DepartureTo", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar departureTo;

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
     * Gets the value of the uaCountryID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getUACountryID() {
        return uaCountryID;
    }

    /**
     * Sets the value of the uaCountryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setUACountryID(JAXBElement<Integer> value) {
        this.uaCountryID = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the uaDestinationID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getUADestinationID() {
        return uaDestinationID;
    }

    /**
     * Sets the value of the uaDestinationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setUADestinationID(JAXBElement<Integer> value) {
        this.uaDestinationID = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the departureFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDepartureFrom() {
        return departureFrom;
    }

    /**
     * Sets the value of the departureFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDepartureFrom(XMLGregorianCalendar value) {
        this.departureFrom = value;
    }

    /**
     * Gets the value of the departureTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDepartureTo() {
        return departureTo;
    }

    /**
     * Sets the value of the departureTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDepartureTo(XMLGregorianCalendar value) {
        this.departureTo = value;
    }

}
