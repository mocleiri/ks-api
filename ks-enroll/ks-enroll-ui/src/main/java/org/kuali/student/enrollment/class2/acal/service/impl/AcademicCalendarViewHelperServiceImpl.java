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
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import java.util.List;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import javax.xml.namespace.QName;

/**
 * This class implement ViewHelperServiceImpl for  all AcademicCalendar views
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarViewHelperServiceImpl extends ViewHelperServiceImpl implements AcademicCalendarViewHelperService {
    private AcademicCalendarService acalService;

    public AcademicCalendarService getAcalService() {
           if(acalService == null) {
             acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    public HolidayCalendarInfo createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        hcInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        hcInfo.setTypeKey(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        //create dummy descr for db AtpEntity.plain is not nullable
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(hcInfo.getName());
        hcInfo.setDescr(rti);
        HolidayCalendarInfo createdHc = getAcalService().createHolidayCalendar(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY, hcInfo, getContextInfo());
        return createdHc;
    }

    public HolidayCalendarInfo getHolidayCalendar(String hcId) throws Exception{
        HolidayCalendarInfo retrievedHc = getAcalService().getHolidayCalendar(hcId, getContextInfo());
        return retrievedHc;
    }

    public HolidayCalendarInfo updateHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();

        HolidayCalendarInfo updatedHc = getAcalService().updateHolidayCalendar(hcInfo.getId(), hcInfo, getContextInfo());

        return updatedHc;
    }
    public List<HolidayInfo> getHolidaysForHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        List<HolidayInfo> holidays = getAcalService().getHolidaysForHolidayCalendar(hcInfo.getId(), getContextInfo());

        return holidays;
    }

    public HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo) throws Exception {
        holidayInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        HolidayInfo createdHoliday = getAcalService().createHoliday(holidayCalendarId, holidayTypeKey, holidayInfo, getContextInfo());
        return createdHoliday;
    }

    public HolidayInfo updateHoliday(String holidayId, HolidayInfo holidayInfo) throws Exception {
        getAcalService().updateHoliday(holidayId, holidayInfo, getContextInfo());
        return getAcalService().getHoliday(holidayId, getContextInfo());
    }

    public void deleteHoliday(String holidayId) throws Exception{
        getAcalService().deleteHoliday(holidayId, getContextInfo());
    }

    public AcademicCalendarInfo createAcademicCalendar(AcademicCalendarForm acalForm) throws Exception{
        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        acalInfo.setStateKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_DRAFT_STATE_KEY);
        acalInfo.setTypeKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
        AcademicCalendarInfo newAcal = getAcalService().createAcademicCalendar(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY, acalInfo, getContextInfo());
        return newAcal;
    }

    public AcademicCalendarInfo getAcademicCalendar(String acalId) throws Exception {
        AcademicCalendarInfo acalInfo = getAcalService().getAcademicCalendar(acalId, getContextInfo());
        return acalInfo;
    }

    public AcademicCalendarInfo updateAcademicCalendar(AcademicCalendarForm acalForm) throws Exception{
        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        AcademicCalendarInfo updatedAcalInfo = getAcalService().updateAcademicCalendar(acalInfo.getId(), acalInfo, getContextInfo());
        return updatedAcalInfo;
    }
    private ContextInfo getContextInfo(){
        return new ContextInfo();
    }
}
