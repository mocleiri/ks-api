
package org.kuali.student.core.organization.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 16 11:42:39 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "searchForResults", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchForResults", namespace = "http://org.kuali.student/core/organization", propOrder = {"arg0","arg1"})

public class SearchForResults {

    @XmlElement(name = "arg0")
    private java.lang.String arg0;
    @XmlElement(name = "arg1")
    private java.util.List<org.kuali.student.core.search.dto.QueryParamValue> arg1;

    public java.lang.String getArg0() {
        return this.arg0;
    }

    public void setArg0(java.lang.String newArg0)  {
        this.arg0 = newArg0;
    }

    public java.util.List<org.kuali.student.core.search.dto.QueryParamValue> getArg1() {
        return this.arg1;
    }

    public void setArg1(java.util.List<org.kuali.student.core.search.dto.QueryParamValue> newArg1)  {
        this.arg1 = newArg1;
    }

}

