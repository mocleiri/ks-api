
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

@XmlRootElement(name = "getAtpsByDate", namespace = "http://student.kuali.org/core/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAtpsByDate", namespace = "http://student.kuali.org/core/atp")

public class GetAtpsByDate {

    @XmlElement(name = "searchDate")
    private java.util.Date searchDate;

    public java.util.Date getSearchDate() {
        return this.searchDate;
    }

    public void setSearchDate(java.util.Date newSearchDate)  {
        this.searchDate = newSearchDate;
    }

}

