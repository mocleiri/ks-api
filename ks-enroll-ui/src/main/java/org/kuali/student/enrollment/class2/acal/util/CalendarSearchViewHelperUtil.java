package org.kuali.student.enrollment.class2.acal.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.r2.common.class1.type.dto.TypeInfo;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;

public class CalendarSearchViewHelperUtil {

    public final static String NAME = "name";
    public final static String START_DATE = "startDate";
    public final static String END_DATE = "endDate";
    public final static String CALENDAR_TYPE = "atpType";


    public static List<TermInfo> searchForTerms(String name, String year,ContextInfo context, AcademicCalendarService academicCalendarService)throws Exception {

    	List<TermInfo> termInfoList = new ArrayList<TermInfo>();

        QueryByCriteria.Builder query = buildQueryByCriteria(name,year,"Term");

        List<TermInfo> terms = academicCalendarService.searchForTerms(query.build(),context);
        for (TermInfo term : terms) {
            termInfoList.add(term);
        }

        return termInfoList;

    }

    public static List<AcademicCalendarInfo> searchForAcademicCalendars(String name, String year,ContextInfo context, AcademicCalendarService academicCalendarService)throws Exception {

        List<AcademicCalendarInfo> acalInfoList = new ArrayList<AcademicCalendarInfo>();

        QueryByCriteria.Builder query = buildQueryByCriteria(name,year,AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);

        List<AcademicCalendarInfo> acals = academicCalendarService.searchForAcademicCalendars(query.build(), context);
        for (AcademicCalendarInfo acal : acals) {
            acalInfoList.add(acal);
        }

        return acalInfoList;


    }

    public static List<HolidayCalendarInfo> searchForHolidayCalendars(String name, String year,ContextInfo context, AcademicCalendarService academicCalendarService)throws Exception {

        List<HolidayCalendarInfo> hCals = new ArrayList<HolidayCalendarInfo>();

        QueryByCriteria.Builder query = buildQueryByCriteria(name,year,AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);

        List<HolidayCalendarInfo> hcs = academicCalendarService.searchForHolidayCalendars(query.build(), context);
        for (HolidayCalendarInfo hc : hcs) {
            hCals.add(hc);
        }

        return hCals;


    }

    private static QueryByCriteria.Builder buildQueryByCriteria(String name, String year,String typeKey){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(name)){
            p = like(NAME, "%" + name + "%");
    		pList.add(p);
        }

        if (StringUtils.isNotBlank(year)){
            try {
                //FIXME: Find some better way to check the year
                Predicate startDatePredicate = and(greaterThanOrEqual(START_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("01/01/" + year)),
                                                   lessThanOrEqual(START_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("12/31/" + year)));


                Predicate endDatePredicate = and(greaterThanOrEqual(END_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("01/01/" + year)),
                                                lessThanOrEqual(END_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("12/31/" + year)));

                pList.add(or(startDatePredicate, endDatePredicate));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        if (StringUtils.equalsIgnoreCase(typeKey, "Term")){
            p = notIn(CALENDAR_TYPE,AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY, AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        }else{
            p = equal(CALENDAR_TYPE,typeKey);
        }

        pList.add(p);

        if (!pList.isEmpty()){
            Predicate[] preds = new Predicate[pList.size()];
            pList.toArray(preds);
            qBuilder.setPredicates(and(preds));
        }
        return qBuilder;
    }

    public static HolidayCalendarWrapper getHolidayCalendarWrapper(String hcId, AcademicCalendarService acalService, ContextInfo context){

        HolidayCalendarWrapper holidayCalendarWrapper = new HolidayCalendarWrapper();
        List<HolidayWrapper> holidays = new ArrayList<HolidayWrapper>();
        try{
            //need to retrieve HolidayCalendarInfo and all Holidays to form the HolidayCalendarWrapper.
            HolidayCalendarInfo holidayCalendarInfo = acalService.getHolidayCalendar(hcId, context);
            holidayCalendarWrapper.setHolidayCalendarInfo(holidayCalendarInfo);
            holidayCalendarWrapper.setId(holidayCalendarInfo.getId());
            holidayCalendarWrapper.setAdminOrgName(CommonUtils.getAdminOrgNameById(holidayCalendarInfo.getAdminOrgId()));
            StateInfo hcState = acalService.getHolidayCalendarState(holidayCalendarInfo.getStateKey(), context);
            holidayCalendarWrapper.setStateName(hcState.getName());

            List<HolidayInfo> holidayInfoList = acalService.getHolidaysForHolidayCalendar(holidayCalendarInfo.getId(), context);
            for(HolidayInfo holidayInfo : holidayInfoList){
                HolidayWrapper holiday = new HolidayWrapper(holidayInfo);
                TypeInfo typeInfo = acalService.getHolidayType(holidayInfo.getTypeKey(), context);
                holiday.setTypeName(typeInfo.getName());
                holidays.add(holiday);
            }
            holidayCalendarWrapper.setHolidays(holidays);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return holidayCalendarWrapper;

    }

    public static List<HolidayCalendarWrapper> loadHolidayCalendars(AcademicCalendarInfo acalInfo, AcademicCalendarService acalService, ContextInfo context) throws Exception {
        List<HolidayCalendarWrapper> holidayCalendarWrapperList = new ArrayList<HolidayCalendarWrapper>();
        List<String> hcIds = acalInfo.getHolidayCalendarIds();
        if (hcIds != null && !hcIds.isEmpty()){
            for (String hcId : hcIds){
                HolidayCalendarWrapper holidayCalendarWrapper = CalendarSearchViewHelperUtil.getHolidayCalendarWrapper(hcId,acalService, context);
                holidayCalendarWrapperList.add(holidayCalendarWrapper);
            }
        }
        return holidayCalendarWrapperList;
    }

}
