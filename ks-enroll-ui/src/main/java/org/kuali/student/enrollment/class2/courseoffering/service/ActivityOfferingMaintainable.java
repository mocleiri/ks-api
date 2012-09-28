package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.ActivityOfferingForm;

public interface ActivityOfferingMaintainable extends Maintainable{

    public void addScheduleRequestComponent(ActivityOfferingForm form);

    public void prepareForScheduleRevise(ActivityOfferingWrapper wrapper);

    public void saveAndProcessScheduleRequest(ActivityOfferingWrapper activityOfferingWrapper,ActivityOfferingForm form);
}
