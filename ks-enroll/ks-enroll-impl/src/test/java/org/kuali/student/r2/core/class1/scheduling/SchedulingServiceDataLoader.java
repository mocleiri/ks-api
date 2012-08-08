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

package org.kuali.student.r2.core.class1.scheduling;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Test data loader for Scheduling Service testing
 * @author andrewlubbers
 *
 */
public class SchedulingServiceDataLoader {

    ///////////////////////
    // START / END TIMES
    ///////////////////////

    public final static Long START_TIME_MILLIS_8_00_AM = (long) (8 * 60 * 60 * 1000);
    public final static Long START_TIME_MILLIS_10_00_AM = (long) (10 * 60 * 60 * 1000);
    public final static Long START_TIME_MILLIS_1_00_PM = (long) (13 * 60 * 60 * 1000);
    public final static Long START_TIME_MILLIS_3_00_PM = (long) (15 * 60 * 60 * 1000);

    public final static Long END_TIME_MILLIS_8_50_AM = (long) (8 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_9_10_AM = (long) (8 * 60 * 60 * 1000 + 70 * 60 * 1000);
    public final static Long END_TIME_MILLIS_10_50_AM = (long) (10 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_11_10_AM = (long) (10 * 60 * 60 * 1000 + 70 * 60 * 1000);
    public final static Long END_TIME_MILLIS_1_50_PM = (long) (13 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_2_10_PM = (long) (13 * 60 * 60 * 1000 + 70 * 60 * 1000);
    public final static Long END_TIME_MILLIS_3_50_PM = (long) (15 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_4_10_PM = (long) (15 * 60 * 60 * 1000 + 70 * 60 * 1000);

    public final static Long START_TIME_MILLIS_5_10_PM = (long) (17 * 60 * 60 * 1000 + 10 * 60 * 1000);
    public final static Long END_TIME_MILLIS_6_00_PM = (long) (18 * 60 * 60 * 1000);

    private ContextInfo contextInfo;

    public SchedulingServiceDataLoader() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        contextInfo.setCurrentDate(new Date());
    }

    public SchedulingServiceDataLoader (SchedulingService schedulingService) {
        this();
        setSchedulingService(schedulingService);
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
    private SchedulingService schedulingService;
    private static String principalId = SchedulingServiceDataLoader.class.getSimpleName();

    public void loadData () throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, OperationFailedException, PermissionDeniedException {
        //////////////////////////
        // DAYS OF WEEK
        //////////////////////////

        // days of week M W F
        List<Integer> DOW_M_W_F= new ArrayList<Integer>();
        DOW_M_W_F.add(Calendar.MONDAY);
        DOW_M_W_F.add(Calendar.WEDNESDAY);
        DOW_M_W_F.add(Calendar.FRIDAY);
        // days of week T TH
        List<Integer> DOW_T_TH = new ArrayList<Integer>();
        DOW_T_TH.add(Calendar.TUESDAY);
        DOW_T_TH.add(Calendar.THURSDAY);

        ////////////////////
        // TEST DATA
        ////////////////////
        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, true);

        loadTimeSlotInfo("1", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_8_50_AM);
        loadTimeSlotInfo("2", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_9_10_AM);
        loadTimeSlotInfo("3", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_8_50_AM);
        loadTimeSlotInfo("4", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_9_10_AM);
        loadTimeSlotInfo("5", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_10_50_AM);
        loadTimeSlotInfo("6", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_11_10_AM);
        loadTimeSlotInfo("7", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_10_50_AM);
        loadTimeSlotInfo("8", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_11_10_AM);
        loadTimeSlotInfo("9", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_1_50_PM);
        loadTimeSlotInfo("10", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_2_10_PM);
        loadTimeSlotInfo("11", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_1_50_PM);
        loadTimeSlotInfo("12", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_2_10_PM);
        loadTimeSlotInfo("13", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_3_50_PM);
        loadTimeSlotInfo("14", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_4_10_PM);
        loadTimeSlotInfo("15", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_3_50_PM);
        loadTimeSlotInfo("16", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_4_10_PM);

        loadTimeSlotInfo("toDelete", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_5_10_PM, END_TIME_MILLIS_6_00_PM);
        loadTimeSlotInfo("toUpdate", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, START_TIME_MILLIS_5_10_PM, END_TIME_MILLIS_6_00_PM);

        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, false);
    }

    private void loadTimeSlotInfo (String ts_id, String stateKey, String typeKey, List<Integer> weekdays, Long startTimeInMillisecs, Long endTimeInMillisecs)
            throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        TimeSlotInfo ts = new TimeSlotInfo();
        ts.setId(ts_id);
        ts.setWeekdays(weekdays);
        TimeOfDayInfo startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(startTimeInMillisecs);
        ts.setStartTime(startTime);
        TimeOfDayInfo endTime = new TimeOfDayInfo();
        endTime.setMilliSeconds(endTimeInMillisecs);
        ts.setEndTime(endTime);
        ts.setStateKey(stateKey);
        ts.setTypeKey(typeKey);
        schedulingService.createTimeSlot(typeKey, ts, contextInfo);
    }

    public static ScheduleRequestInfo setupScheduleRequestInfo(String scheduleRequestInfoId, String scheduleRequestInfoRefObjectId,
                                                         String ScheduleRequestComponentInfoId, String scheduleRequestInfoName) {
        ScheduleRequestInfo scheduleRequestInfo = new ScheduleRequestInfo();
        scheduleRequestInfo.setId(scheduleRequestInfoId);
        scheduleRequestInfo.setRefObjectId(scheduleRequestInfoRefObjectId);
        scheduleRequestInfo.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
        scheduleRequestInfo.setName(scheduleRequestInfoName);

        List<ScheduleRequestComponentInfo> componentInfoList = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setId(ScheduleRequestComponentInfoId);
        List<String> buildingIds = new ArrayList<String>();
        buildingIds.add("TestBuilding1");
        buildingIds.add("TestBuilding2");
        componentInfo.setBuildingIds(buildingIds);
        List<String> campusIds = new ArrayList<String>();
        campusIds.add("Test-Central");
        componentInfo.setCampusIds(campusIds);
        List<String> orgIds = new ArrayList<String>();
        orgIds.add("Test-IT");
        orgIds.add("Test-Comp");
        componentInfo.setOrgIds(orgIds);
        List<String> roomIds = new ArrayList<String>();
        roomIds.add("Chem-101");
        roomIds.add("Law-201");
        componentInfo.setRoomIds(roomIds);
        List<String> timeSlotIds = new ArrayList<String>();
        timeSlotIds.add("1");
        timeSlotIds.add("2");
        componentInfo.setTimeSlotIds(timeSlotIds);
        List<String> resourceTypeKeys = new ArrayList<String>();
        resourceTypeKeys.add(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
        componentInfo.setResourceTypeKeys(resourceTypeKeys);

        componentInfoList.add(componentInfo);

        scheduleRequestInfo.setScheduleRequestComponents(componentInfoList);

        return scheduleRequestInfo;
    }
}
