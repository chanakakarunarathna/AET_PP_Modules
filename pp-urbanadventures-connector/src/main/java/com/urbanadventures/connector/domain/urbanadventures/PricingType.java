package com.urbanadventures.connector.domain.urbanadventures;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pricingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pricingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParticipantCategory" type="{http://tempuri.org/urbanadventures.xsd}participantCategoryType" maxOccurs="3" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pricingType", propOrder = {
    "participantCategory"
})
public class PricingType {

    @XmlElement(name = "ParticipantCategory")
    protected List<ParticipantCategoryType> participantCategory;

    /**
     * Gets the value of the participantCategory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participantCategory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipantCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParticipantCategoryType }
     * 
     * 
     */
    public List<ParticipantCategoryType> getParticipantCategory() {
        if (participantCategory == null) {
            participantCategory = new ArrayList<ParticipantCategoryType>();
        }
        return this.participantCategory;
    }

}
