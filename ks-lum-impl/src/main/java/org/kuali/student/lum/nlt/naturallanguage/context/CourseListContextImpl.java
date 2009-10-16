/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.nlt.naturallanguage.context;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.ReqComponentTypes;

/**
 * This class creates the template context for course list types.
 */
public class CourseListContextImpl extends AbstractContext<CustomReqComponentInfo> {
    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(CustomReqComponentInfo reqComponent) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        
        contextMap.put(EXPECTED_VALUE_TOKEN, getReqCompFieldValue(reqComponent, ReqComponentTypes.ReqCompFieldTypes.REQUIRED_COUNT_KEY.getKey()));
        contextMap.put(OPERATOR_TOKEN, getReqCompFieldValue(reqComponent, ReqComponentTypes.ReqCompFieldTypes.OPERATOR_KEY.getKey()));
        contextMap.put(CLU_SET_TOKEN, getCluSet(reqComponent));

        return contextMap;
    }
}
