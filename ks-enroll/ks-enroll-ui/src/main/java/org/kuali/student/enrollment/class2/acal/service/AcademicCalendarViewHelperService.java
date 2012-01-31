package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 1/24/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AcademicCalendarViewHelperService extends ViewHelperService {
    //HC
    public HolidayCalendarInfo createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception;
    public HolidayCalendarInfo getHolidayCalendar(String hcId) throws Exception;
    public HolidayCalendarInfo updateHolidayCalendar(HolidayCalendarForm hcForm) throws Exception;
    public List<HolidayInfo> getHolidaysForHolidayCalendar(HolidayCalendarForm hcForm) throws Exception;
    public HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo) throws Exception;

    //Acal
    public AcademicCalendarInfo createAcademicCalendar(AcademicCalendarForm acalForm) throws Exception;
    public AcademicCalendarInfo getAcademicCalendar(String acalId) throws Exception;
    public AcademicCalendarInfo updateAcademicCalendar(AcademicCalendarForm acalForm) throws Exception;
}
