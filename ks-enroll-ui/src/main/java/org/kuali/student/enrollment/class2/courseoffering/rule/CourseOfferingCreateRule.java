package org.kuali.student.enrollment.class2.courseoffering.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingCreateRule extends MaintenanceDocumentRuleBase {

    private CourseOfferingService courseOfferingService;

    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = true;

        if (document.getNewMaintainableObject().getDataObject() instanceof CourseOfferingCreateWrapper){
            CourseOfferingCreateWrapper coWrapper = (CourseOfferingCreateWrapper)document.getNewMaintainableObject().getDataObject();

            valid = validateRequiredFields(coWrapper);

            if (valid){
                valid = validateDuplicateSuffix(coWrapper);
            }
        }

        return valid;
    }

    protected boolean validateRequiredFields(CourseOfferingCreateWrapper coWrapper){
        if (coWrapper.getFormatOfferingWrappers().isEmpty()){
            GlobalVariables.getMessageMap().putError(CourseOfferingConstants.DELIVERY_FORMAT_SECTION_ID,CourseOfferingConstants.DELIVERY_FORMAT_REQUIRED_ERROR);
            return false;
        }
        return true;
    }

    protected boolean validateDuplicateSuffix(CourseOfferingCreateWrapper coWrapper){
        String courseCode = coWrapper.getCatalogCourseCode().toUpperCase();
        String newCoCode = courseCode + coWrapper.getCourseOfferingSuffix().toUpperCase();
        try {
            List<CourseOfferingInfo> wrapperList = _findCourseOfferingsByTermAndCourseCode(coWrapper.getTerm().getId(), newCoCode);
            for (CourseOfferingInfo courseOfferingInfo : wrapperList) {

                if (StringUtils.equals(newCoCode, courseOfferingInfo.getCourseOfferingCode())) {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,
                            CourseOfferingConstants.COURSEOFFERING_ERROR_CREATE_DUPLICATECODE,
                            newCoCode, courseCode);
                    coWrapper.setEnableCreateButton(true);
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    private List<CourseOfferingInfo> _findCourseOfferingsByTermAndCourseCode(String termId, String courseCode)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>();
        if (StringUtils.isNotBlank(courseCode) && StringUtils.isNotBlank(termId)) {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.equal("courseOfferingCode", courseCode),
                    PredicateFactory.equalIgnoreCase("atpId", termId)));
            QueryByCriteria criteria = qbcBuilder.build();

            courseOfferings = getCourseOfferingService().searchForCourseOfferings(criteria, ContextUtils.createDefaultContextInfo());
        }
        return courseOfferings;
    }
}