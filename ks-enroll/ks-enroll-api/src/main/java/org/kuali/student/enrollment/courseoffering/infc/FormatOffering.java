/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;


/**
 * A Format Offering specifies the allowable Activity Offering Types
 * that may be offered.
 *
 * @author tom
 */

public interface FormatOffering 
    extends IdEntity {
    
    /**
     * The Course Offering Id to which this Format Offering belongs.
     *
     * @name Course Offering Id
     * @required
     * @readOnly
     */
    public String getCourseOfferingId();

    /**
     * Canonical Format this Format Offering corresponds
     * to. Currently, this is optional and Format Offerings may not
     * directly map to any canonical Format.
     *
     * @name Format Id
     */
    public String getFormatId();

    /**
     * Gets a list of Activity Offering Types within this Format
     * Offering.
     *
     * @name Activity Offering Types
     */
    public List<String> getActivityOfferingTypeKeys();
}
