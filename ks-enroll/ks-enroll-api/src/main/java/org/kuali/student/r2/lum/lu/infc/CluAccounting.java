/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.lum.lu.infc;

import org.kuali.student.r2.common.infc.HasAttributes;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.lum.lu.dto.AffiliatedOrgInfo;

import java.util.List;

/**
 * Detailed information about accounting for a clu.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface CluAccounting extends HasAttributes, HasId {
    /**
     * List of Affiliated Orgs
     *
     * @name Affiliated Orgs
     */
    List<AffiliatedOrgInfo> getAffiliatedOrgs();
}
