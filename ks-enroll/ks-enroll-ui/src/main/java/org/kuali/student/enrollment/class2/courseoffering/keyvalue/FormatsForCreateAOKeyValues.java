/**
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

package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class retrieves Formats based on the course and returns a key-value pair list of
 * the Activity Type name and the Activity Id
 *
 * @author andrewlubbers
 *
 */
public class FormatsForCreateAOKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        CourseOfferingManagementForm coForm = (CourseOfferingManagementForm) model;
        CourseOfferingManagementViewHelperServiceImpl helperService = ((CourseOfferingManagementViewHelperServiceImpl)coForm.getView().getViewHelperService());

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Format Type"));
        CourseOfferingInfo selectedCourseOffering = coForm.getTheCourseOffering();

        List<FormatInfo> formatInfos;
        try {
            formatInfos = helperService.getCourseService().getCourse(selectedCourseOffering.getCourseId()).getFormats();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            StringBuilder formatNameBuilder = new StringBuilder();
            for (FormatInfo formatInfo : formatInfos) {
                formatNameBuilder.setLength(0);
                // Create a derived name based on the activities, until https://jira.kuali.org/browse/KSENROLL-1518 is finished
                List<ActivityInfo> activities = formatInfo.getActivities();
                for (ActivityInfo activity : activities) {
                    if(formatNameBuilder.length() != 0) {
                        formatNameBuilder.append(" / ");
                    }
                    TypeInfo type = helperService.getTypeService().getType(activity.getActivityType(), helperService.getContextInfo());
                    formatNameBuilder.append(type.getName());
                }

                if(formatInfo.getActivities().size() == 1) {
                    formatNameBuilder.append(" Only");
                }

                keyValues.add(new ConcreteKeyValue(formatInfo.getId(), formatNameBuilder.toString()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return keyValues;
    }
}
