//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.10.29 at 10:22:39 AM PDT 
//


package org.kuali.student.core.enumerationmanagement.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


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
 *         &lt;element ref="{http://www.dto.enumeration.student.kuali.org}EnumeratedValue" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "", propOrder = {
//    "enumeratedValue"
//})
//@XmlRootElement(name = "enumeratedValueList")
public class EnumeratedValueList implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "EnumeratedValue", required = true)
    protected List<EnumeratedValue> enumeratedValue;

    /**
     * Gets the value of the enumeratedValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the enumeratedValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnumeratedValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnumeratedValue }
     * 
     * 
     */
    public List<EnumeratedValue> getEnumeratedValue() {
        if (enumeratedValue == null) {
            enumeratedValue = new ArrayList<EnumeratedValue>();
        }
        return this.enumeratedValue;
    }

    public void setEnumeratedValue(List<EnumeratedValue> l) {
        enumeratedValue = l;

    } 
}
