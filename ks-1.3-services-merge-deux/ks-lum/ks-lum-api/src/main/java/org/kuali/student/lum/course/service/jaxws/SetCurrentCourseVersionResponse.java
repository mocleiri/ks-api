
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

@XmlRootElement(name = "setCurrentCourseVersionResponse", namespace = "http://student.kuali.org/wsdl/course")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setCurrentCourseVersionResponse", namespace = "http://student.kuali.org/wsdl/course")

public class SetCurrentCourseVersionResponse {

    @XmlElement(name = "return")
    private org.kuali.student.common.dto.StatusInfo _return;

    public org.kuali.student.common.dto.StatusInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.common.dto.StatusInfo new_return)  {
        this._return = new_return;
    }

}

