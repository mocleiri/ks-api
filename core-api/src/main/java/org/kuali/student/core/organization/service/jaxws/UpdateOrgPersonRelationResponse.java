
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

@XmlRootElement(name = "updateOrgPersonRelationResponse", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOrgPersonRelationResponse", namespace = "http://org.kuali.student/core/organization")

public class UpdateOrgPersonRelationResponse {

    @XmlElement(name = "return")
    private org.kuali.student.core.organization.dto.OrgPersonRelationInfo _return;

    public org.kuali.student.core.organization.dto.OrgPersonRelationInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.core.organization.dto.OrgPersonRelationInfo new_return)  {
        this._return = new_return;
    }

}

