
package org.kuali.student.lum.lrc.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Wed Apr 22 09:59:25 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getGrade", namespace = "http://student.kuali.org/lum/lrc")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getGrade", namespace = "http://student.kuali.org/lum/lrc")

public class GetGrade {

    @XmlElement(name = "gradeKey")
    private java.lang.String gradeKey;

    public java.lang.String getGradeKey() {
        return this.gradeKey;
    }

    public void setGradeKey(java.lang.String newGradeKey)  {
        this.gradeKey = newGradeKey;
    }

}

