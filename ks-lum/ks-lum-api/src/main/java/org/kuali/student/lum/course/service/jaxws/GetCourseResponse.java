
package org.kuali.student.lum.course.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2.9
 * Thu Nov 04 11:39:27 EDT 2010
 * Generated source version: 2.2.9
 */

@XmlRootElement(name = "getCourseResponse", namespace = "http://student.kuali.org/wsdl/course")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCourseResponse", namespace = "http://student.kuali.org/wsdl/course")

public class GetCourseResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.course.dto.CourseInfo _return;

    public org.kuali.student.lum.course.dto.CourseInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.course.dto.CourseInfo new_return)  {
        this._return = new_return;
    }

}

