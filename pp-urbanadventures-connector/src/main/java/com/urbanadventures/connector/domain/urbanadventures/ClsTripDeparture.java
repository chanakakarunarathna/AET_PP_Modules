package com.urbanadventures.connector.domain.urbanadventures;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clsTripDeparture complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clsTripDeparture">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DepMin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DepMax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EndMin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EndMax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clsTripDeparture", propOrder = {
    "id",
    "depMin",
    "depMax",
    "endMin",
    "endMax"
})
public class ClsTripDeparture {

    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "DepMin", required = true)
    protected String depMin;
    @XmlElement(name = "DepMax", required = true)
    protected String depMax;
    @XmlElement(name = "EndMin", required = true)
    protected String endMin;
    @XmlElement(name = "EndMax", required = true)
    protected String endMax;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the depMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepMin() {
        return depMin;
    }

    /**
     * Sets the value of the depMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepMin(String value) {
        this.depMin = value;
    }

    /**
     * Gets the value of the depMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepMax() {
        return depMax;
    }

    /**
     * Sets the value of the depMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepMax(String value) {
        this.depMax = value;
    }

    /**
     * Gets the value of the endMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndMin() {
        return endMin;
    }

    /**
     * Sets the value of the endMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndMin(String value) {
        this.endMin = value;
    }

    /**
     * Gets the value of the endMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndMax() {
        return endMax;
    }

    /**
     * Sets the value of the endMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndMax(String value) {
        this.endMax = value;
    }

}
