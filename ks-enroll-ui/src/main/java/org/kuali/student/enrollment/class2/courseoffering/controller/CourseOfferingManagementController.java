package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.*;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r1.common.search.dto.*;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.permutation.PermutationUtils;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Controller
@RequestMapping(value = "/courseOfferingManagement")
public class CourseOfferingManagementController extends UifControllerBase  {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementController.class);
    private CluService cluService;
    private LRCService lrcService;
    private CourseService courseService;
    private AcademicCalendarService academicCalendarService;
    private TypeService typeService;
    private StateService stateService;


    private CourseOfferingManagementViewHelperService viewHelperService;
    private OrganizationService organizationService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingManagementForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
        }
        
        // check if the view is invoked within portal or not
            String inputValue = request.getParameter("withinPortal");
            if ((inputValue != null) && !inputValue.isEmpty()){
                boolean withinPortal = new Boolean(request.getParameter("withinPortal")).booleanValue();
                CourseOfferingManagementForm theForm = (CourseOfferingManagementForm) form;
                theForm.setWithinPortal(withinPortal);
            }

        /**
         * When user cancels edit AO/CO, this will be called. Based on the radio button selected, we need to set the page id
         * sothat that page will be displayed. Otherwise, it displays the default (search page) will be displayed.
         */
        String[] methodToCalls = request.getParameterValues(KRADConstants.DISPATCH_REQUEST_PARAMETER);
        for (String methodToCall : methodToCalls) {
            if (StringUtils.equals(methodToCall,KRADConstants.RETURN_METHOD_TO_CALL)){
                if (StringUtils.equals(((CourseOfferingManagementForm)form).getRadioSelection(),CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE)){
                    form.setPageId(CourseOfferingConstants.MANAGE_AO_PAGE);
                } else if (StringUtils.equals(((CourseOfferingManagementForm)form).getRadioSelection(),CourseOfferingConstants.COURSEOFFERING_SUBJECT_CODE)){
                    form.setPageId(CourseOfferingConstants.MANAGE_CO_PAGE);
                }
                break;
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * Method used to
     *  1) search to get TermInfo based on termCode. Only accept one valid TermInfo. If find more than one TermInfo or
     *  don't find any termInfo, log and report an error message.
     *  2) If the input is subject code, load all course offerings based on termId and subjectCode
     *  3) If the input is course offering code,
     *      a)find THE course offering based on termId and courseOfferingCode. If find more than one CO or don't find
     *        any CO, log and report an error message.
     *      b)load all activity offerings based on the courseOfferingId
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        //First, find TermInfo based on termCode
        String termCode = theForm.getTermCode();
        List<TermInfo> termList = getViewHelperService(theForm).findTermByTermCode(termCode);

        if (termList != null && termList.size() == 1) {
            // Get THE term
            theForm.setTermInfo(termList.get(0));
        } else if (termList.size()>1) {
            LOG.error("Error: Found more than one Term for term code: " + termCode);
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, termCode);
            theForm.getCourseOfferingEditWrapperList().clear();
            return getUIFModelAndView(theForm);
         } else{
            LOG.error("Error: Can't find any Term for term code: " + termCode);
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, termCode);
            theForm.getCourseOfferingEditWrapperList().clear();
            return getUIFModelAndView(theForm);
        }

        //load all courseofferings based on subject Code
        String inputCode = theForm.getInputCode();
        if (inputCode != null && !inputCode.isEmpty()) {
            getViewHelperService(theForm).loadCourseOfferingsByTermAndCourseCode(theForm.getTermInfo().getId(), inputCode, theForm);
            if(!theForm.getCourseOfferingEditWrapperList().isEmpty()) {
                if (theForm.getCourseOfferingEditWrapperList().size() > 1) {
                    theForm.setSubjectCode(theForm.getCourseOfferingEditWrapperList().get(0).getCoInfo().getSubjectArea());
                    String longNameDescr = getOrgNameDescription(theForm.getSubjectCode());
                    theForm.setSubjectCodeDescription(longNameDescr);
                } else { // just one course offering is returned
                    CourseOfferingInfo coToShow = theForm.getCourseOfferingEditWrapperList().get(0).getCoInfo();
                    theForm.setCourseOfferingCode(coToShow.getCourseOfferingCode());
                    return prepareManageAOsModelAndView(theForm, coToShow);
                }
            }
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
        } else {
            LOG.error("Error: Course Code search field can't be empty");
            GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Offering", inputCode, termCode);
            theForm.getCourseOfferingEditWrapperList().clear();
            theForm.setActivityWrapperList(null);

            return getUIFModelAndView(theForm);
        }
    }

    private ModelAndView prepareManageAOsModelAndView(CourseOfferingManagementForm theForm, CourseOfferingInfo coToShow) throws Exception {
        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(coToShow);

        theForm.getCourseOfferingEditWrapperList().clear();
        theForm.getCourseOfferingEditWrapperList().add(wrapper);
        theForm.setTheCourseOffering(coToShow);
        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(coToShow, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm, coToShow);

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=manageRegGroups")
    public ModelAndView manageRegGroups(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        //First cleanup and reset AOCluster list
        List<ActivityOfferingClusterWrapper> filteredAOClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        theForm.setFilteredAOClusterWrapperList(filteredAOClusterWrapperList);

        String courseOfferingId = theForm.getTheCourseOffering().getId();
        List<FormatOfferingInfo> formatOfferingList =
                getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, getContextInfo());
        theForm.setFormatOfferingName(formatOfferingList.get(0).getName());
        theForm.setFormatOfferingIdForViewRG(formatOfferingList.get(0).getId());
        //get unassgined AOs (didn't belong to any cluster)
        List<ActivityOfferingWrapper> filteredAOs = getAOsWithoutClusterForSelectedFO(formatOfferingList.get(0).getId(), theForm);
        theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);

        //get clusters if any for the 1st FO
        List<ActivityOfferingClusterInfo> aoClusters = getCourseOfferingService().getActivityOfferingClustersByFormatOffering(formatOfferingList.get(0).getId(), getContextInfo());
        if (aoClusters == null || aoClusters.size()==0){
            theForm.setHasAOCluster(false);
        }
        else {
            theForm.setHasAOCluster(true);
            List <ActivityOfferingClusterWrapper> aoClusterWrappers = _convertToAOClusterWrappers(aoClusters, theForm);
            theForm.setFilteredAOClusterWrapperList(aoClusterWrappers);

            int clusterIndex =0;
            for (ActivityOfferingClusterWrapper aoClusterWrapper : aoClusterWrappers) {
                if (aoClusterWrapper != null && aoClusterWrapper.isHasAllRegGroups()) {
                    List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(aoClusterWrapper.getAoCluster().getId(), getContextInfo());
                    if (rgInfos != null && !rgInfos.isEmpty()) {
                        List<Integer> rgIndexList = _performRGTimeConflictValidation(aoClusterWrapper.getAoCluster(), rgInfos, clusterIndex);
                    }
                }
                clusterIndex++;
            }
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);

    }

//    @RequestMapping(params = "methodToCall=displayCreateNewClusterInLightBox")
//    public ModelAndView displayCreateNewClusterInLightBox(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
//                                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
//        return showDialog("createNewClusterDialog", theForm, request, response);
//    }

    @RequestMapping(params = "methodToCall=createNewClusterFromLightBox")
    public ModelAndView createNewClusterFromLightBox(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!hasDialogBeenDisplayed("createNewClusterDialog", theForm)){
            // redirect back to client to display lightbox
            return showDialog("createNewClusterDialog", theForm, request, response);
        }
        if(hasDialogBeenAnswered("createNewClusterDialog", theForm)) {
            boolean createNewCluster = getBooleanDialogResponse("createNewClusterDialog", theForm, request, response);
            if(createNewCluster){
                String formatOfferingId = theForm.getFormatOfferingIdForViewRG();
                if (_isClusterUnique(formatOfferingId, theForm.getPrivateClusterNameForLightBox())){
                    //build a new empty cluster
                    ActivityOfferingClusterInfo emptyCluster = _buildEmptyAOCluster(formatOfferingId,
                        theForm.getPrivateClusterNameForLightBox(), theForm.getPublishedClusterNameForLightBox());
    
                    //persist it in DB , comment out for now since it does not work for now
                    emptyCluster = getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
                        emptyCluster.getTypeKey(), emptyCluster, getContextInfo());
    
                    List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
                    ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
                    aoClusterWrapper.setActivityOfferingClusterId(emptyCluster.getId());
                    aoClusterWrapper.setAoCluster(emptyCluster);
                    aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

                    aoClusterWrapperList.add(aoClusterWrapper);
                    theForm.setFilteredAOClusterWrapperList(aoClusterWrapperList);
                    theForm.setHasAOCluster(true);
                    theForm.setPrivateClusterNameForLightBox("");
                    theForm.setPublishedClusterNameForLightBox("");
                }else {
                    GlobalVariables.getMessageMap().putError("privateClusterNameForLightBox", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME);
                }
            }else {
                theForm.setPrivateClusterNameForLightBox("");
                theForm.setPublishedClusterNameForLightBox("");
            }

        }

        // clear dialog history so they can press the button again
        theForm.getDialogManager().removeDialog("createNewClusterDialog");
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }
    
    @RequestMapping(params = "methodToCall=createNewCluster")
    public ModelAndView createNewCluster(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        String formatOfferingId = theForm.getFormatOfferingIdForViewRG();
        if (_isClusterUnique(formatOfferingId, theForm.getPrivateClusterName())){
            //build a new empty cluster
            ActivityOfferingClusterInfo emptyCluster = _buildEmptyAOCluster(formatOfferingId,
                    theForm.getPrivateClusterName(), theForm.getPrivateClusterName());
    
            //persist it in DB , comment out for now since it does not work for now
            emptyCluster = getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
                    emptyCluster.getTypeKey(), emptyCluster, getContextInfo());
            
            List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
            ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
            aoClusterWrapper.setActivityOfferingClusterId(emptyCluster.getId());
            aoClusterWrapper.setAoCluster(emptyCluster);
            aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

            aoClusterWrapperList.add(aoClusterWrapper);
            theForm.setFilteredAOClusterWrapperList(aoClusterWrapperList);
            theForm.setHasAOCluster(true);
            theForm.setPrivateClusterName("");
            theForm.setPublishedClusterName("");
        }else{
            GlobalVariables.getMessageMap().putError("privateClusterName", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME);
        }
        
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }

    @RequestMapping(params = "methodToCall=renameAClusterThroughDialog")
    public ModelAndView renameAClusterThroughDialog(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActivityOfferingClusterWrapper selectedClusterWrapper;
        if(!hasDialogBeenDisplayed("renameClusterDialog", theForm)){
            selectedClusterWrapper = (ActivityOfferingClusterWrapper)_getSelectedObject(theForm, "Rename Cluster");
            theForm.setSelectedCluster(selectedClusterWrapper);
            theForm.setPrivateClusterNameForRename(selectedClusterWrapper.getAoCluster().getPrivateName());
            theForm.setPublishedClusterNameForRename(selectedClusterWrapper.getAoCluster().getName());
            // redirect back to client to display lightbox
            return showDialog("renameClusterDialog", theForm, request, response);
        }
        if(hasDialogBeenAnswered("renameClusterDialog", theForm)) {
            boolean wantToRename = getBooleanDialogResponse("renameClusterDialog", theForm, request, response);
            if(wantToRename){
                if (_isClusterUnique(theForm.getFormatOfferingIdForViewRG(), theForm.getPrivateClusterNameForRename())){
                    selectedClusterWrapper = theForm.getSelectedCluster();
                    ActivityOfferingClusterInfo  aoCluster = selectedClusterWrapper.getAoCluster();
                    aoCluster.setName(theForm.getPublishedClusterNameForRename());
                    aoCluster.setPrivateName(theForm.getPrivateClusterNameForRename());
                    aoCluster = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                            aoCluster.getId(), aoCluster, getContextInfo());
                    selectedClusterWrapper.setAoCluster(aoCluster);
                    selectedClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");
                }else {
                    GlobalVariables.getMessageMap().putError("privateClusterNameForRename", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME);
                }
            }
            theForm.setSelectedCluster(null);
        }
        theForm.setPrivateClusterNameForRename("");
        theForm.setPublishedClusterNameForRename("");
        // clear dialog history so they can press the button again
        theForm.getDialogManager().resetDialogStatus("renameClusterDialog");
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }

    
    @RequestMapping(params = "methodToCall=removeAOFromCluster")
    public ModelAndView removeAOFromCluster(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActivityOfferingWrapper selectedAOWrapper =(ActivityOfferingWrapper)_getSelectedObject(theForm, "Remove");
        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for Remove");
        }
        String selectedClusterIndex = StringUtils.substringBetween(selectedCollectionPath,"filteredAOClusterWrapperList[","]");
        ActivityOfferingClusterWrapper theClusterWrapper = theForm.getFilteredAOClusterWrapperList().get(Integer.parseInt(selectedClusterIndex));

        //First, update the DB for cluster and RGs if any
        String aoTypeKey = selectedAOWrapper.getTypeKey();
        if (aoTypeKey== null || aoTypeKey.isEmpty()){
            try {
                TypeInfo typeInfo = getTypeService().getType(selectedAOWrapper.getAoInfo().getTypeKey(), getContextInfo());
                selectedAOWrapper.setTypeKey(typeInfo.getKey());
                selectedAOWrapper.setTypeName(typeInfo.getName());
                aoTypeKey = typeInfo.getKey();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //try to retrieve the accurate AOCluster from DB always
        ActivityOfferingClusterInfo aoCluster = getCourseOfferingService().getActivityOfferingCluster(
                                                            theClusterWrapper.getAoCluster().getId(), getContextInfo());
        List <ActivityOfferingSetInfo> aoSetList = aoCluster.getActivityOfferingSets();
        for (ActivityOfferingSetInfo aoSet:aoSetList){
            if(aoTypeKey.equalsIgnoreCase(aoSet.getActivityOfferingType())){
                aoSet.getActivityOfferingIds().remove(selectedAOWrapper.getAoInfo().getId());                
                break;
            }
        }
        aoCluster = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                aoCluster.getId(), aoCluster, getContextInfo());
        theClusterWrapper.setAoCluster(aoCluster);

        List<RegistrationGroupInfo> rgInfos =getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(aoCluster.getId(), getContextInfo());
        List<RegistrationGroupWrapper> filteredRGs = _getRGsForSelectedFO(rgInfos, theClusterWrapper.getAoWrapperList());
        theClusterWrapper.setRgWrapperList(filteredRGs);
        if(rgInfos.size()>0){
            theClusterWrapper.setRgStatus("All Registration Groups Generated");
            theClusterWrapper.setHasAllRegGroups(true);
            // perform max enrollment validation
            _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theClusterWrapper.getAoCluster(), new Integer(selectedClusterIndex).intValue());
        }

        //finally, move selected AO from AO table under selected Cluster to the unassigned table
        theClusterWrapper.getAoWrapperList().remove(selectedAOWrapper);
        theForm.getFilteredUnassignedAOsForSelectedFO().add(selectedAOWrapper);

        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }
    
    @RequestMapping(params = "methodToCall=deleteAClusterThroughDialog")
    public ModelAndView deleteAClusterThroughDialog(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActivityOfferingClusterWrapper selectedCluster;
        if(!hasDialogBeenDisplayed("confirmToDeleteClusterDialog", theForm)){
            selectedCluster = (ActivityOfferingClusterWrapper)_getSelectedObject(theForm, "Remove Cluster through Dialog");
            theForm.setSelectedCluster(selectedCluster);
            // redirect back to client to display lightbox
            return showDialog("confirmToDeleteClusterDialog", theForm, request, response); 
        }
        if(hasDialogBeenAnswered("confirmToDeleteClusterDialog", theForm)) {
            boolean wantToDelete = getBooleanDialogResponse("confirmToDeleteClusterDialog", theForm, request, response);
            if(wantToDelete){
                 //first need to move all AOs under the selected Cluster back to filteredUnassignedAOsForSelectedFO
                selectedCluster = theForm.getSelectedCluster();
                List<ActivityOfferingWrapper> unassignedAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
                List<ActivityOfferingWrapper> toBeRemovedAOs = selectedCluster.getAoWrapperList();
                for(ActivityOfferingWrapper aoWrapper:toBeRemovedAOs){
                    unassignedAOs.add(aoWrapper);
                }
                theForm.setFilteredUnassignedAOsForSelectedFO(unassignedAOs);
                //then delete the selected cluster
//                getCourseOfferingService().deleteActivityOfferingCluster(selectedCluster.getActivityOfferingClusterId(), getContextInfo());
                getCourseOfferingService().deleteActivityOfferingClusterCascaded(selectedCluster.getActivityOfferingClusterId(), getContextInfo());
                List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
                aoClusterWrapperList.remove(selectedCluster);
                if(aoClusterWrapperList.size() ==0){
                    theForm.setHasAOCluster(false);
                }                
            }
            theForm.setSelectedCluster(null);
        }
        // clear dialog history so they can press the button again
        theForm.getDialogManager().resetDialogStatus("confirmToDeleteClusterDialog");
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE); 
    }

    @RequestMapping(params = "methodToCall=deleteACluster")
    public ModelAndView deleteACluster(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActivityOfferingClusterWrapper selectedCluster = (ActivityOfferingClusterWrapper)_getSelectedObject(theForm, "Remove Cluster");
        //first need to move all AOs under the selected Cluster back to filteredUnassignedAOsForSelectedFO
        List<ActivityOfferingWrapper> unassignedAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
        List<ActivityOfferingWrapper> toBeRemovedAOs = selectedCluster.getAoWrapperList();
        for(ActivityOfferingWrapper aoWrapper:toBeRemovedAOs){
            unassignedAOs.add(aoWrapper);
        }
        theForm.setFilteredUnassignedAOsForSelectedFO(unassignedAOs);
        //then delete the selected cluster
//        getCourseOfferingService().deleteActivityOfferingCluster(selectedCluster.getActivityOfferingClusterId(), getContextInfo());
        getCourseOfferingService().deleteActivityOfferingClusterCascaded(selectedCluster.getActivityOfferingClusterId(), getContextInfo());
        List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
        aoClusterWrapperList.remove(selectedCluster);
        if(aoClusterWrapperList.size() ==0){
            theForm.setHasAOCluster(false);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }

    @RequestMapping(params = "methodToCall=filterAOsAndRGsPerFO")
    public ModelAndView filterAOsAndRGsPerFO (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        //first update AOs
        List<ActivityOfferingWrapper> filteredAOs = getAOsWithoutClusterForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
        theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);
        if(!filteredAOs.isEmpty()){
            theForm.setFormatOfferingName(filteredAOs.get(0).getFormatOffering().getName());
        }

        //then update RGs
        List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByFormatOffering(theForm.getFormatOfferingIdForViewRG(), getContextInfo());
        List<RegistrationGroupWrapper> filteredRGs = _getRGsForSelectedFO(rgInfos, filteredAOs);
 //**********************************
 //       theForm.setFilteredRGsForSelectedFO(filteredRGs);
 //**********************************
        if(rgInfos != null && rgInfos.size()>0) {
            getViewHelperService(theForm).validateRegistrationGroupsForFormatOffering(rgInfos, theForm.getFormatOfferingIdForViewRG(), theForm);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);

    }

//    @RequestMapping(params = "methodToCall=filterAOsPerFO")
//    public ModelAndView filterAOsPerFO (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
//                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List<ActivityOfferingWrapper> filteredAOs = getAOsWithoutClusterForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
//        theForm.setFilteredAOsForSelectedFO(filteredAOs);
//        if(!filteredAOs.isEmpty()){
//            theForm.setFormatOfferingName(filteredAOs.get(0).getFormatOffering().getName());
//        }
//        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
//
//    }

//    @RequestMapping(params = "methodToCall=filterRGsPerFO")
//    public ModelAndView filterRGsPerFO (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
//                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List<ActivityOfferingWrapper> filteredAOs = getAOsWithoutClusterForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
//        List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByFormatOffering(theForm.getFormatOfferingIdForViewRG(), getContextInfo());
//        List<RegistrationGroupWrapper> filteredRGs = _getRGsForSelectedFO(rgInfos, filteredAOs);
//        theForm.setFilteredRGsForSelectedFO(filteredRGs);
//        if(rgInfos != null && rgInfos.size()>0) {
//            getViewHelperService(theForm).validateRegistrationGroupsForFormatOffering(rgInfos, theForm.getFormatOfferingIdForViewRG(), theForm);
//        }
//
//        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
//    }
    
    @RequestMapping(params = "methodToCall=generateRegGroupsPerCluster")
    public ModelAndView generateRegGroupsPerCluster (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActivityOfferingClusterWrapper selectedClusterWrapper = (ActivityOfferingClusterWrapper)_getSelectedObject(theForm, "Generate Registration Groups");
        String selectedClusterId = selectedClusterWrapper.getAoCluster().getId();
        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = getCourseOfferingService().verifyActivityOfferingClusterForGeneration(selectedClusterId,getContextInfo());
        if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isError())  {
            StatusInfo status = getCourseOfferingService().generateRegistrationGroupsForCluster(selectedClusterId, getContextInfo());
            List<RegistrationGroupInfo> rgInfos =getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, getContextInfo());
            if(rgInfos.size()>0){
                //build RGWrapperList and set it to selectedClusterWrapper
                List<RegistrationGroupWrapper> rgWrapperListPerCluster = _getRGsForSelectedFO(rgInfos, selectedClusterWrapper.getAoWrapperList());
                selectedClusterWrapper.setRgWrapperList(rgWrapperListPerCluster);
                selectedClusterWrapper.setHasAllRegGroups(true);
                selectedClusterWrapper.setRgStatus("All Registration Groups Generated");

                //validation for max enrollment number
                int selectedAOCLineIndex = -1;
                String clusterIndex = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
                if (StringUtils.isNotBlank(clusterIndex)) {
                    selectedAOCLineIndex = Integer.parseInt(clusterIndex);
                }
                if (selectedAOCLineIndex == -1) {
                    throw new RuntimeException("Selected line index was not set");
                }
                _performMaxEnrollmentValidation(selectedClusterWrapper.getAoCluster().getFormatOfferingId(), selectedClusterWrapper.getAoCluster(), Integer.parseInt(clusterIndex));

                //validation AO time conflict in RG
                List<Integer> rgIndexList = _performRGTimeConflictValidation(selectedClusterWrapper.getAoCluster(), rgInfos, Integer.parseInt(clusterIndex));

            }
        }else {
            String errorMessage =  aoClusterVerifyResultsInfo.getValidationResults().get(0).getMessage();
            int selectedLineIndex = -1;
            String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
            if (StringUtils.isNotBlank(selectedLine)) {
                selectedLineIndex = Integer.parseInt(selectedLine);
            }

            if (selectedLineIndex == -1) {
                throw new RuntimeException("Selected line index was not set");
            }
            GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+selectedLine, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }
    
    @RequestMapping(params = "methodToCall=generateUnconstrainedRegGroups")
    public ModelAndView generateUnconstrainedRegGroups (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String formatOfferingId = theForm.getFormatOfferingIdForViewRG();

        //new implementation for M5
        //first, build a new default cluster
        ActivityOfferingClusterInfo defaultCluster = _buildDefaultAOCluster(formatOfferingId, theForm);


        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = getCourseOfferingService().
                    verifyActivityOfferingClusterForGeneration(defaultCluster.getId(),getContextInfo());
        if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isError())  {
            //now create RGs for the default cluster
            StatusInfo status = getCourseOfferingService().generateRegistrationGroupsForCluster(defaultCluster.getId(), getContextInfo());

            //build and set ActivityOfferingClusterWrapper
            ActivityOfferingClusterWrapper aoClusterWrapper = _buildAOClusterWrapper (defaultCluster, theForm,0);
            List<ActivityOfferingWrapper> defaultAOList = new ArrayList<ActivityOfferingWrapper>();
            List<ActivityOfferingWrapper> filteredAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
            for(ActivityOfferingWrapper aoWrapper:filteredAOs){
                defaultAOList.add(aoWrapper);
            }
            aoClusterWrapper.setAoWrapperList(defaultAOList);
            List<ActivityOfferingClusterWrapper> aoClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
            aoClusterWrapperList.add(aoClusterWrapper);
            theForm.setFilteredAOClusterWrapperList(aoClusterWrapperList);
            theForm.setHasAOCluster(true);
            //no AO in unassigned list any more for now
            filteredAOs.clear();
            theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);            
        }else {
            getCourseOfferingService().deleteActivityOfferingCluster(defaultCluster.getId(), getContextInfo());
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RegistrationGroupConstants.MSG_ERROR_INVALID_AOLIST);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }

   /*
    *  Move unassigned FO(s) to one of the existing clusters
    */
    @RequestMapping(params = "methodToCall=moveAOToACluster")
    public ModelAndView moveAOToACluster (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        //get selected AOC info
        if (theForm.getClusterIdIdForNewFO().equals("")){
            GlobalVariables.getMessageMap().putError("AOCselectionError", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
            return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
        }
        ActivityOfferingClusterInfo selectedAOCInfo = getCourseOfferingService().getActivityOfferingCluster(theForm.getClusterIdIdForNewFO(),getContextInfo());

        //get FOs and add them to the selected AOC
        List<ActivityOfferingWrapper> aoWrapperList = theForm.getFilteredUnassignedAOsForSelectedFO();
        boolean aoChecked = false; //at least one AO must be checked
        for (ActivityOfferingWrapper aoWrapper : aoWrapperList) {
            if (aoWrapper.getIsChecked()) {
                aoChecked = true;
                //add to appropriate aosIds
                for ( int i=0; i < selectedAOCInfo.getActivityOfferingSets().size(); i++) {
                    if ( selectedAOCInfo.getActivityOfferingSets().get(i).getActivityOfferingType().equals(aoWrapper.getAoInfo().getTypeKey()) ) {  //add aoId
                        selectedAOCInfo.getActivityOfferingSets().get(i).getActivityOfferingIds().add(aoWrapper.getAoInfo().getId());
                        break;
                    }
                }
            }
        }
        if (!aoChecked) {
            GlobalVariables.getMessageMap().putError("AOCselectionError", RegistrationGroupConstants.MSG_ERROR_INVALID_AO_SELECTION);
            return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
        }

        //persist selected AOC
        ActivityOfferingClusterInfo updatedSelectedAOCInfo = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                                                                                                                      theForm.getClusterIdIdForNewFO(),
                                                                                                                      selectedAOCInfo, getContextInfo());
        //update AO list without cluster
        List<ActivityOfferingWrapper> filteredAOs = getAOsWithoutClusterForSelectedFO(updatedSelectedAOCInfo.getFormatOfferingId(), theForm);
        theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);
        if(!filteredAOs.isEmpty()){
            theForm.setFormatOfferingName(filteredAOs.get(0).getAoInfo().getFormatOfferingName());
        }

        //update AO list in selected cluster and sort out RGs
        List<ActivityOfferingWrapper> filteredClusteredAOs = new ArrayList <ActivityOfferingWrapper>();
        List<ActivityOfferingInfo> aosInCluster = getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfo.getId(), getContextInfo());
        for ( ActivityOfferingInfo aoInfo : aosInCluster) {
            filteredClusteredAOs.add(getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo));
        }
        for ( int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++ ) {
            if (theForm.getFilteredAOClusterWrapperList().get(i).getActivityOfferingClusterId().equals(updatedSelectedAOCInfo.getId())) {
                //need to update AOCluster in the AOClusterWrapper because it will be used for other operation
                theForm.getFilteredAOClusterWrapperList().get(i).setAoCluster(updatedSelectedAOCInfo);
                theForm.getFilteredAOClusterWrapperList().get(i).setAoWrapperList(filteredClusteredAOs);
                //update RG status
                List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(updatedSelectedAOCInfo.getId(), getContextInfo());
                if (rgInfos.size() > 0) {
                    theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(false);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus("Only Some Registration Groups Generated");
                }
                break;
            }
        }

        //return updated form
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }

   /*
    *  Move assigned FO(s) between clusters
    */
    @RequestMapping(params = "methodToCall=moveAOBetweenClusters")
    public ModelAndView moveAOBetweenClusters (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActivityOfferingClusterInfo selectedAOCInfoTo = new ActivityOfferingClusterInfo();
        ActivityOfferingClusterInfo selectedAOCInfoFrom = new ActivityOfferingClusterInfo();
        List<ActivityOfferingClusterWrapper> aocWrapperList = theForm.getFilteredAOClusterWrapperList();
        int clusterIndex = 0;   //hold selected clusterIndex for error display
        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        clusterIndex = Integer.parseInt(StringUtils.substringBetween(selectedCollectionPath,"filteredAOClusterWrapperList[","]"));

        //get selected TO AOC info
        if (theForm.getClusterIdForAOMove() != null && !theForm.getClusterIdForAOMove().equals("") &&
                theForm.getClusterIdForAOMove().length() > 0  ) {
            String aocId = "";
            if (theForm.getClusterIdForAOMove().contains(",") ) {
                //strip off "," - not sure why commas are between IDs in the keyValue finder in the footer?
                aocId = theForm.getClusterIdForAOMove().replace(",", "");
            } else {
                aocId = theForm.getClusterIdForAOMove();
            }
            if (aocId.equals("")) {
                GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
                return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
            }
            selectedAOCInfoTo = getCourseOfferingService().getActivityOfferingCluster(aocId, getContextInfo());
        }

        //check if valid selectedAOCInfoTo is selected
        if(selectedAOCInfoTo.getId() == null || selectedAOCInfoTo.getId().equals("") ) {
            GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
            return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
        }

        //get FOs and add them to the selected AOC
        boolean aoChecked = false; //at least one AO must be checked
        for (ActivityOfferingClusterWrapper aocWreapperFrom : aocWrapperList) {
            for (ActivityOfferingWrapper aoWrapper : aocWreapperFrom.getAoWrapperList()) {
                if (aoWrapper.getIsChecked()) {
                    aoChecked = true;
                    //selectedAOCInfoFrom and selectedAOCInfoTo clusters have to be different and only one selectedAOCInfoFrom cluster is allowed at this point
                    if (aocWreapperFrom.getActivityOfferingClusterId().equals(selectedAOCInfoTo.getId())) {
                        GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
                        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
                    } else {
                        selectedAOCInfoFrom = aocWreapperFrom.getAoCluster();
                    }

                    //delete all RGs for AO being moved
                    List<RegistrationGroupInfo> rgInfoList = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedAOCInfoFrom.getId(),getContextInfo());
                    if (rgInfoList.size() > 0) {
                        for (RegistrationGroupInfo rgInfo :rgInfoList) {
                            for (String aoId : rgInfo.getActivityOfferingIds()) {
                                if (aoWrapper.getAoInfo().getId().equals(aoId)) {
                                    getCourseOfferingService().deleteRegistrationGroup(rgInfo.getId(),getContextInfo());
                                }
                            }
                        }
                    }

                    //add AO to selectedAOCInfoTo
                    for ( int i=0; i < selectedAOCInfoTo.getActivityOfferingSets().size(); i++) {
                        if ( selectedAOCInfoTo.getActivityOfferingSets().get(i).getActivityOfferingType().equals(aoWrapper.getAoInfo().getTypeKey()) ) {
                            selectedAOCInfoTo.getActivityOfferingSets().get(i).getActivityOfferingIds().add(aoWrapper.getAoInfo().getId());
                            break;
                        }
                    }
                    //remove AO from selectedAOCInfoFrom
                    for ( int i=0; i < selectedAOCInfoFrom.getActivityOfferingSets().size(); i++) {
                        if ( selectedAOCInfoFrom.getActivityOfferingSets().get(i).getActivityOfferingType().equals(aoWrapper.getAoInfo().getTypeKey()) ) {
                            selectedAOCInfoFrom.getActivityOfferingSets().get(i).getActivityOfferingIds().remove(aoWrapper.getAoInfo().getId());
                            break;
                        }
                    }
                }
            }
        }
        if (!aoChecked) {
            GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_ERROR_INVALID_AO_SELECTION);
            return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
        }
        //persist selected AOCs
        ActivityOfferingClusterInfo updatedSelectedAOCInfoTo = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                                                                                                                      selectedAOCInfoTo.getId(),
                                                                                                                      selectedAOCInfoTo, getContextInfo());

        ActivityOfferingClusterInfo updatedSelectedAOCInfoFrom = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                                                                                                                      selectedAOCInfoFrom.getId(),
                                                                                                                      selectedAOCInfoFrom, getContextInfo());
        //update AO list in updatedSelectedAOCInfoTo and in updatedSelectedAOCInfoFrom
        List<ActivityOfferingWrapper> filteredClusteredAOsTo = new ArrayList <ActivityOfferingWrapper>();
        List<ActivityOfferingWrapper> filteredClusteredAOsFrom = new ArrayList <ActivityOfferingWrapper>();
        //updatedSelectedAOCInfoFrom
        List<ActivityOfferingInfo> aosInClusterFrom = getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfoFrom.getId(), getContextInfo());
        for ( ActivityOfferingInfo aoInfo : aosInClusterFrom) {
            filteredClusteredAOsFrom.add(getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo));
        }
        //updatedSelectedAOCInfoTo
        List<ActivityOfferingInfo> aosInClusterTo = getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfoTo.getId(), getContextInfo());
        for ( ActivityOfferingInfo aoInfo : aosInClusterTo) {
            filteredClusteredAOsTo.add(getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo));
        }
        for ( int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            if (theForm.getFilteredAOClusterWrapperList().get(i).getActivityOfferingClusterId().equals(updatedSelectedAOCInfoTo.getId())) {
                theForm.getFilteredAOClusterWrapperList().get(i).setAoWrapperList(filteredClusteredAOsTo);
                //update RG status for updatedSelectedAOCInfoTo
                List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(updatedSelectedAOCInfoTo.getId(), getContextInfo());
                if (rgInfos.size() > 0) {
                    theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(false);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus("Only Some Registration Groups Generated");
                }
            }
            if (theForm.getFilteredAOClusterWrapperList().get(i).getActivityOfferingClusterId().equals(updatedSelectedAOCInfoFrom.getId())) {
                theForm.getFilteredAOClusterWrapperList().get(i).setAoWrapperList(filteredClusteredAOsFrom);
                //update RG status for updatedSelectedAOCInfoFrom
                List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(updatedSelectedAOCInfoFrom.getId(), getContextInfo());
                if (rgInfos.size() > 0) {
                    theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(true);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus("All Registration Groups Generated");
                    // perform max enrollment validation
                     _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                    //update RGs for the form
                    List<RegistrationGroupWrapper> filteredRGs = _getRGsForSelectedFO(rgInfos, theForm.getFilteredAOClusterWrapperList().get(i).getAoWrapperList());
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgWrapperList(filteredRGs);
                } else {
                    theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(false);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus("No Registration Groups Generated");
                }
            }
        }
        //return updated form
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
    }


    private ActivityOfferingClusterInfo _buildDefaultAOCluster (String formatOfferingId,
                                                                CourseOfferingManagementForm theForm) throws Exception{
        ActivityOfferingClusterInfo defaultCluster = _buildEmptyAOCluster(formatOfferingId,"Default Cluster", "Default Cluster");
        defaultCluster = getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
                defaultCluster.getTypeKey(), defaultCluster, getContextInfo());
        List<ActivityOfferingWrapper> filteredAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
        defaultCluster = _updateAOSets(filteredAOs,defaultCluster,formatOfferingId);
        return defaultCluster;
    }

    private ActivityOfferingClusterInfo _buildEmptyAOCluster (String formatOfferingId, String privateName, String publishedName){
        ActivityOfferingClusterInfo emptyCluster = new ActivityOfferingClusterInfo();
        emptyCluster.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
        emptyCluster.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        emptyCluster.setPrivateName(privateName);
        emptyCluster.setName(publishedName);
        emptyCluster.setFormatOfferingId(formatOfferingId);
        List<ActivityOfferingSetInfo> activityOfferingSets = new ArrayList<ActivityOfferingSetInfo>();
        emptyCluster.setActivityOfferingSets(activityOfferingSets);
        return emptyCluster;
    }

    private  ActivityOfferingClusterInfo _updateAOSets(List<ActivityOfferingWrapper> aoWrapperList,
                                         ActivityOfferingClusterInfo clusterInfo, String formatOfferingId) throws Exception {
        List<ActivityOfferingSetInfo> aoSetInfoList = clusterInfo.getActivityOfferingSets();
        for (ActivityOfferingWrapper aoWrapper:aoWrapperList){
            try {
                TypeInfo typeInfo = getTypeService().getType(aoWrapper.getAoInfo().getTypeKey(), getContextInfo());
                aoWrapper.setTypeKey(typeInfo.getKey());
                aoWrapper.setTypeName(typeInfo.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            String aoTypeKey = aoWrapper.getTypeKey();
            for(ActivityOfferingSetInfo aoSetInfo: aoSetInfoList){
                if(aoSetInfo.getActivityOfferingType().equals(aoTypeKey)){
                    List<String> aoIds = aoSetInfo.getActivityOfferingIds();
                    aoIds.add(aoWrapper.getAoInfo().getId());
                    aoSetInfo.setActivityOfferingIds(aoIds);
                    break;
                }
            }
            clusterInfo.setActivityOfferingSets(aoSetInfoList);
        }
        clusterInfo = getCourseOfferingService().updateActivityOfferingCluster(formatOfferingId,
                                                                    clusterInfo.getId(), clusterInfo, getContextInfo());
        return clusterInfo;
    }
    
    /*
     * convert List<ActivityOfferingClusterInfo> to List<ActivityOfferingClusterWrapper> and set it to the Form
     */
    private List<ActivityOfferingClusterWrapper> _convertToAOClusterWrappers(List<ActivityOfferingClusterInfo> aoClusterList,
                                    CourseOfferingManagementForm theForm) throws Exception{
        List<ActivityOfferingClusterWrapper> aoClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        int clusterIndex =0;
        for (ActivityOfferingClusterInfo aoCluster: aoClusterList) {
            ActivityOfferingClusterWrapper aoClusterWrapper = _buildAOClusterWrapper (aoCluster, theForm, clusterIndex);
            aoClusterWrapperList.add(aoClusterWrapper);
            clusterIndex++;
        }
        return aoClusterWrapperList;
    }

    private  ActivityOfferingClusterWrapper _buildAOClusterWrapper (ActivityOfferingClusterInfo aoCluster,
                         CourseOfferingManagementForm theForm, int clusterIndex) throws Exception{

        ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
        aoClusterWrapper.setActivityOfferingClusterId(aoCluster.getId());
        aoClusterWrapper.setAoCluster(aoCluster);
        aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

        List<ActivityOfferingInfo> aoInfoList = getCourseOfferingService().getActivityOfferingsByCluster(aoCluster.getId(), getContextInfo());
        List<ActivityOfferingWrapper> aoWrapperListPerCluster = new ArrayList<ActivityOfferingWrapper>();
        for(ActivityOfferingInfo aoInfo: aoInfoList){
            ActivityOfferingWrapper aoWrapper = getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);
            aoWrapperListPerCluster.add(aoWrapper);
        }
        aoClusterWrapper.setAoWrapperList(aoWrapperListPerCluster);

        List<RegistrationGroupInfo> rgInfos =getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(aoCluster.getId(), getContextInfo());
        List<RegistrationGroupWrapper> rgListPerCluster = new ArrayList<RegistrationGroupWrapper>();
        if(rgInfos.size()>0){
             _validateRegistrationGroupsPerCluster(rgInfos, aoInfoList, aoClusterWrapper, theForm, clusterIndex);
            rgListPerCluster= _getRGsForSelectedFO(rgInfos, aoWrapperListPerCluster);
        }
        else{
            aoClusterWrapper.setHasAllRegGroups(false);
            aoClusterWrapper.setRgStatus("No Registration Groups Generated");
        }

        aoClusterWrapper.setRgWrapperList(rgListPerCluster);
        return aoClusterWrapper;
    }

    /*
     * Perform several validations:
     * 1. check if All RGs have been generated given AOs in a cluster
     *    if not, set rgStatus="Only Some Registration Groups Generated" and
     *            set hasAllRegGroups=false, therefore "Generate Registration Group" action link will show up
     *    if yes, set rgStatus="All Registration Groups Generated" and
     *            set hasAllRegGroups=true, therefore "View Registration Group" action link will show up
     * 2. when #1 validation result is yes, need to perform max enrollment validation.
     * 3. when #1 validation result is yes, need to perform time conflict validation
     *
     */
    private void _validateRegistrationGroupsPerCluster(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingInfo> aoList,
                              ActivityOfferingClusterWrapper aoClusterWrapper, CourseOfferingManagementForm theForm, int clusterIndex) throws Exception{

        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap =
                _constructActivityOfferingTypeToAvailableActivityOfferingMap(aoList);

        List<List<String>> generatedPermutations = new ArrayList<List<String>>();
        List<List<String>> foundList = new ArrayList<List<String>>();

        PermutationUtils.generatePermutations(new ArrayList<String>(
                activityOfferingTypeToAvailableActivityOfferingMap.keySet()),
                new ArrayList<String>(),
                activityOfferingTypeToAvailableActivityOfferingMap,
                generatedPermutations);

        List<RegistrationGroupInfo> rgInfosCopy = new ArrayList<RegistrationGroupInfo>(rgInfos.size());
        for(RegistrationGroupInfo rgInfo:rgInfos) {
            rgInfosCopy.add(rgInfo);
        }

        for (List<String> activityOfferingPermutation : generatedPermutations) {
            for (RegistrationGroupInfo rgInfo : rgInfosCopy){
                if (_hasGeneratedRegGroup(activityOfferingPermutation,rgInfo)){
                    rgInfosCopy.remove(rgInfo);
                    foundList.add(activityOfferingPermutation);
                    break;
                }
            }
        }
        if (generatedPermutations.size() != foundList.size() )  {
            aoClusterWrapper.setRgStatus("Only Some Registration Groups Generated");
            aoClusterWrapper.setHasAllRegGroups(false);
        }
        else {
            aoClusterWrapper.setRgStatus("All Registration Groups Generated");
            aoClusterWrapper.setHasAllRegGroups(true);
            // perform max enrollment validation
            _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), aoClusterWrapper.getAoCluster(), clusterIndex);
            //TODO: add validation for time conflict
//            if (rgInfos != null && !rgInfos.isEmpty()) {
//                for (RegistrationGroupInfo rgInfo : rgInfos) {
//                    if (rgInfo != null) {
//                        _performRGTimeConflictValidation(aoClusterWrapper.getAoCluster(), rgInfo, clusterIndex);
//                    }
//                }
//            }
        }
        if (!rgInfosCopy.isEmpty()){
            GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerFormatSection", CourseOfferingConstants.REGISTRATIONGROUP_INVALID_REGGROUPS);
        }

    }
    
    private void _performMaxEnrollmentValidation(String formateOfferingId, ActivityOfferingClusterInfo aoCluster, int clusterIndex) throws Exception{
        List<ValidationResultInfo> validationResultInfoList = getCourseOfferingService().validateActivityOfferingCluster(
                "validation on max enroll totals", formateOfferingId, aoCluster, getContextInfo());

        if (validationResultInfoList.get(0).isError())  {
            String errorMessage =  validationResultInfoList.get(0).getMessage();
            GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_MAX_ENROLLMENT);
        }
    }

    private List<Integer> _performRGTimeConflictValidation(ActivityOfferingClusterInfo aoCluster, List<RegistrationGroupInfo> registrationGroupInfos, int clusterIndex) throws Exception{
        List<Integer> rgIndexList = new ArrayList<Integer>();
        rgIndexList.clear();

        if (aoCluster != null && registrationGroupInfos != null && !registrationGroupInfos.isEmpty()) {
            int rgIndex = 0;
            for (RegistrationGroupInfo registrationGroupInfo : registrationGroupInfos) {
                List<ValidationResultInfo> validationResultInfoList = getCourseOfferingService().validateRegistrationGroup(
                        "validation on AO time conflict check in a RG", aoCluster.getId(), registrationGroupInfo.getTypeKey(), registrationGroupInfo, getContextInfo());

                if (validationResultInfoList.get(0).isError())  {
                    rgIndexList.add(rgIndex);
                }

                rgIndex++;
            }

            if (rgIndexList != null && !rgIndexList.isEmpty()) {
                GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_AO_TIMECONFLICT, aoCluster.getPrivateName());
            }
        }

        return rgIndexList;
    }

    private Map<String, List<String>> _constructActivityOfferingTypeToAvailableActivityOfferingMap(List<ActivityOfferingInfo> aoList) {
        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap = new HashMap<String, List<String>>();

        for (ActivityOfferingInfo info : aoList) {
            String activityType = info.getTypeKey();
            List<String> activityList = activityOfferingTypeToAvailableActivityOfferingMap
                    .get(activityType);

            if (activityList == null) {
                activityList = new ArrayList<String>();
                activityOfferingTypeToAvailableActivityOfferingMap.put(
                        activityType, activityList);
            }

            activityList.add(info.getId());

        }
        return activityOfferingTypeToAvailableActivityOfferingMap;
    }

    private boolean _hasGeneratedRegGroup(List<String>activityOfferingPermutation, RegistrationGroupInfo rgInfo){
        boolean isMatched = true;
        List<String> aoIds = rgInfo.getActivityOfferingIds();
        List<String> aoIdsCopy = new ArrayList<String>(aoIds.size());
        for (String aoId: aoIds){
            aoIdsCopy.add(aoId);
        }
        List<String> foundList = new ArrayList<String>();
        for (String activityOfferingPermutationItem : activityOfferingPermutation){
            for (String aoId: aoIdsCopy){
                if (activityOfferingPermutationItem.equals(aoId)){
                    aoIdsCopy.remove(aoId);
                    foundList.add(activityOfferingPermutationItem);
                    break;
                }
            }
        }
        if (activityOfferingPermutation.size() != foundList.size() ||!aoIdsCopy.isEmpty()  )  {
            isMatched = false;
        }
        return isMatched;
    }

    @RequestMapping(params = "methodToCall=loadPreviousCO")
    public ModelAndView loadPreviousCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(theForm.getPreviousCourseOffering());

        theForm.getCourseOfferingEditWrapperList().clear();
        theForm.getCourseOfferingEditWrapperList().add(wrapper);
        theForm.setTheCourseOffering(theForm.getPreviousCourseOffering());
        theForm.setInputCode(wrapper.getCoInfo().getCourseOfferingCode());

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getPreviousCourseOffering(), theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm,theForm.getPreviousCourseOffering());

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=loadNextCO")
    public ModelAndView loadNextCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(theForm.getNextCourseOffering());

        theForm.getCourseOfferingEditWrapperList().clear();
        theForm.getCourseOfferingEditWrapperList().add(wrapper);
        theForm.setTheCourseOffering(theForm.getNextCourseOffering());
        theForm.setInputCode(wrapper.getCoInfo().getCourseOfferingCode());

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getNextCourseOffering(), theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm,theForm.getNextCourseOffering());

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=loadAOs")
    public ModelAndView loadAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object selectedObject = _getSelectedObject(theForm, "Manage");
        if(selectedObject instanceof CourseOfferingEditWrapper){
            CourseOfferingEditWrapper coWrapper =  (CourseOfferingEditWrapper)selectedObject;
            CourseOfferingInfo theCourseOffering = coWrapper.getCoInfo();

            theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());
            theForm.setInputCode(theCourseOffering.getCourseOfferingCode());
            theForm.setRadioSelection(CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE);

            return prepareManageAOsModelAndView(theForm, theCourseOffering);
        }
        else{
            //TODO log error
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
        }
    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCreateCopy")
    public ModelAndView copyCourseOfferingCreateCopy(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        CourseOfferingCopyWrapper copyWrapper = theForm.getCourseOfferingCopyWrapper();
        CourseOfferingInfo courseOfferingInfo = copyWrapper.getCoInfo();
        List<String> optionKeys = new ArrayList<String>();

        if (copyWrapper.isExcludeSchedulingInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY);
        }
        if (copyWrapper.isExcludeInstructorInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY);
        }
        if (copyWrapper.isExcludeCancelledActivityOfferings()) {
            optionKeys.add(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY);
        }
        //Generate Ids
        optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);

        SocRolloverResultItemInfo item = getCourseOfferingService().rolloverCourseOffering(
                courseOfferingInfo.getId(),
                copyWrapper.getTermId(),
                optionKeys,
                getContextInfo());
        CourseOfferingInfo courseOffering = getCourseOfferingService().getCourseOffering(item.getTargetCourseOfferingId(), getContextInfo());
        ExistingCourseOffering newWrapper = new ExistingCourseOffering(courseOffering);
        newWrapper.setCredits(courseOffering.getCreditCnt());
        newWrapper.setGrading(getGradingOption(courseOffering.getGradingOptionId()));
        copyWrapper.getExistingOfferingsInCurrentTerm().add(newWrapper);
        // reload the COs
        getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(copyWrapper.getTermId(), theForm.getSubjectCode(), theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    private TermInfo getTerm(String termCode){
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p = null;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(termCode)){
            p = equal("atpCode", termCode);
            pList.add(p);
        }

        qBuilder.setPredicates(p);

        try {
            List<TermInfo> terms = getAcademicCalendarService().searchForTerms(qBuilder.build(),getContextInfo());
            if (terms.size() > 1){
                //GlobalVariables.getMessageMap().putError("asf","asdf");//FIXME
                return null;
            }else if (terms.isEmpty()){
                return null;
            }
            return terms.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private CourseInfo getCourseInfo(String courseName) {

        CourseInfo        returnCourseInfo = null;
        String            courseId         = null;
        List<SearchParam> searchParams     = new ArrayList<SearchParam>();
        List <CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("lu.criteria.code");
        qpv1.setValue(courseName);
        searchParams.add(qpv1);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.cluByCode");

        try {
            SearchResult searchResult = getCluService().search(searchRequest);
            if (searchResult.getRows().size() > 0) {
                for(SearchResultRow row : searchResult.getRows()){
                    List<SearchResultCell> srCells = row.getCells();
                    if(srCells != null && srCells.size() > 0){
                        for(SearchResultCell cell : srCells){
                            if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                                courseId = cell.getValue();
                                returnCourseInfo = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
                                courseInfoList.add(returnCourseInfo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (courseInfoList.size() > 1){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Multiple matches found for the course code");
            return null;
        }

        if (courseInfoList.isEmpty()){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "No match found for the course code");
            return null;
        }

        return courseInfoList.get(0);

    }

    private String getGradingOption(String gradingOptionId)throws Exception{
        String gradingOption = "";
        if(StringUtils.isNotBlank(gradingOptionId)){
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, getContextInfo());
            if(rvg!= null && StringUtils.isNotBlank(rvg.getName())){
                gradingOption = rvg.getName();
            }
        }

        return gradingOption;
    }

    private List<ActivityOfferingWrapper> getAOsWithoutClusterForSelectedFO(String theFOId, CourseOfferingManagementForm theForm) throws Exception {
        List<ActivityOfferingWrapper> filterdAOList = theForm.getFilteredUnassignedAOsForSelectedFO();
        filterdAOList.clear();

        //Turn the following code on once the COServiceImpl supports it
        List<ActivityOfferingInfo> aoList = getCourseOfferingService().getActivityOfferingsWithoutClusterByFormatOffering(theFOId,getContextInfo());
        for (ActivityOfferingInfo ao: aoList){
            ActivityOfferingWrapper aoWrapper = getViewHelperService(theForm).convertAOInfoToWrapper(ao);
            filterdAOList.add(aoWrapper);

        }
        return filterdAOList;
    }

    private List<RegistrationGroupWrapper> _getRGsForSelectedFO(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingWrapper> filteredAOs) {

        List<RegistrationGroupWrapper> filterdRGList = new ArrayList<RegistrationGroupWrapper>();

        HashMap<String, ActivityOfferingWrapper> filteredAOsHM = new HashMap<String, ActivityOfferingWrapper>();
        for (ActivityOfferingWrapper wrapper : filteredAOs) {
            filteredAOsHM.put(wrapper.getAoInfo().getId(), wrapper);
        }

        for (RegistrationGroupInfo rgInfo : rgInfos) {
           RegistrationGroupWrapper rgWrapper = new RegistrationGroupWrapper();
            rgWrapper.setRgInfo(rgInfo);
            String aoActivityCodeText = "", aoStateNameText = "", aoTypeNameText = "", aoInstructorText = "", aoMaxEnrText = "";
            for (String aoID : rgInfo.getActivityOfferingIds()) {
                if (filteredAOsHM.get(aoID).getAoInfo().getActivityCode() != null && !filteredAOsHM.get(aoID).getAoInfo().getActivityCode().equalsIgnoreCase("")) {
                    aoActivityCodeText = aoActivityCodeText + filteredAOsHM.get(aoID).getAoInfo().getActivityCode() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getStateName() != null && !filteredAOsHM.get(aoID).getStateName().equalsIgnoreCase("")) {
                    aoStateNameText = aoStateNameText + filteredAOsHM.get(aoID).getStateName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getTypeName() != null && !filteredAOsHM.get(aoID).getTypeName().equalsIgnoreCase("")) {
                    aoTypeNameText = aoTypeNameText + filteredAOsHM.get(aoID).getTypeName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getFirstInstructorDisplayName() != null && !filteredAOsHM.get(aoID).getFirstInstructorDisplayName().equalsIgnoreCase("")) {
                    aoInstructorText = aoInstructorText + filteredAOsHM.get(aoID).getFirstInstructorDisplayName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getAoInfo().getMaximumEnrollment() != null) {
                    aoMaxEnrText = aoMaxEnrText + Integer.toString(filteredAOsHM.get(aoID).getAoInfo().getMaximumEnrollment()) + "<br/>";
                }

                if(filteredAOsHM.get(aoID).getStartTimeDisplay() != null){
                    rgWrapper.setStartTimeDisplay(filteredAOsHM.get(aoID).getStartTimeDisplay(), true);
                }

                if(filteredAOsHM.get(aoID).getEndTimeDisplay() != null){
                    rgWrapper.setEndTimeDisplay(filteredAOsHM.get(aoID).getEndTimeDisplay(), true);
                }

                if(filteredAOsHM.get(aoID).getBuildingName() != null){
                    rgWrapper.setBuildingName(filteredAOsHM.get(aoID).getBuildingName(), true);
                }

                if(filteredAOsHM.get(aoID).getRoomName() != null){
                    rgWrapper.setRoomName(filteredAOsHM.get(aoID).getRoomName(), true);
                }

                if(filteredAOsHM.get(aoID).getDaysDisplayName() != null){
                    rgWrapper.setDaysDisplayName(filteredAOsHM.get(aoID).getDaysDisplayName(), true);
                }
            }
            if (aoActivityCodeText.length() > 0) {
                aoActivityCodeText = aoActivityCodeText.substring(0, aoActivityCodeText.lastIndexOf("<br/>"));
            }
            if (aoStateNameText.length() > 0) {
                aoStateNameText = aoStateNameText.substring(0, aoStateNameText.lastIndexOf("<br/>"));
            }
            if (aoTypeNameText.length() > 0) {
                aoTypeNameText = aoTypeNameText.substring(0, aoTypeNameText.lastIndexOf("<br/>"));
            }
            if (aoInstructorText.length() > 0) {
                aoInstructorText = aoInstructorText.substring(0, aoInstructorText.lastIndexOf("<br/>"));
            }
            if (aoMaxEnrText.length() > 0) {
                aoMaxEnrText = aoMaxEnrText.substring(0, aoMaxEnrText.lastIndexOf("<br/>"));
            }

            rgWrapper.setAoActivityCodeText(aoActivityCodeText);
            rgWrapper.setAoStateNameText(aoStateNameText);
            rgWrapper.setAoTypeNameText(aoTypeNameText);
            rgWrapper.setAoInstructorText(aoInstructorText);
            rgWrapper.setAoMaxEnrText(aoMaxEnrText);
            filterdRGList.add(rgWrapper);

            try{
            rgWrapper.setStateKey(getStateService().getState(rgInfo.getStateKey(), getContextInfo()).getName());
            }catch (Exception e){
                LOG.info("Error occured to get the StateService" + e.getMessage());
            }
        }

        return filterdRGList;
    }
    
    private boolean _isClusterUnique(String formatOfferingId, String privateName) throws Exception{
        //Build up a term search criteria
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equalIgnoreCase("privateName", privateName),
                PredicateFactory.equal("formatOfferingId", formatOfferingId)));
        QueryByCriteria criteria = qbcBuilder.build();

        List<ActivityOfferingClusterInfo> aoClusterList = getCourseOfferingService().searchForActivityOfferingClusters(criteria, getContextInfo());
        if (aoClusterList.size()>0)
            return false;
        else
            return true;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

    private CluService getCluService() {
        if(cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
        }
        return cluService;
    }

    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = CourseOfferingResourceLoader.loadLrcService();
        }
        return this.lrcService;
    }

    private CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCancel")
    public ModelAndView copyCourseOfferingCancel(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOffering")
    public ModelAndView copyCourseOffering(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Object selectedObject = _getSelectedObject(theForm, "Copy"); // Receives edit wrapper, "Copy" for error message.
        if(selectedObject instanceof CourseOfferingEditWrapper){

            // Get the selected CourseOfferingEditWrapper.
            CourseOfferingEditWrapper courseOfferingEditWrapper = (CourseOfferingEditWrapper)selectedObject;
            CourseOfferingInfo courseOfferingInfo = courseOfferingEditWrapper.getCoInfo();

            // Load activity offerings.
            getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(courseOfferingInfo, theForm);

            // Create a new CourseOfferingCopyWrapper from the Course Offering information.
            CourseOfferingCopyWrapper coCopyWrapper = new CourseOfferingCopyWrapper();

            // Add items that the page wrapper intends to displaying.
            coCopyWrapper.setCoInfo(courseOfferingInfo);
            coCopyWrapper.setCourseOfferingCode(courseOfferingInfo.getCourseOfferingCode());
            coCopyWrapper.setCourseTitle(courseOfferingInfo.getCourseOfferingTitle());
            coCopyWrapper.setTermId(courseOfferingInfo.getTermId());
            coCopyWrapper.setCreditCount(courseOfferingInfo.getCreditCnt());
            coCopyWrapper.setGradingOption(courseOfferingInfo.getGradingOptionName());
            coCopyWrapper.setStudentRegistrationGradingOptionsList(courseOfferingInfo.getStudentRegistrationGradingOptions());
            coCopyWrapper.setFinalExamType(courseOfferingInfo.getFinalExamType());
            coCopyWrapper.setWaitlistLevelTypeKey(courseOfferingInfo.getWaitlistLevelTypeKey());
            coCopyWrapper.setWaitlistTypeKey(courseOfferingInfo.getWaitlistTypeKey());
            if(courseOfferingInfo.getIsHonorsOffering() != null) {
                coCopyWrapper.setIsHonors(courseOfferingInfo.getIsHonorsOffering());
            } else {
                coCopyWrapper.setIsHonors(false);
            }
            coCopyWrapper.setActivityOfferingWrapperList(theForm.getActivityWrapperList());

            // Add it to the Copy Wrapper List.
            theForm.setCourseOfferingCopyWrapper(coCopyWrapper);
        } else { //TODO log error
            theForm.setCourseOfferingCopyWrapper(null);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.COPY_CO_PAGE);
    }


    @RequestMapping(params = "methodToCall=selectAllActivityOfferings")
    public ModelAndView selectAllActivityOfferings(
            @ModelAttribute("KualiForm")
            CourseOfferingManagementForm theForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper listElement : list) {
            listElement.setIsChecked(true);
        }
        return getUIFModelAndView(theForm);
    }


    @RequestMapping(params = "methodToCall=loadCOs")
    public ModelAndView loadCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingInfo theCourseOffering = theForm.getTheCourseOffering();
        String subjectCode = theCourseOffering.getSubjectArea();
        String termId = theForm.getTermInfo().getId();
        theForm.setRadioSelection("subjectCode");
        theForm.setInputCode(subjectCode);
        getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /**
     * Method used to copy activityOffering
     */
    @RequestMapping(params = "methodToCall=copyAO")
    public ModelAndView copyAO( @ModelAttribute("KualiForm") CourseOfferingManagementForm form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        ActivityOfferingWrapper selectedAO = (ActivityOfferingWrapper)_getSelectedObject(form, "copy");
        try{
            CourseOfferingResourceLoader.loadCourseOfferingService().copyActivityOffering(selectedAO.getAoInfo().getId(), ContextBuilder.loadContextInfo());

            //reload AOs including the new one just created
            getViewHelperService(form).loadActivityOfferingsByCourseOffering(form.getTheCourseOffering(), form);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(form);
    }

    /**
     * Method used to delete a list of selected Draft activity Offerings
     **/
    @RequestMapping(params = "methodToCall=cancelDeleteAOs")
    public ModelAndView cancelDeleteAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getTheCourseOffering(), theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    /**
     * Method used to delete a list of selected Draft activity Offerings
     **/

    @RequestMapping(params = "methodToCall=deleteSelectedAoList")
    public ModelAndView deleteSelectedAoList(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<ActivityOfferingWrapper> selectedAolist = theForm.getSelectedToDeleteList();

        try{
            for(ActivityOfferingWrapper ao : selectedAolist)  {
                CourseOfferingResourceLoader.loadCourseOfferingService().deleteActivityOfferingCascaded(ao.getAoInfo().getId(), ContextBuilder.loadContextInfo());
            }

            // check for changes to states in CO and related FOs
            ViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(theForm.getTheCourseOffering(), ContextBuilder.loadContextInfo());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getTheCourseOffering(), theForm);
        if(selectedAolist.size() > 0 && theForm.isSelectedIllegalAOInDeletion())  {
            GlobalVariables.getMessageMap().putWarningForSectionId("manageActivityOfferingsPage",
                    CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_SELECTED_AO_TO_DELETE);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);

    }

    /**
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=deleteCoConfirmation")
    public ModelAndView deleteCoConfirmation(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOffering = null;

        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if ( ! StringUtils.isBlank(selectedCollectionPath)) {
            Object selectedObject = _getSelectedObject(theForm, "deleteCo");
            theCourseOffering = ((CourseOfferingEditWrapper) selectedObject).getCoInfo();
            theForm.setTheCourseOffering(theCourseOffering);
        }

        if (theCourseOffering == null) {
            theCourseOffering = theForm.getTheCourseOffering();
        }
        if (theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        //  Verify the state of the CourseOffering is appropriate for deleting.
        //  FIXME: This logic is duplicated in CoreOfferingEditWrapper.isLegalToDelete().
        String coState = theCourseOffering.getStateKey();
        if (StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)
                || StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_CANCELED_KEY)) {
            LOG.error(String.format("Course offering [%s] cannot not be deleted because the state was [%s].",
                theCourseOffering.getCourseOfferingCode(), coState));
            GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                CourseOfferingConstants.COURSEOFFERING_INVALID_STATE_FOR_DELETE);
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
        }

        theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());

        // Load activity offerings
        try {
            getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (ActivityOfferingWrapper ao : theForm.getActivityWrapperList()) {
            //  Verify AO state.
            if ( ! ao.isLegalToDelete()) {
                LOG.error(String.format("Course Offering [%s] cannot be deleted because Activity Offering [%s] is in an invalid state for deleting.",
                    theCourseOffering.getCourseOfferingCode(), ao.getActivityCode()));
                GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                        CourseOfferingConstants.COURSEOFFERING_INVALID_AO_STATE_FOR_DELETE);
                 return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
            }
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
    }

    /**
     * Method used to delete a Course Offering with all Draft activity Offerings
     **/
    @RequestMapping(params = "methodToCall=deleteCo")
    public ModelAndView deleteCo(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) {
        CourseOfferingInfo  theCourseOffering = theForm.getTheCourseOffering();
        if (theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        String termId = theCourseOffering.getTermId();
        String subjectCode = theCourseOffering.getSubjectArea();
        //  Load AOs
        if (theForm.getActivityWrapperList().isEmpty()) {
            try {
                getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            } catch (Exception e) {
                LOG.error(String.format("Could not get AOs for Course offering [%s].", theCourseOffering.getCourseOfferingCode()));
                throw new RuntimeException(e);
            }
        }

        try {
            for (ActivityOfferingWrapper ao : theForm.getActivityWrapperList()) {
                // Verify AO status
                if ( ! ao.isLegalToDelete()) {
                    LOG.error(String.format("Course Offering [%s] cannot be deleted because Activity Offering [%s] is in an invalid state for deleting.",
                        theCourseOffering.getCourseOfferingCode(), ao.getActivityCode()));
                    GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                            CourseOfferingConstants.COURSEOFFERING_INVALID_AO_STATE_FOR_DELETE);
                    return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
                }
            }
            CourseOfferingResourceLoader.loadCourseOfferingService().deleteCourseOfferingCascaded(theCourseOffering.getId(), ContextBuilder.loadContextInfo());
            // Reload existing COs
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /**
     * Method used to delete a Course Offering with all Draft activity Offerings
     **/
    @RequestMapping(params = "methodToCall=cancelDeleteCo")
    public ModelAndView cancelDeleteCo(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) {
        CourseOfferingInfo  theCourseOffering = theForm.getTheCourseOffering();
        if(theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        String termId = theCourseOffering.getTermId();
        String subjectCode = theCourseOffering.getSubjectArea();
        // load all the activity offerings
        if (theForm.getActivityWrapperList().isEmpty()) {
            try {
                getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        try {
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);
        } catch (Exception e) {
            LOG.error("Could not load course offerings.", e);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /**
     * Method used to edit a selected CO or AO
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        Properties urlParameters = new Properties();
        Object selectedObject = _getSelectedObject(theForm, "edit");

        if (selectedObject instanceof CourseOfferingEditWrapper) {
            CourseOfferingInfo courseOfferingInfo = ((CourseOfferingEditWrapper) selectedObject).getCoInfo();
            urlParameters = _buildCOURLParameters(courseOfferingInfo,KRADConstants.Maintenance.METHOD_TO_CALL_EDIT,false,getContextInfo());
        } else if(selectedObject instanceof ActivityOfferingWrapper) {

            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)selectedObject;

            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
            urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, aoWrapper.getAoInfo().getId());
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, theForm.getTheCourseOffering().getId());
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingWrapper.class.getName());
            urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm, "activityOffering", urlParameters);

    }

    /**
     *  Determine if any AOs were check-boxed.
     *  @return True if any AOs where selected. Otherwise, false.
     */
    private boolean hasSelectedActivityOfferings(CourseOfferingManagementForm theForm) {
        boolean bIsSelected = false;
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper activityOfferingWrapper : list) {
            if (activityOfferingWrapper.getIsChecked()) {
                bIsSelected = true;
                break;
            }
        }
        return bIsSelected;
    }

    /**
     * Method used to pick the selected AO actions
     */
    @RequestMapping(params = "methodToCall=selectedAoActions")
    public ModelAndView selectedAoActions(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        //  Stop here if no AOs are selected.
        if ( ! hasSelectedActivityOfferings(theForm)) {
            GlobalVariables.getMessageMap().putError("manageActivityOfferingsPage", CourseOfferingConstants.NO_AOS_SELECTED);
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
        }

        if (StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_DELETE_ACTION)){
            return confirmDelete(theForm, result, request, response);
        }

        if (StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_DRAFT_ACTION) ||
            StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION)) {
            getViewHelperService(theForm).changeActivityOfferingsState(theForm.getActivityWrapperList(), theForm.getTheCourseOffering(), theForm.getSelectedOfferingAction());
        }

        // Reload the AOs
        CourseOfferingInfo theCourseOffering = theForm.getTheCourseOffering();
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm, theCourseOffering);

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

     /**
     *  Determine if any COs were check-boxed.
     *  @return True if any COs where selected. Otherwise, false.
     */
    private boolean hasSelectedCourseOfferings(CourseOfferingManagementForm theForm) {
        boolean isSelected = false;
        List<CourseOfferingEditWrapper> list = theForm.getCourseOfferingEditWrapperList();
        for (CourseOfferingEditWrapper coWrapper : list) {
            if (coWrapper.getIsChecked()) {
                isSelected = true;
                break;
            }
        }
        return isSelected;
    }

    @RequestMapping(params = "methodToCall=selectedCOActions")
    public ModelAndView selectedCOActions(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        //  Stop here if no COs are selected.
        if ( ! hasSelectedCourseOfferings(theForm)) {
            GlobalVariables.getMessageMap().putError("manageActivityOfferingsPage", CourseOfferingConstants.COURSEOFFERING_NONE_SELECTED);
            return getUIFModelAndView(theForm);
        }

        if (StringUtils.equals(theForm.getSelectedOfferingAction(),CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION)) {
/*
            if (!hasDialogBeenAnswered("schedulingConfirmDialog", theForm)){
                loadSelectedCOsForScheduling(theForm);
                return showDialog("schedulingConfirmDialog", theForm, request, response);
            }

            String dialogAnswer = getStringDialogResponse("schedulingConfirmDialog", theForm, request, response);

            if (StringUtils.equalsIgnoreCase(dialogAnswer,"y")){
                getViewHelperService(theForm).markCourseOfferingsForScheduling(theForm.getCourseOfferingEditWrapperList());
                getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
                theForm.getDialogManager().resetDialogStatus("schedulingConfirmDialog");
            }
*/
            getViewHelperService(theForm).markCourseOfferingsForScheduling(theForm.getCourseOfferingEditWrapperList());
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
        }

        return getUIFModelAndView(theForm);

    }

    private void loadSelectedCOsForScheduling(CourseOfferingManagementForm theForm){
        String textToDisplay = StringUtils.EMPTY;
        int count = 1;
        for (CourseOfferingEditWrapper co : theForm.getCourseOfferingEditWrapperList()) {
             if (co.getIsChecked()){
                  textToDisplay = textToDisplay + co.getCoInfo().getCourseOfferingCode() + ",";
                 count++;
             }
        }
        theForm.setToBeScheduledCourseOfferingsUI(StringUtils.stripEnd(textToDisplay,","));
        theForm.setToBeScheduledCourseOfferingsCount(count-1);
    }

    /**
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=confirmDelete")
    public ModelAndView confirmDelete(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Collection<Object> collection;
        Object selectedObject;
        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToDeleteList();
        boolean bEncounteredNonDraftAOInDeletion = false;
        boolean bLegalAOInDeletion = false;

        // clear the list
        selectedIndexList.clear();

        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isNotBlank(selectedCollectionPath)) {
            // select the single AO
            int selectedLineIndex = -1;
            String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
            if (StringUtils.isNotBlank(selectedLine)) {
                selectedLineIndex = Integer.parseInt(selectedLine);
            }

            if (selectedLineIndex == -1) {
                throw new RuntimeException("Selected line index was not set");
            }

            collection = ObjectPropertyUtils.getPropertyValue(theForm, selectedCollectionPath);

            selectedObject = ((List<Object>) collection).get(selectedLineIndex);
            // Record the selected AO IsChecked
            selectedIndexList.add((ActivityOfferingWrapper) selectedObject);
        }
        else {
            // check if there is Draft AO selected
            selectedIndexList.clear();
            for(ActivityOfferingWrapper ao : aoList) {
                if(ao.isLegalToDelete() && ao.getIsChecked()) {
                    selectedIndexList.add(ao);
                    bLegalAOInDeletion = true;
                } else if (ao.getIsChecked()){
                    if (!bEncounteredNonDraftAOInDeletion) {
                        bEncounteredNonDraftAOInDeletion = true;
                    }
                }
            }
        }

        if (selectedIndexList.isEmpty()) {
            if (bEncounteredNonDraftAOInDeletion) {
                GlobalVariables.getMessageMap().putError("manageActivityOfferingsPage",
                        CourseOfferingConstants.AO_NOT_DRAFT_FOR_DELETION_ERROR);
            } else {
                GlobalVariables.getMessageMap().putError("manageActivityOfferingsPage",
                        CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_NO_DRAFT_AO_SELECTED);
            }
            return getUIFModelAndView(theForm);
        }  else {
            theForm.setSelectedIllegalAOInDeletion(false);
            if (bEncounteredNonDraftAOInDeletion) {
                theForm.setSelectedIllegalAOInDeletion(true);
            }
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
    }

    /**
     * Method used to view a CO or an AO
     */
    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        String controllerPath = "inquiry";
        Object selectedObject = _getSelectedObject(theForm, "view");

        if(selectedObject instanceof CourseOfferingInfo){
            urlParameters = _buildCOURLParameters((CourseOfferingInfo)selectedObject,KRADConstants.START_METHOD,true,getContextInfo());
        } else if(selectedObject instanceof ActivityOfferingWrapper) {
            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)selectedObject;
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_ID, aoWrapper.getAoInfo().getId());
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingInfo.class.getName());
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=addActivityOfferings")
    public ModelAndView addActivityOfferings(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        String activityId = theForm.getActivityIdForNewAO();
        String formatId = theForm.getFormatIdForNewAO();
        int aoCount = Integer.parseInt(theForm.getNoOfActivityOfferings());

        getViewHelperService(theForm).createActivityOfferings(formatId, activityId, aoCount, theForm);

        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);

        return getUIFModelAndView(theForm);

    }

    @RequestMapping(params = "methodToCall=createCourseOffering")
    public ModelAndView createCourseOffering(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        String termCode = theForm.getTermCode();

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        props.put("targetTermCode", termCode);
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, "org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper");

         return super.performRedirect(theForm, "courseOffering", props);
    }

    @RequestMapping(params = "methodToCall=markSubjectCodeReadyForScheduling")
    public ModelAndView markSubjectCodeReadyForScheduling(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingManagementViewHelperServiceImpl helperService = (CourseOfferingManagementViewHelperServiceImpl) theForm.getView().getViewHelperService();
        //  State change all of the AOs associated with all CourseOfferings related to the course code. Passing false so that the isChecked() flag is ignored.
        helperService.markCourseOfferingsForScheduling(theForm.getCourseOfferingEditWrapperList(), false);
        getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
        return getUIFModelAndView(theForm);
    }

    /**
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     */
    @RequestMapping(params = "methodToCall=viewTheCO")
    public ModelAndView viewTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getTheCourseOffering();
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,KRADConstants.START_METHOD,false,getContextInfo());
        String controllerPath = KRADConstants.PARAM_MAINTENANCE_VIEW_MODE_INQUIRY;
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    /**
     * Method used to invoke the Edit CO screen from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     */
    @RequestMapping(params = "methodToCall=editTheCO")
    public ModelAndView editTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getTheCourseOffering();
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,KRADConstants.Maintenance.METHOD_TO_CALL_EDIT,false,getContextInfo());
        String controllerPath = KRADConstants.Maintenance.REQUEST_MAPPING_MAINTENANCE;
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    private Properties _buildCOURLParameters(CourseOfferingInfo courseOfferingInfo, String methodToCall, boolean readOnlyView, ContextInfo context){
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("coInfo.id", courseOfferingInfo.getId());
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName());
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        return props;
    }

    private Object _getSelectedObject(CourseOfferingManagementForm theForm, String actionLink){
        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }

        int selectedLineIndex = -1;
        String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(theForm, selectedCollectionPath);
        Object selectedObject = ((List<Object>) collection).get(selectedLineIndex);

        return selectedObject;
    }


    public String getOrgNameDescription(String orgShortName) {
        String shortName = "shortName";
        String longName = "";

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        if (StringUtils.isNotBlank(orgShortName) && !orgShortName.isEmpty()) {
            qBuilder.setPredicates(PredicateFactory.or(
                    PredicateFactory.equal(shortName, orgShortName)));
        } else {
            throw new RuntimeException("Org short name is null!");
        }
        try {
            QueryByCriteria query = qBuilder.build();

            OrganizationService  organizationService = getOrganizationService();

            java.util.List<OrgInfo> orgInfos = organizationService.searchForOrgs(query, getContextInfo());
            if (!orgInfos.isEmpty()){
                longName = orgInfos.get(0).getLongName();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error! No long name description found.", e);
        }
        return longName;
    }

    public CourseOfferingManagementViewHelperService getViewHelperService(CourseOfferingManagementForm theForm){

        if (viewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null){
                viewHelperService = (CourseOfferingManagementViewHelperService) theForm.getView().getViewHelperService();
            }else{
                viewHelperService= (CourseOfferingManagementViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }

        return viewHelperService;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if(stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return this.stateService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));

        }
        return organizationService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }
}