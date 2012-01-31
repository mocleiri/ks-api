/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.core.appointment.dto;


import org.kuali.student.r2.core.appointment.infc.AppointmentSlot;

import java.util.Date;

public class AppointmentSlotInfo implements AppointmentSlot {

    private Date appointmentStartTime;
    private Date appointmentEndTime;
    private String appointmentWindowId;

    @Override
    public Date getAppointmentStartTime() {
        return this.appointmentStartTime;
    }

    @Override
    public Date getAppointmentEndTime() {
        return this.appointmentEndTime;
    }

    @Override
    public String getAppointmentWindowId() {
        return this.appointmentWindowId;
    }
}
