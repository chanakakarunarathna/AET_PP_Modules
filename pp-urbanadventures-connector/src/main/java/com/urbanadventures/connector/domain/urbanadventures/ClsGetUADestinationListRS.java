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
 *         &lt;element name="DestinationList" type="{http://tempuri.org/urbanadventures.xsd}clsDestinationInfo" maxOccurs="unbounded" minOccurs="0"/>
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
    "destinationList"
})
@XmlRootElement(name = "clsGetUADestinationListRS")
public class ClsGetUADestinationListRS {

    @XmlElement(name = "OpResult")
    protected ClsResultType opResult;
    @XmlElement(name = "DestinationList")
    protected List<ClsDestinationInfo> destinationList;

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
     * Gets the value of the destinationList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinationList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinationList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClsDestinationInfo }
     * 
     * 
     */
    public List<ClsDestinationInfo> getDestinationList() {
        if (destinationList == null) {
            destinationList = new ArrayList<ClsDestinationInfo>();
        }
        return this.destinationList;
    }

}
