
package org.kuali.student.lum.course.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue May 18 13:33:13 PDT 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "updateCourseLos", namespace = "http://student.kuali.org/wsdl/course")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCourseLos", namespace = "http://student.kuali.org/wsdl/course", propOrder = {"courseId","loDisplayInfoList"})

public class UpdateCourseLos {

    @XmlElement(name = "courseId")
    private java.lang.String courseId;
    @XmlElement(name = "loDisplayInfoList")
    private java.util.List<org.kuali.student.lum.course.dto.LoDisplayInfo> loDisplayInfoList;

    public java.lang.String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(java.lang.String newCourseId)  {
        this.courseId = newCourseId;
    }

    public java.util.List<org.kuali.student.lum.course.dto.LoDisplayInfo> getLoDisplayInfoList() {
        return this.loDisplayInfoList;
    }

    public void setLoDisplayInfoList(java.util.List<org.kuali.student.lum.course.dto.LoDisplayInfo> newLoDisplayInfoList)  {
        this.loDisplayInfoList = newLoDisplayInfoList;
    }

}

