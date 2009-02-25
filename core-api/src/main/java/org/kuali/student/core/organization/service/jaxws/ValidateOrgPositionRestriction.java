
package org.kuali.student.core.organization.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Wed Jan 21 13:23:58 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "validateOrgPositionRestriction", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateOrgPositionRestriction", namespace = "http://org.kuali.student/core/organization", propOrder = {"validationType","orgPositionRestrictionInfo"})

public class ValidateOrgPositionRestriction {

    @XmlElement(name = "validationType")
    private java.lang.String validationType;
    @XmlElement(name = "orgPositionRestrictionInfo")
    private org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo orgPositionRestrictionInfo;

    public java.lang.String getValidationType() {
        return this.validationType;
    }

    public void setValidationType(java.lang.String newValidationType)  {
        this.validationType = newValidationType;
    }

    public org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo getOrgPositionRestrictionInfo() {
        return this.orgPositionRestrictionInfo;
    }

    public void setOrgPositionRestrictionInfo(org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo newOrgPositionRestrictionInfo)  {
        this.orgPositionRestrictionInfo = newOrgPositionRestrictionInfo;
    }

}

