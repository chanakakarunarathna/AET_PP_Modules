package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="ActivityCancellationPolicy" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="Day" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="Percentage" type="{http://www.w3.org/2001/XMLSchema}int" />
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
@XmlType(name = "", propOrder = {"activityCancellationPolicy"})
public class ClsActivityCancellation {

    @XmlElement(name = "ActivityCancellationPolicy")
    protected List<ClsActivityCancellationPolicy> activityCancellationPolicy;

    /**
     * Gets the value of the activityCancellationPolicy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the activityCancellationPolicy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getActivityCancellationPolicy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClsBookInfoRS.ActivityCancellation.ActivityCancellationPolicy }
     * 
     * 
     */
    public List<ClsActivityCancellationPolicy> getActivityCancellationPolicy() {
        if (activityCancellationPolicy == null) {
            activityCancellationPolicy = new ArrayList<ClsActivityCancellationPolicy>();
        }
        return this.activityCancellationPolicy;
    }
}
