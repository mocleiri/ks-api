
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getOrgOrgRelationsByRelatedOrg complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getOrgOrgRelationsByRelatedOrg">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relatedOrgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrgOrgRelationsByRelatedOrg", propOrder = {
    "relatedOrgId"
})
public class GetOrgOrgRelationsByRelatedOrg {

    protected String relatedOrgId;

    /**
     * Gets the value of the relatedOrgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedOrgId() {
        return relatedOrgId;
    }

    /**
     * Sets the value of the relatedOrgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedOrgId(String value) {
        this.relatedOrgId = value;
    }

}
