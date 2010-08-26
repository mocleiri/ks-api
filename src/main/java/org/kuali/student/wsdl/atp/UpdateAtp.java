
package org.kuali.student.wsdl.atp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateAtp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateAtp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="atpKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="atpInfo" type="{http://student.kuali.org/wsdl/atp}atpInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateAtp", propOrder = {
    "atpKey",
    "atpInfo"
})
public class UpdateAtp {

    protected String atpKey;
    protected AtpInfo atpInfo;

    /**
     * Gets the value of the atpKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAtpKey() {
        return atpKey;
    }

    /**
     * Sets the value of the atpKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAtpKey(String value) {
        this.atpKey = value;
    }

    /**
     * Gets the value of the atpInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AtpInfo }
     *     
     */
    public AtpInfo getAtpInfo() {
        return atpInfo;
    }

    /**
     * Sets the value of the atpInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AtpInfo }
     *     
     */
    public void setAtpInfo(AtpInfo value) {
        this.atpInfo = value;
    }

}
