
package org.kuali.student.wsdl.atp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateMilestone complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateMilestone">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="milestoneInfo" type="{http://student.kuali.org/wsdl/atp}milestoneInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateMilestone", propOrder = {
    "validationType",
    "milestoneInfo"
})
public class ValidateMilestone {

    protected String validationType;
    protected MilestoneInfo milestoneInfo;

    /**
     * Gets the value of the validationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationType() {
        return validationType;
    }

    /**
     * Sets the value of the validationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationType(String value) {
        this.validationType = value;
    }

    /**
     * Gets the value of the milestoneInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MilestoneInfo }
     *     
     */
    public MilestoneInfo getMilestoneInfo() {
        return milestoneInfo;
    }

    /**
     * Sets the value of the milestoneInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MilestoneInfo }
     *     
     */
    public void setMilestoneInfo(MilestoneInfo value) {
        this.milestoneInfo = value;
    }

}
