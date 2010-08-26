
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getOrgOrgRelationTypesForOrgHierarchy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getOrgOrgRelationTypesForOrgHierarchy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgHierarchyKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrgOrgRelationTypesForOrgHierarchy", propOrder = {
    "orgHierarchyKey"
})
public class GetOrgOrgRelationTypesForOrgHierarchy {

    protected String orgHierarchyKey;

    /**
     * Gets the value of the orgHierarchyKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgHierarchyKey() {
        return orgHierarchyKey;
    }

    /**
     * Sets the value of the orgHierarchyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgHierarchyKey(String value) {
        this.orgHierarchyKey = value;
    }

}
