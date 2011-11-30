/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.lum.course.infc.CourseRevenue;

/**
 * Detailed information about revenue collected from the course.
 * 
 * @Author KSContractMojo
 * @Author Daniel Epstein
 * @Since Mon Jul 26 14:12:38 EDT 2010
 * @See <a href=
 *      "https://test.kuali.org/confluence/display/KULSTU/courseRevenueInfo+Structure"
 *      >CourseReenueInfo</>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseRevenueInfo extends IdNamelessEntityInfo implements CourseRevenue, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String feeType;

    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;

    /**
     * A code that identifies the type of the fee with which this revenue is
     * associated with.
     */
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    /**
     * List of affiliated organizations.
     */
    public List<AffiliatedOrgInfo> getAffiliatedOrgs() {
        if (affiliatedOrgs == null) {
            affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>(0);
        }
        return affiliatedOrgs;
    }

    public void setAffiliatedOrgs(List<AffiliatedOrgInfo> affiliatedOrgs) {
        this.affiliatedOrgs = affiliatedOrgs;
    }

}