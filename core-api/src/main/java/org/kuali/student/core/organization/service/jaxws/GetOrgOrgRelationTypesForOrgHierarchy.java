
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

@XmlRootElement(name = "getOrgOrgRelationTypesForOrgHierarchy", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrgOrgRelationTypesForOrgHierarchy", namespace = "http://org.kuali.student/core/organization")

public class GetOrgOrgRelationTypesForOrgHierarchy {

    @XmlElement(name = "orgHierarchyKey")
    private java.lang.String orgHierarchyKey;

    public java.lang.String getOrgHierarchyKey() {
        return this.orgHierarchyKey;
    }

    public void setOrgHierarchyKey(java.lang.String newOrgHierarchyKey)  {
        this.orgHierarchyKey = newOrgHierarchyKey;
    }

}

