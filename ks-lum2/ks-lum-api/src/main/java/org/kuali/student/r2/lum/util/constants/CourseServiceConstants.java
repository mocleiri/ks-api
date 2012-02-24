package org.kuali.student.r2.lum.util.constants;


import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

/**
 * Course Service contants.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public class CourseServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "course";
    public static final String REF_OBJECT_URI_COURSE_OFFERING = NAMESPACE + "/" + CourseInfo.class.getSimpleName();

}
