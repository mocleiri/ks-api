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
 * Created by vgadiyak on 5/30/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.*;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.service.TypeService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditMaintainableImpl extends MaintainableImpl {

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient CourseService courseService;
    private transient OrganizationService organizationService;
    private transient LRCService lrcService;

    //TODO : implement the functionality for Personnel section and its been delayed now since the backend implementation is not yet ready (06/06/2012).

    @Override
    public void saveDataObject() {
        if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)) {
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)getDataObject(); 
            updateCourseOffering(coEditWrapper);
        }
        else{//for new and copy action, report error
             System.out.println(">>>Do not support!");
        }        
 
    }

    private void updateCourseOffering(CourseOfferingEditWrapper coEditWrapper){
        try{
            // persist format offerings
            updateFormatOfferings(coEditWrapper);

            //persist unitDeploymentOrgIds
            List<String> unitDeploymentOrgIds = new ArrayList<String>();
            for(OrganizationInfoWrapper orgWrapper : coEditWrapper.getOrganizationNames()){
                unitDeploymentOrgIds.add(orgWrapper.getId());
            }

            CourseOfferingInfo coInfo = coEditWrapper.getCoInfo();
            coInfo.setUnitsDeploymentOrgIds(unitDeploymentOrgIds);
            coInfo.setStudentRegistrationOptionIds(coEditWrapper.getStudentRegOptions());

            getCourseOfferingService().updateCourseOffering(coInfo.getId(),coInfo,getContextInfo());
        }   catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }

    private void updateFormatOfferings(CourseOfferingEditWrapper coEditWrapper) throws Exception{
        List<FormatOfferingInfo> updatedFormatOfferingList = new ArrayList<FormatOfferingInfo>();
        List<FormatOfferingInfo> formatOfferingList = coEditWrapper.getFormatOfferingList();
        CourseOfferingInfo coInfo = coEditWrapper.getCoInfo();
        List <String> currentFOIds = getExistingFormatOfferingIds(coInfo.getId());
        if (formatOfferingList != null && !formatOfferingList.isEmpty())  {
            for(FormatOfferingInfo formatOfferingInfo : formatOfferingList){
                if(formatOfferingInfo.getId()!=null &&
                        !formatOfferingInfo.getId().isEmpty() &&
                        currentFOIds.contains(formatOfferingInfo.getId())) {
                    //update FO
                    FormatOfferingInfo updatedFormatOffering = getCourseOfferingService().
                            updateFormatOffering(formatOfferingInfo.getId(),formatOfferingInfo, getContextInfo());
                    updatedFormatOfferingList.add(updatedFormatOffering);
                    currentFOIds.remove(formatOfferingInfo.getId());
                }
                else{
                    //create a new FO
                    formatOfferingInfo.setStateKey("kuali.lui.format.offering.state.planned");
                    formatOfferingInfo.setFormatId(formatOfferingInfo.getTypeKey());
                    formatOfferingInfo.setTermId(coInfo.getTermId());
                    formatOfferingInfo.setCourseOfferingId(coInfo.getId());
                    formatOfferingInfo.setTypeKey("kuali.lui.type.course.format.offering");
                    FormatOfferingInfo createdFormatOffering = getCourseOfferingService().
                            createFormatOffering(coInfo.getId(), formatOfferingInfo.getFormatId(), formatOfferingInfo.getTypeKey(), formatOfferingInfo, getContextInfo());
                    updatedFormatOfferingList.add(createdFormatOffering);
                }
            }
            coEditWrapper.setFormatOfferingList(updatedFormatOfferingList);

        }
        //delete FormatOfferings that have been removed by the user
        if (currentFOIds != null && currentFOIds.size() > 0){
            for(String formatOfferingId: currentFOIds){
                //delete all AOs associated with this FO, then delete FO
                //Note by bonnie deleteAO invoked in deleteFormatOfferingCascaded seems not completely correct.
                //I didn't see the code if removing FO-AO relations before deleting AOs....
                getCourseOfferingService().deleteFormatOfferingCascaded(formatOfferingId, getContextInfo());
            }
        }
    }

    private List<String> getExistingFormatOfferingIds(String courseOfferingId) throws Exception{
        List<FormatOfferingInfo> formatOfferingInfoList = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, getContextInfo());
        List<String> formatOfferingIds = new ArrayList<String>();

        if(formatOfferingInfoList != null && !formatOfferingInfoList.isEmpty()){
            for(FormatOfferingInfo formatOfferingInfo : formatOfferingInfoList){
                formatOfferingIds.add(formatOfferingInfo.getId());
            }
        }
        return formatOfferingIds;
    }

/*    public void saveDataObject() {
        if(getDataObject() instanceof CourseOfferingEditWrapper)        {
            persistEditCourseOffering();
        }
        else if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
            try {
                if (getDataObject() instanceof CourseOfferingCreateWrapper){
                    CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)getDataObject();
                    CourseOfferingInfo courseOffering = new CourseOfferingInfo();
                    CourseInfo courseInfo = wrapper.getCourse();
                    courseOffering.setTermId(wrapper.getTerm().getId());
                    courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
//                    courseOffering.setCreditOptionId();
                    courseOffering.setCourseNumberSuffix(wrapper.getCourseCodeSuffix());
                    courseOffering.setCourseId(courseInfo.getId());
                    courseOffering.setCourseCode(courseInfo.getCode());
                    courseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
                    courseOffering.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
                    CourseOfferingInfo info = getCourseOfferingService().createCourseOffering(courseInfo.getId(),wrapper.getTerm().getId(),LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,courseOffering,new ArrayList<String>(),getContextInfo());
                    wrapper.setCoInfo(info);
                    //FIXEM:create formatoffering relation
                }else {
                    ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
                    ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().createActivityOffering(activityOfferingWrapper.getAoInfo().getFormatOfferingId(), activityOfferingWrapper.getAoInfo().getActivityId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, activityOfferingWrapper.getAoInfo(),getContextInfo());
                    setDataObject(new ActivityOfferingWrapper(activityOfferingInfo));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else {   //should be edit action
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
            try {
                ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().updateActivityOffering(activityOfferingWrapper.getAoInfo().getId(), activityOfferingWrapper.getAoInfo(), getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void persistEditCourseOffering() {
        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)getDataObject();
        CourseOfferingInfo coInfo = coEditWrapper.getCoInfo();

        try{
            // persist format offerings
            this.persistFormatOfferings();     //This currently doesn't work.

            //persist unitDeploymentOrgIds
            List<String> unitDeploymentOrgIds = new ArrayList<String>();
            for(OrganizationInfoWrapper orgWrapper : coEditWrapper.getOrganizationNames()){
                unitDeploymentOrgIds.add(orgWrapper.getId());
            }

            coInfo.setUnitsDeploymentOrgIds(unitDeploymentOrgIds);
            coInfo.setStudentRegistrationOptionIds(coEditWrapper.getStudentRegOptions());

            getCourseOfferingService().updateCourseOffering(coInfo.getId(),coInfo,getContextInfo());
        }   catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }
/*
    /**
     * For this method we need to get the old Format offerings and the new format offerings and compare the two.
     * This is because format offerings are a separate service call outside the CourseOffering. The service calls
     * also cannot handle any sort of "merge" operation. So we can only create, update, and delete as three different
     * calls.
     * @throws Exception
     */
/*    protected void persistFormatOfferings() throws Exception{
        //TODO: We need more information on the new course offerings for them to properly submit through the service.
        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)getDataObject();
        CourseOfferingInfo coInfo = coEditWrapper.getCoInfo();

        List<FormatOfferingInfo> newFormats = coEditWrapper.getFormatOfferingList();
        List<FormatOfferingInfo> oldFormats = getCourseOfferingService().getFormatOfferingsByCourseOffering(coInfo.getId(), getContextInfo());

        for(FormatOfferingInfo formatOffering:newFormats){
            formatOffering.setStateKey("kuali.lui.format.offering.state.planned");
            formatOffering.setTermId(coInfo.getTermId());
            formatOffering.setCourseOfferingId(coInfo.getId());
            for ( FormatOfferingInfo oldFormatOffering:oldFormats) {
                if (oldFormatOffering.getId().equals(formatOffering.getId())) {
                    getCourseOfferingService().updateFormatOffering(formatOffering.getId(),formatOffering,getContextInfo());
                } else {                                    // create
                    getCourseOfferingService().createFormatOffering(coInfo.getId(),formatOffering.getFormatId(),formatOffering.getTypeKey(),formatOffering,getContextInfo());
                }
            }
        }

        for(FormatOfferingInfo oldFormatOffering: oldFormats){  // delete
            boolean isTrue = false;
            for (FormatOfferingInfo newFormatOffering:newFormats) {
                if (newFormatOffering.getId().equals(oldFormatOffering.getId())){
                    isTrue = true;
                }
            }
            if (!isTrue) {
                getCourseOfferingService().deleteFormatOfferingCascaded(oldFormatOffering.getId(),getContextInfo());
            }
        }
    }
*/

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            if (getDataObject() instanceof CourseOfferingEditWrapper){
                CourseOfferingInfo info = getCourseOfferingService().getCourseOffering(dataObjectKeys.get("coInfo.id"), getContextInfo());
                CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(info);
                List<FormatOfferingInfo> formats = getCourseOfferingService().getFormatOfferingsByCourseOffering(info.getId(), getContextInfo());
                System.out.println(">>> find "+formats.size()+" format(s):");
                formObject.setFormatOfferingList(formats);

                String courseId = info.getCourseId();
                CourseInfo courseInfo = new CourseInfo();
                if (courseId != null) {
                    courseInfo = (CourseInfo) getCourseService().getCourse(courseId);
                    formObject.setCourse(courseInfo);
                }

                // checking if there are any student registration options from CLU for screen display
                List<String> studentRegOptions = new ArrayList<String>();
                if (courseId != null && courseInfo != null) {
                    List<String> gradingOptions = courseInfo.getGradingOptions();
                    Set<String> regOpts = new HashSet<String>(Arrays.asList(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS));
                    for(String regOpt: regOpts) {
                        if (gradingOptions.contains(regOpt)) {
                            studentRegOptions.add(regOpt);
                        }
                    }
                }
                formObject.setStudentRegOptions(studentRegOptions);

                // Defining Credit Option and if CLU is fixed (then it's disabled)
                boolean creditOptionFixed = false;
                CreditOptionInfo creditOption = new CreditOptionInfo();
                List<ResultComponentInfo> creditOptions = courseInfo.getCreditOptions();
                String creditOptionId = info.getCreditOptionId();
                if (creditOptionId != null) {
                    ResultValuesGroupInfo resultValuesGroupInfo = getLrcService().getResultValuesGroup(creditOptionId, getContextInfo());
                    String typeKey = resultValuesGroupInfo.getTypeKey();
                    List<String> resultValueKeys = resultValuesGroupInfo.getResultValueKeys();
                    List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesByKeys(resultValueKeys, getContextInfo());
                    if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
                        if (!resultValueInfos.isEmpty()) {
                            creditOption.setMinCredits(resultValueInfos.get(0).getValue());
                        }
                    } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
                        if (!resultValueInfos.isEmpty()) {
                            creditOption.setMinCredits(resultValueInfos.get(0).getValue());
                            creditOption.setMaxCredits(resultValueInfos.get(1).getValue());
                        }
                    } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
                        if (!resultValueInfos.isEmpty()) {
                            List<String> credits = new ArrayList<String>();
                            for (ResultValueInfo resultValueInfo : resultValueInfos) {
                                credits.add(resultValueInfo.getValue());
                            }
                            creditOption.setCredits(credits);
                        }
                    }
                }
                if (courseId != null && courseInfo != null && !creditOptions.isEmpty()) {
                    ResultComponentInfo resultComponentInfo = creditOptions.get(0);
                    if (resultComponentInfo.getType().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
                        if (!resultComponentInfo.getResultValues().isEmpty()) {
                            creditOption.setMinCredits(resultComponentInfo.getResultValues().get(0));
                        }
                        creditOptionFixed = true;
                    } else {
                        if (creditOptionId == null || creditOptionId.equals("")) {
                            if (resultComponentInfo.getType().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE)) {
                                creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
                                if (!resultComponentInfo.getResultValues().isEmpty()) {
                                    creditOption.setMinCredits(resultComponentInfo.getResultValues().get(0));
                                    creditOption.setMaxCredits(resultComponentInfo.getResultValues().get(1));
                                }
                            } else if (resultComponentInfo.getType().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE)) {
                                creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
                                if (!resultComponentInfo.getResultValues().isEmpty()) {
                                    List<String> credits = new ArrayList<String>();
                                    for (String credit : resultComponentInfo.getResultValues()) {
                                        credits.add(credit);
                                    }
                                    creditOption.setCredits(credits);
                                }
                            }
                        }
                    }
                }
                formObject.setCreditOption(creditOption);
                formObject.setCreditOptionFixed(creditOptionFixed);

                formObject.setOrganizationNames(new ArrayList<OrganizationInfoWrapper>());

                ArrayList<OrganizationInfoWrapper> orgList = new ArrayList<OrganizationInfoWrapper>();

                if(info.getUnitsDeploymentOrgIds() != null){
                    for(String orgId: info.getUnitsDeploymentOrgIds()){
                        OrgInfo orgInfo = getOrganizationService().getOrg(orgId,getContextInfo());
                        orgList.add(new OrganizationInfoWrapper(orgInfo));
                    }
                }
                formObject.setOrganizationNames(orgList);

                document.getNewMaintainableObject().setDataObject(formObject);
                document.getOldMaintainableObject().setDataObject(formObject);
                document.getDocumentHeader().setDocumentDescription("Edit CO - " + info.getCourseOfferingCode());

                //            StateInfo state = getStateService().getState(formObject.getDto().getStateKey(), getContextInfo());
    //            formObject.setStateName(state.getName());
                return formObject;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

/*    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        if (getDataObject() instanceof ActivityOfferingWrapper){
            ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)document.getNewMaintainableObject().getDataObject();
            document.getDocumentHeader().setDocumentDescription("Activity Offering");
            try {
    //            StateInfo state = getStateService().getState(wrapper.getDto().getStateKey(), getContextInfo());
    //            wrapper.setStateName(state.getName());
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
*/

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if(stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return this.courseService;
    }

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return this.lrcService;
    }
}
