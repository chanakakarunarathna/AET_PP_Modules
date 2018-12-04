package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="AgentSine" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="AgentDutyCode" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="BookingReferenceCityDiscovery" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "bookingReferenceCityDiscovery",
    "voucher"
})
@XmlRootElement(name = "CityDiscovery")
public class ClsCancelBookingRQ {

    @XmlElement(name = "POS", required = true)
    protected ClsBookStatusRQ.POS pos;
    @XmlElement(name = "BookingReferenceCityDiscovery", required = true)
    protected String bookingReferenceCityDiscovery;
    @XmlElement(name = "Voucher", required = true)
    protected String voucher;
    @XmlAttribute(name = "ProcessType")
    protected String processType;

    /**
     * Gets the value of the pos property.
     * 
     * @return
     *     possible object is
     *     {@link ClsBookStatusRQ.POS }
     *     
     */
    public ClsBookStatusRQ.POS getPOS() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClsBookStatusRQ.POS }
     *     
     */
    public void setPOS(ClsBookStatusRQ.POS value) {
        this.pos = value;
    }

    /**
     * Gets the value of the bookingReferenceCityDiscovery property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingReferenceCityDiscovery() {
        return bookingReferenceCityDiscovery;
    }

    /**
     * Sets the value of the bookingReferenceCityDiscovery property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingReferenceCityDiscovery(String value) {
        this.bookingReferenceCityDiscovery = value;
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

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
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
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="AgentSine" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="AgentDutyCode" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        protected ClsBookStatusRQ.POS.Source source;

        /**
         * Gets the value of the source property.
         * 
         * @return
         *     possible object is
         *     {@link ClsBookStatusRQ.POS.Source }
         *     
         */
        public ClsBookStatusRQ.POS.Source getSource() {
            return source;
        }

        /**
         * Sets the value of the source property.
         * 
         * @param value
         *     allowed object is
         *     {@link ClsBookStatusRQ.POS.Source }
         *     
         */
        public void setSource(ClsBookStatusRQ.POS.Source value) {
            this.source = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="AgentSine" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="AgentDutyCode" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Source {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "AgentSine")
            protected String agentSine;
            @XmlAttribute(name = "AgentDutyCode")
            protected String agentDutyCode;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

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
