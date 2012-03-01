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
@XmlRootElement(name = "updatePositionRestrictionForOrg", namespace = "http://student.kuali.org/wsdl/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updatePositionRestrictionForOrg", namespace = "http://student.kuali.org/wsdl/organization", propOrder = {"orgId","orgPersonRelationTypeKey","orgPositionRestrictionInfo"})

public class UpdatePositionRestrictionForOrg {

    @XmlElement(name = "orgId")
    private java.lang.String orgId;
    @XmlElement(name = "orgPersonRelationTypeKey")
    private java.lang.String orgPersonRelationTypeKey;
    @XmlElement(name = "orgPositionRestrictionInfo")
    private org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo orgPositionRestrictionInfo;

    public java.lang.String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(java.lang.String newOrgId)  {
        this.orgId = newOrgId;
    }

    public java.lang.String getOrgPersonRelationTypeKey() {
        return this.orgPersonRelationTypeKey;
    }

    public void setOrgPersonRelationTypeKey(java.lang.String newOrgPersonRelationTypeKey)  {
        this.orgPersonRelationTypeKey = newOrgPersonRelationTypeKey;
    }

    public org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo getOrgPositionRestrictionInfo() {
        return this.orgPositionRestrictionInfo;
    }

    public void setOrgPositionRestrictionInfo(org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo newOrgPositionRestrictionInfo)  {
        this.orgPositionRestrictionInfo = newOrgPositionRestrictionInfo;
    }

}

