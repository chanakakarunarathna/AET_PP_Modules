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
 *         &lt;element name="OpResult" type="{http://tempuri.org/urbanadventures.xsd}clsResultType" minOccurs="0"/>
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date" maxOccurs="unbounded" minOccurs="0"/>
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
    "opResult",
    "date"
})
@XmlRootElement(name = "clsTripAvailableDateRS")
public class ClsTripAvailableDateRS {

    @XmlElement(name = "OpResult")
    protected ClsResultType opResult;
    @XmlElement(name = "Date")
    @XmlSchemaType(name = "date")
    protected List<String> date;

    /**
     * Gets the value of the opResult property.
     * 
     * @return
     *     possible object is
     *     {@link ClsResultType }
     *     
     */
    public ClsResultType getOpResult() {
        return opResult;
    }

    /**
     * Sets the value of the opResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClsResultType }
     *     
     */
    public void setOpResult(ClsResultType value) {
        this.opResult = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the date property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDate() {
        if (date == null) {
            date = new ArrayList<String>();
        }
        return this.date;
    }

}
