
package org.kuali.student.core.search.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.search.dto.SearchResultTypeInfo;

/**
 * This class was generated by Apache CXF 2.1.3
 * Tue Jan 27 08:59:03 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getSearchResultTypesResponse", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSearchResultTypesResponse", namespace = "http://org.kuali.student/core/organization")

public class GetSearchResultTypesResponse {

    @XmlElement(name = "return")
    private java.util.List<SearchResultTypeInfo> _return;

    public java.util.List<SearchResultTypeInfo> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<SearchResultTypeInfo> new_return)  {
        this._return = new_return;
    }

}

