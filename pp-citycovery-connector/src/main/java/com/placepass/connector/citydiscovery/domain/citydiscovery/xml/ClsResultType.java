package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "OTA_ErrorRS")
public class ClsResultType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "ErrorCode")
    protected int errorCode;
    @XmlAttribute(name = "ErrorMessage")
    protected String errorMessage;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int value) {
        this.errorCode = value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

}
