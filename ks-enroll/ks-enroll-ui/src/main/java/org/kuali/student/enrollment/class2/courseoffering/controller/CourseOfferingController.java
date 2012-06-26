package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.common.search.dto.*;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExistingCourseOffering;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Controller
@RequestMapping(value = "/courseOffering")
public class CourseOfferingController extends MaintenanceDocumentController {

    private LuService luService;
    private CourseService courseService;
    private AcademicCalendarService academicCalendarService;
    private CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;

    @RequestMapping(params = "methodToCall=loadCourseCatalog")
    public ModelAndView loadCourseCatalog(@ModelAttribute("KualiForm") MaintenanceForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper coWrapper = ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject());
        String courseCode = coWrapper.getCatalogCourseCode();
        String termCode = coWrapper.getTargetTermCode();

        TermInfo term = getTerm(termCode);
        coWrapper.setTerm(term);

        CourseInfo course = getCourseInfo(courseCode);
        coWrapper.setCourse(course);

        if (course != null && term != null) {
            coWrapper.setCourse(course);
            coWrapper.setCreditCount(course.getCreditOptions().get(0).getResultValues().get(0));
            coWrapper.setShowAllSections(true);
            coWrapper.setShowCatalogLink(false);
            coWrapper.setShowTermOfferingLink(true);

            List<String> courseOfferingIds = getCourseOfferingService().getCourseOfferingIdsByTerm(term.getId(),false,getContextInfo());
            List<CourseOfferingInfo> courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByIds(courseOfferingIds,getContextInfo());

            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                ExistingCourseOffering co = new ExistingCourseOffering();
                co.setTermCode(getTerm(term.getCode()).getName());
                co.setCourseOfferingCode(courseOfferingInfo.getCourseOfferingCode());
                co.setCourseTitle(courseOfferingInfo.getCourseOfferingTitle());
                co.setCredits(courseOfferingInfo.getCreditOptionId());
                co.setGrading(courseOfferingInfo.getGradingOptionId());
                coWrapper.getExistingCourseOfferings().add(co);
            }

        } else {
            coWrapper.setCourse(null);
            coWrapper.setShowAllSections(false);
            coWrapper.setCreditCount("");
            coWrapper.getExistingTermOfferings().clear();
            coWrapper.getExistingCourseOfferings().clear();
        }

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=createFromCatalog")
    public ModelAndView createFromCatalog(@ModelAttribute("KualiForm") MaintenanceForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        wrapper.setShowCatalogLink(false);
        wrapper.setShowTermOfferingLink(true);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=createFromTermOffering")
    public ModelAndView createFromTermOffering(@ModelAttribute("KualiForm") MaintenanceForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        wrapper.setShowCatalogLink(true);
        wrapper.setShowTermOfferingLink(false);

        if (wrapper.getExistingTermOfferings().isEmpty()){
            List<CourseOfferingInfo> courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByCourse(wrapper.getCourse().getId(),getContextInfo());
            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                ExistingCourseOffering co = new ExistingCourseOffering();
                TermInfo term = getAcademicCalendarService().getTerm(courseOfferingInfo.getTermId(),getContextInfo());
                co.setTermCode(term.getName());
                co.setCourseOfferingCode(courseOfferingInfo.getCourseOfferingCode());
                co.setCourseTitle(courseOfferingInfo.getCourseOfferingTitle());
                co.setCredits(courseOfferingInfo.getCreditOptionId());
                co.setGrading(courseOfferingInfo.getGradingOptionId());
                wrapper.getExistingTermOfferings().add(co);
            }
        }

        return getUIFModelAndView(form);
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

    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
             academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

    private CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    private CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    private CourseInfo getCourseInfo(String courseName) {

        CourseInfo        returnCourseInfo = null;
        String            courseId         = null;
        List<SearchParam> searchParams     = new ArrayList<SearchParam>();
        List <CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.setValue("kuali.lu.type.CreditCourse");
        searchParams.add(qpv1);

        SearchParam qpv2 = new SearchParam();
        qpv2.setKey("lu.queryParam.luOptionalCode");
        qpv2.setValue(courseName);
        searchParams.add(qpv2);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.mostCurrent.union");

        try {
            SearchResult searchResult = getLuService().search(searchRequest);
            if (searchResult.getRows().size() > 0) {
                for(SearchResultRow row : searchResult.getRows()){
                    List<SearchResultCell> srCells = row.getCells();
                    if(srCells != null && srCells.size() > 0){
                        for(SearchResultCell cell : srCells){
                            if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                                courseId = cell.getValue();
                                returnCourseInfo = getCourseService().getCourse(courseId);
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

     private LuService getLuService() {
        if(luService == null) {
            luService = CourseOfferingResourceLoader.loadLuService();
        }
        return luService;
    }

}
