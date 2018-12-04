package com.urbanadventures.connector.domain.urbanadventures;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for supplierOperatorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="supplierOperatorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="SupplierInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="OperatorInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "supplierOperatorType", propOrder = {
    "name"
})
public class SupplierOperatorType {

    @XmlElement(name = "Name")
    protected String name;
    @XmlAttribute(name = "SupplierInd", namespace = "http://tempuri.org/urbanadventures.xsd")
    protected Boolean supplierInd;
    @XmlAttribute(name = "OperatorInd", namespace = "http://tempuri.org/urbanadventures.xsd")
    protected Boolean operatorInd;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the supplierInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSupplierInd() {
        return supplierInd;
    }

    /**
     * Sets the value of the supplierInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSupplierInd(Boolean value) {
        this.supplierInd = value;
    }

    /**
     * Gets the value of the operatorInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOperatorInd() {
        return operatorInd;
    }

    /**
     * Sets the value of the operatorInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOperatorInd(Boolean value) {
        this.operatorInd = value;
    }

}
