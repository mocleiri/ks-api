package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public class RelatedObjectHelperAOtoFOImpl implements RelatedObjectHelper {

    private CourseOfferingService courseOfferingService;

    public RelatedObjectHelperAOtoFOImpl(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    @Override
    public Set<String> getRelatedObjectStateKeys(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Set<String> coStateKeySet = new HashSet<String>();

        coStateKeySet.add(getFormatOfferingInfoByActivityOfferingId(activityOfferingId, contextInfo).getStateKey());

        return coStateKeySet;
    }

    @Override
    public List<String> getRelatedObjectIds(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<String> coIdList = new ArrayList<String>();

        coIdList.add(getFormatOfferingInfoByActivityOfferingId(activityOfferingId, contextInfo).getId());

        return coIdList;
    }

    private FormatOfferingInfo getFormatOfferingInfoByActivityOfferingId(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        FormatOfferingInfo formatOfferingInfo = null;

        ActivityOfferingInfo activityOfferingInfo = this.courseOfferingService.getActivityOffering(activityOfferingId, contextInfo);
        formatOfferingInfo = courseOfferingService.getFormatOffering(activityOfferingInfo.getFormatOfferingId(), contextInfo);

        return formatOfferingInfo;
    }
}