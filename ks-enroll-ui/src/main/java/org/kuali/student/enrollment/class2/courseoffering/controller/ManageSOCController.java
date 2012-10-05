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
 * @author Kuali Student Team
 */

package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ManageSOCViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This class handles all the request for Managing SOC. This handles requests from ManageSOCView for different SOC state
 * changes and scheduling/publishing events
 */
@Controller
@RequestMapping(value = "/manageSOC")
public class ManageSOCController extends UifControllerBase {

    final static Logger LOG = Logger.getLogger(ManageSOCController.class);

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        ManageSOCForm form = new ManageSOCForm();

        try {
            LOG.debug("Loading SOC states");
            List<StateInfo>  allSOCStates = CourseOfferingResourceLoader.loadStateService().getStatesByLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, ContextUtils.createDefaultContextInfo());
            for (StateInfo stateInfo : allSOCStates) {
                form.getSocStateKeys2Names().put(stateInfo.getKey(), stateInfo.getName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return form;
    }

    @RequestMapping(params = "methodToCall=lockSOC")
    public ModelAndView lockSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        LOG.debug("Locking SOC");

        String dialogName = ManageSocConstants.ConfirmDialogs.LOCK;

        if (!hasDialogBeenAnswered(dialogName, socForm)){
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer){

            if (socForm.getSocInfo() == null){
                throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
            }

            if (!StringUtils.equals(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY,socForm.getSocInfo().getStateKey())){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"SOC should be in open state to lock");
                return getUIFModelAndView(socForm);
            }

            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
            viewHelper.lockSOC(socForm);

            return buildModel(socForm, result, request, response);

        } else {
            return getUIFModelAndView(socForm);
        }

    }

    @RequestMapping(params = "methodToCall=sendApprovedActivitiesToScheduler")
    public ModelAndView sendApprovedActivitiesToScheduler (@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                                            HttpServletRequest request, HttpServletResponse response)throws Exception {

        if ( ! StringUtils.equals(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY,socForm.getSocInfo().getStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "SOC should be in LOCKED state!");
            return getUIFModelAndView(socForm);
        }

        String dialogName = ManageSocConstants.ConfirmDialogs.MASS_SCHEDULING;

        if (!hasDialogBeenAnswered(dialogName, socForm)){
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer){
            // start send approved activities to scheduler
            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService) socForm.getView().getViewHelperService();
            viewHelper.startMassScheduling(socForm);
            return buildModel(socForm, result, request, response);
        } else {
            return getUIFModelAndView(socForm);
        }
    }

    @RequestMapping(params = "methodToCall=buildModel")
    public ModelAndView buildModel(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
        socForm.clear();

        try {
            List<TermInfo> terms = viewHelper.getTermByCode(socForm.getTermCode());
            if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Multiple entries found for the term code");
                return getUIFModelAndView(socForm);
            }
            if (terms.isEmpty()){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Term not found");
                return getUIFModelAndView(socForm);
            }
            socForm.setTermInfo(terms.get(0));
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,e.getMessage());
            LOG.error("Error building model.", e);
        }

        viewHelper.buildModel(socForm);

        return getUIFModelAndView(socForm);
    }

    @RequestMapping(params = "methodToCall=allowFinalEdits")
    public ModelAndView allowFinalEdits(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (socForm.getSocInfo() == null){
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if ( ! StringUtils.equals(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED,socForm.getSocInfo().getSchedulingStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "SOC scheduling should be completed for final edits");
            return getUIFModelAndView(socForm);
        }

        String dialogName = ManageSocConstants.ConfirmDialogs.FINAL_EDITS;

        if (!hasDialogBeenAnswered(dialogName, socForm)){
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer){

            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
            viewHelper.allowSOCFinalEdit(socForm);

            return buildModel(socForm, result, request, response);

        } else {
            return getUIFModelAndView(socForm);
        }
    }

    @RequestMapping(params = "methodToCall=publishSOC")
    public ModelAndView publishSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (socForm.getSocInfo() == null){
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if ( ! StringUtils.equals(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, socForm.getSocInfo().getStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "SOC should be at Final Edit for publish");
            return getUIFModelAndView(socForm);
        }

        String dialogName = ManageSocConstants.ConfirmDialogs.MASS_PUBLISHLING;

        if (!hasDialogBeenAnswered(dialogName, socForm)){
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer){
            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
            try {
                viewHelper.publishSOC(socForm);
            } catch(Exception e) {
                LOG.error("Could not start mass publishing event.", e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Unable to initiate publishing.");
                return getUIFModelAndView(socForm);
            }

            return buildModel(socForm, result, request, response);
        } else {
            return getUIFModelAndView(socForm);
        }
    }

    @RequestMapping(params = "methodToCall=closeSOC")
    public ModelAndView closeSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (socForm.getSocInfo() == null){
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if (!StringUtils.equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY,socForm.getSocInfo().getStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM," SOC should be at Publish state to close");
            return getUIFModelAndView(socForm);
        }

        String dialogName = ManageSocConstants.ConfirmDialogs.CLOSE_SET;

        if (!hasDialogBeenAnswered(dialogName, socForm)){
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer){
            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
            viewHelper.closeSOC(socForm);

            return buildModel(socForm, result, request, response);
        } else {
            return getUIFModelAndView(socForm);
        }
    }
}
