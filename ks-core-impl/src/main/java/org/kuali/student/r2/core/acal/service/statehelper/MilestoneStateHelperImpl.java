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
package org.kuali.student.r2.core.acal.service.statehelper;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.service.StateHelper;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;

/**
 * This class is the state helper for milestone state changes
 *
 * @author Kuali Student Team
 */
public class MilestoneStateHelperImpl implements StateHelper{
    
    private AtpService atpService;
    private AcademicCalendarService academicCalendarService;
    
    @Override
    public StatusInfo updateState(String entityId, String nextStateKey, ContextInfo context) {
        StatusInfo statusInfo = new StatusInfo();
        try {
            //Make sure it's an acal
            getAcademicCalendarService().getHoliday(entityId,context);
            getAcademicCalendarService().changeHolidayState(entityId,nextStateKey,context);
            return statusInfo;
        } catch (DoesNotExistException e) {
            //shallow... it may be another milestone type
        } catch (Exception e) {
            statusInfo.setSuccess(false);
            statusInfo.setMessage("Error updating Holiday state - " + e.getMessage());
            return statusInfo;
        }

        try {
            //Make sure it's an acal
            getAcademicCalendarService().getAcalEvent(entityId,context);
            getAcademicCalendarService().changeAcalEventState(entityId,nextStateKey,context);
            return statusInfo;
        } catch (DoesNotExistException e) {
            //shallow... it may be another milestone type
        } catch (Exception e) {
            statusInfo.setSuccess(false);
            statusInfo.setMessage("Error updating Event state - " + e.getMessage());
            return statusInfo;
        }

        try {
            //Make sure it's an acal
            getAcademicCalendarService().getKeyDate(entityId,context);
            getAcademicCalendarService().changeKeyDateState(entityId,nextStateKey,context);
            return statusInfo;
        } catch (Exception e) {
            statusInfo.setSuccess(false);
            statusInfo.setMessage("Error updating Event state - " + e.getMessage());
            return statusInfo;
        }
    }

    @Override
    public String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        return getAtpService().getMilestone(entityId, context).getStateKey();
    }
    
    protected AtpService getAtpService(){
        if (atpService == null){
            atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    protected AcademicCalendarService getAcademicCalendarService(){
        if (academicCalendarService == null){
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return academicCalendarService;
    }
}
