
package org.kuali.student.core.organization.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Wed Jan 21 13:23:57 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getOrgOrgRelation", namespace = "http://student.kuali.org/wsdl/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrgOrgRelation", namespace = "http://student.kuali.org/wsdl/organization")

public class GetOrgOrgRelation {

    @XmlElement(name = "orgOrgRelationId")
    private java.lang.String orgOrgRelationId;

    public java.lang.String getOrgOrgRelationId() {
        return this.orgOrgRelationId;
    }

    public void setOrgOrgRelationId(java.lang.String newOrgOrgRelationId)  {
        this.orgOrgRelationId = newOrgOrgRelationId;
    }

}

