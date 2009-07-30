
package org.kuali.student.core.atp.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Mar 31 14:06:06 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "addDateRange", namespace = "http://student.kuali.org/core/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addDateRange", namespace = "http://student.kuali.org/core/atp", propOrder = {"atpKey","dateRangeKey","dateRangeInfo"})

public class AddDateRange {

    @XmlElement(name = "atpKey")
    private java.lang.String atpKey;
    @XmlElement(name = "dateRangeKey")
    private java.lang.String dateRangeKey;
    @XmlElement(name = "dateRangeInfo")
    private org.kuali.student.core.atp.dto.DateRangeInfo dateRangeInfo;

    public java.lang.String getAtpKey() {
        return this.atpKey;
    }

    public void setAtpKey(java.lang.String newAtpKey)  {
        this.atpKey = newAtpKey;
    }

    public java.lang.String getDateRangeKey() {
        return this.dateRangeKey;
    }

    public void setDateRangeKey(java.lang.String newDateRangeKey)  {
        this.dateRangeKey = newDateRangeKey;
    }

    public org.kuali.student.core.atp.dto.DateRangeInfo getDateRangeInfo() {
        return this.dateRangeInfo;
    }

    public void setDateRangeInfo(org.kuali.student.core.atp.dto.DateRangeInfo newDateRangeInfo)  {
        this.dateRangeInfo = newDateRangeInfo;
    }

}

