package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ManageSOCViewHelperService;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/14/12
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/manageSOC")
public class ManageSOCController extends UifControllerBase {


    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ManageSOCForm();
    }

    @RequestMapping(params = "methodToCall=lockSOC")
    public ModelAndView lockSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response){

        if (socForm.getSocInfo() == null){
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if (!StringUtils.equals(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY,socForm.getSocInfo().getStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"SOC should be in open state to lock");
            return getUIFModelAndView(socForm);
        }

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
        viewHelper.lockSOC(socForm);

        return getUIFModelAndView(socForm);
    }

    @RequestMapping(params = "methodToCall=buildModel")
    public ModelAndView buildModel(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
        socForm.clear();

        try {
            List<TermInfo> terms = viewHelper.getTermByCode(socForm.getTermCode());
            if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Multiple entries found for the term code");
                return getUIFModelAndView(socForm);
            }
            if (terms.isEmpty()){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Term not found");
                return getUIFModelAndView(socForm);
            }
            socForm.setTermInfo(terms.get(0));
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,e.getMessage());
            e.printStackTrace();
        }

        viewHelper.buildModel(socForm);

        return getUIFModelAndView(socForm);
    }
}
