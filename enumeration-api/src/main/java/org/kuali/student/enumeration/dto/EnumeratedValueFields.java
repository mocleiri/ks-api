//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.10.29 at 10:22:39 AM PDT 
//


package org.kuali.student.enumeration.dto;

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
 *         &lt;element ref="{http://www.dto.enumeration.student.kuali.org}enumeratedValueField"/>
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
    "enumeratedValueField"
})
@XmlRootElement(name = "enumeratedValueFields")
public class EnumeratedValueFields {

    @XmlElement(required = true)
    protected EnumeratedValueField enumeratedValueField;

    /**
     * Gets the value of the enumeratedValueField property.
     * 
     * @return
     *     possible object is
     *     {@link EnumeratedValueField }
     *     
     */
    public EnumeratedValueField getEnumeratedValueField() {
        return enumeratedValueField;
    }

    /**
     * Sets the value of the enumeratedValueField property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumeratedValueField }
     *     
     */
    public void setEnumeratedValueField(EnumeratedValueField value) {
        this.enumeratedValueField = value;
    }

}
