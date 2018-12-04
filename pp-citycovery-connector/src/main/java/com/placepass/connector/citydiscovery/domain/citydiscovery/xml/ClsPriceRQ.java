//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.13 at 08:05:50 PM EDT 
//


package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="POS">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Source">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="AgentSine" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="AgentDutyCode" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ActivityID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActivityDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ActivityNumberAdults" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ActivityNumberChildren" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ActivityNumberInfant" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ActivityPriceCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ActivityContentLanguage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ActivityPromoCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ProcessType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pos",
    "activityID",
    "activityDate",
    "activityNumberAdults",
    "activityNumberChildren",
    "activityNumberInfant",
    "activityPriceCurrency",
    "activityContentLanguage",
    "activityPromoCode"
})
@XmlRootElement(name = "CityDiscovery")
public class ClsPriceRQ {

    @XmlElement(name = "POS", required = true)
    protected ClsPriceRQ.POS pos;
    @XmlElement(name = "ActivityID")
    protected int activityID;
    @XmlElement(name = "ActivityDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected String activityDate;
    @XmlElement(name = "ActivityNumberAdults", required = true)
    protected String activityNumberAdults;
    @XmlElement(name = "ActivityNumberChildren", required = true)
    protected String activityNumberChildren;
    @XmlElement(name = "ActivityNumberInfant", required = true)
    protected String activityNumberInfant;
    @XmlElement(name = "ActivityPriceCurrency", required = true)
    protected String activityPriceCurrency;
    @XmlElement(name = "ActivityContentLanguage", required = true)
    protected String activityContentLanguage;
    @XmlElement(name = "ActivityPromoCode", required = true)
    protected String activityPromoCode;
    @XmlAttribute(name = "ProcessType")
    protected String processType;

    /**
     * Gets the value of the pos property.
     * 
     * @return
     *     possible object is
     *     {@link ClsPriceRQ.POS }
     *     
     */
    public ClsPriceRQ.POS getPOS() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClsPriceRQ.POS }
     *     
     */
    public void setPOS(ClsPriceRQ.POS value) {
        this.pos = value;
    }

    /**
     * Gets the value of the activityID property.
     * 
     */
    public int getActivityID() {
        return activityID;
    }

    /**
     * Sets the value of the activityID property.
     * 
     */
    public void setActivityID(int value) {
        this.activityID = value;
    }

    /**
     * Gets the value of the activityDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public String getActivityDate() {
        return activityDate;
    }

    /**
     * Sets the value of the activityDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActivityDate(String value) {
        this.activityDate = value;
    }

    /**
     * Gets the value of the activityNumberAdults property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityNumberAdults() {
        return activityNumberAdults;
    }

    /**
     * Sets the value of the activityNumberAdults property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityNumberAdults(String value) {
        this.activityNumberAdults = value;
    }

    /**
     * Gets the value of the activityNumberChildren property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityNumberChildren() {
        return activityNumberChildren;
    }

    /**
     * Sets the value of the activityNumberChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityNumberChildren(String value) {
        this.activityNumberChildren = value;
    }

    /**
     * Gets the value of the activityNumberInfant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityNumberInfant() {
        return activityNumberInfant;
    }

    /**
     * Sets the value of the activityNumberInfant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityNumberInfant(String value) {
        this.activityNumberInfant = value;
    }

    /**
     * Gets the value of the activityPriceCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityPriceCurrency() {
        return activityPriceCurrency;
    }

    /**
     * Sets the value of the activityPriceCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityPriceCurrency(String value) {
        this.activityPriceCurrency = value;
    }

    /**
     * Gets the value of the activityContentLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityContentLanguage() {
        return activityContentLanguage;
    }

    /**
     * Sets the value of the activityContentLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityContentLanguage(String value) {
        this.activityContentLanguage = value;
    }

    /**
     * Gets the value of the activityPromoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityPromoCode() {
        return activityPromoCode;
    }

    /**
     * Sets the value of the activityPromoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityPromoCode(String value) {
        this.activityPromoCode = value;
    }

    /**
     * Gets the value of the processType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessType() {
        return processType;
    }

    /**
     * Sets the value of the processType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessType(String value) {
        this.processType = value;
    }


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
     *         &lt;element name="Source">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="AgentSine" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="AgentDutyCode" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "source"
    })
    public static class POS {

        @XmlElement(name = "Source", required = true)
        protected ClsPriceRQ.POS.Source source;

        /**
         * Gets the value of the source property.
         * 
         * @return
         *     possible object is
         *     {@link ClsPriceRQ.POS.Source }
         *     
         */
        public ClsPriceRQ.POS.Source getSource() {
            return source;
        }

        /**
         * Sets the value of the source property.
         * 
         * @param value
         *     allowed object is
         *     {@link ClsPriceRQ.POS.Source }
         *     
         */
        public void setSource(ClsPriceRQ.POS.Source value) {
            this.source = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="AgentSine" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="AgentDutyCode" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Source {

            @XmlAttribute(name = "AgentSine")
            protected String agentSine;
            @XmlAttribute(name = "AgentDutyCode")
            protected String agentDutyCode;

            /**
             * Gets the value of the agentSine property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAgentSine() {
                return agentSine;
            }

            /**
             * Sets the value of the agentSine property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAgentSine(String value) {
                this.agentSine = value;
            }

            /**
             * Gets the value of the agentDutyCode property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public String getAgentDutyCode() {
                return agentDutyCode;
            }

            /**
             * Sets the value of the agentDutyCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setAgentDutyCode(String value) {
                this.agentDutyCode = value;
            }

        }

    }

}
