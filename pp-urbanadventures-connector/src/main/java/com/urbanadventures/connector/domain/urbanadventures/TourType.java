package com.urbanadventures.connector.domain.urbanadventures;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tourType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tourType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Confirmation" type="{http://tempuri.org/urbanadventures.xsd}confirmationType" minOccurs="0"/>
 *         &lt;element name="BasicInfo" type="{http://tempuri.org/urbanadventures.xsd}basicInfoType" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://tempuri.org/urbanadventures.xsd}descriptionType" minOccurs="0"/>
 *         &lt;element name="PickupDropoff" type="{http://tempuri.org/urbanadventures.xsd}pickupDropoffType" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="Schedule" type="{http://tempuri.org/urbanadventures.xsd}scheduleType" minOccurs="0"/>
 *         &lt;element name="ParticipantInfo" type="{http://tempuri.org/urbanadventures.xsd}participantInfoType" minOccurs="0"/>
 *         &lt;element name="Pricing" type="{http://tempuri.org/urbanadventures.xsd}pricingType" minOccurs="0"/>
 *         &lt;element name="SupplierOperator" type="{http://tempuri.org/urbanadventures.xsd}supplierOperatorType" maxOccurs="2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tourType", propOrder = {
    "confirmation",
    "basicInfo",
    "description",
    "pickupDropoff",
    "schedule",
    "participantInfo",
    "pricing",
    "supplierOperator"
})
public class TourType {

    @XmlElement(name = "Confirmation")
    protected ConfirmationType confirmation;
    @XmlElement(name = "BasicInfo")
    protected BasicInfoType basicInfo;
    @XmlElement(name = "Description")
    protected DescriptionType description;
    @XmlElement(name = "PickupDropoff")
    protected List<PickupDropoffType> pickupDropoff;
    @XmlElement(name = "Schedule")
    protected ScheduleType schedule;
    @XmlElement(name = "ParticipantInfo")
    protected ParticipantInfoType participantInfo;
    @XmlElement(name = "Pricing")
    protected PricingType pricing;
    @XmlElement(name = "SupplierOperator")
    protected List<SupplierOperatorType> supplierOperator;

    /**
     * Gets the value of the confirmation property.
     * 
     * @return
     *     possible object is
     *     {@link ConfirmationType }
     *     
     */
    public ConfirmationType getConfirmation() {
        return confirmation;
    }

    /**
     * Sets the value of the confirmation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfirmationType }
     *     
     */
    public void setConfirmation(ConfirmationType value) {
        this.confirmation = value;
    }

    /**
     * Gets the value of the basicInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BasicInfoType }
     *     
     */
    public BasicInfoType getBasicInfo() {
        return basicInfo;
    }

    /**
     * Sets the value of the basicInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicInfoType }
     *     
     */
    public void setBasicInfo(BasicInfoType value) {
        this.basicInfo = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link DescriptionType }
     *     
     */
    public DescriptionType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link DescriptionType }
     *     
     */
    public void setDescription(DescriptionType value) {
        this.description = value;
    }

    /**
     * Gets the value of the pickupDropoff property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pickupDropoff property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPickupDropoff().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PickupDropoffType }
     * 
     * 
     */
    public List<PickupDropoffType> getPickupDropoff() {
        if (pickupDropoff == null) {
            pickupDropoff = new ArrayList<PickupDropoffType>();
        }
        return this.pickupDropoff;
    }

    /**
     * Gets the value of the schedule property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleType }
     *     
     */
    public ScheduleType getSchedule() {
        return schedule;
    }

    /**
     * Sets the value of the schedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleType }
     *     
     */
    public void setSchedule(ScheduleType value) {
        this.schedule = value;
    }

    /**
     * Gets the value of the participantInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantInfoType }
     *     
     */
    public ParticipantInfoType getParticipantInfo() {
        return participantInfo;
    }

    /**
     * Sets the value of the participantInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantInfoType }
     *     
     */
    public void setParticipantInfo(ParticipantInfoType value) {
        this.participantInfo = value;
    }

    /**
     * Gets the value of the pricing property.
     * 
     * @return
     *     possible object is
     *     {@link PricingType }
     *     
     */
    public PricingType getPricing() {
        return pricing;
    }

    /**
     * Sets the value of the pricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link PricingType }
     *     
     */
    public void setPricing(PricingType value) {
        this.pricing = value;
    }

    /**
     * Gets the value of the supplierOperator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplierOperator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplierOperator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplierOperatorType }
     * 
     * 
     */
    public List<SupplierOperatorType> getSupplierOperator() {
        if (supplierOperator == null) {
            supplierOperator = new ArrayList<SupplierOperatorType>();
        }
        return this.supplierOperator;
    }

}
