/*
 * Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.acal.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.*;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/academicCalendar")
public class AcademicCalendarController extends UifControllerBase {

    private AcademicCalendarService acalService;
    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new AcademicCalendarForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        AcademicCalendarForm acalForm = (AcademicCalendarForm) form;

        String acalId = request.getParameter(CalendarConstants.CALENDAR_ID);

        if (StringUtils.isNotBlank(acalId)){
            try {
                loadAcademicCalendar(acalId, acalForm);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        String readOnlyView = request.getParameter(CalendarConstants.READ_ONLY_VIEW);
        acalForm.getView().setReadOnly(BooleanUtils.toBoolean(readOnlyView));

        if (StringUtils.isNotBlank(request.getParameter(CalendarConstants.SELECT_TAB))) {
            acalForm.setDefaultTabToShow(request.getParameter(CalendarConstants.SELECT_TAB));
        }

        return super.start(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=toCreate")
    public ModelAndView toCreate(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response){
        acalForm.setAcademicCalendarInfo(new AcademicCalendarInfo());
        acalForm.setEvents(new ArrayList<AcalEventWrapper>());
        acalForm.setHolidayCalendarList(new ArrayList<HolidayCalendarWrapper>());
        acalForm.setTermWrapperList(new ArrayList<AcademicTermWrapper>());
        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
    }

    // if acalId is not empty, use the acalInfo of that acalId as the template for copying
    //otherwise, find the latest acal and use it as the template for copying
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=startNew")
    public ModelAndView startNew( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {

        AcademicCalendarInfo acalInfo = null;

        String acalId = request.getParameter(CalendarConstants.CALENDAR_ID);
        if (acalId != null && !acalId.trim().isEmpty()) {
            String pageId = request.getParameter(CalendarConstants.PAGE_ID);
            if (CalendarConstants.ACADEMIC_CALENDAR_COPY_PAGE.equals(pageId)) {
//                acalForm.setViewTypeName(UifConstants.ViewType.INQUIRY);
                try {
                    acalInfo= getAcalService().getAcademicCalendar(acalId, acalForm.getContextInfo());
                    acalForm.setOrgAcalInfo(acalInfo);

                } catch (Exception ex) {
                    //TODO: handle exception properly
                }
            }
        }
        else {
            // try to get the latest AC from DB
            try {
                acalInfo = acalForm.getViewHelperService().getLatestAcademicCalendar();
                acalForm.setOrgAcalInfo(acalInfo);
            }
            catch (Exception x) {
                //TODO - what to do here?
            }

//            if (null != acalInfo) {
//                // do some calculations on probable values for the new calendar
//                Calendar cal=Calendar.getInstance();
//                cal.setTime(acalInfo.getStartDate());
//                cal.add(Calendar.YEAR, 1);
//                form.setNewCalendarStartDate(cal.getTime());
//                cal.setTime(acalInfo.getEndDate());
//                cal.add(Calendar.YEAR, 1);
//                form.setNewCalendarEndDate(cal.getTime());
//            }

        }
        //return super.start(acalForm, result, request, response);
        return getUIFModelAndView(acalForm);
    }

    @RequestMapping(params = "methodToCall=toEdit")
    public ModelAndView toEdit(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        AcademicCalendarInfo orgAcalInfo = acalForm.getOrgAcalInfo();

        if (StringUtils.isBlank(acalInfo.getId()) && StringUtils.isNotBlank(orgAcalInfo.getId())){
            try{
                loadAcademicCalendar(orgAcalInfo.getId(), acalForm);
             } catch (Exception ex) {
                 throw new RuntimeException("unable to getAcademicCalendar");
            }
            acalForm.setOrgAcalInfo(new AcademicCalendarInfo());
        }

        acalForm.getView().setReadOnly(false);

        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
    }

    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=copyForNew")
    public ModelAndView copyForNew( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {

        AcademicCalendarInfo acalInfo = null;

        String acalId = request.getParameter(CalendarConstants.CALENDAR_ID);
        if (acalId != null && !acalId.trim().isEmpty()) {
            String pageId = request.getParameter(CalendarConstants.PAGE_ID);
            if (CalendarConstants.ACADEMIC_CALENDAR_COPY_PAGE.equals(pageId)) {

                try {
                    acalInfo= getAcalService().getAcademicCalendar(acalId, acalForm.getContextInfo());
                    acalForm.setOrgAcalInfo(acalInfo);

                } catch (Exception ex) {
                    //TODO: handle exception properly
                }
            }
        }
        else {
            // try to get the latest AC from DB
            try {
                acalInfo = acalForm.getViewHelperService().getLatestAcademicCalendar();
                acalForm.setOrgAcalInfo(acalInfo);
            }
            catch (Exception x) {
                //TODO - what to do here?
            }

        }
        return copy(acalForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=toCopy")
    public ModelAndView toCopy(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        if(acalForm.getOrgAcalInfo() == null || StringUtils.isBlank(acalForm.getOrgAcalInfo().getId()))
            acalForm.setOrgAcalInfo(acalInfo);
        acalForm.reset();
        return copy(acalForm, result, request, response);
    }

    //copy over from the existing AcalInfo to create a new
    @RequestMapping(method = RequestMethod.POST, params="methodToCall=copy")
    public ModelAndView copy( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        if ( null == acalForm.getOrgAcalInfo() || null == acalForm.getOrgAcalInfo().getId()) {
            //TODO - display some kind of error
            return getUIFModelAndView(acalForm);
        }

        acalForm.setNewCalendar(false);
        try {
           acalForm.getViewHelperService().copyToCreateAcademicCalendar(acalForm);
        }catch (Exception ex) {

        }
        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);

    }

    //Editing Hcal is implemented fully and it's working.. but we're having some issues with KRAD form management
    //This will be implemented in future milestones
    @RequestMapping(method = RequestMethod.POST, params="methodToCall=editHolidayCalendar")
    public ModelAndView editHolidayCalendar( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = acalForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = acalForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        Properties hcalURLParam = new Properties();
        hcalURLParam.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, CalendarConstants.HC_EDIT_METHOD);
        hcalURLParam.put(CalendarConstants.CALENDAR_ID, acalForm.getHolidayCalendarList().get(selectedLineIndex).getId());
        hcalURLParam.put(UifParameters.VIEW_ID, CalendarConstants.HOLIDAYCALENDAR_FLOWVIEW);
        hcalURLParam.put(UifParameters.PAGE_ID, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        hcalURLParam.put(UifParameters.RETURN_FORM_KEY, acalForm.getFormKey());

        Properties acalReturnURLParams = new Properties();
        acalReturnURLParams.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, CalendarConstants.AC_EDIT_METHOD);
        acalReturnURLParams.put(UifParameters.VIEW_ID, CalendarConstants.ACAL_VIEW);
        acalReturnURLParams.put(UifParameters.PAGE_ID, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
        String returnUrl = UrlFactory.parameterizeUrl(CalendarConstants.ACAL_CONTROLLER_PATH, acalReturnURLParams);
        hcalURLParam.put(UifParameters.RETURN_LOCATION, returnUrl);

        return super.performRedirect(acalForm,CalendarConstants.HCAL_CONTROLLER_PATH, hcalURLParam);

    }


    /**
     * redirect to search Calendar page
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        String controllerPath = CalendarConstants.CALENDAR_SEARCH_CONTROLLER_PATH;
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put(UifParameters.VIEW_ID, CalendarConstants.CALENDAR_SEARCH_VIEW);
        return super.performRedirect(acalForm,controllerPath, urlParameters);
    }


    /**
     * Method used to save AcademicCalendar
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        return updateAcademicCalendar(academicCalendarForm, CalendarConstants.MSG_INFO_ACADEMIC_CALENDAR_SAVED,
                CalendarConstants.UPDATE_SAVE);
    }

    /**
     * Method used to delete AcademicCalendar
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        acalForm.getViewHelperService().deleteAcademicCalendar(
                acalForm.getAcademicCalendarInfo().getId());

        GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES,
                CalendarConstants.MSG_INFO_SEARCH_DELETE_SUCCESS, acalForm.getAcademicCalendarInfo().getName());

        Properties urlParameters = new  Properties();
        urlParameters.put("viewId", CalendarConstants.ENROLLMENT_HOME_VIEW);
        urlParameters.put("methodToCall", KRADConstants.START_METHOD);
        return performRedirect(acalForm, request.getRequestURL().toString(), urlParameters);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelTerm")
    public ModelAndView cancelTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        if (termWrapper.isNew()){
           academicCalendarForm.getTermWrapperList().remove(selectedLineIndex);
        }else{
            try {
                TermInfo termInfo = getAcalService().getTerm(termWrapper.getTermInfo().getId(), academicCalendarForm.getContextInfo());
                AcademicTermWrapper termWrapperFromDB = academicCalendarForm.getViewHelperService().createAndLoadTermWrapper(termInfo);
                academicCalendarForm.getTermWrapperList().set(selectedLineIndex,termWrapperFromDB);

                //Calculate instructional days (if HC exists)
                if (academicCalendarForm.getHolidayCalendarList() != null && !academicCalendarForm.getHolidayCalendarList().isEmpty()) {
                   try{
                        academicCalendarForm.getViewHelperService().populateInstructionalDays(academicCalendarForm.getTermWrapperList());
                    }catch(Exception e){
                        //TODO: FIXME: Have to handle the error.. but for now, as it's causing issue, just skipping calculation when there are errors
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return updateComponent(academicCalendarForm, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=makeTermOfficial")
    public ModelAndView makeTermOfficial(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        academicCalendarForm.getViewHelperService().validateTerm(academicCalendarForm.getTermWrapperList(),selectedLineIndex,academicCalendarForm.getAcademicCalendarInfo());

        if (GlobalVariables.getMessageMap().getErrorCount() > 0){
           return getUIFModelAndView(academicCalendarForm);
        }

//        if (termWrapper.getKeyDatesGroupWrappers() == null || termWrapper.getKeyDatesGroupWrappers().isEmpty()){
//            GlobalVariables.getMessageMap().putError("termWrapperList","error.enroll.term.nokeydates",termWrapper.getTermNameForUI());
//            return updateComponent(academicCalendarForm, result, request, response);
//        }else{
//            for (KeyDatesGroupWrapper keyDatesGroup : termWrapper.getKeyDatesGroupWrappers()) {
//                if (keyDatesGroup.getKeydates() == null || keyDatesGroup.getKeydates().isEmpty()){
//                    GlobalVariables.getMessageMap().putError("termWrapperList","error.enroll.term.nokeydates",termWrapper.getTermNameForUI());
//                    return updateComponent(academicCalendarForm, result, request, response);
//                }
//            }
//        }

        try{
            academicCalendarForm.getViewHelperService().saveTerm(termWrapper, academicCalendarForm.getAcademicCalendarInfo().getId(),true);
            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS,"info.enroll.term.official",termWrapper.getTermNameForUI());
        }catch (Exception e){
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
           throw new RuntimeException(e);
        }

        //Calculate instructional days (if HC exists)
        if (academicCalendarForm.getHolidayCalendarList() != null && !academicCalendarForm.getHolidayCalendarList().isEmpty()) {
           try{
                academicCalendarForm.getViewHelperService().populateInstructionalDays(termWrapper);
            }catch(Exception e){
                //TODO: FIXME: Have to handle the error.. but for now, as it's causing issue, just skipping calculation when there are errors
                e.printStackTrace();
            }
        }

        return updateComponent(academicCalendarForm, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelAddingTerm")
    public ModelAndView cancelAddingTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        ((AcademicTermWrapper)academicCalendarForm.getNewCollectionLines().get("termWrapperList")).clear();

        return updateComponent(academicCalendarForm, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelAddingHoliday")
    public ModelAndView cancelAddingHoliday(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        ((HolidayCalendarWrapper)academicCalendarForm.getNewCollectionLines().get("holidayCalendarList")).setId(StringUtils.EMPTY);

        return updateComponent(academicCalendarForm, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteTerm")
    public ModelAndView deleteTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        if (!termWrapper.isNew()){
            academicCalendarForm.getTermsToDeleteOnSave().add(termWrapper);
        }

        academicCalendarForm.getTermWrapperList().remove(selectedLineIndex);

        return updateComponent(academicCalendarForm, result, request, response);

    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteKeyDate")
    public ModelAndView deleteKeyDate(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        String selectedTermIndex = StringUtils.substringBetween(selectedCollectionPath,"termWrapperList[","]");
        String selectedKeyDateGroup = StringUtils.substringBetween(selectedCollectionPath,"keyDatesGroupWrappers[","]");

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(Integer.parseInt(selectedTermIndex));
        KeyDatesGroupWrapper keydateGroup = termWrapper.getKeyDatesGroupWrappers().get(Integer.parseInt(selectedKeyDateGroup));
        KeyDateWrapper keyDateWrapper = keydateGroup.getKeydates().get(selectedLineIndex);

        if (StringUtils.isNotBlank(keyDateWrapper.getKeyDateInfo().getId())){
            termWrapper.getKeyDatesToDeleteOnSave().add(keyDateWrapper);
        }

        keydateGroup.getKeydates().remove(selectedLineIndex);

        return updateComponent(academicCalendarForm, result, request, response);

    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteKeyDateGroup")
    public ModelAndView deleteKeyDateGroup(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        String selectedTermIndex = StringUtils.substringBetween(selectedCollectionPath,"termWrapperList[","]");

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(Integer.parseInt(selectedTermIndex));
        KeyDatesGroupWrapper keydateGroup = termWrapper.getKeyDatesGroupWrappers().get(selectedLineIndex);
        for (KeyDateWrapper keyDateWrapper : keydateGroup.getKeydates()) {
            if (StringUtils.isNotBlank(keyDateWrapper.getKeyDateInfo().getId())){
                termWrapper.getKeyDatesToDeleteOnSave().add(keyDateWrapper);
            }
        }

        termWrapper.getKeyDatesGroupWrappers().remove(keydateGroup);

        return updateComponent(academicCalendarForm, result, request, response);

    }

    /**
     * Method used to set Acal as official
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=makeAcalOfficial")
    public ModelAndView makeAcalOfficial(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateAcademicCalendar(acalForm, CalendarConstants.MSG_INFO_ACADEMIC_CALENDAR_OFFICIAL,
                CalendarConstants.UPDATE_MAKEOFFICIAL);
    }


    private void loadAcademicCalendar(String acalId, AcademicCalendarForm acalForm) throws Exception {

        AcademicCalendarInfo acalInfo = getAcalService().getAcademicCalendar(acalId,acalForm.getContextInfo());
        acalForm.setAcademicCalendarInfo(acalInfo);
        acalForm.setAdminOrgName(getAdminOrgNameById(acalInfo.getAdminOrgId()));
        acalForm.setNewCalendar(false);
        acalForm.setOfficialCalendar(acalInfo.getStateKey().equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY));

        List<AcalEventWrapper> events = acalForm.getViewHelperService().getEventsForAcademicCalendar(acalForm);
        if (events.size() == 0){
            System.out.println(">>> didn't find any event associated with Academic Calendar: "+acalInfo.getName());
        }

        acalForm.setEvents(events);

        List<AcademicTermWrapper> termWrappers = acalForm.getViewHelperService().loadTerms(acalId);
        acalForm.setTermWrapperList(termWrappers);
        
        List<HolidayCalendarWrapper> holidayCalendarWrapperList = acalForm.getViewHelperService().loadHolidayCalendars(acalInfo);
        acalForm.setHolidayCalendarList(holidayCalendarWrapperList);

        //Calculate instructional days (if HC exists)
        if (acalForm.getHolidayCalendarList() != null && !acalForm.getHolidayCalendarList().isEmpty()) {
           try{
                acalForm.getViewHelperService().populateInstructionalDays(acalForm.getTermWrapperList());
            }catch(Exception e){
                //TODO: FIXME: Have to handle the error.. but for now, as it's causing issue, just skipping calculation when there are errors
                e.printStackTrace();
            }
        }

    }

    private AcademicCalendarInfo processHolidayCalendars(AcademicCalendarForm academicCalendarForm)    {
        AcademicCalendarInfo acalInfo = academicCalendarForm.getAcademicCalendarInfo();
        List<HolidayCalendarWrapper> holidayCalendarList = academicCalendarForm.getHolidayCalendarList();
        List<String> holidayCalendarIds = new ArrayList<String>();
        if (holidayCalendarList!=null && !holidayCalendarList.isEmpty()) {
            for (HolidayCalendarWrapper hcWrapper : holidayCalendarList){
                holidayCalendarIds.add(hcWrapper.getHolidayCalendarInfo().getId());
            }
            acalInfo.setHolidayCalendarIds(holidayCalendarIds);
            academicCalendarForm.setAcademicCalendarInfo(acalInfo);
        }
        return acalInfo;
    }

    private ModelAndView updateAcademicCalendar(AcademicCalendarForm academicCalendarForm, String updateMessage,
                                                String methodCalled) throws Exception {
        AcademicCalendarInfo academicCalendarInfo = academicCalendarForm.getAcademicCalendarInfo();

        //Populate default times
        academicCalendarForm.getViewHelperService().populateAcademicCalendarDefaults(academicCalendarForm);

        //Validate Term
        academicCalendarForm.getViewHelperService().validateAcademicCalendar(academicCalendarForm);

        if (GlobalVariables.getMessageMap().getErrorCount() > 0){
            return getUIFModelAndView(academicCalendarForm);
        }

        //If validation succeeds, continue save
        if(academicCalendarInfo.getId() != null && !academicCalendarInfo.getId().trim().isEmpty()){
            // 1. update acal and AC-HC relationships
            academicCalendarInfo = processHolidayCalendars(academicCalendarForm);
            if (CalendarConstants.UPDATE_MAKEOFFICIAL.equals(methodCalled)) {
                academicCalendarInfo.setStateKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY);
            }
            AcademicCalendarInfo acalInfo = getAcalService().updateAcademicCalendar(academicCalendarInfo.getId(), academicCalendarInfo, academicCalendarForm.getContextInfo() );
            academicCalendarForm.setAcademicCalendarInfo(getAcalService().getAcademicCalendar(acalInfo.getId(), academicCalendarForm.getContextInfo()));

            // 2. update acalEvents if any
            List<AcalEventWrapper> events = academicCalendarForm.getEvents();
            processEvents(academicCalendarForm, events, acalInfo.getId());
        }
        else {
            AcademicCalendarInfo acalInfo = null;
            // 1. create  a new acalInfo with a list of HC Ids
            processHolidayCalendars(academicCalendarForm);
            acalInfo = academicCalendarForm.getViewHelperService().createAcademicCalendar(academicCalendarForm);
            academicCalendarForm.setAcademicCalendarInfo(getAcalService().getAcademicCalendar(acalInfo.getId(), academicCalendarForm.getContextInfo()));
            // 2. create new events if any
            createEvents(acalInfo.getId(), academicCalendarForm);
        }

        //Delete terms which are deleted by the user in the ui
        for (AcademicTermWrapper termWrapper : academicCalendarForm.getTermsToDeleteOnSave()){
            getAcalService().deleteTerm(termWrapper.getTermInfo().getId(),academicCalendarForm.getContextInfo());
        }

        academicCalendarForm.getTermsToDeleteOnSave().clear();

        //Save Term and keydates
        for(AcademicTermWrapper termWrapper : academicCalendarForm.getTermWrapperList()){
            academicCalendarForm.getViewHelperService().saveTerm(termWrapper, academicCalendarForm.getAcademicCalendarInfo().getId(),false);
        }

        //Calculate instructional days (if HC exists)
        if (academicCalendarForm.getHolidayCalendarList() != null && !academicCalendarForm.getHolidayCalendarList().isEmpty()) {
            try{
                academicCalendarForm.getViewHelperService().populateInstructionalDays(academicCalendarForm.getTermWrapperList());
            }catch(Exception e){
                //FIXME: Have to handle the error.. but for now, as it's causing issue, just skipping calculation when there are errors
                e.printStackTrace();
            }
        }

        academicCalendarForm.setNewCalendar(false);

        if (CalendarConstants.UPDATE_MAKEOFFICIAL.equals(methodCalled)) {
            academicCalendarForm.setOfficialCalendar(true);
            academicCalendarForm.getView().setReadOnly(true);
        }

        GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES, updateMessage,
                academicCalendarForm.getAcademicCalendarInfo().getName());

        return getUIFModelAndView(academicCalendarForm);
    }

    private String getAdminOrgNameById(String id){
        //TODO: hard-coded for now, going to call OrgService
        String adminOrgName = null;
        Map<String, String> allAcalOrgs = new HashMap<String, String>();
        allAcalOrgs.put("102", "Registrar's Office");
        allAcalOrgs.put("34", "Medical School");

        if(allAcalOrgs.containsKey(id)){
            adminOrgName = allAcalOrgs.get(id);
        }

        return adminOrgName;
    }

    private void createEvents(String acalId, AcademicCalendarForm acalForm) throws Exception {
        List<AcalEventWrapper> events = acalForm.getEvents();

        if(events != null && !events.isEmpty()){
            List<AcalEventWrapper> createdEvents = new ArrayList<AcalEventWrapper>();
            for (AcalEventWrapper event : events){
                createdEvents.add(acalForm.getViewHelperService().createEvent(acalId, event));
            }
            acalForm.setEvents(createdEvents);
        }

    }

    /**
     * Update existing events, create new events, and delete events that do not exist any more when a user modifies and saves an Academic Calendar
     */
    private void processEvents(AcademicCalendarForm acalForm, List<AcalEventWrapper> events, String acalId)throws Exception{
        List<AcalEventWrapper> updatedEvents = new ArrayList<AcalEventWrapper>();
        List<String> currentEventIds = getExistingEventIds(acalForm);
        if(events != null && !events.isEmpty()){
            for(AcalEventWrapper event : events){
                if(currentEventIds.contains(event.getAcalEventInfo().getId())){
                    //update event
                    AcalEventWrapper updatedEvent = acalForm.getViewHelperService().updateEvent(event.getAcalEventInfo().getId(), event);
                    updatedEvents.add(updatedEvent);
                    currentEventIds.remove(event.getAcalEventInfo().getId());
                }
                else {
                    //create a new event
                    AcalEventWrapper createdEvent = acalForm.getViewHelperService().createEvent(acalId, event);
                    updatedEvents.add(createdEvent);
                }
            }
        }
        acalForm.setEvents(updatedEvents);

        //delete events that have been removed by the user
        if (currentEventIds != null && currentEventIds.size() > 0){
            for(String eventId: currentEventIds){
                //TODO: delete completely from db, when "deleted" state is available, update the event with state ="deleted"
                acalForm.getViewHelperService().deleteEvent(eventId);
            }
        }

    }

    private List<String> getExistingEventIds(AcademicCalendarForm acalForm) throws Exception{
        List<AcalEventWrapper> events = acalForm.getViewHelperService().getEventsForAcademicCalendar(acalForm);
        List<String> eventIds = new ArrayList<String>();

        if(events != null && !events.isEmpty()){
            for(AcalEventWrapper event : events){
                eventIds.add(event.getAcalEventInfo().getId());
            }
        }
        return eventIds;
    }


    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }


}
