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
package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class HolidayWrapper extends TimeSetWrapper {

    private String typeName;
    private HolidayInfo holidayInfo;
    private boolean instructional;
    private String typeKey;

    public HolidayWrapper(){
        holidayInfo = new HolidayInfo();
        setAllDay(true);
        setInstructional(false);
        setDateRange(false);
    }

    public HolidayWrapper(HolidayInfo holidayInfo){
        this.setHolidayInfo(holidayInfo);
        this.setStartDate(holidayInfo.getStartDate());
        this.setAllDay(holidayInfo.getIsAllDay());
        this.setDateRange(holidayInfo.getIsDateRange());
        this.setTypeKey(holidayInfo.getTypeKey());
        this.setEndDate(holidayInfo.getEndDate());
        this.setInstructional(holidayInfo.getIsInstructionalDay());

        //This is needed to display enddate for readonly view.
        endDateUI = holidayInfo.getEndDate();

        // If not all day, set start/end time in the wrapper
        if (!isAllDay()){
            DateFormat dfm = new SimpleDateFormat("hh:mm");

            setStartTime(dfm.format(holidayInfo.getStartDate()));
            setEndTime(dfm.format(holidayInfo.getEndDate()));

            dfm = new SimpleDateFormat("a");
            setStartTimeAmPm(dfm.format(holidayInfo.getStartDate()));
            setEndTimeAmPm(dfm.format(holidayInfo.getEndDate()));

            if (!isDateRange()){
                setEndDate(null);
            }
        }

    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public HolidayInfo getHolidayInfo() {
        return holidayInfo;
    }

    public void setHolidayInfo(HolidayInfo holidayInfo) {
        this.holidayInfo = holidayInfo;
    }

    //This is for UI display purpose
    public String getIsNonInstructional(){
        if (holidayInfo != null){
            return StringUtils.capitalize(BooleanUtils.toStringYesNo(!holidayInfo.getIsInstructionalDay()));
        }
        return StringUtils.capitalize(BooleanUtils.toStringYesNo(true));
    }

    public boolean isInstructional() {
        return instructional;
    }

    public void setInstructional(boolean instructional) {
        this.instructional = instructional;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }
}
