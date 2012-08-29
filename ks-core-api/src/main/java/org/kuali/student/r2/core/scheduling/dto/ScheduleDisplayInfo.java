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
 * Created by mahtabme on 8/28/12
 */
package org.kuali.student.r2.core.scheduling.dto;

import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.DateRange;
import org.kuali.student.r2.core.scheduling.infc.MeetingTime;
import org.kuali.student.r2.core.scheduling.infc.Schedule;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponent;
import org.kuali.student.r2.core.scheduling.infc.ScheduleDisplay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a reusable display object in the Scheduling Service for Schedules.
 *
 * @Version 2.0
 * @Author Mezba Mahtab
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleDisplayInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "scheduleTime", "scheduleWeekdays", "location",
        "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
public class ScheduleDisplayInfo extends IdEntityInfo implements ScheduleDisplay, Serializable {

    ////////////////////////
    // DATA VARIABLES
    ////////////////////////

    @XmlElement
    private String scheduleTime;
    @XmlElement
    private String scheduleWeekdays;
    @XmlElement
    private String location;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    ////////////////////////
    // CONSTRUCTORS
    ////////////////////////

    public ScheduleDisplayInfo() {
    }

    public ScheduleDisplayInfo(ScheduleDisplay scheduleDisplay) {
        super (scheduleDisplay);
        if (null != scheduleDisplay) {
            this.scheduleTime= scheduleDisplay.getScheduleTime();
            this.scheduleWeekdays= scheduleDisplay.getScheduleWeekdays();
            this.location = scheduleDisplay.getLocation();
        }
    }

    ////////////////////////////////////
    // ACCESSORS AND MODIFIERS
    ////////////////////////////////////

    @Override
    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @Override
    public String getScheduleWeekdays() {
        return scheduleWeekdays;
    }

    public void setScheduleWeekdays(String scheduleWeekdays) {
        this.scheduleWeekdays = scheduleWeekdays;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
