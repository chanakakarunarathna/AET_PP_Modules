package com.urbanadventures.connector.domain.urbanadventures;


import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="UACountryList" type="{http://tempuri.org/urbanadventures.xsd}clsItem" maxOccurs="unbounded" minOccurs="0"/>
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
    "uaCountryList"
})
@XmlRootElement(name = "clsGetUACountryListRS")
public class ClsGetUACountryListRS {

    @XmlElement(name = "OpResult")
    protected ClsResultType opResult;
    @XmlElement(name = "UACountryList")
    protected List<ClsItem> uaCountryList;

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
     * Gets the value of the uaCountryList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uaCountryList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUACountryList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClsItem }
     * 
     * 
     */
    public List<ClsItem> getUACountryList() {
        if (uaCountryList == null) {
            uaCountryList = new ArrayList<ClsItem>();
        }
        return this.uaCountryList;
    }

}
