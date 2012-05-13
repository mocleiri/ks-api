/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by David Yin on 5/11/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SocRolloverResultItemInfoInquirableImpl extends InquirableImpl {
    private transient CourseOfferingSetService courseOfferingSetService;

    public final static String SOC_ROLLOVER_RESULT_ID = "socRolloverResultId";

    @Override
    public SocRolloverResultItemInfo retrieveDataObject(Map<String, String> parameters) {
        List<SocRolloverResultItemInfo> socRolloverResultItemInfos = new ArrayList<SocRolloverResultItemInfo>();
        String resultId = parameters.get(SOC_ROLLOVER_RESULT_ID);

        try {
            //TODO
            // Notes from Bonnie, your implementation is not generic since you always try to return the same one for inquiry.
            //inquiry should always use DTO's id as an input. You will count on lookup result id field to get into the inquiry page.
            //So you should call getCourseOfferingSetService().getSocRolloverResultItem(id,getContextInfo());
            socRolloverResultItemInfos = getCourseOfferingSetService().getSocRolloverResultItemsByResultId(resultId, getContextInfo());
        } catch (DoesNotExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MissingParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (OperationFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (PermissionDeniedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return socRolloverResultItemInfos.get(0);
    }





    public CourseOfferingSetService getCourseOfferingSetService() {
        if (courseOfferingSetService == null) {
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
