
package org.kuali.student.core.search.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.search.dto.Result;

/**
 * This class was generated by Apache CXF 2.1.4
 * Thu Feb 19 10:05:55 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "searchForResultsResponse", namespace = "http://student.kuali.org/wsdl/search")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchForResultsResponse", namespace = "http://student.kuali.org/wsdl/search")

public class SearchForResultsResponse {

    @XmlElement(name = "return")
    private java.util.List<Result> _return;

    public java.util.List<Result> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<Result> new_return)  {
        this._return = new_return;
    }

}

