/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lui.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

import org.kuali.student.r2.lum.lu.infc.LuCode;
import org.kuali.student.r2.lum.lu.infc.Expenditure;
import org.kuali.student.r2.lum.lu.infc.Fee;
import org.kuali.student.r2.lum.lu.infc.Revenue;

/**
 * Detailed information about a single LUI.
 */
public interface Lui extends IdEntity, HasEffectiveDates {

    /**
     *  The LUI identifier.
     *
     *  @name: Lui Identifier
     */
    public LuiIdentifier getOfficialIdentifier();

    /**
     *  The alternate LUI identifiers.
     *
     *  @name: Lui Alternate Identifiers
     */
    public List<? extends LuiIdentifier> getAlternateIdentifiers();

    /**
     * The unique identifier for the Canonical Learning Unit
     * (CLU) of which this is an instance.
     *
     * @name Clu Id
     */
    public String getCluId();

    /**
     * Any Clu Clu Relation identifiers on which this instance is
     * based. While the LUI is in instance of a CLU, the LUI may be an
     * instance of a set of CLUs related to the principal CLU, such as
     * a Course Format.
     *
     * @name Clu Clu Relation Ids
     */
    public List<String> getCluCluRelationIds();

    /**
     * The unique identifier for the Academic Time Period (ATP)
     * for which this instance is offered.
     *
     * @name Atp Key
     */
    public String getAtpKey();

    /**
     * List of LU code info structures. These are structures so that
     * many different types of codes can be associated with the
     * clu. This allows them to be put into categories.
     *
     * @name codes
     */
    public List<? extends LuCode> getLuiCodes();
 
    /**
     * The total maximum number of "seats" or enrollment slots that
     * can be filled for the lui.
     *
     * @name Maximum Enrollment
     */    
    public Integer getMaximumEnrollment();
    
    /**
     * Total minimum number of seats that must be filled for the lui.
     *
     * @name Minimum Enrollment
     */
    public Integer getMinimumEnrollment();

    /**
     * The reference URL for this LUI.
     *
     * @name referenceURL
     */
    public String getReferenceURL();

    /**
     * Organization(s) that is responsible for the delivery - and all
     * associated logistics - of the Lui.
     *
     * @name Units Deployment
     */
    public List<String> getUnitsDeployment();

    /**
     * Organization(s) that is responsible for the academic content of
     * the Lui as approved in its canonical form.
     *
     * @name Units Content Owner
     */
    public List<String> getUnitsContentOwner();

    /**
     * The options/scales that indicate the allowable grades that can
     * be awarded.  If the value is set here then the Clu must have a
     * grading option set on the canonical activity.
     * 
     * ResultValuesGroup will contain grade values valid for this
     * course offering
     * 
     * @name: Result Options Ids
     */
    public List<String> getResultOptionIds();
    
    /**
     * The fees associated with the course offering. 
     *
     * @name Fees
     */    
    public List<? extends Fee> getFees();
    
    /**
     * The organization that receives the revenue associated with
     * the course.
     *
     * @name Revenues
     */
    public List<? extends Revenue> getRevenues();
        
    /**
     * The organization that incurs the cost associated with the
     * course.
     *
     * @name Expenditure
     */
    public Expenditure getExpenditure();
}