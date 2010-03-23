
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Thu Jan 21 10:05:23 PST 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "validateCluLoRelation", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateCluLoRelation", namespace = "http://student.kuali.org/wsdl/lu", propOrder = {"validationType","cluLoRelationInfo"})

public class ValidateCluLoRelation {

    @XmlElement(name = "validationType")
    private java.lang.String validationType;
    @XmlElement(name = "cluLoRelationInfo")
    private org.kuali.student.lum.lu.dto.CluLoRelationInfo cluLoRelationInfo;

    public java.lang.String getValidationType() {
        return this.validationType;
    }

    public void setValidationType(java.lang.String newValidationType)  {
        this.validationType = newValidationType;
    }

    public org.kuali.student.lum.lu.dto.CluLoRelationInfo getCluLoRelationInfo() {
        return this.cluLoRelationInfo;
    }

    public void setCluLoRelationInfo(org.kuali.student.lum.lu.dto.CluLoRelationInfo newCluLoRelationInfo)  {
        this.cluLoRelationInfo = newCluLoRelationInfo;
    }

}

