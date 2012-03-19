/* Copyright 2011 The Kuali Foundation
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
package org.kuali.student.enrollment.class2.acal.form;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import org.kuali.rice.krad.web.form.UifFormBase;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

public class AcademicCalendarForm extends UifFormBase {
    private static final long serialVersionUID = 4898118410378641665L;

    private AcademicCalendarInfo academicCalendarInfo;
    private String adminOrgName;
    private String updateTimeString;

    private List<AcalEventWrapper> events;
    private List<HolidayCalendarWrapper> holidayCalendarList;
    private List<AcademicTermWrapper> termWrapperList;

    //used by copy for creating a new
    private String newCalendarName;
    private Date newCalendarStartDate;
    private Date newCalendarEndDate;

    //used by copying
    private boolean official;
    private boolean delete;

    public AcademicCalendarForm() {
        super();
        academicCalendarInfo = new AcademicCalendarInfo();
        termWrapperList = new ArrayList<AcademicTermWrapper>();
        events = new ArrayList<AcalEventWrapper>();
        holidayCalendarList = new ArrayList<HolidayCalendarWrapper>();
        official = false;
        delete = false;
    }

    public AcademicCalendarInfo getAcademicCalendarInfo() {
        return academicCalendarInfo;
    }

    public void setAcademicCalendarInfo(AcademicCalendarInfo academicCalendarInfo) {
        this.academicCalendarInfo = academicCalendarInfo;
    }

    public String getAdminOrgName() {
        return adminOrgName;
    }

    public void setAdminOrgName(String adminOrgName) {
        this.adminOrgName = adminOrgName;
    }

    public void setHolidayCalendarList(List<HolidayCalendarWrapper> holidayCalendarList) {
        this.holidayCalendarList = holidayCalendarList;
    }

    public List<HolidayCalendarWrapper> getHolidayCalendarList() {
        return holidayCalendarList;
    }

    public void setTermWrapperList(List<AcademicTermWrapper> termWrapperList) {
        this.termWrapperList = termWrapperList;
    }

    public List<AcademicTermWrapper> getTermWrapperList() {
        return termWrapperList;
    }

    public String getUpdateTimeString(){
        updateTimeString = new String("");
        if (getAcademicCalendarInfo() == null ||
            getAcademicCalendarInfo().getId()== null ||
            getAcademicCalendarInfo().getId().isEmpty()){
            return updateTimeString;
        }
        else {
            Date updateTime = academicCalendarInfo.getMeta().getUpdateTime();
            if (updateTime != null){
                updateTimeString = "Last saved at "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(updateTime);
            }
            return updateTimeString;
        }
    }

    public List<AcalEventWrapper> getEvents() {
        return events;
    }

    public void setEvents(List<AcalEventWrapper> events) {
        this.events = events;
    }

    public String getNewCalendarName() {
        return newCalendarName;
    }

    public void setNewCalendarName(String newCalendarName) {
        this.newCalendarName = newCalendarName;
    }

    public Date getNewCalendarStartDate() {
        return newCalendarStartDate;
    }

    public void setNewCalendarStartDate(Date newCalendarStartDate) {
        this.newCalendarStartDate = newCalendarStartDate;
    }

    public Date getNewCalendarEndDate() {
        return newCalendarEndDate;
    }

    public void setNewCalendarEndDate(Date newCalendarEndDate) {
        this.newCalendarEndDate = newCalendarEndDate;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
}
