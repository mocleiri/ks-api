
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

@XmlRootElement(name = "getOrganizationsByIdList", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrganizationsByIdList", namespace = "http://org.kuali.student/core/organization")

public class GetOrganizationsByIdList {

    @XmlElement(name = "orgIdList")
    private java.util.List<java.lang.String> orgIdList;

    public java.util.List<java.lang.String> getOrgIdList() {
        return this.orgIdList;
    }

    public void setOrgIdList(java.util.List<java.lang.String> newOrgIdList)  {
        this.orgIdList = newOrgIdList;
    }

}

