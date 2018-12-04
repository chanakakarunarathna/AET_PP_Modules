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
 *         &lt;element name="OpResult" type="{http://tempuri.org/urbanadventures.xsd}clsResultType" minOccurs="0"/>
 *         &lt;element name="PriceInfo" type="{http://tempuri.org/urbanadventures.xsd}clsTripPrice" minOccurs="0"/>
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
    "priceInfo"
})
@XmlRootElement(name = "clsGetPriceRS")
public class ClsGetPriceRS {

    @XmlElement(name = "OpResult")
    protected ClsResultType opResult;
    @XmlElement(name = "PriceInfo")
    protected ClsTripPrice priceInfo;

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
     * Gets the value of the priceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ClsTripPrice }
     *     
     */
    public ClsTripPrice getPriceInfo() {
        return priceInfo;
    }

    /**
     * Sets the value of the priceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClsTripPrice }
     *     
     */
    public void setPriceInfo(ClsTripPrice value) {
        this.priceInfo = value;
    }

}
