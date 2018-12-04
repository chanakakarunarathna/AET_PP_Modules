package com.urbanadventures.connector.domain.urbanadventures;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for clsTripPrice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clsTripPrice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TripCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DepDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="PriceAdult" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="PriceChild" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="NetPriceAdult" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="NetPriceChild" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="TotalAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Commission" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clsTripPrice", propOrder = {
    "tripCode",
    "depDate",
    "priceAdult",
    "priceChild",
    "netPriceAdult",
    "netPriceChild",
    "totalAmount",
    "commission",
    "currency"
})
public class ClsTripPrice {

    @XmlElement(name = "TripCode", required = true)
    protected String tripCode;
    @XmlElement(name = "DepDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar depDate;
    @XmlElement(name = "PriceAdult")
    protected float priceAdult;
    @XmlElement(name = "PriceChild")
    protected float priceChild;
    @XmlElement(name = "NetPriceAdult")
    protected Float netPriceAdult;
    @XmlElement(name = "NetPriceChild")
    protected Float netPriceChild;
    @XmlElement(name = "TotalAmount")
    protected float totalAmount;
    @XmlElement(name = "Commission")
    protected float commission;
    @XmlElement(name = "Currency", required = true)
    protected String currency;

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
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDepDate() {
        return depDate;
    }

    /**
     * Sets the value of the depDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDepDate(XMLGregorianCalendar value) {
        this.depDate = value;
    }

    /**
     * Gets the value of the priceAdult property.
     * 
     */
    public float getPriceAdult() {
        return priceAdult;
    }

    /**
     * Sets the value of the priceAdult property.
     * 
     */
    public void setPriceAdult(float value) {
        this.priceAdult = value;
    }

    /**
     * Gets the value of the priceChild property.
     * 
     */
    public float getPriceChild() {
        return priceChild;
    }

    /**
     * Sets the value of the priceChild property.
     * 
     */
    public void setPriceChild(float value) {
        this.priceChild = value;
    }

    /**
     * Gets the value of the netPriceAdult property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getNetPriceAdult() {
        return netPriceAdult;
    }

    /**
     * Sets the value of the netPriceAdult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setNetPriceAdult(Float value) {
        this.netPriceAdult = value;
    }

    /**
     * Gets the value of the netPriceChild property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getNetPriceChild() {
        return netPriceChild;
    }

    /**
     * Sets the value of the netPriceChild property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setNetPriceChild(Float value) {
        this.netPriceChild = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     */
    public float getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     */
    public void setTotalAmount(float value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the commission property.
     * 
     */
    public float getCommission() {
        return commission;
    }

    /**
     * Sets the value of the commission property.
     * 
     */
    public void setCommission(float value) {
        this.commission = value;
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

}
