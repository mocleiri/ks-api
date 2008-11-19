
package org.kuali.student.lum.atp.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Wed Nov 19 10:43:53 EST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "validateDateRange", namespace = "http://org.kuali.student/lum/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateDateRange", namespace = "http://org.kuali.student/lum/atp", propOrder = {"validationType","dateRangeInfo"})

public class ValidateDateRange {

    @XmlElement(name = "validationType")
    private java.lang.String validationType;
    @XmlElement(name = "dateRangeInfo")
    private org.kuali.student.lum.atp.dto.DateRangeInfo dateRangeInfo;

    public java.lang.String getValidationType() {
        return this.validationType;
    }

    public void setValidationType(java.lang.String newValidationType)  {
        this.validationType = newValidationType;
    }

    public org.kuali.student.lum.atp.dto.DateRangeInfo getDateRangeInfo() {
        return this.dateRangeInfo;
    }

    public void setDateRangeInfo(org.kuali.student.lum.atp.dto.DateRangeInfo newDateRangeInfo)  {
        this.dateRangeInfo = newDateRangeInfo;
    }

}

