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
 * Created by Li Pan on 1/18/12
 */
package org.kuali.student.enrollment.class2.acal.controller;

import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implement controller for HolidayCalendar
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/holidayCalendar")
public class HolidayCalendarController extends UifControllerBase {
    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new HolidayCalendarForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        HolidayCalendarForm hcForm = (HolidayCalendarForm) form;
        if(action != null && !action.trim().isEmpty()){
            HolidayInfo holiday = new HolidayInfo();

            if(action.equals("C")){
                hcForm.getHolidays().add(holiday);
            }

            if (action.equals("U")){
                //get hc and populate it on the form
            }
        }

        return super.start(form, result, request, response);
    }

     /**
     * Method used to save HC
     */
    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        HolidayCalendarInfo hc = hcForm.getHolidayCalendarInfo();

        if(hc.getId() != null && !hc.getId().trim().isEmpty()){
            // edit hc
        }
        else {
           // create hc
            //TODO: fix hc service impl bugs first, come back later
            //createHolidayCalendar(hcForm);
        }
        return getUIFModelAndView(hcForm);
    }

    private void createHolidayCalendar(HolidayCalendarForm hcForm)  throws Exception {
       ((AcademicCalendarViewHelperService)hcForm.getView().getViewHelperService()).createHolidayCalendar(hcForm);
    }
}
