
package org.kuali.student.core.organization.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 16 11:42:39 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "updatePositionRestrictionForOrg", namespace = "http://service.organization.core.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updatePositionRestrictionForOrg", namespace = "http://service.organization.core.student.kuali.org/", propOrder = {"arg0","arg1","arg2"})

public class UpdatePositionRestrictionForOrg {

    @XmlElement(name = "arg0")
    private java.lang.String arg0;
    @XmlElement(name = "arg1")
    private java.lang.String arg1;
    @XmlElement(name = "arg2")
    private org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo arg2;

    public java.lang.String getArg0() {
        return this.arg0;
    }

    public void setArg0(java.lang.String newArg0)  {
        this.arg0 = newArg0;
    }

    public java.lang.String getArg1() {
        return this.arg1;
    }

    public void setArg1(java.lang.String newArg1)  {
        this.arg1 = newArg1;
    }

    public org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo getArg2() {
        return this.arg2;
    }

    public void setArg2(org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo newArg2)  {
        this.arg2 = newArg2;
    }

}

