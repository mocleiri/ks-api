/**
 * Copyright 2013 The Kuali Foundation
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
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.waitlist.dto.WaitListEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * This class holds constants used by the WaitList Service
 */
public class WaitListServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "waitlist";
    public static final String SERVICE_NAME_LOCAL_PART = "WaitListService";
    public static final String REF_OBJECT_URI_WAIT_LIST = NAMESPACE + "/" + WaitListInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ISSUE = NAMESPACE + "/" + WaitListEntryInfo.class.getSimpleName();

}
