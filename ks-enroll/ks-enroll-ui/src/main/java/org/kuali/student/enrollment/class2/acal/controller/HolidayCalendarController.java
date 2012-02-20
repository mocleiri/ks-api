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
 * Created by Li Pan on 1/18/12
 */
package org.kuali.student.enrollment.class2.acal.controller;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * This class implement controller for HolidayCalendar
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/holidayCalendar")
public class HolidayCalendarController extends UifControllerBase {
    private AcademicCalendarViewHelperService acalHelper;
    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new HolidayCalendarForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addLine")
    public ModelAndView addLine(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarForm hcForm = (HolidayCalendarForm) form;
        List<HolidayInfo> holidays = hcForm.getHolidays();
        Map<String, Object> newCollectionLines = form.getNewCollectionLines();

        if(newCollectionLines != null && !newCollectionLines.isEmpty()){
            if(holidays != null && !holidays.isEmpty()){
                GlobalVariables.getMessageMap().removeAllErrorMessagesForProperty(KRADConstants.GLOBAL_ERRORS);
                for (Map.Entry<String, Object> entry : newCollectionLines.entrySet()){
                    HolidayInfo newHoliday = (HolidayInfo)entry.getValue();
                    for(HolidayInfo holiday : holidays){
                        boolean duplicated = isDuplicateHoliday(newHoliday, holiday);
                        if(duplicated){
                            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "The Holiday is already in the collection.");
                            return updateComponent(form, result, request, response);
                        }
                    }
                }
            }
        }

        return super.addLine(form, result, request, response);
    }

    private boolean isDuplicateHoliday(HolidayInfo newHoliday, HolidayInfo sourceHoliday){
        return (newHoliday.getTypeKey().equals(sourceHoliday.getTypeKey()) &&
                       newHoliday.getStartDate().equals(sourceHoliday.getStartDate()) &&
                       newHoliday.getEndDate().equals(sourceHoliday.getEndDate()));

    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarForm hcForm = (HolidayCalendarForm) form;

        String hcId = request.getParameter("hcId");
        if ((hcId != null) && !hcId.trim().isEmpty()) {
            String viewId = request.getParameter("viewId");
            if ("holidayCalendarView".equals(viewId)) {
                hcForm.setViewTypeName(UifConstants.ViewType.INQUIRY);
            }

            try {
                getHolidayCalendar(hcId, hcForm);
            } catch (Exception ex) {
            }
        }

        return super.start(form, result, request, response);
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=startNew")
    public ModelAndView startNew( @ModelAttribute("KualiForm") HolidayCalendarForm form, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarInfo hcInfo = null;
        try {
            hcInfo = getHolidayCalendarFormHelper(form).getNewestHolidayCalendar();
            form.setHolidayCalendarInfo(hcInfo);
        }
        catch (Exception x) {
            //TODO - what to do here?
        }

        if (null != hcInfo) {
            // do some calculations on probable values for the new calendar
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            cal.setTime(hcInfo.getEndDate());
            int year = cal.get(Calendar.YEAR);
            if (year == currentYear) {
                StringBuilder calName = new StringBuilder();
                calName.append(++year);
                calName.append(" Holiday Calendar");
                form.setNewCalendarName(calName.toString());
                cal.add(Calendar.YEAR, 1);
                form.setNewCalendarEndDate(cal.getTime());
                cal.setTime(hcInfo.getStartDate());
                cal.add(Calendar.YEAR, 1);
                form.setNewCalendarStartDate(cal.getTime());
            }
        }

        return super.start(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=copy")
    public ModelAndView copy( @ModelAttribute("KualiForm") HolidayCalendarForm form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        if ((null == form.getHolidayCalendarInfo()) || (null == form.getHolidayCalendarInfo().getId())) {
            //TODO - display some kind of error
            return getUIFModelAndView(form);
        }

        HolidayCalendarInfo newHCInfo = null;
        try {
            newHCInfo = getHolidayCalendarFormHelper(form).copyHolidayCalendar(form);
        }
        catch (Exception x) {
        }

        if (null == newHCInfo) {
            //TODO - display some kind of error
            return getUIFModelAndView(form);
        }
        else {
            form.setHolidayCalendarInfo(newHCInfo);
            return getUIFModelAndView(form, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        }
    }

    @RequestMapping(params = "methodToCall=toCreate")
    public ModelAndView toCreate(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        hcForm.setHolidayCalendarInfo( new HolidayCalendarInfo());
        hcForm.setHolidays(new ArrayList<HolidayInfo>());
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
    }

    @RequestMapping(params = "methodToCall=toEdit")
    public ModelAndView toEdit(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
    }

    @RequestMapping(params = "methodToCall=toCopy")
    public ModelAndView toCopy(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_COPYPAGE);
    }

     /**
     * Method used to save HC
     */
    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        HolidayCalendarInfo hc = hcForm.getHolidayCalendarInfo();

        if(hc.getId() != null && !hc.getId().trim().isEmpty()){
            // edit hc
           updateHolidayCalendar(hc.getId(), hcForm);
        }
        else {
           // create hc
            createHolidayCalendar(hcForm);
        }

        GlobalVariables.getMessageMap().putInfo("holidayCalendarInfo.name","info.enroll.holidaycalendar.saved", hc.getName());
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_VIEWPAGE);
    }

    private void createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception {
        HolidayCalendarInfo hcInfo = getHolidayCalendarFormHelper(hcForm).createHolidayCalendar(hcForm);
        hcForm.setHolidayCalendarInfo(hcInfo);

        createHolidays(hcInfo.getId(), hcForm);
    }

    private void getHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        HolidayCalendarInfo hcInfo = getHolidayCalendarFormHelper(hcForm).getHolidayCalendar(hcId);
        hcForm.setHolidayCalendarInfo(hcInfo);
        hcForm.setAdminOrgName(getAdminOrgNameById(hcInfo.getAdminOrgId()));

        List<HolidayInfo> holidays = getHolidayCalendarFormHelper(hcForm).getHolidaysForHolidayCalendar(hcForm);
        hcForm.setHolidays(holidays);
    }

    public void updateHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        //update hc meta data
        getHolidayCalendarFormHelper(hcForm).updateHolidayCalendar(hcForm);
        hcForm.setHolidayCalendarInfo(getHolidayCalendarFormHelper(hcForm).getHolidayCalendar(hcId));

        //update hc-holidays
        List<HolidayInfo> holidays = hcForm.getHolidays();
        processHolidays(hcForm, holidays, hcId);
    }

    private void createHolidays(String holidayCalendarId, HolidayCalendarForm hcForm) throws Exception {
        List<HolidayInfo> holidays = hcForm.getHolidays();
        List<HolidayInfo> createdHolidays = new ArrayList<HolidayInfo>();

        if(holidays != null && !holidays.isEmpty()){
            for (HolidayInfo holiday : holidays){
                createdHolidays.add(createHoliday(holidayCalendarId, holiday.getTypeKey(), holiday, hcForm));
            }

            hcForm.setHolidays(createdHolidays);
        }

    }

    private HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, HolidayCalendarForm hcForm)throws Exception {
        //create dummy value for db MilestoneEntity.descr_plain as it is not nullable
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain("Plain description of " + holidayInfo.getTypeKey());
        holidayInfo.setDescr(rti);
        holidayInfo.setName(getHolidayCalendarFormHelper(hcForm).getAcalService().getHolidayType(holidayInfo.getTypeKey(), getContextInfo()).getName());
        return getHolidayCalendarFormHelper(hcForm).createHoliday(holidayCalendarId, holidayTypeKey, holidayInfo);
    }

    private List<String> getHolidayIds(HolidayCalendarForm hcForm) throws Exception{
        List<HolidayInfo> holidays = getHolidayCalendarFormHelper(hcForm).getHolidaysForHolidayCalendar(hcForm);
        List<String> holidayIds = new ArrayList<String>();

        if(holidays != null && !holidays.isEmpty()){
            for(HolidayInfo holiday : holidays){
                holidayIds.add(holiday.getId());
            }
        }

        return holidayIds;
    }

    private void processHolidays(HolidayCalendarForm hcForm, List<HolidayInfo> holidays, String hcId)throws Exception{
        List<HolidayInfo> updatedHolidays = new ArrayList<HolidayInfo>();
        List<String> currentHolidays = getHolidayIds(hcForm);

        if(holidays != null && !holidays.isEmpty()){
            for(HolidayInfo holiday : holidays){
                if(currentHolidays.contains(holiday.getId())){
                    //update holiday
                    HolidayInfo updatedHoliday = upateHoliday(holiday.getId(), holiday, hcForm);
                    updatedHolidays.add(updatedHoliday);
                    currentHolidays.remove(holiday.getId());
                }
                else {
                    //create Holiday
                    HolidayInfo createdHoliday = createHoliday(hcId, holiday.getTypeKey(), holiday, hcForm);
                    updatedHolidays.add(createdHoliday);
                }
            }
        }

        hcForm.setHolidays(updatedHolidays);

        if (currentHolidays != null && currentHolidays.size() > 0){
            for(String holidayId: currentHolidays){
                //TODO: delete completely from db, when "deleted" state is available, update the holiday with state ="deleted"
                deleteHoliday(holidayId, hcForm);
            }
        }

	}

    private HolidayInfo upateHoliday(String holidayId, HolidayInfo holidayInfo, HolidayCalendarForm hcForm)throws Exception {
        return getHolidayCalendarFormHelper(hcForm).updateHoliday(holidayId, holidayInfo);
    }

    private void deleteHoliday(String holidayId, HolidayCalendarForm hcForm)throws Exception {
        getHolidayCalendarFormHelper(hcForm).deleteHoliday(holidayId);
    }

    private String getAdminOrgNameById(String id){
        //TODO: hard-coded for now, going to call OrgService
        String adminOrgName = null;
        Map<String, String> allHcOrgs = new HashMap<String, String>();
        allHcOrgs.put("102", "Registrar's Office");

        if(allHcOrgs.containsKey(id)){
            adminOrgName = allHcOrgs.get(id);
        }

        return adminOrgName;
    }

    private AcademicCalendarViewHelperService getHolidayCalendarFormHelper(HolidayCalendarForm hcForm) {
        if (null == acalHelper) {
            acalHelper = (AcademicCalendarViewHelperService)hcForm.getView().getViewHelperService();
        }
        return acalHelper;
    }
/* //test
    private AcademicCalendarViewHelperService getHolidayCalendarCopyFormHelper(HolidayCalendarCopyForm hcForm) {
        if (null == acalHelper) {
            acalHelper = (AcademicCalendarViewHelperService)hcForm.getView().getViewHelperService();
        }
        return acalHelper;
    }*/

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

}
