/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * Course Offering Service Constants
 * @see LuiServiceConstants
 *
 * @author nwright
 */
public class CourseOfferingSetServiceConstants {

   
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseOfferingSet";
    public static final String SERVICE_NAME_LOCAL_PART = "socService";
    public static final String REF_OBJECT_URI_SOC = NAMESPACE + "/" + SocInfo.class.getSimpleName();
    
    public static final String MAIN_SOC_TYPE_KEY = "kuali.soc.type.main";
    public static final String ROLLOVER_RESULTS_TYPE_KEY = "kuali.soc.rollover.results.rollover";
    public static final String REVERSE_ROLLOVER_RESULTS_TYPE_KEY = "kuali.soc.rollover.results.reverse";
}
