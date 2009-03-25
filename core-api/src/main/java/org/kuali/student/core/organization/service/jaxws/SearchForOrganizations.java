
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

@XmlRootElement(name = "searchForOrganizations", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchForOrganizations", namespace = "http://org.kuali.student/core/organization")

public class SearchForOrganizations {

    @XmlElement(name = "orgCriteria")
    private org.kuali.student.core.organization.dto.OrgCriteriaInfo orgCriteria;

    public org.kuali.student.core.organization.dto.OrgCriteriaInfo getOrgCriteria() {
        return this.orgCriteria;
    }

    public void setOrgCriteria(org.kuali.student.core.organization.dto.OrgCriteriaInfo newOrgCriteria)  {
        this.orgCriteria = newOrgCriteria;
    }

}

