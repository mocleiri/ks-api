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
 * Created by Charles on 9/11/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.Schedule;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ScheduleDisplayTransformer {
    public static ScheduleDisplayInfo schedule2scheduleDisplay(ScheduleInfo scheduleInfo,
                                                               AtpService atpService,
                                                               RoomService roomService,
                                                               SchedulingService schedulingService,
                                                               ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException,
                   PermissionDeniedException {
        ScheduleDisplayInfo displayInfo = new ScheduleDisplayInfo();
        // Schedule Display fields:
        // atp;
        String atpId = scheduleInfo.getAtpId();
        if (atpId != null) {
            AtpInfo atpInfo = atpService.getAtp(atpId, contextInfo);
            displayInfo.setAtp(atpInfo);
        } else {
            displayInfo.setAtp(null);
        }
        // scheduleComponentDisplays;
        List<ScheduleComponentInfo> scheduleComponentInfos = scheduleInfo.getScheduleComponents();
        List<ScheduleComponentDisplayInfo> compDisplayInfos = new ArrayList<ScheduleComponentDisplayInfo>();
        if (scheduleComponentInfos != null) {
            for (ScheduleComponentInfo info: scheduleComponentInfos) {
                ScheduleComponentDisplayInfo convert =
                        schedComp2schedCompDisplay(info, roomService, schedulingService, contextInfo);
                compDisplayInfos.add(convert);
            }
        }
        displayInfo.setScheduleComponentDisplays(compDisplayInfos);
        return displayInfo;
    }

    public static ScheduleComponentDisplayInfo schedComp2schedCompDisplay(ScheduleComponentInfo compInfo,
                                                                          RoomService roomService,
                                                                          SchedulingService schedulingService,
                                                                          ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        ScheduleComponentDisplayInfo cdInfo = new ScheduleComponentDisplayInfo();
        // Fields: room, building,
        String roomId = compInfo.getRoomId();
        if (roomId != null) {
            RoomInfo roomInfo = roomService.getRoom(roomId, contextInfo);
            BuildingInfo buildingInfo = roomService.getBuilding(roomInfo.getBuildingId(), contextInfo);
            cdInfo.setBuilding(buildingInfo);
            cdInfo.setRoom(roomInfo);
        } else {
            cdInfo.setRoom(null);
            cdInfo.setBuilding(null);
        }
        // Fields: timeSlots;
        List<String> timeSlotIds = compInfo.getTimeSlotIds();
        List<TimeSlotInfo> timeSlotInfoList = new ArrayList<TimeSlotInfo>();
        if (timeSlotIds != null) {
           timeSlotInfoList = schedulingService.getTimeSlotsByIds(timeSlotIds, contextInfo);
        }
        cdInfo.setTimeSlots(timeSlotInfoList);
        // Some basic fields
        cdInfo.setId(compInfo.getId());
        return cdInfo;
    }
}
