/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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

@Deprecated
@XmlRootElement(name = "updateOrgOrgRelation", namespace = "http://student.kuali.org/wsdl/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOrgOrgRelation", namespace = "http://student.kuali.org/wsdl/organization", propOrder = {"orgOrgRelationId","orgOrgRelationInfo"})

public class UpdateOrgOrgRelation {

    @XmlElement(name = "orgOrgRelationId")
    private java.lang.String orgOrgRelationId;
    @XmlElement(name = "orgOrgRelationInfo")
    private org.kuali.student.core.organization.dto.OrgOrgRelationInfo orgOrgRelationInfo;

    public java.lang.String getOrgOrgRelationId() {
        return this.orgOrgRelationId;
    }

    public void setOrgOrgRelationId(java.lang.String newOrgOrgRelationId)  {
        this.orgOrgRelationId = newOrgOrgRelationId;
    }

    public org.kuali.student.core.organization.dto.OrgOrgRelationInfo getOrgOrgRelationInfo() {
        return this.orgOrgRelationInfo;
    }

    public void setOrgOrgRelationInfo(org.kuali.student.core.organization.dto.OrgOrgRelationInfo newOrgOrgRelationInfo)  {
        this.orgOrgRelationInfo = newOrgOrgRelationInfo;
    }

}

